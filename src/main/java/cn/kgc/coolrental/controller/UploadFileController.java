package cn.kgc.coolrental.controller;

import cn.kgc.coolrental.config.UploadProperties;
import cn.kgc.coolrental.constant.ResponseStatus;
import cn.kgc.coolrental.dto.ResponseMsg;
import cn.kgc.coolrental.util.StringUtil;
import cn.kgc.coolrental.util.UploadFileQiniu;
import com.qiniu.util.Auth;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

@RestController
@RequestMapping("/yun")
public class UploadFileController {
    @Autowired
    UploadProperties uploadProperties;

    @PostMapping("/uploadFile")
    public ResponseMsg uploadImg(MultipartFile file) {
        // 上传到七牛云
        ResponseMsg msg = new ResponseMsg();
        if (file != null) {
            UploadFileQiniu uploadFile = new UploadFileQiniu(uploadProperties.getQiniu());
            msg.setMsg(uploadFile.uploadFile(file));
            msg.setCode(ResponseStatus.SUCCESS.getCode());
        } else {
            msg.setMsg("上传文件为空");
            msg.setCode(ResponseStatus.FAIL.getCode());
        }
        return msg;
    }

    @GetMapping("/downloadFile")
    public ResponseMsg downloadFile(String fileName) {
        // 解析文件公开文件地址
        ResponseMsg msg = new ResponseMsg();
        if (StringUtil.notEmpty(fileName)) {
            UploadFileQiniu uploadFile = new UploadFileQiniu(uploadProperties.getQiniu());
            String downloadPrivatePath = uploadFile.getDownloadPrivatePath(fileName);
            if (StringUtil.notEmpty(downloadPrivatePath)) {
                msg.setMsg("地址解析成功");
                msg.setCode(ResponseStatus.SUCCESS.getCode());
                msg.setData(downloadPrivatePath);
            } else {
                msg.setMsg("地址解析失败");
                msg.setCode(ResponseStatus.FAIL.getCode());
            }
        } else {
            msg.setMsg("上传文件为空");
            msg.setCode(ResponseStatus.FAIL.getCode());
        }
        return msg;
    }

    @PostMapping("/uploadUserHeadImg")
    public ResponseMsg uploadUserHeadImg(MultipartFile file) {
        // 上传到七牛云
        ResponseMsg msg = new ResponseMsg();
        if (file != null) {
            UploadFileQiniu uploadFile = new UploadFileQiniu(uploadProperties.getQiniu());
            String fileKey = uploadFile.uploadFile(file);
            msg.setMsg("上传成功");
            msg.setData(fileKey);
            msg.setCode(ResponseStatus.SUCCESS.getCode());
        } else {
            msg.setMsg("上传文件为空");
            msg.setCode(ResponseStatus.FAIL.getCode());
        }
        return msg;
    }

    @ApiOperation("加载图片api")
    @GetMapping("/loadImg/{fileKey}")
    public void loadImg(@PathVariable("fileKey") String fileKey, HttpServletResponse response) {
        UploadFileQiniu uploadFile = new UploadFileQiniu(uploadProperties.getQiniu());
        ServletOutputStream outputStream = null;
        try {
            if (StringUtil.notEmpty(fileKey)) {
                response.setHeader("Content-Type", "image;video");
                outputStream = response.getOutputStream();
                outputStream.write(uploadFile.loadFile(fileKey));
            } else {
                PrintWriter writer = response.getWriter();
                writer.print("图片参数错误");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}