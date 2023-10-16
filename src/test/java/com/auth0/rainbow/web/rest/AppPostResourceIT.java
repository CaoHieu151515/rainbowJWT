package com.auth0.rainbow.web.rest;

import static com.auth0.rainbow.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.auth0.rainbow.IntegrationTest;
import com.auth0.rainbow.domain.AppPost;
import com.auth0.rainbow.repository.AppPostRepository;
import com.auth0.rainbow.service.dto.AppPostDTO;
import com.auth0.rainbow.service.mapper.AppPostMapper;
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
 * Integration tests for the {@link AppPostResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AppPostResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final String DEFAULT_AUTHOR = "AAAAAAAAAA";
    private static final String UPDATED_AUTHOR = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE_WRITTEN = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_WRITTEN = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_PUBLISHED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_PUBLISHED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Boolean DEFAULT_IS_FEATURED = false;
    private static final Boolean UPDATED_IS_FEATURED = true;

    private static final String ENTITY_API_URL = "/api/app-posts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AppPostRepository appPostRepository;

    @Autowired
    private AppPostMapper appPostMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAppPostMockMvc;

    private AppPost appPost;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppPost createEntity(EntityManager em) {
        AppPost appPost = new AppPost()
            .title(DEFAULT_TITLE)
            .content(DEFAULT_CONTENT)
            .author(DEFAULT_AUTHOR)
            .dateWritten(DEFAULT_DATE_WRITTEN)
            .publishedDate(DEFAULT_PUBLISHED_DATE)
            .isFeatured(DEFAULT_IS_FEATURED);
        return appPost;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppPost createUpdatedEntity(EntityManager em) {
        AppPost appPost = new AppPost()
            .title(UPDATED_TITLE)
            .content(UPDATED_CONTENT)
            .author(UPDATED_AUTHOR)
            .dateWritten(UPDATED_DATE_WRITTEN)
            .publishedDate(UPDATED_PUBLISHED_DATE)
            .isFeatured(UPDATED_IS_FEATURED);
        return appPost;
    }

    @BeforeEach
    public void initTest() {
        appPost = createEntity(em);
    }

    @Test
    @Transactional
    void createAppPost() throws Exception {
        int databaseSizeBeforeCreate = appPostRepository.findAll().size();
        // Create the AppPost
        AppPostDTO appPostDTO = appPostMapper.toDto(appPost);
        restAppPostMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appPostDTO)))
            .andExpect(status().isCreated());

        // Validate the AppPost in the database
        List<AppPost> appPostList = appPostRepository.findAll();
        assertThat(appPostList).hasSize(databaseSizeBeforeCreate + 1);
        AppPost testAppPost = appPostList.get(appPostList.size() - 1);
        assertThat(testAppPost.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testAppPost.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testAppPost.getAuthor()).isEqualTo(DEFAULT_AUTHOR);
        assertThat(testAppPost.getDateWritten()).isEqualTo(DEFAULT_DATE_WRITTEN);
        assertThat(testAppPost.getPublishedDate()).isEqualTo(DEFAULT_PUBLISHED_DATE);
        assertThat(testAppPost.getIsFeatured()).isEqualTo(DEFAULT_IS_FEATURED);
    }

    @Test
    @Transactional
    void createAppPostWithExistingId() throws Exception {
        // Create the AppPost with an existing ID
        appPost.setId(1L);
        AppPostDTO appPostDTO = appPostMapper.toDto(appPost);

        int databaseSizeBeforeCreate = appPostRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppPostMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appPostDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AppPost in the database
        List<AppPost> appPostList = appPostRepository.findAll();
        assertThat(appPostList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAppPosts() throws Exception {
        // Initialize the database
        appPostRepository.saveAndFlush(appPost);

        // Get all the appPostList
        restAppPostMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appPost.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR)))
            .andExpect(jsonPath("$.[*].dateWritten").value(hasItem(sameInstant(DEFAULT_DATE_WRITTEN))))
            .andExpect(jsonPath("$.[*].publishedDate").value(hasItem(sameInstant(DEFAULT_PUBLISHED_DATE))))
            .andExpect(jsonPath("$.[*].isFeatured").value(hasItem(DEFAULT_IS_FEATURED.booleanValue())));
    }

    @Test
    @Transactional
    void getAppPost() throws Exception {
        // Initialize the database
        appPostRepository.saveAndFlush(appPost);

        // Get the appPost
        restAppPostMockMvc
            .perform(get(ENTITY_API_URL_ID, appPost.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(appPost.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT))
            .andExpect(jsonPath("$.author").value(DEFAULT_AUTHOR))
            .andExpect(jsonPath("$.dateWritten").value(sameInstant(DEFAULT_DATE_WRITTEN)))
            .andExpect(jsonPath("$.publishedDate").value(sameInstant(DEFAULT_PUBLISHED_DATE)))
            .andExpect(jsonPath("$.isFeatured").value(DEFAULT_IS_FEATURED.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingAppPost() throws Exception {
        // Get the appPost
        restAppPostMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAppPost() throws Exception {
        // Initialize the database
        appPostRepository.saveAndFlush(appPost);

        int databaseSizeBeforeUpdate = appPostRepository.findAll().size();

        // Update the appPost
        AppPost updatedAppPost = appPostRepository.findById(appPost.getId()).get();
        // Disconnect from session so that the updates on updatedAppPost are not directly saved in db
        em.detach(updatedAppPost);
        updatedAppPost
            .title(UPDATED_TITLE)
            .content(UPDATED_CONTENT)
            .author(UPDATED_AUTHOR)
            .dateWritten(UPDATED_DATE_WRITTEN)
            .publishedDate(UPDATED_PUBLISHED_DATE)
            .isFeatured(UPDATED_IS_FEATURED);
        AppPostDTO appPostDTO = appPostMapper.toDto(updatedAppPost);

        restAppPostMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appPostDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appPostDTO))
            )
            .andExpect(status().isOk());

        // Validate the AppPost in the database
        List<AppPost> appPostList = appPostRepository.findAll();
        assertThat(appPostList).hasSize(databaseSizeBeforeUpdate);
        AppPost testAppPost = appPostList.get(appPostList.size() - 1);
        assertThat(testAppPost.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testAppPost.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testAppPost.getAuthor()).isEqualTo(UPDATED_AUTHOR);
        assertThat(testAppPost.getDateWritten()).isEqualTo(UPDATED_DATE_WRITTEN);
        assertThat(testAppPost.getPublishedDate()).isEqualTo(UPDATED_PUBLISHED_DATE);
        assertThat(testAppPost.getIsFeatured()).isEqualTo(UPDATED_IS_FEATURED);
    }

    @Test
    @Transactional
    void putNonExistingAppPost() throws Exception {
        int databaseSizeBeforeUpdate = appPostRepository.findAll().size();
        appPost.setId(count.incrementAndGet());

        // Create the AppPost
        AppPostDTO appPostDTO = appPostMapper.toDto(appPost);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppPostMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appPostDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appPostDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppPost in the database
        List<AppPost> appPostList = appPostRepository.findAll();
        assertThat(appPostList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAppPost() throws Exception {
        int databaseSizeBeforeUpdate = appPostRepository.findAll().size();
        appPost.setId(count.incrementAndGet());

        // Create the AppPost
        AppPostDTO appPostDTO = appPostMapper.toDto(appPost);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppPostMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appPostDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppPost in the database
        List<AppPost> appPostList = appPostRepository.findAll();
        assertThat(appPostList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAppPost() throws Exception {
        int databaseSizeBeforeUpdate = appPostRepository.findAll().size();
        appPost.setId(count.incrementAndGet());

        // Create the AppPost
        AppPostDTO appPostDTO = appPostMapper.toDto(appPost);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppPostMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appPostDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppPost in the database
        List<AppPost> appPostList = appPostRepository.findAll();
        assertThat(appPostList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAppPostWithPatch() throws Exception {
        // Initialize the database
        appPostRepository.saveAndFlush(appPost);

        int databaseSizeBeforeUpdate = appPostRepository.findAll().size();

        // Update the appPost using partial update
        AppPost partialUpdatedAppPost = new AppPost();
        partialUpdatedAppPost.setId(appPost.getId());

        partialUpdatedAppPost.title(UPDATED_TITLE).publishedDate(UPDATED_PUBLISHED_DATE).isFeatured(UPDATED_IS_FEATURED);

        restAppPostMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppPost.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAppPost))
            )
            .andExpect(status().isOk());

        // Validate the AppPost in the database
        List<AppPost> appPostList = appPostRepository.findAll();
        assertThat(appPostList).hasSize(databaseSizeBeforeUpdate);
        AppPost testAppPost = appPostList.get(appPostList.size() - 1);
        assertThat(testAppPost.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testAppPost.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testAppPost.getAuthor()).isEqualTo(DEFAULT_AUTHOR);
        assertThat(testAppPost.getDateWritten()).isEqualTo(DEFAULT_DATE_WRITTEN);
        assertThat(testAppPost.getPublishedDate()).isEqualTo(UPDATED_PUBLISHED_DATE);
        assertThat(testAppPost.getIsFeatured()).isEqualTo(UPDATED_IS_FEATURED);
    }

    @Test
    @Transactional
    void fullUpdateAppPostWithPatch() throws Exception {
        // Initialize the database
        appPostRepository.saveAndFlush(appPost);

        int databaseSizeBeforeUpdate = appPostRepository.findAll().size();

        // Update the appPost using partial update
        AppPost partialUpdatedAppPost = new AppPost();
        partialUpdatedAppPost.setId(appPost.getId());

        partialUpdatedAppPost
            .title(UPDATED_TITLE)
            .content(UPDATED_CONTENT)
            .author(UPDATED_AUTHOR)
            .dateWritten(UPDATED_DATE_WRITTEN)
            .publishedDate(UPDATED_PUBLISHED_DATE)
            .isFeatured(UPDATED_IS_FEATURED);

        restAppPostMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppPost.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAppPost))
            )
            .andExpect(status().isOk());

        // Validate the AppPost in the database
        List<AppPost> appPostList = appPostRepository.findAll();
        assertThat(appPostList).hasSize(databaseSizeBeforeUpdate);
        AppPost testAppPost = appPostList.get(appPostList.size() - 1);
        assertThat(testAppPost.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testAppPost.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testAppPost.getAuthor()).isEqualTo(UPDATED_AUTHOR);
        assertThat(testAppPost.getDateWritten()).isEqualTo(UPDATED_DATE_WRITTEN);
        assertThat(testAppPost.getPublishedDate()).isEqualTo(UPDATED_PUBLISHED_DATE);
        assertThat(testAppPost.getIsFeatured()).isEqualTo(UPDATED_IS_FEATURED);
    }

    @Test
    @Transactional
    void patchNonExistingAppPost() throws Exception {
        int databaseSizeBeforeUpdate = appPostRepository.findAll().size();
        appPost.setId(count.incrementAndGet());

        // Create the AppPost
        AppPostDTO appPostDTO = appPostMapper.toDto(appPost);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppPostMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, appPostDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(appPostDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppPost in the database
        List<AppPost> appPostList = appPostRepository.findAll();
        assertThat(appPostList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAppPost() throws Exception {
        int databaseSizeBeforeUpdate = appPostRepository.findAll().size();
        appPost.setId(count.incrementAndGet());

        // Create the AppPost
        AppPostDTO appPostDTO = appPostMapper.toDto(appPost);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppPostMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(appPostDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppPost in the database
        List<AppPost> appPostList = appPostRepository.findAll();
        assertThat(appPostList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAppPost() throws Exception {
        int databaseSizeBeforeUpdate = appPostRepository.findAll().size();
        appPost.setId(count.incrementAndGet());

        // Create the AppPost
        AppPostDTO appPostDTO = appPostMapper.toDto(appPost);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppPostMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(appPostDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppPost in the database
        List<AppPost> appPostList = appPostRepository.findAll();
        assertThat(appPostList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAppPost() throws Exception {
        // Initialize the database
        appPostRepository.saveAndFlush(appPost);

        int databaseSizeBeforeDelete = appPostRepository.findAll().size();

        // Delete the appPost
        restAppPostMockMvc
            .perform(delete(ENTITY_API_URL_ID, appPost.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AppPost> appPostList = appPostRepository.findAll();
        assertThat(appPostList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
