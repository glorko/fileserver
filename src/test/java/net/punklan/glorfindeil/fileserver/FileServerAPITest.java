package net.punklan.glorfindeil.fileserver;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

/**
 * Created by glorfindeil on 16.04.16.
 */
public class FileServerAPITest {

    @Autowired
    FileServerAPI api;

    @Before
    public void setUp() throws Exception {
        //TODO initalize storage
    }

    @Test
    public void deleteByHash() throws Exception {
        assertTrue(api.deleteByHash("testHash"));
    }

    @Test
    public void getByHash() throws Exception {
        byte[] testFile = new byte[0];
        assertArrayEquals(api.getByHash("testHash"), testFile);
    }

    @Test
    public void searchByQuery() throws Exception {
        assertEquals(api.searchByQuery(".txt").size(), 3);
    }

    @Test
    public void uploadFile() throws Exception {
        String testHash = "testHash2";
        String fileName = "testFile5.txt";
        Path path = Paths.get(fileName);

        assertEquals(api.uploadFile("testFile5.txt", Files.readAllBytes(path)), testHash);
    }
}