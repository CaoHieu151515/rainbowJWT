package com.auth0.rainbow.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A AppLessonInfo.
 */
@Entity
@Table(name = "app_lesson_info")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppLessonInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "pdf_url")
    private String pdfUrl;

    @OneToMany(mappedBy = "lessonInfo")
    @JsonIgnoreProperties(value = { "lessonInfo" }, allowSetters = true)
    private Set<AppLessonVideo> videos = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = { "lessons", "lessonInfos", "course" }, allowSetters = true)
    private AppLesson lesson;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AppLessonInfo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public AppLessonInfo description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPdfUrl() {
        return this.pdfUrl;
    }

    public AppLessonInfo pdfUrl(String pdfUrl) {
        this.setPdfUrl(pdfUrl);
        return this;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    public Set<AppLessonVideo> getVideos() {
        return this.videos;
    }

    public void setVideos(Set<AppLessonVideo> appLessonVideos) {
        if (this.videos != null) {
            this.videos.forEach(i -> i.setLessonInfo(null));
        }
        if (appLessonVideos != null) {
            appLessonVideos.forEach(i -> i.setLessonInfo(this));
        }
        this.videos = appLessonVideos;
    }

    public AppLessonInfo videos(Set<AppLessonVideo> appLessonVideos) {
        this.setVideos(appLessonVideos);
        return this;
    }

    public AppLessonInfo addVideos(AppLessonVideo appLessonVideo) {
        this.videos.add(appLessonVideo);
        appLessonVideo.setLessonInfo(this);
        return this;
    }

    public AppLessonInfo removeVideos(AppLessonVideo appLessonVideo) {
        this.videos.remove(appLessonVideo);
        appLessonVideo.setLessonInfo(null);
        return this;
    }

    public AppLesson getLesson() {
        return this.lesson;
    }

    public void setLesson(AppLesson appLesson) {
        this.lesson = appLesson;
    }

    public AppLessonInfo lesson(AppLesson appLesson) {
        this.setLesson(appLesson);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppLessonInfo)) {
            return false;
        }
        return id != null && id.equals(((AppLessonInfo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppLessonInfo{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", pdfUrl='" + getPdfUrl() + "'" +
            "}";
    }
}
