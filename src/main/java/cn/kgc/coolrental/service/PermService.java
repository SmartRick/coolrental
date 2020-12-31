package cn.kgc.coolrental.service;

import cn.kgc.coolrental.dto.ElementUiTree;
import cn.kgc.coolrental.entity.Perm;
import cn.kgc.coolrental.entity.Role;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

public interface PermService {
    List<Perm> queryWord(String word);

    List<Perm> queryAll();

    List<ElementUiTree> queryAllTree();

    List<ElementUiTree> queryChildByPid(Integer pid);

    boolean hashChild(Integer pid);

    Perm queryById(Integer id);

    boolean add(Perm perm);

    boolean remove(Integer id);

    boolean modify(Perm perm);
}
