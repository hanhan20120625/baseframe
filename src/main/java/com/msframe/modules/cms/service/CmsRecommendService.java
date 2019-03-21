/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.cms.service;

import java.io.IOException;
import java.util.List;

import com.msframe.common.utils.FileUploadUtils;
import com.msframe.modules.cms.entity.CmsCategory;
import com.msframe.modules.cms.entity.CmsType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msframe.modules.cms.entity.CmsProgram;
import com.msframe.common.persistence.Page;
import com.msframe.common.service.CrudService;
import com.msframe.modules.cms.entity.CmsRecommend;
import com.msframe.modules.cms.dao.CmsRecommendDao;
import org.springframework.web.multipart.MultipartFile;

/**
 * 影片推荐Service
 *
 * @author leon
 * @version 2018-11-28
 */
@Service
@Transactional(readOnly = true)
public class CmsRecommendService extends CrudService<CmsRecommendDao, CmsRecommend> {

    @Override
    public CmsRecommend get(String id) {
        return super.get(id);
    }

    @Override
    public List<CmsRecommend> findList(CmsRecommend cmsRecommend) {
        return super.findList(cmsRecommend);
    }

    @Override
    public Page<CmsRecommend> findPage(Page<CmsRecommend> page, CmsRecommend cmsRecommend) {
        return super.findPage(page, cmsRecommend);
    }

    @Transactional(readOnly = false)
    public void save(CmsRecommend cmsRecommend, MultipartFile file) {
        try {
            if (!file.isEmpty()) {
                String picPath = FileUploadUtils.uploadImageToPath(file, "cmsRecommend");
                cmsRecommend.setPicurl(picPath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.save(cmsRecommend);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(CmsRecommend cmsRecommend) {
        super.delete(cmsRecommend);
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

    public Page<CmsType> findPageByTypeId(Page<CmsType> page, CmsType cmsType) {
        page.setList(dao.findListByTypeId(cmsType));
        return page;
    }


}