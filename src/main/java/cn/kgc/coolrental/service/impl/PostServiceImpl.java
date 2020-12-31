package cn.kgc.coolrental.service.impl;

import cn.kgc.coolrental.entity.Post;
import cn.kgc.coolrental.mapper.PostMapper;
import cn.kgc.coolrental.service.PostService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostMapper postMapper;

    @Override
//    @Cacheable(cacheNames = "post",key = "'page:'+#postPage.current+':'+#postPage.size")
    public Page<Post> queryAllPage(Page<Post> postPage) {
        return postMapper.selectPage(postPage, new QueryWrapper<Post>());
    }

    /**
     * --@Cacheable注解会先查询是否已经有缓存，有会使用缓存，没有则会执行方法并缓存
     * 命名空间:@Cacheable的value会替换@CacheConfig的cacheNames(两者必须有一个)
     * --key是[命名空间]::[@Cacheable的key或者KeyGenerator生成的key](@Cacheable的key优先级高,KeyGenerator不配置走默认KeyGenerator SimpleKey [])
     */
    @Override
    @Cacheable(cacheNames = "post",key = "#id")
    public Post queryById(Integer id) {
        return postMapper.selectById(id);
    }

    /**
     * --@CachePut注解的作用 主要针对方法配置，能够根据方法的请求参数对其结果进行缓存，
     * 和 @Cacheable 不同的是，它每次都会触发真实方法的调用
     * 简单来说就是用户更新缓存数据。但需要注意的是该注解的value 和 key 必须与要更新的缓存相同，也就是与@Cacheable 相同
     * 默认先执行数据库更新再执行缓存更新
     * 注意返回值必须是要修改后的数据
     */
    @Override
    @CachePut(cacheNames = "post",key="#post.id")
    public boolean add(Post post) {
        return postMapper.insert(post) > 0;
    }

    @Override
    @CacheEvict(cacheNames = "post",key="#post.id")
    public boolean remove(Integer integer) {
        return postMapper.deleteById(integer) > 0;
    }

    @Override
    @CachePut(cacheNames = "post",key="#post.id")
    public boolean modify(Post post) {
        return postMapper.updateById(post) > 0;
    }

    @Override
    public Page<Post> queryWord(String word, Page<Post> postPage) {
        return postMapper.selectPage(postPage, new QueryWrapper<Post>().like("type_name", word).or().like("post_names", word));
    }
}
