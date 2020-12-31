package cn.kgc.coolrental.service.impl;

import cn.kgc.coolrental.dto.ElementUiTree;
import cn.kgc.coolrental.entity.Perm;
import cn.kgc.coolrental.entity.Product;
import cn.kgc.coolrental.entity.ProductType;
import cn.kgc.coolrental.mapper.ProductTypeMapper;
import cn.kgc.coolrental.service.ProductTypeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductTypeServiceImpl implements ProductTypeService {
    @Autowired
    private ProductTypeMapper mapper;

    @Override
    public List<ProductType> queryAll() {
        return mapper.selectList(new QueryWrapper<>());
    }

    @Override
    public List<ElementUiTree> queryAllTree() {
        List<ProductType> parent = mapper.selectList(new QueryWrapper<ProductType>().isNull("p_id"));
        List<ElementUiTree> rootList = new ArrayList<>();
        ElementUiTree elementUiTree = null;
        for (ProductType productType : parent) {
            elementUiTree = new ElementUiTree();
            elementUiTree.setId(productType.getId());
            elementUiTree.setLabel(productType.getProductName());
            elementUiTree.setChildren(queryChildByPid(productType.getId()));
            rootList.add(elementUiTree);
        }
        return rootList;
    }

    @Override
    public List<ElementUiTree> queryChildByPid(Integer pid) {
        List<ProductType> childList = mapper.selectList(new QueryWrapper<ProductType>().eq("p_id", pid));
        List<ElementUiTree> childs = new ArrayList<>();
        ElementUiTree elementUiTree = null;
        if(childList!=null && childList.size()>0){
            for (ProductType productType : childList) {
                elementUiTree = new ElementUiTree();
                elementUiTree.setId(productType.getId());
                elementUiTree.setLabel(productType.getProductName());
                elementUiTree.setChildren(queryChildByPid(productType.getId()));
                childs.add(elementUiTree);
            }
        }
        return childs;
    }

    @Override
    public ProductType queryById(Integer id) {
        return mapper.selectById(id);
    }

    @Override
    public boolean add(ProductType productType) {
        return mapper.insert(productType)>0;
    }

    @Override
    public boolean remove(Integer integer) {
        return mapper.deleteById(integer) > 0;
    }

    @Override
    public boolean modify(ProductType productType) {
        return mapper.updateById(productType) > 0;
    }

    @Override
    public boolean hashChild(Integer pid) {
        return mapper.selectCount(new QueryWrapper<ProductType>().eq("p_id",pid)) > 0;
    }
}
