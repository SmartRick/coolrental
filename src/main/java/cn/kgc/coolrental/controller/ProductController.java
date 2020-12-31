package cn.kgc.coolrental.controller;

import cn.kgc.coolrental.constant.ResponseStatus;
import cn.kgc.coolrental.dto.ResponseMsg;
import cn.kgc.coolrental.entity.Post;
import cn.kgc.coolrental.entity.Product;
import cn.kgc.coolrental.service.ProductService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "产品管理API")
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @ApiOperation("查询所有产品信息")
    @GetMapping("/queryAllPage")
    public ResponseMsg queryAllPage(@RequestParam(required = false, defaultValue = "1") Integer index,
                                    @RequestParam(required = false, defaultValue = "10") Integer size) {
        Page<Product> page = new Page<Product>();
        page.setSize(size);
        page.setCurrent(index);
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setData(productService.queryAllPage(page));
        return responseMsg;
    }

    @ApiOperation("根据Id查询产品信息")
    @GetMapping("/queryById/{id}")
    public ResponseMsg queryById(@PathVariable("id") Integer id) {
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setData(productService.queryById(id));
        return responseMsg;
    }

    @ApiOperation("根据关键字查询产品信息")
    @PostMapping("/queryWord")
    public ResponseMsg queryWord(
            @ApiParam(name = "word", value = "搜索关键字", required = true) String word,
            @RequestParam(required = false, defaultValue = "1") Integer index,
            @RequestParam(required = false, defaultValue = "10") Integer size) {
        Page<Product> page = new Page<Product>();
        page.setSize(size);
        page.setCurrent(index);
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setData(productService.queryWord(word, page));
        return responseMsg;
    }

    @ApiOperation("添加产品信息")
    @PostMapping("/add")
    public ResponseMsg add(
            @ApiParam(name = "product", value = "产品信息对象", required = true)
            @RequestBody Product product) {
        System.out.println(product);
        ResponseMsg msg = new ResponseMsg();
        if (productService.add(product)) {
            msg.setCode(cn.kgc.coolrental.constant.ResponseStatus.SUCCESS.getCode());
            msg.setMsg("添加产品信息成功");
            msg.setData(product);
        } else {
            msg.setCode(ResponseStatus.FAIL.getCode());
            msg.setMsg("添加产品信息失败");
        }
        return msg;
    }

    @ApiOperation("根据Id删除岗位信息")
    @DeleteMapping("/remove/{id}")
    public ResponseMsg remove(
            @ApiParam(name = "id", value = "岗位信息Id", required = true)
            @PathVariable("id") Integer id) {
        ResponseMsg msg = new ResponseMsg();
        if (id != null) {
            if (productService.remove(id)) {
                msg.setCode(cn.kgc.coolrental.constant.ResponseStatus.SUCCESS.getCode());
                msg.setMsg("删除产品信息成功");
            } else {
                msg.setCode(ResponseStatus.FAIL.getCode());
                msg.setMsg("删除产品信息失败");
            }
        } else {
            msg.setCode(ResponseStatus.FAIL.getCode());
            msg.setMsg("提交数据异常");
        }
        return msg;
    }

    @ApiOperation("修改产品信息")
    @PutMapping("/modify")
    public ResponseMsg modify(
            @ApiParam(name = "product", value = "产品信息对象", required = true)
            @RequestBody Product product) {

        System.out.println(product);
        ResponseMsg msg = new ResponseMsg();
        if (product.getId() != null) {
            if (productService.modify(product)) {
                msg.setCode(cn.kgc.coolrental.constant.ResponseStatus.SUCCESS.getCode());
                msg.setMsg("修改产品信息成功");
                msg.setData(product);
            } else {
                msg.setCode(ResponseStatus.FAIL.getCode());
                msg.setMsg("修改产品信息失败");
            }
        } else {
            msg.setCode(ResponseStatus.FAIL.getCode());
            msg.setMsg("提交数据异常");
        }
        return msg;
    }

    @ApiOperation("改变岗位类型状态")
    @PutMapping("/statusop")
    public ResponseMsg statusop(
            @ApiParam(name = "product", value = "产品信息对象", required = true)
            @RequestBody Product product) {
        ResponseMsg msg = new ResponseMsg();
        if (productService.modify(product)) {
            msg.setCode(ResponseStatus.SUCCESS.getCode());
            msg.setMsg("改变产品状态成功");
        } else {
            msg.setCode(ResponseStatus.FAIL.getCode());
            msg.setMsg("改变产品状态失败");
        }
        return msg;
    }
}
