package com.auth0.rainbow.service.mapper;

import com.auth0.rainbow.domain.AppAvailableCourse;
import com.auth0.rainbow.domain.AppCourse;
import com.auth0.rainbow.service.dto.AppAvailableCourseDTO;
import com.auth0.rainbow.service.dto.AppCourseDTO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for the entity {@link AppCourse} and its DTO {@link AppCourseDTO}.
 */
@Mapper(componentModel = "spring")
public interface AppCourseMapper extends EntityMapper<AppCourseDTO, AppCourse> {
    AppCourseMapper INSTANCE = Mappers.getMapper(AppCourseMapper.class);

    AppCourseDTO toDto(AppCourse s);
}
