package com.github.binarywang.demo.wx.cp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: zule
 * @Date: 2019/5/7
 */
@RestController
public class SecurityController {

    @Autowired
    UserDetailsService userDetailsService;

    //===========step1:jwt token 身份认证===========
    //登录页面
    @GetMapping("/security/loginpage")
    String loginpage(){
        UserDetails admin = userDetailsService.loadUserByUsername("admin");

        return "loginpage";
    }
    //登出
    @GetMapping("/security/loginout")
    String loginout(){
        return "loginout success";
    }
    //不需要token，就可以访问
    @GetMapping("/security/noauth")
    String noauth(){
        return "noauth";
    }

    //==========step2:需要token，需要权限校验===========
    //需要token，需要common角色
    @GetMapping("/security/common")
    String common(){
        return "security common";
    }
    //需要token，需要admin角色
    @GetMapping("/security/manager")
    String manager(){
        return "security manager";
    }

    //==========step3:需要token，不需要权限校验===========
    @GetMapping("/security/needauth")
    String auth(){
        return "needauth";
    }
}
