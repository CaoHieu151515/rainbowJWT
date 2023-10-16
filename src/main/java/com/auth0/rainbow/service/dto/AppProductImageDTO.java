package com.auth0.rainbow.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.auth0.rainbow.domain.AppProductImage} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppProductImageDTO implements Serializable {

    private Long id;

    private String imageUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppProductImageDTO)) {
            return false;
        }

        AppProductImageDTO appProductImageDTO = (AppProductImageDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, appProductImageDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppProductImageDTO{" +
            "id=" + getId() +
            ", imageUrl='" + getImageUrl() + "'" +
            "}";
    }
}
