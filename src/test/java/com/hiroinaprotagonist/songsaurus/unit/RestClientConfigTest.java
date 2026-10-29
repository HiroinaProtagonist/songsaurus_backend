package com.hiroinaprotagonist.songsaurus.unit;

import com.hiroinaprotagonist.songsaurus.config.RestClientConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ContextConfiguration(classes = RestClientConfig.class)
public class RestClientConfigTest {

    @Autowired
    private RestClientConfig restClientConfig = new RestClientConfig();

    @Test
    void positive_restClientConfig() {
        assertNotNull(restClientConfig);
        assertEquals(RestClient.create().getClass(),
                restClientConfig.restClient().getClass());
    }

}
