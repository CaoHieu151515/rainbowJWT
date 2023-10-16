package com.auth0.rainbow.repository;

import com.auth0.rainbow.domain.AppProductImage;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AppProductImage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppProductImageRepository extends JpaRepository<AppProductImage, Long> {}
