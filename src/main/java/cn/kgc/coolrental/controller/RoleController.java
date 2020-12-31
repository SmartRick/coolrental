package cn.kgc.coolrental.controller;

import cn.kgc.coolrental.constant.ResponseStatus;
import cn.kgc.coolrental.dto.ResponseMsg;
import cn.kgc.coolrental.dto.RoleDto;
import cn.kgc.coolrental.dto.UserDto;
import cn.kgc.coolrental.entity.Perm;
import cn.kgc.coolrental.entity.Role;
import cn.kgc.coolrental.entity.User;
import cn.kgc.coolrental.service.RoleService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Api(tags = "角色管理API")
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @ApiOperation("查询所有角色")
    @GetMapping("/queryAll")
    public ResponseMsg queryAll() {
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setData(roleService.queryAll());
        return responseMsg;
    }

    @ApiOperation("分页查询所有角色")
    @GetMapping("/queryAllPage")
    public ResponseMsg queryAllPage(@RequestParam(required = false, defaultValue = "1") Integer index,
                                    @RequestParam(required = false, defaultValue = "10") Integer size) {
        ResponseMsg responseMsg = new ResponseMsg();
        Page<Role> rolePage = new Page<>();
        rolePage.setSize(size);
        rolePage.setCurrent(index);
        responseMsg.setData(roleService.queryAllPage(rolePage));
        return responseMsg;
    }

    @ApiOperation("查询角色的权限信息")
    @GetMapping("/queryRolePerms/{id}")
    public ResponseMsg queryRolePerms(@PathVariable("id") Integer id) {
        ResponseMsg msg = new ResponseMsg();
        msg.setData(roleService.queryRolePerms(id));
        return msg;
    }

    @ApiOperation("根据角色名称模糊查询")
    @PostMapping("/queryWord")
    public ResponseMsg queryWord(String word,
                                 @RequestParam(required = false, defaultValue = "1") Integer index,
                                 @RequestParam(required = false, defaultValue = "10") Integer size) {
        ResponseMsg msg = new ResponseMsg();
        Page<Role> rolePage = new Page<>();
        rolePage.setSize(size);
        rolePage.setCurrent(index);
        msg.setData(roleService.queryWord(word, rolePage));
        return msg;
    }

    @ApiOperation("根据Id查询角色")
    @GetMapping("/queryById")
    public ResponseMsg queryById(
            @ApiParam(name = "id", value = "角色Id")
            @RequestBody Integer id) {
        ResponseMsg msg = new ResponseMsg();
        Role role = roleService.queryById(id);
        if (role != null) {
            msg.setCode(cn.kgc.coolrental.constant.ResponseStatus.FAIL.getCode());
        } else {
            msg.setCode(ResponseStatus.SUCCESS.getCode());
            msg.setData(role);
        }
        return msg;
    }


    @ApiOperation("根据ID删除角色")
    @DeleteMapping("/remove/{id}")
    public ResponseMsg remove(
            @ApiParam(name = "id", value = "角色Id")
            @PathVariable("id") Integer id) {
        ResponseMsg msg = new ResponseMsg();
        if (roleService.remove(id)) {
            msg.setCode(ResponseStatus.SUCCESS.getCode());
            msg.setMsg("删除角色成功");
        } else {
            msg.setCode(ResponseStatus.FAIL.getCode());
            msg.setMsg("删除角色失败");
        }
        return msg;
    }


    @ApiOperation("添加角色信息")
    @PostMapping("/add")
    public ResponseMsg add(
            @ApiParam(name = "role", value = "角色传输对象", required = true)
            @RequestBody RoleDto roleDto) {
        ResponseMsg msg = new ResponseMsg();
        if (roleDto != null) {
            if (roleService.add(roleDto)) {
                msg.setCode(ResponseStatus.SUCCESS.getCode());
                msg.setMsg("添加角色成功");
                msg.setData(roleDto.getRole());
            } else {
                msg.setCode(ResponseStatus.FAIL.getCode());
                msg.setMsg("添加角色失败");
            }
        } else {
            msg.setCode(ResponseStatus.FAIL.getCode());
            msg.setMsg("提交数据异常");
        }
        return msg;
    }

    @ApiOperation("改变角色状态")
    @PutMapping("/statusop")
    public ResponseMsg statusop(
            @ApiParam(name = "role", value = "角色对象", required = true)
            @RequestBody Role role) {
        ResponseMsg msg = new ResponseMsg();
        if (roleService.modify(role)) {
            msg.setCode(ResponseStatus.SUCCESS.getCode());
            msg.setMsg("改变角色状态成功");
        } else {
            msg.setCode(ResponseStatus.FAIL.getCode());
            msg.setMsg("改变角色状态失败");
        }
        return msg;
    }

    @ApiOperation("修改角色接口")
    @PutMapping("/modify")
    public ResponseMsg modify(
            @ApiParam(name = "role", value = "角色对象", required = true)
            @RequestBody RoleDto roleDto) {
        System.out.println(roleDto);
        ResponseMsg msg = new ResponseMsg();
        if (roleDto.getRole() != null) {
            if (roleService.modify(roleDto)) {
                msg.setCode(ResponseStatus.SUCCESS.getCode());
                msg.setMsg("修改角色成功");
            } else {
                msg.setCode(ResponseStatus.FAIL.getCode());
                msg.setMsg("修改角色失败");
            }
        } else {
            msg.setCode(ResponseStatus.FAIL.getCode());
            msg.setMsg("提交异常");
        }
        return msg;
    }
}
