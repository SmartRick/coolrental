package cn.kgc.coolrental.service;

import cn.kgc.coolrental.dto.RoleDto;
import cn.kgc.coolrental.entity.Perm;
import cn.kgc.coolrental.entity.Role;
import cn.kgc.coolrental.entity.User;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Set;

public interface RoleService {
    Page<Role> queryWord(String word, Page<Role> rolePage);

    List<Role> queryAll();

    Page<Role> queryAllPage(Page<Role> rolePage);

    Set<Perm> queryRolePerms(Integer id);

    Role queryById(Integer id);

    boolean add(Role role);

    boolean add(RoleDto roleDto);

    boolean remove(Integer integer);

    boolean modify(Role role);

    boolean modify(RoleDto roleDto);
}
