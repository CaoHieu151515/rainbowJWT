package com.auth0.rainbow.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

/**
 * A AppOrderItem.
 */
@Entity
@Table(name = "app_order_item")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppOrderItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "price", precision = 21, scale = 2)
    private BigDecimal price;

    @Column(name = "unit")
    private String unit;

    @Column(name = "note")
    private String note;

    @ManyToOne
    @JsonIgnoreProperties(value = { "orderItems", "payments", "user" }, allowSetters = true)
    private AppOrder order;

    @ManyToOne
    @JsonIgnoreProperties(value = { "appOrderItems", "category", "images", "carts" }, allowSetters = true)
    private AppProduct product;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AppOrderItem id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public AppOrderItem quantity(Integer quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public AppOrderItem price(BigDecimal price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getUnit() {
        return this.unit;
    }

    public AppOrderItem unit(String unit) {
        this.setUnit(unit);
        return this;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getNote() {
        return this.note;
    }

    public AppOrderItem note(String note) {
        this.setNote(note);
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public AppOrder getOrder() {
        return this.order;
    }

    public void setOrder(AppOrder appOrder) {
        this.order = appOrder;
    }

    public AppOrderItem order(AppOrder appOrder) {
        this.setOrder(appOrder);
        return this;
    }

    public AppProduct getProduct() {
        return this.product;
    }

    public void setProduct(AppProduct appProduct) {
        this.product = appProduct;
    }

    public AppOrderItem product(AppProduct appProduct) {
        this.setProduct(appProduct);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppOrderItem)) {
            return false;
        }
        return id != null && id.equals(((AppOrderItem) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppOrderItem{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", price=" + getPrice() +
            ", unit='" + getUnit() + "'" +
            ", note='" + getNote() + "'" +
            "}";
    }
}
