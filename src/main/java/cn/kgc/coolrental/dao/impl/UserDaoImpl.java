package cn.kgc.coolrental.dao.impl;

import cn.kgc.coolrental.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl {
    @Autowired
    private RedisUtil redisUtil;
}
