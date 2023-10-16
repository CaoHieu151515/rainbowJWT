package com.auth0.rainbow.repository;

import com.auth0.rainbow.domain.AppMultipleChoiceAnswer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AppMultipleChoiceAnswer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppMultipleChoiceAnswerRepository extends JpaRepository<AppMultipleChoiceAnswer, Long> {}
