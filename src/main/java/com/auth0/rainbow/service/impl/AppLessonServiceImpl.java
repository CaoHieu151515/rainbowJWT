package com.auth0.rainbow.service.impl;

import com.auth0.rainbow.domain.AppCourse;
import com.auth0.rainbow.domain.AppLesson;
import com.auth0.rainbow.repository.AppCourseRepository;
import com.auth0.rainbow.repository.AppLessonRepository;
import com.auth0.rainbow.service.AppLessonService;
import com.auth0.rainbow.service.dto.AppLessonDTO;
import com.auth0.rainbow.service.mapper.AppLessonMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AppLesson}.
 */
@Service
@Transactional
public class AppLessonServiceImpl implements AppLessonService {

    private final Logger log = LoggerFactory.getLogger(AppLessonServiceImpl.class);

    private final AppLessonRepository appLessonRepository;

    private final AppLessonMapper appLessonMapper;

    private final AppCourseRepository appCourseRepository;

    public AppLessonServiceImpl(
        AppLessonRepository appLessonRepository,
        AppLessonMapper appLessonMapper,
        AppCourseRepository appCourseRepository
    ) {
        this.appLessonRepository = appLessonRepository;
        this.appLessonMapper = appLessonMapper;
        this.appCourseRepository = appCourseRepository;
    }

    @Override
    public AppLessonDTO save(AppLessonDTO appLessonDTO) {
        log.debug("Request to save AppLesson : {}", appLessonDTO);
        AppLesson appLesson = appLessonMapper.toEntity(appLessonDTO);
        Optional<AppCourse> appCourseOptional = appCourseRepository.findById(appLessonDTO.getId());

        if (appCourseOptional.isPresent()) {
            AppCourse appCourse = appCourseOptional.get();
            appLesson.setCourse(appCourse);
        }

        appLesson = appLessonRepository.save(appLesson);
        return appLessonMapper.toDto(appLesson);
    }

    @Override
    public AppLessonDTO update(AppLessonDTO appLessonDTO) {
        log.debug("Request to update AppLesson : {}", appLessonDTO);
        AppLesson appLesson = appLessonMapper.toEntity(appLessonDTO);
        Optional<AppCourse> appCourseOptional = appCourseRepository.findById(appLessonDTO.getId());

        if (appCourseOptional.isPresent()) {
            AppCourse appCourse = appCourseOptional.get();
            appLesson.setCourse(appCourse);
        }
        appLesson = appLessonRepository.save(appLesson);
        return appLessonMapper.toDto(appLesson);
    }

    @Override
    public Optional<AppLessonDTO> partialUpdate(AppLessonDTO appLessonDTO) {
        log.debug("Request to partially update AppLesson : {}", appLessonDTO);

        return appLessonRepository
            .findById(appLessonDTO.getId())
            .map(existingAppLesson -> {
                appLessonMapper.partialUpdate(existingAppLesson, appLessonDTO);

                return existingAppLesson;
            })
            .map(appLessonRepository::save)
            .map(appLessonMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppLessonDTO> findAll() {
        log.debug("Request to get all AppLessons");
        return appLessonRepository.findAll().stream().map(appLessonMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AppLessonDTO> findOne(Long id) {
        log.debug("Request to get AppLesson : {}", id);
        return appLessonRepository.findById(id).map(appLessonMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AppLesson : {}", id);
        appLessonRepository.deleteById(id);
    }
}
