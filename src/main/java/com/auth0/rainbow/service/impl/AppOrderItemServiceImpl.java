package com.auth0.rainbow.service.impl;

import com.auth0.rainbow.domain.AppOrderItem;
import com.auth0.rainbow.repository.AppOrderItemRepository;
import com.auth0.rainbow.service.AppOrderItemService;
import com.auth0.rainbow.service.dto.AppOrderItemDTO;
import com.auth0.rainbow.service.mapper.AppOrderItemMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AppOrderItem}.
 */
@Service
@Transactional
public class AppOrderItemServiceImpl implements AppOrderItemService {

    private final Logger log = LoggerFactory.getLogger(AppOrderItemServiceImpl.class);

    private final AppOrderItemRepository appOrderItemRepository;

    private final AppOrderItemMapper appOrderItemMapper;

    public AppOrderItemServiceImpl(AppOrderItemRepository appOrderItemRepository, AppOrderItemMapper appOrderItemMapper) {
        this.appOrderItemRepository = appOrderItemRepository;
        this.appOrderItemMapper = appOrderItemMapper;
    }

    @Override
    public AppOrderItemDTO save(AppOrderItemDTO appOrderItemDTO) {
        log.debug("Request to save AppOrderItem : {}", appOrderItemDTO);
        AppOrderItem appOrderItem = appOrderItemMapper.toEntity(appOrderItemDTO);
        appOrderItem = appOrderItemRepository.save(appOrderItem);
        return appOrderItemMapper.toDto(appOrderItem);
    }

    @Override
    public AppOrderItemDTO update(AppOrderItemDTO appOrderItemDTO) {
        log.debug("Request to update AppOrderItem : {}", appOrderItemDTO);
        AppOrderItem appOrderItem = appOrderItemMapper.toEntity(appOrderItemDTO);
        appOrderItem = appOrderItemRepository.save(appOrderItem);
        return appOrderItemMapper.toDto(appOrderItem);
    }

    @Override
    public Optional<AppOrderItemDTO> partialUpdate(AppOrderItemDTO appOrderItemDTO) {
        log.debug("Request to partially update AppOrderItem : {}", appOrderItemDTO);

        return appOrderItemRepository
            .findById(appOrderItemDTO.getId())
            .map(existingAppOrderItem -> {
                appOrderItemMapper.partialUpdate(existingAppOrderItem, appOrderItemDTO);

                return existingAppOrderItem;
            })
            .map(appOrderItemRepository::save)
            .map(appOrderItemMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppOrderItemDTO> findAll() {
        log.debug("Request to get all AppOrderItems");
        return appOrderItemRepository.findAll().stream().map(appOrderItemMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AppOrderItemDTO> findOne(Long id) {
        log.debug("Request to get AppOrderItem : {}", id);
        return appOrderItemRepository.findById(id).map(appOrderItemMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AppOrderItem : {}", id);
        appOrderItemRepository.deleteById(id);
    }
}
