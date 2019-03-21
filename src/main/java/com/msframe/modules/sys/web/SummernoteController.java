package com.msframe.modules.sys.web;

import com.msframe.common.web.BaseController;
import com.msframe.modules.sys.utils.SummernoteUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 富文本编辑器Controller
 *
 * @author leon
 * @version 1.0.0
 * @date 2019/3/18 1:41 PM
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/summernote")
public class SummernoteController extends BaseController {

    @RequestMapping(value = "summernoteImage", headers = "Accept=application/json", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String summernoteImage(HttpServletRequest request, HttpServletResponse response, ModelMap model, MultipartFile file) throws Exception {
        String result = "";
        try {
            if (file != null) {
                String path = SummernoteUtils.commonUpload(1, file);
                return path;
            }

        } catch (Exception e) {
            result = "";
            e.printStackTrace();
        }
        return result;
    }
}
