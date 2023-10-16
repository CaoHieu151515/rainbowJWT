package com.auth0.rainbow.service.mapper;

import com.auth0.rainbow.domain.AppOrder;
import com.auth0.rainbow.domain.AppPayment;
import com.auth0.rainbow.service.dto.AppOrderDTO;
import com.auth0.rainbow.service.dto.AppPaymentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AppPayment} and its DTO {@link AppPaymentDTO}.
 */
@Mapper(componentModel = "spring")
public interface AppPaymentMapper extends EntityMapper<AppPaymentDTO, AppPayment> {
    @Mapping(target = "order", source = "order", qualifiedByName = "appOrderId")
    AppPaymentDTO toDto(AppPayment s);

    @Named("appOrderId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppOrderDTO toDtoAppOrderId(AppOrder appOrder);
}
