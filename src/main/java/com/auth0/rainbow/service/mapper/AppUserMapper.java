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
import org.mapstruct.factory.Mappers;

/**
 * Mapper for the entity {@link AppUser} and its DTO {@link AppUserDTO}.
 */
@Mapper(componentModel = "spring")
public interface AppUserMapper extends EntityMapper<AppUserDTO, AppUser> {
    AppUserMapper INSTANCE = Mappers.getMapper(AppUserMapper.class);

    AppAvailableCourseMapper other = Mappers.getMapper(AppAvailableCourseMapper.class);

    AppCourseMapper appCoursemap = Mappers.getMapper(AppCourseMapper.class);

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

    @Named("toAppUserDTO")
    @Mappings(
        {
            // Map các trường khác ở đây
            @Mapping(target = "courses", source = "courses", qualifiedByName = "mapToAppCourseSet"),
            @Mapping(target = "availableCourses", source = "availableCourses", qualifiedByName = "mapToAppAvailableCourseSet"),
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
                return AppCourseMapper.INSTANCE.toDto(appCourse);
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

    @Named("appAvailableCourseId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppAvailableCourseDTO toDtoAppAvailableCourseId(AppAvailableCourse appAvailableCourse);

    @Named("appAvailableCourseIdSet")
    default Set<AppAvailableCourseDTO> toDtoAppAvailableCourseIdSet(Set<AppAvailableCourse> appAvailableCourse) {
        return appAvailableCourse.stream().map(this::toDtoAppAvailableCourseId).collect(Collectors.toSet());
    }
}
