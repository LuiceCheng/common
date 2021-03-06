package com.example.common.services;

import com.example.common.dao.IBaseDao;
import com.example.common.entity.Msg;
import com.example.common.entity.PageBounds;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @Author sen
 * @Date: 2019/3/11 21:21
 * @Description:
 */
public interface IBaseService<T> {
    Long countByExample(T record);

    Msg<T> insert(T record);

    Msg<T> insertSelective(T record);

    Msg<List<T>> batchInsert(List<T> records);

    Msg<List<T>> batchInsertSelective(List<T> records);

    Msg<Boolean> deleteByPrimaryKey(String keys);

    Msg<Integer> deleteByExample(T record);

    Msg<Integer> batchDeleteByPrimaryKey(String[] keys);

    Msg<T> updateByPrimaryKey(T record);

    Msg<T> updateByPrimaryKeySelective(T record);

    Msg<List<T>> batchUpdateByPrimaryKey(List<T> records);

    Msg<List<T>> batchUpdateByPrimaryKeySelective(List<T> records);

    Msg<T> selectByPrimaryKey(String keys);

    Msg<List<T>> selectByExample(T record, boolean distinct);

    Msg<List<T>> selectByExample(T record);

    /**
     * 翻页查询
     * @param record
     * @return
     */
    Msg<PageInfo<T>> selectByPager(T record, PageBounds pageBounds);

    Msg<T> fuzzySearch(T record);

    Msg<PageInfo<T>> fuzzySearchByPager(T record, PageBounds pageBounds);

    IBaseDao<T> getRepositoryDao();

    void setDefaults(T record);

    /**
     * @param record 待验证对象字段
     * @return 验证结果 msg.code == 0 时表示该字段可用
     */
    Msg<String> uniqueValid(T record);

}
