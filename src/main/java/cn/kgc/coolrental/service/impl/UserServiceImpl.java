package cn.kgc.coolrental.service.impl;

import cn.kgc.coolrental.dto.UserDto;
import cn.kgc.coolrental.entity.Perm;
import cn.kgc.coolrental.entity.Role;
import cn.kgc.coolrental.entity.User;
import cn.kgc.coolrental.mapper.UserMapper;
import cn.kgc.coolrental.service.UserService;
import cn.kgc.coolrental.util.StringUtil;
import com.alibaba.druid.pool.WrapperAdapter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(User user) {
        return userMapper.selectOne(new QueryWrapper<User>(user));
    }

    @Override
    public Page<User> queryAllPage(Page<User> userPage) {
        return userMapper.selectPage(userPage, new QueryWrapper<User>());
    }

    @Override
    public User queryById(Integer id) {
        return userMapper.selectById(id);
    }

    @Override
    public boolean add(UserDto userDto) {
        User user = userDto.getUser();
        if (userMapper.insert(user) > 0) {
            if (userDto.getRoles() != null && userDto.getRoles().size() > 0) {
                return userMapper.insertUserRoles(user.getId(), userDto.getRoles());
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(Integer id) {
        return userMapper.deleteById(id) > 0;
    }

    @Override
    public boolean modify(User user) {
        return userMapper.updateById(user) > 0;
    }

    @Override
    public boolean modify(UserDto userDto) {
        userMapper.updateById(userDto.getUser());
        if (userDto.getRoles() != null && userDto.getRoles().size() > 0) {
            //删除原有 插入新增
            //这里无法判断逻辑，因为原来如果就为空，那么最后返回的是false，这不代表删除原有失败，需要继续插入操作才可
            userMapper.deleteUserRoles(userDto.getUser().getId());
            userMapper.insertUserRoles(userDto.getUser().getId(), userDto.getRoles());
        } else if (userDto.getRoles() != null && userDto.getRoles().size() == 0) {
            //仅删除原有
            userMapper.deleteUserRoles(userDto.getUser().getId());
        }
        return true;
    }

    @Override
    public Page<User> queryWord(Integer roleId, String word, Page<User> userPage) {
        Page<User> page = null;
        if (roleId != null && StringUtil.notEmpty(word)) {
            //如果俩个条件都不为空

        } else if (StringUtil.notEmpty(word)) {
            //如果关键字不为空
            page = userMapper.selectPage(userPage,
                    new QueryWrapper<User>().like("name", word)
                            .or()
                            .like("nick", word)
                            .or()
                            .eq("phone", word));
        } else if (roleId != null) {
            //如果角色不为空
            List<User> users = userMapper.queryUsersByRoleId(roleId, ((userPage.getCurrent() - 1) * userPage.getSize()), userPage.getSize());
            page = userPage.setRecords(users);
        } else {
            page = queryAllPage(userPage);
        }
        return page;
    }

    @Override
    public boolean hashName(String name) {
        return userMapper.selectCount(new QueryWrapper<User>().eq("nick", name)) > 0;
    }

    @Override
    public Set<Role> queryUserRoles(Integer id) {
        return userMapper.queryUserRoles(id);
    }

    @Override
    public Set<Perm> queryUserPerms(Integer id) {
        return userMapper.queryUserPerms(id);
    }

    @Override
    public UserDto queryUserDto(Integer id) {
        UserDto userDto = new UserDto();
        userDto.setUser(queryById(id));
//        userDto.setRoles(queryUserRoles(id));
//        userDto.setPerms(queryUserPerms(id));
        return userDto;
    }
}
