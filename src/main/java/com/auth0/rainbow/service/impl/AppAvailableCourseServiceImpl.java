package com.auth0.rainbow.service.impl;

import com.auth0.rainbow.domain.AppAvailableCourse;
import com.auth0.rainbow.domain.AppCourse;
import com.auth0.rainbow.repository.AppAvailableCourseRepository;
import com.auth0.rainbow.repository.AppCourseRepository;
import com.auth0.rainbow.service.AppAvailableCourseService;
import com.auth0.rainbow.service.dto.AppAvailableCourseDTO;
import com.auth0.rainbow.service.mapper.AppAvailableCourseMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AppAvailableCourse}.
 */
@Service
@Transactional
public class AppAvailableCourseServiceImpl implements AppAvailableCourseService {

    private final Logger log = LoggerFactory.getLogger(AppAvailableCourseServiceImpl.class);

    private final AppAvailableCourseRepository appAvailableCourseRepository;

    private final AppAvailableCourseMapper appAvailableCourseMapper;

    private final AppCourseRepository appCourseRepository;

    public AppAvailableCourseServiceImpl(
        AppAvailableCourseRepository appAvailableCourseRepository,
        AppAvailableCourseMapper appAvailableCourseMapper,
        AppCourseRepository appCourseRepository
    ) {
        this.appAvailableCourseRepository = appAvailableCourseRepository;
        this.appAvailableCourseMapper = appAvailableCourseMapper;
        this.appCourseRepository = appCourseRepository;
    }

    @Override
    public AppAvailableCourseDTO save(AppAvailableCourseDTO appAvailableCourseDTO) {
        log.debug("Request to save AppAvailableCourse : {}", appAvailableCourseDTO);
        AppAvailableCourse appAvailableCourse = appAvailableCourseMapper.toEntity(appAvailableCourseDTO);

        Optional<AppCourse> optionalCourse = appCourseRepository.findById(appAvailableCourse.getCourses().getId());
        AppCourse course = optionalCourse.orElseThrow(() -> new IllegalArgumentException("Course not found"));

        appAvailableCourse.setCourses(course);

        appAvailableCourse = appAvailableCourseRepository.save(appAvailableCourse);
        return appAvailableCourseMapper.toDto(appAvailableCourse);
    }

    @Override
    public AppAvailableCourseDTO update(AppAvailableCourseDTO appAvailableCourseDTO) {
        log.debug("Request to update AppAvailableCourse : {}", appAvailableCourseDTO);
        AppAvailableCourse appAvailableCourse = appAvailableCourseMapper.toEntity(appAvailableCourseDTO);
        appAvailableCourse = appAvailableCourseRepository.save(appAvailableCourse);
        return appAvailableCourseMapper.toAvaiCourseDTO(appAvailableCourse);
    }

    @Override
    public Optional<AppAvailableCourseDTO> partialUpdate(AppAvailableCourseDTO appAvailableCourseDTO) {
        log.debug("Request to partially update AppAvailableCourse : {}", appAvailableCourseDTO);

        return appAvailableCourseRepository
            .findById(appAvailableCourseDTO.getId())
            .map(existingAppAvailableCourse -> {
                appAvailableCourseMapper.partialUpdate(existingAppAvailableCourse, appAvailableCourseDTO);

                return existingAppAvailableCourse;
            })
            .map(appAvailableCourseRepository::save)
            .map(appAvailableCourseMapper::toAvaiCourseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppAvailableCourseDTO> findAll() {
        log.debug("Request to get all AppAvailableCourses");
        return appAvailableCourseRepository
            .findAll()
            .stream()
            .map(appAvailableCourseMapper::toAvaiCourseDTO)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AppAvailableCourseDTO> findOne(Long id) {
        log.debug("Request to get AppAvailableCourse : {}", id);
        return appAvailableCourseRepository.findById(id).map(appAvailableCourseMapper::toAvaiCourseDTO);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AppAvailableCourse : {}", id);
        appAvailableCourseRepository.deleteById(id);
    }
}
