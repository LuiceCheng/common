package com.example.common.controller;

import com.example.common.entity.Msg;
import com.example.common.entity.PageBounds;
import com.example.common.model.User;
import com.example.common.services.IUserService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * description:
 *
 * @author:cxs
 * @date: 2019/9/12 14:36
 */
@RestController
@RequestMapping("/user")
@Api(value = "用户管理")
public class UserController {
    @Autowired
    private IUserService userService;

    @ApiOperation(value = "新增用户", tags = "用户")
    @PostMapping(value = "add")
    @ResponseBody
    public Msg<User> add(@RequestBody User user){
        Msg<User> insert = userService.insert(user);
        return insert;
    }

    @ApiOperation(value = "获取短信验证令牌", notes = "获取短信验证令牌", tags = "用户")
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

    @ApiOperation(value = "修改密码（通过短信验证令牌）", notes = "通过短信验证令牌",tags = "用户")
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


    @ApiOperation(value = "获取用户", notes = "获取yonghu",tags = "用户")
    @PostMapping(value = "/selectByPager")
    public Msg<PageInfo<User>>  selectByPager(@RequestBody PageBounds pageBounds){
        User user = new User();
        Msg<PageInfo<User>> pageInfoMsg = userService.selectByPager(user, pageBounds);
        return pageInfoMsg;
    }

    @ApiOperation(value = "countByExample", tags = "用户", httpMethod = "GET")
    @GetMapping("countByExample")
    public Msg countByExample(@ModelAttribute User user){
        Long aLong = userService.countByExample(user);
        Msg msg = new Msg();
        msg.setData(aLong);
        return msg;
    }

    @ApiOperation(value = "insertSelective", tags = "用户")
    @PostMapping("insertSelective")
    public Msg insertSelective(@RequestBody User user){
        Msg<User> userMsg = userService.insertSelective(user);
        return userMsg;
    }

    @ApiOperation(value = "batchInsert", tags = "用户")
    @PostMapping("batchInsert")
    public Msg batchInsert(@RequestBody List<User> userList){
        Msg<List<User>> listMsg = userService.batchInsert(userList);
        return listMsg;
    }

    @ApiOperation(value = "batchInsertSelective", tags = "用户")
    @PostMapping("batchInsertSelective")
    public Msg batchInsertSelective(@RequestBody List<User> userList){
        Msg<List<User>> listMsg = userService.batchInsertSelective(userList);
        return listMsg;
    }
}
