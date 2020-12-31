package cn.kgc.coolrental.mapper;

import cn.kgc.coolrental.entity.Perm;
import cn.kgc.coolrental.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

public interface RoleMapper extends BaseMapper<Role> {
    Set<Perm> queryRolePerms(Integer id);
    boolean deleteRolePerms(Integer roleId);
    boolean insertRolePerms(@Param("roleId") Integer roleId,@Param("perms") Set<Integer> perms);
}
