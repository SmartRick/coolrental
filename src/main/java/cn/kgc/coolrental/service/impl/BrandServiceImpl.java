package cn.kgc.coolrental.service.impl;

import cn.kgc.coolrental.entity.Brand;
import cn.kgc.coolrental.entity.BrandModel;
import cn.kgc.coolrental.entity.User;
import cn.kgc.coolrental.mapper.BrandMapper;
import cn.kgc.coolrental.mapper.BrandModelMapper;
import cn.kgc.coolrental.service.BrandModelService;
import cn.kgc.coolrental.service.BrandService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BrandServiceImpl  extends ServiceImpl<BrandMapper,Brand> implements BrandService{

    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private BrandModelMapper brandModelMapper;

    @Override
    public Page<Brand> queryAllPage(Page<Brand> brandPage) {
        List<Brand> brands = brandMapper.queryAllPage((int) ((brandPage.getCurrent() - 1) * brandPage.getSize()), (int) brandPage.getSize());
        System.out.println(brands);
        brandPage.setRecords(brands);
        brandPage.setTotal(brandMapper.selectCount(new QueryWrapper<Brand>()));
        return brandPage;
    }

    @Override
    public List<Brand> queryAll() {
        return brandMapper.queryAll();
    }

    @Override
    @Cacheable(cacheNames = "brand",key = "#brand.id")
    public Brand queryById(Integer id) {
        return brandMapper.queryById(id);
    }

    @Override
    public boolean hashName(String name) {
        return brandMapper.selectCount(new QueryWrapper<Brand>().eq("name", name)) > 0;
    }

    @Override
    @CachePut(cacheNames = "brand",key = "#brand.id")
    public boolean add(Brand brand) {
        if (brandMapper.insert(brand) > 0) {
            List<BrandModel> modelList = brand.getModelList();
            if (modelList != null && modelList.size() > 0) {
                for (BrandModel brandModel : modelList) {
                    if(brandModel.getId()==null && brandModel.getName()!=null){
                        brandModelMapper.insert(brandModel);
                    }
                }
//                System.out.println(modelList);
                return brandMapper.addModelList(brand.getId(), modelList);
            }
            return true;
        }
        return false;
    }

    @Override
    @CacheEvict(cacheNames = "brand",key = "#brand.id")
    public boolean remove(Integer id) {
        if (brandMapper.deleteById(id) > 0) {
            brandMapper.deleteModelsById(id);
            return true;
        }
        return false;
    }

    @Override
    @CachePut(cacheNames = "brand",key = "#brand.id")
    public boolean modify(Brand brand) {
        brandMapper.updateById(brand);
        brandMapper.deleteModelsById(brand.getId());
        List<BrandModel> modelList = brand.getModelList();
        if (modelList != null && modelList.size() > 0) {
            for (BrandModel brandModel : modelList) {
                if(brandModel.getId()==null && brandModel.getName()!=null){
                    brandModelMapper.insert(brandModel);
                }
            }
//            System.out.println(modelList);
            return brandMapper.addModelList(brand.getId(), modelList);
        }
        return true;
    }

    @Override
    public Page<Brand> queryWord(String word, Page<Brand> brandPage) {
        List<Brand> brands = brandMapper.queryWord(word, (int) ((brandPage.getCurrent() - 1) * brandPage.getSize()), (int) brandPage.getSize());
        brandPage.setRecords(brands);
        brandPage.setTotal(brandMapper.selectCount(new QueryWrapper<Brand>()));
        return brandPage;
    }

    @Override
    public boolean statusOp(Brand brand) {
        return brandMapper.updateById(brand) > 0;
    }
}
