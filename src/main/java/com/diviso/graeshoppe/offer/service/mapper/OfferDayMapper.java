package com.diviso.graeshoppe.offer.service.mapper;

import com.diviso.graeshoppe.offer.domain.*;
import com.diviso.graeshoppe.offer.service.dto.OfferDayDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity OfferDay and its DTO OfferDayDTO.
 */
@Mapper(componentModel = "spring", uses = {OfferMapper.class})
public interface OfferDayMapper extends EntityMapper<OfferDayDTO, OfferDay> {

    @Mapping(source = "offer.id", target = "offerId")
    OfferDayDTO toDto(OfferDay offerDay);

    @Mapping(source = "offerId", target = "offer")
    OfferDay toEntity(OfferDayDTO offerDayDTO);

    default OfferDay fromId(Long id) {
        if (id == null) {
            return null;
        }
        OfferDay offerDay = new OfferDay();
        offerDay.setId(id);
        return offerDay;
    }
}
