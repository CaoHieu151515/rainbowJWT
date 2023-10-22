package com.auth0.rainbow.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.auth0.rainbow.domain.AppQuestion} entity.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppQuestionDTO implements Serializable {

    private Long id;

    private String questionName;

    private String questionText;

    private Set<AppMultipleChoiceAnswerDTO> multiChoice;

    private AppQuestionVideoInfoDTO appQuestionvideo;

    public AppQuestionVideoInfoDTO getAppQuestionvideo() {
        return appQuestionvideo;
    }

    public void setAppQuestionvideo(AppQuestionVideoInfoDTO appQuestionvideo) {
        this.appQuestionvideo = appQuestionvideo;
    }

    public Set<AppMultipleChoiceAnswerDTO> getmultiChoice() {
        return multiChoice;
    }

    public void setmultiChoice(Set<AppMultipleChoiceAnswerDTO> multiChoice) {
        this.multiChoice = multiChoice;
    }

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private AppLessonDTO lesson;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
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
        if (!(o instanceof AppQuestionDTO)) {
            return false;
        }

        AppQuestionDTO appQuestionDTO = (AppQuestionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, appQuestionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppQuestionDTO{" + 
            "id=" + getId() +
            ", questionName='" + getQuestionName() + "'" +
            ", questionText='" + getQuestionText() + "'" +
            ", MutiChoice='" + getmultiChoice() + "'" +
            // ", lesson=" + getLesson() +
            "}";
    }
}
