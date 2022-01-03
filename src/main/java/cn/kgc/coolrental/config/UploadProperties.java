package cn.kgc.coolrental.config;

/**
 * @author: SmartRick
 * @Date: 2019/12/16
 * 七牛云上传配置
 */

import lombok.Data;
import org.apache.tomcat.jni.Local;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;


@Data
@ConfigurationProperties(prefix = "upload")
public class UploadProperties {
    /**
     * 域名
     */
    private String domain;

    /**
     * 从下面这个地址中获取accessKey和secretKey
     * https://portal.qiniu.com/user/key
     */
    private String accessKey;

    private String secretKey;

    /**
     * 存储空间名
     */
    private String bucket;
}