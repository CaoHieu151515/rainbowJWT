package com.auth0.rainbow.service.mapper;

import com.auth0.rainbow.domain.AppAvailableCourse;
import com.auth0.rainbow.domain.AppCourse;
import com.auth0.rainbow.domain.AppLesson;
import com.auth0.rainbow.domain.AppUser;
import com.auth0.rainbow.service.dto.AppAvailableCourseDTO;
import com.auth0.rainbow.service.dto.AppCourseDTO;
import com.auth0.rainbow.service.dto.AppLessonDTO;
import com.auth0.rainbow.service.dto.AppUserDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for the entity {@link AppCourse} and its DTO {@link AppCourseDTO}.
 */
@Mapper(componentModel = "spring")
public interface AppCourseMapper extends EntityMapper<AppCourseDTO, AppCourse> {
    AppCourseMapper INSTANCE = Mappers.getMapper(AppCourseMapper.class);
    AppLessonMapper otherAppLessonMapper = Mappers.getMapper(AppLessonMapper.class);

    AppCourseDTO toDto(AppCourse s);

    @Named("toCourseDTO")
    @Mappings({ @Mapping(target = "appLesson", source = "courses", qualifiedByName = "mapToLesonSet") })
    AppCourseDTO toCourseDTO(AppCourse appCourse);

    @Named("mapToLesonSet")
    static Set<AppLessonDTO> mapToLesonSet(Set<AppLesson> appLessons) {
        return appLessons
            .stream()
            .map(appLesson -> {
                if (appLesson == null) {
                    return null;
                }
                return AppLessonMapper.INSTANCE.toLessonDTO(appLesson);
            })
            .collect(Collectors.toSet());
    }
}
