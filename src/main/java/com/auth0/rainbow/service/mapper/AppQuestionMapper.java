package com.auth0.rainbow.service.mapper;

import com.auth0.rainbow.domain.AppLesson;
import com.auth0.rainbow.domain.AppQuestion;
import com.auth0.rainbow.service.dto.AppLessonDTO;
import com.auth0.rainbow.service.dto.AppQuestionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AppQuestion} and its DTO {@link AppQuestionDTO}.
 */
@Mapper(componentModel = "spring")
public interface AppQuestionMapper extends EntityMapper<AppQuestionDTO, AppQuestion> {
    @Mapping(target = "lesson", source = "lesson", qualifiedByName = "appLessonId")
    AppQuestionDTO toDto(AppQuestion s);

    @Named("appLessonId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppLessonDTO toDtoAppLessonId(AppLesson appLesson);
}
