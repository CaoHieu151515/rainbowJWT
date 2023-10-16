package com.auth0.rainbow.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.auth0.rainbow.IntegrationTest;
import com.auth0.rainbow.domain.AppLessonVideo;
import com.auth0.rainbow.repository.AppLessonVideoRepository;
import com.auth0.rainbow.service.dto.AppLessonVideoDTO;
import com.auth0.rainbow.service.mapper.AppLessonVideoMapper;
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
 * Integration tests for the {@link AppLessonVideoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AppLessonVideoResourceIT {

    private static final String DEFAULT_VIDEO_URL = "AAAAAAAAAA";
    private static final String UPDATED_VIDEO_URL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/app-lesson-videos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AppLessonVideoRepository appLessonVideoRepository;

    @Autowired
    private AppLessonVideoMapper appLessonVideoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAppLessonVideoMockMvc;

    private AppLessonVideo appLessonVideo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppLessonVideo createEntity(EntityManager em) {
        AppLessonVideo appLessonVideo = new AppLessonVideo().videoUrl(DEFAULT_VIDEO_URL);
        return appLessonVideo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppLessonVideo createUpdatedEntity(EntityManager em) {
        AppLessonVideo appLessonVideo = new AppLessonVideo().videoUrl(UPDATED_VIDEO_URL);
        return appLessonVideo;
    }

    @BeforeEach
    public void initTest() {
        appLessonVideo = createEntity(em);
    }

    @Test
    @Transactional
    void createAppLessonVideo() throws Exception {
        int databaseSizeBeforeCreate = appLessonVideoRepository.findAll().size();
        // Create the AppLessonVideo
        AppLessonVideoDTO appLessonVideoDTO = appLessonVideoMapper.toDto(appLessonVideo);
        restAppLessonVideoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appLessonVideoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AppLessonVideo in the database
        List<AppLessonVideo> appLessonVideoList = appLessonVideoRepository.findAll();
        assertThat(appLessonVideoList).hasSize(databaseSizeBeforeCreate + 1);
        AppLessonVideo testAppLessonVideo = appLessonVideoList.get(appLessonVideoList.size() - 1);
        assertThat(testAppLessonVideo.getVideoUrl()).isEqualTo(DEFAULT_VIDEO_URL);
    }

    @Test
    @Transactional
    void createAppLessonVideoWithExistingId() throws Exception {
        // Create the AppLessonVideo with an existing ID
        appLessonVideo.setId(1L);
        AppLessonVideoDTO appLessonVideoDTO = appLessonVideoMapper.toDto(appLessonVideo);

        int databaseSizeBeforeCreate = appLessonVideoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppLessonVideoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appLessonVideoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppLessonVideo in the database
        List<AppLessonVideo> appLessonVideoList = appLessonVideoRepository.findAll();
        assertThat(appLessonVideoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAppLessonVideos() throws Exception {
        // Initialize the database
        appLessonVideoRepository.saveAndFlush(appLessonVideo);

        // Get all the appLessonVideoList
        restAppLessonVideoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appLessonVideo.getId().intValue())))
            .andExpect(jsonPath("$.[*].videoUrl").value(hasItem(DEFAULT_VIDEO_URL)));
    }

    @Test
    @Transactional
    void getAppLessonVideo() throws Exception {
        // Initialize the database
        appLessonVideoRepository.saveAndFlush(appLessonVideo);

        // Get the appLessonVideo
        restAppLessonVideoMockMvc
            .perform(get(ENTITY_API_URL_ID, appLessonVideo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(appLessonVideo.getId().intValue()))
            .andExpect(jsonPath("$.videoUrl").value(DEFAULT_VIDEO_URL));
    }

    @Test
    @Transactional
    void getNonExistingAppLessonVideo() throws Exception {
        // Get the appLessonVideo
        restAppLessonVideoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAppLessonVideo() throws Exception {
        // Initialize the database
        appLessonVideoRepository.saveAndFlush(appLessonVideo);

        int databaseSizeBeforeUpdate = appLessonVideoRepository.findAll().size();

        // Update the appLessonVideo
        AppLessonVideo updatedAppLessonVideo = appLessonVideoRepository.findById(appLessonVideo.getId()).get();
        // Disconnect from session so that the updates on updatedAppLessonVideo are not directly saved in db
        em.detach(updatedAppLessonVideo);
        updatedAppLessonVideo.videoUrl(UPDATED_VIDEO_URL);
        AppLessonVideoDTO appLessonVideoDTO = appLessonVideoMapper.toDto(updatedAppLessonVideo);

        restAppLessonVideoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appLessonVideoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appLessonVideoDTO))
            )
            .andExpect(status().isOk());

        // Validate the AppLessonVideo in the database
        List<AppLessonVideo> appLessonVideoList = appLessonVideoRepository.findAll();
        assertThat(appLessonVideoList).hasSize(databaseSizeBeforeUpdate);
        AppLessonVideo testAppLessonVideo = appLessonVideoList.get(appLessonVideoList.size() - 1);
        assertThat(testAppLessonVideo.getVideoUrl()).isEqualTo(UPDATED_VIDEO_URL);
    }

    @Test
    @Transactional
    void putNonExistingAppLessonVideo() throws Exception {
        int databaseSizeBeforeUpdate = appLessonVideoRepository.findAll().size();
        appLessonVideo.setId(count.incrementAndGet());

        // Create the AppLessonVideo
        AppLessonVideoDTO appLessonVideoDTO = appLessonVideoMapper.toDto(appLessonVideo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppLessonVideoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appLessonVideoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appLessonVideoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppLessonVideo in the database
        List<AppLessonVideo> appLessonVideoList = appLessonVideoRepository.findAll();
        assertThat(appLessonVideoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAppLessonVideo() throws Exception {
        int databaseSizeBeforeUpdate = appLessonVideoRepository.findAll().size();
        appLessonVideo.setId(count.incrementAndGet());

        // Create the AppLessonVideo
        AppLessonVideoDTO appLessonVideoDTO = appLessonVideoMapper.toDto(appLessonVideo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppLessonVideoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appLessonVideoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppLessonVideo in the database
        List<AppLessonVideo> appLessonVideoList = appLessonVideoRepository.findAll();
        assertThat(appLessonVideoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAppLessonVideo() throws Exception {
        int databaseSizeBeforeUpdate = appLessonVideoRepository.findAll().size();
        appLessonVideo.setId(count.incrementAndGet());

        // Create the AppLessonVideo
        AppLessonVideoDTO appLessonVideoDTO = appLessonVideoMapper.toDto(appLessonVideo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppLessonVideoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appLessonVideoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppLessonVideo in the database
        List<AppLessonVideo> appLessonVideoList = appLessonVideoRepository.findAll();
        assertThat(appLessonVideoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAppLessonVideoWithPatch() throws Exception {
        // Initialize the database
        appLessonVideoRepository.saveAndFlush(appLessonVideo);

        int databaseSizeBeforeUpdate = appLessonVideoRepository.findAll().size();

        // Update the appLessonVideo using partial update
        AppLessonVideo partialUpdatedAppLessonVideo = new AppLessonVideo();
        partialUpdatedAppLessonVideo.setId(appLessonVideo.getId());

        partialUpdatedAppLessonVideo.videoUrl(UPDATED_VIDEO_URL);

        restAppLessonVideoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppLessonVideo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAppLessonVideo))
            )
            .andExpect(status().isOk());

        // Validate the AppLessonVideo in the database
        List<AppLessonVideo> appLessonVideoList = appLessonVideoRepository.findAll();
        assertThat(appLessonVideoList).hasSize(databaseSizeBeforeUpdate);
        AppLessonVideo testAppLessonVideo = appLessonVideoList.get(appLessonVideoList.size() - 1);
        assertThat(testAppLessonVideo.getVideoUrl()).isEqualTo(UPDATED_VIDEO_URL);
    }

    @Test
    @Transactional
    void fullUpdateAppLessonVideoWithPatch() throws Exception {
        // Initialize the database
        appLessonVideoRepository.saveAndFlush(appLessonVideo);

        int databaseSizeBeforeUpdate = appLessonVideoRepository.findAll().size();

        // Update the appLessonVideo using partial update
        AppLessonVideo partialUpdatedAppLessonVideo = new AppLessonVideo();
        partialUpdatedAppLessonVideo.setId(appLessonVideo.getId());

        partialUpdatedAppLessonVideo.videoUrl(UPDATED_VIDEO_URL);

        restAppLessonVideoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppLessonVideo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAppLessonVideo))
            )
            .andExpect(status().isOk());

        // Validate the AppLessonVideo in the database
        List<AppLessonVideo> appLessonVideoList = appLessonVideoRepository.findAll();
        assertThat(appLessonVideoList).hasSize(databaseSizeBeforeUpdate);
        AppLessonVideo testAppLessonVideo = appLessonVideoList.get(appLessonVideoList.size() - 1);
        assertThat(testAppLessonVideo.getVideoUrl()).isEqualTo(UPDATED_VIDEO_URL);
    }

    @Test
    @Transactional
    void patchNonExistingAppLessonVideo() throws Exception {
        int databaseSizeBeforeUpdate = appLessonVideoRepository.findAll().size();
        appLessonVideo.setId(count.incrementAndGet());

        // Create the AppLessonVideo
        AppLessonVideoDTO appLessonVideoDTO = appLessonVideoMapper.toDto(appLessonVideo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppLessonVideoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, appLessonVideoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(appLessonVideoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppLessonVideo in the database
        List<AppLessonVideo> appLessonVideoList = appLessonVideoRepository.findAll();
        assertThat(appLessonVideoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAppLessonVideo() throws Exception {
        int databaseSizeBeforeUpdate = appLessonVideoRepository.findAll().size();
        appLessonVideo.setId(count.incrementAndGet());

        // Create the AppLessonVideo
        AppLessonVideoDTO appLessonVideoDTO = appLessonVideoMapper.toDto(appLessonVideo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppLessonVideoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(appLessonVideoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppLessonVideo in the database
        List<AppLessonVideo> appLessonVideoList = appLessonVideoRepository.findAll();
        assertThat(appLessonVideoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAppLessonVideo() throws Exception {
        int databaseSizeBeforeUpdate = appLessonVideoRepository.findAll().size();
        appLessonVideo.setId(count.incrementAndGet());

        // Create the AppLessonVideo
        AppLessonVideoDTO appLessonVideoDTO = appLessonVideoMapper.toDto(appLessonVideo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppLessonVideoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(appLessonVideoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppLessonVideo in the database
        List<AppLessonVideo> appLessonVideoList = appLessonVideoRepository.findAll();
        assertThat(appLessonVideoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAppLessonVideo() throws Exception {
        // Initialize the database
        appLessonVideoRepository.saveAndFlush(appLessonVideo);

        int databaseSizeBeforeDelete = appLessonVideoRepository.findAll().size();

        // Delete the appLessonVideo
        restAppLessonVideoMockMvc
            .perform(delete(ENTITY_API_URL_ID, appLessonVideo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AppLessonVideo> appLessonVideoList = appLessonVideoRepository.findAll();
        assertThat(appLessonVideoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
