package com.auth0.rainbow.service;

import com.auth0.rainbow.service.dto.AppOrderItemDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.auth0.rainbow.domain.AppOrderItem}.
 */
public interface AppOrderItemService {
    /**
     * Save a appOrderItem.
     *
     * @param appOrderItemDTO the entity to save.
     * @return the persisted entity.
     */
    AppOrderItemDTO save(AppOrderItemDTO appOrderItemDTO);

    /**
     * Updates a appOrderItem.
     *
     * @param appOrderItemDTO the entity to update.
     * @return the persisted entity.
     */
    AppOrderItemDTO update(AppOrderItemDTO appOrderItemDTO);

    /**
     * Partially updates a appOrderItem.
     *
     * @param appOrderItemDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AppOrderItemDTO> partialUpdate(AppOrderItemDTO appOrderItemDTO);

    /**
     * Get all the appOrderItems.
     *
     * @return the list of entities.
     */
    List<AppOrderItemDTO> findAll();

    /**
     * Get the "id" appOrderItem.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AppOrderItemDTO> findOne(Long id);

    /**
     * Delete the "id" appOrderItem.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
