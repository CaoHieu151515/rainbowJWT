package com.auth0.rainbow.service.mapper;

import com.auth0.rainbow.domain.AppCourse;
import com.auth0.rainbow.domain.AppLesson;
import com.auth0.rainbow.domain.AppLessonInfo;
import com.auth0.rainbow.domain.AppQuestion;
import com.auth0.rainbow.domain.AppUser;
import com.auth0.rainbow.service.dto.AppCourseDTO;
import com.auth0.rainbow.service.dto.AppLessonDTO;
import com.auth0.rainbow.service.dto.AppLessonInfoDTO;
import com.auth0.rainbow.service.dto.AppQuestionDTO;
import com.auth0.rainbow.service.dto.AppUserDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for the entity {@link AppLesson} and its DTO {@link AppLessonDTO}.
 */
@Mapper(componentModel = "spring")
public interface AppLessonMapper extends EntityMapper<AppLessonDTO, AppLesson> {
    AppLessonMapper INSTANCE = Mappers.getMapper(AppLessonMapper.class);

    AppQuestionMapper oAppQuestionMapper = Mappers.getMapper(AppQuestionMapper.class);

    AppLessonInfoMapper othInfoMapper = Mappers.getMapper(AppLessonInfoMapper.class);

    AppLessonDTO toDto(AppLesson s);

    @Named("toLessonDTO")
    @Mappings(
        {
            @Mapping(target = "appQuestion", source = "lessons", qualifiedByName = "mapToQuestionSet"),
            @Mapping(target = "appLesonInf", source = "lessonInfos", qualifiedByName = "mapToessonInfoSet"),
        }
    )
    AppLessonDTO toLessonDTO(AppLesson appLesson);

    @Named("mapToQuestionSet")
    static Set<AppQuestionDTO> mapToQuestionSet(Set<AppQuestion> appQuestions) {
        return appQuestions
            .stream()
            .map(appQuestion -> {
                if (appQuestion == null) {
                    return null;
                }

                return AppQuestionMapper.INSTANCE.toChoiceDTO(appQuestion);
            })
            .collect(Collectors.toSet());
    }

    @Named("mapToessonInfoSet")
    static Set<AppLessonInfoDTO> mapToessonInfoSet(Set<AppLessonInfo> appLessonInfo) {
        return appLessonInfo
            .stream()
            .map(appLesonInf -> {
                if (appLesonInf == null) {
                    return null;
                }

                return AppLessonInfoMapper.INSTANCE.toVideoDTO(appLesonInf);
            })
            .collect(Collectors.toSet());
    }
}
