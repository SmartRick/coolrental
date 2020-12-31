package cn.kgc.coolrental.controller;

import cn.kgc.coolrental.constant.ResponseStatus;
import cn.kgc.coolrental.dto.ResponseMsg;
import cn.kgc.coolrental.util.StringUtil;
import com.google.code.kaptcha.Producer;
import io.swagger.annotations.Api;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
@Api("验证码API")
@Controller
public class KaptchaContoroller {
    @Autowired
    private Producer kaptchaProducer;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private Logger logger = LoggerFactory.getLogger(KaptchaContoroller.class);

    @RequestMapping(path = "/tokenCode", method = RequestMethod.GET)
    public void getKaptcha(Long timestamp, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        if (redisTemplate.hasKey(request.getRemoteAddr())) {
            //将之前的删除
            redisTemplate.delete(request.getRemoteAddr());
        }
        // 生成验证码
        String tokenText = kaptchaProducer.createText();
        ValueOperations<String, Object> valueDao = redisTemplate.opsForValue();
        valueDao.set(request.getRemoteAddr(), tokenText);
        BufferedImage image = kaptchaProducer.createImage(tokenText);

        // 将突图片输出给浏览器
        response.setContentType("image/png");
        try {
            OutputStream os = response.getOutputStream();
            ImageIO.write(image, "png", os);
        } catch (IOException e) {
            logger.error("响应验证码失败:" + e.getMessage());
        }
    }

    @ResponseBody
    @PostMapping(path = "/checkTokenCode")
    public ResponseMsg checkKaptcha(String tokenCode, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        ResponseMsg msg = new ResponseMsg();
        String remoteAddr = request.getRemoteAddr();
        if (StringUtil.notEmpty(tokenCode)) {
            if (redisTemplate.hasKey(remoteAddr)) {
                if (((String) redisTemplate.opsForValue().get(remoteAddr)).toUpperCase().equals(tokenCode.toUpperCase())) {
                    msg.setMsg("验证码输入正确");
                    msg.setCode(ResponseStatus.SUCCESS.getCode());
                } else {
                    msg.setMsg("验证码错误，请重新输入");
                    msg.setCode(ResponseStatus.FAIL.getCode());
                }
                redisTemplate.delete(remoteAddr);
            } else {
                msg.setCode(ResponseStatus.FAIL.getCode());
                msg.setMsg("该客户端未请求过验证码");
            }
        } else {
            msg.setCode(ResponseStatus.FAIL.getCode());
            msg.setMsg("请填写验证码");
        }

        return msg;
    }

}
