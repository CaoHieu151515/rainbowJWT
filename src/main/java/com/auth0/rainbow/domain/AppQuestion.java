package com.auth0.rainbow.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A AppQuestion.
 */
@Entity
@Table(name = "app_question")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppQuestion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "question_name")
    private String questionName;

    @Column(name = "question_text")
    private String questionText;

    @OneToMany(mappedBy = "question")
    @JsonIgnoreProperties(value = { "question" }, allowSetters = true)
    private Set<AppMultipleChoiceAnswer> questions = new HashSet<>();

    @JsonIgnoreProperties(value = { "appQuestion" }, allowSetters = true)
    @OneToOne(mappedBy = "appQuestion")
    private AppQuestionVideoInfo appQuestion;

    @ManyToOne
    @JsonIgnoreProperties(value = { "lessons", "lessonInfos", "course" }, allowSetters = true)
    private AppLesson lesson;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AppQuestion id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestionName() {
        return this.questionName;
    }

    public AppQuestion questionName(String questionName) {
        this.setQuestionName(questionName);
        return this;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public String getQuestionText() {
        return this.questionText;
    }

    public AppQuestion questionText(String questionText) {
        this.setQuestionText(questionText);
        return this;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public Set<AppMultipleChoiceAnswer> getQuestions() {
        return this.questions;
    }

    public void setQuestions(Set<AppMultipleChoiceAnswer> appMultipleChoiceAnswers) {
        if (this.questions != null) {
            this.questions.forEach(i -> i.setQuestion(null));
        }
        if (appMultipleChoiceAnswers != null) {
            appMultipleChoiceAnswers.forEach(i -> i.setQuestion(this));
        }
        this.questions = appMultipleChoiceAnswers;
    }

    public AppQuestion questions(Set<AppMultipleChoiceAnswer> appMultipleChoiceAnswers) {
        this.setQuestions(appMultipleChoiceAnswers);
        return this;
    }

    public AppQuestion addQuestions(AppMultipleChoiceAnswer appMultipleChoiceAnswer) {
        this.questions.add(appMultipleChoiceAnswer);
        appMultipleChoiceAnswer.setQuestion(this);
        return this;
    }

    public AppQuestion removeQuestions(AppMultipleChoiceAnswer appMultipleChoiceAnswer) {
        this.questions.remove(appMultipleChoiceAnswer);
        appMultipleChoiceAnswer.setQuestion(null);
        return this;
    }

    public AppQuestionVideoInfo getAppQuestion() {
        return this.appQuestion;
    }

    public void setAppQuestion(AppQuestionVideoInfo appQuestionVideoInfo) {
        if (this.appQuestion != null) {
            this.appQuestion.setAppQuestion(null);
        }
        if (appQuestionVideoInfo != null) {
            appQuestionVideoInfo.setAppQuestion(this);
        }
        this.appQuestion = appQuestionVideoInfo;
    }

    public AppQuestion appQuestion(AppQuestionVideoInfo appQuestionVideoInfo) {
        this.setAppQuestion(appQuestionVideoInfo);
        return this;
    }

    public AppLesson getLesson() {
        return this.lesson;
    }

    public void setLesson(AppLesson appLesson) {
        this.lesson = appLesson;
    }

    public AppQuestion lesson(AppLesson appLesson) {
        this.setLesson(appLesson);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppQuestion)) {
            return false;
        }
        return id != null && id.equals(((AppQuestion) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppQuestion{" +
            "id=" + getId() +
            ", questionName='" + getQuestionName() + "'" +
            ", questionText='" + getQuestionText() + "'" +
            "}";
    }
}
