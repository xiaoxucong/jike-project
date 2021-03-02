package com.xiao.service.impl;

import com.spring.sql.DatabaseHelper;
import com.spring.aop.interfaces.Transactional;
import com.spring.ioc.interfaces.Service;
import com.spring.sql.MyDatabaseUtils;
import com.xiao.pojo.User;
import com.xiao.service.IUserService;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService implements IUserService {
    /**
     * 获取所有用户
     */
    public List<User> getAllUser() {
        String sql = "SELECT * FROM user";
        return null;
    }

    /**
     * 根据id获取用户信息
     */
    public User GetUserInfoById(Integer id) {
        String sql = "SELECT * FROM user WHERE id = ?";
        return DatabaseHelper.queryEntity(User.class, sql, id);
    }

    /**
     * 修改用户信息
     */
    @Transactional
    public boolean updateUser(int id, Map<String, Object> fieldMap) {
        return DatabaseHelper.updateEntity(User.class, id, fieldMap);
    }

    @Override
    public void register(LinkedHashMap<String, Object> fieldMap) {
        String sql = "insert into user (username,password,createtime)value(?,?,now())";
       // DatabaseHelper.insertEntity(User.class, fieldMap);
        MyDatabaseUtils.updateSql(sql,fieldMap);
        String sql_1 = "select id,username,password,createtime,time from user  where  id = 1 limit 1 ";
        MyDatabaseUtils.queryOne(User.class,sql_1,null);
    }
}
