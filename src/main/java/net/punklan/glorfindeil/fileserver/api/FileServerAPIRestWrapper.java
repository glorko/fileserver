package net.punklan.glorfindeil.fileserver.api;

import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by glorfindeil on 17.04.16.
 */

@RestController
@Configuration
public class FileServerAPIRestWrapper {
    @Autowired
    FileServerAPI api;

    @RequestMapping(value = "/fileserver", method = RequestMethod.POST)
    public
    @ResponseBody
    String uploadFile(
            @RequestParam("name") String fileName, @RequestParam("file") MultipartFile file) throws FileServerAPIException {
        try {
            return api.uploadFile(fileName, file.getBytes());
        } catch (IOException e) {
            throw new FileServerAPIException("Error while saving file: " + e.getMessage());
        }
    }

    @RequestMapping(value = "/fileserver", method = RequestMethod.GET)
    public void download(final HttpServletRequest request, final HttpServletResponse response, String hash) throws FileServerAPIException {
        Pair<String, byte[]> file = api.getByHash(hash);
        byte[] binaryFile = file.getValue();
        String fileName = file.getKey();
        OutputStream output = null;
        try {
            output = response.getOutputStream();
            response.reset();

            response.setContentType("application/octet-stream");
            response.setContentLength(binaryFile.length);
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            output.write(binaryFile);


            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
