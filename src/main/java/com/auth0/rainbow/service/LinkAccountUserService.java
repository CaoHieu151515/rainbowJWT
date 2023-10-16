package com.auth0.rainbow.service;

import com.auth0.rainbow.service.dto.LinkAccountUserDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.auth0.rainbow.domain.LinkAccountUser}.
 */
public interface LinkAccountUserService {
    /**
     * Save a linkAccountUser.
     *
     * @param linkAccountUserDTO the entity to save.
     * @return the persisted entity.
     */
    LinkAccountUserDTO save(LinkAccountUserDTO linkAccountUserDTO);

    /**
     * Updates a linkAccountUser.
     *
     * @param linkAccountUserDTO the entity to update.
     * @return the persisted entity.
     */
    LinkAccountUserDTO update(LinkAccountUserDTO linkAccountUserDTO);

    /**
     * Partially updates a linkAccountUser.
     *
     * @param linkAccountUserDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LinkAccountUserDTO> partialUpdate(LinkAccountUserDTO linkAccountUserDTO);

    /**
     * Get all the linkAccountUsers.
     *
     * @return the list of entities.
     */
    List<LinkAccountUserDTO> findAll();

    /**
     * Get the "id" linkAccountUser.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LinkAccountUserDTO> findOne(Long id);

    /**
     * Delete the "id" linkAccountUser.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
