/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.common.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.msframe.common.constant.Constant;
import com.msframe.modules.zms.entity.ZmsChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.msframe.common.persistence.CrudDao;
import com.msframe.common.persistence.DataEntity;
import com.msframe.common.persistence.Page;

/**
 * Service基类
 *
 * @author msframe
 * @version 2014-05-16
 */
@Transactional(readOnly = true)
public abstract class CrudService<D extends CrudDao<T>, T extends DataEntity<T>> extends BaseService {

    /**
     * 持久层对象
     */
    @Autowired
    protected D dao;

    /**
     * 获取单条数据
     *
     * @param id
     * @return
     */
    public T get(String id) {
        return dao.get(id);
    }

    /**
     * 获取单条数据
     *
     * @param entity
     * @return
     */
    public T get(T entity) {
        return dao.get(entity);
    }

    /**
     * 查询列表数据
     *
     * @param entity
     * @return
     */
    public List<T> findList(T entity) {
        return dao.findList(entity);
    }

    /**
     * 查询分页数据
     *
     * @param page   分页对象
     * @param entity
     * @return
     */
    public Page<T> findPage(Page<T> page, T entity) {
        entity.setPage(page);
        page.setList(dao.findList(entity));
        return page;
    }

    /**
     * 保存数据（插入或更新）
     *
     * @param entity
     */
    @Transactional(readOnly = false)
    public void save(T entity) {
        if (entity.getIsNewRecord()) {
            entity.preInsert();
            dao.insert(entity);
        } else {
            entity.preUpdate();
            dao.update(entity);
        }
    }

    /**
     * 删除数据
     *
     * @param entity
     */
    @Transactional(readOnly = false)
    public void delete(T entity) {
        dao.delete(entity);
    }


    /**
     * 删除全部数据
     *
     * @param entity
     */
    @Transactional(readOnly = false)
    public void deleteAll(Collection<T> entitys) {
        for (T entity : entitys) {
            dao.delete(entity);
        }
    }


    /**
     * 获取单条数据
     *
     * @param id
     * @return
     */
    public T findUniqueByProperty(String propertyName, Object value) {
        return dao.findUniqueByProperty(propertyName, value);
    }

    /**
     * @param id          排序对象id
     * @param currentSort 当前数据的排序值
     * @param operateType 排序类型（上移、下移）
     * @return
     * @description 排序通用方法
     * @author leon
     * @date 2018/12/04
     */
    @Transactional
    public boolean changeSort(String id, Long currentSort, String operateType,Map<String,Object> queryPreviousSortMap) {
        String dbname = new ZmsChannel().getDbName();

        try {
            queryPreviousSortMap.put("dbName", dbname);
            if (currentSort == null) {
                Long topSort = dao.selectTopSort(queryPreviousSortMap);
                Map<String, Object> updateSortMap = new HashMap<String, Object>();
                queryPreviousSortMap.put("id", id);
                queryPreviousSortMap.put("sort", topSort - 10);
                dao.updateSelfSort(queryPreviousSortMap);
            } else {
                queryPreviousSortMap.put("sort", currentSort);

                // 获取当前移动数据的上一个、下一个排序数据
                Map<String, Object> tempMap = null;
                if ("up".equals(operateType)) {
                    // 上移
                    tempMap = dao.selectPreviousSort(queryPreviousSortMap);
                } else if ("down".equals(operateType)) {
                    // 下移
                    tempMap = dao.selectNextSort(queryPreviousSortMap);
                }

                // 当前是第一个或最后一个数据
                 if (currentSort == tempMap.get("sort")) {
                    return true;
                }

                Map<String, Object> doChangeSortMap = new HashMap<String, Object>();
                doChangeSortMap.put("dbName", dbname);

                // 修改当前移动数据的排序值
                doChangeSortMap.put("id", id);
                doChangeSortMap.put("sort", tempMap.get("sort"));
                dao.updateSelfSort(doChangeSortMap);

                // 修改上一条、下一条数据的排序值
                doChangeSortMap.put("id", tempMap.get("id"));
                doChangeSortMap.put("sort", currentSort);
                dao.updateSelfSort(doChangeSortMap);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
