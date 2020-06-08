package com.github.prerna.helloPackage;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//controller
@RestController
public class HelloController {

    //URI - /helloWorld
    //@RequestMapping(method = RequestMethod.GET, path = "/helloWorld")
    @GetMapping("/helloWorld")
    public String helloWorld(){
        return "Hello World!";
    }

    @GetMapping("/helloWorld-bean")
    public UserDetails helloWorldBean(){
        return new UserDetails("prerna", "verma", "bangalore");
    }

    @GetMapping("hello-int")
    public String getMessagesInI18NFormat(){
        return "Hello world I18N";
    }
}
