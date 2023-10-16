package com.auth0.rainbow.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link com.auth0.rainbow.domain.AppProduct} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppProductDTO implements Serializable {

    private Long id;

    private String name;

    private BigDecimal price;

    private Integer unit;

    private String description;

    private String status;

    private AppCategoryDTO category;

    private AppProductImageDTO images;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public AppCategoryDTO getCategory() {
        return category;
    }

    public void setCategory(AppCategoryDTO category) {
        this.category = category;
    }

    public AppProductImageDTO getImages() {
        return images;
    }

    public void setImages(AppProductImageDTO images) {
        this.images = images;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppProductDTO)) {
            return false;
        }

        AppProductDTO appProductDTO = (AppProductDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, appProductDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppProductDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", price=" + getPrice() +
            ", unit=" + getUnit() +
            ", description='" + getDescription() + "'" +
            ", status='" + getStatus() + "'" +
            ", category=" + getCategory() +
            ", images=" + getImages() +
            "}";
    }
}
