package com.example.common.controller;

import com.example.common.entity.Msg;
import com.example.common.model.User;
import com.example.common.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * description:
 *
 * @author:cxs
 * @date: 2019/9/12 14:36
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public Msg<User> add(User user){
        Msg<User> insert = userService.insert(user);
        return insert;
    }
}
