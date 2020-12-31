package cn.kgc.coolrental.service.impl;

import cn.kgc.coolrental.dto.RoleDto;
import cn.kgc.coolrental.entity.Perm;
import cn.kgc.coolrental.entity.Role;
import cn.kgc.coolrental.entity.User;
import cn.kgc.coolrental.mapper.RoleMapper;
import cn.kgc.coolrental.mapper.UserMapper;
import cn.kgc.coolrental.service.RoleService;
import cn.kgc.coolrental.service.UserService;
import cn.kgc.coolrental.util.StringUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Set<Perm> queryRolePerms(Integer id) {
        return roleMapper.queryRolePerms(id);
    }

    @Override
    public List<Role> queryAll() {
        return roleMapper.selectList(new QueryWrapper<Role>());
    }

    @Override
    public Page<Role> queryAllPage(Page<Role> rolePage) {
        return roleMapper.selectPage(rolePage, new QueryWrapper<Role>());
    }

    @Override
    public Role queryById(Integer id) {
        return roleMapper.selectById(id);
    }

    @Override
    public boolean add(Role role) {
        return roleMapper.insert(role) > 0;
    }

    @Override
    public boolean add(RoleDto roleDto) {
        Role role = roleDto.getRole();
        if (roleMapper.insert(role) > 0) {
            if (roleDto.getPerms() != null && roleDto.getPerms().size() > 0) {
                return roleMapper.insertRolePerms(role.getId(), roleDto.getPerms());
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(Integer id) {
        return roleMapper.deleteById(id) > 0;
    }

    @Override
    public boolean modify(Role role) {
        return roleMapper.updateById(role) > 0;
    }

    @Override
    public boolean modify(RoleDto roleDto) {
        Role role = roleDto.getRole();
        //更新角色也无法判断，如果用户没有该角色数据，而是修改的角色权限数据，那么更新会返回false，但这不代表更新失败了
        roleMapper.updateById(role);
        if (roleDto.getPerms() != null && roleDto.getPerms().size() > 0) {
            //删除原有 插入新增
            //这里无法判断逻辑，因为原来如果就为空，那么最后返回的是false，这不代表删除原有失败，需要继续插入操作才可
            roleMapper.deleteRolePerms(role.getId());
            roleMapper.insertRolePerms(role.getId(), roleDto.getPerms());
        } else if (roleDto.getPerms() != null && roleDto.getPerms().size() == 0) {
            //仅删除原有
            roleMapper.deleteRolePerms(role.getId());
        }
        return true;
    }

    @Override
    public Page<Role> queryWord(String word, Page<Role> rolePage) {
        return roleMapper.selectPage(rolePage, new QueryWrapper<Role>().like("name", word).or().like("`desc`", word));
    }
}
