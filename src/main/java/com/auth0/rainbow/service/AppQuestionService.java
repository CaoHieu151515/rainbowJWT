package com.auth0.rainbow.service;

import com.auth0.rainbow.service.dto.AppQuestionDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.auth0.rainbow.domain.AppQuestion}.
 */
public interface AppQuestionService {
    /**
     * Save a appQuestion.
     *
     * @param appQuestionDTO the entity to save.
     * @return the persisted entity.
     */
    AppQuestionDTO save(AppQuestionDTO appQuestionDTO);

    /**
     * Updates a appQuestion.
     *
     * @param appQuestionDTO the entity to update.
     * @return the persisted entity.
     */
    AppQuestionDTO update(AppQuestionDTO appQuestionDTO);

    /**
     * Partially updates a appQuestion.
     *
     * @param appQuestionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AppQuestionDTO> partialUpdate(AppQuestionDTO appQuestionDTO);

    /**
     * Get all the appQuestions.
     *
     * @return the list of entities.
     */
    List<AppQuestionDTO> findAll();

    /**
     * Get the "id" appQuestion.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AppQuestionDTO> findOne(Long id);

    /**
     * Delete the "id" appQuestion.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
