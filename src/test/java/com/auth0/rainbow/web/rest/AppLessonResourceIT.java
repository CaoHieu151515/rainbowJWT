package com.auth0.rainbow.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.auth0.rainbow.IntegrationTest;
import com.auth0.rainbow.domain.AppLesson;
import com.auth0.rainbow.repository.AppLessonRepository;
import com.auth0.rainbow.service.dto.AppLessonDTO;
import com.auth0.rainbow.service.mapper.AppLessonMapper;
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
 * Integration tests for the {@link AppLessonResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AppLessonResourceIT {

    private static final String ENTITY_API_URL = "/api/app-lessons";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AppLessonRepository appLessonRepository;

    @Autowired
    private AppLessonMapper appLessonMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAppLessonMockMvc;

    private AppLesson appLesson;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppLesson createEntity(EntityManager em) {
        AppLesson appLesson = new AppLesson();
        return appLesson;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppLesson createUpdatedEntity(EntityManager em) {
        AppLesson appLesson = new AppLesson();
        return appLesson;
    }

    @BeforeEach
    public void initTest() {
        appLesson = createEntity(em);
    }

    @Test
    @Transactional
    void createAppLesson() throws Exception {
        int databaseSizeBeforeCreate = appLessonRepository.findAll().size();
        // Create the AppLesson
        AppLessonDTO appLessonDTO = appLessonMapper.toDto(appLesson);
        restAppLessonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appLessonDTO)))
            .andExpect(status().isCreated());

        // Validate the AppLesson in the database
        List<AppLesson> appLessonList = appLessonRepository.findAll();
        assertThat(appLessonList).hasSize(databaseSizeBeforeCreate + 1);
        AppLesson testAppLesson = appLessonList.get(appLessonList.size() - 1);
    }

    @Test
    @Transactional
    void createAppLessonWithExistingId() throws Exception {
        // Create the AppLesson with an existing ID
        appLesson.setId(1L);
        AppLessonDTO appLessonDTO = appLessonMapper.toDto(appLesson);

        int databaseSizeBeforeCreate = appLessonRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppLessonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appLessonDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AppLesson in the database
        List<AppLesson> appLessonList = appLessonRepository.findAll();
        assertThat(appLessonList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAppLessons() throws Exception {
        // Initialize the database
        appLessonRepository.saveAndFlush(appLesson);

        // Get all the appLessonList
        restAppLessonMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appLesson.getId().intValue())));
    }

    @Test
    @Transactional
    void getAppLesson() throws Exception {
        // Initialize the database
        appLessonRepository.saveAndFlush(appLesson);

        // Get the appLesson
        restAppLessonMockMvc
            .perform(get(ENTITY_API_URL_ID, appLesson.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(appLesson.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingAppLesson() throws Exception {
        // Get the appLesson
        restAppLessonMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAppLesson() throws Exception {
        // Initialize the database
        appLessonRepository.saveAndFlush(appLesson);

        int databaseSizeBeforeUpdate = appLessonRepository.findAll().size();

        // Update the appLesson
        AppLesson updatedAppLesson = appLessonRepository.findById(appLesson.getId()).get();
        // Disconnect from session so that the updates on updatedAppLesson are not directly saved in db
        em.detach(updatedAppLesson);
        AppLessonDTO appLessonDTO = appLessonMapper.toDto(updatedAppLesson);

        restAppLessonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appLessonDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appLessonDTO))
            )
            .andExpect(status().isOk());

        // Validate the AppLesson in the database
        List<AppLesson> appLessonList = appLessonRepository.findAll();
        assertThat(appLessonList).hasSize(databaseSizeBeforeUpdate);
        AppLesson testAppLesson = appLessonList.get(appLessonList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingAppLesson() throws Exception {
        int databaseSizeBeforeUpdate = appLessonRepository.findAll().size();
        appLesson.setId(count.incrementAndGet());

        // Create the AppLesson
        AppLessonDTO appLessonDTO = appLessonMapper.toDto(appLesson);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppLessonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appLessonDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appLessonDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppLesson in the database
        List<AppLesson> appLessonList = appLessonRepository.findAll();
        assertThat(appLessonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAppLesson() throws Exception {
        int databaseSizeBeforeUpdate = appLessonRepository.findAll().size();
        appLesson.setId(count.incrementAndGet());

        // Create the AppLesson
        AppLessonDTO appLessonDTO = appLessonMapper.toDto(appLesson);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppLessonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appLessonDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppLesson in the database
        List<AppLesson> appLessonList = appLessonRepository.findAll();
        assertThat(appLessonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAppLesson() throws Exception {
        int databaseSizeBeforeUpdate = appLessonRepository.findAll().size();
        appLesson.setId(count.incrementAndGet());

        // Create the AppLesson
        AppLessonDTO appLessonDTO = appLessonMapper.toDto(appLesson);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppLessonMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appLessonDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppLesson in the database
        List<AppLesson> appLessonList = appLessonRepository.findAll();
        assertThat(appLessonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAppLessonWithPatch() throws Exception {
        // Initialize the database
        appLessonRepository.saveAndFlush(appLesson);

        int databaseSizeBeforeUpdate = appLessonRepository.findAll().size();

        // Update the appLesson using partial update
        AppLesson partialUpdatedAppLesson = new AppLesson();
        partialUpdatedAppLesson.setId(appLesson.getId());

        restAppLessonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppLesson.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAppLesson))
            )
            .andExpect(status().isOk());

        // Validate the AppLesson in the database
        List<AppLesson> appLessonList = appLessonRepository.findAll();
        assertThat(appLessonList).hasSize(databaseSizeBeforeUpdate);
        AppLesson testAppLesson = appLessonList.get(appLessonList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateAppLessonWithPatch() throws Exception {
        // Initialize the database
        appLessonRepository.saveAndFlush(appLesson);

        int databaseSizeBeforeUpdate = appLessonRepository.findAll().size();

        // Update the appLesson using partial update
        AppLesson partialUpdatedAppLesson = new AppLesson();
        partialUpdatedAppLesson.setId(appLesson.getId());

        restAppLessonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppLesson.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAppLesson))
            )
            .andExpect(status().isOk());

        // Validate the AppLesson in the database
        List<AppLesson> appLessonList = appLessonRepository.findAll();
        assertThat(appLessonList).hasSize(databaseSizeBeforeUpdate);
        AppLesson testAppLesson = appLessonList.get(appLessonList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingAppLesson() throws Exception {
        int databaseSizeBeforeUpdate = appLessonRepository.findAll().size();
        appLesson.setId(count.incrementAndGet());

        // Create the AppLesson
        AppLessonDTO appLessonDTO = appLessonMapper.toDto(appLesson);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppLessonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, appLessonDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(appLessonDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppLesson in the database
        List<AppLesson> appLessonList = appLessonRepository.findAll();
        assertThat(appLessonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAppLesson() throws Exception {
        int databaseSizeBeforeUpdate = appLessonRepository.findAll().size();
        appLesson.setId(count.incrementAndGet());

        // Create the AppLesson
        AppLessonDTO appLessonDTO = appLessonMapper.toDto(appLesson);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppLessonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(appLessonDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppLesson in the database
        List<AppLesson> appLessonList = appLessonRepository.findAll();
        assertThat(appLessonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAppLesson() throws Exception {
        int databaseSizeBeforeUpdate = appLessonRepository.findAll().size();
        appLesson.setId(count.incrementAndGet());

        // Create the AppLesson
        AppLessonDTO appLessonDTO = appLessonMapper.toDto(appLesson);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppLessonMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(appLessonDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppLesson in the database
        List<AppLesson> appLessonList = appLessonRepository.findAll();
        assertThat(appLessonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAppLesson() throws Exception {
        // Initialize the database
        appLessonRepository.saveAndFlush(appLesson);

        int databaseSizeBeforeDelete = appLessonRepository.findAll().size();

        // Delete the appLesson
        restAppLessonMockMvc
            .perform(delete(ENTITY_API_URL_ID, appLesson.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AppLesson> appLessonList = appLessonRepository.findAll();
        assertThat(appLessonList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
