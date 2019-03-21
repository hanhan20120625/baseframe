/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.cms.service;

import java.io.IOException;
import java.util.List;

import com.msframe.common.utils.FileUploadUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msframe.common.persistence.Page;
import com.msframe.common.service.CrudService;
import com.msframe.modules.cms.entity.CmsMovie;
import com.msframe.modules.cms.dao.CmsMovieDao;
import org.springframework.web.multipart.MultipartFile;

/**
 * 视频Service
 *
 * @author lpz
 * @version 2018-11-14
 */
@Service
@Transactional(readOnly = true)
public class CmsMovieService extends CrudService<CmsMovieDao, CmsMovie> {

    @Override
    public CmsMovie get(String id) {
        return super.get(id);
    }

    @Override
    public List<CmsMovie> findList(CmsMovie cmsMovie) {
        return super.findList(cmsMovie);
    }

    @Override
    public Page<CmsMovie> findPage(Page<CmsMovie> page, CmsMovie cmsMovie) {
        return super.findPage(page, cmsMovie);
    }

    @Transactional(readOnly = false)
    public void save(CmsMovie cmsMovie, MultipartFile file) {

        try {
            if (!file.isEmpty()) {
                String picPath = FileUploadUtils.uploadImageToPath(file, "cmsMovie");
                cmsMovie.setPicurl(picPath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        super.save(cmsMovie);

    }

    @Override
    @Transactional(readOnly = false)
    public void delete(CmsMovie cmsMovie) {
        super.delete(cmsMovie);
    }


}