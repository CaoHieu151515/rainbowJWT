package com.auth0.rainbow.repository;

import com.auth0.rainbow.domain.AppAvailableCourse;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AppAvailableCourse entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppAvailableCourseRepository extends JpaRepository<AppAvailableCourse, Long> {}
