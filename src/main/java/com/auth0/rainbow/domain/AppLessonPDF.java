package com.auth0.rainbow.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A AppLessonPDF.
 */
@Entity
@Table(name = "app_lesson_pdf")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppLessonPDF implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "pdf_url")
    private String pdfUrl;

    @ManyToOne
    @JsonIgnoreProperties(value = { "videos", "pdfs", "lesson" }, allowSetters = true)
    private AppLessonInfo lesson;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AppLessonPDF id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public AppLessonPDF description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPdfUrl() {
        return this.pdfUrl;
    }

    public AppLessonPDF pdfUrl(String pdfUrl) {
        this.setPdfUrl(pdfUrl);
        return this;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    public AppLessonInfo getLesson() {
        return this.lesson;
    }

    public void setLesson(AppLessonInfo appLessonInfo) {
        this.lesson = appLessonInfo;
    }

    public AppLessonPDF lesson(AppLessonInfo appLessonInfo) {
        this.setLesson(appLessonInfo);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppLessonPDF)) {
            return false;
        }
        return id != null && id.equals(((AppLessonPDF) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppLessonPDF{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", pdfUrl='" + getPdfUrl() + "'" +
            "}";
    }
}
