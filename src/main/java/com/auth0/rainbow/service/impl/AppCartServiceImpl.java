package com.auth0.rainbow.service.impl;

import com.auth0.rainbow.domain.AppCart;
import com.auth0.rainbow.repository.AppCartRepository;
import com.auth0.rainbow.service.AppCartService;
import com.auth0.rainbow.service.dto.AppCartDTO;
import com.auth0.rainbow.service.mapper.AppCartMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AppCart}.
 */
@Service
@Transactional
public class AppCartServiceImpl implements AppCartService {

    private final Logger log = LoggerFactory.getLogger(AppCartServiceImpl.class);

    private final AppCartRepository appCartRepository;

    private final AppCartMapper appCartMapper;

    public AppCartServiceImpl(AppCartRepository appCartRepository, AppCartMapper appCartMapper) {
        this.appCartRepository = appCartRepository;
        this.appCartMapper = appCartMapper;
    }

    @Override
    public AppCartDTO save(AppCartDTO appCartDTO) {
        log.debug("Request to save AppCart : {}", appCartDTO);
        AppCart appCart = appCartMapper.toEntity(appCartDTO);
        appCart = appCartRepository.save(appCart);
        return appCartMapper.toDto(appCart);
    }

    @Override
    public AppCartDTO update(AppCartDTO appCartDTO) {
        log.debug("Request to update AppCart : {}", appCartDTO);
        AppCart appCart = appCartMapper.toEntity(appCartDTO);
        appCart = appCartRepository.save(appCart);
        return appCartMapper.toDto(appCart);
    }

    @Override
    public Optional<AppCartDTO> partialUpdate(AppCartDTO appCartDTO) {
        log.debug("Request to partially update AppCart : {}", appCartDTO);

        return appCartRepository
            .findById(appCartDTO.getId())
            .map(existingAppCart -> {
                appCartMapper.partialUpdate(existingAppCart, appCartDTO);

                return existingAppCart;
            })
            .map(appCartRepository::save)
            .map(appCartMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppCartDTO> findAll() {
        log.debug("Request to get all AppCarts");
        return appCartRepository.findAll().stream().map(appCartMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    public Page<AppCartDTO> findAllWithEagerRelationships(Pageable pageable) {
        return appCartRepository.findAllWithEagerRelationships(pageable).map(appCartMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AppCartDTO> findOne(Long id) {
        log.debug("Request to get AppCart : {}", id);
        return appCartRepository.findOneWithEagerRelationships(id).map(appCartMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AppCart : {}", id);
        appCartRepository.deleteById(id);
    }
}
