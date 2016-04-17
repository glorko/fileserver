package net.punklan.glorfindeil.fileserver.translation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Locale;
import java.util.Properties;

/**
 * Created by glorfindeil on 17.04.16.
 */
@Controller
@RequestMapping("/messageBundle")
public class SerializableMessageBundleController {

    @Autowired
    SerializableResourceBundleMessageSource messageBundle;

    /**
     * ReadAll
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Properties list(@RequestParam String lang) {
        return messageBundle.getAllProperties(new Locale(lang));
    }
}
