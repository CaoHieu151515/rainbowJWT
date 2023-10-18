package com.auth0.rainbow.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.auth0.rainbow.IntegrationTest;
import com.auth0.rainbow.domain.AppQuestionVideoInfo;
import com.auth0.rainbow.repository.AppQuestionVideoInfoRepository;
import com.auth0.rainbow.service.dto.AppQuestionVideoInfoDTO;
import com.auth0.rainbow.service.mapper.AppQuestionVideoInfoMapper;
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
 * Integration tests for the {@link AppQuestionVideoInfoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AppQuestionVideoInfoResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_QUENSTION_VIDEO = "AAAAAAAAAA";
    private static final String UPDATED_QUENSTION_VIDEO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/app-question-video-infos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AppQuestionVideoInfoRepository appQuestionVideoInfoRepository;

    @Autowired
    private AppQuestionVideoInfoMapper appQuestionVideoInfoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAppQuestionVideoInfoMockMvc;

    private AppQuestionVideoInfo appQuestionVideoInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppQuestionVideoInfo createEntity(EntityManager em) {
        AppQuestionVideoInfo appQuestionVideoInfo = new AppQuestionVideoInfo()
            .description(DEFAULT_DESCRIPTION)
            .quenstionVideo(DEFAULT_QUENSTION_VIDEO);
        return appQuestionVideoInfo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppQuestionVideoInfo createUpdatedEntity(EntityManager em) {
        AppQuestionVideoInfo appQuestionVideoInfo = new AppQuestionVideoInfo()
            .description(UPDATED_DESCRIPTION)
            .quenstionVideo(UPDATED_QUENSTION_VIDEO);
        return appQuestionVideoInfo;
    }

    @BeforeEach
    public void initTest() {
        appQuestionVideoInfo = createEntity(em);
    }

    @Test
    @Transactional
    void createAppQuestionVideoInfo() throws Exception {
        int databaseSizeBeforeCreate = appQuestionVideoInfoRepository.findAll().size();
        // Create the AppQuestionVideoInfo
        AppQuestionVideoInfoDTO appQuestionVideoInfoDTO = appQuestionVideoInfoMapper.toDto(appQuestionVideoInfo);
        restAppQuestionVideoInfoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appQuestionVideoInfoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AppQuestionVideoInfo in the database
        List<AppQuestionVideoInfo> appQuestionVideoInfoList = appQuestionVideoInfoRepository.findAll();
        assertThat(appQuestionVideoInfoList).hasSize(databaseSizeBeforeCreate + 1);
        AppQuestionVideoInfo testAppQuestionVideoInfo = appQuestionVideoInfoList.get(appQuestionVideoInfoList.size() - 1);
        assertThat(testAppQuestionVideoInfo.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAppQuestionVideoInfo.getQuenstionVideo()).isEqualTo(DEFAULT_QUENSTION_VIDEO);
    }

    @Test
    @Transactional
    void createAppQuestionVideoInfoWithExistingId() throws Exception {
        // Create the AppQuestionVideoInfo with an existing ID
        appQuestionVideoInfo.setId(1L);
        AppQuestionVideoInfoDTO appQuestionVideoInfoDTO = appQuestionVideoInfoMapper.toDto(appQuestionVideoInfo);

        int databaseSizeBeforeCreate = appQuestionVideoInfoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppQuestionVideoInfoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appQuestionVideoInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppQuestionVideoInfo in the database
        List<AppQuestionVideoInfo> appQuestionVideoInfoList = appQuestionVideoInfoRepository.findAll();
        assertThat(appQuestionVideoInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAppQuestionVideoInfos() throws Exception {
        // Initialize the database
        appQuestionVideoInfoRepository.saveAndFlush(appQuestionVideoInfo);

        // Get all the appQuestionVideoInfoList
        restAppQuestionVideoInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appQuestionVideoInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].quenstionVideo").value(hasItem(DEFAULT_QUENSTION_VIDEO)));
    }

    @Test
    @Transactional
    void getAppQuestionVideoInfo() throws Exception {
        // Initialize the database
        appQuestionVideoInfoRepository.saveAndFlush(appQuestionVideoInfo);

        // Get the appQuestionVideoInfo
        restAppQuestionVideoInfoMockMvc
            .perform(get(ENTITY_API_URL_ID, appQuestionVideoInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(appQuestionVideoInfo.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.quenstionVideo").value(DEFAULT_QUENSTION_VIDEO));
    }

    @Test
    @Transactional
    void getNonExistingAppQuestionVideoInfo() throws Exception {
        // Get the appQuestionVideoInfo
        restAppQuestionVideoInfoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAppQuestionVideoInfo() throws Exception {
        // Initialize the database
        appQuestionVideoInfoRepository.saveAndFlush(appQuestionVideoInfo);

        int databaseSizeBeforeUpdate = appQuestionVideoInfoRepository.findAll().size();

        // Update the appQuestionVideoInfo
        AppQuestionVideoInfo updatedAppQuestionVideoInfo = appQuestionVideoInfoRepository.findById(appQuestionVideoInfo.getId()).get();
        // Disconnect from session so that the updates on updatedAppQuestionVideoInfo are not directly saved in db
        em.detach(updatedAppQuestionVideoInfo);
        updatedAppQuestionVideoInfo.description(UPDATED_DESCRIPTION).quenstionVideo(UPDATED_QUENSTION_VIDEO);
        AppQuestionVideoInfoDTO appQuestionVideoInfoDTO = appQuestionVideoInfoMapper.toDto(updatedAppQuestionVideoInfo);

        restAppQuestionVideoInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appQuestionVideoInfoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appQuestionVideoInfoDTO))
            )
            .andExpect(status().isOk());

        // Validate the AppQuestionVideoInfo in the database
        List<AppQuestionVideoInfo> appQuestionVideoInfoList = appQuestionVideoInfoRepository.findAll();
        assertThat(appQuestionVideoInfoList).hasSize(databaseSizeBeforeUpdate);
        AppQuestionVideoInfo testAppQuestionVideoInfo = appQuestionVideoInfoList.get(appQuestionVideoInfoList.size() - 1);
        assertThat(testAppQuestionVideoInfo.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAppQuestionVideoInfo.getQuenstionVideo()).isEqualTo(UPDATED_QUENSTION_VIDEO);
    }

    @Test
    @Transactional
    void putNonExistingAppQuestionVideoInfo() throws Exception {
        int databaseSizeBeforeUpdate = appQuestionVideoInfoRepository.findAll().size();
        appQuestionVideoInfo.setId(count.incrementAndGet());

        // Create the AppQuestionVideoInfo
        AppQuestionVideoInfoDTO appQuestionVideoInfoDTO = appQuestionVideoInfoMapper.toDto(appQuestionVideoInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppQuestionVideoInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appQuestionVideoInfoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appQuestionVideoInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppQuestionVideoInfo in the database
        List<AppQuestionVideoInfo> appQuestionVideoInfoList = appQuestionVideoInfoRepository.findAll();
        assertThat(appQuestionVideoInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAppQuestionVideoInfo() throws Exception {
        int databaseSizeBeforeUpdate = appQuestionVideoInfoRepository.findAll().size();
        appQuestionVideoInfo.setId(count.incrementAndGet());

        // Create the AppQuestionVideoInfo
        AppQuestionVideoInfoDTO appQuestionVideoInfoDTO = appQuestionVideoInfoMapper.toDto(appQuestionVideoInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppQuestionVideoInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appQuestionVideoInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppQuestionVideoInfo in the database
        List<AppQuestionVideoInfo> appQuestionVideoInfoList = appQuestionVideoInfoRepository.findAll();
        assertThat(appQuestionVideoInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAppQuestionVideoInfo() throws Exception {
        int databaseSizeBeforeUpdate = appQuestionVideoInfoRepository.findAll().size();
        appQuestionVideoInfo.setId(count.incrementAndGet());

        // Create the AppQuestionVideoInfo
        AppQuestionVideoInfoDTO appQuestionVideoInfoDTO = appQuestionVideoInfoMapper.toDto(appQuestionVideoInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppQuestionVideoInfoMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appQuestionVideoInfoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppQuestionVideoInfo in the database
        List<AppQuestionVideoInfo> appQuestionVideoInfoList = appQuestionVideoInfoRepository.findAll();
        assertThat(appQuestionVideoInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAppQuestionVideoInfoWithPatch() throws Exception {
        // Initialize the database
        appQuestionVideoInfoRepository.saveAndFlush(appQuestionVideoInfo);

        int databaseSizeBeforeUpdate = appQuestionVideoInfoRepository.findAll().size();

        // Update the appQuestionVideoInfo using partial update
        AppQuestionVideoInfo partialUpdatedAppQuestionVideoInfo = new AppQuestionVideoInfo();
        partialUpdatedAppQuestionVideoInfo.setId(appQuestionVideoInfo.getId());

        restAppQuestionVideoInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppQuestionVideoInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAppQuestionVideoInfo))
            )
            .andExpect(status().isOk());

        // Validate the AppQuestionVideoInfo in the database
        List<AppQuestionVideoInfo> appQuestionVideoInfoList = appQuestionVideoInfoRepository.findAll();
        assertThat(appQuestionVideoInfoList).hasSize(databaseSizeBeforeUpdate);
        AppQuestionVideoInfo testAppQuestionVideoInfo = appQuestionVideoInfoList.get(appQuestionVideoInfoList.size() - 1);
        assertThat(testAppQuestionVideoInfo.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAppQuestionVideoInfo.getQuenstionVideo()).isEqualTo(DEFAULT_QUENSTION_VIDEO);
    }

    @Test
    @Transactional
    void fullUpdateAppQuestionVideoInfoWithPatch() throws Exception {
        // Initialize the database
        appQuestionVideoInfoRepository.saveAndFlush(appQuestionVideoInfo);

        int databaseSizeBeforeUpdate = appQuestionVideoInfoRepository.findAll().size();

        // Update the appQuestionVideoInfo using partial update
        AppQuestionVideoInfo partialUpdatedAppQuestionVideoInfo = new AppQuestionVideoInfo();
        partialUpdatedAppQuestionVideoInfo.setId(appQuestionVideoInfo.getId());

        partialUpdatedAppQuestionVideoInfo.description(UPDATED_DESCRIPTION).quenstionVideo(UPDATED_QUENSTION_VIDEO);

        restAppQuestionVideoInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppQuestionVideoInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAppQuestionVideoInfo))
            )
            .andExpect(status().isOk());

        // Validate the AppQuestionVideoInfo in the database
        List<AppQuestionVideoInfo> appQuestionVideoInfoList = appQuestionVideoInfoRepository.findAll();
        assertThat(appQuestionVideoInfoList).hasSize(databaseSizeBeforeUpdate);
        AppQuestionVideoInfo testAppQuestionVideoInfo = appQuestionVideoInfoList.get(appQuestionVideoInfoList.size() - 1);
        assertThat(testAppQuestionVideoInfo.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAppQuestionVideoInfo.getQuenstionVideo()).isEqualTo(UPDATED_QUENSTION_VIDEO);
    }

    @Test
    @Transactional
    void patchNonExistingAppQuestionVideoInfo() throws Exception {
        int databaseSizeBeforeUpdate = appQuestionVideoInfoRepository.findAll().size();
        appQuestionVideoInfo.setId(count.incrementAndGet());

        // Create the AppQuestionVideoInfo
        AppQuestionVideoInfoDTO appQuestionVideoInfoDTO = appQuestionVideoInfoMapper.toDto(appQuestionVideoInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppQuestionVideoInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, appQuestionVideoInfoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(appQuestionVideoInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppQuestionVideoInfo in the database
        List<AppQuestionVideoInfo> appQuestionVideoInfoList = appQuestionVideoInfoRepository.findAll();
        assertThat(appQuestionVideoInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAppQuestionVideoInfo() throws Exception {
        int databaseSizeBeforeUpdate = appQuestionVideoInfoRepository.findAll().size();
        appQuestionVideoInfo.setId(count.incrementAndGet());

        // Create the AppQuestionVideoInfo
        AppQuestionVideoInfoDTO appQuestionVideoInfoDTO = appQuestionVideoInfoMapper.toDto(appQuestionVideoInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppQuestionVideoInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(appQuestionVideoInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppQuestionVideoInfo in the database
        List<AppQuestionVideoInfo> appQuestionVideoInfoList = appQuestionVideoInfoRepository.findAll();
        assertThat(appQuestionVideoInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAppQuestionVideoInfo() throws Exception {
        int databaseSizeBeforeUpdate = appQuestionVideoInfoRepository.findAll().size();
        appQuestionVideoInfo.setId(count.incrementAndGet());

        // Create the AppQuestionVideoInfo
        AppQuestionVideoInfoDTO appQuestionVideoInfoDTO = appQuestionVideoInfoMapper.toDto(appQuestionVideoInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppQuestionVideoInfoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(appQuestionVideoInfoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppQuestionVideoInfo in the database
        List<AppQuestionVideoInfo> appQuestionVideoInfoList = appQuestionVideoInfoRepository.findAll();
        assertThat(appQuestionVideoInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAppQuestionVideoInfo() throws Exception {
        // Initialize the database
        appQuestionVideoInfoRepository.saveAndFlush(appQuestionVideoInfo);

        int databaseSizeBeforeDelete = appQuestionVideoInfoRepository.findAll().size();

        // Delete the appQuestionVideoInfo
        restAppQuestionVideoInfoMockMvc
            .perform(delete(ENTITY_API_URL_ID, appQuestionVideoInfo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AppQuestionVideoInfo> appQuestionVideoInfoList = appQuestionVideoInfoRepository.findAll();
        assertThat(appQuestionVideoInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
