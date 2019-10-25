package com.example.common.services.impl;

import com.example.common.dao.IBaseDao;
import com.example.common.dao.IResourceDao;
import com.example.common.model.Resource;
import com.example.common.services.IResourceService;
import com.example.common.utils.IdWorker;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class ResourceServiceImpl extends BaseServiceImpl<Resource> implements IResourceService {

    private static final Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);

    @Autowired
    private IResourceDao resourceDao;

    @Override
    public IBaseDao<Resource> getRepositoryDao() {
        return this.resourceDao;
    }

    @Override
    public void setDefaults(Resource record) {
        String resourceId = record.getResourceId();
        if(StringUtils.isEmpty(resourceId)){
            record.setResourceId(IdWorker.getIdString());
        }
    }

    @Override
    public String getPrimaryKeyValue(Resource record) {
        return null;
    }

    @Override
    public void emptyPrimaryKeyValue(Resource record) {

    }
}
