package com.example.common.controller;

import com.example.common.config.redis.RedisRepository;
import com.example.common.model.Demo;
import com.example.common.services.IDemoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private IDemoService demoService;

    @Autowired
    private RedisRepository redisRepository;

    @Autowired
    private HttpServletRequest request1;

    @RequestMapping("/index")
    public String base(HttpServletRequest request){
        return "index";
    }

    @RequestMapping("/success")
    public String success(Map<String,Object> map){
        logger.info("wolaila");
        map.put("hello","<h2>nihao2</h2>");
        Demo demo = new Demo();
        demo.setName("zhangsan");
        demoService.selectByExample(demo);
        redisRepository.set("abc","张三");
        String id = request1.getSession().getId();
        logger.info(id);
        Map<String, String> hashValue = redisRepository.getHashValue("spring:session:sessions:"+id);
        map.put("sessionId", id);
        return "succes";
    }
}
