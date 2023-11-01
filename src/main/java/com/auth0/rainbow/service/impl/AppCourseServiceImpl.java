package com.auth0.rainbow.service.impl;

import com.auth0.rainbow.domain.AppCourse;
import com.auth0.rainbow.repository.AppCourseRepository;
import com.auth0.rainbow.service.AppCourseService;
import com.auth0.rainbow.service.dto.AppCourseDTO;
import com.auth0.rainbow.service.mapper.AppCourseMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AppCourse}.
 */
@Service
@Transactional
public class AppCourseServiceImpl implements AppCourseService {

    private final Logger log = LoggerFactory.getLogger(AppCourseServiceImpl.class);

    private final AppCourseRepository appCourseRepository;

    private final AppCourseMapper appCourseMapper;

    public AppCourseServiceImpl(AppCourseRepository appCourseRepository, AppCourseMapper appCourseMapper) {
        this.appCourseRepository = appCourseRepository;
        this.appCourseMapper = appCourseMapper;
    }

    @Override
    public AppCourseDTO save(AppCourseDTO appCourseDTO) {
        log.debug("Request to save AppCourse : {}", appCourseDTO);
        AppCourse appCourse = appCourseMapper.toEntity(appCourseDTO);
        appCourse = appCourseRepository.save(appCourse);
        return appCourseMapper.toDto(appCourse);
    }

    @Override
    public AppCourseDTO update(AppCourseDTO appCourseDTO) {
        log.debug("Request to update AppCourse : {}", appCourseDTO);
        AppCourse appCourse = appCourseMapper.toEntity(appCourseDTO);
        appCourse = appCourseRepository.save(appCourse);
        return appCourseMapper.toDto(appCourse);
    }

    @Override
    public Optional<AppCourseDTO> partialUpdate(AppCourseDTO appCourseDTO) {
        log.debug("Request to partially update AppCourse : {}", appCourseDTO);

        return appCourseRepository
            .findById(appCourseDTO.getId())
            .map(existingAppCourse -> {
                appCourseMapper.partialUpdate(existingAppCourse, appCourseDTO);

                return existingAppCourse;
            })
            .map(appCourseRepository::save)
            .map(appCourseMapper::toCourseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppCourseDTO> findAll() {
        log.debug("Request to get all AppCourses");
        return appCourseRepository.findAll().stream().map(appCourseMapper::toCourseDTO2).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AppCourseDTO> findOne(Long id) {
        log.debug("Request to get AppCourse : {}", id);
        return appCourseRepository.findById(id).map(appCourseMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AppCourseDTO> findOneDetails(Long id) {
        log.debug("Request to get AppCourse : {}", id);
        return appCourseRepository.findById(id).map(appCourseMapper::toCourseDTO);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AppCourse : {}", id);
        appCourseRepository.deleteById(id);
    }
}
