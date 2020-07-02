package com.cijee.blog.service.impl;

import com.cijee.blog.exception.NotFoundException;
import com.cijee.blog.model.po.Tag;
import com.cijee.blog.repository.TagRepository;
import com.cijee.blog.service.TagService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    /**
     * 保存标签
     *
     * @param tag 标签对象
     * @return 标签对象
     */
    @Override
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    /**
     * 根据标签主键查询标签
     *
     * @param id 主键
     * @return 标签对象
     */
    @Override
    public Tag getTag(Long id) {
        return tagRepository.findById(id).orElse(null);
    }

    /**
     * 根据标签名查询标签对象
     *
     * @param name 标签名
     * @return 标签对象
     */
    @Override
    public Tag getTagByName(String name) {
        Tag tag = tagRepository.findTagByName(name);
        return tag;
    }

    /**
     * 根据分页参数获取分页结果
     *
     * @param pageable 分页参数
     * @return 分页结果
     */
    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    /**
     * 返回所有标签
     *
     * @return 标签集合
     */
    @Override
    public List<Tag> listTag() {
        return tagRepository.findAll();
    }

    /**
     * 根据主键，返回标签
     *
     * @param ids 主键
     * @return 标签集合
     */
    @Override
    public List<Tag> listTag(String ids) {
        List<Tag> tags = tagRepository.findAllById(coverToList(ids));
        return tags;
    }

    @Override
    public List<Tag> listTagTop(Integer topSize) {
        Pageable pageable = PageRequest.of(0, topSize, Sort.by(Sort.Direction.DESC, "blogs.size"));
        return tagRepository.findTop(pageable);
    }

    /**
     * 将字符串转换成列表
     * ids = "1,2,3,4,5"
     * cover -->>
     * list = [1,2,3,4,5]
     *
     * @param ids 主键集
     * @return 主键列表
     */
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

    /**
     * 更新标签
     *
     * @param id 主键
     * @param tag 更新的标签对象
     * @return 新的标签对象
     */
    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag t = tagRepository.findById(id).orElse(null);
        if (t == null) {
            throw new NotFoundException("该标签不存在");
        }
        BeanUtils.copyProperties(tag, t);
        return tagRepository.save(t);
    }

    /**
     * 删除标签
     *
     * @param id 主键
     */
    @Override
    public void removeTag(Long id) {
        tagRepository.deleteById(id);
    }
}
