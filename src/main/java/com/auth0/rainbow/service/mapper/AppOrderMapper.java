package com.auth0.rainbow.service.mapper;

import com.auth0.rainbow.domain.AppOrder;
import com.auth0.rainbow.domain.AppOrderItem;
import com.auth0.rainbow.domain.AppUser;
import com.auth0.rainbow.service.dto.AppOrderDTO;
import com.auth0.rainbow.service.dto.AppOrderItemDTO;
import com.auth0.rainbow.service.dto.AppUserDTO;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for the entity {@link AppOrder} and its DTO {@link AppOrderDTO}.
 */
@Mapper(componentModel = "spring")
public interface AppOrderMapper extends EntityMapper<AppOrderDTO, AppOrder> {
    AppOrderMapper INSTANCE = Mappers.getMapper(AppOrderMapper.class);
    AppOrderItemMapper othOrderItemMapper = Mappers.getMapper(AppOrderItemMapper.class);

    AppUserMapper othUserMapper = Mappers.getMapper(AppUserMapper.class);

    @Named("toDto")
    @Mappings(
        {
            @Mapping(target = "user", source = "user", qualifiedByName = "toDtoUserIdSet"),
            @Mapping(target = "orderItemss", source = "orderItems", qualifiedByName = "orderItemIdSet"),
        }
    )
    AppOrderDTO toDto(AppOrder s);

    @Named("toUserDto")
    @Mappings({ @Mapping(target = "orderItemss", source = "orderItems", qualifiedByName = "orderItemIdSet") })
    AppOrderDTO toUserDto(AppOrder s);

    @Named("appUserId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppUserDTO toDtoAppUserId(AppUser appUser);

    @Named("orderItemId")
    @BeanMapping(ignoreByDefault = false)
    @Mapping(target = "id", source = "id")
    AppOrderItemDTO toDtoOrderItemId(AppOrderItem appOrderItem);

    @Named("toDtoUserIdSet")
    @BeanMapping(ignoreByDefault = true)
    default AppUserDTO toDtoUserIdSet(AppUser appUser) {
        if (appUser == null) {
            return null;
        }
        return AppUserMapper.INSTANCE.toDto(appUser);
    }

    @Named("orderItemIdSet")
    default Set<AppOrderItemDTO> toDtoAppProductIdSet(Set<AppOrderItem> appOrderItem) {
        if (appOrderItem == null) {
            return Collections.emptySet();
        }

        return appOrderItem
            .stream()
            .map(orderItemss -> {
                return AppOrderItemMapper.INSTANCE.toDto(orderItemss);
            })
            .collect(Collectors.toSet());
    }
}
