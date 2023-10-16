package com.auth0.rainbow.repository;

import com.auth0.rainbow.domain.AppCategory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AppCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppCategoryRepository extends JpaRepository<AppCategory, Long> {}
