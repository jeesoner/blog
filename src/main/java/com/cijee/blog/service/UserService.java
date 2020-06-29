package com.cijee.blog.service;

import com.cijee.blog.model.po.User;

/**
 * @author cijee
 * @date 2020/6/27
 */
public interface UserService {

    /**
     * 检查用户在数据库中是否存在
     *
     * @param username 用户名
     * @param password 用户密码
     * @return 返回存在的用户
     */
    User checkUser(String username, String password);
}
