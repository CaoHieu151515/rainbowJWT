package com.auth0.rainbow.service.mapper;

import com.auth0.rainbow.domain.AppProductImage;
import com.auth0.rainbow.service.dto.AppProductImageDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AppProductImage} and its DTO {@link AppProductImageDTO}.
 */
@Mapper(componentModel = "spring")
public interface AppProductImageMapper extends EntityMapper<AppProductImageDTO, AppProductImage> {}
