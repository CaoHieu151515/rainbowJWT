package com.auth0.rainbow.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.auth0.rainbow.IntegrationTest;
import com.auth0.rainbow.domain.AppCart;
import com.auth0.rainbow.repository.AppCartRepository;
import com.auth0.rainbow.service.AppCartService;
import com.auth0.rainbow.service.dto.AppCartDTO;
import com.auth0.rainbow.service.mapper.AppCartMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AppCartResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AppCartResourceIT {

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final String ENTITY_API_URL = "/api/app-carts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AppCartRepository appCartRepository;

    @Mock
    private AppCartRepository appCartRepositoryMock;

    @Autowired
    private AppCartMapper appCartMapper;

    @Mock
    private AppCartService appCartServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAppCartMockMvc;

    private AppCart appCart;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppCart createEntity(EntityManager em) {
        AppCart appCart = new AppCart().quantity(DEFAULT_QUANTITY);
        return appCart;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppCart createUpdatedEntity(EntityManager em) {
        AppCart appCart = new AppCart().quantity(UPDATED_QUANTITY);
        return appCart;
    }

    @BeforeEach
    public void initTest() {
        appCart = createEntity(em);
    }

    @Test
    @Transactional
    void createAppCart() throws Exception {
        int databaseSizeBeforeCreate = appCartRepository.findAll().size();
        // Create the AppCart
        AppCartDTO appCartDTO = appCartMapper.toDto(appCart);
        restAppCartMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appCartDTO)))
            .andExpect(status().isCreated());

        // Validate the AppCart in the database
        List<AppCart> appCartList = appCartRepository.findAll();
        assertThat(appCartList).hasSize(databaseSizeBeforeCreate + 1);
        AppCart testAppCart = appCartList.get(appCartList.size() - 1);
        assertThat(testAppCart.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
    }

    @Test
    @Transactional
    void createAppCartWithExistingId() throws Exception {
        // Create the AppCart with an existing ID
        appCart.setId(1L);
        AppCartDTO appCartDTO = appCartMapper.toDto(appCart);

        int databaseSizeBeforeCreate = appCartRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppCartMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appCartDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AppCart in the database
        List<AppCart> appCartList = appCartRepository.findAll();
        assertThat(appCartList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAppCarts() throws Exception {
        // Initialize the database
        appCartRepository.saveAndFlush(appCart);

        // Get all the appCartList
        restAppCartMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appCart.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAppCartsWithEagerRelationshipsIsEnabled() throws Exception {
        when(appCartServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAppCartMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(appCartServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAppCartsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(appCartServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAppCartMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(appCartRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getAppCart() throws Exception {
        // Initialize the database
        appCartRepository.saveAndFlush(appCart);

        // Get the appCart
        restAppCartMockMvc
            .perform(get(ENTITY_API_URL_ID, appCart.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(appCart.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY));
    }

    @Test
    @Transactional
    void getNonExistingAppCart() throws Exception {
        // Get the appCart
        restAppCartMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAppCart() throws Exception {
        // Initialize the database
        appCartRepository.saveAndFlush(appCart);

        int databaseSizeBeforeUpdate = appCartRepository.findAll().size();

        // Update the appCart
        AppCart updatedAppCart = appCartRepository.findById(appCart.getId()).get();
        // Disconnect from session so that the updates on updatedAppCart are not directly saved in db
        em.detach(updatedAppCart);
        updatedAppCart.quantity(UPDATED_QUANTITY);
        AppCartDTO appCartDTO = appCartMapper.toDto(updatedAppCart);

        restAppCartMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appCartDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appCartDTO))
            )
            .andExpect(status().isOk());

        // Validate the AppCart in the database
        List<AppCart> appCartList = appCartRepository.findAll();
        assertThat(appCartList).hasSize(databaseSizeBeforeUpdate);
        AppCart testAppCart = appCartList.get(appCartList.size() - 1);
        assertThat(testAppCart.getQuantity()).isEqualTo(UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void putNonExistingAppCart() throws Exception {
        int databaseSizeBeforeUpdate = appCartRepository.findAll().size();
        appCart.setId(count.incrementAndGet());

        // Create the AppCart
        AppCartDTO appCartDTO = appCartMapper.toDto(appCart);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppCartMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appCartDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appCartDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppCart in the database
        List<AppCart> appCartList = appCartRepository.findAll();
        assertThat(appCartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAppCart() throws Exception {
        int databaseSizeBeforeUpdate = appCartRepository.findAll().size();
        appCart.setId(count.incrementAndGet());

        // Create the AppCart
        AppCartDTO appCartDTO = appCartMapper.toDto(appCart);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppCartMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appCartDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppCart in the database
        List<AppCart> appCartList = appCartRepository.findAll();
        assertThat(appCartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAppCart() throws Exception {
        int databaseSizeBeforeUpdate = appCartRepository.findAll().size();
        appCart.setId(count.incrementAndGet());

        // Create the AppCart
        AppCartDTO appCartDTO = appCartMapper.toDto(appCart);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppCartMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appCartDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppCart in the database
        List<AppCart> appCartList = appCartRepository.findAll();
        assertThat(appCartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAppCartWithPatch() throws Exception {
        // Initialize the database
        appCartRepository.saveAndFlush(appCart);

        int databaseSizeBeforeUpdate = appCartRepository.findAll().size();

        // Update the appCart using partial update
        AppCart partialUpdatedAppCart = new AppCart();
        partialUpdatedAppCart.setId(appCart.getId());

        restAppCartMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppCart.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAppCart))
            )
            .andExpect(status().isOk());

        // Validate the AppCart in the database
        List<AppCart> appCartList = appCartRepository.findAll();
        assertThat(appCartList).hasSize(databaseSizeBeforeUpdate);
        AppCart testAppCart = appCartList.get(appCartList.size() - 1);
        assertThat(testAppCart.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
    }

    @Test
    @Transactional
    void fullUpdateAppCartWithPatch() throws Exception {
        // Initialize the database
        appCartRepository.saveAndFlush(appCart);

        int databaseSizeBeforeUpdate = appCartRepository.findAll().size();

        // Update the appCart using partial update
        AppCart partialUpdatedAppCart = new AppCart();
        partialUpdatedAppCart.setId(appCart.getId());

        partialUpdatedAppCart.quantity(UPDATED_QUANTITY);

        restAppCartMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppCart.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAppCart))
            )
            .andExpect(status().isOk());

        // Validate the AppCart in the database
        List<AppCart> appCartList = appCartRepository.findAll();
        assertThat(appCartList).hasSize(databaseSizeBeforeUpdate);
        AppCart testAppCart = appCartList.get(appCartList.size() - 1);
        assertThat(testAppCart.getQuantity()).isEqualTo(UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void patchNonExistingAppCart() throws Exception {
        int databaseSizeBeforeUpdate = appCartRepository.findAll().size();
        appCart.setId(count.incrementAndGet());

        // Create the AppCart
        AppCartDTO appCartDTO = appCartMapper.toDto(appCart);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppCartMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, appCartDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(appCartDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppCart in the database
        List<AppCart> appCartList = appCartRepository.findAll();
        assertThat(appCartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAppCart() throws Exception {
        int databaseSizeBeforeUpdate = appCartRepository.findAll().size();
        appCart.setId(count.incrementAndGet());

        // Create the AppCart
        AppCartDTO appCartDTO = appCartMapper.toDto(appCart);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppCartMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(appCartDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppCart in the database
        List<AppCart> appCartList = appCartRepository.findAll();
        assertThat(appCartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAppCart() throws Exception {
        int databaseSizeBeforeUpdate = appCartRepository.findAll().size();
        appCart.setId(count.incrementAndGet());

        // Create the AppCart
        AppCartDTO appCartDTO = appCartMapper.toDto(appCart);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppCartMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(appCartDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppCart in the database
        List<AppCart> appCartList = appCartRepository.findAll();
        assertThat(appCartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAppCart() throws Exception {
        // Initialize the database
        appCartRepository.saveAndFlush(appCart);

        int databaseSizeBeforeDelete = appCartRepository.findAll().size();

        // Delete the appCart
        restAppCartMockMvc
            .perform(delete(ENTITY_API_URL_ID, appCart.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AppCart> appCartList = appCartRepository.findAll();
        assertThat(appCartList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
