package com.example.common.services.impl;

import com.example.common.dao.IBaseDao;
import com.example.common.model.Role;
import com.example.common.services.IRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class RoleServiceImpl extends  BaseServiceImpl<Role> implements IRoleService {

    private static final Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);

    @Override
    public IBaseDao<Role> getRepositoryDao() {
        return null;
    }
}
