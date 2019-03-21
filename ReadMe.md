# 更新说明

## 20190320 富文本编辑器初始化
### 编辑器初始化

    // 初始化
    $("#rich_testupload").summernote({
        lang: 'zh-CN',
        focus: true,
        onImageUpload: function (files, editor, welEditable) {
            richSendImage(files[0], editor, welEditable);
        }
     });
     
     // richSendImage 为msframe.js中富文本编辑器图片上传方法