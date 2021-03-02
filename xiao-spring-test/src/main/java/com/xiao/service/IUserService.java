package com.xiao.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.xiao.pojo.User;

public interface IUserService {
    List<User> getAllUser();

    User GetUserInfoById(Integer id);

    boolean updateUser(int id, Map<String, Object> fieldMap);

    void register(LinkedHashMap<String, Object> fieldMap);
}
