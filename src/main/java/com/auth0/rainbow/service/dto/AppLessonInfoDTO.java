package com.auth0.rainbow.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.auth0.rainbow.domain.AppLessonInfo} entity.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppLessonInfoDTO implements Serializable {

    private Long id;

    private String Name;

    private String description;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Set<AppLessonPDFDTO> pdfss;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private AppLessonDTO lessonDTO;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Set<AppLessonVideoDTO> lessonvideo;

    public Set<AppLessonPDFDTO> getpdfss() {
        return pdfss;
    }

    public void setpdfss(Set<AppLessonPDFDTO> pdfss) {
        this.pdfss = pdfss;
    }

    public Set<AppLessonVideoDTO> getlessonvideo() {
        return lessonvideo;
    }

    public void setlessonvideo(Set<AppLessonVideoDTO> lessonvideo) {
        this.lessonvideo = lessonvideo;
    }

    public AppLessonDTO getlessonDTO() {
        return lessonDTO;
    }

    public void setlessonDTO(AppLessonDTO lessonDTO) {
        this.lessonDTO = lessonDTO;
    }

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

    public String getName() {
        return Name;
    }

    public void setName(String pdfUrl) {
        this.Name = pdfUrl;
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
            ", pdfUrl='" + getName() + "'" +
            ", videourl='" + getlessonvideo() + "'" +
            // ", lesson=" + getLesson() +
            "}";
    }
}
