package com.auth0.rainbow.service.impl;

import com.auth0.rainbow.domain.AppPost;
import com.auth0.rainbow.domain.AppPostImage;
import com.auth0.rainbow.repository.AppPostRepository;
import com.auth0.rainbow.service.AppPostService;
import com.auth0.rainbow.service.dto.AppPostDTO;
import com.auth0.rainbow.service.dto.AppPostImageDTO;
import com.auth0.rainbow.service.mapper.AppPostImageMapper;
import com.auth0.rainbow.service.mapper.AppPostMapper;
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

    private final AppPostImageMapper appPostImageMapper;

    private final AppPostRepository appPostRepository;

    private final AppPostMapper appPostMapper;

    public AppPostServiceImpl(AppPostRepository appPostRepository, AppPostMapper appPostMapper, AppPostImageMapper appPostImageMapper) {
        this.appPostRepository = appPostRepository;
        this.appPostImageMapper = appPostImageMapper;
        this.appPostMapper = appPostMapper;
    }

    @Override
    public AppPostDTO save(AppPostDTO appPostDTO) {
        log.debug("Request to save AppPost : {}", appPostDTO);
        AppPost appPost = appPostMapper.toEntity(appPostDTO);
        appPost = appPostRepository.save(appPost);
        return appPostMapper.toDto(appPost);
    }

    @Override
    public AppPostDTO update(AppPostDTO appPostDTO) {
        log.debug("Request to update AppPost : {}", appPostDTO);
        AppPost appPost = appPostMapper.toEntity(appPostDTO);
        appPost = appPostRepository.save(appPost);
        return appPostMapper.toDto(appPost);
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
            .map(appPostMapper::toDto);
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
        // AppPost appPost = appPostRepository.findById(id).orElse(null);
        // Set<AppPostImage> appPostImages = appPostRepository.findImagesByPostId(id);
        // Set<AppPostImageDTO> appPostImageDTOs = appPostImages.stream()
        //         .map(appPostImage -> AppPostMapper.INSTANCE.toDtoImage(appPostImage))
        //         .collect(Collectors.toSet());

        // AppPostDTO appPostDTO = AppPostMapper.INSTANCE.toDto(appPost);
        // appPostDTO.setImages(appPostImageDTOs);
        // return Optional.ofNullable(appPostDTO);

    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AppPost : {}", id);
        appPostRepository.deleteById(id);
    }
}
