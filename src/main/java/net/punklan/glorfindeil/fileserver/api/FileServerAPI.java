package net.punklan.glorfindeil.fileserver.api;

import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * Created by glorfindeil on 16.04.16.
 */
@Configuration
public interface FileServerAPI {

    public Boolean deleteByHash(String hash) throws FileServerAPIException;

    public byte[] getByHash(String hash) throws FileServerAPIException;

    public Map<String, String> searchByQuery(String query) throws FileServerAPIException;

    public String uploadFile(String fileName, byte[] file) throws FileServerAPIException;

}
