package org.geektimes.projects.user.web.controller;

import org.geektimes.projects.user.domain.User;
import org.geektimes.projects.user.service.UserService;
import org.geektimes.projects.user.service.UserServiceImpl;
import org.geektimes.web.mvc.controller.PageController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/test")
public class TestController implements PageController {
    private final UserService userService = new UserServiceImpl();
    @GET
    @POST
    @Path("/register") // /hello/world -> HelloWorldController
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        User user = new User();
        user.setId(1234L);
        user.setName("小马哥");
        user.setPassword("******1");
        user.setEmail("mercyblitz@gmail.com");
        user.setPhoneNumber("abcdefg");
        userService.register(user);
        return "index.jsp";
    }
}
