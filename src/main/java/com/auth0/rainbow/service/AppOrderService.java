package com.auth0.rainbow.service;

import com.auth0.rainbow.service.dto.AppOrderDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.auth0.rainbow.domain.AppOrder}.
 */
public interface AppOrderService {
    /**
     * Save a appOrder.
     *
     * @param appOrderDTO the entity to save.
     * @return the persisted entity.
     */
    AppOrderDTO save(AppOrderDTO appOrderDTO);

    /**
     * Updates a appOrder.
     *
     * @param appOrderDTO the entity to update.
     * @return the persisted entity.
     */
    AppOrderDTO update(AppOrderDTO appOrderDTO);

    /**
     * Partially updates a appOrder.
     *
     * @param appOrderDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AppOrderDTO> partialUpdate(AppOrderDTO appOrderDTO);

    /**
     * Get all the appOrders.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AppOrderDTO> findAll(Pageable pageable);

    /**
     * Get the "id" appOrder.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AppOrderDTO> findOne(Long id);

    /**
     * Delete the "id" appOrder.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
