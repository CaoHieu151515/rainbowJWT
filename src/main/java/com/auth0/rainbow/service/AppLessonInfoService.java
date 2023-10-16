package com.auth0.rainbow.service;

import com.auth0.rainbow.service.dto.AppLessonInfoDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.auth0.rainbow.domain.AppLessonInfo}.
 */
public interface AppLessonInfoService {
    /**
     * Save a appLessonInfo.
     *
     * @param appLessonInfoDTO the entity to save.
     * @return the persisted entity.
     */
    AppLessonInfoDTO save(AppLessonInfoDTO appLessonInfoDTO);

    /**
     * Updates a appLessonInfo.
     *
     * @param appLessonInfoDTO the entity to update.
     * @return the persisted entity.
     */
    AppLessonInfoDTO update(AppLessonInfoDTO appLessonInfoDTO);

    /**
     * Partially updates a appLessonInfo.
     *
     * @param appLessonInfoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AppLessonInfoDTO> partialUpdate(AppLessonInfoDTO appLessonInfoDTO);

    /**
     * Get all the appLessonInfos.
     *
     * @return the list of entities.
     */
    List<AppLessonInfoDTO> findAll();

    /**
     * Get the "id" appLessonInfo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AppLessonInfoDTO> findOne(Long id);

    /**
     * Delete the "id" appLessonInfo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
