/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.cms.service;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.msframe.common.constant.Constant;
import com.msframe.common.utils.FileUploadUtils;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msframe.common.service.TreeService;
import com.msframe.common.utils.StringUtils;
import com.msframe.modules.cms.entity.CmsCategory;
import com.msframe.modules.cms.dao.CmsCategoryDao;
import org.springframework.web.multipart.MultipartFile;
import sun.rmi.rmic.Constants;

/**
 * 栏目Service
 *
 * @author lpz
 * @version 2018-11-16
 */
@Service
@Transactional(readOnly = true)
public class CmsCategoryService extends TreeService<CmsCategoryDao, CmsCategory> {

    @Override
    public CmsCategory get(String id) {
        return super.get(id);
    }

    @Override
    public List<CmsCategory> findList(CmsCategory cmsCategory) {
        if (StringUtils.isNotBlank(cmsCategory.getParentIds())) {
            cmsCategory.setParentIds("," + cmsCategory.getParentIds() + ",");
        }
        return super.findList(cmsCategory);
    }

    @Transactional(readOnly = false)
    public void save(CmsCategory cmsCategory, MultipartFile[] files) {

        // 图片上传数据
        MultipartFile iconFile = files[0];
        MultipartFile posterFile = files[1];
        MultipartFile bgImageFile = files[2];

        try {

            if (!iconFile.isEmpty()) {
                String iconPath = FileUploadUtils.uploadImageToPath(iconFile, Constant.CMS_CATEGORY_FILE_TYPE_ICON);
                cmsCategory.setIcon(iconPath);

            }

            if (!posterFile.isEmpty()) {
                String posterPath = FileUploadUtils.uploadImageToPath(posterFile, Constant.CMS_CATEGORY_FILE_TYPE_POSTER);
                cmsCategory.setPoster(posterPath);
            }

            if (!bgImageFile.isEmpty()) {
                String bgImagePath = FileUploadUtils.uploadImageToPath(bgImageFile, Constant.CMS_CATEGORY_FILE_TYPE_BGIMAGE);
                cmsCategory.setBgImage(bgImagePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        super.save(cmsCategory);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(CmsCategory cmsCategory) {
        super.delete(cmsCategory);
    }

    /**
     * @param file
     * @param type
     * @return
     * @description 上传文件 返回地址
     */
    public String uploadFile(MultipartFile file, String type) throws IOException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String newName = simpleDateFormat.format(new Date()) + System.currentTimeMillis();

        String dbSavePath = "";

        if (!file.isEmpty()) {
            // 原始文件名
            String oldName = file.getOriginalFilename();
            // 后缀名
            String suffixName = oldName.substring(oldName.indexOf("."), oldName.length());
            String path = Constant.FILE_UPLOAD_BASE_PATH;

            if (Constant.CMS_CATEGORY_FILE_TYPE_ICON.equals(type)) {
                String resultPath = File.separator + "icon" + File.separator + newName + suffixName;
                dbSavePath = resultPath;
                path += resultPath;
            } else if (Constant.CMS_CATEGORY_FILE_TYPE_BGIMAGE.equals(type)) {
                String resultPath = File.separator + "bgImage" + File.separator + newName + suffixName;
                dbSavePath = resultPath;
                path += resultPath;
            } else if (Constant.CMS_CATEGORY_FILE_TYPE_POSTER.equals(type)) {
                String resultPath = File.separator + "poster" + File.separator + newName + suffixName;
                dbSavePath = resultPath;
                path += resultPath;
            }

            File uploadFile = new File(path);

            // 判断文件夹是否存在，如果不存在，则创建文件夹
            if (!uploadFile.getParentFile().exists()) {
                uploadFile.getParentFile().mkdirs();
            }
            file.transferTo(uploadFile);
        }

        return dbSavePath;
    }

}