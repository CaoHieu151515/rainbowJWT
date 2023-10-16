package com.auth0.rainbow.web.rest;

import static com.auth0.rainbow.web.rest.TestUtil.sameInstant;
import static com.auth0.rainbow.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.auth0.rainbow.IntegrationTest;
import com.auth0.rainbow.domain.AppOrder;
import com.auth0.rainbow.repository.AppOrderRepository;
import com.auth0.rainbow.service.dto.AppOrderDTO;
import com.auth0.rainbow.service.mapper.AppOrderMapper;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
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
 * Integration tests for the {@link AppOrderResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AppOrderResourceIT {

    private static final BigDecimal DEFAULT_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL = new BigDecimal(2);

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Long DEFAULT_PAYMENT_ID = 1L;
    private static final Long UPDATED_PAYMENT_ID = 2L;

    private static final String ENTITY_API_URL = "/api/app-orders";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AppOrderRepository appOrderRepository;

    @Autowired
    private AppOrderMapper appOrderMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAppOrderMockMvc;

    private AppOrder appOrder;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppOrder createEntity(EntityManager em) {
        AppOrder appOrder = new AppOrder()
            .total(DEFAULT_TOTAL)
            .createdAt(DEFAULT_CREATED_AT)
            .status(DEFAULT_STATUS)
            .paymentID(DEFAULT_PAYMENT_ID);
        return appOrder;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppOrder createUpdatedEntity(EntityManager em) {
        AppOrder appOrder = new AppOrder()
            .total(UPDATED_TOTAL)
            .createdAt(UPDATED_CREATED_AT)
            .status(UPDATED_STATUS)
            .paymentID(UPDATED_PAYMENT_ID);
        return appOrder;
    }

    @BeforeEach
    public void initTest() {
        appOrder = createEntity(em);
    }

    @Test
    @Transactional
    void createAppOrder() throws Exception {
        int databaseSizeBeforeCreate = appOrderRepository.findAll().size();
        // Create the AppOrder
        AppOrderDTO appOrderDTO = appOrderMapper.toDto(appOrder);
        restAppOrderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appOrderDTO)))
            .andExpect(status().isCreated());

        // Validate the AppOrder in the database
        List<AppOrder> appOrderList = appOrderRepository.findAll();
        assertThat(appOrderList).hasSize(databaseSizeBeforeCreate + 1);
        AppOrder testAppOrder = appOrderList.get(appOrderList.size() - 1);
        assertThat(testAppOrder.getTotal()).isEqualByComparingTo(DEFAULT_TOTAL);
        assertThat(testAppOrder.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testAppOrder.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testAppOrder.getPaymentID()).isEqualTo(DEFAULT_PAYMENT_ID);
    }

    @Test
    @Transactional
    void createAppOrderWithExistingId() throws Exception {
        // Create the AppOrder with an existing ID
        appOrder.setId(1L);
        AppOrderDTO appOrderDTO = appOrderMapper.toDto(appOrder);

        int databaseSizeBeforeCreate = appOrderRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppOrderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appOrderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AppOrder in the database
        List<AppOrder> appOrderList = appOrderRepository.findAll();
        assertThat(appOrderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAppOrders() throws Exception {
        // Initialize the database
        appOrderRepository.saveAndFlush(appOrder);

        // Get all the appOrderList
        restAppOrderMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(sameNumber(DEFAULT_TOTAL))))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].paymentID").value(hasItem(DEFAULT_PAYMENT_ID.intValue())));
    }

    @Test
    @Transactional
    void getAppOrder() throws Exception {
        // Initialize the database
        appOrderRepository.saveAndFlush(appOrder);

        // Get the appOrder
        restAppOrderMockMvc
            .perform(get(ENTITY_API_URL_ID, appOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(appOrder.getId().intValue()))
            .andExpect(jsonPath("$.total").value(sameNumber(DEFAULT_TOTAL)))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.paymentID").value(DEFAULT_PAYMENT_ID.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingAppOrder() throws Exception {
        // Get the appOrder
        restAppOrderMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAppOrder() throws Exception {
        // Initialize the database
        appOrderRepository.saveAndFlush(appOrder);

        int databaseSizeBeforeUpdate = appOrderRepository.findAll().size();

        // Update the appOrder
        AppOrder updatedAppOrder = appOrderRepository.findById(appOrder.getId()).get();
        // Disconnect from session so that the updates on updatedAppOrder are not directly saved in db
        em.detach(updatedAppOrder);
        updatedAppOrder.total(UPDATED_TOTAL).createdAt(UPDATED_CREATED_AT).status(UPDATED_STATUS).paymentID(UPDATED_PAYMENT_ID);
        AppOrderDTO appOrderDTO = appOrderMapper.toDto(updatedAppOrder);

        restAppOrderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appOrderDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appOrderDTO))
            )
            .andExpect(status().isOk());

        // Validate the AppOrder in the database
        List<AppOrder> appOrderList = appOrderRepository.findAll();
        assertThat(appOrderList).hasSize(databaseSizeBeforeUpdate);
        AppOrder testAppOrder = appOrderList.get(appOrderList.size() - 1);
        assertThat(testAppOrder.getTotal()).isEqualByComparingTo(UPDATED_TOTAL);
        assertThat(testAppOrder.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testAppOrder.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAppOrder.getPaymentID()).isEqualTo(UPDATED_PAYMENT_ID);
    }

    @Test
    @Transactional
    void putNonExistingAppOrder() throws Exception {
        int databaseSizeBeforeUpdate = appOrderRepository.findAll().size();
        appOrder.setId(count.incrementAndGet());

        // Create the AppOrder
        AppOrderDTO appOrderDTO = appOrderMapper.toDto(appOrder);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppOrderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appOrderDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appOrderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppOrder in the database
        List<AppOrder> appOrderList = appOrderRepository.findAll();
        assertThat(appOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAppOrder() throws Exception {
        int databaseSizeBeforeUpdate = appOrderRepository.findAll().size();
        appOrder.setId(count.incrementAndGet());

        // Create the AppOrder
        AppOrderDTO appOrderDTO = appOrderMapper.toDto(appOrder);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppOrderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appOrderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppOrder in the database
        List<AppOrder> appOrderList = appOrderRepository.findAll();
        assertThat(appOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAppOrder() throws Exception {
        int databaseSizeBeforeUpdate = appOrderRepository.findAll().size();
        appOrder.setId(count.incrementAndGet());

        // Create the AppOrder
        AppOrderDTO appOrderDTO = appOrderMapper.toDto(appOrder);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppOrderMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appOrderDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppOrder in the database
        List<AppOrder> appOrderList = appOrderRepository.findAll();
        assertThat(appOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAppOrderWithPatch() throws Exception {
        // Initialize the database
        appOrderRepository.saveAndFlush(appOrder);

        int databaseSizeBeforeUpdate = appOrderRepository.findAll().size();

        // Update the appOrder using partial update
        AppOrder partialUpdatedAppOrder = new AppOrder();
        partialUpdatedAppOrder.setId(appOrder.getId());

        partialUpdatedAppOrder.createdAt(UPDATED_CREATED_AT);

        restAppOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppOrder.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAppOrder))
            )
            .andExpect(status().isOk());

        // Validate the AppOrder in the database
        List<AppOrder> appOrderList = appOrderRepository.findAll();
        assertThat(appOrderList).hasSize(databaseSizeBeforeUpdate);
        AppOrder testAppOrder = appOrderList.get(appOrderList.size() - 1);
        assertThat(testAppOrder.getTotal()).isEqualByComparingTo(DEFAULT_TOTAL);
        assertThat(testAppOrder.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testAppOrder.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testAppOrder.getPaymentID()).isEqualTo(DEFAULT_PAYMENT_ID);
    }

    @Test
    @Transactional
    void fullUpdateAppOrderWithPatch() throws Exception {
        // Initialize the database
        appOrderRepository.saveAndFlush(appOrder);

        int databaseSizeBeforeUpdate = appOrderRepository.findAll().size();

        // Update the appOrder using partial update
        AppOrder partialUpdatedAppOrder = new AppOrder();
        partialUpdatedAppOrder.setId(appOrder.getId());

        partialUpdatedAppOrder.total(UPDATED_TOTAL).createdAt(UPDATED_CREATED_AT).status(UPDATED_STATUS).paymentID(UPDATED_PAYMENT_ID);

        restAppOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppOrder.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAppOrder))
            )
            .andExpect(status().isOk());

        // Validate the AppOrder in the database
        List<AppOrder> appOrderList = appOrderRepository.findAll();
        assertThat(appOrderList).hasSize(databaseSizeBeforeUpdate);
        AppOrder testAppOrder = appOrderList.get(appOrderList.size() - 1);
        assertThat(testAppOrder.getTotal()).isEqualByComparingTo(UPDATED_TOTAL);
        assertThat(testAppOrder.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testAppOrder.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAppOrder.getPaymentID()).isEqualTo(UPDATED_PAYMENT_ID);
    }

    @Test
    @Transactional
    void patchNonExistingAppOrder() throws Exception {
        int databaseSizeBeforeUpdate = appOrderRepository.findAll().size();
        appOrder.setId(count.incrementAndGet());

        // Create the AppOrder
        AppOrderDTO appOrderDTO = appOrderMapper.toDto(appOrder);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, appOrderDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(appOrderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppOrder in the database
        List<AppOrder> appOrderList = appOrderRepository.findAll();
        assertThat(appOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAppOrder() throws Exception {
        int databaseSizeBeforeUpdate = appOrderRepository.findAll().size();
        appOrder.setId(count.incrementAndGet());

        // Create the AppOrder
        AppOrderDTO appOrderDTO = appOrderMapper.toDto(appOrder);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(appOrderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppOrder in the database
        List<AppOrder> appOrderList = appOrderRepository.findAll();
        assertThat(appOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAppOrder() throws Exception {
        int databaseSizeBeforeUpdate = appOrderRepository.findAll().size();
        appOrder.setId(count.incrementAndGet());

        // Create the AppOrder
        AppOrderDTO appOrderDTO = appOrderMapper.toDto(appOrder);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppOrderMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(appOrderDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppOrder in the database
        List<AppOrder> appOrderList = appOrderRepository.findAll();
        assertThat(appOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAppOrder() throws Exception {
        // Initialize the database
        appOrderRepository.saveAndFlush(appOrder);

        int databaseSizeBeforeDelete = appOrderRepository.findAll().size();

        // Delete the appOrder
        restAppOrderMockMvc
            .perform(delete(ENTITY_API_URL_ID, appOrder.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AppOrder> appOrderList = appOrderRepository.findAll();
        assertThat(appOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
