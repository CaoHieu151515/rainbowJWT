package com.auth0.rainbow.service.impl;

import com.auth0.rainbow.domain.AppAvailableCourse;
import com.auth0.rainbow.domain.AppCourse;
import com.auth0.rainbow.domain.AppUser;
import com.auth0.rainbow.repository.AppAvailableCourseRepository;
import com.auth0.rainbow.repository.AppCourseRepository;
import com.auth0.rainbow.repository.AppUserRepository;
import com.auth0.rainbow.service.AppUserService;
import com.auth0.rainbow.service.dto.AppAvailableCourseDTO;
import com.auth0.rainbow.service.dto.AppCourseDTO;
import com.auth0.rainbow.service.dto.AppUserDTO;
import com.auth0.rainbow.service.mapper.AppUserMapper;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AppUser}.
 */
@Service
@Transactional
public class AppUserServiceImpl implements AppUserService {

    private final Logger log = LoggerFactory.getLogger(AppUserServiceImpl.class);

    private final AppUserRepository appUserRepository;

    private final AppUserMapper appUserMapper;
    private final AppCourseRepository appCourseRepository;
    private final AppAvailableCourseRepository appAvailableCourseRepository;

    public AppUserServiceImpl(
        AppUserRepository appUserRepository,
        AppUserMapper appUserMapper,
        AppCourseRepository appCourseRepository,
        AppAvailableCourseRepository appAvailableCourseRepository
    ) {
        this.appUserRepository = appUserRepository;
        this.appUserMapper = appUserMapper;
        this.appCourseRepository = appCourseRepository;
        this.appAvailableCourseRepository = appAvailableCourseRepository;
    }

    @Override
    public AppUserDTO save(AppUserDTO appUserDTO) {
        log.debug("Request to save AppUser : {}", appUserDTO);
        AppUser appUser = appUserMapper.toEntity(appUserDTO);
        appUser = appUserRepository.save(appUser);
        return appUserMapper.toAppUserDTO(appUser);
    }

    @Override
    public AppUserDTO update(AppUserDTO appUserDTO) {
        log.debug("Request to update AppUser : {}", appUserDTO);
        AppUser appUser = appUserMapper.toEntity(appUserDTO);

        if (appUserDTO.getCourses() != null) {
            Set<AppCourse> courses = new HashSet<>();
            for (AppCourseDTO courseDTO : appUserDTO.getCourses()) {
                courses.add(appCourseRepository.findById(courseDTO.getId()).orElse(null));
            }
            appUser.setCourses(courses);
        }
        if (appUserDTO.getAvailableCourses() != null) {
            Set<AppAvailableCourse> availableCourses = new HashSet<>();
            for (AppAvailableCourseDTO availableCourseDTO : appUserDTO.getAvailableCourses()) {
                availableCourses.add(appAvailableCourseRepository.findById(availableCourseDTO.getId()).orElse(null));
            }
            appUser.setAvailableCourses(availableCourses);
        }
        appUser = appUserRepository.save(appUser);
        return appUserMapper.toDto(appUser);
    }

    @Override
    public Optional<AppUserDTO> partialUpdate(AppUserDTO appUserDTO) {
        log.debug("Request to partially update AppUser : {}", appUserDTO);

        return appUserRepository
            .findById(appUserDTO.getId())
            .map(existingAppUser -> {
                appUserMapper.partialUpdate(existingAppUser, appUserDTO);

                return existingAppUser;
            })
            .map(appUserRepository::save)
            .map(appUserMapper::toAppUserDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AppUserDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AppUsers");
        return appUserRepository.findAll(pageable).map(appUserMapper::toAppUsershortDTO);
    }

    public Page<AppUserDTO> findAllWithEagerRelationships(Pageable pageable) {
        return appUserRepository.findAllWithEagerRelationships(pageable).map(appUserMapper::toAppUsershortDTO);
    }

    /**
     *  Get all the appUsers where Cart is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AppUserDTO> findAllWhereCartIsNull() {
        log.debug("Request to get all appUsers where Cart is null");
        return StreamSupport
            .stream(appUserRepository.findAll().spliterator(), false)
            .filter(appUser -> appUser.getCart() == null)
            .map(appUserMapper::toAppUserDTO)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AppUserDTO> findOne(Long id) {
        log.debug("Request to get AppUser : {}", id);
        return appUserRepository.findOneWithEagerRelationships(id).map(appUserMapper::toAppUsershortDTO);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AppUser : {}", id);
        appUserRepository.deleteById(id);
    }
}
