package com.cijee.blog.service.impl;

import com.cijee.blog.exception.NotFoundException;
import com.cijee.blog.model.po.Type;
import com.cijee.blog.repository.TypeRepository;
import com.cijee.blog.service.TypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author cijee
 * @date 2020/6/28
 */
@Service
@Transactional
public class TypeServiceImpl implements TypeService {

    private final TypeRepository typeRepository;

    public TypeServiceImpl(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    /**
     * 保存分类实体
     *
     * @param type 分类实体
     * @return 返回保存的实体
     */
    @Override
    public Type saveType(Type type) {
        return typeRepository.save(type);
    }

    /**
     * 获取分类
     *
     * @param id 主键
     * @return 分类对象
     */
    @Override
    public Type getType(Long id) {
        return typeRepository.findById(id).orElse(null);
    }

    /**
     * 根据名称获取分类
     *
     * @param name 分类名称
     * @return 分类对象
     */
    @Override
    public Type getTypeByName(String name) {
        Type type = typeRepository.findTypeByName(name);
        return type;
    }

    /**
     * 获取所有分类
     *
     * @param pageable
     * @return 分页结果
     */
    @Override
    public Page<Type> listType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }

    /**
     * 返回所有分类对象
     *
     * @return 分类集合
     */
    @Override
    public List<Type> listType() {
        return typeRepository.findAll();
    }

    /**
     * 返回前n条热门分类
     * ASC: 分类下博客的数量
     *
     * @return 分类集合
     */
    @Override
    public List<Type> listTypeTop(Integer topSize) {
        Sort sort = Sort.by(Sort.Direction.DESC, "blogs.size");
        Pageable pageable = PageRequest.of(0, topSize, sort);
        return typeRepository.findTop(pageable);
    }

    /**
     * 根据id更新分类
     *
     * @param id 主键
     * @param type 分类实体
     * @return 分类对象
     */
    @Override
    public Type updateType(Long id, Type type) {
        Type t = typeRepository.findById(id).orElse(null);
        if (t == null) {
            throw new NotFoundException("该分类不存在");
        }
        BeanUtils.copyProperties(type, t);
        return typeRepository.save(t);
    }

    /**
     * 根据id删除分类
     *
     * @param id 主键
     */
    @Override
    public void removeType(Long id) {
        typeRepository.deleteById(id);
    }

}
