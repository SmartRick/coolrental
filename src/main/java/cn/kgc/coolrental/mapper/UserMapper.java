package cn.kgc.coolrental.mapper;

import cn.kgc.coolrental.entity.Perm;
import cn.kgc.coolrental.entity.Role;
import cn.kgc.coolrental.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface UserMapper extends BaseMapper<User> {
    Set<Role> queryUserRoles(Integer id);
    Set<Perm> queryUserPerms(Integer id);
    List<User> queryUsersByRoleId(@Param("roleId") Integer roleId,@Param("current")Long current,@Param("size") Long size);
    boolean insertUserRoles(@Param("userId") Integer userId,@Param("roles") Set<Integer> roles);
    boolean deleteUserRoles(@Param("userId") Integer userId);
}
