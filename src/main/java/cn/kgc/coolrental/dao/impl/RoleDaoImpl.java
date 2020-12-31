package cn.kgc.coolrental.dao.impl;

import cn.kgc.coolrental.dao.RoleDao;
import cn.kgc.coolrental.dto.RoleDto;
import cn.kgc.coolrental.entity.Perm;
import cn.kgc.coolrental.entity.Role;
import cn.kgc.coolrental.util.RedisUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class RoleDaoImpl implements RoleDao {
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public Page<Role> queryWord(String word, Page<Role> rolePage) {
        return null;
    }

    @Override
    public List<Role> queryAll() {
        return (List<Role>) redisUtil.get("roleAll");
    }

    @Override
    public Page<Role> queryAllPage(Page<Role> rolePage) {
        return null;
    }

    @Override
    public Set<Perm> queryRolePerms(Integer id) {
        return null;
    }

    @Override
    public Role queryById(Integer id) {
        return null;
    }

    @Override
    public boolean add(Role role) {
        return false;
    }

    @Override
    public boolean add(RoleDto roleDto) {
        return false;
    }

    @Override
    public boolean remove(Integer integer) {
        return false;
    }

    @Override
    public boolean modify(Role role) {
        return false;
    }

    @Override
    public boolean modify(RoleDto roleDto) {
        return false;
    }
}
