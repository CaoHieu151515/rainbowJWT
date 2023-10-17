package com.auth0.rainbow.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A AppLessonVideo.
 */
@Entity
@Table(name = "app_lesson_video")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppLessonVideo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "video_url")
    private String videoUrl;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = { "videos", "lesson" }, allowSetters = true)
    private AppLessonInfo lessonInfo;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AppLessonVideo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVideoUrl() {
        return this.videoUrl;
    }

    public AppLessonVideo videoUrl(String videoUrl) {
        this.setVideoUrl(videoUrl);
        return this;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public AppLessonInfo getLessonInfo() {
        return this.lessonInfo;
    }

    public void setLessonInfo(AppLessonInfo appLessonInfo) {
        this.lessonInfo = appLessonInfo;
    }

    public AppLessonVideo lessonInfo(AppLessonInfo appLessonInfo) {
        this.setLessonInfo(appLessonInfo);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppLessonVideo)) {
            return false;
        }
        return id != null && id.equals(((AppLessonVideo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppLessonVideo{" +
            "id=" + getId() +
            ", videoUrl='" + getVideoUrl() + "'" +
            "}";
    }
}
