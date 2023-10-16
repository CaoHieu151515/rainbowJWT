package com.auth0.rainbow.service.mapper;

import com.auth0.rainbow.domain.AppOrder;
import com.auth0.rainbow.domain.AppOrderItem;
import com.auth0.rainbow.domain.AppProduct;
import com.auth0.rainbow.service.dto.AppOrderDTO;
import com.auth0.rainbow.service.dto.AppOrderItemDTO;
import com.auth0.rainbow.service.dto.AppProductDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AppOrderItem} and its DTO {@link AppOrderItemDTO}.
 */
@Mapper(componentModel = "spring")
public interface AppOrderItemMapper extends EntityMapper<AppOrderItemDTO, AppOrderItem> {
    @Mapping(target = "product", source = "product", qualifiedByName = "appProductId")
    @Mapping(target = "order", source = "order", qualifiedByName = "appOrderId")
    AppOrderItemDTO toDto(AppOrderItem s);

    @Named("appProductId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppProductDTO toDtoAppProductId(AppProduct appProduct);

    @Named("appOrderId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppOrderDTO toDtoAppOrderId(AppOrder appOrder);
}
