package com.auth0.rainbow.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.auth0.rainbow.IntegrationTest;
import com.auth0.rainbow.domain.AppLessonInfo;
import com.auth0.rainbow.repository.AppLessonInfoRepository;
import com.auth0.rainbow.service.dto.AppLessonInfoDTO;
import com.auth0.rainbow.service.mapper.AppLessonInfoMapper;
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
 * Integration tests for the {@link AppLessonInfoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AppLessonInfoResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_PDF_URL = "AAAAAAAAAA";
    private static final String UPDATED_PDF_URL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/app-lesson-infos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AppLessonInfoRepository appLessonInfoRepository;

    @Autowired
    private AppLessonInfoMapper appLessonInfoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAppLessonInfoMockMvc;

    private AppLessonInfo appLessonInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppLessonInfo createEntity(EntityManager em) {
        AppLessonInfo appLessonInfo = new AppLessonInfo().description(DEFAULT_DESCRIPTION).pdfUrl(DEFAULT_PDF_URL);
        return appLessonInfo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppLessonInfo createUpdatedEntity(EntityManager em) {
        AppLessonInfo appLessonInfo = new AppLessonInfo().description(UPDATED_DESCRIPTION).pdfUrl(UPDATED_PDF_URL);
        return appLessonInfo;
    }

    @BeforeEach
    public void initTest() {
        appLessonInfo = createEntity(em);
    }

    @Test
    @Transactional
    void createAppLessonInfo() throws Exception {
        int databaseSizeBeforeCreate = appLessonInfoRepository.findAll().size();
        // Create the AppLessonInfo
        AppLessonInfoDTO appLessonInfoDTO = appLessonInfoMapper.toDto(appLessonInfo);
        restAppLessonInfoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appLessonInfoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AppLessonInfo in the database
        List<AppLessonInfo> appLessonInfoList = appLessonInfoRepository.findAll();
        assertThat(appLessonInfoList).hasSize(databaseSizeBeforeCreate + 1);
        AppLessonInfo testAppLessonInfo = appLessonInfoList.get(appLessonInfoList.size() - 1);
        assertThat(testAppLessonInfo.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAppLessonInfo.getPdfUrl()).isEqualTo(DEFAULT_PDF_URL);
    }

    @Test
    @Transactional
    void createAppLessonInfoWithExistingId() throws Exception {
        // Create the AppLessonInfo with an existing ID
        appLessonInfo.setId(1L);
        AppLessonInfoDTO appLessonInfoDTO = appLessonInfoMapper.toDto(appLessonInfo);

        int databaseSizeBeforeCreate = appLessonInfoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppLessonInfoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appLessonInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppLessonInfo in the database
        List<AppLessonInfo> appLessonInfoList = appLessonInfoRepository.findAll();
        assertThat(appLessonInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAppLessonInfos() throws Exception {
        // Initialize the database
        appLessonInfoRepository.saveAndFlush(appLessonInfo);

        // Get all the appLessonInfoList
        restAppLessonInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appLessonInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].pdfUrl").value(hasItem(DEFAULT_PDF_URL)));
    }

    @Test
    @Transactional
    void getAppLessonInfo() throws Exception {
        // Initialize the database
        appLessonInfoRepository.saveAndFlush(appLessonInfo);

        // Get the appLessonInfo
        restAppLessonInfoMockMvc
            .perform(get(ENTITY_API_URL_ID, appLessonInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(appLessonInfo.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.pdfUrl").value(DEFAULT_PDF_URL));
    }

    @Test
    @Transactional
    void getNonExistingAppLessonInfo() throws Exception {
        // Get the appLessonInfo
        restAppLessonInfoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAppLessonInfo() throws Exception {
        // Initialize the database
        appLessonInfoRepository.saveAndFlush(appLessonInfo);

        int databaseSizeBeforeUpdate = appLessonInfoRepository.findAll().size();

        // Update the appLessonInfo
        AppLessonInfo updatedAppLessonInfo = appLessonInfoRepository.findById(appLessonInfo.getId()).get();
        // Disconnect from session so that the updates on updatedAppLessonInfo are not directly saved in db
        em.detach(updatedAppLessonInfo);
        updatedAppLessonInfo.description(UPDATED_DESCRIPTION).pdfUrl(UPDATED_PDF_URL);
        AppLessonInfoDTO appLessonInfoDTO = appLessonInfoMapper.toDto(updatedAppLessonInfo);

        restAppLessonInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appLessonInfoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appLessonInfoDTO))
            )
            .andExpect(status().isOk());

        // Validate the AppLessonInfo in the database
        List<AppLessonInfo> appLessonInfoList = appLessonInfoRepository.findAll();
        assertThat(appLessonInfoList).hasSize(databaseSizeBeforeUpdate);
        AppLessonInfo testAppLessonInfo = appLessonInfoList.get(appLessonInfoList.size() - 1);
        assertThat(testAppLessonInfo.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAppLessonInfo.getPdfUrl()).isEqualTo(UPDATED_PDF_URL);
    }

    @Test
    @Transactional
    void putNonExistingAppLessonInfo() throws Exception {
        int databaseSizeBeforeUpdate = appLessonInfoRepository.findAll().size();
        appLessonInfo.setId(count.incrementAndGet());

        // Create the AppLessonInfo
        AppLessonInfoDTO appLessonInfoDTO = appLessonInfoMapper.toDto(appLessonInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppLessonInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appLessonInfoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appLessonInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppLessonInfo in the database
        List<AppLessonInfo> appLessonInfoList = appLessonInfoRepository.findAll();
        assertThat(appLessonInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAppLessonInfo() throws Exception {
        int databaseSizeBeforeUpdate = appLessonInfoRepository.findAll().size();
        appLessonInfo.setId(count.incrementAndGet());

        // Create the AppLessonInfo
        AppLessonInfoDTO appLessonInfoDTO = appLessonInfoMapper.toDto(appLessonInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppLessonInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appLessonInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppLessonInfo in the database
        List<AppLessonInfo> appLessonInfoList = appLessonInfoRepository.findAll();
        assertThat(appLessonInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAppLessonInfo() throws Exception {
        int databaseSizeBeforeUpdate = appLessonInfoRepository.findAll().size();
        appLessonInfo.setId(count.incrementAndGet());

        // Create the AppLessonInfo
        AppLessonInfoDTO appLessonInfoDTO = appLessonInfoMapper.toDto(appLessonInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppLessonInfoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appLessonInfoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppLessonInfo in the database
        List<AppLessonInfo> appLessonInfoList = appLessonInfoRepository.findAll();
        assertThat(appLessonInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAppLessonInfoWithPatch() throws Exception {
        // Initialize the database
        appLessonInfoRepository.saveAndFlush(appLessonInfo);

        int databaseSizeBeforeUpdate = appLessonInfoRepository.findAll().size();

        // Update the appLessonInfo using partial update
        AppLessonInfo partialUpdatedAppLessonInfo = new AppLessonInfo();
        partialUpdatedAppLessonInfo.setId(appLessonInfo.getId());

        restAppLessonInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppLessonInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAppLessonInfo))
            )
            .andExpect(status().isOk());

        // Validate the AppLessonInfo in the database
        List<AppLessonInfo> appLessonInfoList = appLessonInfoRepository.findAll();
        assertThat(appLessonInfoList).hasSize(databaseSizeBeforeUpdate);
        AppLessonInfo testAppLessonInfo = appLessonInfoList.get(appLessonInfoList.size() - 1);
        assertThat(testAppLessonInfo.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAppLessonInfo.getPdfUrl()).isEqualTo(DEFAULT_PDF_URL);
    }

    @Test
    @Transactional
    void fullUpdateAppLessonInfoWithPatch() throws Exception {
        // Initialize the database
        appLessonInfoRepository.saveAndFlush(appLessonInfo);

        int databaseSizeBeforeUpdate = appLessonInfoRepository.findAll().size();

        // Update the appLessonInfo using partial update
        AppLessonInfo partialUpdatedAppLessonInfo = new AppLessonInfo();
        partialUpdatedAppLessonInfo.setId(appLessonInfo.getId());

        partialUpdatedAppLessonInfo.description(UPDATED_DESCRIPTION).pdfUrl(UPDATED_PDF_URL);

        restAppLessonInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppLessonInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAppLessonInfo))
            )
            .andExpect(status().isOk());

        // Validate the AppLessonInfo in the database
        List<AppLessonInfo> appLessonInfoList = appLessonInfoRepository.findAll();
        assertThat(appLessonInfoList).hasSize(databaseSizeBeforeUpdate);
        AppLessonInfo testAppLessonInfo = appLessonInfoList.get(appLessonInfoList.size() - 1);
        assertThat(testAppLessonInfo.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAppLessonInfo.getPdfUrl()).isEqualTo(UPDATED_PDF_URL);
    }

    @Test
    @Transactional
    void patchNonExistingAppLessonInfo() throws Exception {
        int databaseSizeBeforeUpdate = appLessonInfoRepository.findAll().size();
        appLessonInfo.setId(count.incrementAndGet());

        // Create the AppLessonInfo
        AppLessonInfoDTO appLessonInfoDTO = appLessonInfoMapper.toDto(appLessonInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppLessonInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, appLessonInfoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(appLessonInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppLessonInfo in the database
        List<AppLessonInfo> appLessonInfoList = appLessonInfoRepository.findAll();
        assertThat(appLessonInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAppLessonInfo() throws Exception {
        int databaseSizeBeforeUpdate = appLessonInfoRepository.findAll().size();
        appLessonInfo.setId(count.incrementAndGet());

        // Create the AppLessonInfo
        AppLessonInfoDTO appLessonInfoDTO = appLessonInfoMapper.toDto(appLessonInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppLessonInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(appLessonInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppLessonInfo in the database
        List<AppLessonInfo> appLessonInfoList = appLessonInfoRepository.findAll();
        assertThat(appLessonInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAppLessonInfo() throws Exception {
        int databaseSizeBeforeUpdate = appLessonInfoRepository.findAll().size();
        appLessonInfo.setId(count.incrementAndGet());

        // Create the AppLessonInfo
        AppLessonInfoDTO appLessonInfoDTO = appLessonInfoMapper.toDto(appLessonInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppLessonInfoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(appLessonInfoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppLessonInfo in the database
        List<AppLessonInfo> appLessonInfoList = appLessonInfoRepository.findAll();
        assertThat(appLessonInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAppLessonInfo() throws Exception {
        // Initialize the database
        appLessonInfoRepository.saveAndFlush(appLessonInfo);

        int databaseSizeBeforeDelete = appLessonInfoRepository.findAll().size();

        // Delete the appLessonInfo
        restAppLessonInfoMockMvc
            .perform(delete(ENTITY_API_URL_ID, appLessonInfo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AppLessonInfo> appLessonInfoList = appLessonInfoRepository.findAll();
        assertThat(appLessonInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
