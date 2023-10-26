package com.auth0.rainbow.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.auth0.rainbow.domain.LinkAccountUser} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LinkAccountUserDTO implements Serializable {

    private Long id;

    private UserDTO user;

    private AppUserDTO appUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public AppUserDTO getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUserDTO appUser) {
        this.appUser = appUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LinkAccountUserDTO)) {
            return false;
        }

        LinkAccountUserDTO linkAccountUserDTO = (LinkAccountUserDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, linkAccountUserDTO.id);
    }

    public LinkAccountUserDTO removeUser() {
        this.user = null;
        return this;
    }

    public LinkAccountUserDTO removeAppUser() {
        this.appUser = null;
        return this;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LinkAccountUserDTO{" +
            "id=" + getId() +
            ", user=" + getUser() +
            ", appUser=" + getAppUser() +
            "}";
    }
}
