package com.auth0.rainbow.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.auth0.rainbow.domain.AppUser} entity.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppUserDTO implements Serializable {

    private Long id;

    private String name;

    private String gender;

    private ZonedDateTime dob;

    private String status;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Set<AppCourseDTO> courses;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Set<AppAvailableCourseDTO> availableCourses;

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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public ZonedDateTime getDob() {
        return dob;
    }

    public void setDob(ZonedDateTime dob) {
        this.dob = dob;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<AppCourseDTO> getCourses() {
        return courses;
    }

    public void setCourses(Set<AppCourseDTO> courses) {
        this.courses = courses;
    }

    public Set<AppAvailableCourseDTO> getAvailableCourses() {
        return availableCourses;
    }

    public void setAvailableCourses(Set<AppAvailableCourseDTO> availableCourses) {
        this.availableCourses = availableCourses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppUserDTO)) {
            return false;
        }

        AppUserDTO appUserDTO = (AppUserDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, appUserDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppUserDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", gender='" + getGender() + "'" +
            ", dob='" + getDob() + "'" +
            ", status='" + getStatus() + "'" +
            ", courses=" + getCourses() +
            ", availableCourses=" + getAvailableCourses() +
            "}";
    }
}
