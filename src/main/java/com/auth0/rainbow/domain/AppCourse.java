package com.auth0.rainbow.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A AppCourse.
 */
@Entity
@Table(name = "app_course")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppCourse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "level")
    private String level;

    @Column(name = "image")
    private String image;

    @OneToMany(mappedBy = "course")
    @JsonIgnoreProperties(value = { "lessons", "lessonInfos", "course" }, allowSetters = true)
    private Set<AppLesson> courses = new HashSet<>();

    @ManyToMany(mappedBy = "courses")
    @JsonIgnoreProperties(value = { "orders", "posts", "courses", "availableCourses", "cart" }, allowSetters = true)
    private Set<AppUser> users = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AppCourse id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public AppCourse name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return this.level;
    }

    public AppCourse level(String level) {
        this.setLevel(level);
        return this;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getImage() {
        return this.image;
    }

    public AppCourse image(String image) {
        this.setImage(image);
        return this;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Set<AppLesson> getCourses() {
        return this.courses;
    }

    public void setCourses(Set<AppLesson> appLessons) {
        if (this.courses != null) {
            this.courses.forEach(i -> i.setCourse(null));
        }
        if (appLessons != null) {
            appLessons.forEach(i -> i.setCourse(this));
        }
        this.courses = appLessons;
    }

    public AppCourse courses(Set<AppLesson> appLessons) {
        this.setCourses(appLessons);
        return this;
    }

    public AppCourse addCourses(AppLesson appLesson) {
        this.courses.add(appLesson);
        appLesson.setCourse(this);
        return this;
    }

    public AppCourse removeCourses(AppLesson appLesson) {
        this.courses.remove(appLesson);
        appLesson.setCourse(null);
        return this;
    }

    public Set<AppUser> getUsers() {
        return this.users;
    }

    public void setUsers(Set<AppUser> appUsers) {
        if (this.users != null) {
            this.users.forEach(i -> i.removeCourses(this));
        }
        if (appUsers != null) {
            appUsers.forEach(i -> i.addCourses(this));
        }
        this.users = appUsers;
    }

    public AppCourse users(Set<AppUser> appUsers) {
        this.setUsers(appUsers);
        return this;
    }

    public AppCourse addUsers(AppUser appUser) {
        this.users.add(appUser);
        appUser.getCourses().add(this);
        return this;
    }

    public AppCourse removeUsers(AppUser appUser) {
        this.users.remove(appUser);
        appUser.getCourses().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppCourse)) {
            return false;
        }
        return id != null && id.equals(((AppCourse) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppCourse{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", level='" + getLevel() + "'" +
            ", image='" + getImage() + "'" +
            "}";
    }
}
