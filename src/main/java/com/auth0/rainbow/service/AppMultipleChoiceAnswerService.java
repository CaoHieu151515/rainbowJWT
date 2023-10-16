package com.auth0.rainbow.service;

import com.auth0.rainbow.service.dto.AppMultipleChoiceAnswerDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.auth0.rainbow.domain.AppMultipleChoiceAnswer}.
 */
public interface AppMultipleChoiceAnswerService {
    /**
     * Save a appMultipleChoiceAnswer.
     *
     * @param appMultipleChoiceAnswerDTO the entity to save.
     * @return the persisted entity.
     */
    AppMultipleChoiceAnswerDTO save(AppMultipleChoiceAnswerDTO appMultipleChoiceAnswerDTO);

    /**
     * Updates a appMultipleChoiceAnswer.
     *
     * @param appMultipleChoiceAnswerDTO the entity to update.
     * @return the persisted entity.
     */
    AppMultipleChoiceAnswerDTO update(AppMultipleChoiceAnswerDTO appMultipleChoiceAnswerDTO);

    /**
     * Partially updates a appMultipleChoiceAnswer.
     *
     * @param appMultipleChoiceAnswerDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AppMultipleChoiceAnswerDTO> partialUpdate(AppMultipleChoiceAnswerDTO appMultipleChoiceAnswerDTO);

    /**
     * Get all the appMultipleChoiceAnswers.
     *
     * @return the list of entities.
     */
    List<AppMultipleChoiceAnswerDTO> findAll();

    /**
     * Get the "id" appMultipleChoiceAnswer.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AppMultipleChoiceAnswerDTO> findOne(Long id);

    /**
     * Delete the "id" appMultipleChoiceAnswer.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
