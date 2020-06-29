package com.cijee.blog.service.impl;

import com.cijee.blog.model.po.User;
import com.cijee.blog.repository.UserRepository;
import com.cijee.blog.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author cijee
 * @date 2020/6/27
 */
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User checkUser(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }
}
