package com.auth0.rainbow.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.auth0.rainbow.domain.AppCart} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppCartDTO implements Serializable {

    private Long id;

    private Integer quantity;

    private AppUserDTO user;

    private Set<AppProductDTO> products = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public AppUserDTO getUser() {
        return user;
    }

    public void setUser(AppUserDTO user) {
        this.user = user;
    }

    public Set<AppProductDTO> getProducts() {
        return products;
    }

    public void setProducts(Set<AppProductDTO> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppCartDTO)) {
            return false;
        }

        AppCartDTO appCartDTO = (AppCartDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, appCartDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppCartDTO{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", user=" + getUser() +
            ", products=" + getProducts() +
            "}";
    }
}
