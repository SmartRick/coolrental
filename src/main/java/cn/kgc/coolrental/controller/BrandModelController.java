package cn.kgc.coolrental.controller;

import cn.kgc.coolrental.constant.ResponseStatus;
import cn.kgc.coolrental.dto.ResponseMsg;
import cn.kgc.coolrental.entity.BrandModel;
import cn.kgc.coolrental.entity.Post;
import cn.kgc.coolrental.service.BrandModelService;
import cn.kgc.coolrental.service.PostService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "品牌型号管理API")
@RestController
@RequestMapping("/bm")
public class BrandModelController {
    @Autowired
    private BrandModelService service;
    private final String type = "品牌型号";

    @ApiOperation("查询所有"+type+"信息")
    @GetMapping("/queryAll")
    public ResponseMsg queryAllPage() {
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setData(service.queryAll());
        return responseMsg;
    }

    @ApiOperation("根据Id查询"+type+"信息")
    @GetMapping("/queryById/{id}")
    public ResponseMsg queryById(@PathVariable("id") Integer id) {
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setData(service.queryById(id));
        return responseMsg;
    }

    @ApiOperation("根据关键字查询"+type+"信息")
    @PostMapping("/queryWord")
    public ResponseMsg queryWord(
            @ApiParam(name = "word", value = "搜索关键字", required = true) String word) {
        return ResponseMsg.data(service.queryWord(word));
    }

    @ApiOperation("添加"+type+"信息")
    @PostMapping("/add")
    public ResponseMsg add(
            @ApiParam(name = "brandModel", value = type+"对象", required = true)
            @RequestBody BrandModel brandModel) {
        return ResponseMsg.res(service.add(brandModel),"添加",type,brandModel);
    }

    @ApiOperation("根据Id删除"+type+"信息")
    @DeleteMapping("/remove/{id}")
    public ResponseMsg remove(
            @ApiParam(name = "id", value = type+"Id", required = true)
            @PathVariable("id") Integer id) {
        ResponseMsg msg = null;
        if (id != null) {
            msg = ResponseMsg.res(service.remove(id),"删除",type);
        } else {
            msg = ResponseMsg.fail("提交数据异常");
        }
        return msg;
    }

    @ApiOperation("修改"+type+"信息")
    @PutMapping("/modify")
    public ResponseMsg modify(
            @ApiParam(name = "brandModel", value = type+"对象", required = true)
            @RequestBody BrandModel brandModel) {
        ResponseMsg msg = null;
        if (brandModel.getId() != null) {
            msg = ResponseMsg.res(service.modify(brandModel),"修改",type,brandModel);
        } else {
            msg = ResponseMsg.fail("提交数据异常");
        }
        return msg;
    }
}
