package cn.kgc.coolrental.dto;

import cn.kgc.coolrental.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StorageUserInfo {
    private User user;
    private String targetRemote;
    private Set<String> roles;
    private Set<String> perms;
}
