package com.auth0.rainbow.service.mapper;

import com.auth0.rainbow.domain.AppOrder;
import com.auth0.rainbow.domain.AppUser;
import com.auth0.rainbow.service.dto.AppOrderDTO;
import com.auth0.rainbow.service.dto.AppUserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AppOrder} and its DTO {@link AppOrderDTO}.
 */
@Mapper(componentModel = "spring")
public interface AppOrderMapper extends EntityMapper<AppOrderDTO, AppOrder> {
    @Mapping(target = "user", source = "user", qualifiedByName = "appUserId")
    AppOrderDTO toDto(AppOrder s);

    @Named("appUserId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppUserDTO toDtoAppUserId(AppUser appUser);
}
