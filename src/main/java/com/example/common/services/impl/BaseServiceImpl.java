package com.example.common.services.impl;

import com.example.common.app.ContextUtil;
import com.example.common.app.user.AppUserDetails;
import com.example.common.entity.GlobalConstants;
import com.example.common.entity.Msg;
import com.example.common.enums.EnError;
import com.example.common.services.IBaseService;
import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author sen
 * @Date: 2019/3/14 21:02
 * @Description:
 */
public abstract class BaseServiceImpl<T> implements IBaseService<T> {

    private static final Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);

    public abstract String getPrimaryKeyValue(T record);

    public abstract void emptyPrimaryKeyValue(T record);

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
        if (0 == result) {
            msg.setResult(EnError.INSERT_NONE);
            return msg;
        }
        msg.setData(record);
        return msg;
    }

    @Override
    public Msg<T> insertSelective(T record) {
        Msg<T> msg = new Msg<>();
        setDefaults(record);
        setCreateInfo(record);
        int result = getRepositoryDao().insertSelective(record);
        if (0 == result) {
            msg.setResult(EnError.INSERT_NONE);
            return msg;
        }
        msg.setData(record);
        return msg;
    }

    @Override
    public Msg<List<T>> batchInsert(List<T> records) {
        int insertLength = records.size();
        int i = 0;
        while (insertLength > GlobalConstants.BATCH_INSERT_SIZE) {
            getRepositoryDao().batchInsert(records.subList(i, i + insertLength));
            i = i + GlobalConstants.BATCH_INSERT_SIZE;
            insertLength = insertLength - GlobalConstants.BATCH_INSERT_SIZE;
        }
        if (insertLength > 0) {
            getRepositoryDao().batchInsert(records.subList(i, i + insertLength));
        }
        Msg result = new Msg();
        result.setData(records);
        return result;
    }

    @Override
    public Msg<List<T>> batchInsertSelective(List<T> records) {
        int insertLength = records.size();
        int i = 0;
        while (insertLength > GlobalConstants.BATCH_INSERT_SIZE) {
            getRepositoryDao().batchInsertSelective(records.subList(i, i + insertLength));
            i = i + GlobalConstants.BATCH_INSERT_SIZE;
            insertLength = insertLength - GlobalConstants.BATCH_INSERT_SIZE;
        }
        if (insertLength > 0) {
            getRepositoryDao().batchInsertSelective(records.subList(i, i + insertLength));
        }
        Msg result = new Msg();
        result.setData(records);
        return result;
    }

    @Override
    public Msg<Boolean> deleteByPrimaryKey(String keys) {
        Msg<Boolean> msg = new Msg<>();
        int result = getRepositoryDao().deleteByPrimaryKey(keys);
        if (0 == result) {
            msg.setResult(EnError.DELETE_NONE);
            return msg;
        }

        return msg;
    }

    @Override
    public Msg<Integer> deleteByExample(T record) {
        Msg<Integer> msg = new Msg<>();
        int result = getRepositoryDao().deleteByExample(record);
        if (0 == result) {
            msg.setResult(EnError.DELETE_NONE);
            return msg;
        }

        msg.setData(result);
        return msg;
    }

    @Override
    public Msg<Integer> batchDeleteByPrimaryKey(String[] keys) {
        Msg<Integer> msg = new Msg<>();
        int result = getRepositoryDao().batchDeleteByPrimaryKey(keys);
        if (0 == result) {
            msg.setResult(EnError.DELETE_NONE);
            return msg;
        }

        msg.setData(result);
        return msg;
    }

    @Override
    public Msg<T> updateByPrimaryKey(T record) {
        Msg<T> msg = new Msg<>();
        setDefaults(record);
        setUpdateInfo(record);
        int result = getRepositoryDao().updateByPrimaryKey(record);
        if (0 == result) {
            msg.setResult(EnError.UPDATE_NONE);
            return msg;
        }

        msg.setData(record);
        return msg;
    }

    @Override
    public Msg<T> updateByPrimaryKeySelective(T record) {
        Msg<T> msg = new Msg<>();
        setDefaults(record);
        setUpdateInfo(record);
        int result = getRepositoryDao().updateByPrimaryKeySelective(record);
        if (0 == result) {
            msg.setResult(EnError.UPDATE_NONE);
            return msg;
        }

        // 重新获取数据
        T dbRecord = getRepositoryDao().selectByPrimaryKey(getPrimaryKeyValue(record));
        msg.setData(dbRecord);
        return msg;
    }

    @Override
    public Msg<T> updateByExample(T example, T record) {
        Msg<T> msg = new Msg<>();
        setDefaults(record);
        setUpdateInfo(record);
        int result = getRepositoryDao().updateByExample(example, record);
        if (0 == result) {
            msg.setResult(EnError.UPDATE_NONE);
            return msg;
        }

        // 重新获取数据
        T dbRecord = getRepositoryDao().selectByPrimaryKey(getPrimaryKeyValue(record));
        msg.setData(dbRecord);
        return msg;
    }

    @Override
    public Msg<T> updateByExampleSelective(T example, T record) {
        Msg<T> msg = new Msg<>();
        setDefaults(record);
        setUpdateInfo(record);
        int result = getRepositoryDao().updateByExampleSelective(example, record);
        if (0 == result) {
            msg.setResult(EnError.UPDATE_NONE);
            return msg;
        }

        // 重新获取数据
        T dbRecord = getRepositoryDao().selectByPrimaryKey(getPrimaryKeyValue(record));
        msg.setData(dbRecord);
        return msg;
    }

    @Override
    public Msg<List<T>> batchUpdateByPrimaryKey(List<T> records) {
        Msg<List<T>> msg = new Msg<>();
        for (T record : records) {
            setDefaults(record);
            setUpdateInfo(record);
        }
        int result = getRepositoryDao().batchUpdateByPrimaryKey(records);
        if (0 == result) {
            msg.setResult(EnError.UPDATE_NONE);
            return msg;
        }

        msg.setData(records);
        return msg;
    }

    @Override
    public Msg<List<T>> batchUpdateByPrimaryKeySelective(List<T> records) {
        Msg<List<T>> msg = new Msg<>();
        for (T record : records) {
            setDefaults(record);
            setUpdateInfo(record);
        }
        int result = getRepositoryDao().batchUpdateByPrimaryKeySelective(records);
        if (0 == result) {
            msg.setResult(EnError.UPDATE_NONE);
            return msg;
        }

        msg.setData(records);
        return msg;
    }

    @Override
    public Msg<T> selectByPrimaryKey(String keys) {
        Msg<T> msg = new Msg<>();
        T result = getRepositoryDao().selectByPrimaryKey(keys);
        if (null == result) {
            msg.setResult(EnError.NO_MATCH);
            return msg;
        }

        msg.setData(result);
        return msg;
    }

    @Override
    public Msg<List<T>> selectByExample(T record, boolean distinct) {
        Msg<List<T>> msg = new Msg<>();
        List<T> result = getRepositoryDao().selectByExample(record, distinct);
        if (null == result || result.isEmpty()) {
            msg.setResult(EnError.NO_MATCH);
            return msg;
        }

        msg.setData(result);
        return msg;
    }

    @Override
    public Msg<List<T>> selectByExample(T record) {
        Msg<List<T>> msg = new Msg<>();
        List<T> result = getRepositoryDao().selectByExample(record, false);
        if (null == result || result.isEmpty()) {
            msg.setResult(EnError.NO_MATCH);
            return msg;
        }
        msg.setData(result);
        return msg;
    }

    @Override
    public Msg<List<T>> selectAllByExample(T record) {
        Msg<List<T>> msg = new Msg<>();
        Msg<PageList<T>> pageListMsg = new Msg<>();
        List<T> list = new ArrayList<>();
        PageBounds pageBounds = new PageBounds(1, 5000);

        while (0 == pageListMsg.getCode()) {
            pageListMsg = selectByExampleByPager(record, pageBounds);
            if (0 == pageListMsg.getCode()) {
                list.addAll(pageListMsg.getData());
            }

            pageBounds.setPage(pageBounds.getPage() + 1);
        }

        if (list.isEmpty()) {
            return msg;
        }

        msg.setData(list);
        return msg;
    }

    @Override
    public Msg<PageList<T>> selectByExampleByPager(T record, PageBounds pageBounds) {
        Msg<PageList<T>> msg = new Msg<>();
        PageList<T> result = getRepositoryDao().selectByExampleByPager(record, pageBounds);
        if (result.isEmpty()) {
            msg.setResult(EnError.NO_MATCH);
            return msg;
        }

        msg.setData(result);
        return msg;
    }

    @Override
    public Msg<T> fuzzySearch(T record) {
        Msg<T> msg = new Msg<>();
        T result = getRepositoryDao().fuzzySearch(record);
        if (null == result) {
            msg.setResult(EnError.NO_MATCH);
            return msg;
        }

        msg.setData(record);
        return msg;
    }

    @Override
    public Msg<PageList<T>> fuzzySearchByPager(T record, PageBounds pageBounds) {
        Msg<PageList<T>> msg = new Msg<>();
        if (null == pageBounds.getOrders() && pageBounds.getOrders().isEmpty()) {
            List<Order> orders = new ArrayList<>();
            orders.add(new Order("createAt", Order.Direction.DESC, ""));
            pageBounds.setOrders(orders);
        }
        PageList<T> result = getRepositoryDao().fuzzySearchByPager(record, pageBounds);
        if (result.isEmpty()) {
            msg.setResult(EnError.NO_MATCH);
            return msg;
        }

        msg.setData(result);
        return msg;
    }

    @Override
    public Msg<String> uniqueValid(T record) {
        Msg<String> msg = new Msg<>();
        if (StringUtils.isNotEmpty(getPrimaryKeyValue(record))) {
            Long existNumber = getRepositoryDao().countByExample(record);
            if (existNumber > 0) {
                msg.setResult(EnError.CONFLICT);
                return msg;
            }

            return msg;
        }

        /* 主键存在 参数获取的对象主键不一致时 返回错误 */
        String id = getPrimaryKeyValue(record);
        emptyPrimaryKeyValue(record);
        List<T> dbRecord = getRepositoryDao().selectByExample(record, true);
        if (null != dbRecord && dbRecord.size() != 0 && !id.equals(getPrimaryKeyValue(dbRecord.get(0)))) {
            msg.setResult(EnError.CONFLICT);
            return msg;
        }

        return msg;
    }

    private void setCreateInfo(T record) {
        try {
            Method setCreateAt = record.getClass().getMethod("setCreateAt", Date.class);
            setCreateAt.invoke(record, new Date());
            AppUserDetails user = ContextUtil.getCurrentUser();
            Method setCreateBy = record.getClass().getMethod("setCreateBy", String.class);
            setCreateBy.invoke(record, user.getUserId());
        } catch (Exception e) {
            logger.error("set CreateInfo error:{}", "无对应方法");
        }
    }

    public void setUpdateInfo(T record) {
        try {
            Method setUpdateAt = record.getClass().getMethod("setUpdateAt", Date.class);
            setUpdateAt.invoke(record, new Date());
            AppUserDetails user = ContextUtil.getCurrentUser();
            Method setUpdateBy = record.getClass().getMethod("setUpdateBy", String.class);
            setUpdateBy.invoke(record, user.getUserId());
        } catch (Exception e) {
            logger.error("setUpdate Info error:{}", "无对应方法");
        }
    }
}
