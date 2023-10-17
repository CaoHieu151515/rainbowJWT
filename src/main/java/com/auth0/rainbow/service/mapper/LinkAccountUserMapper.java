package com.auth0.rainbow.service.mapper;

import com.auth0.rainbow.domain.AppCourse;
import com.auth0.rainbow.domain.AppUser;
import com.auth0.rainbow.domain.LinkAccountUser;
import com.auth0.rainbow.domain.User;
import com.auth0.rainbow.service.dto.AppCourseDTO;
import com.auth0.rainbow.service.dto.AppUserDTO;
import com.auth0.rainbow.service.dto.LinkAccountUserDTO;
import com.auth0.rainbow.service.dto.UserDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for the entity {@link LinkAccountUser} and its DTO
 * {@link LinkAccountUserDTO}.
 */
@Mapper(componentModel = "spring")
public interface LinkAccountUserMapper extends EntityMapper<LinkAccountUserDTO, LinkAccountUser> {
    AppPostMapper INSTANCE = Mappers.getMapper(AppPostMapper.class);

    AppUserMapper otherMapper = Mappers.getMapper(AppUserMapper.class);

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

    @Named("toLinkDTO")
    @Mappings(
        {
            @Mapping(target = "user", source = "user", qualifiedByName = "mapToUser"),
            @Mapping(target = "appUser", source = "appUser", qualifiedByName = "mapToAppuser"),
        }
    )
    LinkAccountUserDTO toLinkDTO(LinkAccountUser linkAccountUser);

    @Named("mapToUser")
    static UserDTO mapToUser(User user) {
        UserDTO UserDTO = new UserDTO();
        UserDTO.setId(user.getId());
        UserDTO.setLogin(user.getLogin());
        return UserDTO;
    }

    @Named("mapToAppuser")
    static AppUserDTO mapToAppuser(AppUser appUser) {
        if (appUser == null) {
            return null;
        }
        return AppUserMapper.INSTANCE.toAppUserDTO(appUser);
    }
}
