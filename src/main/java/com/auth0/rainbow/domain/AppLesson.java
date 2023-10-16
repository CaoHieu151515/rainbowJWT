package com.auth0.rainbow.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A AppLesson.
 */
@Entity
@Table(name = "app_lesson")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppLesson implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToMany(mappedBy = "lesson")
    @JsonIgnoreProperties(value = { "questions", "lesson" }, allowSetters = true)
    private Set<AppQuestion> lessons = new HashSet<>();

    @OneToMany(mappedBy = "lesson")
    @JsonIgnoreProperties(value = { "videos", "lesson" }, allowSetters = true)
    private Set<AppLessonInfo> lessonInfos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "courses", "users" }, allowSetters = true)
    private AppCourse course;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AppLesson id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<AppQuestion> getLessons() {
        return this.lessons;
    }

    public void setLessons(Set<AppQuestion> appQuestions) {
        if (this.lessons != null) {
            this.lessons.forEach(i -> i.setLesson(null));
        }
        if (appQuestions != null) {
            appQuestions.forEach(i -> i.setLesson(this));
        }
        this.lessons = appQuestions;
    }

    public AppLesson lessons(Set<AppQuestion> appQuestions) {
        this.setLessons(appQuestions);
        return this;
    }

    public AppLesson addLessons(AppQuestion appQuestion) {
        this.lessons.add(appQuestion);
        appQuestion.setLesson(this);
        return this;
    }

    public AppLesson removeLessons(AppQuestion appQuestion) {
        this.lessons.remove(appQuestion);
        appQuestion.setLesson(null);
        return this;
    }

    public Set<AppLessonInfo> getLessonInfos() {
        return this.lessonInfos;
    }

    public void setLessonInfos(Set<AppLessonInfo> appLessonInfos) {
        if (this.lessonInfos != null) {
            this.lessonInfos.forEach(i -> i.setLesson(null));
        }
        if (appLessonInfos != null) {
            appLessonInfos.forEach(i -> i.setLesson(this));
        }
        this.lessonInfos = appLessonInfos;
    }

    public AppLesson lessonInfos(Set<AppLessonInfo> appLessonInfos) {
        this.setLessonInfos(appLessonInfos);
        return this;
    }

    public AppLesson addLessonInfos(AppLessonInfo appLessonInfo) {
        this.lessonInfos.add(appLessonInfo);
        appLessonInfo.setLesson(this);
        return this;
    }

    public AppLesson removeLessonInfos(AppLessonInfo appLessonInfo) {
        this.lessonInfos.remove(appLessonInfo);
        appLessonInfo.setLesson(null);
        return this;
    }

    public AppCourse getCourse() {
        return this.course;
    }

    public void setCourse(AppCourse appCourse) {
        this.course = appCourse;
    }

    public AppLesson course(AppCourse appCourse) {
        this.setCourse(appCourse);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppLesson)) {
            return false;
        }
        return id != null && id.equals(((AppLesson) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppLesson{" +
            "id=" + getId() +
            "}";
    }
}
