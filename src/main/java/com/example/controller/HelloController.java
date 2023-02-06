package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HelloController {

    /*添加请求方式必须为POST请求*/
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String toIndex(){
        /*返回视图名称,刚才配置文件会自己给他加前缀/WEB-INF/templates/和后缀.html*/
        return "index";
    }

    @RequestMapping(
            value = {"/other", "other2", "other3"},
            method = {RequestMethod.GET, RequestMethod.POST}
    )
    public String toOther(){
        return "other";
    }

}
