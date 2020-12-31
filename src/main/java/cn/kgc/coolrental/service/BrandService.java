package cn.kgc.coolrental.service;

import cn.kgc.coolrental.dto.UserDto;
import cn.kgc.coolrental.entity.Brand;
import cn.kgc.coolrental.entity.Perm;
import cn.kgc.coolrental.entity.Role;
import cn.kgc.coolrental.entity.User;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

public interface BrandService extends IService<Brand> {
    Page<Brand> queryAllPage(Page<Brand> userPage);

    Page<Brand> queryWord(String word, Page<Brand> userPage);

    List<Brand> queryAll();

    Brand queryById(Integer id);

    boolean hashName(String name);

    boolean add(Brand brand);

    boolean remove(Integer id);

    boolean modify(Brand brand);

    boolean statusOp(Brand brand);

}
