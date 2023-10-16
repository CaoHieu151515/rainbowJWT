package com.auth0.rainbow.service.mapper;

import com.auth0.rainbow.domain.AppAvailableCourse;
import com.auth0.rainbow.domain.AppCourse;
import com.auth0.rainbow.domain.AppUser;
import com.auth0.rainbow.service.dto.AppAvailableCourseDTO;
import com.auth0.rainbow.service.dto.AppCourseDTO;
import com.auth0.rainbow.service.dto.AppUserDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AppUser} and its DTO {@link AppUserDTO}.
 */
@Mapper(componentModel = "spring")
public interface AppUserMapper extends EntityMapper<AppUserDTO, AppUser> {
    @Mapping(target = "courses", source = "courses", qualifiedByName = "appCourseIdSet")
    @Mapping(target = "availableCourses", source = "availableCourses", qualifiedByName = "appAvailableCourseIdSet")
    AppUserDTO toDto(AppUser s);

    @Mapping(target = "removeCourses", ignore = true)
    @Mapping(target = "removeAvailableCourses", ignore = true)
    AppUser toEntity(AppUserDTO appUserDTO);

    @Named("appCourseId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppCourseDTO toDtoAppCourseId(AppCourse appCourse);

    @Named("appCourseIdSet")
    default Set<AppCourseDTO> toDtoAppCourseIdSet(Set<AppCourse> appCourse) {
        return appCourse.stream().map(this::toDtoAppCourseId).collect(Collectors.toSet());
    }

    @Named("appAvailableCourseId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppAvailableCourseDTO toDtoAppAvailableCourseId(AppAvailableCourse appAvailableCourse);

    @Named("appAvailableCourseIdSet")
    default Set<AppAvailableCourseDTO> toDtoAppAvailableCourseIdSet(Set<AppAvailableCourse> appAvailableCourse) {
        return appAvailableCourse.stream().map(this::toDtoAppAvailableCourseId).collect(Collectors.toSet());
    }
}
