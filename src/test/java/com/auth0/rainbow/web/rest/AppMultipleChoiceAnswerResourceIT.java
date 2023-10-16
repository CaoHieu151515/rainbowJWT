package com.auth0.rainbow.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.auth0.rainbow.IntegrationTest;
import com.auth0.rainbow.domain.AppMultipleChoiceAnswer;
import com.auth0.rainbow.repository.AppMultipleChoiceAnswerRepository;
import com.auth0.rainbow.service.dto.AppMultipleChoiceAnswerDTO;
import com.auth0.rainbow.service.mapper.AppMultipleChoiceAnswerMapper;
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
 * Integration tests for the {@link AppMultipleChoiceAnswerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AppMultipleChoiceAnswerResourceIT {

    private static final String DEFAULT_ANSWER_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_ANSWER_TEXT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_CORRECT = false;
    private static final Boolean UPDATED_IS_CORRECT = true;

    private static final String ENTITY_API_URL = "/api/app-multiple-choice-answers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AppMultipleChoiceAnswerRepository appMultipleChoiceAnswerRepository;

    @Autowired
    private AppMultipleChoiceAnswerMapper appMultipleChoiceAnswerMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAppMultipleChoiceAnswerMockMvc;

    private AppMultipleChoiceAnswer appMultipleChoiceAnswer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppMultipleChoiceAnswer createEntity(EntityManager em) {
        AppMultipleChoiceAnswer appMultipleChoiceAnswer = new AppMultipleChoiceAnswer()
            .answerText(DEFAULT_ANSWER_TEXT)
            .isCorrect(DEFAULT_IS_CORRECT);
        return appMultipleChoiceAnswer;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppMultipleChoiceAnswer createUpdatedEntity(EntityManager em) {
        AppMultipleChoiceAnswer appMultipleChoiceAnswer = new AppMultipleChoiceAnswer()
            .answerText(UPDATED_ANSWER_TEXT)
            .isCorrect(UPDATED_IS_CORRECT);
        return appMultipleChoiceAnswer;
    }

    @BeforeEach
    public void initTest() {
        appMultipleChoiceAnswer = createEntity(em);
    }

    @Test
    @Transactional
    void createAppMultipleChoiceAnswer() throws Exception {
        int databaseSizeBeforeCreate = appMultipleChoiceAnswerRepository.findAll().size();
        // Create the AppMultipleChoiceAnswer
        AppMultipleChoiceAnswerDTO appMultipleChoiceAnswerDTO = appMultipleChoiceAnswerMapper.toDto(appMultipleChoiceAnswer);
        restAppMultipleChoiceAnswerMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appMultipleChoiceAnswerDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AppMultipleChoiceAnswer in the database
        List<AppMultipleChoiceAnswer> appMultipleChoiceAnswerList = appMultipleChoiceAnswerRepository.findAll();
        assertThat(appMultipleChoiceAnswerList).hasSize(databaseSizeBeforeCreate + 1);
        AppMultipleChoiceAnswer testAppMultipleChoiceAnswer = appMultipleChoiceAnswerList.get(appMultipleChoiceAnswerList.size() - 1);
        assertThat(testAppMultipleChoiceAnswer.getAnswerText()).isEqualTo(DEFAULT_ANSWER_TEXT);
        assertThat(testAppMultipleChoiceAnswer.getIsCorrect()).isEqualTo(DEFAULT_IS_CORRECT);
    }

    @Test
    @Transactional
    void createAppMultipleChoiceAnswerWithExistingId() throws Exception {
        // Create the AppMultipleChoiceAnswer with an existing ID
        appMultipleChoiceAnswer.setId(1L);
        AppMultipleChoiceAnswerDTO appMultipleChoiceAnswerDTO = appMultipleChoiceAnswerMapper.toDto(appMultipleChoiceAnswer);

        int databaseSizeBeforeCreate = appMultipleChoiceAnswerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppMultipleChoiceAnswerMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appMultipleChoiceAnswerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppMultipleChoiceAnswer in the database
        List<AppMultipleChoiceAnswer> appMultipleChoiceAnswerList = appMultipleChoiceAnswerRepository.findAll();
        assertThat(appMultipleChoiceAnswerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAppMultipleChoiceAnswers() throws Exception {
        // Initialize the database
        appMultipleChoiceAnswerRepository.saveAndFlush(appMultipleChoiceAnswer);

        // Get all the appMultipleChoiceAnswerList
        restAppMultipleChoiceAnswerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appMultipleChoiceAnswer.getId().intValue())))
            .andExpect(jsonPath("$.[*].answerText").value(hasItem(DEFAULT_ANSWER_TEXT)))
            .andExpect(jsonPath("$.[*].isCorrect").value(hasItem(DEFAULT_IS_CORRECT.booleanValue())));
    }

    @Test
    @Transactional
    void getAppMultipleChoiceAnswer() throws Exception {
        // Initialize the database
        appMultipleChoiceAnswerRepository.saveAndFlush(appMultipleChoiceAnswer);

        // Get the appMultipleChoiceAnswer
        restAppMultipleChoiceAnswerMockMvc
            .perform(get(ENTITY_API_URL_ID, appMultipleChoiceAnswer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(appMultipleChoiceAnswer.getId().intValue()))
            .andExpect(jsonPath("$.answerText").value(DEFAULT_ANSWER_TEXT))
            .andExpect(jsonPath("$.isCorrect").value(DEFAULT_IS_CORRECT.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingAppMultipleChoiceAnswer() throws Exception {
        // Get the appMultipleChoiceAnswer
        restAppMultipleChoiceAnswerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAppMultipleChoiceAnswer() throws Exception {
        // Initialize the database
        appMultipleChoiceAnswerRepository.saveAndFlush(appMultipleChoiceAnswer);

        int databaseSizeBeforeUpdate = appMultipleChoiceAnswerRepository.findAll().size();

        // Update the appMultipleChoiceAnswer
        AppMultipleChoiceAnswer updatedAppMultipleChoiceAnswer = appMultipleChoiceAnswerRepository
            .findById(appMultipleChoiceAnswer.getId())
            .get();
        // Disconnect from session so that the updates on updatedAppMultipleChoiceAnswer are not directly saved in db
        em.detach(updatedAppMultipleChoiceAnswer);
        updatedAppMultipleChoiceAnswer.answerText(UPDATED_ANSWER_TEXT).isCorrect(UPDATED_IS_CORRECT);
        AppMultipleChoiceAnswerDTO appMultipleChoiceAnswerDTO = appMultipleChoiceAnswerMapper.toDto(updatedAppMultipleChoiceAnswer);

        restAppMultipleChoiceAnswerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appMultipleChoiceAnswerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appMultipleChoiceAnswerDTO))
            )
            .andExpect(status().isOk());

        // Validate the AppMultipleChoiceAnswer in the database
        List<AppMultipleChoiceAnswer> appMultipleChoiceAnswerList = appMultipleChoiceAnswerRepository.findAll();
        assertThat(appMultipleChoiceAnswerList).hasSize(databaseSizeBeforeUpdate);
        AppMultipleChoiceAnswer testAppMultipleChoiceAnswer = appMultipleChoiceAnswerList.get(appMultipleChoiceAnswerList.size() - 1);
        assertThat(testAppMultipleChoiceAnswer.getAnswerText()).isEqualTo(UPDATED_ANSWER_TEXT);
        assertThat(testAppMultipleChoiceAnswer.getIsCorrect()).isEqualTo(UPDATED_IS_CORRECT);
    }

    @Test
    @Transactional
    void putNonExistingAppMultipleChoiceAnswer() throws Exception {
        int databaseSizeBeforeUpdate = appMultipleChoiceAnswerRepository.findAll().size();
        appMultipleChoiceAnswer.setId(count.incrementAndGet());

        // Create the AppMultipleChoiceAnswer
        AppMultipleChoiceAnswerDTO appMultipleChoiceAnswerDTO = appMultipleChoiceAnswerMapper.toDto(appMultipleChoiceAnswer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppMultipleChoiceAnswerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appMultipleChoiceAnswerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appMultipleChoiceAnswerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppMultipleChoiceAnswer in the database
        List<AppMultipleChoiceAnswer> appMultipleChoiceAnswerList = appMultipleChoiceAnswerRepository.findAll();
        assertThat(appMultipleChoiceAnswerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAppMultipleChoiceAnswer() throws Exception {
        int databaseSizeBeforeUpdate = appMultipleChoiceAnswerRepository.findAll().size();
        appMultipleChoiceAnswer.setId(count.incrementAndGet());

        // Create the AppMultipleChoiceAnswer
        AppMultipleChoiceAnswerDTO appMultipleChoiceAnswerDTO = appMultipleChoiceAnswerMapper.toDto(appMultipleChoiceAnswer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppMultipleChoiceAnswerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appMultipleChoiceAnswerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppMultipleChoiceAnswer in the database
        List<AppMultipleChoiceAnswer> appMultipleChoiceAnswerList = appMultipleChoiceAnswerRepository.findAll();
        assertThat(appMultipleChoiceAnswerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAppMultipleChoiceAnswer() throws Exception {
        int databaseSizeBeforeUpdate = appMultipleChoiceAnswerRepository.findAll().size();
        appMultipleChoiceAnswer.setId(count.incrementAndGet());

        // Create the AppMultipleChoiceAnswer
        AppMultipleChoiceAnswerDTO appMultipleChoiceAnswerDTO = appMultipleChoiceAnswerMapper.toDto(appMultipleChoiceAnswer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppMultipleChoiceAnswerMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appMultipleChoiceAnswerDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppMultipleChoiceAnswer in the database
        List<AppMultipleChoiceAnswer> appMultipleChoiceAnswerList = appMultipleChoiceAnswerRepository.findAll();
        assertThat(appMultipleChoiceAnswerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAppMultipleChoiceAnswerWithPatch() throws Exception {
        // Initialize the database
        appMultipleChoiceAnswerRepository.saveAndFlush(appMultipleChoiceAnswer);

        int databaseSizeBeforeUpdate = appMultipleChoiceAnswerRepository.findAll().size();

        // Update the appMultipleChoiceAnswer using partial update
        AppMultipleChoiceAnswer partialUpdatedAppMultipleChoiceAnswer = new AppMultipleChoiceAnswer();
        partialUpdatedAppMultipleChoiceAnswer.setId(appMultipleChoiceAnswer.getId());

        partialUpdatedAppMultipleChoiceAnswer.answerText(UPDATED_ANSWER_TEXT);

        restAppMultipleChoiceAnswerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppMultipleChoiceAnswer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAppMultipleChoiceAnswer))
            )
            .andExpect(status().isOk());

        // Validate the AppMultipleChoiceAnswer in the database
        List<AppMultipleChoiceAnswer> appMultipleChoiceAnswerList = appMultipleChoiceAnswerRepository.findAll();
        assertThat(appMultipleChoiceAnswerList).hasSize(databaseSizeBeforeUpdate);
        AppMultipleChoiceAnswer testAppMultipleChoiceAnswer = appMultipleChoiceAnswerList.get(appMultipleChoiceAnswerList.size() - 1);
        assertThat(testAppMultipleChoiceAnswer.getAnswerText()).isEqualTo(UPDATED_ANSWER_TEXT);
        assertThat(testAppMultipleChoiceAnswer.getIsCorrect()).isEqualTo(DEFAULT_IS_CORRECT);
    }

    @Test
    @Transactional
    void fullUpdateAppMultipleChoiceAnswerWithPatch() throws Exception {
        // Initialize the database
        appMultipleChoiceAnswerRepository.saveAndFlush(appMultipleChoiceAnswer);

        int databaseSizeBeforeUpdate = appMultipleChoiceAnswerRepository.findAll().size();

        // Update the appMultipleChoiceAnswer using partial update
        AppMultipleChoiceAnswer partialUpdatedAppMultipleChoiceAnswer = new AppMultipleChoiceAnswer();
        partialUpdatedAppMultipleChoiceAnswer.setId(appMultipleChoiceAnswer.getId());

        partialUpdatedAppMultipleChoiceAnswer.answerText(UPDATED_ANSWER_TEXT).isCorrect(UPDATED_IS_CORRECT);

        restAppMultipleChoiceAnswerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppMultipleChoiceAnswer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAppMultipleChoiceAnswer))
            )
            .andExpect(status().isOk());

        // Validate the AppMultipleChoiceAnswer in the database
        List<AppMultipleChoiceAnswer> appMultipleChoiceAnswerList = appMultipleChoiceAnswerRepository.findAll();
        assertThat(appMultipleChoiceAnswerList).hasSize(databaseSizeBeforeUpdate);
        AppMultipleChoiceAnswer testAppMultipleChoiceAnswer = appMultipleChoiceAnswerList.get(appMultipleChoiceAnswerList.size() - 1);
        assertThat(testAppMultipleChoiceAnswer.getAnswerText()).isEqualTo(UPDATED_ANSWER_TEXT);
        assertThat(testAppMultipleChoiceAnswer.getIsCorrect()).isEqualTo(UPDATED_IS_CORRECT);
    }

    @Test
    @Transactional
    void patchNonExistingAppMultipleChoiceAnswer() throws Exception {
        int databaseSizeBeforeUpdate = appMultipleChoiceAnswerRepository.findAll().size();
        appMultipleChoiceAnswer.setId(count.incrementAndGet());

        // Create the AppMultipleChoiceAnswer
        AppMultipleChoiceAnswerDTO appMultipleChoiceAnswerDTO = appMultipleChoiceAnswerMapper.toDto(appMultipleChoiceAnswer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppMultipleChoiceAnswerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, appMultipleChoiceAnswerDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(appMultipleChoiceAnswerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppMultipleChoiceAnswer in the database
        List<AppMultipleChoiceAnswer> appMultipleChoiceAnswerList = appMultipleChoiceAnswerRepository.findAll();
        assertThat(appMultipleChoiceAnswerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAppMultipleChoiceAnswer() throws Exception {
        int databaseSizeBeforeUpdate = appMultipleChoiceAnswerRepository.findAll().size();
        appMultipleChoiceAnswer.setId(count.incrementAndGet());

        // Create the AppMultipleChoiceAnswer
        AppMultipleChoiceAnswerDTO appMultipleChoiceAnswerDTO = appMultipleChoiceAnswerMapper.toDto(appMultipleChoiceAnswer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppMultipleChoiceAnswerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(appMultipleChoiceAnswerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppMultipleChoiceAnswer in the database
        List<AppMultipleChoiceAnswer> appMultipleChoiceAnswerList = appMultipleChoiceAnswerRepository.findAll();
        assertThat(appMultipleChoiceAnswerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAppMultipleChoiceAnswer() throws Exception {
        int databaseSizeBeforeUpdate = appMultipleChoiceAnswerRepository.findAll().size();
        appMultipleChoiceAnswer.setId(count.incrementAndGet());

        // Create the AppMultipleChoiceAnswer
        AppMultipleChoiceAnswerDTO appMultipleChoiceAnswerDTO = appMultipleChoiceAnswerMapper.toDto(appMultipleChoiceAnswer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppMultipleChoiceAnswerMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(appMultipleChoiceAnswerDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppMultipleChoiceAnswer in the database
        List<AppMultipleChoiceAnswer> appMultipleChoiceAnswerList = appMultipleChoiceAnswerRepository.findAll();
        assertThat(appMultipleChoiceAnswerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAppMultipleChoiceAnswer() throws Exception {
        // Initialize the database
        appMultipleChoiceAnswerRepository.saveAndFlush(appMultipleChoiceAnswer);

        int databaseSizeBeforeDelete = appMultipleChoiceAnswerRepository.findAll().size();

        // Delete the appMultipleChoiceAnswer
        restAppMultipleChoiceAnswerMockMvc
            .perform(delete(ENTITY_API_URL_ID, appMultipleChoiceAnswer.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AppMultipleChoiceAnswer> appMultipleChoiceAnswerList = appMultipleChoiceAnswerRepository.findAll();
        assertThat(appMultipleChoiceAnswerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
