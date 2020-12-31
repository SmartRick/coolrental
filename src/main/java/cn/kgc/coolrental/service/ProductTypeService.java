package cn.kgc.coolrental.service;

import cn.kgc.coolrental.dto.ElementUiTree;
import cn.kgc.coolrental.entity.Perm;
import cn.kgc.coolrental.entity.ProductType;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

public interface ProductTypeService {
    List<ProductType> queryAll();

    List<ElementUiTree> queryAllTree();

    List<ElementUiTree> queryChildByPid(Integer pid);

    ProductType queryById(Integer id);

    boolean add(ProductType productType);

    boolean remove(Integer integer);

    boolean modify(ProductType productType);

    boolean hashChild(Integer pid);
}
