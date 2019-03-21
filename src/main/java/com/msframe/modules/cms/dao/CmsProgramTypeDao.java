/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.cms.dao;

import java.util.List;

import com.msframe.common.persistence.CrudDao;
import com.msframe.common.persistence.annotation.MyBatisDao;
import com.msframe.modules.cms.entity.CmsProgramType;

/**
 * 影片关联类别DAO接口
 *
 * @author lpz
 * @version 2018-11-14
 */
@MyBatisDao
public interface CmsProgramTypeDao extends CrudDao<CmsProgramType> {

    /**
     * @param cmsProgramType
     * @author leon
     * @date 2018/11/20
     * @description 根据ProgramId更新对应的LangId
     */
    public void updateByProgramId(CmsProgramType cmsProgramType);

    /**
     * @param cmsProgramType
     * @description 根据ProgramId、TypeId删除相应记录
     * @author leon
     * @date 2018/11/20
     */
    public void deleteByProgramIdAndTypeId(CmsProgramType cmsProgramType);
}