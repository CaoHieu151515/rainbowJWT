package com.auth0.rainbow.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.auth0.rainbow.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AppQuestionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppQuestion.class);
        AppQuestion appQuestion1 = new AppQuestion();
        appQuestion1.setId(1L);
        AppQuestion appQuestion2 = new AppQuestion();
        appQuestion2.setId(appQuestion1.getId());
        assertThat(appQuestion1).isEqualTo(appQuestion2);
        appQuestion2.setId(2L);
        assertThat(appQuestion1).isNotEqualTo(appQuestion2);
        appQuestion1.setId(null);
        assertThat(appQuestion1).isNotEqualTo(appQuestion2);
    }
}
