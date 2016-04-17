package net.punklan.glorfindeil.fileserver.dao;

import net.punklan.glorfindeil.fileserver.FileServerApplication;
import org.junit.*;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.embedded.RedisServer;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by glorfindeil on 17.04.16.
 */
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(FileServerApplication.class)
@TestPropertySource(locations = "classpath:test.properties")
public class FileHashDAOTest {


    private final Logger log = LoggerFactory.getLogger(FileHashDAOTest.class);

    @Value("${spring.redis.port}")
    String redisPort;

    @Autowired
    FileHashDAO fileHashDAO;

    static RedisServer redisServer;

    @BeforeClass
    public static void setUpAll() throws IOException, InterruptedException {
        redisServer = new RedisServer(6380);
        redisServer.start();
        Thread.sleep(50);
    }

    @AfterClass
    public static void tearDownAll() {
        redisServer.stop();
    }

    @Before
    public void setUp() throws Exception {
        log.debug("Redis port is " + redisPort);

        fileHashDAO.addFileHash("hash1", "value1");
        fileHashDAO.addFileHash("hash2", "value2");
        fileHashDAO.addFileHash("hash3", "value3");
        fileHashDAO.addFileHash("hash4", "value4");
    }

    @After
    public void tearDown() throws Exception {
        fileHashDAO.deleteFileHash("hash1");
        fileHashDAO.deleteFileHash("hash2");
        fileHashDAO.deleteFileHash("hash3");
        fileHashDAO.deleteFileHash("hash4");
        fileHashDAO.deleteFileHash("hash5");
    }

    @Test
    public void getPathByHash() throws Exception {
        assertEquals("value4", fileHashDAO.getPathByHash("hash4"));
    }

    @Test
    public void addFileHash() throws Exception {
        fileHashDAO.addFileHash("hash5", "value5");
        assertEquals("value5", fileHashDAO.getPathByHash("hash5"));
    }

    @Test
    public void deleteFileHash() throws Exception {
        fileHashDAO.deleteFileHash("value4");
        assertNull(fileHashDAO.getPathByHash("value4"));
    }

    @Test
    public void searchByHash() throws Exception {
        //assertEquals(1, fileHashDAO.searchByHash("value4").size());
        assertEquals(4, fileHashDAO.searchByHash("hash*").size());

    }

    @Test
    public void searchByValue() throws Exception {
        assertEquals(4, fileHashDAO.searchByValue("value*").size());
    }
}