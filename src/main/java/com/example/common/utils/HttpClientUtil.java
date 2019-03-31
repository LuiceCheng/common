package com.example.common.utils;

import com.alibaba.fastjson.JSON;

import javax.ws.rs.core.Form;
import java.lang.reflect.Field;
import java.util.List;

public class HttpClientUtil {
    public static Form objectToForm(Object obj) {
        Boolean isListAsString = false;
        Form form = new Form();
        Field[] fields = obj.getClass().getDeclaredFields();
        fieldToForm(obj, form, fields, isListAsString);

        return form;
    }

    public static Form objectToForm(Object obj, Boolean isListAsString) {
        Form form = new Form();
        Field[] fields = obj.getClass().getDeclaredFields();
        fieldToForm(obj, form, fields, isListAsString);

        return form;
    }

    private static void fieldToForm(Object obj, Form form, Field[] fields, Boolean isListAsString) {
        for (Field field : fields) {
            if ("serialVersionUID".equals(field.getName())) {
                continue;
            }
            // list 处理
            if (field.getType().getTypeName().contains("List")) {
                if (isListAsString) {
                    List<?> getter = (List<?>) JavaBeansUtil.getter(obj, field.getName());
                    if (null == getter) {
                        form.param(field.getName(), "");
                    } else {
                        form.param(field.getName(), JSON.toJSONString(getter));
                    }
                } else {
                    doListParam(form, obj, field);
                }
            } else {
                setFormParam(form, obj, field.getName(), field);
            }
        }
    }

    private static void setFormParam(Form form, Object obj, String paramName, Field field) {
        Class<?> genericType = (Class<?>) field.getGenericType();
        if (String.class.isAssignableFrom(genericType)) {
            form.param(paramName, (String) JavaBeansUtil.getter(obj, field.getName()));
        } else if (Number.class.isAssignableFrom(genericType)) {
            form.param(paramName, String.valueOf(JavaBeansUtil.getter(obj, field.getName())));
        } else if (Boolean.class.isAssignableFrom(genericType)) {
            form.param(paramName, Boolean.toString((Boolean) JavaBeansUtil.getter(obj, field.getName())));
        }
    }

    private static void listToForm(String paramName, Object obj, Form form, Field[] fields) {
        for (Field field : fields) {
            if ("serialVersionUID".equals(field.getName())) {
                continue;
            }
            // list 处理
            if (field.getType().getTypeName().contains("List")) {
                doListParam(form, obj, field);
            } else {
                setFormParam(form, obj, paramName + "." + field.getName(), field);
            }
        }
    }

    private static void listObjectToForm(String paramName, Object obj, Form form) {
        Field[] fields = obj.getClass().getDeclaredFields();
        listToForm(paramName, obj, form, fields);
    }

    private static void doListParam(Form form, Object obj, Field field) {
        List<?> getter = (List<?>) JavaBeansUtil.getter(obj, field.getName());
        if (null == getter) {
            return;
        }
        for (int i = 0; i < getter.size(); i++) {
            Object listObj = getter.get(0);
            Class<?> aClass = listObj.getClass();
            if ("java.lang.String".equals(aClass.getName())) {
                form.param(field.getName() + "[" + Integer.toString(i) + "]", String.valueOf(getter.get(0)));
            } else {
                listObjectToForm(field.getName() + "[" + Integer.toString(i) + "]", listObj, form);
            }

        }
    }
}
