package net.punklan.glorfindeil.fileserver;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static junit.framework.TestCase.assertNotNull;

/**
 * Created by glorfindeil on 17.04.16.
 */
@ActiveProfiles("test")
@SpringApplicationConfiguration(FileServerApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(locations = "classpath:test.properties")
public class LocalRedisConfigTest {

    @Autowired
    private JedisConnectionFactory jedisConnectionFactory;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    public void testJedisConnectionFactory() {
        assertNotNull(jedisConnectionFactory);
    }

    @Test
    public void testRedisTemplate() {
        assertNotNull(redisTemplate);
    }

}