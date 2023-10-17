package com.auth0.rainbow.repository;

import com.auth0.rainbow.domain.AppPost;
import com.auth0.rainbow.domain.AppPostImage;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AppPost entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppPostRepository extends JpaRepository<AppPost, Long> {
    @Query("SELECT DISTINCT p FROM AppPost p LEFT JOIN FETCH p.images")
    Set<AppPost> findAllWithImages();

    List<AppPost> findByIsFeaturedTrue();
}
