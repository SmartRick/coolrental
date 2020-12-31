package cn.kgc.coolrental.service.impl;

import cn.kgc.coolrental.entity.Product;
import cn.kgc.coolrental.mapper.ProductMapper;
import cn.kgc.coolrental.service.ProductService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper mapper;

    @Override
    public Page<Product> queryAllPage(Page<Product> productPage) {
        return mapper.selectPage(productPage,new QueryWrapper<>());
    }

    @Override
    public Page<Product> queryWord(String word, Page<Product> productPage) {
        return mapper.selectPage(productPage,new QueryWrapper<Product>().like("name",word));
    }

    @Override
    public Product queryById(Integer id) {
        return mapper.selectById(id);
    }

    @Override
    public boolean add(Product product) {
        return mapper.insert(product) > 0;
    }

    @Override
    public boolean remove(Integer integer) {
        return mapper.deleteById(integer) > 0;
    }

    @Override
    public boolean modify(Product product) {
        return mapper.updateById(product) > 0;
    }
}
