package com.auth0.rainbow.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.auth0.rainbow.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AppQuestionVideoInfoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppQuestionVideoInfo.class);
        AppQuestionVideoInfo appQuestionVideoInfo1 = new AppQuestionVideoInfo();
        appQuestionVideoInfo1.setId(1L);
        AppQuestionVideoInfo appQuestionVideoInfo2 = new AppQuestionVideoInfo();
        appQuestionVideoInfo2.setId(appQuestionVideoInfo1.getId());
        assertThat(appQuestionVideoInfo1).isEqualTo(appQuestionVideoInfo2);
        appQuestionVideoInfo2.setId(2L);
        assertThat(appQuestionVideoInfo1).isNotEqualTo(appQuestionVideoInfo2);
        appQuestionVideoInfo1.setId(null);
        assertThat(appQuestionVideoInfo1).isNotEqualTo(appQuestionVideoInfo2);
    }
}
