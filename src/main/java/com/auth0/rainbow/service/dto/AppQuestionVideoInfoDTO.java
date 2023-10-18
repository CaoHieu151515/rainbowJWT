package com.auth0.rainbow.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.auth0.rainbow.domain.AppQuestionVideoInfo} entity.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppQuestionVideoInfoDTO implements Serializable {

    private Long id;

    private String description;

    private String quenstionVideo;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private AppQuestionDTO appQuestion;

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

    public String getQuenstionVideo() {
        return quenstionVideo;
    }

    public void setQuenstionVideo(String quenstionVideo) {
        this.quenstionVideo = quenstionVideo;
    }

    public AppQuestionDTO getAppQuestion() {
        return appQuestion;
    }

    public void setAppQuestion(AppQuestionDTO appQuestion) {
        this.appQuestion = appQuestion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppQuestionVideoInfoDTO)) {
            return false;
        }

        AppQuestionVideoInfoDTO appQuestionVideoInfoDTO = (AppQuestionVideoInfoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, appQuestionVideoInfoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppQuestionVideoInfoDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", quenstionVideo='" + getQuenstionVideo() + "'" +
            ", appQuestion=" + getAppQuestion() +
            "}";
    }
}
