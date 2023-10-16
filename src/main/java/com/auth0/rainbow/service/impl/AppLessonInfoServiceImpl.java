package com.auth0.rainbow.service.impl;

import com.auth0.rainbow.domain.AppLessonInfo;
import com.auth0.rainbow.repository.AppLessonInfoRepository;
import com.auth0.rainbow.service.AppLessonInfoService;
import com.auth0.rainbow.service.dto.AppLessonInfoDTO;
import com.auth0.rainbow.service.mapper.AppLessonInfoMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AppLessonInfo}.
 */
@Service
@Transactional
public class AppLessonInfoServiceImpl implements AppLessonInfoService {

    private final Logger log = LoggerFactory.getLogger(AppLessonInfoServiceImpl.class);

    private final AppLessonInfoRepository appLessonInfoRepository;

    private final AppLessonInfoMapper appLessonInfoMapper;

    public AppLessonInfoServiceImpl(AppLessonInfoRepository appLessonInfoRepository, AppLessonInfoMapper appLessonInfoMapper) {
        this.appLessonInfoRepository = appLessonInfoRepository;
        this.appLessonInfoMapper = appLessonInfoMapper;
    }

    @Override
    public AppLessonInfoDTO save(AppLessonInfoDTO appLessonInfoDTO) {
        log.debug("Request to save AppLessonInfo : {}", appLessonInfoDTO);
        AppLessonInfo appLessonInfo = appLessonInfoMapper.toEntity(appLessonInfoDTO);
        appLessonInfo = appLessonInfoRepository.save(appLessonInfo);
        return appLessonInfoMapper.toDto(appLessonInfo);
    }

    @Override
    public AppLessonInfoDTO update(AppLessonInfoDTO appLessonInfoDTO) {
        log.debug("Request to update AppLessonInfo : {}", appLessonInfoDTO);
        AppLessonInfo appLessonInfo = appLessonInfoMapper.toEntity(appLessonInfoDTO);
        appLessonInfo = appLessonInfoRepository.save(appLessonInfo);
        return appLessonInfoMapper.toDto(appLessonInfo);
    }

    @Override
    public Optional<AppLessonInfoDTO> partialUpdate(AppLessonInfoDTO appLessonInfoDTO) {
        log.debug("Request to partially update AppLessonInfo : {}", appLessonInfoDTO);

        return appLessonInfoRepository
            .findById(appLessonInfoDTO.getId())
            .map(existingAppLessonInfo -> {
                appLessonInfoMapper.partialUpdate(existingAppLessonInfo, appLessonInfoDTO);

                return existingAppLessonInfo;
            })
            .map(appLessonInfoRepository::save)
            .map(appLessonInfoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppLessonInfoDTO> findAll() {
        log.debug("Request to get all AppLessonInfos");
        return appLessonInfoRepository.findAll().stream().map(appLessonInfoMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AppLessonInfoDTO> findOne(Long id) {
        log.debug("Request to get AppLessonInfo : {}", id);
        return appLessonInfoRepository.findById(id).map(appLessonInfoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AppLessonInfo : {}", id);
        appLessonInfoRepository.deleteById(id);
    }
}
