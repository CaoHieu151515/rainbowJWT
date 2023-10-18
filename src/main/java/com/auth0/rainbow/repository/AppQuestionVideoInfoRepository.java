package com.auth0.rainbow.repository;

import com.auth0.rainbow.domain.AppQuestionVideoInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AppQuestionVideoInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppQuestionVideoInfoRepository extends JpaRepository<AppQuestionVideoInfo, Long> {}
