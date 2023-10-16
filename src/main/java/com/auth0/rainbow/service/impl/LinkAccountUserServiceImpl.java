package com.auth0.rainbow.service.impl;

import com.auth0.rainbow.domain.LinkAccountUser;
import com.auth0.rainbow.repository.LinkAccountUserRepository;
import com.auth0.rainbow.service.LinkAccountUserService;
import com.auth0.rainbow.service.dto.LinkAccountUserDTO;
import com.auth0.rainbow.service.mapper.LinkAccountUserMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LinkAccountUser}.
 */
@Service
@Transactional
public class LinkAccountUserServiceImpl implements LinkAccountUserService {

    private final Logger log = LoggerFactory.getLogger(LinkAccountUserServiceImpl.class);

    private final LinkAccountUserRepository linkAccountUserRepository;

    private final LinkAccountUserMapper linkAccountUserMapper;

    public LinkAccountUserServiceImpl(LinkAccountUserRepository linkAccountUserRepository, LinkAccountUserMapper linkAccountUserMapper) {
        this.linkAccountUserRepository = linkAccountUserRepository;
        this.linkAccountUserMapper = linkAccountUserMapper;
    }

    @Override
    public LinkAccountUserDTO save(LinkAccountUserDTO linkAccountUserDTO) {
        log.debug("Request to save LinkAccountUser : {}", linkAccountUserDTO);
        LinkAccountUser linkAccountUser = linkAccountUserMapper.toEntity(linkAccountUserDTO);
        linkAccountUser = linkAccountUserRepository.save(linkAccountUser);
        return linkAccountUserMapper.toDto(linkAccountUser);
    }

    @Override
    public LinkAccountUserDTO update(LinkAccountUserDTO linkAccountUserDTO) {
        log.debug("Request to update LinkAccountUser : {}", linkAccountUserDTO);
        LinkAccountUser linkAccountUser = linkAccountUserMapper.toEntity(linkAccountUserDTO);
        linkAccountUser = linkAccountUserRepository.save(linkAccountUser);
        return linkAccountUserMapper.toDto(linkAccountUser);
    }

    @Override
    public Optional<LinkAccountUserDTO> partialUpdate(LinkAccountUserDTO linkAccountUserDTO) {
        log.debug("Request to partially update LinkAccountUser : {}", linkAccountUserDTO);

        return linkAccountUserRepository
            .findById(linkAccountUserDTO.getId())
            .map(existingLinkAccountUser -> {
                linkAccountUserMapper.partialUpdate(existingLinkAccountUser, linkAccountUserDTO);

                return existingLinkAccountUser;
            })
            .map(linkAccountUserRepository::save)
            .map(linkAccountUserMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LinkAccountUserDTO> findAll() {
        log.debug("Request to get all LinkAccountUsers");
        return linkAccountUserRepository
            .findAll()
            .stream()
            .map(linkAccountUserMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LinkAccountUserDTO> findOne(Long id) {
        log.debug("Request to get LinkAccountUser : {}", id);
        return linkAccountUserRepository.findById(id).map(linkAccountUserMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete LinkAccountUser : {}", id);
        linkAccountUserRepository.deleteById(id);
    }
}
