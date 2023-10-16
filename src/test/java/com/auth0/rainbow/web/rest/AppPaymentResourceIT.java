package com.auth0.rainbow.web.rest;

import static com.auth0.rainbow.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.auth0.rainbow.IntegrationTest;
import com.auth0.rainbow.domain.AppPayment;
import com.auth0.rainbow.repository.AppPaymentRepository;
import com.auth0.rainbow.service.dto.AppPaymentDTO;
import com.auth0.rainbow.service.mapper.AppPaymentMapper;
import java.math.BigDecimal;
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
 * Integration tests for the {@link AppPaymentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AppPaymentResourceIT {

    private static final String DEFAULT_METHOD = "AAAAAAAAAA";
    private static final String UPDATED_METHOD = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/app-payments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AppPaymentRepository appPaymentRepository;

    @Autowired
    private AppPaymentMapper appPaymentMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAppPaymentMockMvc;

    private AppPayment appPayment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppPayment createEntity(EntityManager em) {
        AppPayment appPayment = new AppPayment().method(DEFAULT_METHOD).status(DEFAULT_STATUS).amount(DEFAULT_AMOUNT).note(DEFAULT_NOTE);
        return appPayment;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppPayment createUpdatedEntity(EntityManager em) {
        AppPayment appPayment = new AppPayment().method(UPDATED_METHOD).status(UPDATED_STATUS).amount(UPDATED_AMOUNT).note(UPDATED_NOTE);
        return appPayment;
    }

    @BeforeEach
    public void initTest() {
        appPayment = createEntity(em);
    }

    @Test
    @Transactional
    void createAppPayment() throws Exception {
        int databaseSizeBeforeCreate = appPaymentRepository.findAll().size();
        // Create the AppPayment
        AppPaymentDTO appPaymentDTO = appPaymentMapper.toDto(appPayment);
        restAppPaymentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appPaymentDTO)))
            .andExpect(status().isCreated());

        // Validate the AppPayment in the database
        List<AppPayment> appPaymentList = appPaymentRepository.findAll();
        assertThat(appPaymentList).hasSize(databaseSizeBeforeCreate + 1);
        AppPayment testAppPayment = appPaymentList.get(appPaymentList.size() - 1);
        assertThat(testAppPayment.getMethod()).isEqualTo(DEFAULT_METHOD);
        assertThat(testAppPayment.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testAppPayment.getAmount()).isEqualByComparingTo(DEFAULT_AMOUNT);
        assertThat(testAppPayment.getNote()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    void createAppPaymentWithExistingId() throws Exception {
        // Create the AppPayment with an existing ID
        appPayment.setId(1L);
        AppPaymentDTO appPaymentDTO = appPaymentMapper.toDto(appPayment);

        int databaseSizeBeforeCreate = appPaymentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppPaymentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appPaymentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AppPayment in the database
        List<AppPayment> appPaymentList = appPaymentRepository.findAll();
        assertThat(appPaymentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAppPayments() throws Exception {
        // Initialize the database
        appPaymentRepository.saveAndFlush(appPayment);

        // Get all the appPaymentList
        restAppPaymentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appPayment.getId().intValue())))
            .andExpect(jsonPath("$.[*].method").value(hasItem(DEFAULT_METHOD)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(sameNumber(DEFAULT_AMOUNT))))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)));
    }

    @Test
    @Transactional
    void getAppPayment() throws Exception {
        // Initialize the database
        appPaymentRepository.saveAndFlush(appPayment);

        // Get the appPayment
        restAppPaymentMockMvc
            .perform(get(ENTITY_API_URL_ID, appPayment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(appPayment.getId().intValue()))
            .andExpect(jsonPath("$.method").value(DEFAULT_METHOD))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.amount").value(sameNumber(DEFAULT_AMOUNT)))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE));
    }

    @Test
    @Transactional
    void getNonExistingAppPayment() throws Exception {
        // Get the appPayment
        restAppPaymentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAppPayment() throws Exception {
        // Initialize the database
        appPaymentRepository.saveAndFlush(appPayment);

        int databaseSizeBeforeUpdate = appPaymentRepository.findAll().size();

        // Update the appPayment
        AppPayment updatedAppPayment = appPaymentRepository.findById(appPayment.getId()).get();
        // Disconnect from session so that the updates on updatedAppPayment are not directly saved in db
        em.detach(updatedAppPayment);
        updatedAppPayment.method(UPDATED_METHOD).status(UPDATED_STATUS).amount(UPDATED_AMOUNT).note(UPDATED_NOTE);
        AppPaymentDTO appPaymentDTO = appPaymentMapper.toDto(updatedAppPayment);

        restAppPaymentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appPaymentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appPaymentDTO))
            )
            .andExpect(status().isOk());

        // Validate the AppPayment in the database
        List<AppPayment> appPaymentList = appPaymentRepository.findAll();
        assertThat(appPaymentList).hasSize(databaseSizeBeforeUpdate);
        AppPayment testAppPayment = appPaymentList.get(appPaymentList.size() - 1);
        assertThat(testAppPayment.getMethod()).isEqualTo(UPDATED_METHOD);
        assertThat(testAppPayment.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAppPayment.getAmount()).isEqualByComparingTo(UPDATED_AMOUNT);
        assertThat(testAppPayment.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    void putNonExistingAppPayment() throws Exception {
        int databaseSizeBeforeUpdate = appPaymentRepository.findAll().size();
        appPayment.setId(count.incrementAndGet());

        // Create the AppPayment
        AppPaymentDTO appPaymentDTO = appPaymentMapper.toDto(appPayment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppPaymentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appPaymentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appPaymentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppPayment in the database
        List<AppPayment> appPaymentList = appPaymentRepository.findAll();
        assertThat(appPaymentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAppPayment() throws Exception {
        int databaseSizeBeforeUpdate = appPaymentRepository.findAll().size();
        appPayment.setId(count.incrementAndGet());

        // Create the AppPayment
        AppPaymentDTO appPaymentDTO = appPaymentMapper.toDto(appPayment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppPaymentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appPaymentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppPayment in the database
        List<AppPayment> appPaymentList = appPaymentRepository.findAll();
        assertThat(appPaymentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAppPayment() throws Exception {
        int databaseSizeBeforeUpdate = appPaymentRepository.findAll().size();
        appPayment.setId(count.incrementAndGet());

        // Create the AppPayment
        AppPaymentDTO appPaymentDTO = appPaymentMapper.toDto(appPayment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppPaymentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appPaymentDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppPayment in the database
        List<AppPayment> appPaymentList = appPaymentRepository.findAll();
        assertThat(appPaymentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAppPaymentWithPatch() throws Exception {
        // Initialize the database
        appPaymentRepository.saveAndFlush(appPayment);

        int databaseSizeBeforeUpdate = appPaymentRepository.findAll().size();

        // Update the appPayment using partial update
        AppPayment partialUpdatedAppPayment = new AppPayment();
        partialUpdatedAppPayment.setId(appPayment.getId());

        partialUpdatedAppPayment.status(UPDATED_STATUS).amount(UPDATED_AMOUNT);

        restAppPaymentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppPayment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAppPayment))
            )
            .andExpect(status().isOk());

        // Validate the AppPayment in the database
        List<AppPayment> appPaymentList = appPaymentRepository.findAll();
        assertThat(appPaymentList).hasSize(databaseSizeBeforeUpdate);
        AppPayment testAppPayment = appPaymentList.get(appPaymentList.size() - 1);
        assertThat(testAppPayment.getMethod()).isEqualTo(DEFAULT_METHOD);
        assertThat(testAppPayment.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAppPayment.getAmount()).isEqualByComparingTo(UPDATED_AMOUNT);
        assertThat(testAppPayment.getNote()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    void fullUpdateAppPaymentWithPatch() throws Exception {
        // Initialize the database
        appPaymentRepository.saveAndFlush(appPayment);

        int databaseSizeBeforeUpdate = appPaymentRepository.findAll().size();

        // Update the appPayment using partial update
        AppPayment partialUpdatedAppPayment = new AppPayment();
        partialUpdatedAppPayment.setId(appPayment.getId());

        partialUpdatedAppPayment.method(UPDATED_METHOD).status(UPDATED_STATUS).amount(UPDATED_AMOUNT).note(UPDATED_NOTE);

        restAppPaymentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppPayment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAppPayment))
            )
            .andExpect(status().isOk());

        // Validate the AppPayment in the database
        List<AppPayment> appPaymentList = appPaymentRepository.findAll();
        assertThat(appPaymentList).hasSize(databaseSizeBeforeUpdate);
        AppPayment testAppPayment = appPaymentList.get(appPaymentList.size() - 1);
        assertThat(testAppPayment.getMethod()).isEqualTo(UPDATED_METHOD);
        assertThat(testAppPayment.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAppPayment.getAmount()).isEqualByComparingTo(UPDATED_AMOUNT);
        assertThat(testAppPayment.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    void patchNonExistingAppPayment() throws Exception {
        int databaseSizeBeforeUpdate = appPaymentRepository.findAll().size();
        appPayment.setId(count.incrementAndGet());

        // Create the AppPayment
        AppPaymentDTO appPaymentDTO = appPaymentMapper.toDto(appPayment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppPaymentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, appPaymentDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(appPaymentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppPayment in the database
        List<AppPayment> appPaymentList = appPaymentRepository.findAll();
        assertThat(appPaymentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAppPayment() throws Exception {
        int databaseSizeBeforeUpdate = appPaymentRepository.findAll().size();
        appPayment.setId(count.incrementAndGet());

        // Create the AppPayment
        AppPaymentDTO appPaymentDTO = appPaymentMapper.toDto(appPayment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppPaymentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(appPaymentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppPayment in the database
        List<AppPayment> appPaymentList = appPaymentRepository.findAll();
        assertThat(appPaymentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAppPayment() throws Exception {
        int databaseSizeBeforeUpdate = appPaymentRepository.findAll().size();
        appPayment.setId(count.incrementAndGet());

        // Create the AppPayment
        AppPaymentDTO appPaymentDTO = appPaymentMapper.toDto(appPayment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppPaymentMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(appPaymentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppPayment in the database
        List<AppPayment> appPaymentList = appPaymentRepository.findAll();
        assertThat(appPaymentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAppPayment() throws Exception {
        // Initialize the database
        appPaymentRepository.saveAndFlush(appPayment);

        int databaseSizeBeforeDelete = appPaymentRepository.findAll().size();

        // Delete the appPayment
        restAppPaymentMockMvc
            .perform(delete(ENTITY_API_URL_ID, appPayment.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AppPayment> appPaymentList = appPaymentRepository.findAll();
        assertThat(appPaymentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
