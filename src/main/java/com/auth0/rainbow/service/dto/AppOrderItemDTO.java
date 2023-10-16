package com.auth0.rainbow.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link com.auth0.rainbow.domain.AppOrderItem} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppOrderItemDTO implements Serializable {

    private Long id;

    private Integer quantity;

    private BigDecimal price;

    private String unit;

    private String note;

    private AppProductDTO product;

    private AppOrderDTO order;

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public AppProductDTO getProduct() {
        return product;
    }

    public void setProduct(AppProductDTO product) {
        this.product = product;
    }

    public AppOrderDTO getOrder() {
        return order;
    }

    public void setOrder(AppOrderDTO order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppOrderItemDTO)) {
            return false;
        }

        AppOrderItemDTO appOrderItemDTO = (AppOrderItemDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, appOrderItemDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppOrderItemDTO{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", price=" + getPrice() +
            ", unit='" + getUnit() + "'" +
            ", note='" + getNote() + "'" +
            ", product=" + getProduct() +
            ", order=" + getOrder() +
            "}";
    }
}
