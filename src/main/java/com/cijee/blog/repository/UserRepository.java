package com.cijee.blog.repository;

import com.cijee.blog.model.po.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author cijee
 * @date 2020/6/27
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsernameAndPassword(String username, String password);


}
