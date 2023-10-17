package com.auth0.rainbow.service;

import com.auth0.rainbow.service.dto.AppCourseDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.auth0.rainbow.domain.AppCourse}.
 */
public interface AppCourseService {
    /**
     * Save a appCourse.
     *
     * @param appCourseDTO the entity to save.
     * @return the persisted entity.
     */
    AppCourseDTO save(AppCourseDTO appCourseDTO);

    /**
     * Updates a appCourse.
     *
     * @param appCourseDTO the entity to update.
     * @return the persisted entity.
     */
    AppCourseDTO update(AppCourseDTO appCourseDTO);

    /**
     * Partially updates a appCourse.
     *
     * @param appCourseDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AppCourseDTO> partialUpdate(AppCourseDTO appCourseDTO);

    /**
     * Get all the appCourses.
     *
     * @return the list of entities.
     */
    List<AppCourseDTO> findAll();

    /**
     * Get the "id" appCourse.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AppCourseDTO> findOne(Long id);

    Optional<AppCourseDTO> findOneDetails(Long id);

    /**
     * Delete the "id" appCourse.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
