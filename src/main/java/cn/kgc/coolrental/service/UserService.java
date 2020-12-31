package cn.kgc.coolrental.service;

import cn.kgc.coolrental.dto.UserDto;
import cn.kgc.coolrental.entity.Perm;
import cn.kgc.coolrental.entity.Role;
import cn.kgc.coolrental.entity.User;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Set;

public interface UserService {
    User login(User user);

    Page<User> queryAllPage(Page<User> userPage);

    UserDto queryUserDto(Integer id);

    User queryById(Integer id);

    boolean hashName(String name);

    boolean add(UserDto userDto);

    boolean remove(Integer integer);

    boolean modify(User user);

    boolean modify(UserDto userdto);

    Page<User> queryWord(Integer roleId, String word, Page<User> userPage);

    Set<Role> queryUserRoles(Integer id);

    Set<Perm> queryUserPerms(Integer id);

}
