package com.cijee.blog.service;

import com.cijee.blog.model.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author cijee
 * @date 2020/6/28
 */
public interface TypeService {

    /**
     * 保存分类实体
     *
     * @param type 分类实体
     * @return 返回保存的实体
     */
    Type saveType(Type type);

    /**
     * 获取分类
     *
     * @param id 主键
     * @return 分类对象
     */
    Type getType(Long id);

    /**
     * 根据名称获取分类
     *
     * @param name 分类名称
     * @return 分类对象
     */
    Type getTypeByName(String name);

    /**
     * 获取所有分类
     *
     * @param pageable
     * @return 分页结果
     */
    Page<Type> listType(Pageable pageable);

    /**
     * 根据id更新分类
     *
     * @param id 主键
     * @param type 分类实体
     * @return 分类对象
     */
    Type updateType(Long id, Type type);

    /**
     * 根据id删除分类
     *
     * @param id 主键
     */
    void removeType(Long id);
}
