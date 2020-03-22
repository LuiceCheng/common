package com.example.common.dao;

import com.example.common.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IUserDao extends IBaseDao<User>{
    /**
     * 根据用户名查询登录用户
     * @param user
     * @return
     */
    List<User> getUserByName(User user);
}
