package com.auth0.rainbow.repository;

import com.auth0.rainbow.domain.AppCart;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class AppCartRepositoryWithBagRelationshipsImpl implements AppCartRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<AppCart> fetchBagRelationships(Optional<AppCart> appCart) {
        return appCart.map(this::fetchProducts);
    }

    @Override
    public Page<AppCart> fetchBagRelationships(Page<AppCart> appCarts) {
        return new PageImpl<>(fetchBagRelationships(appCarts.getContent()), appCarts.getPageable(), appCarts.getTotalElements());
    }

    @Override
    public List<AppCart> fetchBagRelationships(List<AppCart> appCarts) {
        return Optional.of(appCarts).map(this::fetchProducts).orElse(Collections.emptyList());
    }

    AppCart fetchProducts(AppCart result) {
        return entityManager
            .createQuery("select appCart from AppCart appCart left join fetch appCart.products where appCart is :appCart", AppCart.class)
            .setParameter("appCart", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<AppCart> fetchProducts(List<AppCart> appCarts) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, appCarts.size()).forEach(index -> order.put(appCarts.get(index).getId(), index));
        List<AppCart> result = entityManager
            .createQuery(
                "select distinct appCart from AppCart appCart left join fetch appCart.products where appCart in :appCarts",
                AppCart.class
            )
            .setParameter("appCarts", appCarts)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
