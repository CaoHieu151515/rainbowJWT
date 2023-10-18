package com.auth0.rainbow.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A AppQuestionVideoInfo.
 */
@Entity
@Table(name = "app_question_video_info")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppQuestionVideoInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "quenstion_video")
    private String quenstionVideo;

    @JsonIgnoreProperties(value = { "questions", "appQuestion", "lesson" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private AppQuestion appQuestion;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AppQuestionVideoInfo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public AppQuestionVideoInfo description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQuenstionVideo() {
        return this.quenstionVideo;
    }

    public AppQuestionVideoInfo quenstionVideo(String quenstionVideo) {
        this.setQuenstionVideo(quenstionVideo);
        return this;
    }

    public void setQuenstionVideo(String quenstionVideo) {
        this.quenstionVideo = quenstionVideo;
    }

    public AppQuestion getAppQuestion() {
        return this.appQuestion;
    }

    public void setAppQuestion(AppQuestion appQuestion) {
        this.appQuestion = appQuestion;
    }

    public AppQuestionVideoInfo appQuestion(AppQuestion appQuestion) {
        this.setAppQuestion(appQuestion);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppQuestionVideoInfo)) {
            return false;
        }
        return id != null && id.equals(((AppQuestionVideoInfo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppQuestionVideoInfo{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", quenstionVideo='" + getQuenstionVideo() + "'" +
            "}";
    }
}
