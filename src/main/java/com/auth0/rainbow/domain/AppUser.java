package com.auth0.rainbow.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A AppUser.
 */
@Entity
@Table(name = "app_user")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "gender")
    private String gender;

    @Column(name = "dob")
    private ZonedDateTime dob;

    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties(value = { "orderItems", "payments", "user" }, allowSetters = true)
    private Set<AppOrder> orders = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties(value = { "images", "user" }, allowSetters = true)
    private Set<AppPost> posts = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
        name = "rel_app_user__courses",
        joinColumns = @JoinColumn(name = "app_user_id"),
        inverseJoinColumns = @JoinColumn(name = "courses_id")
    )
    @JsonIgnoreProperties(value = { "courses", "users" }, allowSetters = true)
    private Set<AppCourse> courses = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
        name = "rel_app_user__available_courses",
        joinColumns = @JoinColumn(name = "app_user_id"),
        inverseJoinColumns = @JoinColumn(name = "available_courses_id")
    )
    @JsonIgnoreProperties(value = { "courses", "users" }, allowSetters = true)
    private Set<AppAvailableCourse> availableCourses = new HashSet<>();

    @JsonIgnoreProperties(value = { "user", "products" }, allowSetters = true)
    @OneToOne(mappedBy = "user")
    private AppCart cart;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AppUser id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public AppUser name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return this.gender;
    }

    public AppUser gender(String gender) {
        this.setGender(gender);
        return this;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public ZonedDateTime getDob() {
        return this.dob;
    }

    public AppUser dob(ZonedDateTime dob) {
        this.setDob(dob);
        return this;
    }

    public void setDob(ZonedDateTime dob) {
        this.dob = dob;
    }

    public String getStatus() {
        return this.status;
    }

    public AppUser status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<AppOrder> getOrders() {
        return this.orders;
    }

    public void setOrders(Set<AppOrder> appOrders) {
        if (this.orders != null) {
            this.orders.forEach(i -> i.setUser(null));
        }
        if (appOrders != null) {
            appOrders.forEach(i -> i.setUser(this));
        }
        this.orders = appOrders;
    }

    public AppUser orders(Set<AppOrder> appOrders) {
        this.setOrders(appOrders);
        return this;
    }

    public AppUser addOrders(AppOrder appOrder) {
        this.orders.add(appOrder);
        appOrder.setUser(this);
        return this;
    }

    public AppUser removeOrders(AppOrder appOrder) {
        this.orders.remove(appOrder);
        appOrder.setUser(null);
        return this;
    }

    public Set<AppPost> getPosts() {
        return this.posts;
    }

    public void setPosts(Set<AppPost> appPosts) {
        if (this.posts != null) {
            this.posts.forEach(i -> i.setUser(null));
        }
        if (appPosts != null) {
            appPosts.forEach(i -> i.setUser(this));
        }
        this.posts = appPosts;
    }

    public AppUser posts(Set<AppPost> appPosts) {
        this.setPosts(appPosts);
        return this;
    }

    public AppUser addPosts(AppPost appPost) {
        this.posts.add(appPost);
        appPost.setUser(this);
        return this;
    }

    public AppUser removePosts(AppPost appPost) {
        this.posts.remove(appPost);
        appPost.setUser(null);
        return this;
    }

    public Set<AppCourse> getCourses() {
        return this.courses;
    }

    public void setCourses(Set<AppCourse> appCourses) {
        this.courses = appCourses;
    }

    public AppUser courses(Set<AppCourse> appCourses) {
        this.setCourses(appCourses);
        return this;
    }

    public AppUser addCourses(AppCourse appCourse) {
        this.courses.add(appCourse);
        appCourse.getUsers().add(this);
        return this;
    }

    public AppUser removeCourses(AppCourse appCourse) {
        this.courses.remove(appCourse);
        appCourse.getUsers().remove(this);
        return this;
    }

    public Set<AppAvailableCourse> getAvailableCourses() {
        return this.availableCourses;
    }

    public void setAvailableCourses(Set<AppAvailableCourse> appAvailableCourses) {
        this.availableCourses = appAvailableCourses;
    }

    public AppUser availableCourses(Set<AppAvailableCourse> appAvailableCourses) {
        this.setAvailableCourses(appAvailableCourses);
        return this;
    }

    public AppUser addAvailableCourses(AppAvailableCourse appAvailableCourse) {
        this.availableCourses.add(appAvailableCourse);
        appAvailableCourse.getUsers().add(this);
        return this;
    }

    public AppUser removeAvailableCourses(AppAvailableCourse appAvailableCourse) {
        this.availableCourses.remove(appAvailableCourse);
        appAvailableCourse.getUsers().remove(this);
        return this;
    }

    public AppCart getCart() {
        return this.cart;
    }

    public void setCart(AppCart appCart) {
        if (this.cart != null) {
            this.cart.setUser(null);
        }
        if (appCart != null) {
            appCart.setUser(this);
        }
        this.cart = appCart;
    }

    public AppUser cart(AppCart appCart) {
        this.setCart(appCart);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppUser)) {
            return false;
        }
        return id != null && id.equals(((AppUser) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppUser{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", gender='" + getGender() + "'" +
            ", dob='" + getDob() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
