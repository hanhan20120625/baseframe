/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.cms.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.msframe.common.constant.Constant;
import com.msframe.common.utils.FileUploadUtils;
import com.msframe.modules.cms.dao.*;
import com.msframe.modules.cms.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msframe.common.persistence.Page;
import com.msframe.common.service.CrudService;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Multipart;

/**
 * 视频项目Service
 * @author lpz
 * @version 2018-11-14
 */
@Service
@Transactional(readOnly = true)
public class CmsProgramService extends CrudService<CmsProgramDao, CmsProgram> {

    @Autowired
    private CmsProgramDao cmsProgramDao;

    @Autowired
    private CmsProgramRegionDao cmsProgramRegionDao;

    @Autowired
    private CmsProgramTypeDao cmsProgramTypeDao;

    @Autowired
    private CmsProgramLangDao cmsProgramLangDao;

    @Autowired
    private CmsProgramCategoryDao cmsProgramCategoryDao;

    @Autowired
    private CmsCategoryService cmsCategoryService;

    @Override
    public CmsProgram get(String id) {
        CmsProgram cmsProgram = super.get(id);
        CmsProgramType cmsProgramType = new CmsProgramType();
        cmsProgramType.setProgramId(cmsProgram);
        cmsProgram.setCmsProgramTypeList(cmsProgramTypeDao.findList(cmsProgramType));

        // 获取地区数据
        CmsProgramRegion cmsProgramRegion = new CmsProgramRegion();
        cmsProgramRegion.setProgramId(cmsProgram);
        cmsProgram.setCmsProgramRegionList(cmsProgramRegionDao.findList(cmsProgramRegion));

        // 获取语言数据
        CmsProgramLang cmsProgramLang = new CmsProgramLang();
        cmsProgramLang.setProgramId(cmsProgram);
        cmsProgram.setCmsProgramLangList(cmsProgramLangDao.findList(cmsProgramLang));

        // 获取栏目数据
        CmsProgramCategory cmsProgramCategory = new CmsProgramCategory();
        cmsProgramCategory.setProgramId(cmsProgram);
        cmsProgram.setCmsProgramCategoryList(cmsProgramCategoryDao.findList(cmsProgramCategory));

        return cmsProgram;
    }

    @Override
    public List<CmsProgram> findList(CmsProgram cmsProgram) {
        return super.findList(cmsProgram);
    }

    @Override
    public Page<CmsProgram> findPage(Page<CmsProgram> page, CmsProgram cmsProgram) {

        Page<CmsProgram> cmsProgramPage = super.findPage(page, cmsProgram);
        List<CmsProgram> programList = cmsProgramPage.getList();

        for (CmsProgram program : programList) {
            // 获取栏目数据
            CmsProgramCategory cmsProgramCategory = new CmsProgramCategory();
            cmsProgramCategory.setProgramId(program);
            program.setCmsProgramCategoryList(cmsProgramCategoryDao.findList(cmsProgramCategory));
        }

        return cmsProgramPage;
    }

    @Transactional(readOnly = false)
    public void save(CmsProgram cmsProgram, MultipartFile[] files) {

        MultipartFile hBigPicFile = files[0];
        MultipartFile hSmallPicFile = files[1];
        MultipartFile vBigPicFile = files[2];
        MultipartFile vSmallPicFile = files[3];
        MultipartFile squarePicFile = files[4];
        MultipartFile picUrlFile = files[5];

        try {
            if (!hBigPicFile.isEmpty()) {
                String hBigPicPath = FileUploadUtils.uploadImageToPath(hBigPicFile, "cmsProgramHBigPic");
                cmsProgram.sethBigPic(hBigPicPath);
            }

            if (!hSmallPicFile.isEmpty()) {
                String hSmallPicPath = FileUploadUtils.uploadImageToPath(hSmallPicFile, "cmsProgramHSmallPic");
                cmsProgram.sethSmallPic(hSmallPicPath);
            }

            if (!vBigPicFile.isEmpty()) {
                String vBigPicPath = FileUploadUtils.uploadImageToPath(vBigPicFile, "cmsProgramVBigPic");
                cmsProgram.setvBigPic(vBigPicPath);
            }

            if (!vSmallPicFile.isEmpty()) {
                String vSmallPicPath = FileUploadUtils.uploadImageToPath(vSmallPicFile, "cmsProgramVSmallPic");
                cmsProgram.setvSmallPic(vSmallPicPath);
            }

            if (!squarePicFile.isEmpty()) {
                String squarePicPath = FileUploadUtils.uploadImageToPath(squarePicFile, "cmsProgramSquarePic");
                cmsProgram.setSquarePic(squarePicPath);
            }

            if (!picUrlFile.isEmpty()) {
                String picUrlPath = FileUploadUtils.uploadImageToPath(picUrlFile, "cmsProgramPicUrl");
                cmsProgram.setPicUrl(picUrlPath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        super.save(cmsProgram);
        saveProgramType(cmsProgram);
        saveProgramLang(cmsProgram);
        saveProgramRegion(cmsProgram);
        saveProgramCategory(cmsProgram);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(CmsProgram cmsProgram) {
        super.delete(cmsProgram);
    }

    /**
     * @param cmsProgram
     * @description 更新影片类型信息
     * @author leon
     * @date 2018/11/20
     */
    @Transactional(readOnly = false)
    public void saveProgramType(CmsProgram cmsProgram) {
        // 原始影片类型数据集合（即需要删除的源数据）
        List<CmsProgramType> cmsProgramTypeList = cmsProgram.getCmsProgramTypeList();

        // 修改后的影片类型数据集合（需要保存的新数据）
        List<String> cmsTypeSaveList = cmsProgram.getCmsTypeSaveList();

        Iterator<CmsProgramType> cmsProgramTypeIterator = cmsProgramTypeList.iterator();
        Iterator<String> cmsTypeSaveIterator = cmsTypeSaveList.iterator();
        while (cmsProgramTypeIterator.hasNext()) {
            CmsProgramType next = cmsProgramTypeIterator.next();
            if (next != null) {
                String id = next.getTypeId().getId();
                while (cmsTypeSaveIterator.hasNext()) {
                    String saveId = cmsTypeSaveIterator.next();
                    if (saveId.equals(id)) {
                        cmsProgramTypeIterator.remove();
                        cmsTypeSaveIterator.remove();
                    }
                }
                cmsTypeSaveIterator = cmsTypeSaveList.iterator();
            }
        }

        if (cmsProgramTypeList != null && cmsProgramTypeList.size() > 0) {
            // 执行删除
            for (CmsProgramType cmsProgramType : cmsProgramTypeList) {
                cmsProgramTypeDao.deleteByProgramIdAndTypeId(cmsProgramType);
            }
        }

        if (cmsTypeSaveList != null && cmsTypeSaveList.size() > 0) {
            // 执行保存
            for (String typeId : cmsTypeSaveList) {
                CmsProgramType cmsProgramType = new CmsProgramType();
                cmsProgramType.setProgramId(cmsProgram);
                cmsProgramType.setTypeId(new CmsType(typeId));
                cmsProgramType.preInsert();
                cmsProgramTypeDao.insert(cmsProgramType);
            }
        }
    }

    /**
     * @param cmsProgram
     * @description 保存影片语言相关信息
     * @author leon
     * @date 2018/11/20
     */
    public void saveProgramLang(CmsProgram cmsProgram) {
        // 原始影片类型数据集合（即需要删除的源数据）
        List<CmsProgramLang> cmsProgramLangList = cmsProgram.getCmsProgramLangList();

        // 修改后的影片类型数据集合（需要保存的新数据）
        List<String> cmsLangSaveList = cmsProgram.getCmsLangSaveList();

        Iterator<CmsProgramLang> cmsProgramLangIterator = cmsProgramLangList.iterator();
        Iterator<String> cmsLangSaveIterator = cmsLangSaveList.iterator();
        while (cmsProgramLangIterator.hasNext()) {
            CmsProgramLang next = cmsProgramLangIterator.next();
            if (next != null) {
                String id = next.getLangId().getId();
                while (cmsLangSaveIterator.hasNext()) {
                    String saveId = cmsLangSaveIterator.next();
                    if (saveId.equals(id)) {
                        cmsProgramLangIterator.remove();
                        cmsLangSaveIterator.remove();
                    }
                }
                cmsLangSaveIterator = cmsLangSaveList.iterator();
            }
        }

        if (cmsProgramLangList != null && cmsProgramLangList.size() > 0) {
            // 执行删除
            for (CmsProgramLang cmsProgramLang : cmsProgramLangList) {
                cmsProgramLangDao.deleteByProgramIdAndLangId(cmsProgramLang);
            }
        }

        if (cmsLangSaveList != null && cmsLangSaveList.size() > 0) {
            // 执行保存
            for (String langId : cmsLangSaveList) {
                CmsProgramLang cmsProgramLang = new CmsProgramLang();
                cmsProgramLang.setProgramId(cmsProgram);
                cmsProgramLang.setLangId(new CmsLang(langId));
                cmsProgramLang.preInsert();
                cmsProgramLangDao.insert(cmsProgramLang);
            }
        }
    }

    /**
     * @param cmsProgram
     * @description 保存影片地区相关信息
     * @author leon
     * @date 2018/11/20
     */
    public void saveProgramRegion(CmsProgram cmsProgram) {

        List<CmsProgramRegion> cmsProgramRegionList = cmsProgram.getCmsProgramRegionList();
        List<String> cmsRegionSaveList = cmsProgram.getCmsRegionSaveList();

        Iterator<CmsProgramRegion> cmsProgramRegionIterator = cmsProgramRegionList.iterator();
        Iterator<String> cmsRegionSaveIterator = cmsRegionSaveList.iterator();

        while (cmsProgramRegionIterator.hasNext()) {
            CmsProgramRegion next = cmsProgramRegionIterator.next();
            if (next != null) {
                String id = next.getRegion().getId();
                while (cmsRegionSaveIterator.hasNext()) {
                    String saveId = cmsRegionSaveIterator.next();
                    if (saveId.equals(id)) {
                        cmsProgramRegionIterator.remove();
                        cmsRegionSaveIterator.remove();
                    }
                }
                cmsRegionSaveIterator = cmsRegionSaveList.iterator();
            }
        }

        if (cmsProgramRegionList != null && cmsProgramRegionList.size() > 0) {
            for (CmsProgramRegion cmsProgramRegion : cmsProgramRegionList) {
                cmsProgramRegionDao.deleteByProgramIdAndRegionId(cmsProgramRegion);
            }
        }

        if (cmsRegionSaveList != null && cmsRegionSaveList.size() > 0) {
            for (String regionId : cmsRegionSaveList) {
                CmsProgramRegion cmsProgramRegion = new CmsProgramRegion();
                cmsProgramRegion.setProgramId(cmsProgram);
                cmsProgramRegion.setRegion(new CmsRegion(regionId));
                cmsProgramRegion.preInsert();
                cmsProgramRegionDao.insert(cmsProgramRegion);
            }
        }
    }

    /**
     * @param cmsProgram
     * @description 保存影片栏目信息
     * @author leon
     * @date 2018/11/21
     */
    public void saveProgramCategory(CmsProgram cmsProgram) {
        List<CmsProgramCategory> cmsProgramCategoryList = cmsProgram.getCmsProgramCategoryList();
        List<String> cmsCategorySaveList = cmsProgram.getCmsCategorySaveList();

        Iterator<CmsProgramCategory> cmsProgramCategoryIterator = cmsProgramCategoryList.iterator();
        Iterator<String> cmsCategorySaveIterator = cmsCategorySaveList.iterator();

        while (cmsProgramCategoryIterator.hasNext()) {
            CmsProgramCategory next = cmsProgramCategoryIterator.next();
            if (next != null) {
                String id = next.getCategoryId().getId();
                while (cmsCategorySaveIterator.hasNext()) {
                    String saveId = cmsCategorySaveIterator.next();
                    if (saveId.equals(id)) {
                        cmsProgramCategoryIterator.remove();
                        cmsCategorySaveIterator.remove();
                    }
                }
                cmsCategorySaveIterator = cmsCategorySaveList.iterator();
            }
        }

        if (cmsProgramCategoryList != null && cmsProgramCategoryList.size() > 0) {
            for (CmsProgramCategory cmsProgramCategory : cmsProgramCategoryList) {
                cmsProgramCategoryDao.deleteByProgramIdAndCategoryId(cmsProgramCategory);
            }
        }

        if (cmsCategorySaveList != null && cmsCategorySaveList.size() > 0) {
            for (String categoryId : cmsCategorySaveList) {
                CmsProgramCategory cmsProgramCategory = new CmsProgramCategory();
                cmsProgramCategory.setProgramId(cmsProgram);
                cmsProgramCategory.setCategoryId(new CmsCategory(categoryId));
                cmsProgramCategory.preInsert();
                cmsProgramCategoryDao.insert(cmsProgramCategory);
            }
        }
    }
}