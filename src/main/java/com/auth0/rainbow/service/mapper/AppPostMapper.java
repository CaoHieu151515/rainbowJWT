package com.auth0.rainbow.service.mapper;

import com.auth0.rainbow.domain.AppPost;
import com.auth0.rainbow.domain.AppPostImage;
import com.auth0.rainbow.domain.AppQuestion;
import com.auth0.rainbow.domain.AppUser;
import com.auth0.rainbow.service.dto.AppPostDTO;
import com.auth0.rainbow.service.dto.AppPostImageDTO;
import com.auth0.rainbow.service.dto.AppQuestionDTO;
import com.auth0.rainbow.service.dto.AppUserDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for the entity {@link AppPost} and its DTO {@link AppPostDTO}.
 */

@Mapper(componentModel = "spring")
public interface AppPostMapper extends EntityMapper<AppPostDTO, AppPost> {
    AppPostMapper INSTANCE = Mappers.getMapper(AppPostMapper.class);
    AppUserMapper other = Mappers.getMapper(AppUserMapper.class);

    @Mapping(target = "user", source = "user", qualifiedByName = "appUserId")
    AppPostDTO toDto(AppPost s);

    @Mapping(target = "imageUrl", source = "imageUrl")
    AppPostImageDTO toDtoImage(AppPostImage appPostImage);

    @Named("appUserId")
    @BeanMapping(ignoreByDefault = false)
    @Mapping(target = "id", source = "id")
    AppUserDTO toDtoAppUserId(AppUser appUser);

    @Named("toPOSTDTO")
    @Mappings(
        {
            @Mapping(target = "user", source = "user", qualifiedByName = "mapToUser"),
            @Mapping(target = "images", source = "images", qualifiedByName = "mapToimages"),
        }
    )
    AppPostDTO toPOSTDTO(AppPost AppPost);

    @Named("mapToUser")
    static AppUserDTO mapToUser(AppUser appUser) {
        if (appUser == null) {
            return null;
        }
        AppUserDTO appUserDTO = new AppUserDTO();
        appUserDTO.setId(appUser.getId());
        appUserDTO.setName(appUser.getName());
        appUserDTO.setGender(appUser.getGender());
        appUserDTO.setDob(appUser.getDob());
        appUserDTO.setStatus(appUser.getStatus());
        appUserDTO.setAvailableCourses(null);
        appUserDTO.setCourses(null);

        return appUserDTO;
    }

    @Named("mapToimages")
    static Set<AppPostImageDTO> mapToimages(Set<AppPostImage> appPostImages) {
        // Thực hiện ánh xạ từ danh sách AppPostImage sang Set AppPostImageDTO
        // Ví dụ:
        return appPostImages
            .stream()
            .map(appPostImage -> {
                AppPostImageDTO appPostImageDTO = new AppPostImageDTO();
                appPostImageDTO.setId(appPostImage.getId());
                appPostImageDTO.setImageUrl(appPostImage.getImageUrl());
                // Thêm các trường khác
                return appPostImageDTO;
            })
            .collect(Collectors.toSet());
    }
}
