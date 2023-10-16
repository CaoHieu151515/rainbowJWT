package com.auth0.rainbow.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.auth0.rainbow.domain.AppAvailableCourse} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppAvailableCourseDTO implements Serializable {

    private Long id;

    private AppCourseDTO courses;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AppCourseDTO getCourses() {
        return courses;
    }

    public void setCourses(AppCourseDTO courses) {
        this.courses = courses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppAvailableCourseDTO)) {
            return false;
        }

        AppAvailableCourseDTO appAvailableCourseDTO = (AppAvailableCourseDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, appAvailableCourseDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppAvailableCourseDTO{" +
            "id=" + getId() +
            ", courses=" + getCourses() +
            "}";
    }
}
