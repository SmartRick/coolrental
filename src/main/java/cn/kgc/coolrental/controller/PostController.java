package cn.kgc.coolrental.controller;

import cn.kgc.coolrental.annotation.RequestPost;
import cn.kgc.coolrental.constant.ResponseStatus;
import cn.kgc.coolrental.dto.ResponseMsg;
import cn.kgc.coolrental.entity.Post;
import cn.kgc.coolrental.entity.User;
import cn.kgc.coolrental.service.PostService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "岗位管理API")
@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    private PostService postService;

    @ApiOperation("查询所有岗位信息")
    @GetMapping("/queryAllPage")
    public ResponseMsg queryAllPage(@RequestParam(required = false, defaultValue = "1") Integer index,
                                    @RequestParam(required = false, defaultValue = "10") Integer size) {
        Page<Post> postPage = new Page<Post>();
        postPage.setSize(size);
        postPage.setCurrent(index);
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setData(postService.queryAllPage(postPage));
        return responseMsg;
    }

    @ApiOperation("根据Id查询岗位信息")
    @GetMapping("/queryById/{id}")
    public ResponseMsg queryById(@PathVariable("id") Integer id) {
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setData(postService.queryById(id));
        return responseMsg;
    }

    @ApiOperation("根据关键字查询岗位信息")
    @PostMapping("/queryWord")
    public ResponseMsg queryWord(
            @ApiParam(name = "word", value = "搜索关键字", required = true) String word,
            @RequestParam(required = false, defaultValue = "1") Integer index,
            @RequestParam(required = false, defaultValue = "10") Integer size) {
        Page<Post> postPage = new Page<Post>();
        postPage.setSize(size);
        postPage.setCurrent(index);
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setData(postService.queryWord(word, postPage));
        return responseMsg;
    }

    @ApiOperation("根据Id查询岗位信息")
    @PostMapping("/add")
    public ResponseMsg add(
            @ApiParam(name = "post", value = "岗位信息对象", required = true)
            @RequestBody Post post) {
        System.out.println(post);
        ResponseMsg msg = new ResponseMsg();
        if (postService.add(post)) {
            msg.setCode(cn.kgc.coolrental.constant.ResponseStatus.SUCCESS.getCode());
            msg.setMsg("添加岗位信息成功");
            msg.setData(post);
        } else {
            msg.setCode(ResponseStatus.FAIL.getCode());
            msg.setMsg("添加岗位信息失败");
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
            if (postService.remove(id)) {
                msg.setCode(cn.kgc.coolrental.constant.ResponseStatus.SUCCESS.getCode());
                msg.setMsg("删除岗位信息成功");
            } else {
                msg.setCode(ResponseStatus.FAIL.getCode());
                msg.setMsg("删除岗位信息失败");
            }
        } else {
            msg.setCode(ResponseStatus.FAIL.getCode());
            msg.setMsg("提交数据异常");
        }
        return msg;
    }

    @ApiOperation("修改岗位信息")
    @PutMapping("/modify")
    public ResponseMsg modify(
            @ApiParam(name = "post", value = "岗位信息对象", required = true)
            @RequestBody Post post) {

        System.out.println(post);
        ResponseMsg msg = new ResponseMsg();
        if (post.getId() != null) {
            if (postService.modify(post)) {
                msg.setCode(cn.kgc.coolrental.constant.ResponseStatus.SUCCESS.getCode());
                msg.setMsg("修改岗位信息成功");
                msg.setData(post);
            } else {
                msg.setCode(ResponseStatus.FAIL.getCode());
                msg.setMsg("修改岗位信息失败");
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
            @ApiParam(name = "post", value = "岗位信息对象", required = true)
            @RequestBody Post post) {
        ResponseMsg msg = new ResponseMsg();
        if (postService.modify(post)) {
            msg.setCode(ResponseStatus.SUCCESS.getCode());
            msg.setMsg("改变岗位类型状态成功");
        } else {
            msg.setCode(ResponseStatus.FAIL.getCode());
            msg.setMsg("改变岗位类型状态失败");
        }
        return msg;
    }
}
