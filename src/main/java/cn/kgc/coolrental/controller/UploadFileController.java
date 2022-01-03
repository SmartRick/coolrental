package cn.kgc.coolrental.controller;

import cn.kgc.coolrental.config.UploadProperties;
import cn.kgc.coolrental.constant.ResponseStatus;
import cn.kgc.coolrental.dto.ResponseMsg;
import cn.kgc.coolrental.service.UploadService;
import cn.kgc.coolrental.util.StringUtil;
import cn.kgc.coolrental.util.UploadFileQiniu;
import com.qiniu.util.Auth;
import com.qiniu.util.IOUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

@RestController
@RequestMapping("/yun")
public class UploadFileController {
    @Autowired
    private UploadService uploadService;

    @PostMapping("/uploadFile")
    public ResponseMsg uploadImg(MultipartFile file) {
        return uploadService.upload(file);
    }

    @GetMapping("/downloadFile")
    @Validated
    public ResponseMsg downloadFile(@NotEmpty String fileName) {
        return uploadService.obtainFilePath(fileName);
    }

    @PostMapping("/uploadUserHeadImg")
    public ResponseMsg uploadUserHeadImg(MultipartFile file) {
        // 上传到七牛云
        return uploadService.upload(file);
    }

    @ApiOperation("加载图片api")
    @GetMapping("/loadImg/{fileKey}")
    public void loadImg(@PathVariable("fileKey") String fileKey, HttpServletResponse response) {
        ServletOutputStream outputStream = null;
        try {
            if (StringUtil.notEmpty(fileKey)) {
                response.setHeader("Content-Type", "image;video");
                outputStream = response.getOutputStream();
                outputStream.write(uploadService.download(fileKey));
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