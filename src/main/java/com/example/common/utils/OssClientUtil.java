package com.example.common.utils;

import com.aliyun.oss.OSSClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Description:OSS获取文件下载签名链接
 * @Author
 * @Date: 2017/12/20
 */
@Component
public class OssClientUtil {

	private static String endpoint;
	private static String accessKeyId;
	private static String secretAccessKey;
	private static String bucketName;
	private static OSSClient ossClient;

	/**
	 * 拼接OSS文件签名路径
	 * @param fileSaveName
	 * @return
	 */
	public static String getOssSingRealUrl(String fileSaveName) {
		if (StringUtils.isEmpty(fileSaveName)) {
			return "";
		}
		if (ossClient == null) {
			//读取配置文件参数值
			String fileName = "config.properties";
			endpoint = ProperUtil.getProperty(fileName, "aliyun.oss.endpoint");
			accessKeyId = ProperUtil.getProperty(fileName, "aliyun.oss.accessKeyId");
			secretAccessKey = ProperUtil.getProperty(fileName, "aliyun.oss.secretAccessKey");
			bucketName = ProperUtil.getProperty(fileName, "aliyun.oss.bucketName");
			ossClient = new OSSClient(endpoint, accessKeyId, secretAccessKey);
		}
		Date expiration = new Date(new Date().getTime() + 100000000 * 1000); //生成失效时间
		return ossClient.generatePresignedUrl(bucketName, fileSaveName, expiration).toString();
	}

}
