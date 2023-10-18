package com.auth0.rainbow.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.auth0.rainbow.domain.AppLessonPDF} entity.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppLessonPDFDTO implements Serializable {

    private Long id;

    private String description;

    private String pdfUrl;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private AppLessonInfoDTO lesson;

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

    public AppLessonInfoDTO getLesson() {
        return lesson;
    }

    public void setLesson(AppLessonInfoDTO lesson) {
        this.lesson = lesson;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppLessonPDFDTO)) {
            return false;
        }

        AppLessonPDFDTO appLessonPDFDTO = (AppLessonPDFDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, appLessonPDFDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppLessonPDFDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", pdfUrl='" + getPdfUrl() + "'" +
            ", lesson=" + getLesson() +
            "}";
    }
}
