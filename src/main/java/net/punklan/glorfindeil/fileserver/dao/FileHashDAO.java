package net.punklan.glorfindeil.fileserver.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by glorfindeil on 17.04.16.
 */

@Repository
@Configuration
public class FileHashDAO {
    private final Logger log = LoggerFactory.getLogger(FileHashDAO.class);
    @Autowired
    StringRedisTemplate template;

    private final static String fileHashRedisKey = "fileHash";
    private final static int searchCount = 25;

    /**
     * @param text    Text to test
     * @param pattern (Wildcard) pattern to test
     * @return True if the text matches the wildcard pattern
     */
    private static boolean match(String text, String pattern) {
        return text.matches(pattern.replace("?", ".?").replace("*", ".*?"));
    }

    public void addFileHash(String hash, String filepath) {
        template.opsForHash().put(fileHashRedisKey, hash, filepath);

    }

    public void deleteFileHash(String hash) {
        template.opsForHash().delete(fileHashRedisKey, hash);
    }

    public Map<String, String> searchByHash(String query) throws IOException {
        Map<String, String> result = new HashMap<>();
        try (Cursor<Map.Entry<Object, Object>> cursor
                     = template.opsForHash().scan(fileHashRedisKey, ScanOptions.scanOptions().count(25).match(query).build())) {
            while (cursor.hasNext()) {
                Map.Entry<Object, Object> entry = cursor.next();
                System.out.println(entry.getKey() + " " + entry.getValue());
                result.put(entry.getKey().toString(), entry.getValue().toString());
            }
            /*cursor.forEachRemaining(n -> {
                result.put(n.getKey() + "", n.getValue() + "");
            });*/
        }

        return result;
    }

    public Map<String, String> searchByValue(String query) throws IOException {
        Map<String, String> result = new HashMap<>();
        template.opsForHash().entries(fileHashRedisKey).forEach((k, v) ->
        {
            if (match(v + "", query) && result.size() < searchCount) {
                result.put(k + "", v + "");
            }
        });
        return result;
    }

    public String getPathByHash(String hash) {
        return (String) template.opsForHash().get(fileHashRedisKey, hash);
    }
}