package cn.kgc.coolrental.service;

import cn.kgc.coolrental.entity.Post;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.context.annotation.Scope;

import java.math.BigDecimal;

public interface PostService {

    Page<Post> queryAllPage(Page<Post> postTypePage);

    Page<Post> queryWord(String word, Page<Post> postTypePage);

    Post queryById(Integer id);

    boolean add(Post post);

    boolean remove(Integer integer);

    boolean modify(Post post);
}
