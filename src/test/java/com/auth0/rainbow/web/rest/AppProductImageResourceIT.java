package com.auth0.rainbow.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.auth0.rainbow.IntegrationTest;
import com.auth0.rainbow.domain.AppProductImage;
import com.auth0.rainbow.repository.AppProductImageRepository;
import com.auth0.rainbow.service.dto.AppProductImageDTO;
import com.auth0.rainbow.service.mapper.AppProductImageMapper;
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
 * Integration tests for the {@link AppProductImageResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AppProductImageResourceIT {

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/app-product-images";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AppProductImageRepository appProductImageRepository;

    @Autowired
    private AppProductImageMapper appProductImageMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAppProductImageMockMvc;

    private AppProductImage appProductImage;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppProductImage createEntity(EntityManager em) {
        AppProductImage appProductImage = new AppProductImage().imageUrl(DEFAULT_IMAGE_URL);
        return appProductImage;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppProductImage createUpdatedEntity(EntityManager em) {
        AppProductImage appProductImage = new AppProductImage().imageUrl(UPDATED_IMAGE_URL);
        return appProductImage;
    }

    @BeforeEach
    public void initTest() {
        appProductImage = createEntity(em);
    }

    @Test
    @Transactional
    void createAppProductImage() throws Exception {
        int databaseSizeBeforeCreate = appProductImageRepository.findAll().size();
        // Create the AppProductImage
        AppProductImageDTO appProductImageDTO = appProductImageMapper.toDto(appProductImage);
        restAppProductImageMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appProductImageDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AppProductImage in the database
        List<AppProductImage> appProductImageList = appProductImageRepository.findAll();
        assertThat(appProductImageList).hasSize(databaseSizeBeforeCreate + 1);
        AppProductImage testAppProductImage = appProductImageList.get(appProductImageList.size() - 1);
        assertThat(testAppProductImage.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
    }

    @Test
    @Transactional
    void createAppProductImageWithExistingId() throws Exception {
        // Create the AppProductImage with an existing ID
        appProductImage.setId(1L);
        AppProductImageDTO appProductImageDTO = appProductImageMapper.toDto(appProductImage);

        int databaseSizeBeforeCreate = appProductImageRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppProductImageMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appProductImageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppProductImage in the database
        List<AppProductImage> appProductImageList = appProductImageRepository.findAll();
        assertThat(appProductImageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAppProductImages() throws Exception {
        // Initialize the database
        appProductImageRepository.saveAndFlush(appProductImage);

        // Get all the appProductImageList
        restAppProductImageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appProductImage.getId().intValue())))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)));
    }

    @Test
    @Transactional
    void getAppProductImage() throws Exception {
        // Initialize the database
        appProductImageRepository.saveAndFlush(appProductImage);

        // Get the appProductImage
        restAppProductImageMockMvc
            .perform(get(ENTITY_API_URL_ID, appProductImage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(appProductImage.getId().intValue()))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL));
    }

    @Test
    @Transactional
    void getNonExistingAppProductImage() throws Exception {
        // Get the appProductImage
        restAppProductImageMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAppProductImage() throws Exception {
        // Initialize the database
        appProductImageRepository.saveAndFlush(appProductImage);

        int databaseSizeBeforeUpdate = appProductImageRepository.findAll().size();

        // Update the appProductImage
        AppProductImage updatedAppProductImage = appProductImageRepository.findById(appProductImage.getId()).get();
        // Disconnect from session so that the updates on updatedAppProductImage are not directly saved in db
        em.detach(updatedAppProductImage);
        updatedAppProductImage.imageUrl(UPDATED_IMAGE_URL);
        AppProductImageDTO appProductImageDTO = appProductImageMapper.toDto(updatedAppProductImage);

        restAppProductImageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appProductImageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appProductImageDTO))
            )
            .andExpect(status().isOk());

        // Validate the AppProductImage in the database
        List<AppProductImage> appProductImageList = appProductImageRepository.findAll();
        assertThat(appProductImageList).hasSize(databaseSizeBeforeUpdate);
        AppProductImage testAppProductImage = appProductImageList.get(appProductImageList.size() - 1);
        assertThat(testAppProductImage.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    void putNonExistingAppProductImage() throws Exception {
        int databaseSizeBeforeUpdate = appProductImageRepository.findAll().size();
        appProductImage.setId(count.incrementAndGet());

        // Create the AppProductImage
        AppProductImageDTO appProductImageDTO = appProductImageMapper.toDto(appProductImage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppProductImageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appProductImageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appProductImageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppProductImage in the database
        List<AppProductImage> appProductImageList = appProductImageRepository.findAll();
        assertThat(appProductImageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAppProductImage() throws Exception {
        int databaseSizeBeforeUpdate = appProductImageRepository.findAll().size();
        appProductImage.setId(count.incrementAndGet());

        // Create the AppProductImage
        AppProductImageDTO appProductImageDTO = appProductImageMapper.toDto(appProductImage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppProductImageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appProductImageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppProductImage in the database
        List<AppProductImage> appProductImageList = appProductImageRepository.findAll();
        assertThat(appProductImageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAppProductImage() throws Exception {
        int databaseSizeBeforeUpdate = appProductImageRepository.findAll().size();
        appProductImage.setId(count.incrementAndGet());

        // Create the AppProductImage
        AppProductImageDTO appProductImageDTO = appProductImageMapper.toDto(appProductImage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppProductImageMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appProductImageDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppProductImage in the database
        List<AppProductImage> appProductImageList = appProductImageRepository.findAll();
        assertThat(appProductImageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAppProductImageWithPatch() throws Exception {
        // Initialize the database
        appProductImageRepository.saveAndFlush(appProductImage);

        int databaseSizeBeforeUpdate = appProductImageRepository.findAll().size();

        // Update the appProductImage using partial update
        AppProductImage partialUpdatedAppProductImage = new AppProductImage();
        partialUpdatedAppProductImage.setId(appProductImage.getId());

        restAppProductImageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppProductImage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAppProductImage))
            )
            .andExpect(status().isOk());

        // Validate the AppProductImage in the database
        List<AppProductImage> appProductImageList = appProductImageRepository.findAll();
        assertThat(appProductImageList).hasSize(databaseSizeBeforeUpdate);
        AppProductImage testAppProductImage = appProductImageList.get(appProductImageList.size() - 1);
        assertThat(testAppProductImage.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
    }

    @Test
    @Transactional
    void fullUpdateAppProductImageWithPatch() throws Exception {
        // Initialize the database
        appProductImageRepository.saveAndFlush(appProductImage);

        int databaseSizeBeforeUpdate = appProductImageRepository.findAll().size();

        // Update the appProductImage using partial update
        AppProductImage partialUpdatedAppProductImage = new AppProductImage();
        partialUpdatedAppProductImage.setId(appProductImage.getId());

        partialUpdatedAppProductImage.imageUrl(UPDATED_IMAGE_URL);

        restAppProductImageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppProductImage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAppProductImage))
            )
            .andExpect(status().isOk());

        // Validate the AppProductImage in the database
        List<AppProductImage> appProductImageList = appProductImageRepository.findAll();
        assertThat(appProductImageList).hasSize(databaseSizeBeforeUpdate);
        AppProductImage testAppProductImage = appProductImageList.get(appProductImageList.size() - 1);
        assertThat(testAppProductImage.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    void patchNonExistingAppProductImage() throws Exception {
        int databaseSizeBeforeUpdate = appProductImageRepository.findAll().size();
        appProductImage.setId(count.incrementAndGet());

        // Create the AppProductImage
        AppProductImageDTO appProductImageDTO = appProductImageMapper.toDto(appProductImage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppProductImageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, appProductImageDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(appProductImageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppProductImage in the database
        List<AppProductImage> appProductImageList = appProductImageRepository.findAll();
        assertThat(appProductImageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAppProductImage() throws Exception {
        int databaseSizeBeforeUpdate = appProductImageRepository.findAll().size();
        appProductImage.setId(count.incrementAndGet());

        // Create the AppProductImage
        AppProductImageDTO appProductImageDTO = appProductImageMapper.toDto(appProductImage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppProductImageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(appProductImageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppProductImage in the database
        List<AppProductImage> appProductImageList = appProductImageRepository.findAll();
        assertThat(appProductImageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAppProductImage() throws Exception {
        int databaseSizeBeforeUpdate = appProductImageRepository.findAll().size();
        appProductImage.setId(count.incrementAndGet());

        // Create the AppProductImage
        AppProductImageDTO appProductImageDTO = appProductImageMapper.toDto(appProductImage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppProductImageMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(appProductImageDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppProductImage in the database
        List<AppProductImage> appProductImageList = appProductImageRepository.findAll();
        assertThat(appProductImageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAppProductImage() throws Exception {
        // Initialize the database
        appProductImageRepository.saveAndFlush(appProductImage);

        int databaseSizeBeforeDelete = appProductImageRepository.findAll().size();

        // Delete the appProductImage
        restAppProductImageMockMvc
            .perform(delete(ENTITY_API_URL_ID, appProductImage.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AppProductImage> appProductImageList = appProductImageRepository.findAll();
        assertThat(appProductImageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
