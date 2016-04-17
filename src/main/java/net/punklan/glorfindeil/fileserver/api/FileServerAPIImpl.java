package net.punklan.glorfindeil.fileserver.api;

import net.punklan.glorfindeil.fileserver.dao.FileHashDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.DigestUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

/**
 * Created by glorfindeil on 17.04.16.
 */

@Configuration
public class FileServerAPIImpl implements FileServerAPI {

    @Autowired
    FileHashDAO fileHashDAO;

    @Value("${net.punklan.glorfindeil.working.folder}")
    String fileFolder;

    private String getFileHash(byte[] file) {
        return new String(DigestUtils.md5Digest(file));
    }

    @Override
    public Boolean deleteByHash(String hash) throws FileServerAPIException {

        String filePath = fileHashDAO.getPathByHash(hash);
        if (filePath == null || filePath.equals("")) return false;
        try {
            Files.delete(Paths.get(fileFolder + filePath));
            fileHashDAO.deleteFileHash(hash);
        } catch (Exception e) {
            throw new FileServerAPIException(e.getMessage());
        }
        return true;
    }

    @Override
    public byte[] getByHash(String hash) throws FileServerAPIException {
        String filePath = fileHashDAO.getPathByHash(hash);
        if (filePath == null || filePath.equals("")) throw new FileServerAPIException("File not finded");
        try {
            return Files.readAllBytes(Paths.get(fileFolder + filePath));
        } catch (IOException e) {
            throw new FileServerAPIException("Error while reading file " + filePath);
        }
    }

    @Override
    public Map<String, String> searchByQuery(String query) throws FileServerAPIException {
        try {
            return fileHashDAO.searchByValue(query);
        } catch (IOException e) {
            throw new FileServerAPIException("Error while searching:" + e.getMessage());
        }
    }

    @Override
    public String uploadFile(String fileName, byte[] file) throws FileServerAPIException {
        String fileHash = getFileHash(file);
        String filePath = fileHashDAO.getPathByHash(fileHash);
        if (filePath != null) {
            throw new FileServerAPIException("File " + fileName + " already exists with name " + filePath);
        }
        try {
            fileHashDAO.addFileHash(fileHash, fileName);

            Files.write(Paths.get(fileFolder + fileName), file);
        } catch (Exception e) {
            throw new FileServerAPIException("Error while saving file: " + e.getMessage());
        }

        return fileHash;
    }
}
