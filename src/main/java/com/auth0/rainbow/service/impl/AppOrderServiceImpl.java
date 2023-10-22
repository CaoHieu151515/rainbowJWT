package com.auth0.rainbow.service.impl;

import com.auth0.rainbow.domain.AppOrder;
import com.auth0.rainbow.domain.AppOrderItem;
import com.auth0.rainbow.domain.AppProduct;
import com.auth0.rainbow.domain.AppUser;
import com.auth0.rainbow.domain.LinkAccountUser;
import com.auth0.rainbow.domain.User;
import com.auth0.rainbow.repository.AppOrderItemRepository;
import com.auth0.rainbow.repository.AppOrderRepository;
import com.auth0.rainbow.repository.LinkAccountUserRepository;
import com.auth0.rainbow.service.AppOrderService;
import com.auth0.rainbow.service.UserService;
import com.auth0.rainbow.service.dto.AppOrderDTO;
import com.auth0.rainbow.service.dto.AppOrderItemDTO;
import com.auth0.rainbow.service.mapper.AppOrderMapper;
import com.auth0.rainbow.service.mapper.AppProductMapper;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AppOrder}.
 */
@Service
@Transactional
public class AppOrderServiceImpl implements AppOrderService {

    private final Logger log = LoggerFactory.getLogger(AppOrderServiceImpl.class);

    private final AppOrderRepository appOrderRepository;

    private final AppOrderMapper appOrderMapper;

    private final UserService userService;

    private final LinkAccountUserRepository linkAccountUserRepository;

    private final AppOrderItemRepository appOrderItemRepository;

    public AppOrderServiceImpl(
        AppOrderRepository appOrderRepository,
        AppOrderMapper appOrderMapper,
        UserService userService,
        LinkAccountUserRepository linkAccountUserRepository,
        AppOrderItemRepository appOrderItemRepository
    ) {
        this.appOrderRepository = appOrderRepository;
        this.appOrderMapper = appOrderMapper;
        this.userService = userService;
        this.linkAccountUserRepository = linkAccountUserRepository;
        this.appOrderItemRepository = appOrderItemRepository;
    }

    @Override
    public AppOrderDTO save(AppOrderDTO appOrderDTO) {
        log.debug("Request to save AppOrder : {}", appOrderDTO);
        AppOrder appOrder = appOrderMapper.toEntity(appOrderDTO);

        appOrder.setUser(GetCurrentAppUser());
        appOrder.setOrderItems(createOrderItem(appOrderDTO.getorderItemss()));

        // List<AppOrder> appOrders = appOrderRepository.findAll();
        // Long randomNumber = RandomNumberGenerator.generateUniqueRandomNumber(appOrders);

        // appOrder.setPaymentID(randomNumber);
        appOrder = appOrderRepository.save(appOrder);
        return appOrderMapper.toDto(appOrder);
    }

    private Set<AppOrderItem> createOrderItem(Set<AppOrderItemDTO> item) {
        Set<AppOrderItem> itemEntities = new HashSet<>();
        for (AppOrderItemDTO itemDTO : item) {
            AppOrderItem itemEntity = new AppOrderItem();
            itemEntity.setNote(itemDTO.getNote());
            itemEntity.setPrice(itemDTO.getPrice());
            AppProduct product = AppProductMapper.INSTANCE.toEntity(itemDTO.getProduct());
            itemEntity.setProduct(product);
            itemEntity.setQuantity(itemDTO.getQuantity());
            itemEntity.setUnit(itemDTO.getUnit());
            appOrderItemRepository.save(itemEntity);
            itemEntities.add(itemEntity);
        }
        return itemEntities;
    }

    @Override
    public AppOrderDTO update(AppOrderDTO appOrderDTO) {
        log.debug("Request to update AppOrder : {}", appOrderDTO);
        AppOrder appOrder = appOrderMapper.toEntity(appOrderDTO);
        appOrder = appOrderRepository.save(appOrder);
        return appOrderMapper.toDto(appOrder);
    }

    @Override
    public Optional<AppOrderDTO> partialUpdate(AppOrderDTO appOrderDTO) {
        log.debug("Request to partially update AppOrder : {}", appOrderDTO);

        return appOrderRepository
            .findById(appOrderDTO.getId())
            .map(existingAppOrder -> {
                appOrderMapper.partialUpdate(existingAppOrder, appOrderDTO);

                return existingAppOrder;
            })
            .map(appOrderRepository::save)
            .map(appOrderMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AppOrderDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AppOrders");
        return appOrderRepository.findAll(pageable).map(appOrderMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AppOrderDTO> findOne(Long id) {
        log.debug("Request to get AppOrder : {}", id);
        return appOrderRepository.findById(id).map(appOrderMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AppOrder : {}", id);
        appOrderRepository.deleteById(id);
    }

    private AppUser GetCurrentAppUser() {
        Optional<User> optionalUser = userService.getUserWithAuthorities();
        User currentUser = optionalUser.orElseThrow(() -> new RuntimeException("User not found"));

        LinkAccountUser LinkAc = linkAccountUserRepository.findByUserId(currentUser.getId());

        AppUser appUser = LinkAc.getAppUser();
        log.debug("Current AppUser", appUser);
        return appUser;
    }
}
