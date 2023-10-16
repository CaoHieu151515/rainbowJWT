package com.auth0.rainbow.service.mapper;

import com.auth0.rainbow.domain.AppLesson;
import com.auth0.rainbow.domain.AppLessonInfo;
import com.auth0.rainbow.service.dto.AppLessonDTO;
import com.auth0.rainbow.service.dto.AppLessonInfoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AppLessonInfo} and its DTO {@link AppLessonInfoDTO}.
 */
@Mapper(componentModel = "spring")
public interface AppLessonInfoMapper extends EntityMapper<AppLessonInfoDTO, AppLessonInfo> {
    @Mapping(target = "lesson", source = "lesson", qualifiedByName = "appLessonId")
    AppLessonInfoDTO toDto(AppLessonInfo s);

    @Named("appLessonId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppLessonDTO toDtoAppLessonId(AppLesson appLesson);
}
