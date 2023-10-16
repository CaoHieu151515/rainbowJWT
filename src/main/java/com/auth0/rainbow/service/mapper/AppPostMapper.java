package com.auth0.rainbow.service.mapper;

import com.auth0.rainbow.domain.AppPost;
import com.auth0.rainbow.domain.AppUser;
import com.auth0.rainbow.service.dto.AppPostDTO;
import com.auth0.rainbow.service.dto.AppUserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AppPost} and its DTO {@link AppPostDTO}.
 */
@Mapper(componentModel = "spring")
public interface AppPostMapper extends EntityMapper<AppPostDTO, AppPost> {
    @Mapping(target = "user", source = "user", qualifiedByName = "appUserId")
    AppPostDTO toDto(AppPost s);

    @Named("appUserId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppUserDTO toDtoAppUserId(AppUser appUser);
}
