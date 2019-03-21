package com.msframe.modules.sys.utils;

import com.msframe.common.config.Global;
import com.msframe.common.utils.PropertiesLoader;
import com.msframe.common.web.Servlets;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author leon
 * @version 1.0.0
 * @date 2019/3/18 1:46 PM
 */
public class SummernoteUtils {

    private static PropertiesLoader loader = new PropertiesLoader("msframe.properties");

    public static String commonUpload(Integer type, MultipartFile filedata) throws IOException {
        // 获取时间戳 我是以获取时间戳来命名上传的文件的
        String fileName = getRandomOrderNoOrFileName();
        String suffix = filedata.getOriginalFilename().substring(filedata.getOriginalFilename().lastIndexOf("."));
        String name = fileName + suffix;
        String name_x = fileName + "_x" + suffix;
        String imgAddr = "";

        String root = "";
        switch (type) {
            case 1:
                //这里我是通过system.Config.properties。配置的路径，然后根据名称来获得 图片上传的路径；  你可以，直接在这里写好路径；
                root = loader.getProperty("richImage");
                //最后返回的 图片路径
                imgAddr = Servlets.getRequest().getContextPath() + "/userfiles/richImage" + "/" + name;
                break;
        }


        File file = new File(root, name_x);
        File rootPath = new File(root);
        if (!rootPath.exists()) {
            rootPath.mkdir();
        }
        File destFile = new File(rootPath, name);
        byte[] bytes = filedata.getBytes();

        // 写入文件
        FileOutputStream fos = new FileOutputStream(destFile);
        fos.write(bytes);
        fos.close();
        if ("png,jpg,PNG,JPG,jpeg".contains(suffix)) {
//            NarrowImage.writeHighQuality(NarrowImage.zoomImage(destFile.getAbsolutePath()), file.getAbsolutePath());
        }
        return imgAddr;
    }

    /**
     * 获取时间戳
     *
     * @return
     */
    public static String getRandomOrderNoOrFileName() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String yid = sdf.format(new Date()) + (new Random().nextInt(100));
        return yid;
    }
}
