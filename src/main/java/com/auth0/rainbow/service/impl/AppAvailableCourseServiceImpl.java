package com.auth0.rainbow.service.impl;

import com.auth0.rainbow.domain.AppAvailableCourse;
import com.auth0.rainbow.domain.AppCourse;
import com.auth0.rainbow.domain.AppUser;
import com.auth0.rainbow.domain.LinkAccountUser;
import com.auth0.rainbow.domain.User;
import com.auth0.rainbow.repository.AppAvailableCourseRepository;
import com.auth0.rainbow.repository.AppCourseRepository;
import com.auth0.rainbow.repository.AppUserRepository;
import com.auth0.rainbow.repository.LinkAccountUserRepository;
import com.auth0.rainbow.service.AppAvailableCourseService;
import com.auth0.rainbow.service.UserService;
import com.auth0.rainbow.service.dto.AppAvailableCourseDTO;
import com.auth0.rainbow.service.mapper.AppAvailableCourseMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
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

    private final AppUserRepository appUserRepository;

    private final UserService userService;

    private final LinkAccountUserRepository linkAccountUserRepository;

    public AppAvailableCourseServiceImpl(
        AppAvailableCourseRepository appAvailableCourseRepository,
        AppAvailableCourseMapper appAvailableCourseMapper,
        AppCourseRepository appCourseRepository,
        AppUserRepository appUserRepository,
        UserService userService,
        LinkAccountUserRepository linkAccountUserRepository
    ) {
        this.appAvailableCourseRepository = appAvailableCourseRepository;
        this.appAvailableCourseMapper = appAvailableCourseMapper;
        this.appCourseRepository = appCourseRepository;
        this.appUserRepository = appUserRepository;
        this.userService = userService;
        this.linkAccountUserRepository = linkAccountUserRepository;
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
            .map(appAvailableCourseMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppAvailableCourseDTO> findAll() {
        log.debug("Request to get all AppAvailableCourses");
        return appAvailableCourseRepository
            .findAll()
            .stream()
            .map(appAvailableCourseMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AppAvailableCourseDTO> findOne(Long id) {
        log.debug("Request to get AppAvailableCourse : {}", id);
        return appAvailableCourseRepository.findById(id).map(appAvailableCourseMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AppAvailableCourse : {}", id);
        appAvailableCourseRepository.deleteById(id);
    }

    public ResponseEntity<String> receiveCourse(Long id) {
        try {
            AppCourse course = appCourseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Course not found"));

            AppUser appUser = GetCurrentAppUser();
            Set<AppAvailableCourse> currentAvailableCourses = appUser.getAvailableCourses();

            // Kiểm tra xem người dùng đã có availableCourse và availableCourse đó chứa khóa học hay chưa
            boolean isCourseReceived = currentAvailableCourses.stream().anyMatch(ac -> ac.getCourses().getId().equals(id));

            if (isCourseReceived) {
                return ResponseEntity.ok("User already has this course in their available courses.");
            } else {
                AppAvailableCourse availableCourse = new AppAvailableCourse();
                availableCourse.setCourses(course);

                currentAvailableCourses.add(availableCourse);
                appUser.setAvailableCourses(currentAvailableCourses);

                appAvailableCourseRepository.save(availableCourse);
                appUserRepository.save(appUser);

                return ResponseEntity.ok("Course received successfully.");
            }
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body("An error occurred while receiving the course.");
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
