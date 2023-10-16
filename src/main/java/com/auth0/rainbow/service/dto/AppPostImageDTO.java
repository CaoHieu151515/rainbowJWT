package com.auth0.rainbow.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.auth0.rainbow.domain.AppPostImage} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppPostImageDTO implements Serializable {

    private Long id;

    private String imageUrl;

    private AppPostDTO post;

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

    public AppPostDTO getPost() {
        return post;
    }

    public void setPost(AppPostDTO post) {
        this.post = post;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppPostImageDTO)) {
            return false;
        }

        AppPostImageDTO appPostImageDTO = (AppPostImageDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, appPostImageDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppPostImageDTO{" +
            "id=" + getId() +
            ", imageUrl='" + getImageUrl() + "'" +
            ", post=" + getPost() +
            "}";
    }
}
