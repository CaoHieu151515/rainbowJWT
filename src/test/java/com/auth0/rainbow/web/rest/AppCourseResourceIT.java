package com.auth0.rainbow.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.auth0.rainbow.IntegrationTest;
import com.auth0.rainbow.domain.AppCourse;
import com.auth0.rainbow.repository.AppCourseRepository;
import com.auth0.rainbow.service.dto.AppCourseDTO;
import com.auth0.rainbow.service.mapper.AppCourseMapper;
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
 * Integration tests for the {@link AppCourseResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AppCourseResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LEVEL = "AAAAAAAAAA";
    private static final String UPDATED_LEVEL = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/app-courses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AppCourseRepository appCourseRepository;

    @Autowired
    private AppCourseMapper appCourseMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAppCourseMockMvc;

    private AppCourse appCourse;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppCourse createEntity(EntityManager em) {
        AppCourse appCourse = new AppCourse().name(DEFAULT_NAME).level(DEFAULT_LEVEL).image(DEFAULT_IMAGE);
        return appCourse;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppCourse createUpdatedEntity(EntityManager em) {
        AppCourse appCourse = new AppCourse().name(UPDATED_NAME).level(UPDATED_LEVEL).image(UPDATED_IMAGE);
        return appCourse;
    }

    @BeforeEach
    public void initTest() {
        appCourse = createEntity(em);
    }

    @Test
    @Transactional
    void createAppCourse() throws Exception {
        int databaseSizeBeforeCreate = appCourseRepository.findAll().size();
        // Create the AppCourse
        AppCourseDTO appCourseDTO = appCourseMapper.toDto(appCourse);
        restAppCourseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appCourseDTO)))
            .andExpect(status().isCreated());

        // Validate the AppCourse in the database
        List<AppCourse> appCourseList = appCourseRepository.findAll();
        assertThat(appCourseList).hasSize(databaseSizeBeforeCreate + 1);
        AppCourse testAppCourse = appCourseList.get(appCourseList.size() - 1);
        assertThat(testAppCourse.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAppCourse.getLevel()).isEqualTo(DEFAULT_LEVEL);
        assertThat(testAppCourse.getImage()).isEqualTo(DEFAULT_IMAGE);
    }

    @Test
    @Transactional
    void createAppCourseWithExistingId() throws Exception {
        // Create the AppCourse with an existing ID
        appCourse.setId(1L);
        AppCourseDTO appCourseDTO = appCourseMapper.toDto(appCourse);

        int databaseSizeBeforeCreate = appCourseRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppCourseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appCourseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AppCourse in the database
        List<AppCourse> appCourseList = appCourseRepository.findAll();
        assertThat(appCourseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAppCourses() throws Exception {
        // Initialize the database
        appCourseRepository.saveAndFlush(appCourse);

        // Get all the appCourseList
        restAppCourseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appCourse.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE)));
    }

    @Test
    @Transactional
    void getAppCourse() throws Exception {
        // Initialize the database
        appCourseRepository.saveAndFlush(appCourse);

        // Get the appCourse
        restAppCourseMockMvc
            .perform(get(ENTITY_API_URL_ID, appCourse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(appCourse.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL))
            .andExpect(jsonPath("$.image").value(DEFAULT_IMAGE));
    }

    @Test
    @Transactional
    void getNonExistingAppCourse() throws Exception {
        // Get the appCourse
        restAppCourseMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAppCourse() throws Exception {
        // Initialize the database
        appCourseRepository.saveAndFlush(appCourse);

        int databaseSizeBeforeUpdate = appCourseRepository.findAll().size();

        // Update the appCourse
        AppCourse updatedAppCourse = appCourseRepository.findById(appCourse.getId()).get();
        // Disconnect from session so that the updates on updatedAppCourse are not directly saved in db
        em.detach(updatedAppCourse);
        updatedAppCourse.name(UPDATED_NAME).level(UPDATED_LEVEL).image(UPDATED_IMAGE);
        AppCourseDTO appCourseDTO = appCourseMapper.toDto(updatedAppCourse);

        restAppCourseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appCourseDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appCourseDTO))
            )
            .andExpect(status().isOk());

        // Validate the AppCourse in the database
        List<AppCourse> appCourseList = appCourseRepository.findAll();
        assertThat(appCourseList).hasSize(databaseSizeBeforeUpdate);
        AppCourse testAppCourse = appCourseList.get(appCourseList.size() - 1);
        assertThat(testAppCourse.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAppCourse.getLevel()).isEqualTo(UPDATED_LEVEL);
        assertThat(testAppCourse.getImage()).isEqualTo(UPDATED_IMAGE);
    }

    @Test
    @Transactional
    void putNonExistingAppCourse() throws Exception {
        int databaseSizeBeforeUpdate = appCourseRepository.findAll().size();
        appCourse.setId(count.incrementAndGet());

        // Create the AppCourse
        AppCourseDTO appCourseDTO = appCourseMapper.toDto(appCourse);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppCourseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appCourseDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appCourseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppCourse in the database
        List<AppCourse> appCourseList = appCourseRepository.findAll();
        assertThat(appCourseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAppCourse() throws Exception {
        int databaseSizeBeforeUpdate = appCourseRepository.findAll().size();
        appCourse.setId(count.incrementAndGet());

        // Create the AppCourse
        AppCourseDTO appCourseDTO = appCourseMapper.toDto(appCourse);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppCourseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appCourseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppCourse in the database
        List<AppCourse> appCourseList = appCourseRepository.findAll();
        assertThat(appCourseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAppCourse() throws Exception {
        int databaseSizeBeforeUpdate = appCourseRepository.findAll().size();
        appCourse.setId(count.incrementAndGet());

        // Create the AppCourse
        AppCourseDTO appCourseDTO = appCourseMapper.toDto(appCourse);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppCourseMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appCourseDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppCourse in the database
        List<AppCourse> appCourseList = appCourseRepository.findAll();
        assertThat(appCourseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAppCourseWithPatch() throws Exception {
        // Initialize the database
        appCourseRepository.saveAndFlush(appCourse);

        int databaseSizeBeforeUpdate = appCourseRepository.findAll().size();

        // Update the appCourse using partial update
        AppCourse partialUpdatedAppCourse = new AppCourse();
        partialUpdatedAppCourse.setId(appCourse.getId());

        partialUpdatedAppCourse.name(UPDATED_NAME).level(UPDATED_LEVEL);

        restAppCourseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppCourse.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAppCourse))
            )
            .andExpect(status().isOk());

        // Validate the AppCourse in the database
        List<AppCourse> appCourseList = appCourseRepository.findAll();
        assertThat(appCourseList).hasSize(databaseSizeBeforeUpdate);
        AppCourse testAppCourse = appCourseList.get(appCourseList.size() - 1);
        assertThat(testAppCourse.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAppCourse.getLevel()).isEqualTo(UPDATED_LEVEL);
        assertThat(testAppCourse.getImage()).isEqualTo(DEFAULT_IMAGE);
    }

    @Test
    @Transactional
    void fullUpdateAppCourseWithPatch() throws Exception {
        // Initialize the database
        appCourseRepository.saveAndFlush(appCourse);

        int databaseSizeBeforeUpdate = appCourseRepository.findAll().size();

        // Update the appCourse using partial update
        AppCourse partialUpdatedAppCourse = new AppCourse();
        partialUpdatedAppCourse.setId(appCourse.getId());

        partialUpdatedAppCourse.name(UPDATED_NAME).level(UPDATED_LEVEL).image(UPDATED_IMAGE);

        restAppCourseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppCourse.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAppCourse))
            )
            .andExpect(status().isOk());

        // Validate the AppCourse in the database
        List<AppCourse> appCourseList = appCourseRepository.findAll();
        assertThat(appCourseList).hasSize(databaseSizeBeforeUpdate);
        AppCourse testAppCourse = appCourseList.get(appCourseList.size() - 1);
        assertThat(testAppCourse.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAppCourse.getLevel()).isEqualTo(UPDATED_LEVEL);
        assertThat(testAppCourse.getImage()).isEqualTo(UPDATED_IMAGE);
    }

    @Test
    @Transactional
    void patchNonExistingAppCourse() throws Exception {
        int databaseSizeBeforeUpdate = appCourseRepository.findAll().size();
        appCourse.setId(count.incrementAndGet());

        // Create the AppCourse
        AppCourseDTO appCourseDTO = appCourseMapper.toDto(appCourse);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppCourseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, appCourseDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(appCourseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppCourse in the database
        List<AppCourse> appCourseList = appCourseRepository.findAll();
        assertThat(appCourseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAppCourse() throws Exception {
        int databaseSizeBeforeUpdate = appCourseRepository.findAll().size();
        appCourse.setId(count.incrementAndGet());

        // Create the AppCourse
        AppCourseDTO appCourseDTO = appCourseMapper.toDto(appCourse);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppCourseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(appCourseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppCourse in the database
        List<AppCourse> appCourseList = appCourseRepository.findAll();
        assertThat(appCourseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAppCourse() throws Exception {
        int databaseSizeBeforeUpdate = appCourseRepository.findAll().size();
        appCourse.setId(count.incrementAndGet());

        // Create the AppCourse
        AppCourseDTO appCourseDTO = appCourseMapper.toDto(appCourse);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppCourseMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(appCourseDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppCourse in the database
        List<AppCourse> appCourseList = appCourseRepository.findAll();
        assertThat(appCourseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAppCourse() throws Exception {
        // Initialize the database
        appCourseRepository.saveAndFlush(appCourse);

        int databaseSizeBeforeDelete = appCourseRepository.findAll().size();

        // Delete the appCourse
        restAppCourseMockMvc
            .perform(delete(ENTITY_API_URL_ID, appCourse.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AppCourse> appCourseList = appCourseRepository.findAll();
        assertThat(appCourseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
