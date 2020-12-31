package cn.kgc.coolrental.util;

import cn.kgc.coolrental.config.UploadProperties;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * @author Johnson
 * @date 2019/12/14/ 17:20:16
 * 上传文件到七牛云
 */
public class UploadFileQiniu {

    private UploadProperties.Qiniu properties;

    //构造一个带指定Region对象的配置类
    private Configuration cfg = new Configuration(Region.region2());

    private UploadManager uploadManager = new UploadManager(cfg);

    public UploadFileQiniu(UploadProperties.Qiniu properties) {
        this.properties = properties;
    }

    public String uploadFile(MultipartFile file) {
        String uploadToken = getAuth().uploadToken(properties.getBucket());
        try {
            String originalFilename = file.getOriginalFilename();
            // 文件后缀
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            String fileKey = UUID.randomUUID().toString() + suffix;
            Response response = uploadManager.put(file.getInputStream(), fileKey, uploadToken,
                    getPutPolicy(), null);
            System.out.println(response.bodyString());
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);

            System.out.println(putRet.key);
            System.out.println(putRet.hash);
            return fileKey;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error";
    }

    public String getDownloadPrivatePath(String fileName) {
        try {
            String finalUrl = getPrivateFile(fileName);
            return finalUrl;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 定义七牛云上传的相关策略
     */
    public StringMap getPutPolicy() {
        StringMap stringMap = new StringMap();
        stringMap.put("returnBody",
                "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"width\":$(imageInfo.width), \"height\":${imageInfo.height}}");
        return stringMap;
    }

    /**
     * 删除七牛云上的相关文件
     *
     * @param key
     * @return
     * @throws QiniuException
     */
    public String delete(String key) throws QiniuException {
        BucketManager bucketManager = new BucketManager(getAuth(), cfg);
        Response response = bucketManager.delete(properties.getBucket(), key);
        int retry = 0;
        //判断是否需要 重试 删除 且重试次数为3
        while (response.needRetry() && retry++ < 3) {
            System.out.println("正在尝试删除");
            response = bucketManager.delete(properties.getBucket(), key);
        }
        return response.statusCode == 200 ? "删除成功!" : "删除失败!";
    }

    /**
     * 获取私有空间文件
     *
     * @param fileKey 要下载的文件名
     * @return
     */
    public String getPrivateFile(String fileKey) throws Exception {
        String encodedFileName = URLEncoder.encode(fileKey, "utf-8").replace("+", "%20");
        String publicUrl = String.format("%s/%s", properties.getDomain(), encodedFileName);
//        //1小时，可以自定义链接过期时间
//        long expireInSeconds = 3600;
        String privateUrl = getAuth().privateDownloadUrl(publicUrl);
        return privateUrl;
    }


    /**
     * 通过发送http get 请求获取文件资源
     *
     * @return
     */
    public byte[] loadFile(String fileKey) {
        String downloadUrl = getDownloadPrivatePath(fileKey);
        OkHttpClient client = new OkHttpClient();
        System.out.println(downloadUrl);
        Request req = new Request.Builder().url(downloadUrl).build();
        okhttp3.Response resp = null;
        try {
            resp = client.newCall(req).execute();
            System.out.println(resp.isSuccessful());
            if (resp.isSuccessful()) {
                ResponseBody body = resp.body();
                InputStream is = body.byteStream();
                return inputString2ByteArray(is);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Unexpected code " + resp);
        }
        return null;
    }


    public byte[] inputString2ByteArray(InputStream inputStream) {
        ByteArrayOutputStream byteArrayOutputStream = null;
        byte[] buffer = new byte[1024];
        int len = -1;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            while ((len = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, len);
            }
            byteArrayOutputStream.flush();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (byteArrayOutputStream != null) {
                try {
                    byteArrayOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;

    }


    private Auth getAuth() {
        return Auth.create(properties.getAccessKey(), properties.getSecretKey());
    }

    ;
}