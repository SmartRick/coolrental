package cn.kgc.coolrental.interceptor;


import cn.kgc.coolrental.annotation.PermAuthority;
import cn.kgc.coolrental.annotation.RoleAuthority;
import cn.kgc.coolrental.constant.ResponseStatus;
import cn.kgc.coolrental.dto.ResponseMsg;
import cn.kgc.coolrental.dto.StorageUserInfo;
import cn.kgc.coolrental.util.JwtUtil;
import cn.kgc.coolrental.util.RedisUtil;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //是否放行的标识
        boolean flag = true;
        if (handler instanceof HandlerMethod) {

            ValueOperations<String, Object> redisValueDao = redisTemplate.opsForValue();
//            HttpSession session = request.getSession();
//            String id = session.getId();
//            System.out.println("sessionId：" + id);

            System.out.println("进入拦截器");

            String requestURI = request.getRequestURI();
            System.out.println("请求地址" + requestURI);


            //将用户的认证信息保存在客户端，每一次请求时加在请求头上
            String token = request.getHeader("token");
            System.out.println(token);
//            boolean verity = JwtUtil.simpleVerity(token);
//            System.out.println("token检测结果："+verity);

            //开始进行认证校验
            if (token == null || "".equals(token) || "null".equals(token)) {
                System.out.println("请求未携带Token");
                request.setAttribute("err", ResponseStatus.NOT_AUTHORITY);
                flag = false;
            } else {
                System.out.println("请求携带Token");
                StorageUserInfo userInfo = (StorageUserInfo) redisValueDao.get(token);
                System.out.println(userInfo);
                if (userInfo == null) {
                    System.out.println("没有登录");
                    request.setAttribute("err", ResponseStatus.NOT_LOGIN);
                    flag = false;
                } else {
                    ResponseMsg responseMsg = JwtUtil.verity(token);
                    if (responseMsg.getResponseStatus() == ResponseStatus.TOKEN_VERIFY_FAIL) {
                        System.out.println("token解析失败");
                        request.setAttribute("err", ResponseStatus.TOKEN_VERIFY_FAIL);
                    } else if (responseMsg.getResponseStatus() == ResponseStatus.AUTHORITY_TIME_OUT) {
                        System.out.println("token过期时间到了");
                        System.out.println("将过期的key删除：" + redisTemplate.delete(token));
                        request.setAttribute("err", ResponseStatus.AUTHORITY_TIME_OUT);
                    } else if (responseMsg.getResponseStatus() == ResponseStatus.SUCCESS) {
                        System.out.println("开始进行权限校验");
                        //token时间还没到
                        //开始进行权限校验
                        System.out.println("请求到达Controller方法");
                        PermAuthority permAuthority = ((HandlerMethod) handler).getMethodAnnotation(PermAuthority.class);
                        RoleAuthority roleAuthority = ((HandlerMethod) handler).getMethodAnnotation(RoleAuthority.class);
                        if (roleAuthority != null) {
                            System.out.println("角色检测");
                            if (!roleCheck(roleAuthority, userInfo.getRoles())) {
                                request.setAttribute("err", ResponseStatus.NO_ROLE);
                                flag = false;
                            }
                        }
                        if (permAuthority != null) {
                            System.out.println("权限检测");
                            if (!permCheck(permAuthority, userInfo.getPerms())) {
                                request.setAttribute("err", ResponseStatus.NO_PERM);
                                flag = false;
                            }
                        }
                    }
                }
            }

            //如果不放行，就跳转
            if (!flag) request.getRequestDispatcher("/tokenFail").forward(request, response);
            return flag;
        }

        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }


    private boolean permCheck(PermAuthority permAuthority, Set<String> perms) {
        String[] requirePerms = permAuthority.value();
        System.out.println("方法需要：" + Arrays.toString(requirePerms));
        System.out.println("当前用户权限：" + perms);
        for (String requirePerm : requirePerms) {
            boolean require = false;
            for (String perm : perms) {
                if (requirePerm.equals(perm)) {
                    //具备相应权限
                    require = true;
                    break;
                }
            }
            if (!require) {
                System.out.println("操作需要" + requirePerm + "权限，但是请求用户不具备");
                return false;
            }
        }
        return true;
    }

    private boolean roleCheck(RoleAuthority roleAuthorities, Set<String> roles) {
        String[] requireRoles = roleAuthorities.value();
        System.out.println("方法需要：" + Arrays.toString(requireRoles));
        System.out.println("当前用户权限：" + roles);
        for (String requireRole : requireRoles) {
            boolean require = false;
            for (String role : roles) {
                if (requireRole.equals(role)) {
                    //具备相应角色
                    require = true;
                    break;
                }
            }
            if (!require) {
                System.out.println("操作需要" + requireRole + "权限，但是请求用户不具备");
                return false;
            }
        }
        return true;
    }

}
