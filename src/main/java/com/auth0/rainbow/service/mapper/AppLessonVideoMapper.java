package com.auth0.rainbow.service.mapper;

import com.auth0.rainbow.domain.AppLessonInfo;
import com.auth0.rainbow.domain.AppLessonVideo;
import com.auth0.rainbow.service.dto.AppLessonInfoDTO;
import com.auth0.rainbow.service.dto.AppLessonVideoDTO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for the entity {@link AppLessonVideo} and its DTO {@link AppLessonVideoDTO}.
 */
@Mapper(componentModel = "spring")
public interface AppLessonVideoMapper extends EntityMapper<AppLessonVideoDTO, AppLessonVideo> {
    AppLessonVideoMapper INSTANCE = Mappers.getMapper(AppLessonVideoMapper.class);

    @Mapping(target = "lessonInfo", source = "lessonInfo", qualifiedByName = "appLessonInfoId")
    AppLessonVideoDTO toDto(AppLessonVideo s);

    @Named("appLessonInfoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppLessonInfoDTO toDtoAppLessonInfoId(AppLessonInfo appLessonInfo);
}
