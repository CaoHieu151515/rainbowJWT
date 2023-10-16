package com.auth0.rainbow.service;

import com.auth0.rainbow.service.dto.AppProductDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.auth0.rainbow.domain.AppProduct}.
 */
public interface AppProductService {
    /**
     * Save a appProduct.
     *
     * @param appProductDTO the entity to save.
     * @return the persisted entity.
     */
    AppProductDTO save(AppProductDTO appProductDTO);

    /**
     * Updates a appProduct.
     *
     * @param appProductDTO the entity to update.
     * @return the persisted entity.
     */
    AppProductDTO update(AppProductDTO appProductDTO);

    /**
     * Partially updates a appProduct.
     *
     * @param appProductDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AppProductDTO> partialUpdate(AppProductDTO appProductDTO);

    /**
     * Get all the appProducts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AppProductDTO> findAll(Pageable pageable);

    /**
     * Get the "id" appProduct.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AppProductDTO> findOne(Long id);

    /**
     * Delete the "id" appProduct.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
