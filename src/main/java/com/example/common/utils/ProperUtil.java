package com.example.common.utils;

import org.apache.logging.log4j.util.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Description:读取指定配置文件
 * @Author Leeg
 * @Date:  2017/12/20
 */
public class ProperUtil {

	private static Properties properties;
	private static Logger logger = LoggerFactory.getLogger(ProperUtil.class);

	public static void getProperties(String fileName) {
		try {
			if (properties == null) {
				properties = new Properties();
			}
			ClassLoader cl = Thread.currentThread().getContextClassLoader();
			if (cl == null) {
				cl = ProperUtil.class.getClassLoader();
			}
			InputStream inputStream = cl.getResourceAsStream(fileName);
			try {
				properties.load(inputStream);
				//logger.info("==========>>>  读取配置文件" + fileName + "完成");
			} catch (IOException e) {
				logger.error("==========>>>  读取配置文件" + fileName + "失败", e);
				e.printStackTrace();
			} finally {
				inputStream.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @Description:获取指定配置文件指定key的value
	 * @param  fileName 配置文件
	 * @return key key值
	 * @Author Leeg
	 * @Date:  2017/12/20
	 */
	public static String getProperty(String fileName, String key) {
		if (properties == null) {
			properties = new Properties();
			getProperties(fileName);
		}
		return properties.getProperty(key);
	}

}
