package com.auth0.rainbow.service.impl;

import com.auth0.rainbow.domain.AppMultipleChoiceAnswer;
import com.auth0.rainbow.repository.AppMultipleChoiceAnswerRepository;
import com.auth0.rainbow.service.AppMultipleChoiceAnswerService;
import com.auth0.rainbow.service.dto.AppMultipleChoiceAnswerDTO;
import com.auth0.rainbow.service.mapper.AppMultipleChoiceAnswerMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AppMultipleChoiceAnswer}.
 */
@Service
@Transactional
public class AppMultipleChoiceAnswerServiceImpl implements AppMultipleChoiceAnswerService {

    private final Logger log = LoggerFactory.getLogger(AppMultipleChoiceAnswerServiceImpl.class);

    private final AppMultipleChoiceAnswerRepository appMultipleChoiceAnswerRepository;

    private final AppMultipleChoiceAnswerMapper appMultipleChoiceAnswerMapper;

    public AppMultipleChoiceAnswerServiceImpl(
        AppMultipleChoiceAnswerRepository appMultipleChoiceAnswerRepository,
        AppMultipleChoiceAnswerMapper appMultipleChoiceAnswerMapper
    ) {
        this.appMultipleChoiceAnswerRepository = appMultipleChoiceAnswerRepository;
        this.appMultipleChoiceAnswerMapper = appMultipleChoiceAnswerMapper;
    }

    @Override
    public AppMultipleChoiceAnswerDTO save(AppMultipleChoiceAnswerDTO appMultipleChoiceAnswerDTO) {
        log.debug("Request to save AppMultipleChoiceAnswer : {}", appMultipleChoiceAnswerDTO);
        AppMultipleChoiceAnswer appMultipleChoiceAnswer = appMultipleChoiceAnswerMapper.toEntity(appMultipleChoiceAnswerDTO);
        appMultipleChoiceAnswer = appMultipleChoiceAnswerRepository.save(appMultipleChoiceAnswer);
        return appMultipleChoiceAnswerMapper.toDto(appMultipleChoiceAnswer);
    }

    @Override
    public AppMultipleChoiceAnswerDTO update(AppMultipleChoiceAnswerDTO appMultipleChoiceAnswerDTO) {
        log.debug("Request to update AppMultipleChoiceAnswer : {}", appMultipleChoiceAnswerDTO);
        AppMultipleChoiceAnswer appMultipleChoiceAnswer = appMultipleChoiceAnswerMapper.toEntity(appMultipleChoiceAnswerDTO);
        appMultipleChoiceAnswer = appMultipleChoiceAnswerRepository.save(appMultipleChoiceAnswer);
        return appMultipleChoiceAnswerMapper.toDto(appMultipleChoiceAnswer);
    }

    @Override
    public Optional<AppMultipleChoiceAnswerDTO> partialUpdate(AppMultipleChoiceAnswerDTO appMultipleChoiceAnswerDTO) {
        log.debug("Request to partially update AppMultipleChoiceAnswer : {}", appMultipleChoiceAnswerDTO);

        return appMultipleChoiceAnswerRepository
            .findById(appMultipleChoiceAnswerDTO.getId())
            .map(existingAppMultipleChoiceAnswer -> {
                appMultipleChoiceAnswerMapper.partialUpdate(existingAppMultipleChoiceAnswer, appMultipleChoiceAnswerDTO);

                return existingAppMultipleChoiceAnswer;
            })
            .map(appMultipleChoiceAnswerRepository::save)
            .map(appMultipleChoiceAnswerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppMultipleChoiceAnswerDTO> findAll() {
        log.debug("Request to get all AppMultipleChoiceAnswers");
        return appMultipleChoiceAnswerRepository
            .findAll()
            .stream()
            .map(appMultipleChoiceAnswerMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AppMultipleChoiceAnswerDTO> findOne(Long id) {
        log.debug("Request to get AppMultipleChoiceAnswer : {}", id);
        return appMultipleChoiceAnswerRepository.findById(id).map(appMultipleChoiceAnswerMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AppMultipleChoiceAnswer : {}", id);
        appMultipleChoiceAnswerRepository.deleteById(id);
    }
}
