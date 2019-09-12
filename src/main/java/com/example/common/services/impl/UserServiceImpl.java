package com.example.common.services.impl;

import com.example.common.dao.IBaseDao;
import com.example.common.dao.IUserDao;
import com.example.common.entity.GlobalConstants;
import com.example.common.model.User;
import com.example.common.services.IUserService;
import com.example.common.utils.EncryptUtil;
import com.example.common.utils.IdWorker;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements IUserService {

    private static final Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);

    @Autowired
    private IUserDao userDao;

    @Override
    public void setDefaults(User record) {
        super.setDefaults(record);
        String userId = record.getUserId();
        if(StringUtils.isEmpty(userId)){
            userId = IdWorker.getIdString();
            record.setUserId(userId);
        }
        String password = record.getPassword();
        if(StringUtils.isNotEmpty(password)){
            String ps = EncryptUtil.AESencode(password, GlobalConstants.AES_KEY);
            record.setPassword(ps);
        }
    }

    @Override
    public IBaseDao<User> getRepositoryDao() {
        return this.userDao;
    }
}
