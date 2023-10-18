package com.auth0.rainbow.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.auth0.rainbow.IntegrationTest;
import com.auth0.rainbow.domain.AppLessonPDF;
import com.auth0.rainbow.repository.AppLessonPDFRepository;
import com.auth0.rainbow.service.dto.AppLessonPDFDTO;
import com.auth0.rainbow.service.mapper.AppLessonPDFMapper;
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
 * Integration tests for the {@link AppLessonPDFResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AppLessonPDFResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_PDF_URL = "AAAAAAAAAA";
    private static final String UPDATED_PDF_URL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/app-lesson-pdfs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AppLessonPDFRepository appLessonPDFRepository;

    @Autowired
    private AppLessonPDFMapper appLessonPDFMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAppLessonPDFMockMvc;

    private AppLessonPDF appLessonPDF;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppLessonPDF createEntity(EntityManager em) {
        AppLessonPDF appLessonPDF = new AppLessonPDF().description(DEFAULT_DESCRIPTION).pdfUrl(DEFAULT_PDF_URL);
        return appLessonPDF;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppLessonPDF createUpdatedEntity(EntityManager em) {
        AppLessonPDF appLessonPDF = new AppLessonPDF().description(UPDATED_DESCRIPTION).pdfUrl(UPDATED_PDF_URL);
        return appLessonPDF;
    }

    @BeforeEach
    public void initTest() {
        appLessonPDF = createEntity(em);
    }

    @Test
    @Transactional
    void createAppLessonPDF() throws Exception {
        int databaseSizeBeforeCreate = appLessonPDFRepository.findAll().size();
        // Create the AppLessonPDF
        AppLessonPDFDTO appLessonPDFDTO = appLessonPDFMapper.toDto(appLessonPDF);
        restAppLessonPDFMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appLessonPDFDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AppLessonPDF in the database
        List<AppLessonPDF> appLessonPDFList = appLessonPDFRepository.findAll();
        assertThat(appLessonPDFList).hasSize(databaseSizeBeforeCreate + 1);
        AppLessonPDF testAppLessonPDF = appLessonPDFList.get(appLessonPDFList.size() - 1);
        assertThat(testAppLessonPDF.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAppLessonPDF.getPdfUrl()).isEqualTo(DEFAULT_PDF_URL);
    }

    @Test
    @Transactional
    void createAppLessonPDFWithExistingId() throws Exception {
        // Create the AppLessonPDF with an existing ID
        appLessonPDF.setId(1L);
        AppLessonPDFDTO appLessonPDFDTO = appLessonPDFMapper.toDto(appLessonPDF);

        int databaseSizeBeforeCreate = appLessonPDFRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppLessonPDFMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appLessonPDFDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppLessonPDF in the database
        List<AppLessonPDF> appLessonPDFList = appLessonPDFRepository.findAll();
        assertThat(appLessonPDFList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAppLessonPDFS() throws Exception {
        // Initialize the database
        appLessonPDFRepository.saveAndFlush(appLessonPDF);

        // Get all the appLessonPDFList
        restAppLessonPDFMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appLessonPDF.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].pdfUrl").value(hasItem(DEFAULT_PDF_URL)));
    }

    @Test
    @Transactional
    void getAppLessonPDF() throws Exception {
        // Initialize the database
        appLessonPDFRepository.saveAndFlush(appLessonPDF);

        // Get the appLessonPDF
        restAppLessonPDFMockMvc
            .perform(get(ENTITY_API_URL_ID, appLessonPDF.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(appLessonPDF.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.pdfUrl").value(DEFAULT_PDF_URL));
    }

    @Test
    @Transactional
    void getNonExistingAppLessonPDF() throws Exception {
        // Get the appLessonPDF
        restAppLessonPDFMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAppLessonPDF() throws Exception {
        // Initialize the database
        appLessonPDFRepository.saveAndFlush(appLessonPDF);

        int databaseSizeBeforeUpdate = appLessonPDFRepository.findAll().size();

        // Update the appLessonPDF
        AppLessonPDF updatedAppLessonPDF = appLessonPDFRepository.findById(appLessonPDF.getId()).get();
        // Disconnect from session so that the updates on updatedAppLessonPDF are not directly saved in db
        em.detach(updatedAppLessonPDF);
        updatedAppLessonPDF.description(UPDATED_DESCRIPTION).pdfUrl(UPDATED_PDF_URL);
        AppLessonPDFDTO appLessonPDFDTO = appLessonPDFMapper.toDto(updatedAppLessonPDF);

        restAppLessonPDFMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appLessonPDFDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appLessonPDFDTO))
            )
            .andExpect(status().isOk());

        // Validate the AppLessonPDF in the database
        List<AppLessonPDF> appLessonPDFList = appLessonPDFRepository.findAll();
        assertThat(appLessonPDFList).hasSize(databaseSizeBeforeUpdate);
        AppLessonPDF testAppLessonPDF = appLessonPDFList.get(appLessonPDFList.size() - 1);
        assertThat(testAppLessonPDF.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAppLessonPDF.getPdfUrl()).isEqualTo(UPDATED_PDF_URL);
    }

    @Test
    @Transactional
    void putNonExistingAppLessonPDF() throws Exception {
        int databaseSizeBeforeUpdate = appLessonPDFRepository.findAll().size();
        appLessonPDF.setId(count.incrementAndGet());

        // Create the AppLessonPDF
        AppLessonPDFDTO appLessonPDFDTO = appLessonPDFMapper.toDto(appLessonPDF);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppLessonPDFMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appLessonPDFDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appLessonPDFDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppLessonPDF in the database
        List<AppLessonPDF> appLessonPDFList = appLessonPDFRepository.findAll();
        assertThat(appLessonPDFList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAppLessonPDF() throws Exception {
        int databaseSizeBeforeUpdate = appLessonPDFRepository.findAll().size();
        appLessonPDF.setId(count.incrementAndGet());

        // Create the AppLessonPDF
        AppLessonPDFDTO appLessonPDFDTO = appLessonPDFMapper.toDto(appLessonPDF);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppLessonPDFMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appLessonPDFDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppLessonPDF in the database
        List<AppLessonPDF> appLessonPDFList = appLessonPDFRepository.findAll();
        assertThat(appLessonPDFList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAppLessonPDF() throws Exception {
        int databaseSizeBeforeUpdate = appLessonPDFRepository.findAll().size();
        appLessonPDF.setId(count.incrementAndGet());

        // Create the AppLessonPDF
        AppLessonPDFDTO appLessonPDFDTO = appLessonPDFMapper.toDto(appLessonPDF);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppLessonPDFMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appLessonPDFDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppLessonPDF in the database
        List<AppLessonPDF> appLessonPDFList = appLessonPDFRepository.findAll();
        assertThat(appLessonPDFList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAppLessonPDFWithPatch() throws Exception {
        // Initialize the database
        appLessonPDFRepository.saveAndFlush(appLessonPDF);

        int databaseSizeBeforeUpdate = appLessonPDFRepository.findAll().size();

        // Update the appLessonPDF using partial update
        AppLessonPDF partialUpdatedAppLessonPDF = new AppLessonPDF();
        partialUpdatedAppLessonPDF.setId(appLessonPDF.getId());

        partialUpdatedAppLessonPDF.description(UPDATED_DESCRIPTION).pdfUrl(UPDATED_PDF_URL);

        restAppLessonPDFMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppLessonPDF.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAppLessonPDF))
            )
            .andExpect(status().isOk());

        // Validate the AppLessonPDF in the database
        List<AppLessonPDF> appLessonPDFList = appLessonPDFRepository.findAll();
        assertThat(appLessonPDFList).hasSize(databaseSizeBeforeUpdate);
        AppLessonPDF testAppLessonPDF = appLessonPDFList.get(appLessonPDFList.size() - 1);
        assertThat(testAppLessonPDF.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAppLessonPDF.getPdfUrl()).isEqualTo(UPDATED_PDF_URL);
    }

    @Test
    @Transactional
    void fullUpdateAppLessonPDFWithPatch() throws Exception {
        // Initialize the database
        appLessonPDFRepository.saveAndFlush(appLessonPDF);

        int databaseSizeBeforeUpdate = appLessonPDFRepository.findAll().size();

        // Update the appLessonPDF using partial update
        AppLessonPDF partialUpdatedAppLessonPDF = new AppLessonPDF();
        partialUpdatedAppLessonPDF.setId(appLessonPDF.getId());

        partialUpdatedAppLessonPDF.description(UPDATED_DESCRIPTION).pdfUrl(UPDATED_PDF_URL);

        restAppLessonPDFMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppLessonPDF.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAppLessonPDF))
            )
            .andExpect(status().isOk());

        // Validate the AppLessonPDF in the database
        List<AppLessonPDF> appLessonPDFList = appLessonPDFRepository.findAll();
        assertThat(appLessonPDFList).hasSize(databaseSizeBeforeUpdate);
        AppLessonPDF testAppLessonPDF = appLessonPDFList.get(appLessonPDFList.size() - 1);
        assertThat(testAppLessonPDF.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAppLessonPDF.getPdfUrl()).isEqualTo(UPDATED_PDF_URL);
    }

    @Test
    @Transactional
    void patchNonExistingAppLessonPDF() throws Exception {
        int databaseSizeBeforeUpdate = appLessonPDFRepository.findAll().size();
        appLessonPDF.setId(count.incrementAndGet());

        // Create the AppLessonPDF
        AppLessonPDFDTO appLessonPDFDTO = appLessonPDFMapper.toDto(appLessonPDF);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppLessonPDFMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, appLessonPDFDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(appLessonPDFDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppLessonPDF in the database
        List<AppLessonPDF> appLessonPDFList = appLessonPDFRepository.findAll();
        assertThat(appLessonPDFList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAppLessonPDF() throws Exception {
        int databaseSizeBeforeUpdate = appLessonPDFRepository.findAll().size();
        appLessonPDF.setId(count.incrementAndGet());

        // Create the AppLessonPDF
        AppLessonPDFDTO appLessonPDFDTO = appLessonPDFMapper.toDto(appLessonPDF);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppLessonPDFMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(appLessonPDFDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppLessonPDF in the database
        List<AppLessonPDF> appLessonPDFList = appLessonPDFRepository.findAll();
        assertThat(appLessonPDFList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAppLessonPDF() throws Exception {
        int databaseSizeBeforeUpdate = appLessonPDFRepository.findAll().size();
        appLessonPDF.setId(count.incrementAndGet());

        // Create the AppLessonPDF
        AppLessonPDFDTO appLessonPDFDTO = appLessonPDFMapper.toDto(appLessonPDF);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppLessonPDFMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(appLessonPDFDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppLessonPDF in the database
        List<AppLessonPDF> appLessonPDFList = appLessonPDFRepository.findAll();
        assertThat(appLessonPDFList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAppLessonPDF() throws Exception {
        // Initialize the database
        appLessonPDFRepository.saveAndFlush(appLessonPDF);

        int databaseSizeBeforeDelete = appLessonPDFRepository.findAll().size();

        // Delete the appLessonPDF
        restAppLessonPDFMockMvc
            .perform(delete(ENTITY_API_URL_ID, appLessonPDF.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AppLessonPDF> appLessonPDFList = appLessonPDFRepository.findAll();
        assertThat(appLessonPDFList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
