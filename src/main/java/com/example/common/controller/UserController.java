package com.example.common.controller;

import com.example.common.entity.Msg;
import com.example.common.model.User;
import com.example.common.services.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * description:
 *
 * @author:cxs
 * @date: 2019/9/12 14:36
 */
@Controller
@RequestMapping("/user")
@Api(value = "用户管理", description = "用户管理")
public class UserController {
    @Autowired
    private IUserService userService;

    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public Msg<User> add(User user){
        Msg<User> insert = userService.insert(user);
        return insert;
    }

    @ApiOperation(value = "获取短信验证令牌", notes = "获取短信验证令牌")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "query", name = "phone", value = "电话号码", required = true),
        @ApiImplicitParam(paramType = "query", name = "vcode", value = "短信验证码", required = true)
    })
    @GetMapping(value = "/getVcodeToken")
    public Msg getVcodeToken(@RequestParam String phone, @RequestParam String vcode) {
        String token = userService.getSmsVcodeToken(phone, vcode);
        Msg msg = new Msg();
        msg.setData(token);
        return msg;
    }

    @ApiOperation(value = "修改密码（通过短信验证令牌）", notes = "通过短信验证令牌")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "query", name = "vcodeToken", value = "短信验证码令牌", required = true),
        @ApiImplicitParam(paramType = "query", name = "phone", value = "电话号码", required = true),
        @ApiImplicitParam(paramType = "query", name = "newPwd", value = "新密码(MD5)", required = true)
    })
    @PostMapping(value = "/modifyPwdWithVcodeToken")
    public Msg modifyPwdWithVcodeToken(@RequestParam String vcodeToken, @RequestParam String phone,
                                             @RequestParam String newPwd) {
        userService.modifyPwdWidthVcodeToken(vcodeToken, phone, newPwd);
        Msg msg = new Msg();

        return msg;
    }
}
