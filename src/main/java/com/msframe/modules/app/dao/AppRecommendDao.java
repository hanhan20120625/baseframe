/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.app.dao;

import java.util.List;

import com.msframe.modules.cms.entity.CmsCategory;
import com.msframe.modules.cms.entity.CmsProgram;

import java.util.List;

import com.msframe.common.persistence.CrudDao;
import com.msframe.common.persistence.annotation.MyBatisDao;
import com.msframe.modules.app.entity.AppRecommend;

/**
 * banner推荐DAO接口
 *
 * @author leon
 * @version 2018-11-28
 */
@MyBatisDao
public interface AppRecommendDao extends CrudDao<AppRecommend> {

    public List<CmsProgram> findListByprogramId(CmsProgram programId);

    public List<CmsCategory> findListByCategoryId(CmsCategory cmsCategory);

}