package cn.kgc.coolrental.controller;

import cn.kgc.coolrental.constant.ResponseStatus;
import cn.kgc.coolrental.dto.ResponseMsg;
import cn.kgc.coolrental.dto.StorageUserInfo;
import cn.kgc.coolrental.dto.UserDto;
import cn.kgc.coolrental.entity.User;
import cn.kgc.coolrental.service.UserService;
import cn.kgc.coolrental.util.JwtUtil;
import cn.kgc.coolrental.util.StringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.*;
import org.apache.shiro.web.filter.mgt.DefaultFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

import static org.apache.shiro.web.filter.mgt.DefaultFilter.user;

@Api(tags = "用户管理API")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public ResponseMsg login(
            @ApiParam(name = "user", value = "用户对象，提交用户名或昵称以及手机号")
            @RequestBody User user, HttpServletRequest request, HttpServletResponse response) {
        ResponseMsg responseMsg = new ResponseMsg();
        System.out.println(user);
        User login = userService.login(user);
        if (login != null) {
            String token = request.getHeader("token");
            if (StringUtil.notEmpty(token) && redisTemplate.hasKey(token)) {
                //缓存中存在改客户端的key
                if (JwtUtil.simpleVerity(token)) {
                    // key仍然有效
                    responseMsg.setCode(ResponseStatus.SUCCESS.getCode());
                    responseMsg.setMsg("你以在该客户端登录过，请勿重复登录");
                } else {
                    //key过期或者失效
                    //将过期的key删除并重新生成
                    redisTemplate.delete(token);
                }
            } else {
                ValueOperations<String, Object> valueDao = redisTemplate.opsForValue();

                StorageUserInfo storageUserInfo = new StorageUserInfo();
                storageUserInfo.setUser(login);
                storageUserInfo.setPerms(null);
                storageUserInfo.setRoles(null);

                String sign = JwtUtil.sign(storageUserInfo);
                //将token存储到redis缓存
                valueDao.set(sign, storageUserInfo);
                //将token在响应头返回回去
                response.setHeader("token", sign);
                responseMsg.setCode(ResponseStatus.SUCCESS.getCode());
                responseMsg.setMsg("登录成功");
                responseMsg.setData(login);
            }
        } else {
            responseMsg.setCode(ResponseStatus.FAIL.getCode());
        }
        return responseMsg;
    }


    @ApiOperation("查询用户的角色信息")
    @GetMapping("/queryUserRoles/{id}")
    public ResponseMsg queryUserRoles(@PathVariable("id") Integer id) {
        ResponseMsg msg = new ResponseMsg();
        msg.setData(userService.queryUserRoles(id));
        return msg;
    }

    @ApiOperation("分页查询所有用户")
    @GetMapping("/queryPage")
    public ResponseMsg queryPage(@RequestParam(required = false, defaultValue = "1") Integer current,
                                 @RequestParam(required = false, defaultValue = "10") Integer size) {
        ResponseMsg msg = new ResponseMsg();
        Page<User> userPage = new Page<User>();
        userPage.setSize(size);
        userPage.setCurrent(current);
        msg.setData(userService.queryAllPage(userPage));
        return msg;
    }

    @ApiOperation("分页模糊查询用户关键字")
    @PostMapping("/queryWord")
    public ResponseMsg queryWord(@RequestParam(value = "roleId", required = false) Integer roleId,
                                 @RequestParam(required = false) String word,
                                 @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                                 @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        ResponseMsg msg = new ResponseMsg();
        Page<User> userPage = new Page<User>();
        userPage.setCurrent(pageIndex);
        userPage.setSize(pageSize);
        msg.setData(userService.queryWord(roleId, word, userPage));
        return msg;
    }

    @ApiOperation("根据Id查询用户对象")
    @GetMapping("/queryById")
    public ResponseMsg queryById(
            @ApiParam(name = "id", value = "用户Id")
            @RequestBody Integer id) {
        ResponseMsg msg = new ResponseMsg();
        User user = userService.queryById(id);
        if (user != null) {
            msg.setCode(ResponseStatus.FAIL.getCode());
        } else {
            msg.setCode(ResponseStatus.SUCCESS.getCode());
            msg.setData(user);
        }
        return msg;
    }

    @ApiOperation("用户注册接口")
    @PostMapping("/register")
    public ResponseMsg register(
            @ApiParam(name = "user", value = "用户对象", required = true)
            @RequestBody UserDto userDto) {
        User user = userDto.getUser();
        ResponseMsg msg = new ResponseMsg();
        if (user != null) {
            user.setRegisterDate(new Date());
            if (!userService.hashName(user.getNick())) {
                if (userService.add(userDto)) {
                    msg.setCode(ResponseStatus.SUCCESS.getCode());
                    msg.setMsg("注册成功");
                    msg.setData(user);
                } else {
                    msg.setCode(ResponseStatus.FAIL.getCode());
                    msg.setMsg("注册失败");
                }
            } else {
                msg.setCode(ResponseStatus.FAIL.getCode());
                msg.setMsg("昵称重复");
            }
        } else {
            msg.setCode(ResponseStatus.FAIL.getCode());
            msg.setMsg("提交数据异常");
        }

        return msg;
    }

    @ApiOperation("删除用户接口")
    @DeleteMapping("/remove/{id}")
    public ResponseMsg remove(
            @ApiParam(name = "id", value = "用户的Id", required = true)
            @PathVariable("id") Integer id) {
        ResponseMsg msg = new ResponseMsg();
        if (userService.remove(id)) {
            msg.setCode(ResponseStatus.SUCCESS.getCode());
            msg.setMsg("删除用户成功");
        } else {
            msg.setCode(ResponseStatus.FAIL.getCode());
            msg.setMsg("删除用户失败");
        }
        return msg;
    }

    @ApiOperation("修改用户接口")
    @PutMapping("/modify")
    public ResponseMsg modify(
            @ApiParam(name = "user", value = "用户对象", required = true)
            @RequestBody UserDto userDto) {
        System.out.println(userDto);
        ResponseMsg msg = new ResponseMsg();
        if (userService.modify(userDto)) {
            msg.setCode(ResponseStatus.SUCCESS.getCode());
            msg.setMsg("修改用户成功");
        } else {
            msg.setCode(ResponseStatus.FAIL.getCode());
            msg.setMsg("修改用户失败");
        }
        return msg;
    }

    @ApiOperation("改变用户状态")
    @PutMapping("/statusop")
    public ResponseMsg statusop(
            @ApiParam(name = "user", value = "用户对象", required = true)
            @RequestBody User user) {
        ResponseMsg msg = new ResponseMsg();
        if (userService.modify(user)) {
            msg.setCode(ResponseStatus.SUCCESS.getCode());
            msg.setMsg("改变用户状态成功");
        } else {
            msg.setCode(ResponseStatus.FAIL.getCode());
            msg.setMsg("改变用户状态失败");
        }
        return msg;
    }

    @ApiOperation("退出登录")
    @GetMapping("/logout")
    public ResponseMsg logout(HttpServletRequest request) {
        ResponseMsg msg = new ResponseMsg();
        String token = request.getHeader("token");
        if (StringUtil.notEmpty(token) && redisTemplate.hasKey(token)) {
            if (redisTemplate.delete(token)) {
                msg.setCode(ResponseStatus.SUCCESS.getCode());
                msg.setMsg("退出登录成功");
            }
        } else {
            msg.setCode(ResponseStatus.FAIL.getCode());
            msg.setMsg("未登录");
        }
        return msg;
    }
}
