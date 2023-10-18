package com.auth0.rainbow.service.impl;

import com.auth0.rainbow.domain.AppQuestionVideoInfo;
import com.auth0.rainbow.repository.AppQuestionVideoInfoRepository;
import com.auth0.rainbow.service.AppQuestionVideoInfoService;
import com.auth0.rainbow.service.dto.AppQuestionVideoInfoDTO;
import com.auth0.rainbow.service.mapper.AppQuestionVideoInfoMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AppQuestionVideoInfo}.
 */
@Service
@Transactional
public class AppQuestionVideoInfoServiceImpl implements AppQuestionVideoInfoService {

    private final Logger log = LoggerFactory.getLogger(AppQuestionVideoInfoServiceImpl.class);

    private final AppQuestionVideoInfoRepository appQuestionVideoInfoRepository;

    private final AppQuestionVideoInfoMapper appQuestionVideoInfoMapper;

    public AppQuestionVideoInfoServiceImpl(
        AppQuestionVideoInfoRepository appQuestionVideoInfoRepository,
        AppQuestionVideoInfoMapper appQuestionVideoInfoMapper
    ) {
        this.appQuestionVideoInfoRepository = appQuestionVideoInfoRepository;
        this.appQuestionVideoInfoMapper = appQuestionVideoInfoMapper;
    }

    @Override
    public AppQuestionVideoInfoDTO save(AppQuestionVideoInfoDTO appQuestionVideoInfoDTO) {
        log.debug("Request to save AppQuestionVideoInfo : {}", appQuestionVideoInfoDTO);
        AppQuestionVideoInfo appQuestionVideoInfo = appQuestionVideoInfoMapper.toEntity(appQuestionVideoInfoDTO);
        appQuestionVideoInfo = appQuestionVideoInfoRepository.save(appQuestionVideoInfo);
        return appQuestionVideoInfoMapper.toDto(appQuestionVideoInfo);
    }

    @Override
    public AppQuestionVideoInfoDTO update(AppQuestionVideoInfoDTO appQuestionVideoInfoDTO) {
        log.debug("Request to update AppQuestionVideoInfo : {}", appQuestionVideoInfoDTO);
        AppQuestionVideoInfo appQuestionVideoInfo = appQuestionVideoInfoMapper.toEntity(appQuestionVideoInfoDTO);
        appQuestionVideoInfo = appQuestionVideoInfoRepository.save(appQuestionVideoInfo);
        return appQuestionVideoInfoMapper.toDto(appQuestionVideoInfo);
    }

    @Override
    public Optional<AppQuestionVideoInfoDTO> partialUpdate(AppQuestionVideoInfoDTO appQuestionVideoInfoDTO) {
        log.debug("Request to partially update AppQuestionVideoInfo : {}", appQuestionVideoInfoDTO);

        return appQuestionVideoInfoRepository
            .findById(appQuestionVideoInfoDTO.getId())
            .map(existingAppQuestionVideoInfo -> {
                appQuestionVideoInfoMapper.partialUpdate(existingAppQuestionVideoInfo, appQuestionVideoInfoDTO);

                return existingAppQuestionVideoInfo;
            })
            .map(appQuestionVideoInfoRepository::save)
            .map(appQuestionVideoInfoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppQuestionVideoInfoDTO> findAll() {
        log.debug("Request to get all AppQuestionVideoInfos");
        return appQuestionVideoInfoRepository
            .findAll()
            .stream()
            .map(appQuestionVideoInfoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AppQuestionVideoInfoDTO> findOne(Long id) {
        log.debug("Request to get AppQuestionVideoInfo : {}", id);
        return appQuestionVideoInfoRepository.findById(id).map(appQuestionVideoInfoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AppQuestionVideoInfo : {}", id);
        appQuestionVideoInfoRepository.deleteById(id);
    }
}
