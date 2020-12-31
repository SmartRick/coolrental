package cn.kgc.coolrental.controller;

import cn.kgc.coolrental.constant.ResponseStatus;
import cn.kgc.coolrental.dto.ResponseMsg;
import cn.kgc.coolrental.entity.Brand;
import cn.kgc.coolrental.entity.BrandModel;
import cn.kgc.coolrental.entity.Post;
import cn.kgc.coolrental.service.BrandService;
import cn.kgc.coolrental.service.PostService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "品牌管理API")
@RestController
@RequestMapping("/brand")
public class BrandController {
    @Autowired
    private BrandService service;
    private final String type = "品牌";

    @ApiOperation("查询所有"+type+"分页")
    @GetMapping("/queryAllPage")
    public ResponseMsg queryAllPage(@RequestParam(required = false, defaultValue = "1") Integer index,
                                    @RequestParam(required = false, defaultValue = "10") Integer size) {
        return ResponseMsg.data(service.queryAllPage(new Page<Brand>(index,size)));
    }

    @ApiOperation("根据关键字查询"+type+"信息")
    @PostMapping("/queryWord")
    public ResponseMsg queryWord(
            @ApiParam(name = "word", value = "搜索关键字", required = true) String word,
            @RequestParam(required = false, defaultValue = "1") Integer index,
            @RequestParam(required = false, defaultValue = "10") Integer size) {
        Page<Brand> brandPage = new Page<Brand>(index,size);
        return ResponseMsg.data(service.queryWord(word, brandPage));
    }

    @ApiOperation("查询所有"+type)
    @GetMapping("/queryAll")
    public ResponseMsg queryAll() {
        return ResponseMsg.data(service.queryAll());
    }

    @ApiOperation("根据Id查询"+type+"信息")
    @GetMapping("/queryById/{id}")
    public ResponseMsg queryById(@PathVariable("id") Integer id) {
        return ResponseMsg.data(service.queryById(id));
    }

    @ApiOperation("添加"+type)
    @PostMapping("/add")
    public ResponseMsg add(
            @ApiParam(name = "brand", value = "品牌对象", required = true)
            @RequestBody Brand brand) {
        ResponseMsg msg = null;
        if (brand != null) {
            if (!service.hashName(brand.getName())) {
                msg = ResponseMsg.res(service.add(brand),"添加",type,brand);
            }else{
                msg = ResponseMsg.fail("添加品牌失败,品牌名称以存在");
            }
        } else {
            msg = ResponseMsg.fail("提交数据异常");
        }
        return msg;
    }

    @ApiOperation("根据Id删除品牌")
    @DeleteMapping("/remove/{id}")
    public ResponseMsg remove(
            @ApiParam(name = "id", value = "品牌型号Id", required = true)
            @PathVariable("id") Integer id) {
        ResponseMsg msg = new ResponseMsg();
        if (id != null) {
            msg = ResponseMsg.res(service.remove(id),"删除",type);
        } else {
            msg = ResponseMsg.fail("提交数据异常");
        }
        return msg;
    }

    @ApiOperation("修改品牌型号信息")
    @PutMapping("/modify")
    public ResponseMsg modify(
            @ApiParam(name = "brand", value = "品牌对象", required = true)
            @RequestBody Brand brand) {
        ResponseMsg msg = new ResponseMsg();
        if (brand != null) {
                if (brand.getId() != null) {
                    if (service.modify(brand)) {
                        msg.setCode(cn.kgc.coolrental.constant.ResponseStatus.SUCCESS.getCode());
                        msg.setMsg("修改品牌成功");
                        msg.setData(brand);
                    } else {
                        msg.setCode(ResponseStatus.FAIL.getCode());
                        msg.setMsg("修改品牌失败");
                    }
                } else {
                    msg.setCode(ResponseStatus.FAIL.getCode());
                    msg.setMsg("提交数据异常");
                }
        }else{
            msg.setCode(ResponseStatus.FAIL.getCode());
            msg.setMsg("提交数据异常");
        }

        return msg;
    }

    @ApiOperation("改变品牌启用状态")
    @PutMapping("/statusop")
    public ResponseMsg statusop(
            @ApiParam(name = "post", value = "品牌对象", required = true)
            @RequestBody Brand brand) {
        ResponseMsg msg = new ResponseMsg();
        if (service.statusOp(brand)) {
            msg.setCode(ResponseStatus.SUCCESS.getCode());
            msg.setMsg("改变品牌状态成功");
        } else {
            msg.setCode(ResponseStatus.FAIL.getCode());
            msg.setMsg("改变品牌状态失败");
        }
        return msg;
    }
}
