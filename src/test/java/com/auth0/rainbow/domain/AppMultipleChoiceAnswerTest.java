package com.auth0.rainbow.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.auth0.rainbow.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AppMultipleChoiceAnswerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppMultipleChoiceAnswer.class);
        AppMultipleChoiceAnswer appMultipleChoiceAnswer1 = new AppMultipleChoiceAnswer();
        appMultipleChoiceAnswer1.setId(1L);
        AppMultipleChoiceAnswer appMultipleChoiceAnswer2 = new AppMultipleChoiceAnswer();
        appMultipleChoiceAnswer2.setId(appMultipleChoiceAnswer1.getId());
        assertThat(appMultipleChoiceAnswer1).isEqualTo(appMultipleChoiceAnswer2);
        appMultipleChoiceAnswer2.setId(2L);
        assertThat(appMultipleChoiceAnswer1).isNotEqualTo(appMultipleChoiceAnswer2);
        appMultipleChoiceAnswer1.setId(null);
        assertThat(appMultipleChoiceAnswer1).isNotEqualTo(appMultipleChoiceAnswer2);
    }
}
