package com.auth0.rainbow.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A AppProduct.
 */
@Entity
@Table(name = "app_product")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppProduct implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price", precision = 21, scale = 2)
    private BigDecimal price;

    @Column(name = "unit")
    private Integer unit;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private String status;

    @Column(name = "course_id")
    private Long courseId;

    @OneToMany(mappedBy = "product")
    @JsonIgnoreProperties(value = { "order", "product" }, allowSetters = true)
    private Set<AppOrderItem> appOrderItems = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private AppCategory category;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private AppProductImage images;

    @ManyToMany(mappedBy = "products")
    @JsonIgnoreProperties(value = { "user", "products" }, allowSetters = true)
    private Set<AppCart> carts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AppProduct id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public AppProduct name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public AppProduct price(BigDecimal price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getUnit() {
        return this.unit;
    }

    public AppProduct unit(Integer unit) {
        this.setUnit(unit);
        return this;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    public String getDescription() {
        return this.description;
    }

    public AppProduct description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return this.status;
    }

    public AppProduct status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCourseId() {
        return this.courseId;
    }

    public AppProduct courseId(Long courseId) {
        this.setCourseId(courseId);
        return this;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Set<AppOrderItem> getAppOrderItems() {
        return this.appOrderItems;
    }

    public void setAppOrderItems(Set<AppOrderItem> appOrderItems) {
        if (this.appOrderItems != null) {
            this.appOrderItems.forEach(i -> i.setProduct(null));
        }
        if (appOrderItems != null) {
            appOrderItems.forEach(i -> i.setProduct(this));
        }
        this.appOrderItems = appOrderItems;
    }

    public AppProduct appOrderItems(Set<AppOrderItem> appOrderItems) {
        this.setAppOrderItems(appOrderItems);
        return this;
    }

    public AppProduct addAppOrderItem(AppOrderItem appOrderItem) {
        this.appOrderItems.add(appOrderItem);
        appOrderItem.setProduct(this);
        return this;
    }

    public AppProduct removeAppOrderItem(AppOrderItem appOrderItem) {
        this.appOrderItems.remove(appOrderItem);
        appOrderItem.setProduct(null);
        return this;
    }

    public AppCategory getCategory() {
        return this.category;
    }

    public void setCategory(AppCategory appCategory) {
        this.category = appCategory;
    }

    public AppProduct category(AppCategory appCategory) {
        this.setCategory(appCategory);
        return this;
    }

    public AppProductImage getImages() {
        return this.images;
    }

    public void setImages(AppProductImage appProductImage) {
        this.images = appProductImage;
    }

    public AppProduct images(AppProductImage appProductImage) {
        this.setImages(appProductImage);
        return this;
    }

    public Set<AppCart> getCarts() {
        return this.carts;
    }

    public void setCarts(Set<AppCart> appCarts) {
        if (this.carts != null) {
            this.carts.forEach(i -> i.removeProducts(this));
        }
        if (appCarts != null) {
            appCarts.forEach(i -> i.addProducts(this));
        }
        this.carts = appCarts;
    }

    public AppProduct carts(Set<AppCart> appCarts) {
        this.setCarts(appCarts);
        return this;
    }

    public AppProduct addCarts(AppCart appCart) {
        this.carts.add(appCart);
        appCart.getProducts().add(this);
        return this;
    }

    public AppProduct removeCarts(AppCart appCart) {
        this.carts.remove(appCart);
        appCart.getProducts().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppProduct)) {
            return false;
        }
        return id != null && id.equals(((AppProduct) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppProduct{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", price=" + getPrice() +
            ", unit=" + getUnit() +
            ", description='" + getDescription() + "'" +
            ", status='" + getStatus() + "'" +
            ", courseId=" + getCourseId() +
            "}";
    }
}
