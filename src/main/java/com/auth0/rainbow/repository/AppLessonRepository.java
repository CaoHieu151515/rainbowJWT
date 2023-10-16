package com.auth0.rainbow.repository;

import com.auth0.rainbow.domain.AppLesson;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AppLesson entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppLessonRepository extends JpaRepository<AppLesson, Long> {}
