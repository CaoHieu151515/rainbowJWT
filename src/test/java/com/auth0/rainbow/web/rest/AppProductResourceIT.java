package com.auth0.rainbow.web.rest;

import static com.auth0.rainbow.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.auth0.rainbow.IntegrationTest;
import com.auth0.rainbow.domain.AppProduct;
import com.auth0.rainbow.repository.AppProductRepository;
import com.auth0.rainbow.service.dto.AppProductDTO;
import com.auth0.rainbow.service.mapper.AppProductMapper;
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
 * Integration tests for the {@link AppProductResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AppProductResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);

    private static final Integer DEFAULT_UNIT = 1;
    private static final Integer UPDATED_UNIT = 2;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Long DEFAULT_COURSE_ID = 1L;
    private static final Long UPDATED_COURSE_ID = 2L;

    private static final String ENTITY_API_URL = "/api/app-products";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AppProductRepository appProductRepository;

    @Autowired
    private AppProductMapper appProductMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAppProductMockMvc;

    private AppProduct appProduct;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppProduct createEntity(EntityManager em) {
        AppProduct appProduct = new AppProduct()
            .name(DEFAULT_NAME)
            .price(DEFAULT_PRICE)
            .unit(DEFAULT_UNIT)
            .description(DEFAULT_DESCRIPTION)
            .status(DEFAULT_STATUS)
            .courseId(DEFAULT_COURSE_ID);
        return appProduct;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppProduct createUpdatedEntity(EntityManager em) {
        AppProduct appProduct = new AppProduct()
            .name(UPDATED_NAME)
            .price(UPDATED_PRICE)
            .unit(UPDATED_UNIT)
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS)
            .courseId(UPDATED_COURSE_ID);
        return appProduct;
    }

    @BeforeEach
    public void initTest() {
        appProduct = createEntity(em);
    }

    @Test
    @Transactional
    void createAppProduct() throws Exception {
        int databaseSizeBeforeCreate = appProductRepository.findAll().size();
        // Create the AppProduct
        AppProductDTO appProductDTO = appProductMapper.toDto(appProduct);
        restAppProductMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appProductDTO)))
            .andExpect(status().isCreated());

        // Validate the AppProduct in the database
        List<AppProduct> appProductList = appProductRepository.findAll();
        assertThat(appProductList).hasSize(databaseSizeBeforeCreate + 1);
        AppProduct testAppProduct = appProductList.get(appProductList.size() - 1);
        assertThat(testAppProduct.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAppProduct.getPrice()).isEqualByComparingTo(DEFAULT_PRICE);
        assertThat(testAppProduct.getUnit()).isEqualTo(DEFAULT_UNIT);
        assertThat(testAppProduct.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAppProduct.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testAppProduct.getCourseId()).isEqualTo(DEFAULT_COURSE_ID);
    }

    @Test
    @Transactional
    void createAppProductWithExistingId() throws Exception {
        // Create the AppProduct with an existing ID
        appProduct.setId(1L);
        AppProductDTO appProductDTO = appProductMapper.toDto(appProduct);

        int databaseSizeBeforeCreate = appProductRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppProductMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appProductDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AppProduct in the database
        List<AppProduct> appProductList = appProductRepository.findAll();
        assertThat(appProductList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAppProducts() throws Exception {
        // Initialize the database
        appProductRepository.saveAndFlush(appProduct);

        // Get all the appProductList
        restAppProductMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appProduct.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(sameNumber(DEFAULT_PRICE))))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].courseId").value(hasItem(DEFAULT_COURSE_ID.intValue())));
    }

    @Test
    @Transactional
    void getAppProduct() throws Exception {
        // Initialize the database
        appProductRepository.saveAndFlush(appProduct);

        // Get the appProduct
        restAppProductMockMvc
            .perform(get(ENTITY_API_URL_ID, appProduct.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(appProduct.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.price").value(sameNumber(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.unit").value(DEFAULT_UNIT))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.courseId").value(DEFAULT_COURSE_ID.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingAppProduct() throws Exception {
        // Get the appProduct
        restAppProductMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAppProduct() throws Exception {
        // Initialize the database
        appProductRepository.saveAndFlush(appProduct);

        int databaseSizeBeforeUpdate = appProductRepository.findAll().size();

        // Update the appProduct
        AppProduct updatedAppProduct = appProductRepository.findById(appProduct.getId()).get();
        // Disconnect from session so that the updates on updatedAppProduct are not directly saved in db
        em.detach(updatedAppProduct);
        updatedAppProduct
            .name(UPDATED_NAME)
            .price(UPDATED_PRICE)
            .unit(UPDATED_UNIT)
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS)
            .courseId(UPDATED_COURSE_ID);
        AppProductDTO appProductDTO = appProductMapper.toDto(updatedAppProduct);

        restAppProductMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appProductDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appProductDTO))
            )
            .andExpect(status().isOk());

        // Validate the AppProduct in the database
        List<AppProduct> appProductList = appProductRepository.findAll();
        assertThat(appProductList).hasSize(databaseSizeBeforeUpdate);
        AppProduct testAppProduct = appProductList.get(appProductList.size() - 1);
        assertThat(testAppProduct.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAppProduct.getPrice()).isEqualByComparingTo(UPDATED_PRICE);
        assertThat(testAppProduct.getUnit()).isEqualTo(UPDATED_UNIT);
        assertThat(testAppProduct.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAppProduct.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAppProduct.getCourseId()).isEqualTo(UPDATED_COURSE_ID);
    }

    @Test
    @Transactional
    void putNonExistingAppProduct() throws Exception {
        int databaseSizeBeforeUpdate = appProductRepository.findAll().size();
        appProduct.setId(count.incrementAndGet());

        // Create the AppProduct
        AppProductDTO appProductDTO = appProductMapper.toDto(appProduct);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppProductMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appProductDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appProductDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppProduct in the database
        List<AppProduct> appProductList = appProductRepository.findAll();
        assertThat(appProductList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAppProduct() throws Exception {
        int databaseSizeBeforeUpdate = appProductRepository.findAll().size();
        appProduct.setId(count.incrementAndGet());

        // Create the AppProduct
        AppProductDTO appProductDTO = appProductMapper.toDto(appProduct);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppProductMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appProductDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppProduct in the database
        List<AppProduct> appProductList = appProductRepository.findAll();
        assertThat(appProductList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAppProduct() throws Exception {
        int databaseSizeBeforeUpdate = appProductRepository.findAll().size();
        appProduct.setId(count.incrementAndGet());

        // Create the AppProduct
        AppProductDTO appProductDTO = appProductMapper.toDto(appProduct);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppProductMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appProductDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppProduct in the database
        List<AppProduct> appProductList = appProductRepository.findAll();
        assertThat(appProductList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAppProductWithPatch() throws Exception {
        // Initialize the database
        appProductRepository.saveAndFlush(appProduct);

        int databaseSizeBeforeUpdate = appProductRepository.findAll().size();

        // Update the appProduct using partial update
        AppProduct partialUpdatedAppProduct = new AppProduct();
        partialUpdatedAppProduct.setId(appProduct.getId());

        partialUpdatedAppProduct.name(UPDATED_NAME).unit(UPDATED_UNIT);

        restAppProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppProduct.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAppProduct))
            )
            .andExpect(status().isOk());

        // Validate the AppProduct in the database
        List<AppProduct> appProductList = appProductRepository.findAll();
        assertThat(appProductList).hasSize(databaseSizeBeforeUpdate);
        AppProduct testAppProduct = appProductList.get(appProductList.size() - 1);
        assertThat(testAppProduct.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAppProduct.getPrice()).isEqualByComparingTo(DEFAULT_PRICE);
        assertThat(testAppProduct.getUnit()).isEqualTo(UPDATED_UNIT);
        assertThat(testAppProduct.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAppProduct.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testAppProduct.getCourseId()).isEqualTo(DEFAULT_COURSE_ID);
    }

    @Test
    @Transactional
    void fullUpdateAppProductWithPatch() throws Exception {
        // Initialize the database
        appProductRepository.saveAndFlush(appProduct);

        int databaseSizeBeforeUpdate = appProductRepository.findAll().size();

        // Update the appProduct using partial update
        AppProduct partialUpdatedAppProduct = new AppProduct();
        partialUpdatedAppProduct.setId(appProduct.getId());

        partialUpdatedAppProduct
            .name(UPDATED_NAME)
            .price(UPDATED_PRICE)
            .unit(UPDATED_UNIT)
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS)
            .courseId(UPDATED_COURSE_ID);

        restAppProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppProduct.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAppProduct))
            )
            .andExpect(status().isOk());

        // Validate the AppProduct in the database
        List<AppProduct> appProductList = appProductRepository.findAll();
        assertThat(appProductList).hasSize(databaseSizeBeforeUpdate);
        AppProduct testAppProduct = appProductList.get(appProductList.size() - 1);
        assertThat(testAppProduct.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAppProduct.getPrice()).isEqualByComparingTo(UPDATED_PRICE);
        assertThat(testAppProduct.getUnit()).isEqualTo(UPDATED_UNIT);
        assertThat(testAppProduct.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAppProduct.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAppProduct.getCourseId()).isEqualTo(UPDATED_COURSE_ID);
    }

    @Test
    @Transactional
    void patchNonExistingAppProduct() throws Exception {
        int databaseSizeBeforeUpdate = appProductRepository.findAll().size();
        appProduct.setId(count.incrementAndGet());

        // Create the AppProduct
        AppProductDTO appProductDTO = appProductMapper.toDto(appProduct);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, appProductDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(appProductDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppProduct in the database
        List<AppProduct> appProductList = appProductRepository.findAll();
        assertThat(appProductList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAppProduct() throws Exception {
        int databaseSizeBeforeUpdate = appProductRepository.findAll().size();
        appProduct.setId(count.incrementAndGet());

        // Create the AppProduct
        AppProductDTO appProductDTO = appProductMapper.toDto(appProduct);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(appProductDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppProduct in the database
        List<AppProduct> appProductList = appProductRepository.findAll();
        assertThat(appProductList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAppProduct() throws Exception {
        int databaseSizeBeforeUpdate = appProductRepository.findAll().size();
        appProduct.setId(count.incrementAndGet());

        // Create the AppProduct
        AppProductDTO appProductDTO = appProductMapper.toDto(appProduct);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppProductMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(appProductDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppProduct in the database
        List<AppProduct> appProductList = appProductRepository.findAll();
        assertThat(appProductList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAppProduct() throws Exception {
        // Initialize the database
        appProductRepository.saveAndFlush(appProduct);

        int databaseSizeBeforeDelete = appProductRepository.findAll().size();

        // Delete the appProduct
        restAppProductMockMvc
            .perform(delete(ENTITY_API_URL_ID, appProduct.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AppProduct> appProductList = appProductRepository.findAll();
        assertThat(appProductList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
