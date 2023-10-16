package com.auth0.rainbow.service.impl;

import com.auth0.rainbow.domain.AppPayment;
import com.auth0.rainbow.repository.AppPaymentRepository;
import com.auth0.rainbow.service.AppPaymentService;
import com.auth0.rainbow.service.dto.AppPaymentDTO;
import com.auth0.rainbow.service.mapper.AppPaymentMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AppPayment}.
 */
@Service
@Transactional
public class AppPaymentServiceImpl implements AppPaymentService {

    private final Logger log = LoggerFactory.getLogger(AppPaymentServiceImpl.class);

    private final AppPaymentRepository appPaymentRepository;

    private final AppPaymentMapper appPaymentMapper;

    public AppPaymentServiceImpl(AppPaymentRepository appPaymentRepository, AppPaymentMapper appPaymentMapper) {
        this.appPaymentRepository = appPaymentRepository;
        this.appPaymentMapper = appPaymentMapper;
    }

    @Override
    public AppPaymentDTO save(AppPaymentDTO appPaymentDTO) {
        log.debug("Request to save AppPayment : {}", appPaymentDTO);
        AppPayment appPayment = appPaymentMapper.toEntity(appPaymentDTO);
        appPayment = appPaymentRepository.save(appPayment);
        return appPaymentMapper.toDto(appPayment);
    }

    @Override
    public AppPaymentDTO update(AppPaymentDTO appPaymentDTO) {
        log.debug("Request to update AppPayment : {}", appPaymentDTO);
        AppPayment appPayment = appPaymentMapper.toEntity(appPaymentDTO);
        appPayment = appPaymentRepository.save(appPayment);
        return appPaymentMapper.toDto(appPayment);
    }

    @Override
    public Optional<AppPaymentDTO> partialUpdate(AppPaymentDTO appPaymentDTO) {
        log.debug("Request to partially update AppPayment : {}", appPaymentDTO);

        return appPaymentRepository
            .findById(appPaymentDTO.getId())
            .map(existingAppPayment -> {
                appPaymentMapper.partialUpdate(existingAppPayment, appPaymentDTO);

                return existingAppPayment;
            })
            .map(appPaymentRepository::save)
            .map(appPaymentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppPaymentDTO> findAll() {
        log.debug("Request to get all AppPayments");
        return appPaymentRepository.findAll().stream().map(appPaymentMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AppPaymentDTO> findOne(Long id) {
        log.debug("Request to get AppPayment : {}", id);
        return appPaymentRepository.findById(id).map(appPaymentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AppPayment : {}", id);
        appPaymentRepository.deleteById(id);
    }
}
