package edu.monash.bridgingculture.controller;

import edu.monash.bridgingculture.controller.annotation.Log;
import edu.monash.bridgingculture.service.entity.ResponseDO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("")
public class HelloController {

    /**
     * Redirects the user to the index page.
     *
     * @return String representing the redirect path
     */
    @GetMapping("/")
    @Log
    public String hello(){
        return "redirect:/index.html";
    }

    @RequestMapping("/abdel")
    @ResponseBody
    public ResponseDO helloAbdel(){
        return ResponseDO.success("عاشت فلسطين حره");
    }

    @RequestMapping("/test")
    @ResponseBody
    public ResponseDO test(){
        return ResponseDO.success("Hello World!");
    }
}
