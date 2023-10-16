package com.auth0.rainbow.repository;

import com.auth0.rainbow.domain.AppProduct;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AppProduct entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppProductRepository extends JpaRepository<AppProduct, Long> {}
