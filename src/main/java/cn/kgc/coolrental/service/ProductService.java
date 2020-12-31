package cn.kgc.coolrental.service;

import cn.kgc.coolrental.entity.Post;
import cn.kgc.coolrental.entity.Product;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public interface ProductService {
    Page<Product> queryAllPage(Page<Product> productPage);

    Page<Product> queryWord(String word, Page<Product> productPage);

    Product queryById(Integer id);

    boolean add(Product product);

    boolean remove(Integer integer);

    boolean modify(Product product);

}
