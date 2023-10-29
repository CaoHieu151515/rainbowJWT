package com.auth0.rainbow.service.mapper;

import com.auth0.rainbow.domain.AppCart;
import com.auth0.rainbow.domain.AppProduct;
import com.auth0.rainbow.domain.AppUser;
import com.auth0.rainbow.service.dto.AppCartDTO;
import com.auth0.rainbow.service.dto.AppProductDTO;
import com.auth0.rainbow.service.dto.AppUserDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AppCart} and its DTO {@link AppCartDTO}.
 */
@Mapper(componentModel = "spring")
public interface AppCartMapper extends EntityMapper<AppCartDTO, AppCart> {
    @Mapping(target = "user", source = "user", qualifiedByName = "appUserId")
    @Mapping(target = "products", source = "products", qualifiedByName = "appProductIdSet")
    AppCartDTO toDto(AppCart s);

    // @Mapping(target = "removeProducts", ignore = true)
    // AppCart toEntity(AppCartDTO appCartDTO);

    @Named("appUserId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppUserDTO toDtoAppUserId(AppUser appUser);

    @Named("appProductId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppProductDTO toDtoAppProductId(AppProduct appProduct);

    @Named("appProductIdSet")
    default Set<AppProductDTO> toDtoAppProductIdSet(Set<AppProduct> appProduct) {
        return appProduct.stream().map(this::toDtoAppProductId).collect(Collectors.toSet());
    }
}
