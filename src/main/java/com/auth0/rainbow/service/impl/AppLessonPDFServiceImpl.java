package com.auth0.rainbow.service.impl;

import com.auth0.rainbow.domain.AppLessonPDF;
import com.auth0.rainbow.repository.AppLessonPDFRepository;
import com.auth0.rainbow.service.AppLessonPDFService;
import com.auth0.rainbow.service.dto.AppLessonPDFDTO;
import com.auth0.rainbow.service.mapper.AppLessonPDFMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AppLessonPDF}.
 */
@Service
@Transactional
public class AppLessonPDFServiceImpl implements AppLessonPDFService {

    private final Logger log = LoggerFactory.getLogger(AppLessonPDFServiceImpl.class);

    private final AppLessonPDFRepository appLessonPDFRepository;

    private final AppLessonPDFMapper appLessonPDFMapper;

    public AppLessonPDFServiceImpl(AppLessonPDFRepository appLessonPDFRepository, AppLessonPDFMapper appLessonPDFMapper) {
        this.appLessonPDFRepository = appLessonPDFRepository;
        this.appLessonPDFMapper = appLessonPDFMapper;
    }

    @Override
    public AppLessonPDFDTO save(AppLessonPDFDTO appLessonPDFDTO) {
        log.debug("Request to save AppLessonPDF : {}", appLessonPDFDTO);
        AppLessonPDF appLessonPDF = appLessonPDFMapper.toEntity(appLessonPDFDTO);
        appLessonPDF = appLessonPDFRepository.save(appLessonPDF);
        return appLessonPDFMapper.toDto(appLessonPDF);
    }

    @Override
    public AppLessonPDFDTO update(AppLessonPDFDTO appLessonPDFDTO) {
        log.debug("Request to update AppLessonPDF : {}", appLessonPDFDTO);
        AppLessonPDF appLessonPDF = appLessonPDFMapper.toEntity(appLessonPDFDTO);
        appLessonPDF = appLessonPDFRepository.save(appLessonPDF);
        return appLessonPDFMapper.toDto(appLessonPDF);
    }

    @Override
    public Optional<AppLessonPDFDTO> partialUpdate(AppLessonPDFDTO appLessonPDFDTO) {
        log.debug("Request to partially update AppLessonPDF : {}", appLessonPDFDTO);

        return appLessonPDFRepository
            .findById(appLessonPDFDTO.getId())
            .map(existingAppLessonPDF -> {
                appLessonPDFMapper.partialUpdate(existingAppLessonPDF, appLessonPDFDTO);

                return existingAppLessonPDF;
            })
            .map(appLessonPDFRepository::save)
            .map(appLessonPDFMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppLessonPDFDTO> findAll() {
        log.debug("Request to get all AppLessonPDFS");
        return appLessonPDFRepository.findAll().stream().map(appLessonPDFMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AppLessonPDFDTO> findOne(Long id) {
        log.debug("Request to get AppLessonPDF : {}", id);
        return appLessonPDFRepository.findById(id).map(appLessonPDFMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AppLessonPDF : {}", id);
        appLessonPDFRepository.deleteById(id);
    }
}
