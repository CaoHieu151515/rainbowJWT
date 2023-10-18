package com.auth0.rainbow.repository;

import com.auth0.rainbow.domain.AppLessonPDF;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AppLessonPDF entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppLessonPDFRepository extends JpaRepository<AppLessonPDF, Long> {}
