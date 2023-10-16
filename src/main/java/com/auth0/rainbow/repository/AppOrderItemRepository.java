package com.auth0.rainbow.repository;

import com.auth0.rainbow.domain.AppOrderItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AppOrderItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppOrderItemRepository extends JpaRepository<AppOrderItem, Long> {}
