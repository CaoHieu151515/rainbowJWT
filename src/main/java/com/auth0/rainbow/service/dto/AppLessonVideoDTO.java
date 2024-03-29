package com.auth0.rainbow.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.auth0.rainbow.domain.AppLessonVideo} entity.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppLessonVideoDTO implements Serializable {

    private Long id;

    private String description;

    private String videoUrl;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private AppLessonInfoDTO lessonInfo;

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

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public AppLessonInfoDTO getLessonInfo() {
        return lessonInfo;
    }

    public void setLessonInfo(AppLessonInfoDTO lessonInfo) {
        this.lessonInfo = lessonInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppLessonVideoDTO)) {
            return false;
        }

        AppLessonVideoDTO appLessonVideoDTO = (AppLessonVideoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, appLessonVideoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppLessonVideoDTO{" +
            "id=" + getId() +
            ", videoUrl='" + getVideoUrl() + "'" +      
            ", lessonInfo=" + getLessonInfo() +
            "}";
    }
}
