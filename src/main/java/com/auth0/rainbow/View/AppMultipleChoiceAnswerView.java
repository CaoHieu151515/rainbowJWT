package com.auth0.rainbow.View;

import com.auth0.rainbow.service.dto.AppQuestionDTO;
import java.util.Objects;

public class AppMultipleChoiceAnswerView {

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
