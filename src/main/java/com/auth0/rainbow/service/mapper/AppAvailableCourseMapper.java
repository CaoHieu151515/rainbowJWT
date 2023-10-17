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
 * Mapper for the entity {@link AppAvailableCourse} and its DTO {@link AppAvailableCourseDTO}.
 */
@Mapper(componentModel = "spring")
public interface AppAvailableCourseMapper extends EntityMapper<AppAvailableCourseDTO, AppAvailableCourse> {
    AppAvailableCourseMapper INSTANCE = Mappers.getMapper(AppAvailableCourseMapper.class);

    AppCourseMapper othMapper = Mappers.getMapper(AppCourseMapper.class);

    @Mapping(target = "courses", source = "courses", qualifiedByName = "appCourseId")
    AppAvailableCourseDTO toDto(AppAvailableCourse s);

    @Named("appCourseId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppCourseDTO toDtoAppCourseId(AppCourse appCourse);

    @Named("toAvaiCourseDTO")
    @Mappings({ @Mapping(target = "courses", source = "courses", qualifiedByName = "mapToAppCourseSet") })
    AppAvailableCourseDTO toAvaiCourseDTO(AppAvailableCourse appAvailableCourse);

    @Named("mapToAppCourseSet")
    static AppCourseDTO mapToAppCourseSet(AppCourse appCourses) {
        if (appCourses == null) {
            return null;
        }
        return AppCourseMapper.INSTANCE.toCourseDTO(appCourses);
    }
}
