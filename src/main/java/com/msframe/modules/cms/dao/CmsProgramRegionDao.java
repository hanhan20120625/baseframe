/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.cms.dao;

import java.util.List;

import com.msframe.common.persistence.CrudDao;
import com.msframe.common.persistence.annotation.MyBatisDao;
import com.msframe.modules.cms.entity.CmsProgramRegion;

/**
 * 视频区域关联DAO接口
 *
 * @author lpz
 * @version 2018-11-14
 */
@MyBatisDao
public interface CmsProgramRegionDao extends CrudDao<CmsProgramRegion> {

    /**
     * @param cmsProgramRegion
     * @description 根据ProgramId修改对应的Region信息
     * @author leon
     * @date 2018/11/20
     */
    public void updateByProgramId(CmsProgramRegion cmsProgramRegion);

    /**
     * @param cmsProgramRegion
     * @description 根据ProgramId、RegionId 删除对应数据
     * @author leon
     * @date 2018/11/20
     */
    void deleteByProgramIdAndRegionId(CmsProgramRegion cmsProgramRegion);
}