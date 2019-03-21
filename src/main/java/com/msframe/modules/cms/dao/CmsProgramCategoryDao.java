/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.cms.dao;

import java.util.List;

import com.msframe.common.persistence.CrudDao;
import com.msframe.common.persistence.annotation.MyBatisDao;
import com.msframe.modules.cms.entity.CmsProgramCategory;

/**
 * 影片与栏目关联DAO接口
 *
 * @author lpz
 * @version 2018-11-14
 */
@MyBatisDao
public interface CmsProgramCategoryDao extends CrudDao<CmsProgramCategory> {

    /**
     * @param cmsProgramCategory
     * @description 根据ProgramId、CategoryId删除对应数据
     * @author leon
     * @date 2018/11/21
     */
    public void deleteByProgramIdAndCategoryId(CmsProgramCategory cmsProgramCategory);
}