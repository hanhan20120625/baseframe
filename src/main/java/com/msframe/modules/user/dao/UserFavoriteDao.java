/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.user.dao;

import java.util.List;

import com.msframe.common.persistence.CrudDao;
import com.msframe.common.persistence.annotation.MyBatisDao;
import com.msframe.modules.cms.entity.CmsMovie;
import com.msframe.modules.cms.entity.CmsProgram;
import com.msframe.modules.user.entity.UserFavorite;

/**
 * 影片收藏信息DAO接口
 *
 * @author jjj
 * @version 2018-11-14
 */
@MyBatisDao
public interface UserFavoriteDao extends CrudDao<UserFavorite> {

    /**
     * @param cmsProgram
     * @return
     * @description 获取影片列表（选择影片功能）
     * @author leon
     * @date 2018/11/29
     */
    public List<CmsProgram> findListByProgramId(CmsProgram cmsProgram);

    /**
     * @param cmsMovie
     * @return
     * @description 获取所有单集影片列表（选择单集影片功能）
     * @author leon
     * @date 2018/11/29
     */
    public List<CmsMovie> findListByMovieId(CmsMovie cmsMovie);

}