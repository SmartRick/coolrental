package cn.kgc.coolrental.controller;

import cn.kgc.coolrental.constant.ResponseStatus;
import cn.kgc.coolrental.dto.ElementUiTree;
import cn.kgc.coolrental.dto.ResponseMsg;
import cn.kgc.coolrental.entity.Perm;
import cn.kgc.coolrental.entity.Post;
import cn.kgc.coolrental.service.PermService;
import cn.kgc.coolrental.service.PostService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "权限管理API")
@RestController
@RequestMapping("/perm")
public class PermController {
    @Autowired
    private PermService permService;

    @ApiOperation("查询所有权限信息树")
    @GetMapping("/queryAllTree")
    public ResponseMsg queryAllTree() {
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setData(permService.queryAllTree());
        return responseMsg;
    }

    @ApiOperation("查询所有权限信息")
    @GetMapping("/queryAll")
    public ResponseMsg queryAll() {
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setData(permService.queryAll());
        return responseMsg;
    }

    @ApiOperation("查询某个权限的子权限")
    @GetMapping("/queryChildByPid/{pid}")
    public ResponseMsg queryChildByPid(@ApiParam(name = "pid", value = "权限Id", required = true)
                                       @PathVariable("pid") Integer pid) {
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setData(permService.queryChildByPid(pid));
        return responseMsg;
    }

    @ApiOperation("根据Id查询具体权限信息")
    @GetMapping("/queryById/{id}")
    public ResponseMsg queryById(@ApiParam(name = "id", value = "权限Id", required = true)
                                 @PathVariable("id") Integer id) {
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setData(permService.queryById(id));
        return responseMsg;
    }

    @ApiOperation("添加权限信息")
    @PostMapping("/add")
    public ResponseMsg add(@ApiParam(name = "perm", value = "权限对象", required = true) Perm perm) {
        ResponseMsg msg = new ResponseMsg();
        if (permService.add(perm)) {
            msg.setCode(ResponseStatus.SUCCESS.getCode());
            msg.setMsg("添加权限信息成功");
            ElementUiTree elementUiTree = new ElementUiTree();
            elementUiTree.setLabel(perm.getName());
            elementUiTree.setId(perm.getId());
            elementUiTree.setDisable(perm.getStatus()==1);
            msg.setData(elementUiTree);
        } else {
            msg.setCode(ResponseStatus.FAIL.getCode());
            msg.setMsg("添加权限信息失败");
        }
        return msg;
    }

    @ApiOperation("修改权限信息")
    @PutMapping("/modify")
    public ResponseMsg modify(@ApiParam(name = "perm", value = "权限对象", required = true) Perm perm) {
        ResponseMsg msg = new ResponseMsg();
        System.out.println(perm);
        if (perm.getId() != null) {
            if (permService.modify(perm)) {
                msg.setCode(ResponseStatus.SUCCESS.getCode());
                msg.setMsg("修改权限信息成功");
                msg.setData(perm);
            } else {
                msg.setCode(ResponseStatus.FAIL.getCode());
                msg.setMsg("修改权限信息失败");
            }
        } else {
            msg.setCode(ResponseStatus.FAIL.getCode());
            msg.setMsg("提交数据异常");
        }
        return msg;
    }

    @ApiOperation("删除权限信息")
    @DeleteMapping("/remove/{id}")
    public ResponseMsg remove(@ApiParam(name = "perm", value = "权限对象id", required = true)
                              @PathVariable("id") Integer id) {
        ResponseMsg msg = new ResponseMsg();
        if(!permService.hashChild(id)){
            if (permService.remove(id)) {
                msg.setCode(ResponseStatus.SUCCESS.getCode());
                msg.setMsg("删除权限信息成功");
            } else {
                msg.setCode(ResponseStatus.FAIL.getCode());
                msg.setMsg("删除权限信息失败");
            }
        }else {
            msg.setCode(ResponseStatus.FAIL.getCode());
            msg.setMsg("当前节点还包含子节点，无法删除");
        }
        return msg;
    }
}
