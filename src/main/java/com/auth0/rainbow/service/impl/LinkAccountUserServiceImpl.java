package com.auth0.rainbow.service.impl;

import com.auth0.rainbow.domain.AppUser;
import com.auth0.rainbow.domain.LinkAccountUser;
import com.auth0.rainbow.repository.AppUserRepository;
import com.auth0.rainbow.repository.LinkAccountUserRepository;
import com.auth0.rainbow.repository.UserRepository;
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
    private final AppUserRepository appUserRepository;
    private final UserRepository userRepository;

    public LinkAccountUserServiceImpl(
        LinkAccountUserRepository linkAccountUserRepository,
        LinkAccountUserMapper linkAccountUserMapper,
        AppUserRepository appUserRepository,
        UserRepository userRepository
    ) {
        this.linkAccountUserRepository = linkAccountUserRepository;
        this.linkAccountUserMapper = linkAccountUserMapper;
        this.appUserRepository = appUserRepository;
        this.userRepository = userRepository;
    }

    @Override
    public LinkAccountUserDTO save(LinkAccountUserDTO linkAccountUserDTO) {
        log.debug("Request to save LinkAccountUser : {}", linkAccountUserDTO);
        LinkAccountUser linkAccountUser = linkAccountUserMapper.toEntity(linkAccountUserDTO);
        linkAccountUser = linkAccountUserRepository.save(linkAccountUser);
        return linkAccountUserMapper.toLinkDTO(linkAccountUser);
    }

    @Override
    public LinkAccountUserDTO update(LinkAccountUserDTO linkAccountUserDTO) {
        log.debug("Request to update LinkAccountUser : {}", linkAccountUserDTO);
        LinkAccountUser linkAccountUser = linkAccountUserMapper.toEntity(linkAccountUserDTO);
        if (linkAccountUser.getAppUser() == null) {
            linkAccountUser.setAppUser(null);
        } else {
            Optional<AppUser> optionalAppUser = appUserRepository.findOneWithEagerRelationships(linkAccountUser.getAppUser().getId());
            optionalAppUser.get();
        }

        if (linkAccountUser.getUser() == null) {
            linkAccountUser.setUser(null);
        } else linkAccountUser.setUser(userRepository.findOneById(linkAccountUser.getUser().getId()));

        linkAccountUser = linkAccountUserRepository.save(linkAccountUser);
        if (linkAccountUser.getAppUser() == null && linkAccountUser.getUser() == null) {
            delete(linkAccountUser.getId());
        }

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
    @Transactional(readOnly = true)
    public Optional<LinkAccountUserDTO> findOneAppUserPost(Long id) {
        log.debug("Request to get LinkAccountUser : {}", id);

        return linkAccountUserRepository.findOneByUserId(id).map(linkAccountUserMapper::toLinkPostDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LinkAccountUserDTO> findOnedetails(Long id) {
        log.debug("Request to get LinkAccountUser : {}", id);
        return linkAccountUserRepository.findOneByUserId(id).map(linkAccountUserMapper::toLinkDTO);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete LinkAccountUser : {}", id);
        linkAccountUserRepository.deleteById(id);
    }
}
