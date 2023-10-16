package com.auth0.rainbow.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.auth0.rainbow.IntegrationTest;
import com.auth0.rainbow.domain.AppCategory;
import com.auth0.rainbow.repository.AppCategoryRepository;
import com.auth0.rainbow.service.dto.AppCategoryDTO;
import com.auth0.rainbow.service.mapper.AppCategoryMapper;
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
 * Integration tests for the {@link AppCategoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AppCategoryResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/app-categories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AppCategoryRepository appCategoryRepository;

    @Autowired
    private AppCategoryMapper appCategoryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAppCategoryMockMvc;

    private AppCategory appCategory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppCategory createEntity(EntityManager em) {
        AppCategory appCategory = new AppCategory().name(DEFAULT_NAME);
        return appCategory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppCategory createUpdatedEntity(EntityManager em) {
        AppCategory appCategory = new AppCategory().name(UPDATED_NAME);
        return appCategory;
    }

    @BeforeEach
    public void initTest() {
        appCategory = createEntity(em);
    }

    @Test
    @Transactional
    void createAppCategory() throws Exception {
        int databaseSizeBeforeCreate = appCategoryRepository.findAll().size();
        // Create the AppCategory
        AppCategoryDTO appCategoryDTO = appCategoryMapper.toDto(appCategory);
        restAppCategoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appCategoryDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AppCategory in the database
        List<AppCategory> appCategoryList = appCategoryRepository.findAll();
        assertThat(appCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        AppCategory testAppCategory = appCategoryList.get(appCategoryList.size() - 1);
        assertThat(testAppCategory.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createAppCategoryWithExistingId() throws Exception {
        // Create the AppCategory with an existing ID
        appCategory.setId(1L);
        AppCategoryDTO appCategoryDTO = appCategoryMapper.toDto(appCategory);

        int databaseSizeBeforeCreate = appCategoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppCategoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppCategory in the database
        List<AppCategory> appCategoryList = appCategoryRepository.findAll();
        assertThat(appCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAppCategories() throws Exception {
        // Initialize the database
        appCategoryRepository.saveAndFlush(appCategory);

        // Get all the appCategoryList
        restAppCategoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getAppCategory() throws Exception {
        // Initialize the database
        appCategoryRepository.saveAndFlush(appCategory);

        // Get the appCategory
        restAppCategoryMockMvc
            .perform(get(ENTITY_API_URL_ID, appCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(appCategory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingAppCategory() throws Exception {
        // Get the appCategory
        restAppCategoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAppCategory() throws Exception {
        // Initialize the database
        appCategoryRepository.saveAndFlush(appCategory);

        int databaseSizeBeforeUpdate = appCategoryRepository.findAll().size();

        // Update the appCategory
        AppCategory updatedAppCategory = appCategoryRepository.findById(appCategory.getId()).get();
        // Disconnect from session so that the updates on updatedAppCategory are not directly saved in db
        em.detach(updatedAppCategory);
        updatedAppCategory.name(UPDATED_NAME);
        AppCategoryDTO appCategoryDTO = appCategoryMapper.toDto(updatedAppCategory);

        restAppCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appCategoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appCategoryDTO))
            )
            .andExpect(status().isOk());

        // Validate the AppCategory in the database
        List<AppCategory> appCategoryList = appCategoryRepository.findAll();
        assertThat(appCategoryList).hasSize(databaseSizeBeforeUpdate);
        AppCategory testAppCategory = appCategoryList.get(appCategoryList.size() - 1);
        assertThat(testAppCategory.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingAppCategory() throws Exception {
        int databaseSizeBeforeUpdate = appCategoryRepository.findAll().size();
        appCategory.setId(count.incrementAndGet());

        // Create the AppCategory
        AppCategoryDTO appCategoryDTO = appCategoryMapper.toDto(appCategory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appCategoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppCategory in the database
        List<AppCategory> appCategoryList = appCategoryRepository.findAll();
        assertThat(appCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAppCategory() throws Exception {
        int databaseSizeBeforeUpdate = appCategoryRepository.findAll().size();
        appCategory.setId(count.incrementAndGet());

        // Create the AppCategory
        AppCategoryDTO appCategoryDTO = appCategoryMapper.toDto(appCategory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppCategory in the database
        List<AppCategory> appCategoryList = appCategoryRepository.findAll();
        assertThat(appCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAppCategory() throws Exception {
        int databaseSizeBeforeUpdate = appCategoryRepository.findAll().size();
        appCategory.setId(count.incrementAndGet());

        // Create the AppCategory
        AppCategoryDTO appCategoryDTO = appCategoryMapper.toDto(appCategory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppCategoryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appCategoryDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppCategory in the database
        List<AppCategory> appCategoryList = appCategoryRepository.findAll();
        assertThat(appCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAppCategoryWithPatch() throws Exception {
        // Initialize the database
        appCategoryRepository.saveAndFlush(appCategory);

        int databaseSizeBeforeUpdate = appCategoryRepository.findAll().size();

        // Update the appCategory using partial update
        AppCategory partialUpdatedAppCategory = new AppCategory();
        partialUpdatedAppCategory.setId(appCategory.getId());

        restAppCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppCategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAppCategory))
            )
            .andExpect(status().isOk());

        // Validate the AppCategory in the database
        List<AppCategory> appCategoryList = appCategoryRepository.findAll();
        assertThat(appCategoryList).hasSize(databaseSizeBeforeUpdate);
        AppCategory testAppCategory = appCategoryList.get(appCategoryList.size() - 1);
        assertThat(testAppCategory.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void fullUpdateAppCategoryWithPatch() throws Exception {
        // Initialize the database
        appCategoryRepository.saveAndFlush(appCategory);

        int databaseSizeBeforeUpdate = appCategoryRepository.findAll().size();

        // Update the appCategory using partial update
        AppCategory partialUpdatedAppCategory = new AppCategory();
        partialUpdatedAppCategory.setId(appCategory.getId());

        partialUpdatedAppCategory.name(UPDATED_NAME);

        restAppCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppCategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAppCategory))
            )
            .andExpect(status().isOk());

        // Validate the AppCategory in the database
        List<AppCategory> appCategoryList = appCategoryRepository.findAll();
        assertThat(appCategoryList).hasSize(databaseSizeBeforeUpdate);
        AppCategory testAppCategory = appCategoryList.get(appCategoryList.size() - 1);
        assertThat(testAppCategory.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingAppCategory() throws Exception {
        int databaseSizeBeforeUpdate = appCategoryRepository.findAll().size();
        appCategory.setId(count.incrementAndGet());

        // Create the AppCategory
        AppCategoryDTO appCategoryDTO = appCategoryMapper.toDto(appCategory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, appCategoryDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(appCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppCategory in the database
        List<AppCategory> appCategoryList = appCategoryRepository.findAll();
        assertThat(appCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAppCategory() throws Exception {
        int databaseSizeBeforeUpdate = appCategoryRepository.findAll().size();
        appCategory.setId(count.incrementAndGet());

        // Create the AppCategory
        AppCategoryDTO appCategoryDTO = appCategoryMapper.toDto(appCategory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(appCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppCategory in the database
        List<AppCategory> appCategoryList = appCategoryRepository.findAll();
        assertThat(appCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAppCategory() throws Exception {
        int databaseSizeBeforeUpdate = appCategoryRepository.findAll().size();
        appCategory.setId(count.incrementAndGet());

        // Create the AppCategory
        AppCategoryDTO appCategoryDTO = appCategoryMapper.toDto(appCategory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(appCategoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppCategory in the database
        List<AppCategory> appCategoryList = appCategoryRepository.findAll();
        assertThat(appCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAppCategory() throws Exception {
        // Initialize the database
        appCategoryRepository.saveAndFlush(appCategory);

        int databaseSizeBeforeDelete = appCategoryRepository.findAll().size();

        // Delete the appCategory
        restAppCategoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, appCategory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AppCategory> appCategoryList = appCategoryRepository.findAll();
        assertThat(appCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
