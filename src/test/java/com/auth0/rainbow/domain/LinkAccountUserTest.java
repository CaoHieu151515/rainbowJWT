package com.auth0.rainbow.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.auth0.rainbow.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LinkAccountUserTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LinkAccountUser.class);
        LinkAccountUser linkAccountUser1 = new LinkAccountUser();
        linkAccountUser1.setId(1L);
        LinkAccountUser linkAccountUser2 = new LinkAccountUser();
        linkAccountUser2.setId(linkAccountUser1.getId());
        assertThat(linkAccountUser1).isEqualTo(linkAccountUser2);
        linkAccountUser2.setId(2L);
        assertThat(linkAccountUser1).isNotEqualTo(linkAccountUser2);
        linkAccountUser1.setId(null);
        assertThat(linkAccountUser1).isNotEqualTo(linkAccountUser2);
    }
}
