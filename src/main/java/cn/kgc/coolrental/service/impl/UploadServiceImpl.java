package cn.kgc.coolrental.service.impl;

import cn.kgc.coolrental.constant.ResponseStatus;
import cn.kgc.coolrental.dto.ResponseMsg;
import cn.kgc.coolrental.service.UploadService;
import cn.kgc.coolrental.util.StringUtil;
import cn.kgc.coolrental.util.UploadFileQiniu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.io.IOException;

/**
 * @Date: 2022/1/3
 * @Author: SmartRick
 * @Description: 云文件服务
 */
@Service
public class UploadServiceImpl implements UploadService {

    @Autowired
    private UploadFileQiniu uploadFile;

    @Override
    public ResponseMsg upload(MultipartFile file) {
        // 上传到七牛云
        ResponseMsg msg = new ResponseMsg();
        if (file != null) {
            try {
                msg.setMsg(uploadFile.uploadFile(file));
                msg.setCode(ResponseStatus.SUCCESS.getCode());
            } catch (IOException e) {
                e.printStackTrace();
                msg.setMsg("上传失败：" + e.getMessage());
                msg.setCode(ResponseStatus.FAIL.getCode());
            }
        } else {
            msg.setMsg("上传文件为空");
            msg.setCode(ResponseStatus.FAIL.getCode());
        }
        return msg;
    }

    @Override
    public byte[] download(String fileName) {
        return uploadFile.loadFile(fileName);
    }

    // 解析文件公开文件地址
    @Override
    public ResponseMsg obtainFilePath(String key) {
        ResponseMsg msg = new ResponseMsg();
        String downloadPrivatePath = uploadFile.getDownloadPrivatePath(key);
        if (StringUtil.notEmpty(downloadPrivatePath)) {
            msg.setMsg("地址解析成功");
            msg.setCode(ResponseStatus.SUCCESS.getCode());
            msg.setData(downloadPrivatePath);
        } else {
            msg.setMsg("地址解析失败");
            msg.setCode(ResponseStatus.FAIL.getCode());
        }
        return msg;
    }
}
