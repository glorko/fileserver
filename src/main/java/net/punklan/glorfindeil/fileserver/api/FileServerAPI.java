package net.punklan.glorfindeil.fileserver.api;

import javafx.util.Pair;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * Created by glorfindeil on 16.04.16.
 */
@Configuration
public interface FileServerAPI {

    /**
     * Deletes file
     * @param hash - hash for delete
     * @return true if succesfully deleted, false if file was not found
     * @throws FileServerAPIException
     */
    public Boolean deleteByHash(String hash) throws FileServerAPIException;

    /**
     * Recieve pair filename-file as byte array by hash. Throw Exception if not finded
     * @param hash
     * @return
     * @throws FileServerAPIException
     */
    public Pair<String,byte[]> getByHash(String hash) throws FileServerAPIException;

    /**
     * Implementation of searching by value
     * @param query
     * @return
     * @throws FileServerAPIException
     */
    public Map<String, String> searchByQuery(String query) throws FileServerAPIException;

    /**
     * Uploads a new file
     * @param fileName
     * @param file
     * @return
     * @throws FileServerAPIException
     */
    public String uploadFile(String fileName, byte[] file) throws FileServerAPIException;

}
