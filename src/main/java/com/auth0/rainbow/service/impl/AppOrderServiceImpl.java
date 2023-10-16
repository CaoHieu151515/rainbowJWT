package com.auth0.rainbow.service.impl;

import com.auth0.rainbow.domain.AppOrder;
import com.auth0.rainbow.repository.AppOrderRepository;
import com.auth0.rainbow.service.AppOrderService;
import com.auth0.rainbow.service.dto.AppOrderDTO;
import com.auth0.rainbow.service.mapper.AppOrderMapper;
import java.util.Optional;
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

    public AppOrderServiceImpl(AppOrderRepository appOrderRepository, AppOrderMapper appOrderMapper) {
        this.appOrderRepository = appOrderRepository;
        this.appOrderMapper = appOrderMapper;
    }

    @Override
    public AppOrderDTO save(AppOrderDTO appOrderDTO) {
        log.debug("Request to save AppOrder : {}", appOrderDTO);
        AppOrder appOrder = appOrderMapper.toEntity(appOrderDTO);
        appOrder = appOrderRepository.save(appOrder);
        return appOrderMapper.toDto(appOrder);
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
}
