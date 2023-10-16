package com.auth0.rainbow.service.impl;

import com.auth0.rainbow.domain.AppProductImage;
import com.auth0.rainbow.repository.AppProductImageRepository;
import com.auth0.rainbow.service.AppProductImageService;
import com.auth0.rainbow.service.dto.AppProductImageDTO;
import com.auth0.rainbow.service.mapper.AppProductImageMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AppProductImage}.
 */
@Service
@Transactional
public class AppProductImageServiceImpl implements AppProductImageService {

    private final Logger log = LoggerFactory.getLogger(AppProductImageServiceImpl.class);

    private final AppProductImageRepository appProductImageRepository;

    private final AppProductImageMapper appProductImageMapper;

    public AppProductImageServiceImpl(AppProductImageRepository appProductImageRepository, AppProductImageMapper appProductImageMapper) {
        this.appProductImageRepository = appProductImageRepository;
        this.appProductImageMapper = appProductImageMapper;
    }

    @Override
    public AppProductImageDTO save(AppProductImageDTO appProductImageDTO) {
        log.debug("Request to save AppProductImage : {}", appProductImageDTO);
        AppProductImage appProductImage = appProductImageMapper.toEntity(appProductImageDTO);
        appProductImage = appProductImageRepository.save(appProductImage);
        return appProductImageMapper.toDto(appProductImage);
    }

    @Override
    public AppProductImageDTO update(AppProductImageDTO appProductImageDTO) {
        log.debug("Request to update AppProductImage : {}", appProductImageDTO);
        AppProductImage appProductImage = appProductImageMapper.toEntity(appProductImageDTO);
        appProductImage = appProductImageRepository.save(appProductImage);
        return appProductImageMapper.toDto(appProductImage);
    }

    @Override
    public Optional<AppProductImageDTO> partialUpdate(AppProductImageDTO appProductImageDTO) {
        log.debug("Request to partially update AppProductImage : {}", appProductImageDTO);

        return appProductImageRepository
            .findById(appProductImageDTO.getId())
            .map(existingAppProductImage -> {
                appProductImageMapper.partialUpdate(existingAppProductImage, appProductImageDTO);

                return existingAppProductImage;
            })
            .map(appProductImageRepository::save)
            .map(appProductImageMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppProductImageDTO> findAll() {
        log.debug("Request to get all AppProductImages");
        return appProductImageRepository
            .findAll()
            .stream()
            .map(appProductImageMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AppProductImageDTO> findOne(Long id) {
        log.debug("Request to get AppProductImage : {}", id);
        return appProductImageRepository.findById(id).map(appProductImageMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AppProductImage : {}", id);
        appProductImageRepository.deleteById(id);
    }
}
