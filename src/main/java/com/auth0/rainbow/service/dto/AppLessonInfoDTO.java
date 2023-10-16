package com.auth0.rainbow.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.auth0.rainbow.domain.AppLessonInfo} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppLessonInfoDTO implements Serializable {

    private Long id;

    private String description;

    private String pdfUrl;

    private AppLessonDTO lesson;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    public AppLessonDTO getLesson() {
        return lesson;
    }

    public void setLesson(AppLessonDTO lesson) {
        this.lesson = lesson;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppLessonInfoDTO)) {
            return false;
        }

        AppLessonInfoDTO appLessonInfoDTO = (AppLessonInfoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, appLessonInfoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppLessonInfoDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", pdfUrl='" + getPdfUrl() + "'" +
            ", lesson=" + getLesson() +
            "}";
    }
}
