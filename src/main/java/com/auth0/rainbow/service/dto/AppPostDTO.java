package com.auth0.rainbow.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.auth0.rainbow.domain.AppPost} entity.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppPostDTO implements Serializable {

    private Long id;

    private String title;

    private String content;

    private String author;

    private ZonedDateTime dateWritten;

    private ZonedDateTime publishedDate;

    private Boolean isFeatured;

    private Boolean confirm;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private AppUserDTO user;

    private Set<AppPostImageDTO> images;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public ZonedDateTime getDateWritten() {
        return dateWritten;
    }

    public void setDateWritten(ZonedDateTime dateWritten) {
        this.dateWritten = dateWritten;
    }

    public ZonedDateTime getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(ZonedDateTime publishedDate) {
        this.publishedDate = publishedDate;
    }

    public Boolean getIsFeatured() {
        return isFeatured;
    }

    public void setIsFeatured(Boolean isFeatured) {
        this.isFeatured = isFeatured;
    }

    public Boolean getConfirm() {
        return confirm;
    }

    public void setConfirm(Boolean confirm) {
        this.confirm = confirm;
    }

    public AppUserDTO getUser() {
        return user;
    }

    public void setUser(AppUserDTO user) {
        this.user = user;
    }

    public Set<AppPostImageDTO> getImages() {
        return images;
    }

    public void setImages(Set<AppPostImageDTO> images) {
        this.images = images;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppPostDTO)) {
            return false;
        }

        AppPostDTO appPostDTO = (AppPostDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, appPostDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppPostDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", content='" + getContent() + "'" +
            ", author='" + getAuthor() + "'" +
            ", dateWritten='" + getDateWritten() + "'" +
            ", publishedDate='" + getPublishedDate() + "'" +
            ", isFeatured='" + getIsFeatured() + "'" +
            ", confirm='" + getConfirm() + "'" +
            ", user=" + getUser() +
            ", imageUrls=" + getImages() + // Thêm thông tin về imageUrls
            "}";
    }
}
