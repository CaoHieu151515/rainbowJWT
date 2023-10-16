package com.auth0.rainbow.service.mapper;

import com.auth0.rainbow.domain.AppUser;
import com.auth0.rainbow.domain.LinkAccountUser;
import com.auth0.rainbow.domain.User;
import com.auth0.rainbow.service.dto.AppUserDTO;
import com.auth0.rainbow.service.dto.LinkAccountUserDTO;
import com.auth0.rainbow.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link LinkAccountUser} and its DTO {@link LinkAccountUserDTO}.
 */
@Mapper(componentModel = "spring")
public interface LinkAccountUserMapper extends EntityMapper<LinkAccountUserDTO, LinkAccountUser> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    @Mapping(target = "appUser", source = "appUser", qualifiedByName = "appUserId")
    LinkAccountUserDTO toDto(LinkAccountUser s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);

    @Named("appUserId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppUserDTO toDtoAppUserId(AppUser appUser);
}
