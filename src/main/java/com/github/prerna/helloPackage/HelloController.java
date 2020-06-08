package com.github.prerna.helloPackage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

//controller
@RestController
public class HelloController {

    @Autowired
    private ResourceBundleMessageSource messageSource;

    //URI - /helloWorld
    //@RequestMapping(method = RequestMethod.GET, path = "/helloWorld")
    @GetMapping("/helloWorld")
    public String helloWorld() {
        return "Hello World!";
    }

    @GetMapping("/helloWorld-bean")
    public UserDetails helloWorldBean() {
        return new UserDetails("prerna", "verma", "bangalore");
    }

    @GetMapping("hello-int")
    public String getMessagesInI18NFormat(){
        return "Hello world I18N";
    }
    @GetMapping("/helloWorld-i18n")
    public String getMessageInI18NFormat(@RequestHeader(name = "Accept-Language", required = false) String locale) {
        return messageSource.getMessage("label.hello", null, new Locale(locale));
    }

    @GetMapping("/helloWorld-i18n2")
    public String getMessageInI18NFormat2() {
        return messageSource.getMessage("label.hello", null, LocaleContextHolder.getLocale());
    }

}
