package net.punklan.glorfindeil.fileserver;

import net.punklan.glorfindeil.fileserver.dao.FileHashDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static net.punklan.glorfindeil.fileserver.api.FileServerAPIImpl.*;

/**
 * Created by glorfindeil on 17.04.16.
 */
@Component
public class FileFolderMonitor {

    @Autowired
    private FileHashDAO fileHashDAO;

    static RedisServer redisServer;

    @Value("${net.punklan.glorfindeil.working.folder}")
    String fileFolder;

    @PostConstruct
    public void init() {
        try {
            redisServer = new RedisServer(6380);

            redisServer.start();

            try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(fileFolder))) {
                for (Path entry : stream) {
                    byte[] file = Files.readAllBytes(entry);
                    String fileName = entry.getFileName() + "";
                    String hash = getFileHash(file);
                    fileHashDAO.addFileHash(hash, fileName);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("redis not started");
        }
    }

    @PreDestroy
    public void shutdown() {
        redisServer.stop();
    }
}