package com.auth0.rainbow.service.mapper;

import com.auth0.rainbow.domain.AppAvailableCourse;
import com.auth0.rainbow.domain.AppCourse;
import com.auth0.rainbow.service.dto.AppAvailableCourseDTO;
import com.auth0.rainbow.service.dto.AppCourseDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AppAvailableCourse} and its DTO {@link AppAvailableCourseDTO}.
 */
@Mapper(componentModel = "spring")
public interface AppAvailableCourseMapper extends EntityMapper<AppAvailableCourseDTO, AppAvailableCourse> {
    @Mapping(target = "courses", source = "courses", qualifiedByName = "appCourseId")
    AppAvailableCourseDTO toDto(AppAvailableCourse s);

    @Named("appCourseId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppCourseDTO toDtoAppCourseId(AppCourse appCourse);
}
