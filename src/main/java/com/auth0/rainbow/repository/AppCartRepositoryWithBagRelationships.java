package com.auth0.rainbow.repository;

import com.auth0.rainbow.domain.AppCart;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface AppCartRepositoryWithBagRelationships {
    Optional<AppCart> fetchBagRelationships(Optional<AppCart> appCart);

    List<AppCart> fetchBagRelationships(List<AppCart> appCarts);

    Page<AppCart> fetchBagRelationships(Page<AppCart> appCarts);
}
