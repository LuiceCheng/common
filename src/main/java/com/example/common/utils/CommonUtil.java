package com.example.common.utils;

import com.example.common.entity.Msg;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collection;

/**
 * description:
 *
 * @author:cxs
 * @date: 2019/12/19 18:24
 */
public class CommonUtil {

    public static boolean collectionIsEmpty(Object object) {
        if (object instanceof Collection) {
            return CollectionUtils.isEmpty((Collection) object);
        } else if (object instanceof Msg) {
            Object data = ((Msg) object).getData();
            if (data instanceof Collection) {
                return CollectionUtils.isEmpty((Collection) data);
            }
        }
        return false;
    }
}
