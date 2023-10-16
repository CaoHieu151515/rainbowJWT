package com.auth0.rainbow.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.auth0.rainbow.IntegrationTest;
import com.auth0.rainbow.domain.AppPostImage;
import com.auth0.rainbow.repository.AppPostImageRepository;
import com.auth0.rainbow.service.dto.AppPostImageDTO;
import com.auth0.rainbow.service.mapper.AppPostImageMapper;
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
 * Integration tests for the {@link AppPostImageResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AppPostImageResourceIT {

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/app-post-images";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AppPostImageRepository appPostImageRepository;

    @Autowired
    private AppPostImageMapper appPostImageMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAppPostImageMockMvc;

    private AppPostImage appPostImage;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppPostImage createEntity(EntityManager em) {
        AppPostImage appPostImage = new AppPostImage().imageUrl(DEFAULT_IMAGE_URL);
        return appPostImage;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppPostImage createUpdatedEntity(EntityManager em) {
        AppPostImage appPostImage = new AppPostImage().imageUrl(UPDATED_IMAGE_URL);
        return appPostImage;
    }

    @BeforeEach
    public void initTest() {
        appPostImage = createEntity(em);
    }

    @Test
    @Transactional
    void createAppPostImage() throws Exception {
        int databaseSizeBeforeCreate = appPostImageRepository.findAll().size();
        // Create the AppPostImage
        AppPostImageDTO appPostImageDTO = appPostImageMapper.toDto(appPostImage);
        restAppPostImageMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appPostImageDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AppPostImage in the database
        List<AppPostImage> appPostImageList = appPostImageRepository.findAll();
        assertThat(appPostImageList).hasSize(databaseSizeBeforeCreate + 1);
        AppPostImage testAppPostImage = appPostImageList.get(appPostImageList.size() - 1);
        assertThat(testAppPostImage.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
    }

    @Test
    @Transactional
    void createAppPostImageWithExistingId() throws Exception {
        // Create the AppPostImage with an existing ID
        appPostImage.setId(1L);
        AppPostImageDTO appPostImageDTO = appPostImageMapper.toDto(appPostImage);

        int databaseSizeBeforeCreate = appPostImageRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppPostImageMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appPostImageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppPostImage in the database
        List<AppPostImage> appPostImageList = appPostImageRepository.findAll();
        assertThat(appPostImageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAppPostImages() throws Exception {
        // Initialize the database
        appPostImageRepository.saveAndFlush(appPostImage);

        // Get all the appPostImageList
        restAppPostImageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appPostImage.getId().intValue())))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)));
    }

    @Test
    @Transactional
    void getAppPostImage() throws Exception {
        // Initialize the database
        appPostImageRepository.saveAndFlush(appPostImage);

        // Get the appPostImage
        restAppPostImageMockMvc
            .perform(get(ENTITY_API_URL_ID, appPostImage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(appPostImage.getId().intValue()))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL));
    }

    @Test
    @Transactional
    void getNonExistingAppPostImage() throws Exception {
        // Get the appPostImage
        restAppPostImageMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAppPostImage() throws Exception {
        // Initialize the database
        appPostImageRepository.saveAndFlush(appPostImage);

        int databaseSizeBeforeUpdate = appPostImageRepository.findAll().size();

        // Update the appPostImage
        AppPostImage updatedAppPostImage = appPostImageRepository.findById(appPostImage.getId()).get();
        // Disconnect from session so that the updates on updatedAppPostImage are not directly saved in db
        em.detach(updatedAppPostImage);
        updatedAppPostImage.imageUrl(UPDATED_IMAGE_URL);
        AppPostImageDTO appPostImageDTO = appPostImageMapper.toDto(updatedAppPostImage);

        restAppPostImageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appPostImageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appPostImageDTO))
            )
            .andExpect(status().isOk());

        // Validate the AppPostImage in the database
        List<AppPostImage> appPostImageList = appPostImageRepository.findAll();
        assertThat(appPostImageList).hasSize(databaseSizeBeforeUpdate);
        AppPostImage testAppPostImage = appPostImageList.get(appPostImageList.size() - 1);
        assertThat(testAppPostImage.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    void putNonExistingAppPostImage() throws Exception {
        int databaseSizeBeforeUpdate = appPostImageRepository.findAll().size();
        appPostImage.setId(count.incrementAndGet());

        // Create the AppPostImage
        AppPostImageDTO appPostImageDTO = appPostImageMapper.toDto(appPostImage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppPostImageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appPostImageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appPostImageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppPostImage in the database
        List<AppPostImage> appPostImageList = appPostImageRepository.findAll();
        assertThat(appPostImageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAppPostImage() throws Exception {
        int databaseSizeBeforeUpdate = appPostImageRepository.findAll().size();
        appPostImage.setId(count.incrementAndGet());

        // Create the AppPostImage
        AppPostImageDTO appPostImageDTO = appPostImageMapper.toDto(appPostImage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppPostImageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appPostImageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppPostImage in the database
        List<AppPostImage> appPostImageList = appPostImageRepository.findAll();
        assertThat(appPostImageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAppPostImage() throws Exception {
        int databaseSizeBeforeUpdate = appPostImageRepository.findAll().size();
        appPostImage.setId(count.incrementAndGet());

        // Create the AppPostImage
        AppPostImageDTO appPostImageDTO = appPostImageMapper.toDto(appPostImage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppPostImageMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appPostImageDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppPostImage in the database
        List<AppPostImage> appPostImageList = appPostImageRepository.findAll();
        assertThat(appPostImageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAppPostImageWithPatch() throws Exception {
        // Initialize the database
        appPostImageRepository.saveAndFlush(appPostImage);

        int databaseSizeBeforeUpdate = appPostImageRepository.findAll().size();

        // Update the appPostImage using partial update
        AppPostImage partialUpdatedAppPostImage = new AppPostImage();
        partialUpdatedAppPostImage.setId(appPostImage.getId());

        partialUpdatedAppPostImage.imageUrl(UPDATED_IMAGE_URL);

        restAppPostImageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppPostImage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAppPostImage))
            )
            .andExpect(status().isOk());

        // Validate the AppPostImage in the database
        List<AppPostImage> appPostImageList = appPostImageRepository.findAll();
        assertThat(appPostImageList).hasSize(databaseSizeBeforeUpdate);
        AppPostImage testAppPostImage = appPostImageList.get(appPostImageList.size() - 1);
        assertThat(testAppPostImage.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    void fullUpdateAppPostImageWithPatch() throws Exception {
        // Initialize the database
        appPostImageRepository.saveAndFlush(appPostImage);

        int databaseSizeBeforeUpdate = appPostImageRepository.findAll().size();

        // Update the appPostImage using partial update
        AppPostImage partialUpdatedAppPostImage = new AppPostImage();
        partialUpdatedAppPostImage.setId(appPostImage.getId());

        partialUpdatedAppPostImage.imageUrl(UPDATED_IMAGE_URL);

        restAppPostImageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppPostImage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAppPostImage))
            )
            .andExpect(status().isOk());

        // Validate the AppPostImage in the database
        List<AppPostImage> appPostImageList = appPostImageRepository.findAll();
        assertThat(appPostImageList).hasSize(databaseSizeBeforeUpdate);
        AppPostImage testAppPostImage = appPostImageList.get(appPostImageList.size() - 1);
        assertThat(testAppPostImage.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    void patchNonExistingAppPostImage() throws Exception {
        int databaseSizeBeforeUpdate = appPostImageRepository.findAll().size();
        appPostImage.setId(count.incrementAndGet());

        // Create the AppPostImage
        AppPostImageDTO appPostImageDTO = appPostImageMapper.toDto(appPostImage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppPostImageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, appPostImageDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(appPostImageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppPostImage in the database
        List<AppPostImage> appPostImageList = appPostImageRepository.findAll();
        assertThat(appPostImageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAppPostImage() throws Exception {
        int databaseSizeBeforeUpdate = appPostImageRepository.findAll().size();
        appPostImage.setId(count.incrementAndGet());

        // Create the AppPostImage
        AppPostImageDTO appPostImageDTO = appPostImageMapper.toDto(appPostImage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppPostImageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(appPostImageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppPostImage in the database
        List<AppPostImage> appPostImageList = appPostImageRepository.findAll();
        assertThat(appPostImageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAppPostImage() throws Exception {
        int databaseSizeBeforeUpdate = appPostImageRepository.findAll().size();
        appPostImage.setId(count.incrementAndGet());

        // Create the AppPostImage
        AppPostImageDTO appPostImageDTO = appPostImageMapper.toDto(appPostImage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppPostImageMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(appPostImageDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppPostImage in the database
        List<AppPostImage> appPostImageList = appPostImageRepository.findAll();
        assertThat(appPostImageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAppPostImage() throws Exception {
        // Initialize the database
        appPostImageRepository.saveAndFlush(appPostImage);

        int databaseSizeBeforeDelete = appPostImageRepository.findAll().size();

        // Delete the appPostImage
        restAppPostImageMockMvc
            .perform(delete(ENTITY_API_URL_ID, appPostImage.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AppPostImage> appPostImageList = appPostImageRepository.findAll();
        assertThat(appPostImageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
