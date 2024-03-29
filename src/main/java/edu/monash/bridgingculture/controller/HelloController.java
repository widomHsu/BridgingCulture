package edu.monash.bridgingculture.controller;

import edu.monash.bridgingculture.service.entity.ResponseDO;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("")
@Hidden
public class HelloController {

    @RequestMapping("/")
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
