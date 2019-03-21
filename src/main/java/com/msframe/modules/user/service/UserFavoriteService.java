/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.user.service;

import java.util.List;

import com.msframe.modules.cms.entity.CmsMovie;
import com.msframe.modules.cms.entity.CmsProgram;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msframe.common.persistence.Page;
import com.msframe.common.service.CrudService;
import com.msframe.modules.user.entity.UserFavorite;
import com.msframe.modules.user.dao.UserFavoriteDao;

/**
 * 影片收藏信息Service
 *
 * @author jjj
 * @version 2018-11-14
 */
@Service
@Transactional(readOnly = true)
public class UserFavoriteService extends CrudService<UserFavoriteDao, UserFavorite> {

    @Override
    public UserFavorite get(String id) {
        return super.get(id);
    }

    @Override
    public List<UserFavorite> findList(UserFavorite userFavorite) {
        return super.findList(userFavorite);
    }

    @Override
    public Page<UserFavorite> findPage(Page<UserFavorite> page, UserFavorite userFavorite) {
        return super.findPage(page, userFavorite);
    }

    @Override
    @Transactional(readOnly = false)
    public void save(UserFavorite userFavorite) {
        super.save(userFavorite);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(UserFavorite userFavorite) {
        super.delete(userFavorite);
    }

    public Page<CmsProgram> findPageByProgramId(Page<CmsProgram> page, CmsProgram cmsProgram) {
        page.setList(dao.findListByProgramId(cmsProgram));
        return page;
    }

    public Page<CmsMovie> findPageByMovieId(Page<CmsMovie> page, CmsMovie cmsMovie) {
        page.setList(dao.findListByMovieId(cmsMovie));
        return page;
    }


}