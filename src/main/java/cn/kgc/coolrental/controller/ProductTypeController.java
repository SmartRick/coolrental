package cn.kgc.coolrental.controller;

import cn.kgc.coolrental.constant.ResponseStatus;
import cn.kgc.coolrental.dto.ElementUiTree;
import cn.kgc.coolrental.dto.ResponseMsg;
import cn.kgc.coolrental.entity.Perm;
import cn.kgc.coolrental.entity.ProductType;
import cn.kgc.coolrental.service.PermService;
import cn.kgc.coolrental.service.ProductTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "产品类型管理API")
@RestController
@RequestMapping("/pt")
public class ProductTypeController {
    @Autowired
    private ProductTypeService service;

    @ApiOperation("查询所有产品类型信息树")
    @GetMapping("/queryAllTree")
    public ResponseMsg queryAllTree() {
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setData(service.queryAllTree());
        return responseMsg;
    }

    @ApiOperation("查询所有产品类型信息")
    @GetMapping("/queryAll")
    public ResponseMsg queryAll() {
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setData(service.queryAll());
        return responseMsg;
    }

    @ApiOperation("查询某个产品分类的子分类")
    @GetMapping("/queryChildByPid/{pid}")
    public ResponseMsg queryChildByPid(@ApiParam(name = "pid", value = "产品分类Id", required = true)
                                       @PathVariable("pid") Integer pid) {
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setData(service.queryChildByPid(pid));
        return responseMsg;
    }

    @ApiOperation("根据Id查询具体产品分类信息")
    @GetMapping("/queryById/{id}")
    public ResponseMsg queryById(@ApiParam(name = "id", value = "产品分类Id", required = true)
                                 @PathVariable("id") Integer id) {
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setData(service.queryById(id));
        return responseMsg;
    }

    @ApiOperation("添加产品分类")
    @PostMapping("/add")
    public ResponseMsg add(@ApiParam(name = "productType", value = "产品分类对象", required = true) ProductType productType) {
        ResponseMsg msg = new ResponseMsg();
        if (service.add(productType)) {
            msg.setCode(ResponseStatus.SUCCESS.getCode());
            msg.setMsg("添加产品分类成功");
            ElementUiTree elementUiTree = new ElementUiTree();
            elementUiTree.setLabel(productType.getProductName());
            elementUiTree.setId(productType.getId());
            msg.setData(elementUiTree);
        } else {
            msg.setCode(ResponseStatus.FAIL.getCode());
            msg.setMsg("添加产品分类失败");
        }
        return msg;
    }

    @ApiOperation("修改产品分类")
    @PutMapping("/modify")
    public ResponseMsg modify(@ApiParam(name = "productType", value = "产品分类对象", required = true) ProductType productType) {
        ResponseMsg msg = new ResponseMsg();
        System.out.println(productType);
        if (productType.getId() != null) {
            if (service.modify(productType)) {
                msg.setCode(ResponseStatus.SUCCESS.getCode());
                msg.setMsg("修改产品分类成功");
                msg.setData(productType);
            } else {
                msg.setCode(ResponseStatus.FAIL.getCode());
                msg.setMsg("修改产品分类失败");
            }
        } else {
            msg.setCode(ResponseStatus.FAIL.getCode());
            msg.setMsg("提交数据异常");
        }
        return msg;
    }

    @ApiOperation("改变产品分类状态")
    @PutMapping("/statusop")
    public ResponseMsg statusop(@ApiParam(name = "productType", value = "产品分类对象", required = true) @RequestBody ProductType productType) {
        ResponseMsg msg = new ResponseMsg();
        System.out.println(productType);
        if (productType.getId() != null) {
            if (service.modify(productType)) {
                msg.setCode(ResponseStatus.SUCCESS.getCode());
                msg.setMsg("修改产品分类成功");
                msg.setData(productType);
            } else {
                msg.setCode(ResponseStatus.FAIL.getCode());
                msg.setMsg("修改产品分类失败");
            }
        } else {
            msg.setCode(ResponseStatus.FAIL.getCode());
            msg.setMsg("提交数据异常");
        }
        return msg;
    }

    @ApiOperation("删除产品分类")
    @DeleteMapping("/remove/{id}")
    public ResponseMsg remove(@ApiParam(name = "id", value = "产品分类对象id", required = true)
                              @PathVariable("id") Integer id) {
        ResponseMsg msg = new ResponseMsg();
        if(!service.hashChild(id)){
            if (service.remove(id)) {
                msg.setCode(ResponseStatus.SUCCESS.getCode());
                msg.setMsg("删除产品分类成功");
            } else {
                msg.setCode(ResponseStatus.FAIL.getCode());
                msg.setMsg("删除产品分类失败");
            }
        }else {
            msg.setCode(ResponseStatus.FAIL.getCode());
            msg.setMsg("当前节点还包含子节点，无法删除");
        }
        return msg;
    }
}
