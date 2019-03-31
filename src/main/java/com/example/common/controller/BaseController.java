package com.example.common.controller;

import com.example.common.model.Demo;
import com.example.common.services.IDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author sen
 * @Date: 2019/3/6 19:56
 * @Description:
 */
@Controller
@RequestMapping("/base")
public class BaseController {

    @Autowired
    private IDemoService demoService;

    @RequestMapping("/index")
    public String base(HttpServletRequest request){
        return "index";
    }

    @RequestMapping("/success")
    public String success(Map<String,Object> map){
        map.put("hello","<h2>nihao</h2>");
        Demo demo = new Demo();
        demo.setName("zhangsan");
        demoService.selectByExample(demo);
        return "succes";
    }
}
