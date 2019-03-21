$(function () {
    // WebUpload上传控件 BEGIN

    var $list = $("#thelist");
    var $btn = $("#ctlBtn");
    var state = 'pending'; // 上传文件初始化
    var uploader = WebUploader.create({
        swf: '${ctx}/webuploader-0.1.5/Uploader.swf',
        server: '${ctx}/cms/cmsCategory/uploadFile',
        pick: '#picker',
        resize: false
    });
    uploader.on('fileQueued', function (file) {
        $list.append('<div id="' + file.id + '" class="item">'
            + '<h4 class="info">' + file.name + '</h4>'
            + '<p class="state">等待上传...</p>' + '</div>');
    });

    uploader.on('uploadProgress',
        function (file, percentage) {
            var $li = $('#' + file.id), $percent = $li
                .find('.progress .progress-bar');

            // 避免重复创建
            if (!$percent.length) {
                $percent = $(
                    '<div class="progress progress-striped active">'
                    + '<div class="progress-bar" role="progressbar" style="width: 0%">'
                    + '</div>' + '</div>')
                    .appendTo($li).find('.progress-bar');
            }

            $li.find('p.state').text('上传中');

            $percent.css('width', percentage * 100 + '%');
        });


    uploader.on('uploadSuccess', function (file) {
        $('#' + file.id).find('p.state').text('已上传');
    });

    uploader.on('uploadError', function (file) {
        $('#' + file.id).find('p.state').text('上传出错');
    });

    uploader.on('uploadComplete', function (file) {
        $('#' + file.id).find('.progress').fadeOut();
    });
    $btn.on('click', function () {
        if (state === 'uploading') {
            uploader.stop();
        } else {
            uploader.upload();
        }
    });

    // WebUpload上传控件 END

});