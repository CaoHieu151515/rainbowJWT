package com.auth0.rainbow.service.impl;

import com.auth0.rainbow.domain.AppPost;
import com.auth0.rainbow.domain.AppPostImage;
import com.auth0.rainbow.domain.AppUser;
import com.auth0.rainbow.domain.LinkAccountUser;
import com.auth0.rainbow.domain.User;
import com.auth0.rainbow.repository.AppPostImageRepository;
import com.auth0.rainbow.repository.AppPostRepository;
import com.auth0.rainbow.repository.AppUserRepository;
import com.auth0.rainbow.repository.LinkAccountUserRepository;
import com.auth0.rainbow.service.AppPostService;
import com.auth0.rainbow.service.UserService;
import com.auth0.rainbow.service.dto.AppPostDTO;
import com.auth0.rainbow.service.dto.AppPostImageDTO;
import com.auth0.rainbow.service.mapper.AppPostMapper;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AppPost}.
 */
@Service
@Transactional
public class AppPostServiceImpl implements AppPostService {

    private final Logger log = LoggerFactory.getLogger(AppPostServiceImpl.class);

    // private final AppPostImageMapper appPostImageMapper;

    private final AppPostRepository appPostRepository;

    private final AppPostMapper appPostMapper;

    private final AppUserRepository appUserRepository;

    private final AppPostImageRepository appPostImageRepository;

    private final UserService userService;

    private final LinkAccountUserRepository linkAccountUserRepository;

    public AppPostServiceImpl(
        AppPostRepository appPostRepository,
        AppPostMapper appPostMapper,
        // AppPostImageMapper appPostImageMapper,
        AppUserRepository appUserRepository,
        AppPostImageRepository appPostImageRepository,
        UserService userService,
        LinkAccountUserRepository linkAccountUserRepository
    ) {
        this.appPostRepository = appPostRepository;
        // this.appPostImageMapper = appPostImageMapper;
        this.appPostMapper = appPostMapper;
        this.appUserRepository = appUserRepository;
        this.appPostImageRepository = appPostImageRepository;
        this.userService = userService;
        this.linkAccountUserRepository = linkAccountUserRepository;
    }

    @Override
    public AppPostDTO save(AppPostDTO appPostDTO) {
        log.debug("Request to save AppPost : {}", appPostDTO);
        AppPost appPost = appPostMapper.toEntity(appPostDTO);

        log.debug("Request to save AppPost : {}", GetCurrentAppUser());
        appPost.setUser(GetCurrentAppUser());

        log.debug("Request to update AppPost : {}", appPost.getUser());

        appPost.setImages(saveimage(appPostDTO.getImages(), appPost));
        appPost = appPostRepository.save(appPost);

        return appPostMapper.toPOSTUpdateDTO(appPost);
    }

    @Override
    public AppPostDTO update(AppPostDTO appPostDTO) {
        log.debug("Request to update AppPost : {}", appPostDTO);
        AppPost appPost = appPostMapper.toEntity(appPostDTO);

        //xóa hết ảnh hiện có
        AppPost existingPost = appPostRepository.findById(appPostDTO.getId()).orElse(null);
        deleteExistingImages(existingPost);
        //Lưu ảnh mới
        appPost.setImages(saveimage(appPostDTO.getImages(), appPost));
        //gán lại AppUser
        Optional<AppUser> optionalEntity = appUserRepository.findOneWithEagerRelationships(appPostDTO.getUser().getId());
        if (optionalEntity.isPresent()) {
            log.debug("Request to update AppPost : {}", optionalEntity);
            AppUser apuser = optionalEntity.get();
            appPost.setUser(apuser);
        }

        appPost = appPostRepository.save(appPost);
        return appPostMapper.toPOSTDTO(appPost);
    }

    @Override
    public Optional<AppPostDTO> partialUpdate(AppPostDTO appPostDTO) {
        log.debug("Request to partially update AppPost : {}", appPostDTO);

        return appPostRepository
            .findById(appPostDTO.getId())
            .map(existingAppPost -> {
                appPostMapper.partialUpdate(existingAppPost, appPostDTO);

                return existingAppPost;
            })
            .map(appPostRepository::save)
            .map(appPostMapper::toPOSTDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppPostDTO> findAll() {
        log.debug("Request to get all AppPosts");
        return appPostRepository.findAll().stream().map(appPostMapper::toPOSTDTO).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AppPostDTO> findOne(Long id) {
        log.debug("Request to get AppPost : {}", id);
        return appPostRepository.findById(id).map(appPostMapper::toPOSTDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppPostDTO> findAllfeature() {
        log.debug("Request to get all AppPosts");
        List<AppPost> postList = new ArrayList<>(appPostRepository.findByIsFeaturedTrue());

        for (AppPost a : postList) {
            if (a.getIsFeatured() != true) {
                postList.remove(a);
            }
        }

        return postList.stream().map(appPostMapper::toPOSTDTO).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AppPost : {}", id);
        appPostRepository.deleteById(id);
    }

    @Override
    public Set<AppPostImage> saveimage(Set<AppPostImageDTO> images, AppPost appPost) {
        Set<AppPostImage> imageEntities = new HashSet<>();
        for (AppPostImageDTO imageDTO : images) {
            AppPostImage imageEntity = new AppPostImage();
            imageEntity.setImageUrl(imageDTO.getImageUrl());
            imageEntity.setPost(appPost);
            imageEntities.add(imageEntity);
        }

        // Save each image entity in the database
        for (AppPostImage image : imageEntities) {
            appPostImageRepository.save(image);
        }

        return imageEntities;
    }

    // private Set<AppPostImage> createimage(Set<AppPostImageDTO> images) {
    //     Set<AppPostImage> imageEntities = new HashSet<>();
    //     for (AppPostImageDTO imageDTO : images) {
    //         AppPostImage imageEntity = new AppPostImage();
    //         imageEntity.setImageUrl(imageDTO.getImageUrl());
    //         imageEntities.add(imageEntity);
    //     }

    //     // Save each image entity in the database
    //     for (AppPostImage image : imageEntities) {
    //         appPostImageRepository.save(image);
    //     }

    //     return imageEntities;
    // }

    private void deleteExistingImages(AppPost existingPost) {
        if (existingPost != null) {
            Set<AppPostImage> existingImages = existingPost.getImages();
            for (AppPostImage image : existingImages) {
                appPostImageRepository.delete(image);
            }
        }
    }

    private AppUser GetCurrentAppUser() {
        Optional<User> optionalUser = userService.getUserWithAuthorities();
        User currentUser = optionalUser.orElseThrow(() -> new RuntimeException("User not found"));

        LinkAccountUser LinkAc = linkAccountUserRepository.findByUserId(currentUser.getId());

        AppUser appUser = LinkAc.getAppUser();
        log.debug("Current AppUser", appUser);
        return appUser;
    }
}
