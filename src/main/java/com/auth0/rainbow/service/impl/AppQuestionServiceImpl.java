package com.auth0.rainbow.service.impl;

import com.auth0.rainbow.domain.AppQuestion;
import com.auth0.rainbow.repository.AppQuestionRepository;
import com.auth0.rainbow.service.AppQuestionService;
import com.auth0.rainbow.service.dto.AppQuestionDTO;
import com.auth0.rainbow.service.mapper.AppQuestionMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AppQuestion}.
 */
@Service
@Transactional
public class AppQuestionServiceImpl implements AppQuestionService {

    private final Logger log = LoggerFactory.getLogger(AppQuestionServiceImpl.class);

    private final AppQuestionRepository appQuestionRepository;

    private final AppQuestionMapper appQuestionMapper;

    public AppQuestionServiceImpl(AppQuestionRepository appQuestionRepository, AppQuestionMapper appQuestionMapper) {
        this.appQuestionRepository = appQuestionRepository;
        this.appQuestionMapper = appQuestionMapper;
    }

    @Override
    public AppQuestionDTO save(AppQuestionDTO appQuestionDTO) {
        log.debug("Request to save AppQuestion : {}", appQuestionDTO);
        AppQuestion appQuestion = appQuestionMapper.toEntity(appQuestionDTO);
        appQuestion = appQuestionRepository.save(appQuestion);
        return appQuestionMapper.toDto(appQuestion);
    }

    @Override
    public AppQuestionDTO update(AppQuestionDTO appQuestionDTO) {
        log.debug("Request to update AppQuestion : {}", appQuestionDTO);
        AppQuestion appQuestion = appQuestionMapper.toEntity(appQuestionDTO);
        appQuestion = appQuestionRepository.save(appQuestion);
        return appQuestionMapper.toDto(appQuestion);
    }

    @Override
    public Optional<AppQuestionDTO> partialUpdate(AppQuestionDTO appQuestionDTO) {
        log.debug("Request to partially update AppQuestion : {}", appQuestionDTO);

        return appQuestionRepository
            .findById(appQuestionDTO.getId())
            .map(existingAppQuestion -> {
                appQuestionMapper.partialUpdate(existingAppQuestion, appQuestionDTO);

                return existingAppQuestion;
            })
            .map(appQuestionRepository::save)
            .map(appQuestionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppQuestionDTO> findAll() {
        log.debug("Request to get all AppQuestions");
        return appQuestionRepository.findAll().stream().map(appQuestionMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AppQuestionDTO> findOne(Long id) {
        log.debug("Request to get AppQuestion : {}", id);
        return appQuestionRepository.findById(id).map(appQuestionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AppQuestion : {}", id);
        appQuestionRepository.deleteById(id);
    }
}
