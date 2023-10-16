package com.auth0.rainbow.repository;

import com.auth0.rainbow.domain.AppLessonVideo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AppLessonVideo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppLessonVideoRepository extends JpaRepository<AppLessonVideo, Long> {}
