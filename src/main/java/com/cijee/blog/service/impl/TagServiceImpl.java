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
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author cijee
 * @date 2020/6/29
 */
@Service
@Transactional
public class TagServiceImpl implements TagService {
    
    private final TagRepository tagRepository;

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
    public List<Tag> listTag() {
        return tagRepository.findAll();
    }

    @Override
    public List<Tag> listTag(String ids) {
        List<Tag> tags = tagRepository.findAllById(coverToList(ids));
        return tags;
    }

    private List<Long> coverToList(String ids) {
        List<Long> list = new ArrayList<>();
        if (!StringUtils.isEmpty(ids)) {
            String[] idArray = ids.split(",");
            for (String id : idArray) {
                list.add(new Long(id));
            }
        }
        return list;
    }

    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag t = tagRepository.findById(id).get();
        if (t == null) {
            throw new NotFoundException("该标签不存在");
        }
        BeanUtils.copyProperties(tag, t);
        return tagRepository.save(t);
    }

    @Override
    public void removeTag(Long id) {
        tagRepository.deleteById(id);
    }
}
