package com.example.common.services.impl;

import com.example.common.dao.IBaseDao;
import com.example.common.model.RoleResourceLink;
import com.example.common.services.IRoleResourceLinkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class RoleResourceLinkServiceImpl extends BaseServiceImpl<RoleResourceLink> implements IRoleResourceLinkService {

    private static final Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);


    @Override
    public IBaseDao<RoleResourceLink> getRepositoryDao() {
        return null;
    }
}
