package com.auth0.rainbow.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link com.auth0.rainbow.domain.AppPayment} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppPaymentDTO implements Serializable {

    private Long id;

    private String method;

    private String status;

    private BigDecimal amount;

    private String note;

    private AppOrderDTO order;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public AppOrderDTO getOrder() {
        return order;
    }

    public void setOrder(AppOrderDTO order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppPaymentDTO)) {
            return false;
        }

        AppPaymentDTO appPaymentDTO = (AppPaymentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, appPaymentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppPaymentDTO{" +
            "id=" + getId() +
            ", method='" + getMethod() + "'" +
            ", status='" + getStatus() + "'" +
            ", amount=" + getAmount() +
            ", note='" + getNote() + "'" +
            ", order=" + getOrder() +
            "}";
    }
}
