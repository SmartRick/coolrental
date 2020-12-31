package cn.kgc.coolrental.service;

import cn.kgc.coolrental.entity.BrandModel;
import cn.kgc.coolrental.entity.Post;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

public interface BrandModelService {
    List<BrandModel> queryAll();

    List<BrandModel> queryWord(String word);

    BrandModel queryById(Integer id);

    boolean add(BrandModel brandModel);

    boolean remove(Integer id);

    boolean modify(BrandModel brandModel);
}
