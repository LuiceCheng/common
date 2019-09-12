package com.example.common.dao;

import com.example.common.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IUserDao extends IBaseDao<User>{
}
