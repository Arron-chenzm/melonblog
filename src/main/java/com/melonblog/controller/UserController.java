package com.melonblog.controller;


import com.melonblog.common.lang.Result;
import com.melonblog.entity.User;
import com.melonblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author anonymous
 * @since 2022-01-11
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/index")
    public Result index(){

        User user = userService.getById(1L);
        return Result.succ(user);
    }

}
