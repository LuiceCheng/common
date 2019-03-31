package com.example.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public class JavaBeansUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JavaBeansUtil.class);

    private JavaBeansUtil() {
        super();
    }

    /**
     * @param obj       操作的对象
     * @param fieldName 操作的属性值
     * @param value     设置的值
     */
    public static void setter(Object obj, String fieldName, Object value) {
        try {
            Method getMethod = obj.getClass().getMethod(getSetterMethodName(fieldName), value.getClass());
            if (null != getMethod) {
                getMethod.invoke(obj, value);
            }
        } catch (Exception e) {
            LOGGER.error("获取{} set方法失败", fieldName, e);
        }
    }

    /**
     * getter方法
     *
     * @param obj       对象
     * @param fieldName 字段
     */
    public static Object getter(Object obj, String fieldName) {
        try {
            Method getMethod = obj.getClass().getMethod(getGetterMethodName(fieldName));
            if (null != getMethod) {
                return getMethod.invoke(obj);
            } else {
                return obj.getClass().getDeclaredField(fieldName).get(obj);
            }

        } catch (Exception e) {
            LOGGER.error("获取{} get方法失败", fieldName, e);
        }
        return null;
    }

    public static String getGetterMethodName(String property) {
        StringBuilder sb = new StringBuilder();

        sb.append(property);
        if (Character.isLowerCase(sb.charAt(0))) {
            if (sb.length() == 1 || !Character.isUpperCase(sb.charAt(1))) {
                sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
            }
        }

        sb.insert(0, "get"); //$NON-NLS-1$

        return sb.toString();
    }


    public static String getSetterMethodName(String property) {
        StringBuilder sb = new StringBuilder();

        sb.append(property);
        if (Character.isLowerCase(sb.charAt(0))) {
            if (sb.length() == 1 || !Character.isUpperCase(sb.charAt(1))) {
                sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
            }
        }

        sb.insert(0, "set"); //$NON-NLS-1$

        return sb.toString();
    }

    public static String getCamelCaseString(String inputString,
                                            boolean firstCharacterUppercase) {
        StringBuilder sb = new StringBuilder();

        boolean nextUpperCase = false;
        for (int i = 0; i < inputString.length(); i++) {
            char c = inputString.charAt(i);

            switch (c) {
                case '_':
                case '-':
                case '@':
                case '$':
                case '#':
                case ' ':
                case '/':
                case '&':
                    if (sb.length() > 0) {
                        nextUpperCase = true;
                    }
                    break;

                default:
                    if (nextUpperCase) {
                        sb.append(Character.toUpperCase(c));
                        nextUpperCase = false;
                    } else {
                        sb.append(Character.toLowerCase(c));
                    }
                    break;
            }
        }

        if (firstCharacterUppercase) {
            sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        }

        return sb.toString();
    }

}
