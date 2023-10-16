package com.auth0.rainbow.repository;

import com.auth0.rainbow.domain.AppPayment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AppPayment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppPaymentRepository extends JpaRepository<AppPayment, Long> {}
