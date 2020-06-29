package com.cijee.blog.repository;

import com.cijee.blog.model.po.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author cijee
 * @date 2020/6/28
 */
@Repository
public interface TypeRepository extends JpaRepository<Type, Long> {

    Type findTypeByName(String name);

}
