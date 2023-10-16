package com.auth0.rainbow.service;

import com.auth0.rainbow.service.dto.AppLessonDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.auth0.rainbow.domain.AppLesson}.
 */
public interface AppLessonService {
    /**
     * Save a appLesson.
     *
     * @param appLessonDTO the entity to save.
     * @return the persisted entity.
     */
    AppLessonDTO save(AppLessonDTO appLessonDTO);

    /**
     * Updates a appLesson.
     *
     * @param appLessonDTO the entity to update.
     * @return the persisted entity.
     */
    AppLessonDTO update(AppLessonDTO appLessonDTO);

    /**
     * Partially updates a appLesson.
     *
     * @param appLessonDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AppLessonDTO> partialUpdate(AppLessonDTO appLessonDTO);

    /**
     * Get all the appLessons.
     *
     * @return the list of entities.
     */
    List<AppLessonDTO> findAll();

    /**
     * Get the "id" appLesson.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AppLessonDTO> findOne(Long id);

    /**
     * Delete the "id" appLesson.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
