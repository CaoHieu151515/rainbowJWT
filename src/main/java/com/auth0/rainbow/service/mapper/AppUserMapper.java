package com.auth0.rainbow.service.mapper;

import com.auth0.rainbow.domain.AppAvailableCourse;
import com.auth0.rainbow.domain.AppCourse;
import com.auth0.rainbow.domain.AppOrder;
import com.auth0.rainbow.domain.AppPost;
import com.auth0.rainbow.domain.AppUser;
import com.auth0.rainbow.service.dto.AppAvailableCourseDTO;
import com.auth0.rainbow.service.dto.AppCourseDTO;
import com.auth0.rainbow.service.dto.AppOrderDTO;
import com.auth0.rainbow.service.dto.AppPostDTO;
import com.auth0.rainbow.service.dto.AppUserDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for the entity {@link AppUser} and its DTO {@link AppUserDTO}.
 */
@Mapper(componentModel = "spring")
public interface AppUserMapper extends EntityMapper<AppUserDTO, AppUser> {
    AppUserMapper INSTANCE = Mappers.getMapper(AppUserMapper.class);

    AppAvailableCourseMapper other = Mappers.getMapper(AppAvailableCourseMapper.class);

    AppCourseMapper appCoursemap = Mappers.getMapper(AppCourseMapper.class);

    AppPostMapper othMapper = Mappers.getMapper(AppPostMapper.class);

    AppOrderMapper otheOrderMapper = Mappers.getMapper(AppOrderMapper.class);

    @Mapping(target = "courses", ignore = true)
    @Mapping(target = "availableCourses", ignore = true)
    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "userposts", ignore = true)
    AppUserDTO toDto(AppUser s);

    @Mapping(target = "courses", ignore = true)
    @Mapping(target = "availableCourses", ignore = true)
    @Mapping(target = "removeCourses", ignore = true)
    @Mapping(target = "removeAvailableCourses", ignore = true)
    @Mapping(source = "orders", target = "orders")
    @Mapping(target = "removeOrders", ignore = true)
    @Mapping(source = "userposts", target = "posts")
    @Mapping(target = "removePosts", ignore = true)
    AppUser toEntity(AppUserDTO appUserDTO);

    @Named("toOrderDto")
    @Mappings(
        {
            @Mapping(target = "orders", source = "orders", qualifiedByName = "mapToUserOrderSet"),
            @Mapping(target = "courses", ignore = true),
            @Mapping(target = "availableCourses", ignore = true),
            @Mapping(target = "userposts", ignore = true),
        }
    )
    AppUserDTO toOrderDto(AppUser s);

    @Named("mapToUserOrderSet")
    @BeanMapping(ignoreByDefault = true)
    Set<AppOrderDTO> mapToUserOrderSet(Set<AppOrder> appOrderItem);

    @Named("appCourseId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppCourseDTO toDtoAppCourseId(AppCourse appCourse);

    @Named("appAvailableCourseId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppAvailableCourseDTO toDtoAppAvailableCourseId(AppAvailableCourse appAvailableCourse);

    @Named("toUserPostDTO")
    @Mappings(
        {
            @Mapping(target = "userposts", source = "posts", qualifiedByName = "mapToUserSet"),
            @Mapping(target = "orders", ignore = true),
            @Mapping(target = "courses", ignore = true),
            @Mapping(target = "availableCourses", ignore = true),
        }
    )
    AppUserDTO toUserPostDTO(AppUser appUser);

    @Named("mapToUserSet")
    static Set<AppPostDTO> mapToUserSet(Set<AppPost> appPost) {
        return appPost
            .stream()
            .map(userposts -> {
                if (userposts == null) {
                    return null;
                }
                userposts.setUser(null);

                return AppPostMapper.INSTANCE.toPOSTDTO(userposts);
            })
            .collect(Collectors.toSet());
    }

    @Named("appCourseIdSet")
    default Set<AppCourseDTO> toDtoAppCourseIdSet(Set<AppCourse> appCourse) {
        if (appCourse == null) {
            return null;
        }
        return appCourse.stream().map(this::toDtoAppCourseId).collect(Collectors.toSet());
    }

    @Named("appAvailableCourseIdSet")
    default Set<AppAvailableCourseDTO> toDtoAppAvailableCourseIdSet(Set<AppAvailableCourse> appAvailableCourse) {
        if (appAvailableCourse == null) {
            return null;
        }

        return appAvailableCourse.stream().map(this::toDtoAppAvailableCourseId).collect(Collectors.toSet());
    }

    @Named("toAppUserDTO")
    @Mappings(
        {
            @Mapping(target = "courses", source = "courses", qualifiedByName = "mapToAppCourseSet"),
            @Mapping(target = "availableCourses", source = "availableCourses", qualifiedByName = "mapToAppAvailableCourseSet"),
            @Mapping(target = "orders", ignore = true),
            @Mapping(target = "userposts", ignore = true),
        }
    )
    AppUserDTO toAppUserDTO(AppUser appUser);

    @Named("mapToAppCourseSet")
    static Set<AppCourseDTO> mapToAppCourseSet(Set<AppCourse> appCourses) {
        return appCourses
            .stream()
            .map(appCourse -> {
                if (appCourse == null) {
                    return null;
                }
                return AppCourseMapper.INSTANCE.toCourseDTO(appCourse);
            })
            .collect(Collectors.toSet());
    }

    @Named("mapToAppAvailableCourseSet")
    static Set<AppAvailableCourseDTO> mapToAppAvailableCourseSet(Set<AppAvailableCourse> appAvailableCourses) {
        return appAvailableCourses
            .stream()
            .map(appAvailableCourse -> {
                if (appAvailableCourse == null) {
                    return null;
                }
                return AppAvailableCourseMapper.INSTANCE.toAvaiCourseDTO(appAvailableCourse);
            })
            .collect(Collectors.toSet());
    }

    @Named("toAppUsershortDTO")
    @Mappings(
        {
            @Mapping(target = "courses", source = "courses", qualifiedByName = "mapToAppshortCourseSet"),
            @Mapping(target = "availableCourses", source = "availableCourses", qualifiedByName = "mapToAppAvailableCourseshortSet"),
            @Mapping(target = "orders", ignore = true),
            @Mapping(target = "userposts", ignore = true),
        }
    )
    AppUserDTO toAppUsershortDTO(AppUser appUser);

    @Named("mapToAppshortCourseSet")
    static Set<AppCourseDTO> mapToAppshortCourseSet(Set<AppCourse> appCourses) {
        return appCourses
            .stream()
            .map(appCourse -> {
                if (appCourse == null) {
                    return null;
                }
                return AppCourseMapper.INSTANCE.toDto(appCourse);
            })
            .collect(Collectors.toSet());
    }

    @Named("mapToAppAvailableCourseshortSet")
    static Set<AppAvailableCourseDTO> mapToAppAvailableCourseshortSet(Set<AppAvailableCourse> appAvailableCourses) {
        return appAvailableCourses
            .stream()
            .map(appAvailableCourse -> {
                if (appAvailableCourse == null) {
                    return null;
                }
                return AppAvailableCourseMapper.INSTANCE.toDto(appAvailableCourse);
            })
            .collect(Collectors.toSet());
    }
}
