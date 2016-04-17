package net.punklan.glorfindeil.fileserver;

import net.punklan.glorfindeil.fileserver.api.FileServerAPI;
import net.punklan.glorfindeil.fileserver.api.FileServerAPIException;
import org.apache.commons.io.FileUtils;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.embedded.RedisServer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

/**
 * Created by glorfindeil on 16.04.16.
 */
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(FileServerApplication.class)
@TestPropertySource(locations = "classpath:test.properties")
public class FileServerAPITest {

    @Value("${spring.redis.port}")
    String redisPort;

    @Value("${net.punklan.glorfindeil.working.folder}")
    String fileFolder;

    String resourcesPath = "src/test/resources/";

    String fileTestHash;
    String testFileName = "testFile1.txt";
    byte[] file;
    @Autowired
    FileServerAPI api;

    static RedisServer redisServer;


    @BeforeClass
    public static void setUpAll() throws IOException {
        redisServer = new RedisServer(6380);
        redisServer.start();


    }

    @AfterClass
    public static void tearDownAll() {
        redisServer.stop();
    }

    @Before
    public void setUp() throws Exception {
        FileUtils.deleteDirectory(new File(fileFolder));
        Files.createDirectory(Paths.get(fileFolder));
        file = Files.readAllBytes(Paths.get(resourcesPath+testFileName));
        fileTestHash = api.uploadFile(testFileName, file);
    }

    @After
    public void tearDown() throws Exception {
        api.deleteByHash(fileTestHash);
    }

    @Test
    public void deleteByHash() throws Exception {
        assertTrue(api.deleteByHash(fileTestHash));
    }

    @Test
    public void getByHash() throws Exception {

        assertArrayEquals(api.getByHash(fileTestHash).getValue(), file);
    }

    @Test
    public void searchByQuery() throws Exception {
        assertEquals(api.searchByQuery("*.txt").size(), 1);
    }

    @Test
    public void uploadFile() throws Exception {
        byte[] file2 = Files.readAllBytes(Paths.get(resourcesPath+"testFile4.xml"));
        api.uploadFile("testFile4.xml", file2);
        assertEquals(api.searchByQuery("testFile*").size(), 2);
    }

    @Test(expected = FileServerAPIException.class)
    public void uploadSameFile() throws FileServerAPIException, IOException {
        byte[] file2 = Files.readAllBytes(Paths.get(resourcesPath+"testFile2.txt"));
        api.uploadFile("testFile2.txt", file2);

    }
}