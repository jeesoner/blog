package com.cijee.blog.service.impl;

import com.cijee.blog.exception.NotFoundException;
import com.cijee.blog.model.po.Tag;
import com.cijee.blog.repository.TagRepository;
import com.cijee.blog.service.TagService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author cijee
 * @date 2020/6/29
 */
@Service
@Transactional
public class TagServiceImpl implements TagService {
    
    private TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public Tag getTag(Long id) {
        return tagRepository.findById(id).get();
    }

    @Override
    public Tag getTagByName(String name) {
        Tag tag = tagRepository.findTagByName(name);
        return tag;
    }

    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag t = tagRepository.findById(id).get();
        if (t == null) {
            throw new NotFoundException("不存在该类型");
        }
        BeanUtils.copyProperties(tag, t);
        return tagRepository.save(t);
    }

    @Override
    public void removeTag(Long id) {
        tagRepository.deleteById(id);
    }
}
