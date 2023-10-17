package com.auth0.rainbow.repository;

import com.auth0.rainbow.domain.LinkAccountUser;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LinkAccountUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LinkAccountUserRepository extends JpaRepository<LinkAccountUser, Long> {
    @Query("select linkAccountUser from LinkAccountUser linkAccountUser where linkAccountUser.user.login = ?#{principal.username}")
    List<LinkAccountUser> findByUserIsCurrentUser();

    LinkAccountUser findByUserId(Long userId);

    Optional<LinkAccountUser> findOneByUserId(Long userId);
}
