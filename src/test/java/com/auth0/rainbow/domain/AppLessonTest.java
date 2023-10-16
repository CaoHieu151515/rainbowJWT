package com.auth0.rainbow.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.auth0.rainbow.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AppLessonTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppLesson.class);
        AppLesson appLesson1 = new AppLesson();
        appLesson1.setId(1L);
        AppLesson appLesson2 = new AppLesson();
        appLesson2.setId(appLesson1.getId());
        assertThat(appLesson1).isEqualTo(appLesson2);
        appLesson2.setId(2L);
        assertThat(appLesson1).isNotEqualTo(appLesson2);
        appLesson1.setId(null);
        assertThat(appLesson1).isNotEqualTo(appLesson2);
    }
}
