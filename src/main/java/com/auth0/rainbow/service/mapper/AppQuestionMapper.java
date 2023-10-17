package com.auth0.rainbow.service.mapper;

import com.auth0.rainbow.domain.AppCourse;
import com.auth0.rainbow.domain.AppLesson;
import com.auth0.rainbow.domain.AppMultipleChoiceAnswer;
import com.auth0.rainbow.domain.AppQuestion;
import com.auth0.rainbow.service.dto.AppCourseDTO;
import com.auth0.rainbow.service.dto.AppLessonDTO;
import com.auth0.rainbow.service.dto.AppMultipleChoiceAnswerDTO;
import com.auth0.rainbow.service.dto.AppQuestionDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for the entity {@link AppQuestion} and its DTO {@link AppQuestionDTO}.
 */
@Mapper(componentModel = "spring")
public interface AppQuestionMapper extends EntityMapper<AppQuestionDTO, AppQuestion> {
    AppQuestionMapper INSTANCE = Mappers.getMapper(AppQuestionMapper.class);
    AppMultipleChoiceAnswerMapper oMultipleChoiceAnswerMapper = Mappers.getMapper(AppMultipleChoiceAnswerMapper.class);

    AppQuestionDTO toDto(AppQuestion s);

    @Named("toChoiceDTO")
    @Mappings({ @Mapping(target = "multiChoice", source = "questions", qualifiedByName = "mapToMultiChoiceSet") })
    AppQuestionDTO toChoiceDTO(AppQuestion appQuestion);

    @Named("mapToMultiChoiceSet")
    static Set<AppMultipleChoiceAnswerDTO> mapToMultiChoiceSet(Set<AppMultipleChoiceAnswer> multi) {
        return multi
            .stream()
            .map(multiChoice -> {
                if (multiChoice == null) {
                    return null;
                }
                multiChoice.setQuestion(null);
                return AppMultipleChoiceAnswerMapper.INSTANCE.toDto(multiChoice);
            })
            .collect(Collectors.toSet());
    }
}
