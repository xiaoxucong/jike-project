package com.xiao.controller;

import com.spring.ioc.interfaces.Autowired;
import com.spring.ioc.interfaces.Controller;
import com.spring.ioc.interfaces.RequestMapping;
import com.spring.utils.RequestMethod;
import com.spring.mvc.Data;
import com.spring.mvc.Param;
import com.spring.mvc.View;
import com.xiao.service.IUserService;
import com.xiao.pojo.User;

import java.util.*;

@Controller
public class UserController {
    @Autowired
    private IUserService userService;

    /**
     * 用户列表
     * @return
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public View index() {
        return new View("index.jsp").addModel("list", null);
    }
    /**
     * 用户列表
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public View login(Param param) {
        boolean result = true;
        try {
            String username = (String) param.getParamMap().get("username");
            String password = (String) param.getParamMap().get("password");
            LinkedHashMap<String, Object> fieldMap = new LinkedHashMap<>();
            fieldMap.put("username", username);
            fieldMap.put("password", password);
            System.out.println("username="+username);
            System.out.println("password="+password);
            userService.register(fieldMap);

        }catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        if(result) {
            return new View("success.jsp").addModel("list", null);
        }else {
            return new View("error.jsp").addModel("list", null);
        }

    }



    /**
     * 用户列表
     * @return
     */
    @RequestMapping(value = "/userList", method = RequestMethod.GET)
    public View getUserList() {
        List<User> userList = userService.getAllUser();
        return new View("index.jsp").addModel("list", userList);
    }

    /**
     * 用户详情
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public Data getUserInfo(Param param) {
        String id = (String) param.getParamMap().get("id");
        User user = userService.GetUserInfoById(Integer.parseInt(id));

        return new Data(user);
    }

    @RequestMapping(value = "/userEdit", method = RequestMethod.GET)
    public Data editUser(Param param) {
        String id = (String) param.getParamMap().get("id");
        Map<String, Object> fieldMap = new HashMap<>();
        fieldMap.put("age", 911);
        userService.updateUser(Integer.parseInt(id), fieldMap);

        return new Data("Success.");
    }
}
