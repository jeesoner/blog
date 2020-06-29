package com.cijee.blog.service;

import com.cijee.blog.model.po.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author cijee
 * @date 2020/6/29
 */
public interface TagService {

    Tag saveTag(Tag tag);
    
    Tag getTag(Long id);
    
    Tag getTagByName(String name);
    
    Page<Tag> listTag(Pageable pageable);
    
    Tag updateTag(Long id, Tag tag);

    void removeTag(Long id);
}
