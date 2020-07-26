package com.github.binarywang.demo.wx.cp.security.entity;

import java.util.List;

/**
 * @Description:角色类
 * @Author: zule
 * @Date: 2019/5/6
 */

public class Role  {
    private Integer id ;
    private String name;

    private List<User> users;

    public Role() {
    }

    public Role(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
