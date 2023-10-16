package com.auth0.rainbow.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A AppCart.
 */
@Entity
@Table(name = "app_cart")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppCart implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "quantity")
    private Integer quantity;

    @JsonIgnoreProperties(value = { "orders", "posts", "courses", "availableCourses", "cart" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private AppUser user;

    @ManyToMany
    @JoinTable(
        name = "rel_app_cart__products",
        joinColumns = @JoinColumn(name = "app_cart_id"),
        inverseJoinColumns = @JoinColumn(name = "products_id")
    )
    @JsonIgnoreProperties(value = { "category", "images", "carts" }, allowSetters = true)
    private Set<AppProduct> products = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AppCart id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public AppCart quantity(Integer quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public AppUser getUser() {
        return this.user;
    }

    public void setUser(AppUser appUser) {
        this.user = appUser;
    }

    public AppCart user(AppUser appUser) {
        this.setUser(appUser);
        return this;
    }

    public Set<AppProduct> getProducts() {
        return this.products;
    }

    public void setProducts(Set<AppProduct> appProducts) {
        this.products = appProducts;
    }

    public AppCart products(Set<AppProduct> appProducts) {
        this.setProducts(appProducts);
        return this;
    }

    public AppCart addProducts(AppProduct appProduct) {
        this.products.add(appProduct);
        appProduct.getCarts().add(this);
        return this;
    }

    public AppCart removeProducts(AppProduct appProduct) {
        this.products.remove(appProduct);
        appProduct.getCarts().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppCart)) {
            return false;
        }
        return id != null && id.equals(((AppCart) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppCart{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            "}";
    }
}
