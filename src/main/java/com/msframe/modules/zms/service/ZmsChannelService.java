/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.zms.service;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import com.msframe.common.utils.FileUploadUtils;
import com.msframe.modules.zms.dao.ZmsCategoryDao;
import com.msframe.modules.zms.dao.ZmsChannelCategoryDao;
import com.msframe.modules.zms.entity.ZmsCategory;
import com.msframe.modules.zms.entity.ZmsChannelCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msframe.common.persistence.Page;
import com.msframe.common.service.CrudService;
import com.msframe.modules.zms.entity.ZmsChannel;
import com.msframe.modules.zms.dao.ZmsChannelDao;
import org.springframework.web.multipart.MultipartFile;

/**
 * 频道信息管理Service
 *
 * @author wlh
 * @version 2018-11-13
 */
@Service
@Transactional(readOnly = true)
public class ZmsChannelService extends CrudService<ZmsChannelDao, ZmsChannel> {

    @Autowired
    private ZmsChannelCategoryDao zmsChannelCategoryDao;


    @Override
    public ZmsChannel get(String id) {

        ZmsChannel zmsChannel = super.get(id);

        /**
         * 获取频道类别数据
         */
        ZmsChannelCategory zmsChannelCategory = new ZmsChannelCategory();
        zmsChannelCategory.setChannelId(zmsChannel);
        zmsChannel.setZmsChannelCategoryList(zmsChannelCategoryDao.findList(zmsChannelCategory));

        return zmsChannel;
    }

    @Override
    public List<ZmsChannel> findList(ZmsChannel zmsChannel) {
        return super.findList(zmsChannel);
    }

    @Override
    public Page<ZmsChannel> findPage(Page<ZmsChannel> page, ZmsChannel zmsChannel) {
        return super.findPage(page, zmsChannel);
    }

    @Transactional(readOnly = false)
    public void save(ZmsChannel zmsChannel, MultipartFile[] files) {

        // 图片上传数据
        MultipartFile bigPicFile = files[0];
        MultipartFile smallPicFile = files[1];

        try {
            if (!bigPicFile.isEmpty()) {
                String bigPicPath = FileUploadUtils.uploadImageToPath(bigPicFile, "channelBigPic");
                zmsChannel.setBigPicurl(bigPicPath);
            }

            if (!smallPicFile.isEmpty()) {
                String smallPicPath = FileUploadUtils.uploadImageToPath(smallPicFile, "channelSmallPic");
                zmsChannel.setPicurl(smallPicPath);
            }

            super.save(zmsChannel);
            saveChannelCategory(zmsChannel);

        } catch (IOException e) {

        }
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(ZmsChannel zmsChannel) {
        super.delete(zmsChannel);
    }

    /**
     * @param zmsChannel
     * @description 更新频道所属类别数据
     */
    @Transactional(readOnly = false)
    public void saveChannelCategory(ZmsChannel zmsChannel) {
        List<ZmsChannelCategory> zmsChannelCategoryList = zmsChannel.getZmsChannelCategoryList();

        List<String> zmsCategorySaveList = zmsChannel.getZmsCategorySaveList();

        Iterator<ZmsChannelCategory> zmsChannelCategoryIterator = zmsChannelCategoryList.iterator();
        Iterator<String> zmsCategorySaveIterator = zmsCategorySaveList.iterator();

        while (zmsChannelCategoryIterator.hasNext()) {
            ZmsChannelCategory next = zmsChannelCategoryIterator.next();
            if (next != null) {
                String id = next.getCategoryId().getId();
                while (zmsCategorySaveIterator.hasNext()) {
                    String saveId = zmsCategorySaveIterator.next();
                    if (saveId.equals(id)) {
                        zmsChannelCategoryIterator.remove();
                        zmsCategorySaveIterator.remove();
                    }
                }
                zmsCategorySaveIterator = zmsCategorySaveList.iterator();
            }
        }

        if (zmsChannelCategoryList != null && zmsChannelCategoryList.size() > 0) {
            for (ZmsChannelCategory zmsChannelCategory : zmsChannelCategoryList) {
                zmsChannelCategoryDao.deleteByChannelIdAndCategoryId(zmsChannelCategory);
            }
        }

        if (zmsCategorySaveList != null && zmsCategorySaveList.size() > 0) {
            for (String categoryId : zmsCategorySaveList) {
                ZmsChannelCategory zmsChannelCategory = new ZmsChannelCategory();
                zmsChannelCategory.setChannelId(zmsChannel);
                zmsChannelCategory.setCategoryId(new ZmsCategory(categoryId));
                zmsChannelCategory.preInsert();
                zmsChannelCategoryDao.insert(zmsChannelCategory);
            }
        }
    }


}