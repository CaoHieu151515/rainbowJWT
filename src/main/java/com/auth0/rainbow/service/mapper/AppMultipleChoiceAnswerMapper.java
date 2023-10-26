package com.auth0.rainbow.service.mapper;

import com.auth0.rainbow.domain.AppCourse;
import com.auth0.rainbow.domain.AppMultipleChoiceAnswer;
import com.auth0.rainbow.service.dto.AppCourseDTO;
import com.auth0.rainbow.service.dto.AppMultipleChoiceAnswerDTO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for the entity {@link AppMultipleChoiceAnswer} and its DTO {@link AppMultipleChoiceAnswerDTO}.
 */
@Mapper(componentModel = "spring")
public interface AppMultipleChoiceAnswerMapper extends EntityMapper<AppMultipleChoiceAnswerDTO, AppMultipleChoiceAnswer> {
    AppMultipleChoiceAnswerMapper INSTANCE = Mappers.getMapper(AppMultipleChoiceAnswerMapper.class);

    @Mapping(target = "question", ignore = true)
    AppMultipleChoiceAnswerDTO toDto(AppMultipleChoiceAnswer s);
}
