<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>视频项目管理</title>
    <meta name="decorator" content="default"/>

    <script type="text/javascript">
        var validateForm;

        function doSubmit() {//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
            if (validateForm.form()) {
                $("#inputForm").submit();
                return true;
            }

            return false;
        }

        $(document).ready(function () {
            validateForm = $("#inputForm").validate({
                submitHandler: function (form) {
                    loading('正在提交，请稍等...');
                    form.submit();
                },
                errorContainer: "#messageBox",
                errorPlacement: function (error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
                        error.appendTo(element.parent().parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });

            laydate({
                elem: '#orgAirDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
                event: 'focus' //响应事件。如果没有传入event，则按照默认的click
            });
            laydate({
                elem: '#licensingWindowStart', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
                event: 'focus' //响应事件。如果没有传入event，则按照默认的click
            });
            laydate({
                elem: '#licensingWindowEnd', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
                event: 'focus' //响应事件。如果没有传入event，则按照默认的click
            });

            $(".iCheck-helper").click(function () {
                var id = $(this).prev().attr("id");
                categoryForType($(this).parent().next(), id, 'input');
            });


            /*$(".file-input").on("change", "input[type='file']", function (obj) {
                var filePath = $(this).val();
                if (filePath.indexOf("jpg") != -1 || filePath.indexOf("png") != -1) {
                    $(".fileerrorTip1").html("").hide();
                    var arr = filePath.split('\\');
                    var fileName = arr[arr.length - 1];
                    $(this).parents(".upload-box").find(".showFileName1").html(fileName);

                    
                    

                } else {
                    $(this).parents(".upload-box").find(".showFileName1").html("");
                    return false
                }

                
            })
          */
            // 还原数据已经勾选的栏目信息
            var temp = $(".cmsCategoryList").find("input:checked");
            $.each(temp, function (i, item) {
                var tempId = $(item).val();
                $("." + tempId).show();
            });

            $(".pr").hover(function () {
                $(this).find(".preview-img").show();
            },function () {
                $(this).find(".preview-img").hide();
            })
        });
        function changepic(obj) {
            //console.log(obj.files[0]);//这里可以获取上传文件的name
            var filePath = $(obj).val();
            if (filePath.indexOf("jpg") != -1 || filePath.indexOf("png") != -1) {
                $(".fileerrorTip1").html("").hide();
                var arr = filePath.split('\\');
                var fileName = arr[arr.length - 1];
                $(this).parents(".upload-box").find(".showFileName1").html(fileName);

                var newsrc=getObjectURL(obj.files[0]);
                // document.getElementById('imga').src=newsrc;
                $(obj).parents(".box-upload").find(".show-img").show().attr("src",newsrc)

            } else {
                $(this).parents(".upload-box").find(".showFileName1").html("");
                return false
            }

        }
        //建立一個可存取到該file的url
        function getObjectURL(file) {
            var url = null ;
            // 下面函数执行的效果是一样的，只是需要针对不同的浏览器执行不同的 js 函数而已
            if (window.createObjectURL!=undefined) { // basic
                url = window.createObjectURL(file) ;
            } else if (window.URL!=undefined) { // mozilla(firefox)
                url = window.URL.createObjectURL(file) ;
            } else if (window.webkitURL!=undefined) { // webkit or chrome
                url = window.webkitURL.createObjectURL(file) ;
            }
            return url ;
        }
        
        
        /**
         *
         * @param obj
         * @param id
         */
        function categoryForType(obj, id, type) {
            console.log(id);
            console.log($(obj).prev().children(".i-checks")[0].checked);
            if ($(obj).prev().children(".i-checks")[0].checked) {
                if (type == 'input') {
                    $("." + id).show();
                } else {
                    $("." + id).hide();
                }
            } else {
                if (type == 'input') {
                    $("." + id).hide();
                } else {
                    $("." + id).show();
                }
            }
        }

    </script>

</head>
<body>
<%--@elvariable id="cmsProgram" type="com.msframe.modules.cms.entity.CmsProgram"--%>
<form:form id="inputForm" modelAttribute="cmsProgram" action="${ctx}/cms/cmsProgram/save" method="post"
           class="form-horizontal" enctype="multipart/form-data">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
        <tbody>

        <tr>
            <td class="width-15 active"><label class="pull-right">cp内容标识：</label></td>
            <td class="width-35">
                <form:input path="cpContentid" htmlEscape="false" maxlength="64" class="form-control "/>
            </td>
            <td class="width-15 active"><label class="pull-right">内容提供商：</label></td>
            <td class="width-35">
                <sys:gridselect id="spId" name="spId.id" value="${cmsProgram.spId.id}" labelName="spId.name"
                                labelValue="${cmsProgram.spId.name}" fieldLabels="名称" fieldKeys="name"
                                searchLabel="内容提供商名称" searchKey="name" title="选择内容提供商"
                                url="${ctx}/cms/cmsSp/selectCmsSp" cssClass="form-control" linkage="false"/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">名称：</label></td>
            <td class="width-35">
                <form:input path="name" htmlEscape="false" maxlength="128" class="form-control "/>
            </td>
            <td class="width-15 active"><label class="pull-right">原始名称：</label></td>
            <td class="width-35">
                <form:input path="originalName" htmlEscape="false" maxlength="128" class="form-control "/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">SP栏目id：</label></td>
            <td class="width-35">
                <form:input path="spCategoryid" htmlEscape="false" maxlength="128" class="form-control"/>
            </td>
            <td class="width-15 active"><label class="pull-right">排序名称：</label></td>
            <td class="width-35">
                <form:input path="sortName" htmlEscape="false" maxlength="128" class="form-control "/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">搜索名称：</label></td>
            <td class="width-35">
                <form:input path="searchName" htmlEscape="false" maxlength="128" class="form-control "/>
            </td>
            <td class="width-15 active"><label class="pull-right">风格：</label></td>
            <td class="width-35">
                <form:input path="genre" htmlEscape="false" maxlength="128" class="form-control "/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">栏目：</label></td>
            <td class="width-35 cmsCategoryList">
                <c:forEach items="${cmsCategoryList}" var="cmsCategory" varStatus="status">
                    <div style="float: left;padding: 0px;" class="col-md-4 col-sm-6">
                        <input type="checkbox" id="${cmsCategory.id}" name="cmsCategorySaveList"
                               value="${cmsCategory.id}"
                               <c:if test="${cmsCategory.checkFlag}">checked="checked"</c:if>
                               class="i-checks required"/>
                        <label for="${cmsCategory.id}"
                               onclick="categoryForType(this,'${cmsCategory.id}')">${cmsCategory.name}</label>
                    </div>
                </c:forEach>
            </td>
            <td class="width-15 active"><label class="pull-right">影片类型：</label></td>
            <td class="width-35">
                <c:forEach items="${cmsTypeList}" var="cmsType" varStatus="status">
                    <div style="float: left;padding: 0px;display:none" class="col-md-4 col-sm-6 ${cmsType.category.id}">
                        <input type="checkbox" id="${cmsType.id}" name="cmsTypeSaveList" value="${cmsType.id}"
                               <c:if test="${cmsType.checkFlag}">checked="checked"</c:if>
                               class="i-checks required"/>
                        <label for="${cmsType.id}">${cmsType.name}</label>
                    </div>
                </c:forEach>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">语言：</label></td>
            <td class="width-35">
                <c:forEach items="${cmsLangList}" var="cmsLang" varStatus="status">
                    <div style="float: left;padding: 0px;" class="col-md-4 col-sm-6">
                        <input type="checkbox" id="${cmsLang.id}" name="cmsLangSaveList" value="${cmsLang.id}"
                               <c:if test="${cmsLang.checkFlag}">checked="checked"</c:if>
                               class="i-checks required"/>
                        <label for="${cmsLang.id}">${cmsLang.name}</label>
                    </div>
                </c:forEach>
            </td>
            <td class="width-15 active"><label class="pull-right">国家地区：</label></td>
            <td class="width-35">
                <c:forEach items="${cmsRegionList}" var="cmsRegion" varStatus="status">
                    <div style="float: left;padding: 0px;" class="col-md-4 col-sm-6">
                        <input type="checkbox" id="${cmsRegion.id}" name="cmsRegionSaveList" value="${cmsRegion.id}"
                               <c:if test="${cmsRegion.checkFlag}">checked="checked"</c:if>
                               class="i-checks required"/>
                        <label for="${cmsRegion.id}">${cmsRegion.name}</label>
                    </div>
                </c:forEach>
            </td>
        </tr>

        <tr>
            <td class="width-15 active"><label class="pull-right">横大图</label></td>
            <td class="width-35">
                <div class="pr">
                    <c:if test="${cmsProgram.hBigPic ne '' and cmsProgram.hBigPic ne null}">
                        <img src="${crc}${cmsProgram.hBigPic}" class="program-img-style"/>
                    </c:if>
                    <div class="preview-img">
                        <div class="preview-main center2">
                            <span class="preview-btn">
                                <i class="fa fa-eye"></i>
                                <span>预览大图</span>
                            </span>
                        </div>
                    </div>
                </div>
                <input type="hidden" name="hBigPic" value="${cmsProgram.hBigPic}"/>
                    <%--<div class="upload-box">
                        <input type="file" style="opacity: 0;" name="files" class="form-control "/>
                        <i class="fa fa-upload upload-icon"></i>
                    </div>--%>
                <div class="box-upload">
                    <img class="show-img program-img-style" src="" alt="">
                    <div class="upload-box">
                        <div class="file-input">
                            <i class="fa fa-upload upload-icon"></i>
                            <span>选择文件</span>
                            <input type="file" name="files" onchange="changepic(this)" class="form-control filepath"/>
                        </div>
                        <span class="showFileName1"></span>
                    </div>
                </div>

            </td>
            <td class="width-15 active"><label class="pull-right">横小图</label></td>
            <td class="width-35">
                <div class="pr">
                    <c:if test="${cmsProgram.hSmallPic ne '' and cmsProgram.hSmallPic ne null}">
                        <img src="${crc}${cmsProgram.hSmallPic}" class="program-img-style"/>
                    </c:if>
                    <div class="preview-img">
                        <div class="preview-main center2">
                            <span class="preview-btn">
                                <i class="fa fa-eye"></i>
                                <span>预览大图</span>
                            </span>
                        </div>
                    </div>
                </div>
                <input type="hidden" name="hSmallPic" value="${cmsProgram.hSmallPic}"/>
                    <%--<input type="file" name="files" class="form-control"/>--%>
                <div class="box-upload">
                    <img class="show-img program-img-style" src="" alt="">
                    <div class="upload-box">
                        <div class="file-input">
                            <i class="fa fa-upload upload-icon"></i>
                            <span>选择文件</span>
                            <input type="file" name="files" onchange="changepic(this)" class="form-control filepath"/>
                        </div>
                        <span class="showFileName1"></span>
                    </div>
                </div>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">竖大图</label></td>
            <td class="width-35">
                <div class="pr">
                    <c:if test="${cmsProgram.vBigPic ne '' and cmsProgram.vBigPic ne null}">
                        <img src="${crc}${cmsProgram.vBigPic}" class="program-img-style"/>
                    </c:if>
                    <div class="preview-img">
                        <div class="preview-main center2">
                            <span class="preview-btn">
                                <i class="fa fa-eye"></i>
                                <span>预览大图</span>
                            </span>
                        </div>
                    </div>
                </div>
                <input type="hidden" name="vBigPic" value="${cmsProgram.vBigPic}"/>
                    <%-- <input type="file" name="files" class="form-control"/>--%>
                <div class="box-upload">
                    <img class="show-img program-img-style" src="" alt="">
                    <div class="upload-box">
                        <div class="file-input">
                            <i class="fa fa-upload upload-icon"></i>
                            <span>选择文件</span>
                            <input type="file" name="files" onchange="changepic(this)" class="form-control filepath"/>
                        </div>
                        <span class="showFileName1"></span>
                    </div>
                </div>
            </td>
            <td class="width-15 active"><label class="pull-right">竖小图</label></td>
            <td class="width-35">
                <div class="pr">
                    <c:if test="${cmsProgram.vSmallPic ne '' and cmsProgram.vSmallPic ne null}">
                        <img src="${crc}${cmsProgram.vSmallPic}" class="program-img-style"/>
                    </c:if>
                    <div class="preview-img">
                        <div class="preview-main center2">
                            <span class="preview-btn">
                                <i class="fa fa-eye"></i>
                                <span>预览大图</span>
                            </span>
                        </div>
                    </div>
                </div>
                <input type="hidden" name="vSamllPic" value="${cmsProgram.vSmallPic}"/>
                    <%--<input type="file" name="files" class="form-control"/>--%>
                <div class="box-upload">
                    <img class="show-img program-img-style" src="" alt="">
                    <div class="upload-box">
                        <div class="file-input">
                            <i class="fa fa-upload upload-icon"></i>
                            <span>选择文件</span>
                            <input type="file" name="files" onchange="changepic(this)" class="form-control filepath"/>
                        </div>
                        <span class="showFileName1"></span>
                    </div>
                </div>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">方形图</label></td>
            <td class="width-35">
                <div class="pr">
                    <c:if test="${cmsProgram.squarePic ne '' and cmsProgram.squarePic ne null}">
                        <img src="${crc}${cmsProgram.squarePic}" class="program-img-style"/>
                    </c:if>
                    <div class="preview-img">
                        <div class="preview-main center2">
                            <span class="preview-btn">
                                <i class="fa fa-eye"></i>
                                <span>预览大图</span>
                            </span>
                        </div>
                    </div>
                </div>
                <input type="hidden" name="squarePic" value="${cmsProgram.squarePic}"/>
                    <%--<input type="file" name="files" class="form-control"/>--%>
                <div class="box-upload">
                    <img class="show-img program-img-style" src="" alt="">
                    <div class="upload-box">
                        <div class="file-input">
                            <i class="fa fa-upload upload-icon"></i>
                            <span>选择文件</span>
                            <input type="file" name="files" onchange="changepic(this)" class="form-control filepath"/>
                        </div>
                        <span class="showFileName1"></span>
                    </div>
                </div>
            </td>
            <td class="width-15 active"><label class="pull-right">海报图</label></td>
            <td class="width-35">
                <div class="pr">
                    <c:if test="${cmsProgram.picUrl ne '' and cmsProgram.picUrl ne null}">
                        <img src="${crc}${cmsProgram.picUrl}" class="program-img-style" alt="">
                    </c:if>
                    <div class="preview-img">
                        <div class="preview-main center2">
                            <span class="preview-btn">
                                <i class="fa fa-eye"></i>
                                <span>预览大图</span>
                            </span>
                        </div>
                    </div>
                </div>
                <input type="hidden" name="picUrl" value="${cmsProgram.picUrl}"/>
                    <%--<input type="file" name="files" class="form-control"/>--%>
                <div class="box-upload">
                    <img class="show-img program-img-style" src="" alt="">
                    <div class="upload-box">
                        <div class="file-input">
                            <i class="fa fa-upload upload-icon"></i>
                            <span>选择文件</span>
                            <input type="file" name="files" onchange="changepic(this)" class="form-control filepath"/>
                        </div>
                        <span class="showFileName1"></span>
                    </div>
                </div>
            </td>
        </tr>

        <tr>
            <td class="width-15 active"><label class="pull-right">拷贝保护标志：</label></td>
            <td class="width-35">
                <form:select path="macroVision" cssClass="form-control">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('general_macro_vision')}" itemLabel="label" itemValue="value"
                                  htmlEscape="false"/>
                </form:select>
            </td>
            <td class="width-15 active"><label class="pull-right">标签：</label></td>
            <td class="width-35">
                <form:input path="tagId" htmlEscape="false" maxlength="2000" class="form-control "/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">上映年份：</label></td>
            <td class="width-35">
                <form:input path="releaseYear" htmlEscape="false" maxlength="4" autocomplete="off"
                            class="form-control  digits"/>
            </td>
            <td class="width-15 active"><label class="pull-right">首播日期：</label></td>
            <td class="width-35">
                <input id="orgAirDate" name="orgAirDate" type="text" maxlength="20"
                       class="laydate-icon form-control layer-date " autocomplete="off"
                       value="<fmt:formatDate value="${cmsProgram.orgAirDate}" pattern="yyyy-MM-dd"/>"/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">有效开始时间：</label></td>
            <td class="width-35">
                <input id="licensingWindowStart" name="licensingWindowStart" type="text" maxlength="20"
                       class="laydate-icon form-control layer-date " autocomplete="off"
                       value="<fmt:formatDate value="${cmsProgram.licensingWindowStart}" pattern="yyyy-MM-dd"/>"/>
            </td>
            <td class="width-15 active"><label class="pull-right">有效结束时间：</label></td>
            <td class="width-35">
                <input id="licensingWindowEnd" name="licensingWindowEnd" type="text" maxlength="20"
                       class="laydate-icon form-control layer-date " autocomplete="off"
                       value="<fmt:formatDate value="${cmsProgram.licensingWindowEnd}" pattern="yyyy-MM-dd"/>"/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">新到天数：</label></td>
            <td class="width-35">
                <form:input path="displayAsNew" htmlEscape="false" maxlength="8" class="form-control "/>
            </td>
            <td class="width-15 active"><label class="pull-right">剩余天数：</label></td>
            <td class="width-35">
                <form:input path="displayAsLastChance" htmlEscape="false" maxlength="8" class="form-control "/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">列表定价：</label></td>
            <td class="width-35">
                <form:input path="priceTaxin" htmlEscape="false" maxlength="11" class="form-control  digits"/>
            </td>
            <td class="width-15 active"><label class="pull-right">源类型：</label></td>
            <td class="width-35">
                <form:select path="sourceType" class="form-control ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('cms_program_source_type')}" itemLabel="label"
                                  itemValue="value" htmlEscape="false"/>
                </form:select>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">连续剧标志：</label></td>
            <td class="width-35">
                <form:select path="seriesFlag" class="form-control ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('cms_program_series_flag')}" itemLabel="label"
                                  itemValue="value" htmlEscape="false"/>
                </form:select>
            </td>
            <td class="width-15 active"><label class="pull-right">总集数：</label></td>
            <td class="width-35">
                <form:input path="totalEpisode" htmlEscape="false" maxlength="11" class="form-control  digits"/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">关键字：</label></td>
            <td class="width-35">
                <form:input path="keyWord" htmlEscape="false" maxlength="256" class="form-control "/>
            </td>
            <td class="width-15 active"><label class="pull-right">看点：</label></td>
            <td class="width-35">
                <form:input path="viewPoint" htmlEscape="false" maxlength="256" class="form-control "/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">星级推荐：</label></td>
            <td class="width-35">
                <form:input path="starLevel" htmlEscape="false" maxlength="8" class="form-control "/>
            </td>
            <td class="width-15 active"><label class="pull-right">分级：</label></td>
            <td class="width-35">
                <form:select path="rating" class="form-control ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('cms_program_rating')}" itemLabel="label" itemValue="value"
                                  htmlEscape="false"/>
                </form:select>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">奖项：</label></td>
            <td class="width-35">
                <form:input path="awards" htmlEscape="false" maxlength="128" class="form-control "/>
            </td>
            <td class="width-15 active"><label class="pull-right">播放时长(秒)：</label></td>
            <td class="width-35">
                <form:input path="length" htmlEscape="false" maxlength="4" class="form-control "/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">同步状态：</label></td>
            <td class="width-35">
                <form:select path="syncState" cssClass="form-control">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('general_sync_state')}" itemLabel="label" itemValue="value"
                                  htmlEscape="false"/>
                </form:select>
            </td>
            <td class="width-15 active"><label class="pull-right">CMS状态：</label></td>
            <td class="width-35">
                <form:select path="cmsState" cssClass="form-control">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('general_cms_state')}" itemLabel="label" itemValue="value"
                                  htmlEscape="false"/>
                </form:select>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">状态：</label></td>
            <td class="width-35">
                <form:select path="status" class="form-control ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('general_status')}" itemLabel="label" itemValue="value"
                                  htmlEscape="false"/>
                </form:select>
            </td>
            <td class="width-15 active"><label class="pull-right">排序：</label></td>
            <td class="width-35">
                <form:input path="sort" htmlEscape="false" maxlength="64" class="form-control  digits"/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">影片展示类型：</label></td>
            <td class="width-35">
                <form:select path="showType" class="form-control ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('cms_program_show_type')}" itemLabel="label"
                                  itemValue="value"
                                  htmlEscape="false"/>
                </form:select>
            </td>
            <td class="width-15 active"></td>
            <td class="width-35"></td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">描述：</label></td>
            <td class="width-35" colspan="3">
                <form:textarea path="description" htmlEscape="false" rows="10" class="form-control"/>
            </td>
        </tr>
        </tbody>
    </table>
</form:form>
</body>
</html>