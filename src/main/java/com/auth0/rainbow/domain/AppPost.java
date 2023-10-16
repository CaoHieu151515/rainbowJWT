package com.auth0.rainbow.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A AppPost.
 */
@Entity
@Table(name = "app_post")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppPost implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "author")
    private String author;

    @Column(name = "date_written")
    private ZonedDateTime dateWritten;

    @Column(name = "published_date")
    private ZonedDateTime publishedDate;

    @Column(name = "is_featured")
    private Boolean isFeatured;

    @OneToMany(mappedBy = "post")
    @JsonIgnoreProperties(value = { "post" }, allowSetters = true)
    private Set<AppPostImage> images = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "orders", "posts", "courses", "availableCourses", "cart" }, allowSetters = true)
    private AppUser user;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AppPost id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public AppPost title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public AppPost content(String content) {
        this.setContent(content);
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return this.author;
    }

    public AppPost author(String author) {
        this.setAuthor(author);
        return this;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public ZonedDateTime getDateWritten() {
        return this.dateWritten;
    }

    public AppPost dateWritten(ZonedDateTime dateWritten) {
        this.setDateWritten(dateWritten);
        return this;
    }

    public void setDateWritten(ZonedDateTime dateWritten) {
        this.dateWritten = dateWritten;
    }

    public ZonedDateTime getPublishedDate() {
        return this.publishedDate;
    }

    public AppPost publishedDate(ZonedDateTime publishedDate) {
        this.setPublishedDate(publishedDate);
        return this;
    }

    public void setPublishedDate(ZonedDateTime publishedDate) {
        this.publishedDate = publishedDate;
    }

    public Boolean getIsFeatured() {
        return this.isFeatured;
    }

    public AppPost isFeatured(Boolean isFeatured) {
        this.setIsFeatured(isFeatured);
        return this;
    }

    public void setIsFeatured(Boolean isFeatured) {
        this.isFeatured = isFeatured;
    }

    public Set<AppPostImage> getImages() {
        return this.images;
    }

    public void setImages(Set<AppPostImage> appPostImages) {
        if (this.images != null) {
            this.images.forEach(i -> i.setPost(null));
        }
        if (appPostImages != null) {
            appPostImages.forEach(i -> i.setPost(this));
        }
        this.images = appPostImages;
    }

    public AppPost images(Set<AppPostImage> appPostImages) {
        this.setImages(appPostImages);
        return this;
    }

    public AppPost addImages(AppPostImage appPostImage) {
        this.images.add(appPostImage);
        appPostImage.setPost(this);
        return this;
    }

    public AppPost removeImages(AppPostImage appPostImage) {
        this.images.remove(appPostImage);
        appPostImage.setPost(null);
        return this;
    }

    public AppUser getUser() {
        return this.user;
    }

    public void setUser(AppUser appUser) {
        this.user = appUser;
    }

    public AppPost user(AppUser appUser) {
        this.setUser(appUser);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppPost)) {
            return false;
        }
        return id != null && id.equals(((AppPost) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppPost{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", content='" + getContent() + "'" +
            ", author='" + getAuthor() + "'" +
            ", dateWritten='" + getDateWritten() + "'" +
            ", publishedDate='" + getPublishedDate() + "'" +
            ", isFeatured='" + getIsFeatured() + "'" +
            "}";
    }
}
