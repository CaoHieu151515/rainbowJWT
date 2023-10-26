package com.auth0.rainbow.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.auth0.rainbow.domain.AppLesson} entity.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppLessonDTO implements Serializable {

    private Long id;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private AppCourseDTO course;

    private Set<AppQuestionDTO> appQuestion; // sử dụng tên 'appQuestion' thay vì 'appQuestionDTO'

    private Set<AppLessonInfoDTO> appLesonInf;

    //...

    public Set<AppQuestionDTO> getappQuestion() { // sử dụng tên 'getAppQuestion' thay vì 'getAppQuestionDTO'
        return appQuestion;
    }

    public void setAppQuestion(Set<AppQuestionDTO> appQuestion) { // sử dụng tên 'setAppQuestion' thay vì 'setAppAvailableCourses'
        this.appQuestion = appQuestion;
    }

    public Set<AppLessonInfoDTO> getappLesonInf() {
        return appLesonInf;
    }

    public void setappLesonInf(Set<AppLessonInfoDTO> appLesonInf) {
        this.appLesonInf = appLesonInf;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AppCourseDTO getCourse() {
        return course;
    }

    public void setCourse(AppCourseDTO course) {
        this.course = course;
    }

    public void removeLessonInfos(AppLessonInfoDTO appLessonInfo) {
        if (this.appLesonInf != null) {
            this.appLesonInf.remove(appLessonInfo);
        }
    }

    public void removeLessons(AppQuestionDTO appQuestion) {
        if (this.appQuestion != null) {
            this.appQuestion.remove(appQuestion);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppLessonDTO)) {
            return false;
        }

        AppLessonDTO appLessonDTO = (AppLessonDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, appLessonDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppLessonDTO{" +
            "id=" + getId() +
            // ", course=" + getCourse() +
            "Info=" + getappLesonInf() +
            "Question=" + getappQuestion() +
            "}";
    }
}
