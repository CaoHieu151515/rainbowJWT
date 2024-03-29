package com.auth0.rainbow.service.mapper;

import com.auth0.rainbow.domain.AppPost;
import com.auth0.rainbow.domain.AppPostImage;
import com.auth0.rainbow.domain.AppUser;
import com.auth0.rainbow.service.dto.AppPostDTO;
import com.auth0.rainbow.service.dto.AppPostImageDTO;
import com.auth0.rainbow.service.dto.AppUserDTO;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for the entity {@link AppPost} and its DTO {@link AppPostDTO}.
 */

@Mapper(componentModel = "spring")
public interface AppPostMapper extends EntityMapper<AppPostDTO, AppPost> {
    AppPostMapper INSTANCE = Mappers.getMapper(AppPostMapper.class);
    AppUserMapper other = Mappers.getMapper(AppUserMapper.class);
    AppPostImageMapper ortherAppProductImageMapper = Mappers.getMapper(AppPostImageMapper.class);

    @Mapping(target = "user", source = "user", qualifiedByName = "appUserId")
    @Mapping(target = "images", source = "images", qualifiedByName = "mapToimages")
    AppPostDTO toDto(AppPost s);

    @Named("appUserId")
    @BeanMapping(ignoreByDefault = false)
    @Mapping(target = "id", source = "id")
    AppUserDTO toDtoAppUserId(AppUser appUser);

    @Named("toPOSTUpdateDTO")
    @Mappings(
        { @Mapping(target = "user", ignore = true), @Mapping(target = "images", source = "images", qualifiedByName = "mapToimagesUpdate") }
    )
    AppPostDTO toPOSTUpdateDTO(AppPost AppPost);

    @Named("mapToUserUpdate")
    static AppUserDTO mapToUserUpdate(AppUser appUser) {
        if (appUser == null) {
            return null;
        }

        return AppUserMapper.INSTANCE.toUserPostDTO(appUser);
    }

    @Named("mapToimagesUpdate")
    static Set<AppPostImageDTO> mapToUserUpdate(Set<AppPostImage> appPostImages) {
        if (appPostImages == null) {
            return Collections.emptySet();
        }
        return appPostImages
            .stream()
            .filter(Objects::nonNull)
            .map(appPostImage -> {
                return AppPostImageMapper.INSTANCE.toDto(appPostImage);
            })
            .collect(Collectors.toSet());
    }

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

        return AppUserMapper.INSTANCE.toDto(appUser);
    }

    @Named("mapToimages")
    static Set<AppPostImageDTO> mapToimages(Set<AppPostImage> appPostImages) {
        return appPostImages
            .stream()
            .map(appPostImage -> {
                if (appPostImage == null) {
                    return null;
                }
                appPostImage.setPost(null);
                return AppPostImageMapper.INSTANCE.toDto(appPostImage);
            })
            .collect(Collectors.toSet());
    }
    // @Named("toEntity")
    // default AppPost toEntity(AppPostDTO appPostDTO) {
    //     if (appPostDTO == null) {
    //         return null;
    //     }

    //     AppPost appPost = new AppPost();
    //     appPost.setId(appPostDTO.getId());

    //     AppUserDTO appUserDTO = appPostDTO.getUser();
    //     if (appUserDTO != null) {
    //         appPost.setUser(AppUserMapper.INSTANCE.toEntity(appUserDTO));
    //     }

    //     Set<AppPostImageDTO> appPostImageDTOs = appPostDTO.getImages();
    //     if (appPostImageDTOs != null) {
    //         Set<AppPostImage> appPostImages = appPostImageDTOs
    //             .stream()
    //             .map(AppPostImageMapper.INSTANCE::toEntity)
    //             .collect(Collectors.toSet());
    //         appPost.setImages(appPostImages);
    //     } else {
    //         appPost.setImages(new HashSet<>());
    //     }

    //     return appPost;
    // }
}
