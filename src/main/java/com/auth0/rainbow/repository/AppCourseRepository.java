package com.auth0.rainbow.repository;

import com.auth0.rainbow.domain.AppCourse;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AppCourse entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppCourseRepository extends JpaRepository<AppCourse, Long> {
    Optional<AppCourse> findById(Long id);
}
