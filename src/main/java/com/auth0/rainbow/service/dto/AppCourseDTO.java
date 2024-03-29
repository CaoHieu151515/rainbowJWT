package com.auth0.rainbow.service.dto;

import com.auth0.rainbow.domain.AppUser;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.auth0.rainbow.domain.AppCourse} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppCourseDTO implements Serializable {

    private Long id;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String name;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String level;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String image;

    private Set<AppLessonDTO> appLesson;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Set<AppUser> users;

    public Set<AppUser> getUsers() {
        return users;
    }

    public void setUsers(Set<AppUser> users) {
        this.users = users;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Set<AppLessonDTO> getappLesson() {
        return appLesson;
    }

    public void setAppLesson(Set<AppLessonDTO> appLesson) {
        this.appLesson = appLesson;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppCourseDTO)) {
            return false;
        }

        AppCourseDTO appCourseDTO = (AppCourseDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, appCourseDTO.id);
    }

    public void removeCourses(AppLessonDTO appLesson) {
        if (this.appLesson != null) {
            this.appLesson.remove(appLesson);
        }
    }

    public void removeUsers(AppUser appUser) {
        if (this.users != null) {
            this.users.remove(appUser);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppCourseDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", level='" + getLevel() + "'" +
            ", image='" + getImage() + "'" +
            ", lesson='" + getappLesson() + "'" +
            "}";
    }
}
