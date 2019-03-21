/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.common.persistence;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * DAO支持类实现
 *
 * @param <T>
 * @author msframe
 * @version 2014-05-16
 */
public interface CrudDao<T> extends BaseDao {

    /**
     * 获取单条数据
     *
     * @param id
     * @return
     */
    public T get(String id);

    /**
     * 获取单条数据
     *
     * @param entity
     * @return
     */
    public T get(T entity);

    /**
     * 根据实体名称和字段名称和字段值获取唯一记录
     *
     * @param <T>
     * @param entityClass
     * @param propertyName
     * @param value
     * @return
     */
    public T findUniqueByProperty(@Param(value = "propertyName") String propertyName, @Param(value = "value") Object value);


    /**
     * 查询数据列表，如果需要分页，请设置分页对象，如：entity.setPage(new Page<T>());
     *
     * @param entity
     * @return
     */
    public List<T> findList(T entity);

    /**
     * 查询所有数据列表
     *
     * @param entity
     * @return
     */
    public List<T> findAllList(T entity);

    /**
     * 查询所有数据列表
     *
     * @return
     * @see public List<T> findAllList(T entity)
     */
    @Deprecated
    public List<T> findAllList();

    /**
     * 插入数据
     *
     * @param entity
     * @return
     */
    public int insert(T entity);

    /**
     * 更新数据
     *
     * @param entity
     * @return
     */
    public int update(T entity);

    /**
     * 删除数据（物理删除，从数据库中彻底删除）
     *
     * @param id
     * @return
     * @see public int delete(T entity)
     */
    @Deprecated
    public int delete(String id);

    /**
     * 删除数据（逻辑删除，更新del_flag字段为1,在表包含字段del_flag时，可以调用此方法，将数据隐藏）
     *
     * @param id
     * @return
     * @see public int delete(T entity)
     */
    @Deprecated
    public int deleteByLogic(String id);

    /**
     * 删除数据（物理删除，从数据库中彻底删除）
     *
     * @param entity
     * @return
     */
    public int delete(T entity);

    /**
     * 删除数据（逻辑删除，更新del_flag字段为1,在表包含字段del_flag时，可以调用此方法，将数据隐藏）
     *
     * @param entity
     * @return
     */
    public int deleteByLogic(T entity);

    /**
     * @return
     * @description 获取第一条数据的排序值
     * @author leon
     * @date 2018/12/04
     */
    public Long selectTopSort(Map<String,Object> map);

    /**
     * @param map
     * @return
     * @description 获取当前数据的上一条数据的排序值
     * @author leon
     * @date 2018/12/04
     */
    public Map<String, Object> selectPreviousSort(Map<String, Object> map);

    /**
     * @param map
     * @return
     * @description 获取当前数据的下一条数据的排序值
     * @author leon
     * @date 2018/12/04
     */
    public Map<String, Object> selectNextSort(Map<String, Object> map);

    /**
     * @param map
     * @description 修改当前数据的排序值
     * @author leon
     * @date 2018/12/04
     */
    public void updateSelfSort(Map<String, Object> map);

    /**
     * @param map
     * @description 修改当前数据的上一条、下一条的排序值
     * @author leon
     * @date 2018/12/04
     */
    public void updateOtherSort(Map<String, Object> map);

}