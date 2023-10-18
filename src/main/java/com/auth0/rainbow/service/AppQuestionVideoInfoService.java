package com.auth0.rainbow.service;

import com.auth0.rainbow.service.dto.AppQuestionVideoInfoDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.auth0.rainbow.domain.AppQuestionVideoInfo}.
 */
public interface AppQuestionVideoInfoService {
    /**
     * Save a appQuestionVideoInfo.
     *
     * @param appQuestionVideoInfoDTO the entity to save.
     * @return the persisted entity.
     */
    AppQuestionVideoInfoDTO save(AppQuestionVideoInfoDTO appQuestionVideoInfoDTO);

    /**
     * Updates a appQuestionVideoInfo.
     *
     * @param appQuestionVideoInfoDTO the entity to update.
     * @return the persisted entity.
     */
    AppQuestionVideoInfoDTO update(AppQuestionVideoInfoDTO appQuestionVideoInfoDTO);

    /**
     * Partially updates a appQuestionVideoInfo.
     *
     * @param appQuestionVideoInfoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AppQuestionVideoInfoDTO> partialUpdate(AppQuestionVideoInfoDTO appQuestionVideoInfoDTO);

    /**
     * Get all the appQuestionVideoInfos.
     *
     * @return the list of entities.
     */
    List<AppQuestionVideoInfoDTO> findAll();

    /**
     * Get the "id" appQuestionVideoInfo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AppQuestionVideoInfoDTO> findOne(Long id);

    /**
     * Delete the "id" appQuestionVideoInfo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
