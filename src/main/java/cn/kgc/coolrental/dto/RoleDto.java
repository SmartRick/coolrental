package cn.kgc.coolrental.dto;

import cn.kgc.coolrental.entity.Perm;
import cn.kgc.coolrental.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {
    private Role role;
    private Set<Integer> perms;
}
