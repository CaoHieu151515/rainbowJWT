package com.auth0.rainbow.service.mapper;

import com.auth0.rainbow.domain.AppCourse;
import com.auth0.rainbow.service.dto.AppCourseDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AppCourse} and its DTO {@link AppCourseDTO}.
 */
@Mapper(componentModel = "spring")
public interface AppCourseMapper extends EntityMapper<AppCourseDTO, AppCourse> {}
