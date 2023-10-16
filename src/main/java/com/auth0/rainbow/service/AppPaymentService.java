package com.auth0.rainbow.service;

import com.auth0.rainbow.service.dto.AppPaymentDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.auth0.rainbow.domain.AppPayment}.
 */
public interface AppPaymentService {
    /**
     * Save a appPayment.
     *
     * @param appPaymentDTO the entity to save.
     * @return the persisted entity.
     */
    AppPaymentDTO save(AppPaymentDTO appPaymentDTO);

    /**
     * Updates a appPayment.
     *
     * @param appPaymentDTO the entity to update.
     * @return the persisted entity.
     */
    AppPaymentDTO update(AppPaymentDTO appPaymentDTO);

    /**
     * Partially updates a appPayment.
     *
     * @param appPaymentDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AppPaymentDTO> partialUpdate(AppPaymentDTO appPaymentDTO);

    /**
     * Get all the appPayments.
     *
     * @return the list of entities.
     */
    List<AppPaymentDTO> findAll();

    /**
     * Get the "id" appPayment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AppPaymentDTO> findOne(Long id);

    /**
     * Delete the "id" appPayment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
