package com.example.common.services.impl;

import com.example.common.dao.DemoMapper;
import com.example.common.dao.IBaseDao;
import com.example.common.model.Demo;
import com.example.common.services.IDemoService;
import com.example.common.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @Author sen
 * @Date: 2019/3/14 21:01
 * @Description:
 */
@Service
public class DemoServiceImpl extends BaseServiceImpl<Demo> implements IDemoService {

    @Autowired
    private DemoMapper demoMapper;

    @Override
    public IBaseDao<Demo> getRepositoryDao() {
        return this.demoMapper;
    }

    @Override
    public void setDefaults(Demo record) {
        if(record.getId() == null || "".equals(record.getId())){
            record.setId(IdWorker.getIdString());
        }
        if(StringUtils.isEmpty(record.getAge())){
            record.setAge("0");
        }
    }
}
