package com.auth0.rainbow.service.impl;

import com.auth0.rainbow.domain.AppPostImage;
import com.auth0.rainbow.repository.AppPostImageRepository;
import com.auth0.rainbow.service.AppPostImageService;
import com.auth0.rainbow.service.dto.AppPostImageDTO;
import com.auth0.rainbow.service.mapper.AppPostImageMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AppPostImage}.
 */
@Service
@Transactional
public class AppPostImageServiceImpl implements AppPostImageService {

    private final Logger log = LoggerFactory.getLogger(AppPostImageServiceImpl.class);

    private final AppPostImageRepository appPostImageRepository;

    private final AppPostImageMapper appPostImageMapper;

    public AppPostImageServiceImpl(AppPostImageRepository appPostImageRepository, AppPostImageMapper appPostImageMapper) {
        this.appPostImageRepository = appPostImageRepository;
        this.appPostImageMapper = appPostImageMapper;
    }

    @Override
    public AppPostImageDTO save(AppPostImageDTO appPostImageDTO) {
        log.debug("Request to save AppPostImage : {}", appPostImageDTO);
        AppPostImage appPostImage = appPostImageMapper.toEntity(appPostImageDTO);
        appPostImage = appPostImageRepository.save(appPostImage);
        return appPostImageMapper.toDto(appPostImage);
    }

    @Override
    public AppPostImageDTO update(AppPostImageDTO appPostImageDTO) {
        log.debug("Request to update AppPostImage : {}", appPostImageDTO);
        AppPostImage appPostImage = appPostImageMapper.toEntity(appPostImageDTO);
        appPostImage = appPostImageRepository.save(appPostImage);
        return appPostImageMapper.toDto(appPostImage);
    }

    @Override
    public Optional<AppPostImageDTO> partialUpdate(AppPostImageDTO appPostImageDTO) {
        log.debug("Request to partially update AppPostImage : {}", appPostImageDTO);

        return appPostImageRepository
            .findById(appPostImageDTO.getId())
            .map(existingAppPostImage -> {
                appPostImageMapper.partialUpdate(existingAppPostImage, appPostImageDTO);

                return existingAppPostImage;
            })
            .map(appPostImageRepository::save)
            .map(appPostImageMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppPostImageDTO> findAll() {
        log.debug("Request to get all AppPostImages");
        return appPostImageRepository.findAll().stream().map(appPostImageMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AppPostImageDTO> findOne(Long id) {
        log.debug("Request to get AppPostImage : {}", id);
        return appPostImageRepository.findById(id).map(appPostImageMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AppPostImage : {}", id);
        appPostImageRepository.deleteById(id);
    }
}
