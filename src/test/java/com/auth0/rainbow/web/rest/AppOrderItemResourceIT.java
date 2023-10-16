package com.auth0.rainbow.web.rest;

import static com.auth0.rainbow.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.auth0.rainbow.IntegrationTest;
import com.auth0.rainbow.domain.AppOrderItem;
import com.auth0.rainbow.repository.AppOrderItemRepository;
import com.auth0.rainbow.service.dto.AppOrderItemDTO;
import com.auth0.rainbow.service.mapper.AppOrderItemMapper;
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
 * Integration tests for the {@link AppOrderItemResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AppOrderItemResourceIT {

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);

    private static final String DEFAULT_UNIT = "AAAAAAAAAA";
    private static final String UPDATED_UNIT = "BBBBBBBBBB";

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/app-order-items";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AppOrderItemRepository appOrderItemRepository;

    @Autowired
    private AppOrderItemMapper appOrderItemMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAppOrderItemMockMvc;

    private AppOrderItem appOrderItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppOrderItem createEntity(EntityManager em) {
        AppOrderItem appOrderItem = new AppOrderItem()
            .quantity(DEFAULT_QUANTITY)
            .price(DEFAULT_PRICE)
            .unit(DEFAULT_UNIT)
            .note(DEFAULT_NOTE);
        return appOrderItem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppOrderItem createUpdatedEntity(EntityManager em) {
        AppOrderItem appOrderItem = new AppOrderItem()
            .quantity(UPDATED_QUANTITY)
            .price(UPDATED_PRICE)
            .unit(UPDATED_UNIT)
            .note(UPDATED_NOTE);
        return appOrderItem;
    }

    @BeforeEach
    public void initTest() {
        appOrderItem = createEntity(em);
    }

    @Test
    @Transactional
    void createAppOrderItem() throws Exception {
        int databaseSizeBeforeCreate = appOrderItemRepository.findAll().size();
        // Create the AppOrderItem
        AppOrderItemDTO appOrderItemDTO = appOrderItemMapper.toDto(appOrderItem);
        restAppOrderItemMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appOrderItemDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AppOrderItem in the database
        List<AppOrderItem> appOrderItemList = appOrderItemRepository.findAll();
        assertThat(appOrderItemList).hasSize(databaseSizeBeforeCreate + 1);
        AppOrderItem testAppOrderItem = appOrderItemList.get(appOrderItemList.size() - 1);
        assertThat(testAppOrderItem.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testAppOrderItem.getPrice()).isEqualByComparingTo(DEFAULT_PRICE);
        assertThat(testAppOrderItem.getUnit()).isEqualTo(DEFAULT_UNIT);
        assertThat(testAppOrderItem.getNote()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    void createAppOrderItemWithExistingId() throws Exception {
        // Create the AppOrderItem with an existing ID
        appOrderItem.setId(1L);
        AppOrderItemDTO appOrderItemDTO = appOrderItemMapper.toDto(appOrderItem);

        int databaseSizeBeforeCreate = appOrderItemRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppOrderItemMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appOrderItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppOrderItem in the database
        List<AppOrderItem> appOrderItemList = appOrderItemRepository.findAll();
        assertThat(appOrderItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAppOrderItems() throws Exception {
        // Initialize the database
        appOrderItemRepository.saveAndFlush(appOrderItem);

        // Get all the appOrderItemList
        restAppOrderItemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appOrderItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(sameNumber(DEFAULT_PRICE))))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT)))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)));
    }

    @Test
    @Transactional
    void getAppOrderItem() throws Exception {
        // Initialize the database
        appOrderItemRepository.saveAndFlush(appOrderItem);

        // Get the appOrderItem
        restAppOrderItemMockMvc
            .perform(get(ENTITY_API_URL_ID, appOrderItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(appOrderItem.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.price").value(sameNumber(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.unit").value(DEFAULT_UNIT))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE));
    }

    @Test
    @Transactional
    void getNonExistingAppOrderItem() throws Exception {
        // Get the appOrderItem
        restAppOrderItemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAppOrderItem() throws Exception {
        // Initialize the database
        appOrderItemRepository.saveAndFlush(appOrderItem);

        int databaseSizeBeforeUpdate = appOrderItemRepository.findAll().size();

        // Update the appOrderItem
        AppOrderItem updatedAppOrderItem = appOrderItemRepository.findById(appOrderItem.getId()).get();
        // Disconnect from session so that the updates on updatedAppOrderItem are not directly saved in db
        em.detach(updatedAppOrderItem);
        updatedAppOrderItem.quantity(UPDATED_QUANTITY).price(UPDATED_PRICE).unit(UPDATED_UNIT).note(UPDATED_NOTE);
        AppOrderItemDTO appOrderItemDTO = appOrderItemMapper.toDto(updatedAppOrderItem);

        restAppOrderItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appOrderItemDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appOrderItemDTO))
            )
            .andExpect(status().isOk());

        // Validate the AppOrderItem in the database
        List<AppOrderItem> appOrderItemList = appOrderItemRepository.findAll();
        assertThat(appOrderItemList).hasSize(databaseSizeBeforeUpdate);
        AppOrderItem testAppOrderItem = appOrderItemList.get(appOrderItemList.size() - 1);
        assertThat(testAppOrderItem.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testAppOrderItem.getPrice()).isEqualByComparingTo(UPDATED_PRICE);
        assertThat(testAppOrderItem.getUnit()).isEqualTo(UPDATED_UNIT);
        assertThat(testAppOrderItem.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    void putNonExistingAppOrderItem() throws Exception {
        int databaseSizeBeforeUpdate = appOrderItemRepository.findAll().size();
        appOrderItem.setId(count.incrementAndGet());

        // Create the AppOrderItem
        AppOrderItemDTO appOrderItemDTO = appOrderItemMapper.toDto(appOrderItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppOrderItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appOrderItemDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appOrderItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppOrderItem in the database
        List<AppOrderItem> appOrderItemList = appOrderItemRepository.findAll();
        assertThat(appOrderItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAppOrderItem() throws Exception {
        int databaseSizeBeforeUpdate = appOrderItemRepository.findAll().size();
        appOrderItem.setId(count.incrementAndGet());

        // Create the AppOrderItem
        AppOrderItemDTO appOrderItemDTO = appOrderItemMapper.toDto(appOrderItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppOrderItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appOrderItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppOrderItem in the database
        List<AppOrderItem> appOrderItemList = appOrderItemRepository.findAll();
        assertThat(appOrderItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAppOrderItem() throws Exception {
        int databaseSizeBeforeUpdate = appOrderItemRepository.findAll().size();
        appOrderItem.setId(count.incrementAndGet());

        // Create the AppOrderItem
        AppOrderItemDTO appOrderItemDTO = appOrderItemMapper.toDto(appOrderItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppOrderItemMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appOrderItemDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppOrderItem in the database
        List<AppOrderItem> appOrderItemList = appOrderItemRepository.findAll();
        assertThat(appOrderItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAppOrderItemWithPatch() throws Exception {
        // Initialize the database
        appOrderItemRepository.saveAndFlush(appOrderItem);

        int databaseSizeBeforeUpdate = appOrderItemRepository.findAll().size();

        // Update the appOrderItem using partial update
        AppOrderItem partialUpdatedAppOrderItem = new AppOrderItem();
        partialUpdatedAppOrderItem.setId(appOrderItem.getId());

        partialUpdatedAppOrderItem.quantity(UPDATED_QUANTITY).price(UPDATED_PRICE).unit(UPDATED_UNIT);

        restAppOrderItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppOrderItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAppOrderItem))
            )
            .andExpect(status().isOk());

        // Validate the AppOrderItem in the database
        List<AppOrderItem> appOrderItemList = appOrderItemRepository.findAll();
        assertThat(appOrderItemList).hasSize(databaseSizeBeforeUpdate);
        AppOrderItem testAppOrderItem = appOrderItemList.get(appOrderItemList.size() - 1);
        assertThat(testAppOrderItem.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testAppOrderItem.getPrice()).isEqualByComparingTo(UPDATED_PRICE);
        assertThat(testAppOrderItem.getUnit()).isEqualTo(UPDATED_UNIT);
        assertThat(testAppOrderItem.getNote()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    void fullUpdateAppOrderItemWithPatch() throws Exception {
        // Initialize the database
        appOrderItemRepository.saveAndFlush(appOrderItem);

        int databaseSizeBeforeUpdate = appOrderItemRepository.findAll().size();

        // Update the appOrderItem using partial update
        AppOrderItem partialUpdatedAppOrderItem = new AppOrderItem();
        partialUpdatedAppOrderItem.setId(appOrderItem.getId());

        partialUpdatedAppOrderItem.quantity(UPDATED_QUANTITY).price(UPDATED_PRICE).unit(UPDATED_UNIT).note(UPDATED_NOTE);

        restAppOrderItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppOrderItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAppOrderItem))
            )
            .andExpect(status().isOk());

        // Validate the AppOrderItem in the database
        List<AppOrderItem> appOrderItemList = appOrderItemRepository.findAll();
        assertThat(appOrderItemList).hasSize(databaseSizeBeforeUpdate);
        AppOrderItem testAppOrderItem = appOrderItemList.get(appOrderItemList.size() - 1);
        assertThat(testAppOrderItem.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testAppOrderItem.getPrice()).isEqualByComparingTo(UPDATED_PRICE);
        assertThat(testAppOrderItem.getUnit()).isEqualTo(UPDATED_UNIT);
        assertThat(testAppOrderItem.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    void patchNonExistingAppOrderItem() throws Exception {
        int databaseSizeBeforeUpdate = appOrderItemRepository.findAll().size();
        appOrderItem.setId(count.incrementAndGet());

        // Create the AppOrderItem
        AppOrderItemDTO appOrderItemDTO = appOrderItemMapper.toDto(appOrderItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppOrderItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, appOrderItemDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(appOrderItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppOrderItem in the database
        List<AppOrderItem> appOrderItemList = appOrderItemRepository.findAll();
        assertThat(appOrderItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAppOrderItem() throws Exception {
        int databaseSizeBeforeUpdate = appOrderItemRepository.findAll().size();
        appOrderItem.setId(count.incrementAndGet());

        // Create the AppOrderItem
        AppOrderItemDTO appOrderItemDTO = appOrderItemMapper.toDto(appOrderItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppOrderItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(appOrderItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppOrderItem in the database
        List<AppOrderItem> appOrderItemList = appOrderItemRepository.findAll();
        assertThat(appOrderItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAppOrderItem() throws Exception {
        int databaseSizeBeforeUpdate = appOrderItemRepository.findAll().size();
        appOrderItem.setId(count.incrementAndGet());

        // Create the AppOrderItem
        AppOrderItemDTO appOrderItemDTO = appOrderItemMapper.toDto(appOrderItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppOrderItemMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(appOrderItemDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppOrderItem in the database
        List<AppOrderItem> appOrderItemList = appOrderItemRepository.findAll();
        assertThat(appOrderItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAppOrderItem() throws Exception {
        // Initialize the database
        appOrderItemRepository.saveAndFlush(appOrderItem);

        int databaseSizeBeforeDelete = appOrderItemRepository.findAll().size();

        // Delete the appOrderItem
        restAppOrderItemMockMvc
            .perform(delete(ENTITY_API_URL_ID, appOrderItem.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AppOrderItem> appOrderItemList = appOrderItemRepository.findAll();
        assertThat(appOrderItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
