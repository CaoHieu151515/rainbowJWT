package com.auth0.rainbow.service.mapper;

import com.auth0.rainbow.domain.AppCourse;
import com.auth0.rainbow.domain.AppLesson;
import com.auth0.rainbow.service.dto.AppCourseDTO;
import com.auth0.rainbow.service.dto.AppLessonDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AppLesson} and its DTO {@link AppLessonDTO}.
 */
@Mapper(componentModel = "spring")
public interface AppLessonMapper extends EntityMapper<AppLessonDTO, AppLesson> {
    @Mapping(target = "course", source = "course", qualifiedByName = "appCourseId")
    AppLessonDTO toDto(AppLesson s);

    @Named("appCourseId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppCourseDTO toDtoAppCourseId(AppCourse appCourse);
}
