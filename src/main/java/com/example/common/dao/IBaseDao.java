package com.example.common.dao;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 基础dao
 *
 * @Author sen
 * @Date: 2019/2/26 20:38
 * @Description:
 */
public interface IBaseDao<T> {
    Long countByExample(T record);

    int insert(T record);

    int batchInsert(List<T> records);

    int insertSelective(T rercod);

    int batchInsertSelective(List<T> records);

    int deleteByPrimaryKey(String key);

    int deleteByExample(T record);

    int batchDeleteByPrimaryKey(@Param("items") String[] keys);

    int updateByPrimaryKey(T record);

    int updateByPrimaryKeySelective(T record);

    int updateByExample(@Param("example") T example, @Param("record") T record);

    int updateByExampleSelective(@Param("example") T example, @Param("record") T record);

    int batchUpdateByPrimaryKey(List<T> record);

    int batchUpdateByPrimaryKeySelective(List<T> record);

    T selectByPrimaryKey(String key);

    List<T> selectByExample(@Param("record") T record, @Param("distinct") boolean distinct);

    PageList<T> selectByExampleByPager(@Param("example") T record, @Param("pageBounds") PageBounds pageBounds);

    T fuzzySearch(T record);

    PageList<T> fuzzySearchByPager(@Param("item") T record, @Param("pageBounds") PageBounds pageBounds);
}
