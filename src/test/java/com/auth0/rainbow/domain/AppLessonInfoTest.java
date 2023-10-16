package com.auth0.rainbow.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.auth0.rainbow.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AppLessonInfoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppLessonInfo.class);
        AppLessonInfo appLessonInfo1 = new AppLessonInfo();
        appLessonInfo1.setId(1L);
        AppLessonInfo appLessonInfo2 = new AppLessonInfo();
        appLessonInfo2.setId(appLessonInfo1.getId());
        assertThat(appLessonInfo1).isEqualTo(appLessonInfo2);
        appLessonInfo2.setId(2L);
        assertThat(appLessonInfo1).isNotEqualTo(appLessonInfo2);
        appLessonInfo1.setId(null);
        assertThat(appLessonInfo1).isNotEqualTo(appLessonInfo2);
    }
}
