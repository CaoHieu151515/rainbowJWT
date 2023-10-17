package com.auth0.rainbow.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A AppAvailableCourse.
 */
@Entity
@Table(name = "app_available_course")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppAvailableCourse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = { "courses", "users" }, allowSetters = true)
    private AppCourse courses;

    @ManyToMany(mappedBy = "availableCourses", fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = { "orders", "posts", "courses", "availableCourses", "cart" }, allowSetters = true)
    private Set<AppUser> users = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AppAvailableCourse id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AppCourse getCourses() {
        return this.courses;
    }

    public void setCourses(AppCourse appCourse) {
        this.courses = appCourse;
    }

    public AppAvailableCourse courses(AppCourse appCourse) {
        this.setCourses(appCourse);
        return this;
    }

    public Set<AppUser> getUsers() {
        return this.users;
    }

    public void setUsers(Set<AppUser> appUsers) {
        if (this.users != null) {
            this.users.forEach(i -> i.removeAvailableCourses(this));
        }
        if (appUsers != null) {
            appUsers.forEach(i -> i.addAvailableCourses(this));
        }
        this.users = appUsers;
    }

    public AppAvailableCourse users(Set<AppUser> appUsers) {
        this.setUsers(appUsers);
        return this;
    }

    public AppAvailableCourse addUsers(AppUser appUser) {
        this.users.add(appUser);
        appUser.getAvailableCourses().add(this);
        return this;
    }

    public AppAvailableCourse removeUsers(AppUser appUser) {
        this.users.remove(appUser);
        appUser.getAvailableCourses().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppAvailableCourse)) {
            return false;
        }
        return id != null && id.equals(((AppAvailableCourse) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppAvailableCourse{" +
            "id=" + getId() +
            "}";
    }
}
