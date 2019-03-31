package com.example.common.services.impl;

import com.example.common.app.ContextUtil;
import com.example.common.app.user.AppUserDetails;
import com.example.common.dao.IBaseDao;
import com.example.common.entity.Msg;
import com.example.common.enums.EnError;
import com.example.common.services.IBaseService;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

/**
 * @Author sen
 * @Date: 2019/3/14 21:02
 * @Description:
 */
public abstract class BaseServiceImpl<T> implements IBaseService<T> {

    private static  final Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);
    @Override
    public Long countByExample(T record) {
        return getRepositoryDao().countByExample(record);
    }

    @Override
    public Msg<T> insert(T record) {
        Msg<T> msg = new Msg<>();
        setDefaults(record);
        setCreateInfo(record);
        int result = getRepositoryDao().insert(record);
        if(0 == result){
            msg.setResult(EnError.INSERT_NONE);
            return msg;
        }
        msg.setData(record);
        return msg;
    }

    @Override
    public Msg<T> insertSelective(T record) {
        return null;
    }

    @Override
    public Msg<List<T>> batchInsert(List<T> records) {
        return null;
    }

    @Override
    public Msg<List<T>> batchInsertSelective(List<T> record) {
        return null;
    }

    @Override
    public Msg<Boolean> deleteByPrimaryKey(String keys) {
        return null;
    }

    @Override
    public Msg<Integer> deleteByExample(T record) {
        return null;
    }

    @Override
    public Msg<Integer> batchDeleteByPrimaryKey(String[] keys) {
        return null;
    }

    @Override
    public Msg<T> updateByPrimaryKey(T record) {
        return null;
    }

    @Override
    public Msg<T> updateByPrimaryKeySelective(T record) {
        return null;
    }

    @Override
    public Msg<T> updateByExample(T example, T record) {
        return null;
    }

    @Override
    public Msg<T> updateByExampleSelective(T example, T record) {
        return null;
    }

    @Override
    public Msg<List<T>> batchUpdateByPrimaryKey(List<T> record) {
        return null;
    }

    @Override
    public Msg<List<T>> batchUpdateByPrimaryKeySelective(List<T> record) {
        return null;
    }

    @Override
    public Msg<T> selectByPrimaryKey(String keys) {
        return null;
    }

    @Override
    public Msg<List<T>> selectByExample(T record, boolean distinct) {
        return null;
    }

    @Override
    public Msg<List<T>> selectByExample(T record) {
        Msg<List<T>> msg = new Msg<>();
        List<T> result = getRepositoryDao().selectByExample(record,false);
        if(null == result || result.isEmpty()){
            msg.setResult(EnError.NO_MATCH);
            return msg;
        }
        msg.setData(result);
        return msg;
    }

    @Override
    public Msg<List<T>> selectAllByExample(T record) {
        return null;
    }

    @Override
    public Msg<PageList<T>> selectByExampleByPager(T record, PageBounds pageBounds) {
        return null;
    }

    @Override
    public Msg<T> fuzzySearch(T record) {
        return null;
    }

    @Override
    public Msg<PageList<T>> fuzzySearchByPager(T record, PageBounds pageBounds) {
        return null;
    }

    @Override
    public void setDefaults(T record) {

    }

    @Override
    public Msg<String> uniqueValid(T record) {
        return null;
    }

    private void setCreateInfo(T record){
        try{
            Method setCreateAt = record.getClass().getMethod("setCreateAt", Date.class);
            setCreateAt.invoke(record,new Date());
            AppUserDetails user = ContextUtil.getCurrentUser();
            Method setCreateBy = record.getClass().getMethod("setCreateBy", String.class);
            setCreateBy.invoke(record, user.getUserId());
        }catch (Exception  e){
            logger.error("get user error{}",e);
        }
    }

    public void setUpdateInfo(T record){
        try{
            Method setUpdateAt = record.getClass().getMethod("setUpdateAt", Date.class);
            setUpdateAt.invoke(record,new Date());
            AppUserDetails user = ContextUtil.getCurrentUser();
            Method setUpdateBy = record.getClass().getMethod("setUpdateBy", String.class);
            setUpdateBy.invoke(record, user.getUserId());
        }catch (Exception  e){
            logger.error("get user error{}",e);
        }
    }
}
