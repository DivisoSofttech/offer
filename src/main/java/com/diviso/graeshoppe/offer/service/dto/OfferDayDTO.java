package com.diviso.graeshoppe.offer.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the OfferDay entity.
 */
public class OfferDayDTO implements Serializable {

    private Long id;

    private String day;

    private Long offerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Long getOfferId() {
        return offerId;
    }

    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OfferDayDTO offerDayDTO = (OfferDayDTO) o;
        if (offerDayDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), offerDayDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OfferDayDTO{" +
            "id=" + getId() +
            ", day='" + getDay() + "'" +
            ", offer=" + getOfferId() +
            "}";
    }
}
