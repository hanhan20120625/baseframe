/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.app.service;

import java.io.IOException;
import java.util.List;

import com.msframe.common.utils.FileUploadUtils;
import com.msframe.modules.cms.entity.CmsCategory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msframe.modules.cms.entity.CmsProgram;
import com.msframe.common.persistence.Page;
import com.msframe.common.service.CrudService;
import com.msframe.modules.app.entity.AppRecommend;
import com.msframe.modules.app.dao.AppRecommendDao;
import org.springframework.web.multipart.MultipartFile;

/**
 * banner推荐Service
 *
 * @author leon
 * @version 2018-11-28
 */
@Service
@Transactional(readOnly = true)
public class AppRecommendService extends CrudService<AppRecommendDao, AppRecommend> {

    @Override
    public AppRecommend get(String id) {
        return super.get(id);
    }

    @Override
    public List<AppRecommend> findList(AppRecommend appRecommend) {
        return super.findList(appRecommend);
    }

    @Override
    public Page<AppRecommend> findPage(Page<AppRecommend> page, AppRecommend appRecommend) {
        return super.findPage(page, appRecommend);
    }

    @Transactional(readOnly = false)
    public void save(AppRecommend appRecommend, MultipartFile file) {

        try {
            if (!file.isEmpty()) {
                String picPath = FileUploadUtils.uploadImageToPath(file, "appRecommend");
                appRecommend.setPicurl(picPath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.save(appRecommend);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(AppRecommend appRecommend) {
        super.delete(appRecommend);
    }

    public Page<CmsProgram> findPageByprogramId(Page<CmsProgram> page, CmsProgram programId) {
        //programId.setPage(page);
        page.setList(dao.findListByprogramId(programId));
        return page;
    }

    public Page<CmsCategory> findPageByCategoryId(Page<CmsCategory> page, CmsCategory cmsCategory) {
        page.setList(dao.findListByCategoryId(cmsCategory));
        return page;
    }


}