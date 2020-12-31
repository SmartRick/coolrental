package cn.kgc.coolrental.util;

import cn.kgc.coolrental.constant.ResponseStatus;
import cn.kgc.coolrental.dto.ResponseMsg;
import cn.kgc.coolrental.dto.StorageUserInfo;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.sun.istack.internal.NotNull;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class JwtUtil {

    /**
     * 过期时间为一天
     * TODO 正式上线更换为15分钟
     */
    private static final long EXPIRE_TIME = 24 * 60 * 60 * 1000;

    /**
     * token私钥
     */
    private static final String TOKEN_SECRET = "coolrental_coolrental_@coolrental";

    /**
     * 生成签名,15分钟后过期
     * StorageUserInfo 需要转换为JWT存储的用户验证数据
     *
     * @return
     */
    public static String sign(@NotNull StorageUserInfo storageUserInfo) {
        //过期时间
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        //私钥及加密算法
        Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
        //设置头信息
        HashMap<String, Object> header = new HashMap<>(2);
        header.put("typ", "JWT");
        header.put("alg", "HS256");
        //附带用户信息生成签名
        String jsonString = JSONObject.toJSONString(storageUserInfo);
        return JWT.create().withHeader(header).withClaim("storageUserInfo", jsonString).withExpiresAt(date).sign(algorithm);
    }


    public static boolean simpleVerity(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            //判断token时效是否到期
            Date expiresAt = jwt.getExpiresAt();
            if (System.currentTimeMillis() - expiresAt.getTime() < 0) {
                return true;
            }
        } catch (IllegalArgumentException e) {
            return false;
        } catch (JWTVerificationException e) {
            return false;
        }
        return false;
    }


    public static ResponseMsg verity(String token) {
        ResponseMsg responseMsg = new ResponseMsg();
        StorageUserInfo userInfo = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            Date expiresAt = jwt.getExpiresAt();
            if (System.currentTimeMillis() - expiresAt.getTime() < 0) {
                System.out.println("==========================");
                Claim claim = jwt.getClaim("storageUserInfo");
                if (claim != null) {
                    System.out.println(claim.asString());
                    userInfo = (StorageUserInfo) JSONObject.parse(claim.asString());
                    responseMsg.setData(userInfo);//
                    responseMsg.setResponseStatus(ResponseStatus.SUCCESS);
                } else {
                    responseMsg.setResponseStatus(ResponseStatus.TOKEN_VERIFY_FAIL);
                }
                System.out.println("==========================");
            } else {
                responseMsg.setResponseStatus(ResponseStatus.AUTHORITY_TIME_OUT);
            }
        } catch (IllegalArgumentException e) {
            responseMsg.setResponseStatus(ResponseStatus.TOKEN_VERIFY_FAIL);
        } catch (JWTVerificationException e) {
            responseMsg.setResponseStatus(ResponseStatus.TOKEN_VERIFY_FAIL);
        }
        return responseMsg;
    }


}