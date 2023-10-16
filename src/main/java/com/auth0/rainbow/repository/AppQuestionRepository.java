package com.auth0.rainbow.repository;

import com.auth0.rainbow.domain.AppQuestion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AppQuestion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppQuestionRepository extends JpaRepository<AppQuestion, Long> {}
