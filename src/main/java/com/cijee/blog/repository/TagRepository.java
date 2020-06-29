package com.cijee.blog.repository;

/**
 * @author cijee
 * @date 2020/6/29
 */

import com.cijee.blog.model.po.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    Tag findTagByName(String name);
}
