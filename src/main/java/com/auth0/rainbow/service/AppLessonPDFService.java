package com.auth0.rainbow.service;

import com.auth0.rainbow.service.dto.AppLessonPDFDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.auth0.rainbow.domain.AppLessonPDF}.
 */
public interface AppLessonPDFService {
    /**
     * Save a appLessonPDF.
     *
     * @param appLessonPDFDTO the entity to save.
     * @return the persisted entity.
     */
    AppLessonPDFDTO save(AppLessonPDFDTO appLessonPDFDTO);

    /**
     * Updates a appLessonPDF.
     *
     * @param appLessonPDFDTO the entity to update.
     * @return the persisted entity.
     */
    AppLessonPDFDTO update(AppLessonPDFDTO appLessonPDFDTO);

    /**
     * Partially updates a appLessonPDF.
     *
     * @param appLessonPDFDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AppLessonPDFDTO> partialUpdate(AppLessonPDFDTO appLessonPDFDTO);

    /**
     * Get all the appLessonPDFS.
     *
     * @return the list of entities.
     */
    List<AppLessonPDFDTO> findAll();

    /**
     * Get the "id" appLessonPDF.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AppLessonPDFDTO> findOne(Long id);

    /**
     * Delete the "id" appLessonPDF.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
