package com.cijee.blog.service.impl;

import com.cijee.blog.exception.NotFoundException;
import com.cijee.blog.model.po.Type;
import com.cijee.blog.repository.TypeRepository;
import com.cijee.blog.service.TypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author cijee
 * @date 2020/6/28
 */
@Service
@Transactional
public class TypeServiceImpl implements TypeService {

    private TypeRepository typeRepository;

    public TypeServiceImpl(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    @Override
    public Type saveType(Type type) {
        return typeRepository.save(type);
    }

    @Override
    public Type getType(Long id) {
        return typeRepository.findById(id).get();
    }

    @Override
    public Type getTypeByName(String name) {
        Type type = typeRepository.findTypeByName(name);
        return type;
    }

    @Override
    public Page<Type> listType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }

    @Override
    public Type updateType(Long id, Type type) {
        Type t = typeRepository.findById(id).get();
        if (t == null) {
            throw new NotFoundException("不存在该类型");
        }
        BeanUtils.copyProperties(type, t);
        return typeRepository.save(t);
    }

    @Override
    public void removeType(Long id) {
        typeRepository.deleteById(id);
    }
}
