package com.auth0.rainbow.service.impl;

import com.auth0.rainbow.domain.AppLessonVideo;
import com.auth0.rainbow.repository.AppLessonVideoRepository;
import com.auth0.rainbow.service.AppLessonVideoService;
import com.auth0.rainbow.service.dto.AppLessonVideoDTO;
import com.auth0.rainbow.service.mapper.AppLessonVideoMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AppLessonVideo}.
 */
@Service
@Transactional
public class AppLessonVideoServiceImpl implements AppLessonVideoService {

    private final Logger log = LoggerFactory.getLogger(AppLessonVideoServiceImpl.class);

    private final AppLessonVideoRepository appLessonVideoRepository;

    private final AppLessonVideoMapper appLessonVideoMapper;

    public AppLessonVideoServiceImpl(AppLessonVideoRepository appLessonVideoRepository, AppLessonVideoMapper appLessonVideoMapper) {
        this.appLessonVideoRepository = appLessonVideoRepository;
        this.appLessonVideoMapper = appLessonVideoMapper;
    }

    @Override
    public AppLessonVideoDTO save(AppLessonVideoDTO appLessonVideoDTO) {
        log.debug("Request to save AppLessonVideo : {}", appLessonVideoDTO);
        AppLessonVideo appLessonVideo = appLessonVideoMapper.toEntity(appLessonVideoDTO);
        appLessonVideo = appLessonVideoRepository.save(appLessonVideo);
        return appLessonVideoMapper.toDto(appLessonVideo);
    }

    @Override
    public AppLessonVideoDTO update(AppLessonVideoDTO appLessonVideoDTO) {
        log.debug("Request to update AppLessonVideo : {}", appLessonVideoDTO);
        AppLessonVideo appLessonVideo = appLessonVideoMapper.toEntity(appLessonVideoDTO);
        appLessonVideo = appLessonVideoRepository.save(appLessonVideo);
        return appLessonVideoMapper.toDto(appLessonVideo);
    }

    @Override
    public Optional<AppLessonVideoDTO> partialUpdate(AppLessonVideoDTO appLessonVideoDTO) {
        log.debug("Request to partially update AppLessonVideo : {}", appLessonVideoDTO);

        return appLessonVideoRepository
            .findById(appLessonVideoDTO.getId())
            .map(existingAppLessonVideo -> {
                appLessonVideoMapper.partialUpdate(existingAppLessonVideo, appLessonVideoDTO);

                return existingAppLessonVideo;
            })
            .map(appLessonVideoRepository::save)
            .map(appLessonVideoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppLessonVideoDTO> findAll() {
        log.debug("Request to get all AppLessonVideos");
        return appLessonVideoRepository
            .findAll()
            .stream()
            .map(appLessonVideoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AppLessonVideoDTO> findOne(Long id) {
        log.debug("Request to get AppLessonVideo : {}", id);
        return appLessonVideoRepository.findById(id).map(appLessonVideoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AppLessonVideo : {}", id);
        appLessonVideoRepository.deleteById(id);
    }
}
