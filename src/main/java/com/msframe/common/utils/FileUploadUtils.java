package com.msframe.common.utils;

import com.msframe.common.constant.Constant;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author leon
 * @version 1.0.0
 * @description 文件上传工具类
 * @date 2018/11/26 5:41 PM
 */
public class FileUploadUtils {

    /**
     * @param file
     * @param path
     * @return
     * @description 上传图片到指定路径
     */
    public static String uploadImageToPath(MultipartFile file, String path) throws IOException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String newName = simpleDateFormat.format(new Date());

        // 返回路径 用于保存到库
        String dbSavePath = "";

        // 判断需要上传文件是否为空
        if (!file.isEmpty()) {
            String oldName = file.getOriginalFilename();

            // 获取后缀名
            String fileSuffixName = oldName.substring(oldName.indexOf('.'), oldName.length());

            if (StringUtils.isNotBlank(path)) {
                dbSavePath = Constant.USERFILES_BASE_URL + File.separator + path + File.separator + newName + fileSuffixName;
            } else {
                dbSavePath = Constant.USERFILES_BASE_URL + File.separator + newName + fileSuffixName;
            }

            // 上传路径
            String uploadPath = Constant.FILE_UPLOAD_BASE_PATH + dbSavePath;

            File uploadFile = new File(uploadPath);

            if (!uploadFile.getParentFile().exists()) {
                uploadFile.getParentFile().mkdirs();
            }
            file.transferTo(uploadFile);
        }

        return dbSavePath;
    }
}
