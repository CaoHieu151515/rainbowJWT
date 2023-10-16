package com.auth0.rainbow.service.impl;

import com.auth0.rainbow.domain.AppCategory;
import com.auth0.rainbow.repository.AppCategoryRepository;
import com.auth0.rainbow.service.AppCategoryService;
import com.auth0.rainbow.service.dto.AppCategoryDTO;
import com.auth0.rainbow.service.mapper.AppCategoryMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AppCategory}.
 */
@Service
@Transactional
public class AppCategoryServiceImpl implements AppCategoryService {

    private final Logger log = LoggerFactory.getLogger(AppCategoryServiceImpl.class);

    private final AppCategoryRepository appCategoryRepository;

    private final AppCategoryMapper appCategoryMapper;

    public AppCategoryServiceImpl(AppCategoryRepository appCategoryRepository, AppCategoryMapper appCategoryMapper) {
        this.appCategoryRepository = appCategoryRepository;
        this.appCategoryMapper = appCategoryMapper;
    }

    @Override
    public AppCategoryDTO save(AppCategoryDTO appCategoryDTO) {
        log.debug("Request to save AppCategory : {}", appCategoryDTO);
        AppCategory appCategory = appCategoryMapper.toEntity(appCategoryDTO);
        appCategory = appCategoryRepository.save(appCategory);
        return appCategoryMapper.toDto(appCategory);
    }

    @Override
    public AppCategoryDTO update(AppCategoryDTO appCategoryDTO) {
        log.debug("Request to update AppCategory : {}", appCategoryDTO);
        AppCategory appCategory = appCategoryMapper.toEntity(appCategoryDTO);
        appCategory = appCategoryRepository.save(appCategory);
        return appCategoryMapper.toDto(appCategory);
    }

    @Override
    public Optional<AppCategoryDTO> partialUpdate(AppCategoryDTO appCategoryDTO) {
        log.debug("Request to partially update AppCategory : {}", appCategoryDTO);

        return appCategoryRepository
            .findById(appCategoryDTO.getId())
            .map(existingAppCategory -> {
                appCategoryMapper.partialUpdate(existingAppCategory, appCategoryDTO);

                return existingAppCategory;
            })
            .map(appCategoryRepository::save)
            .map(appCategoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppCategoryDTO> findAll() {
        log.debug("Request to get all AppCategories");
        return appCategoryRepository.findAll().stream().map(appCategoryMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AppCategoryDTO> findOne(Long id) {
        log.debug("Request to get AppCategory : {}", id);
        return appCategoryRepository.findById(id).map(appCategoryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AppCategory : {}", id);
        appCategoryRepository.deleteById(id);
    }
}
