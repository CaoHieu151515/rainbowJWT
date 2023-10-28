package com.auth0.rainbow.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A AppMultipleChoiceAnswer.
 */
@Entity
@Table(name = "app_multiple_choice_answer")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppMultipleChoiceAnswer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "answer_text")
    private String answerText;

    @Column(name = "is_correct")
    private Boolean isCorrect;

    @ManyToOne
    @JsonIgnoreProperties(value = { "questions", "appQuestion", "lesson" }, allowSetters = true)
    private AppQuestion question;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AppMultipleChoiceAnswer id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnswerText() {
        return this.answerText;
    }

    public AppMultipleChoiceAnswer answerText(String answerText) {
        this.setAnswerText(answerText);
        return this;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public Boolean getIsCorrect() {
        return this.isCorrect;
    }

    public AppMultipleChoiceAnswer isCorrect(Boolean isCorrect) {
        this.setIsCorrect(isCorrect);
        return this;
    }

    public void setIsCorrect(Boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public AppQuestion getQuestion() {
        return this.question;
    }

    public void setQuestion(AppQuestion appQuestion) {
        this.question = appQuestion;
    }

    public AppMultipleChoiceAnswer question(AppQuestion appQuestion) {
        this.setQuestion(appQuestion);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppMultipleChoiceAnswer)) {
            return false;
        }
        return id != null && id.equals(((AppMultipleChoiceAnswer) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppMultipleChoiceAnswer{" +
            "id=" + getId() +
            ", answerText='" + getAnswerText() + "'" +
            ", isCorrect='" + getIsCorrect() + "'" +
            "}";
    }
}
