package com.auth0.rainbow.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.auth0.rainbow.domain.AppMultipleChoiceAnswer} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppMultipleChoiceAnswerDTO implements Serializable {

    private Long id;

    private String answerText;

    private Boolean isCorrect;

    private AppQuestionDTO question;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public Boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(Boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public AppQuestionDTO getQuestion() {
        return question;
    }

    public void setQuestion(AppQuestionDTO question) {
        this.question = question;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppMultipleChoiceAnswerDTO)) {
            return false;
        }

        AppMultipleChoiceAnswerDTO appMultipleChoiceAnswerDTO = (AppMultipleChoiceAnswerDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, appMultipleChoiceAnswerDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppMultipleChoiceAnswerDTO{" +
            "id=" + getId() +
            ", answerText='" + getAnswerText() + "'" +
            ", isCorrect='" + getIsCorrect() + "'" +
            // ", question=" + getQuestion() +
            "}";
    }
}
