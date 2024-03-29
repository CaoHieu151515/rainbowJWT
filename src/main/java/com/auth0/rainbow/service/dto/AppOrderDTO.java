package com.auth0.rainbow.service.dto;

import com.auth0.rainbow.domain.AppPayment;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.auth0.rainbow.domain.AppOrder} entity.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppOrderDTO implements Serializable {

    private Long id;

    private BigDecimal total;

    private ZonedDateTime createdAt;

    private String status;

    private Long paymentID;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private AppUserDTO user;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Set<AppPayment> paymentss;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Set<AppOrderItemDTO> orderItemss;

    public Set<AppOrderItemDTO> getorderItemss() {
        return orderItemss;
    }

    public void setorderItemss(Set<AppOrderItemDTO> orderItemss) {
        this.orderItemss = orderItemss;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(Long paymentID) {
        this.paymentID = paymentID;
    }

    public AppUserDTO getUser() {
        return user;
    }

    public void setUser(AppUserDTO user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppOrderDTO)) {
            return false;
        }

        AppOrderDTO appOrderDTO = (AppOrderDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, appOrderDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppOrderDTO{" +
            "id=" + getId() +
            ", total=" + getTotal() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", status='" + getStatus() + "'" +
            ", paymentID=" + getPaymentID() +
            ", user=" + getUser() +
            "}";
    }
}
