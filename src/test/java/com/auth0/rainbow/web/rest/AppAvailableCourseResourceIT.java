package com.auth0.rainbow.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.auth0.rainbow.IntegrationTest;
import com.auth0.rainbow.domain.AppAvailableCourse;
import com.auth0.rainbow.repository.AppAvailableCourseRepository;
import com.auth0.rainbow.service.dto.AppAvailableCourseDTO;
import com.auth0.rainbow.service.mapper.AppAvailableCourseMapper;
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
 * Integration tests for the {@link AppAvailableCourseResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AppAvailableCourseResourceIT {

    private static final String ENTITY_API_URL = "/api/app-available-courses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AppAvailableCourseRepository appAvailableCourseRepository;

    @Autowired
    private AppAvailableCourseMapper appAvailableCourseMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAppAvailableCourseMockMvc;

    private AppAvailableCourse appAvailableCourse;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppAvailableCourse createEntity(EntityManager em) {
        AppAvailableCourse appAvailableCourse = new AppAvailableCourse();
        return appAvailableCourse;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppAvailableCourse createUpdatedEntity(EntityManager em) {
        AppAvailableCourse appAvailableCourse = new AppAvailableCourse();
        return appAvailableCourse;
    }

    @BeforeEach
    public void initTest() {
        appAvailableCourse = createEntity(em);
    }

    @Test
    @Transactional
    void createAppAvailableCourse() throws Exception {
        int databaseSizeBeforeCreate = appAvailableCourseRepository.findAll().size();
        // Create the AppAvailableCourse
        AppAvailableCourseDTO appAvailableCourseDTO = appAvailableCourseMapper.toDto(appAvailableCourse);
        restAppAvailableCourseMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appAvailableCourseDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AppAvailableCourse in the database
        List<AppAvailableCourse> appAvailableCourseList = appAvailableCourseRepository.findAll();
        assertThat(appAvailableCourseList).hasSize(databaseSizeBeforeCreate + 1);
        AppAvailableCourse testAppAvailableCourse = appAvailableCourseList.get(appAvailableCourseList.size() - 1);
    }

    @Test
    @Transactional
    void createAppAvailableCourseWithExistingId() throws Exception {
        // Create the AppAvailableCourse with an existing ID
        appAvailableCourse.setId(1L);
        AppAvailableCourseDTO appAvailableCourseDTO = appAvailableCourseMapper.toDto(appAvailableCourse);

        int databaseSizeBeforeCreate = appAvailableCourseRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppAvailableCourseMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appAvailableCourseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppAvailableCourse in the database
        List<AppAvailableCourse> appAvailableCourseList = appAvailableCourseRepository.findAll();
        assertThat(appAvailableCourseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAppAvailableCourses() throws Exception {
        // Initialize the database
        appAvailableCourseRepository.saveAndFlush(appAvailableCourse);

        // Get all the appAvailableCourseList
        restAppAvailableCourseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appAvailableCourse.getId().intValue())));
    }

    @Test
    @Transactional
    void getAppAvailableCourse() throws Exception {
        // Initialize the database
        appAvailableCourseRepository.saveAndFlush(appAvailableCourse);

        // Get the appAvailableCourse
        restAppAvailableCourseMockMvc
            .perform(get(ENTITY_API_URL_ID, appAvailableCourse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(appAvailableCourse.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingAppAvailableCourse() throws Exception {
        // Get the appAvailableCourse
        restAppAvailableCourseMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAppAvailableCourse() throws Exception {
        // Initialize the database
        appAvailableCourseRepository.saveAndFlush(appAvailableCourse);

        int databaseSizeBeforeUpdate = appAvailableCourseRepository.findAll().size();

        // Update the appAvailableCourse
        AppAvailableCourse updatedAppAvailableCourse = appAvailableCourseRepository.findById(appAvailableCourse.getId()).get();
        // Disconnect from session so that the updates on updatedAppAvailableCourse are not directly saved in db
        em.detach(updatedAppAvailableCourse);
        AppAvailableCourseDTO appAvailableCourseDTO = appAvailableCourseMapper.toDto(updatedAppAvailableCourse);

        restAppAvailableCourseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appAvailableCourseDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appAvailableCourseDTO))
            )
            .andExpect(status().isOk());

        // Validate the AppAvailableCourse in the database
        List<AppAvailableCourse> appAvailableCourseList = appAvailableCourseRepository.findAll();
        assertThat(appAvailableCourseList).hasSize(databaseSizeBeforeUpdate);
        AppAvailableCourse testAppAvailableCourse = appAvailableCourseList.get(appAvailableCourseList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingAppAvailableCourse() throws Exception {
        int databaseSizeBeforeUpdate = appAvailableCourseRepository.findAll().size();
        appAvailableCourse.setId(count.incrementAndGet());

        // Create the AppAvailableCourse
        AppAvailableCourseDTO appAvailableCourseDTO = appAvailableCourseMapper.toDto(appAvailableCourse);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppAvailableCourseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appAvailableCourseDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appAvailableCourseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppAvailableCourse in the database
        List<AppAvailableCourse> appAvailableCourseList = appAvailableCourseRepository.findAll();
        assertThat(appAvailableCourseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAppAvailableCourse() throws Exception {
        int databaseSizeBeforeUpdate = appAvailableCourseRepository.findAll().size();
        appAvailableCourse.setId(count.incrementAndGet());

        // Create the AppAvailableCourse
        AppAvailableCourseDTO appAvailableCourseDTO = appAvailableCourseMapper.toDto(appAvailableCourse);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppAvailableCourseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appAvailableCourseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppAvailableCourse in the database
        List<AppAvailableCourse> appAvailableCourseList = appAvailableCourseRepository.findAll();
        assertThat(appAvailableCourseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAppAvailableCourse() throws Exception {
        int databaseSizeBeforeUpdate = appAvailableCourseRepository.findAll().size();
        appAvailableCourse.setId(count.incrementAndGet());

        // Create the AppAvailableCourse
        AppAvailableCourseDTO appAvailableCourseDTO = appAvailableCourseMapper.toDto(appAvailableCourse);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppAvailableCourseMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appAvailableCourseDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppAvailableCourse in the database
        List<AppAvailableCourse> appAvailableCourseList = appAvailableCourseRepository.findAll();
        assertThat(appAvailableCourseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAppAvailableCourseWithPatch() throws Exception {
        // Initialize the database
        appAvailableCourseRepository.saveAndFlush(appAvailableCourse);

        int databaseSizeBeforeUpdate = appAvailableCourseRepository.findAll().size();

        // Update the appAvailableCourse using partial update
        AppAvailableCourse partialUpdatedAppAvailableCourse = new AppAvailableCourse();
        partialUpdatedAppAvailableCourse.setId(appAvailableCourse.getId());

        restAppAvailableCourseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppAvailableCourse.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAppAvailableCourse))
            )
            .andExpect(status().isOk());

        // Validate the AppAvailableCourse in the database
        List<AppAvailableCourse> appAvailableCourseList = appAvailableCourseRepository.findAll();
        assertThat(appAvailableCourseList).hasSize(databaseSizeBeforeUpdate);
        AppAvailableCourse testAppAvailableCourse = appAvailableCourseList.get(appAvailableCourseList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateAppAvailableCourseWithPatch() throws Exception {
        // Initialize the database
        appAvailableCourseRepository.saveAndFlush(appAvailableCourse);

        int databaseSizeBeforeUpdate = appAvailableCourseRepository.findAll().size();

        // Update the appAvailableCourse using partial update
        AppAvailableCourse partialUpdatedAppAvailableCourse = new AppAvailableCourse();
        partialUpdatedAppAvailableCourse.setId(appAvailableCourse.getId());

        restAppAvailableCourseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppAvailableCourse.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAppAvailableCourse))
            )
            .andExpect(status().isOk());

        // Validate the AppAvailableCourse in the database
        List<AppAvailableCourse> appAvailableCourseList = appAvailableCourseRepository.findAll();
        assertThat(appAvailableCourseList).hasSize(databaseSizeBeforeUpdate);
        AppAvailableCourse testAppAvailableCourse = appAvailableCourseList.get(appAvailableCourseList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingAppAvailableCourse() throws Exception {
        int databaseSizeBeforeUpdate = appAvailableCourseRepository.findAll().size();
        appAvailableCourse.setId(count.incrementAndGet());

        // Create the AppAvailableCourse
        AppAvailableCourseDTO appAvailableCourseDTO = appAvailableCourseMapper.toDto(appAvailableCourse);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppAvailableCourseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, appAvailableCourseDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(appAvailableCourseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppAvailableCourse in the database
        List<AppAvailableCourse> appAvailableCourseList = appAvailableCourseRepository.findAll();
        assertThat(appAvailableCourseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAppAvailableCourse() throws Exception {
        int databaseSizeBeforeUpdate = appAvailableCourseRepository.findAll().size();
        appAvailableCourse.setId(count.incrementAndGet());

        // Create the AppAvailableCourse
        AppAvailableCourseDTO appAvailableCourseDTO = appAvailableCourseMapper.toDto(appAvailableCourse);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppAvailableCourseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(appAvailableCourseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppAvailableCourse in the database
        List<AppAvailableCourse> appAvailableCourseList = appAvailableCourseRepository.findAll();
        assertThat(appAvailableCourseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAppAvailableCourse() throws Exception {
        int databaseSizeBeforeUpdate = appAvailableCourseRepository.findAll().size();
        appAvailableCourse.setId(count.incrementAndGet());

        // Create the AppAvailableCourse
        AppAvailableCourseDTO appAvailableCourseDTO = appAvailableCourseMapper.toDto(appAvailableCourse);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppAvailableCourseMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(appAvailableCourseDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppAvailableCourse in the database
        List<AppAvailableCourse> appAvailableCourseList = appAvailableCourseRepository.findAll();
        assertThat(appAvailableCourseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAppAvailableCourse() throws Exception {
        // Initialize the database
        appAvailableCourseRepository.saveAndFlush(appAvailableCourse);

        int databaseSizeBeforeDelete = appAvailableCourseRepository.findAll().size();

        // Delete the appAvailableCourse
        restAppAvailableCourseMockMvc
            .perform(delete(ENTITY_API_URL_ID, appAvailableCourse.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AppAvailableCourse> appAvailableCourseList = appAvailableCourseRepository.findAll();
        assertThat(appAvailableCourseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
