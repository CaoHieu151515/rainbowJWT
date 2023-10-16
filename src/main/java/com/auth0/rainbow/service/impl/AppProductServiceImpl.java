package com.auth0.rainbow.service.impl;

import com.auth0.rainbow.domain.AppProduct;
import com.auth0.rainbow.repository.AppProductRepository;
import com.auth0.rainbow.service.AppProductService;
import com.auth0.rainbow.service.dto.AppProductDTO;
import com.auth0.rainbow.service.mapper.AppProductMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AppProduct}.
 */
@Service
@Transactional
public class AppProductServiceImpl implements AppProductService {

    private final Logger log = LoggerFactory.getLogger(AppProductServiceImpl.class);

    private final AppProductRepository appProductRepository;

    private final AppProductMapper appProductMapper;

    public AppProductServiceImpl(AppProductRepository appProductRepository, AppProductMapper appProductMapper) {
        this.appProductRepository = appProductRepository;
        this.appProductMapper = appProductMapper;
    }

    @Override
    public AppProductDTO save(AppProductDTO appProductDTO) {
        log.debug("Request to save AppProduct : {}", appProductDTO);
        AppProduct appProduct = appProductMapper.toEntity(appProductDTO);
        appProduct = appProductRepository.save(appProduct);
        return appProductMapper.toDto(appProduct);
    }

    @Override
    public AppProductDTO update(AppProductDTO appProductDTO) {
        log.debug("Request to update AppProduct : {}", appProductDTO);
        AppProduct appProduct = appProductMapper.toEntity(appProductDTO);
        appProduct = appProductRepository.save(appProduct);
        return appProductMapper.toDto(appProduct);
    }

    @Override
    public Optional<AppProductDTO> partialUpdate(AppProductDTO appProductDTO) {
        log.debug("Request to partially update AppProduct : {}", appProductDTO);

        return appProductRepository
            .findById(appProductDTO.getId())
            .map(existingAppProduct -> {
                appProductMapper.partialUpdate(existingAppProduct, appProductDTO);

                return existingAppProduct;
            })
            .map(appProductRepository::save)
            .map(appProductMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AppProductDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AppProducts");
        return appProductRepository.findAll(pageable).map(appProductMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AppProductDTO> findOne(Long id) {
        log.debug("Request to get AppProduct : {}", id);
        return appProductRepository.findById(id).map(appProductMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AppProduct : {}", id);
        appProductRepository.deleteById(id);
    }
}
