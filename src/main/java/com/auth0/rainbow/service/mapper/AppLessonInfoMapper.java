package com.auth0.rainbow.service.mapper;

import com.auth0.rainbow.domain.AppLessonInfo;
import com.auth0.rainbow.domain.AppLessonVideo;
import com.auth0.rainbow.service.dto.AppLessonInfoDTO;
import com.auth0.rainbow.service.dto.AppLessonVideoDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for the entity {@link AppLessonInfo} and its DTO {@link AppLessonInfoDTO}.
 */
@Mapper(componentModel = "spring")
public interface AppLessonInfoMapper extends EntityMapper<AppLessonInfoDTO, AppLessonInfo> {
    AppLessonInfoMapper INSTANCE = Mappers.getMapper(AppLessonInfoMapper.class);
    AppLessonVideoMapper othAppLessonVideoMapper = Mappers.getMapper(AppLessonVideoMapper.class);

    AppLessonInfoDTO toDto(AppLessonInfo s);

    @Named("toVideoDTO")
    @Mappings({ @Mapping(target = "lessonvideo", source = "videos", qualifiedByName = "mapToVideoSet") })
    AppLessonInfoDTO toVideoDTO(AppLessonInfo appLessonInfo);

    @Named("mapToVideoSet")
    static Set<AppLessonVideoDTO> mapToVideoSet(Set<AppLessonVideo> video) {
        return video
            .stream()
            .map(lessonvideo -> {
                if (lessonvideo == null) {
                    return null;
                }
                lessonvideo.setLessonInfo(null);
                return AppLessonVideoMapper.INSTANCE.toDto(lessonvideo);
            })
            .collect(Collectors.toSet());
    }
}
