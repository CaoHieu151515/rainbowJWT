package com.auth0.rainbow.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A AppOrder.
 */
@Entity
@Table(name = "app_order")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "total", precision = 21, scale = 2)
    private BigDecimal total;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @Column(name = "status")
    private String status;

    @Column(name = "payment_id")
    private Long paymentID;

    @OneToMany(mappedBy = "order")
    @JsonIgnoreProperties(value = { "product", "order" }, allowSetters = true)
    private Set<AppOrderItem> orderItems = new HashSet<>();

    @OneToMany(mappedBy = "order")
    @JsonIgnoreProperties(value = { "order" }, allowSetters = true)
    private Set<AppPayment> payments = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JsonIgnoreProperties(value = { "orders", "posts", "courses", "availableCourses", "cart" }, allowSetters = true)
    private AppUser user;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AppOrder id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTotal() {
        return this.total;
    }

    public AppOrder total(BigDecimal total) {
        this.setTotal(total);
        return this;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public ZonedDateTime getCreatedAt() {
        return this.createdAt;
    }

    public AppOrder createdAt(ZonedDateTime createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return this.status;
    }

    public AppOrder status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getPaymentID() {
        return this.paymentID;
    }

    public AppOrder paymentID(Long paymentID) {
        this.setPaymentID(paymentID);
        return this;
    }

    public void setPaymentID(Long paymentID) {
        this.paymentID = paymentID;
    }

    public Set<AppOrderItem> getOrderItems() {
        return this.orderItems;
    }

    public void setOrderItems(Set<AppOrderItem> appOrderItems) {
        if (this.orderItems != null) {
            this.orderItems.forEach(i -> i.setOrder(null));
        }
        if (appOrderItems != null) {
            appOrderItems.forEach(i -> i.setOrder(this));
        }
        this.orderItems = appOrderItems;
    }

    public AppOrder orderItems(Set<AppOrderItem> appOrderItems) {
        this.setOrderItems(appOrderItems);
        return this;
    }

    public AppOrder addOrderItems(AppOrderItem appOrderItem) {
        this.orderItems.add(appOrderItem);
        appOrderItem.setOrder(this);
        return this;
    }

    public AppOrder removeOrderItems(AppOrderItem appOrderItem) {
        this.orderItems.remove(appOrderItem);
        appOrderItem.setOrder(null);
        return this;
    }

    public Set<AppPayment> getPayments() {
        return this.payments;
    }

    public void setPayments(Set<AppPayment> appPayments) {
        if (this.payments != null) {
            this.payments.forEach(i -> i.setOrder(null));
        }
        if (appPayments != null) {
            appPayments.forEach(i -> i.setOrder(this));
        }
        this.payments = appPayments;
    }

    public AppOrder payments(Set<AppPayment> appPayments) {
        this.setPayments(appPayments);
        return this;
    }

    public AppOrder addPayments(AppPayment appPayment) {
        this.payments.add(appPayment);
        appPayment.setOrder(this);
        return this;
    }

    public AppOrder removePayments(AppPayment appPayment) {
        this.payments.remove(appPayment);
        appPayment.setOrder(null);
        return this;
    }

    public AppUser getUser() {
        return this.user;
    }

    public void setUser(AppUser appUser) {
        this.user = appUser;
    }

    public AppOrder user(AppUser appUser) {
        this.setUser(appUser);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppOrder)) {
            return false;
        }
        return id != null && id.equals(((AppOrder) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppOrder{" +
            "id=" + getId() +
            ", total=" + getTotal() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", status='" + getStatus() + "'" +
            ", paymentID=" + getPaymentID() +
            "}";
    }
}
