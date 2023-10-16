package com.auth0.rainbow.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.auth0.rainbow.IntegrationTest;
import com.auth0.rainbow.domain.LinkAccountUser;
import com.auth0.rainbow.repository.LinkAccountUserRepository;
import com.auth0.rainbow.service.dto.LinkAccountUserDTO;
import com.auth0.rainbow.service.mapper.LinkAccountUserMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link LinkAccountUserResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LinkAccountUserResourceIT {

    private static final String ENTITY_API_URL = "/api/link-account-users";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LinkAccountUserRepository linkAccountUserRepository;

    @Autowired
    private LinkAccountUserMapper linkAccountUserMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLinkAccountUserMockMvc;

    private LinkAccountUser linkAccountUser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LinkAccountUser createEntity(EntityManager em) {
        LinkAccountUser linkAccountUser = new LinkAccountUser();
        return linkAccountUser;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LinkAccountUser createUpdatedEntity(EntityManager em) {
        LinkAccountUser linkAccountUser = new LinkAccountUser();
        return linkAccountUser;
    }

    @BeforeEach
    public void initTest() {
        linkAccountUser = createEntity(em);
    }

    @Test
    @Transactional
    void createLinkAccountUser() throws Exception {
        int databaseSizeBeforeCreate = linkAccountUserRepository.findAll().size();
        // Create the LinkAccountUser
        LinkAccountUserDTO linkAccountUserDTO = linkAccountUserMapper.toDto(linkAccountUser);
        restLinkAccountUserMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(linkAccountUserDTO))
            )
            .andExpect(status().isCreated());

        // Validate the LinkAccountUser in the database
        List<LinkAccountUser> linkAccountUserList = linkAccountUserRepository.findAll();
        assertThat(linkAccountUserList).hasSize(databaseSizeBeforeCreate + 1);
        LinkAccountUser testLinkAccountUser = linkAccountUserList.get(linkAccountUserList.size() - 1);
    }

    @Test
    @Transactional
    void createLinkAccountUserWithExistingId() throws Exception {
        // Create the LinkAccountUser with an existing ID
        linkAccountUser.setId(1L);
        LinkAccountUserDTO linkAccountUserDTO = linkAccountUserMapper.toDto(linkAccountUser);

        int databaseSizeBeforeCreate = linkAccountUserRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLinkAccountUserMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(linkAccountUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LinkAccountUser in the database
        List<LinkAccountUser> linkAccountUserList = linkAccountUserRepository.findAll();
        assertThat(linkAccountUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLinkAccountUsers() throws Exception {
        // Initialize the database
        linkAccountUserRepository.saveAndFlush(linkAccountUser);

        // Get all the linkAccountUserList
        restLinkAccountUserMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(linkAccountUser.getId().intValue())));
    }

    @Test
    @Transactional
    void getLinkAccountUser() throws Exception {
        // Initialize the database
        linkAccountUserRepository.saveAndFlush(linkAccountUser);

        // Get the linkAccountUser
        restLinkAccountUserMockMvc
            .perform(get(ENTITY_API_URL_ID, linkAccountUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(linkAccountUser.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingLinkAccountUser() throws Exception {
        // Get the linkAccountUser
        restLinkAccountUserMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLinkAccountUser() throws Exception {
        // Initialize the database
        linkAccountUserRepository.saveAndFlush(linkAccountUser);

        int databaseSizeBeforeUpdate = linkAccountUserRepository.findAll().size();

        // Update the linkAccountUser
        LinkAccountUser updatedLinkAccountUser = linkAccountUserRepository.findById(linkAccountUser.getId()).get();
        // Disconnect from session so that the updates on updatedLinkAccountUser are not directly saved in db
        em.detach(updatedLinkAccountUser);
        LinkAccountUserDTO linkAccountUserDTO = linkAccountUserMapper.toDto(updatedLinkAccountUser);

        restLinkAccountUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, linkAccountUserDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(linkAccountUserDTO))
            )
            .andExpect(status().isOk());

        // Validate the LinkAccountUser in the database
        List<LinkAccountUser> linkAccountUserList = linkAccountUserRepository.findAll();
        assertThat(linkAccountUserList).hasSize(databaseSizeBeforeUpdate);
        LinkAccountUser testLinkAccountUser = linkAccountUserList.get(linkAccountUserList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingLinkAccountUser() throws Exception {
        int databaseSizeBeforeUpdate = linkAccountUserRepository.findAll().size();
        linkAccountUser.setId(count.incrementAndGet());

        // Create the LinkAccountUser
        LinkAccountUserDTO linkAccountUserDTO = linkAccountUserMapper.toDto(linkAccountUser);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLinkAccountUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, linkAccountUserDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(linkAccountUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LinkAccountUser in the database
        List<LinkAccountUser> linkAccountUserList = linkAccountUserRepository.findAll();
        assertThat(linkAccountUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLinkAccountUser() throws Exception {
        int databaseSizeBeforeUpdate = linkAccountUserRepository.findAll().size();
        linkAccountUser.setId(count.incrementAndGet());

        // Create the LinkAccountUser
        LinkAccountUserDTO linkAccountUserDTO = linkAccountUserMapper.toDto(linkAccountUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLinkAccountUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(linkAccountUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LinkAccountUser in the database
        List<LinkAccountUser> linkAccountUserList = linkAccountUserRepository.findAll();
        assertThat(linkAccountUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLinkAccountUser() throws Exception {
        int databaseSizeBeforeUpdate = linkAccountUserRepository.findAll().size();
        linkAccountUser.setId(count.incrementAndGet());

        // Create the LinkAccountUser
        LinkAccountUserDTO linkAccountUserDTO = linkAccountUserMapper.toDto(linkAccountUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLinkAccountUserMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(linkAccountUserDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LinkAccountUser in the database
        List<LinkAccountUser> linkAccountUserList = linkAccountUserRepository.findAll();
        assertThat(linkAccountUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLinkAccountUserWithPatch() throws Exception {
        // Initialize the database
        linkAccountUserRepository.saveAndFlush(linkAccountUser);

        int databaseSizeBeforeUpdate = linkAccountUserRepository.findAll().size();

        // Update the linkAccountUser using partial update
        LinkAccountUser partialUpdatedLinkAccountUser = new LinkAccountUser();
        partialUpdatedLinkAccountUser.setId(linkAccountUser.getId());

        restLinkAccountUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLinkAccountUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLinkAccountUser))
            )
            .andExpect(status().isOk());

        // Validate the LinkAccountUser in the database
        List<LinkAccountUser> linkAccountUserList = linkAccountUserRepository.findAll();
        assertThat(linkAccountUserList).hasSize(databaseSizeBeforeUpdate);
        LinkAccountUser testLinkAccountUser = linkAccountUserList.get(linkAccountUserList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateLinkAccountUserWithPatch() throws Exception {
        // Initialize the database
        linkAccountUserRepository.saveAndFlush(linkAccountUser);

        int databaseSizeBeforeUpdate = linkAccountUserRepository.findAll().size();

        // Update the linkAccountUser using partial update
        LinkAccountUser partialUpdatedLinkAccountUser = new LinkAccountUser();
        partialUpdatedLinkAccountUser.setId(linkAccountUser.getId());

        restLinkAccountUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLinkAccountUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLinkAccountUser))
            )
            .andExpect(status().isOk());

        // Validate the LinkAccountUser in the database
        List<LinkAccountUser> linkAccountUserList = linkAccountUserRepository.findAll();
        assertThat(linkAccountUserList).hasSize(databaseSizeBeforeUpdate);
        LinkAccountUser testLinkAccountUser = linkAccountUserList.get(linkAccountUserList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingLinkAccountUser() throws Exception {
        int databaseSizeBeforeUpdate = linkAccountUserRepository.findAll().size();
        linkAccountUser.setId(count.incrementAndGet());

        // Create the LinkAccountUser
        LinkAccountUserDTO linkAccountUserDTO = linkAccountUserMapper.toDto(linkAccountUser);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLinkAccountUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, linkAccountUserDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(linkAccountUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LinkAccountUser in the database
        List<LinkAccountUser> linkAccountUserList = linkAccountUserRepository.findAll();
        assertThat(linkAccountUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLinkAccountUser() throws Exception {
        int databaseSizeBeforeUpdate = linkAccountUserRepository.findAll().size();
        linkAccountUser.setId(count.incrementAndGet());

        // Create the LinkAccountUser
        LinkAccountUserDTO linkAccountUserDTO = linkAccountUserMapper.toDto(linkAccountUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLinkAccountUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(linkAccountUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LinkAccountUser in the database
        List<LinkAccountUser> linkAccountUserList = linkAccountUserRepository.findAll();
        assertThat(linkAccountUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLinkAccountUser() throws Exception {
        int databaseSizeBeforeUpdate = linkAccountUserRepository.findAll().size();
        linkAccountUser.setId(count.incrementAndGet());

        // Create the LinkAccountUser
        LinkAccountUserDTO linkAccountUserDTO = linkAccountUserMapper.toDto(linkAccountUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLinkAccountUserMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(linkAccountUserDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LinkAccountUser in the database
        List<LinkAccountUser> linkAccountUserList = linkAccountUserRepository.findAll();
        assertThat(linkAccountUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLinkAccountUser() throws Exception {
        // Initialize the database
        linkAccountUserRepository.saveAndFlush(linkAccountUser);

        int databaseSizeBeforeDelete = linkAccountUserRepository.findAll().size();

        // Delete the linkAccountUser
        restLinkAccountUserMockMvc
            .perform(delete(ENTITY_API_URL_ID, linkAccountUser.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LinkAccountUser> linkAccountUserList = linkAccountUserRepository.findAll();
        assertThat(linkAccountUserList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
