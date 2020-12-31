package cn.kgc.coolrental.service.impl;

import cn.kgc.coolrental.entity.BrandModel;
import cn.kgc.coolrental.mapper.BrandModelMapper;
import cn.kgc.coolrental.service.BrandModelService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandModelServiceImpl implements BrandModelService {
    @Autowired
    private BrandModelMapper mapper;

    @Override
    public List<BrandModel> queryAll() {
        return mapper.selectList(new QueryWrapper<>());
    }

    @Override
    public List<BrandModel> queryWord(String word) {
        return mapper.selectList(new QueryWrapper<BrandModel>().like("name", word));
    }

    @Override
    @Cacheable(cacheNames = "brand_model",key = "#id")
    public BrandModel queryById(Integer id) {
        return mapper.selectById(id);
    }

    @Override
    @CachePut(cacheNames = "brand_model",key = "#brandModel.id")
    public boolean add(BrandModel brandModel) {
        return mapper.insert(brandModel) > 0;
    }

    @Override
    @CacheEvict(cacheNames = "brand_model",key = "#id")
    public boolean remove(Integer id) {
        return mapper.deleteById(id) > 0;
    }

    @Override
    @CachePut(cacheNames = "brand_model",key = "#brandModel.id")
    public boolean modify(BrandModel brandModel) {
        return mapper.updateById(brandModel) > 0;
    }
}
