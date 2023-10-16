package com.auth0.rainbow.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.auth0.rainbow.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AppLessonVideoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppLessonVideo.class);
        AppLessonVideo appLessonVideo1 = new AppLessonVideo();
        appLessonVideo1.setId(1L);
        AppLessonVideo appLessonVideo2 = new AppLessonVideo();
        appLessonVideo2.setId(appLessonVideo1.getId());
        assertThat(appLessonVideo1).isEqualTo(appLessonVideo2);
        appLessonVideo2.setId(2L);
        assertThat(appLessonVideo1).isNotEqualTo(appLessonVideo2);
        appLessonVideo1.setId(null);
        assertThat(appLessonVideo1).isNotEqualTo(appLessonVideo2);
    }
}
