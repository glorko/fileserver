import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import org.springframework.stereotype.Controller;

/**
 * Created by glorfindeil on 15.04.16.
 */

@SpringBootApplication
@Controller
@Configuration
@ComponentScan("net.punklan.glorfindeil")
public class FileServerApplication {
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(FileServerApplication.class, args);

    }

}
