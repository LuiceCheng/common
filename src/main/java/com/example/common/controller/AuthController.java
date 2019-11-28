package com.example.common.controller;

import com.example.common.config.security.jwt.JwtUtil;
import com.example.common.entity.AuthRequestUser;
import com.example.common.entity.GlobalConstants;
import com.example.common.entity.Msg;
import com.example.common.model.JwtUser;
import com.example.common.model.User;
import com.example.common.services.IUserService;
import com.example.common.utils.EncryptUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * description:
 *
 * @author:cxs
 * @date: 2019/10/29 9:06
 */
@Api(description = "公共")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private IUserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @ApiOperation(value = "生成用户token", tags = "公共")
    @PostMapping(value = "getToken")
    public Msg getUserToken(@RequestBody AuthRequestUser authRequestUser) {
        Msg msg = new Msg();
        User queryUser = new User();
        queryUser.setUserName(authRequestUser.getUserName());
        Msg<List<User>> listMsg = userService.selectByExample(queryUser);
        if (null == listMsg || null == listMsg.getData() || listMsg.getData().size() ==0) {
            msg.setMsg("账号不存在");
            return msg;
        }
        User responseUser = listMsg.getData().get(0);
        String ps = EncryptUtil.AESdecode(responseUser.getPassword(),GlobalConstants.AES_KEY);
        if(!ps.equals(authRequestUser.getPassword())){
            msg.setMsg("密码不正确");
            return msg;
        }

        JwtUser jwtUser = new JwtUser();
        jwtUser.setUser(responseUser);
        String token = jwtUtil.generateToken(jwtUser);
        jwtUser.setToken(token);
        msg.setData(jwtUser);
        return msg;
    }

    @ApiOperation(value = "登录页面跳转", tags = "公共")
    @PostMapping(value = "/login")
    public String welcome(@RequestBody User user){

        return "success";
    }

    @ApiOperation(value = "获取用户名", tags = "公共")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "query", dataType = "String", name = "userName", value = "用户id", required = true)
    })
    @GetMapping(value = "/getUserName")
    public String getUserName(@RequestParam String userName){

        return userName;
    }
}
