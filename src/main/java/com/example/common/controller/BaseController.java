package com.example.common.controller;

import com.example.common.config.redis.RedisRepository;
import com.example.common.entity.Msg;
import com.example.common.model.Demo;
import com.example.common.model.User;
import com.example.common.services.IDemoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.naming.Name;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author sen
 * @Date: 2019/3/6 19:56
 * @Description:
 */
@Api(description = "测试")
@Controller
@RequestMapping("/base")
public class BaseController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private IDemoService demoService;

    @Autowired
    private RedisRepository redisRepository;

    @Autowired
    private HttpServletRequest request1;

    @ApiOperation(value = "静态页面跳转", tags = "测试", httpMethod = "GET")
    @RequestMapping("/index")
    public String base(HttpServletRequest request){
        return "index";
    }

    @ApiOperation(value = "thymeleaf页面跳转", tags = "测试", httpMethod = "GET")
    @RequestMapping("/success")
    public String success(Map<String,Object> map){
//        logger.info("wolaila");
//        map.put("hello","<h2>nihao2</h2>");
//        Demo demo = new Demo();
//        demo.setName("zhangsan");
//        demoService.selectByExample(demo);
//        redisRepository.set("abc","张三");
//        String id = request1.getSession().getId();
//        logger.info(id);
//        Map<String, String> hashValue = redisRepository.getHashValue("spring:session:sessions:"+id);
//        map.put("sessionId", id);
        return "succes";
    }

    @ApiOperation(value = "home页面跳转", tags = "测试", httpMethod = "GET")
    @RequestMapping("home")
    public String home(){
        return "login";
    }

}
