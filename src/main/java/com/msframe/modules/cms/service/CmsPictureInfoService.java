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
import com.msframe.modules.cms.entity.CmsPictureInfo;
import com.msframe.modules.cms.dao.CmsPictureInfoDao;
import org.springframework.web.multipart.MultipartFile;

/**
 * 图片信息Service
 *
 * @author lpz
 * @version 2018-11-14
 */
@Service
@Transactional(readOnly = true)
public class CmsPictureInfoService extends CrudService<CmsPictureInfoDao, CmsPictureInfo> {

    @Override
    public CmsPictureInfo get(String id) {
        return super.get(id);
    }

    @Override
    public List<CmsPictureInfo> findList(CmsPictureInfo cmsPictureInfo) {
        return super.findList(cmsPictureInfo);
    }

    @Override
    public Page<CmsPictureInfo> findPage(Page<CmsPictureInfo> page, CmsPictureInfo cmsPictureInfo) {
        return super.findPage(page, cmsPictureInfo);
    }

    @Transactional(readOnly = false)
    public void save(CmsPictureInfo cmsPictureInfo, MultipartFile file) {

        try {
            if (!file.isEmpty()) {
                String picPath = FileUploadUtils.uploadImageToPath(file, "cmsPicture");
                cmsPictureInfo.setPicUrl(picPath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.save(cmsPictureInfo);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(CmsPictureInfo cmsPictureInfo) {
        super.delete(cmsPictureInfo);
    }


}