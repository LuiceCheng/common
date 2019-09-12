package com.example.common.services.impl;

import com.example.common.dao.IBaseDao;
import com.example.common.model.UserRoleLink;
import com.example.common.services.IUserRoleLinkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class UserRoleLinkServiceImpl extends BaseServiceImpl<UserRoleLink> implements IUserRoleLinkService {

    private static final Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);


    @Override
    public IBaseDao<UserRoleLink> getRepositoryDao() {
        return null;
    }
}
