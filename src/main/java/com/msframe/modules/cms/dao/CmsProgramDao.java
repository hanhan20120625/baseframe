/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.cms.dao;

import java.util.List;
import java.util.Map;

import com.msframe.common.persistence.CrudDao;
import com.msframe.common.persistence.annotation.MyBatisDao;
import com.msframe.modules.cms.entity.CmsProgram;

/**
 * 视频项目DAO接口
 *
 * @author lpz
 * @version 2018-11-14cm
 */
@MyBatisDao
public interface CmsProgramDao extends CrudDao<CmsProgram> {

}