package com.auth0.rainbow.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.auth0.rainbow.IntegrationTest;
import com.auth0.rainbow.domain.AppQuestion;
import com.auth0.rainbow.repository.AppQuestionRepository;
import com.auth0.rainbow.service.dto.AppQuestionDTO;
import com.auth0.rainbow.service.mapper.AppQuestionMapper;
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
 * Integration tests for the {@link AppQuestionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AppQuestionResourceIT {

    private static final String DEFAULT_QUESTION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_QUESTION_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_QUESTION_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_QUESTION_TEXT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/app-questions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AppQuestionRepository appQuestionRepository;

    @Autowired
    private AppQuestionMapper appQuestionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAppQuestionMockMvc;

    private AppQuestion appQuestion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppQuestion createEntity(EntityManager em) {
        AppQuestion appQuestion = new AppQuestion().questionName(DEFAULT_QUESTION_NAME).questionText(DEFAULT_QUESTION_TEXT);
        return appQuestion;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppQuestion createUpdatedEntity(EntityManager em) {
        AppQuestion appQuestion = new AppQuestion().questionName(UPDATED_QUESTION_NAME).questionText(UPDATED_QUESTION_TEXT);
        return appQuestion;
    }

    @BeforeEach
    public void initTest() {
        appQuestion = createEntity(em);
    }

    @Test
    @Transactional
    void createAppQuestion() throws Exception {
        int databaseSizeBeforeCreate = appQuestionRepository.findAll().size();
        // Create the AppQuestion
        AppQuestionDTO appQuestionDTO = appQuestionMapper.toDto(appQuestion);
        restAppQuestionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appQuestionDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AppQuestion in the database
        List<AppQuestion> appQuestionList = appQuestionRepository.findAll();
        assertThat(appQuestionList).hasSize(databaseSizeBeforeCreate + 1);
        AppQuestion testAppQuestion = appQuestionList.get(appQuestionList.size() - 1);
        assertThat(testAppQuestion.getQuestionName()).isEqualTo(DEFAULT_QUESTION_NAME);
        assertThat(testAppQuestion.getQuestionText()).isEqualTo(DEFAULT_QUESTION_TEXT);
    }

    @Test
    @Transactional
    void createAppQuestionWithExistingId() throws Exception {
        // Create the AppQuestion with an existing ID
        appQuestion.setId(1L);
        AppQuestionDTO appQuestionDTO = appQuestionMapper.toDto(appQuestion);

        int databaseSizeBeforeCreate = appQuestionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppQuestionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appQuestionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppQuestion in the database
        List<AppQuestion> appQuestionList = appQuestionRepository.findAll();
        assertThat(appQuestionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAppQuestions() throws Exception {
        // Initialize the database
        appQuestionRepository.saveAndFlush(appQuestion);

        // Get all the appQuestionList
        restAppQuestionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appQuestion.getId().intValue())))
            .andExpect(jsonPath("$.[*].questionName").value(hasItem(DEFAULT_QUESTION_NAME)))
            .andExpect(jsonPath("$.[*].questionText").value(hasItem(DEFAULT_QUESTION_TEXT)));
    }

    @Test
    @Transactional
    void getAppQuestion() throws Exception {
        // Initialize the database
        appQuestionRepository.saveAndFlush(appQuestion);

        // Get the appQuestion
        restAppQuestionMockMvc
            .perform(get(ENTITY_API_URL_ID, appQuestion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(appQuestion.getId().intValue()))
            .andExpect(jsonPath("$.questionName").value(DEFAULT_QUESTION_NAME))
            .andExpect(jsonPath("$.questionText").value(DEFAULT_QUESTION_TEXT));
    }

    @Test
    @Transactional
    void getNonExistingAppQuestion() throws Exception {
        // Get the appQuestion
        restAppQuestionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAppQuestion() throws Exception {
        // Initialize the database
        appQuestionRepository.saveAndFlush(appQuestion);

        int databaseSizeBeforeUpdate = appQuestionRepository.findAll().size();

        // Update the appQuestion
        AppQuestion updatedAppQuestion = appQuestionRepository.findById(appQuestion.getId()).get();
        // Disconnect from session so that the updates on updatedAppQuestion are not directly saved in db
        em.detach(updatedAppQuestion);
        updatedAppQuestion.questionName(UPDATED_QUESTION_NAME).questionText(UPDATED_QUESTION_TEXT);
        AppQuestionDTO appQuestionDTO = appQuestionMapper.toDto(updatedAppQuestion);

        restAppQuestionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appQuestionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appQuestionDTO))
            )
            .andExpect(status().isOk());

        // Validate the AppQuestion in the database
        List<AppQuestion> appQuestionList = appQuestionRepository.findAll();
        assertThat(appQuestionList).hasSize(databaseSizeBeforeUpdate);
        AppQuestion testAppQuestion = appQuestionList.get(appQuestionList.size() - 1);
        assertThat(testAppQuestion.getQuestionName()).isEqualTo(UPDATED_QUESTION_NAME);
        assertThat(testAppQuestion.getQuestionText()).isEqualTo(UPDATED_QUESTION_TEXT);
    }

    @Test
    @Transactional
    void putNonExistingAppQuestion() throws Exception {
        int databaseSizeBeforeUpdate = appQuestionRepository.findAll().size();
        appQuestion.setId(count.incrementAndGet());

        // Create the AppQuestion
        AppQuestionDTO appQuestionDTO = appQuestionMapper.toDto(appQuestion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppQuestionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appQuestionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appQuestionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppQuestion in the database
        List<AppQuestion> appQuestionList = appQuestionRepository.findAll();
        assertThat(appQuestionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAppQuestion() throws Exception {
        int databaseSizeBeforeUpdate = appQuestionRepository.findAll().size();
        appQuestion.setId(count.incrementAndGet());

        // Create the AppQuestion
        AppQuestionDTO appQuestionDTO = appQuestionMapper.toDto(appQuestion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppQuestionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appQuestionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppQuestion in the database
        List<AppQuestion> appQuestionList = appQuestionRepository.findAll();
        assertThat(appQuestionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAppQuestion() throws Exception {
        int databaseSizeBeforeUpdate = appQuestionRepository.findAll().size();
        appQuestion.setId(count.incrementAndGet());

        // Create the AppQuestion
        AppQuestionDTO appQuestionDTO = appQuestionMapper.toDto(appQuestion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppQuestionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appQuestionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppQuestion in the database
        List<AppQuestion> appQuestionList = appQuestionRepository.findAll();
        assertThat(appQuestionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAppQuestionWithPatch() throws Exception {
        // Initialize the database
        appQuestionRepository.saveAndFlush(appQuestion);

        int databaseSizeBeforeUpdate = appQuestionRepository.findAll().size();

        // Update the appQuestion using partial update
        AppQuestion partialUpdatedAppQuestion = new AppQuestion();
        partialUpdatedAppQuestion.setId(appQuestion.getId());

        restAppQuestionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppQuestion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAppQuestion))
            )
            .andExpect(status().isOk());

        // Validate the AppQuestion in the database
        List<AppQuestion> appQuestionList = appQuestionRepository.findAll();
        assertThat(appQuestionList).hasSize(databaseSizeBeforeUpdate);
        AppQuestion testAppQuestion = appQuestionList.get(appQuestionList.size() - 1);
        assertThat(testAppQuestion.getQuestionName()).isEqualTo(DEFAULT_QUESTION_NAME);
        assertThat(testAppQuestion.getQuestionText()).isEqualTo(DEFAULT_QUESTION_TEXT);
    }

    @Test
    @Transactional
    void fullUpdateAppQuestionWithPatch() throws Exception {
        // Initialize the database
        appQuestionRepository.saveAndFlush(appQuestion);

        int databaseSizeBeforeUpdate = appQuestionRepository.findAll().size();

        // Update the appQuestion using partial update
        AppQuestion partialUpdatedAppQuestion = new AppQuestion();
        partialUpdatedAppQuestion.setId(appQuestion.getId());

        partialUpdatedAppQuestion.questionName(UPDATED_QUESTION_NAME).questionText(UPDATED_QUESTION_TEXT);

        restAppQuestionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppQuestion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAppQuestion))
            )
            .andExpect(status().isOk());

        // Validate the AppQuestion in the database
        List<AppQuestion> appQuestionList = appQuestionRepository.findAll();
        assertThat(appQuestionList).hasSize(databaseSizeBeforeUpdate);
        AppQuestion testAppQuestion = appQuestionList.get(appQuestionList.size() - 1);
        assertThat(testAppQuestion.getQuestionName()).isEqualTo(UPDATED_QUESTION_NAME);
        assertThat(testAppQuestion.getQuestionText()).isEqualTo(UPDATED_QUESTION_TEXT);
    }

    @Test
    @Transactional
    void patchNonExistingAppQuestion() throws Exception {
        int databaseSizeBeforeUpdate = appQuestionRepository.findAll().size();
        appQuestion.setId(count.incrementAndGet());

        // Create the AppQuestion
        AppQuestionDTO appQuestionDTO = appQuestionMapper.toDto(appQuestion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppQuestionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, appQuestionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(appQuestionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppQuestion in the database
        List<AppQuestion> appQuestionList = appQuestionRepository.findAll();
        assertThat(appQuestionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAppQuestion() throws Exception {
        int databaseSizeBeforeUpdate = appQuestionRepository.findAll().size();
        appQuestion.setId(count.incrementAndGet());

        // Create the AppQuestion
        AppQuestionDTO appQuestionDTO = appQuestionMapper.toDto(appQuestion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppQuestionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(appQuestionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppQuestion in the database
        List<AppQuestion> appQuestionList = appQuestionRepository.findAll();
        assertThat(appQuestionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAppQuestion() throws Exception {
        int databaseSizeBeforeUpdate = appQuestionRepository.findAll().size();
        appQuestion.setId(count.incrementAndGet());

        // Create the AppQuestion
        AppQuestionDTO appQuestionDTO = appQuestionMapper.toDto(appQuestion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppQuestionMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(appQuestionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppQuestion in the database
        List<AppQuestion> appQuestionList = appQuestionRepository.findAll();
        assertThat(appQuestionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAppQuestion() throws Exception {
        // Initialize the database
        appQuestionRepository.saveAndFlush(appQuestion);

        int databaseSizeBeforeDelete = appQuestionRepository.findAll().size();

        // Delete the appQuestion
        restAppQuestionMockMvc
            .perform(delete(ENTITY_API_URL_ID, appQuestion.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AppQuestion> appQuestionList = appQuestionRepository.findAll();
        assertThat(appQuestionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
