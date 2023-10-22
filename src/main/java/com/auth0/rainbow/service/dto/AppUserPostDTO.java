package com.auth0.rainbow.service.dto;

import com.auth0.rainbow.domain.AppPost;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Set;

public class AppUserPostDTO {

    private Long id;

    private String name;

    private String gender;

    private ZonedDateTime dob;

    private String status;

    private Set<AppPost> postShare;

    public Set<AppPost> getpostShare() {
        return postShare;
    }

    public void setpostShare(Set<AppPost> postShare) {
        this.postShare = postShare;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppUserPostDTO)) {
            return false;
        }

        AppUserPostDTO AppUserPostDTO = (AppUserPostDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, AppUserPostDTO.id);
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
            "}";
    }
}
