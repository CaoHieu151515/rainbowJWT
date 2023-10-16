package com.auth0.rainbow.repository;

import com.auth0.rainbow.domain.AppLessonInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AppLessonInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppLessonInfoRepository extends JpaRepository<AppLessonInfo, Long> {}
