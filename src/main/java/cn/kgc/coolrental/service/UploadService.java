package cn.kgc.coolrental.service;

import cn.kgc.coolrental.dto.ResponseMsg;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Date: 2022/1/3
 * @Author: SmartRick
 * @Description: 上传服务
 */
public interface UploadService {
    ResponseMsg upload(MultipartFile file);
    ResponseMsg obtainFilePath(String key);
    byte[] download(String fileName);
}
