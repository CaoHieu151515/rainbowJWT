package com.auth0.rainbow.service;

import com.auth0.rainbow.service.dto.AppLessonVideoDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.auth0.rainbow.domain.AppLessonVideo}.
 */
public interface AppLessonVideoService {
    /**
     * Save a appLessonVideo.
     *
     * @param appLessonVideoDTO the entity to save.
     * @return the persisted entity.
     */
    AppLessonVideoDTO save(AppLessonVideoDTO appLessonVideoDTO);

    /**
     * Updates a appLessonVideo.
     *
     * @param appLessonVideoDTO the entity to update.
     * @return the persisted entity.
     */
    AppLessonVideoDTO update(AppLessonVideoDTO appLessonVideoDTO);

    /**
     * Partially updates a appLessonVideo.
     *
     * @param appLessonVideoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AppLessonVideoDTO> partialUpdate(AppLessonVideoDTO appLessonVideoDTO);

    /**
     * Get all the appLessonVideos.
     *
     * @return the list of entities.
     */
    List<AppLessonVideoDTO> findAll();

    /**
     * Get the "id" appLessonVideo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AppLessonVideoDTO> findOne(Long id);

    /**
     * Delete the "id" appLessonVideo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
