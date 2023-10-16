package com.auth0.rainbow.repository;

import com.auth0.rainbow.domain.AppPostImage;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AppPostImage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppPostImageRepository extends JpaRepository<AppPostImage, Long> {}
