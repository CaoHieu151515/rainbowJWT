package com.auth0.rainbow.repository;

import com.auth0.rainbow.domain.AppOrder;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AppOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppOrderRepository extends JpaRepository<AppOrder, Long> {}
