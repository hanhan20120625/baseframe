<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>图片信息管理</title>
    <meta name="decorator" content="default"/>
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/iconfont/css/iconfont.css">
    <style>
        * {
            padding: 0;
            margin: 0;
        }

        ul, li {
            list-style: none;
            padding: 0;
            margin: 0;
        }

        img {
            border: none;
            display: block;
        }

        .img-box {
            margin-left: -0.5%;
            margin-right: -0.5%;
        }

        .img-view:after {
            display: block;
            clear: both;
            content: "";
            visibility: hidden;
            height: 0
        }

        .img-list img {
            width: 100%;
            height: 100%;
            object-fit: cover;
            border-radius: 4px;
        }

        .img-list {
            box-shadow: 0px 10px 40px -10px rgba(0, 64, 128, 0.2);
            padding: 10px;
            background-color: #ffffff;
            border-radius: 4px;
            height: 200px;
            margin: 1%;
            position: relative;
        }

        @media all and (min-width: 768px) {
            .img-box .img-list.col-sm-4 {
                width: 32.33%;
                margin: 0.5%;
            }
        }

        @media all and (min-width: 970px) {
            .img-box .img-list.col-md-3 {
                width: 24%;
                margin: 0.5%;
            }
        }

        @media all and (max-width: 768px) {
            .img-box .img-list.col-xs-6 {
                width: 48%;
                margin: 1%;
            }

            .img-box {
                margin-left: -1%;
                margin-right: -1%;
            }
        }

        @media all and (max-width: 450px) {
            .img-box .img-list.col-xs-6 {
                width: 96%;
                margin: 2%;

            }

            .img-box {
                margin-left: -2%;
                margin-right: -2%;
            }
        }

        .img-list:hover {
            box-shadow: 0px 10px 40px -2px rgba(0, 64, 128, 0.4);
            transition: box-shadow 0.5s;
            -ms-transition: box-shadow 0.5s;
            -o-transition: box-shadow 0.5s;
            -webkit-transition: box-shadow 0.5s;
            -moz--transition: box-shadow 0.5s;
        }

        .pop-box {
            position: absolute;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.5);
            top: 0;
            left: 0;
            border-radius: 4px;
            display: none;
        }

        .center {
            display: -webkit-box;
            display: -ms-flexbox;
            display: -webkit-flex;
            display: flex;
            flex-flow: row;
            -webkit-box-pack: center;
            -ms-flex-pack: center;
            -webkit-justify-content: center;
            justify-content: center;
            -webkit-box-align: center;
            -ms-flex-align: center;
            -webkit-align-items: center;
            align-items: center;
        }

        .inside-box {
            position: absolute;
            width: 100%;
            height: 100%;
        }
    </style>
    <script type="text/javascript">
        $(document).ready(function () {

            $(".img-box .img-list").hover(function () {
                $(this).find(".pop-box").show();
            }, function () {
                $(this).find(".pop-box").hide();
            });

        });

        /**
         * @description 获取当前iframeId
         * @author leon
         * @date 2018/11/19
         */
        function getIframeId() {
            var iframeId = self.frameElement.getAttribute('id');
            return iframeId;
        }

        /**
         * @description 添加影片收藏
         * @author leon
         * @date 2018/11/19
         */
        function add() {
            openDialog("添加图片信息", "${ctx}/cms/cmsPictureInfo/form?programId=${programId}&movieId=${movieId}", "70%", "70%", getIframeId());
        }

        function editPic(id) {
            openDialog("编辑图片信息", "${ctx}/cms/cmsPictureInfo/form?id=" + id + "&programId=${programId}&movieId=${movieId}", "70%", "70%", getIframeId());
        }


        /**
         * @description 更改排序
         * @param id
         * @param type
         * @param obj
         */
        function changeSort(id, type, obj) {
            var current = $(obj).parents(".img-list");
            var prev = current.prev();
            var next = current.next();
            var url = "";

            var name = $("#name").val();
            var description = $("#description").val();
            var isSearch = $("#isSearch").val();
            if(isSearch=="true"){
                url = "${ctx}/cms/cmsPictureInfo/changeSort?id=" + id + "&type=" + type + "&name=" + name + "&description=" + description;
            }else{
                url = "${ctx}/cms/cmsPictureInfo/changeSort?id=" + id + "&type=" + type;
            }
            $.ajax({
                url: url,
                type: "GET",
                success: function (data) {
                    console.log(data);
                    if (data == 'true') {
                        if (type == 'up') {
                            console.log(current);
                            if (current.index() > 0) {
                                current.insertBefore(prev);
                            }
                        } else {
                            if (next) {
                                current.insertAfter(next);
                            }
                        }
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    console.log(XMLHttpRequest.status);
                    console.log(XMLHttpRequest.readyState);
                    console.log(textStatus);
                }
            });
        }


        //图片id
        var imgId = "";
        /**
         * 图片预览
         */
        function preview(url,id) {
            console.log(url);
            imgId = id;
            $("#bigimg").attr("src", url);//设置#bigimg元素的src属性
            $("<img/>").attr("src", url).load(function(){
                var windowW = $(window).width();//获取当前窗口宽度
                var windowH = $(window).height();//获取当前窗口高度
                var realWidth = this.width;//获取图片真实宽度
                var realHeight = this.height;//获取图片真实高度
                var imgWidth, imgHeight;
                var scale = 0.8;//缩放尺寸，当图片真实宽度和高度大于窗口宽度和高度时进行缩放

                if(realHeight>windowH*scale) {//判断图片高度
                    imgHeight = windowH*scale;//如大于窗口高度，图片高度进行缩放
                    imgWidth = imgHeight/realHeight*realWidth;//等比例缩放宽度
                    if(imgWidth>windowW*scale) {//如宽度大于窗口宽度
                        imgWidth = windowW*scale;//再对宽度进行缩放
                    }
                } else if(realWidth>windowW*scale) {//如图片高度合适，判断图片宽度
                    imgWidth = windowW*scale;//如大于窗口宽度，图片宽度进行缩放
                    imgHeight = imgWidth/realWidth*realHeight;//等比例缩放高度
                } else {//如果图片真实高度和宽度都符合要求，高宽不变
                    imgWidth = realWidth;
                    imgHeight = realHeight;
                }
                $("#bigimg").css("width",imgWidth);//以最终的宽度对图片缩放

                var w = (windowW-imgWidth)/2;//计算图片与窗口左边距
                var h = (windowH-imgHeight)/2;//计算图片与窗口上边距
                $("#innerdiv").css({"top":h, "left":w});//设置#innerdiv的top和left属性
                $("#outerdiv").fadeIn("fast");//淡入显示#outerdiv及.pimg
            });

            $("#innerdiv").click(function(){//再次点击淡出消失弹出层
                $(this).parent().fadeOut("fast");
            });
            /*console.log(url);
            top.layer.open({
                type: 2,
                btn: ["取消"],
                content: url,
            });*/
        }

        function prevImg(){
            var len = $("#" + imgId).parent().prev("li").length;
            if (len>0){
                var url = $("#" + imgId).parent().prev("li").find(".img").attr("src");
                imgId = $("#" + imgId).parent().prev("li").find(".img").attr("id");
                preview(url, imgId);
            }
        }

        function nextImg(){
            var len = $("#" + imgId).parent().next("li").length;
            if (len>0){
                var url = $("#" + imgId).parent().next("li").find(".img").attr("src");
                imgId = $("#" + imgId).parent().next("li").find(".img").attr("id");
                preview(url, imgId);
            }
        }


    </script>


</head>
<body class="gray-bg">
<div class="wrapper wrapper-content">
    <div class="ibox">
        <div class="ibox-title">
            <h5>图片信息列表 </h5>
        </div>
        <!--图片预览div-->
        <div id="outerdiv" style="position:fixed;top:0;left:0;background:rgba(0,0,0,0.7);z-index:2;width:100%;height:100%;display:none;">
            <div style="float: left;position: absolute;top: 45%;" onclick="prevImg()">
                <i class="iconfont icon-jiantou4" style="font-size: 35px;color: white"></i></a>
            </div>
            <div id="innerdiv" style="position:absolute;">
                <img id="bigimg" style="border:5px solid #fff;" src="" />
            </div>
            <div style="float: right;position: relative; top: 45%;" onclick="nextImg()">
                <i class="iconfont icon-jiantouyou" style="font-size: 35px;color: white"></i>
            </div>
        </div>
        <!--end-->
        <div class="ibox-content">
            <sys:message content="${message}"/>

            <!--查询条件-->
            <div class="row">
                <div class="col-sm-12">
                    <%--@elvariable id="cmsPictureInfo" type="com.msframe.modules.cms.entity.CmsPictureInfo"--%>
                    <form:form id="searchForm" modelAttribute="cmsPictureInfo" action="${ctx}/cms/cmsPictureInfo/"
                               method="post" class="form-inline">
                        <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                        <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                        <input id="programId" name="programId" type="hidden" value="${programId}">
                        <input id="movieId" name="movieId" type="hidden" value="${movieId}">
                        <input id="isSearch" name="isSearch" type="hidden" value="${isSearch}"/>
                        <table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}"
                                          callback="sortOrRefresh();"/><!-- 支持排序 -->
                        <div class="form-group">


                            <div class="from-screen-box clearfloat">
                                <span class="screen-title-style">名称</span>
                                <form:input path="name" htmlEscape="false" maxlength="128"
                                            class=" form-control input-sm screen-input-style"/>
                            </div>


                            <div class="from-screen-box clearfloat">
                                <span class="screen-title-style">描述</span>
                                <form:input path="description" htmlEscape="false" maxlength="256"
                                            class=" form-control input-sm screen-input-style"/>
                            </div>


                        </div>
                    </form:form>
                    <br/>
                </div>
            </div>

            <!-- 工具栏 -->
            <div class="row">
                <div class="col-sm-12">
                    <div class="pull-left">
                        <shiro:hasPermission name="cms:cmsPictureInfo:add">
                            <!-- 增加按钮 -->
                            <button class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="left"
                                    onclick="add()" title="影片图片"><i class="fa fa-plus"></i> 添加
                            </button>
                        </shiro:hasPermission>
                        <%--<shiro:hasPermission name="cms:cmsPictureInfo:edit">--%>
                        <%--<table:editRow url="${ctx}/cms/cmsPictureInfo/form" title="图片信息"--%>
                        <%--id="contentTable"></table:editRow><!-- 编辑按钮 -->--%>
                        <%--</shiro:hasPermission>--%>
                        <shiro:hasPermission name="cms:cmsPictureInfo:del">
                            <table:delRow url="${ctx}/cms/cmsPictureInfo/deleteAll"
                                          id="contentTable"></table:delRow><!-- 删除按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="cms:cmsPictureInfo:import">
                            <table:importExcel url="${ctx}/cms/cmsPictureInfo/import"></table:importExcel><!-- 导入按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="cms:cmsPictureInfo:export">
                            <table:exportExcel url="${ctx}/cms/cmsPictureInfo/export"></table:exportExcel><!-- 导出按钮 -->
                        </shiro:hasPermission>
                        <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left"
                                onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新
                        </button>

                    </div>
                    <div class="pull-right">
                        <button class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()"><i
                                class="fa fa-search"></i> 查询
                        </button>
                        <button class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="reset()"><i
                                class="fa fa-refresh"></i> 重置
                        </button>
                    </div>
                </div>
            </div>

            <!-- 表格 -->
            <div class="img-box">
                <ul class="img-view">
                    <c:forEach items="${page.list}" var="cmsPictureInfo" varStatus="index">
                        <li class="img-list col-md-3 col-sm-4 col-xs-6">
                            <img src="${crc}${cmsPictureInfo.picUrl}" alt="" id="${cmsPictureInfo.id}" class="img">
                            <div class="pop-box">
                                <div class="inside-box center">
                                    <div style="margin: auto 80px auto -250px;">
                                        <c:if test="${index.first ne true}">
                                            <a href="javaScript:void(0)"
                                               onclick="changeSort('${cmsPictureInfo.id}','up',this)">
                                                <i class="iconfont icon-jiantou4"
                                                   style="font-size: 35px;position: relative;color: white"></i></a>
                                        </c:if>
                                    </div>

                                    <div>
                                    <a href="javaScript:void(0)" style="margin-right: 15px"
                                       onclick="preview('${crc}${cmsPictureInfo.picUrl}','${cmsPictureInfo.id}')">
                                        <i class='iconfont icon-eye' style="font-size: 35px;color: white;"></i></a>
                                    <a href="javaScript:void(0)" onclick="editPic('${cmsPictureInfo.id}')" style="margin-right: 15px">
                                        <i class="iconfont icon-edit" style="font-size: 25px;color: white;"></i></a>
                                    <a href="javaScript:void(0)" onclick="return confirmx('确认要删除该图片信息吗？', '${ctx}/cms/cmsPictureInfo/delete?id=${cmsPictureInfo.id}&programId=${programId}&movieId=${movieId}')">
                                        <i class="iconfont icon-delete" style="font-size: 35px;color: white;"></i></a>
                                    </div>

                                    <div style="margin: auto -250px auto 80px">
                                        <c:if test="${index.last ne true}">
                                            <a href="javaScript:void(0)"
                                               onclick="changeSort('${cmsPictureInfo.id}','down',this)">
                                                <i class="iconfont icon-jiantouyou"
                                                   style="font-size: 35px;color: white;"></i></a>

                                        </c:if>
                                    </div>
                                </div>
                            </div>
                        </li>
                    </c:forEach>
                </ul>
            </div>

            <%--<table id="contentTable"--%>
            <%--class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">--%>
            <%--<thead>--%>
            <%--<tr>--%>
            <%--<th><input type="checkbox" class="i-checks"></th>--%>
            <%--<th class="sort-column name">名称</th>--%>
            <%--<th class="sort-column format">图片类型</th>--%>
            <%--<th class="sort-column description">描述</th>--%>
            <%--<th class="sort-column status">状态</th>--%>
            <%--<th class="sort-column sort">排序</th>--%>
            <%--<th>操作</th>--%>
            <%--</tr>--%>
            <%--</thead>--%>
            <%--<tbody>--%>
            <%--<c:forEach items="${page.list}" var="cmsPictureInfo">--%>
            <%--<tr>--%>
            <%--<td><input type="checkbox" id="${cmsPictureInfo.id}" class="i-checks"></td>--%>
            <%--<td><a href="#"--%>
            <%--onclick="openDialogView('查看图片信息', '${ctx}/cms/cmsPictureInfo/view?id=${cmsPictureInfo.id}','90%', '90%')">--%>
            <%--${cmsPictureInfo.name}--%>

            <%--</a></td>--%>
            <%--<td>--%>
            <%--${cmsPictureInfo.format}--%>
            <%--</td>--%>
            <%--<td>--%>
            <%--${cmsPictureInfo.description}--%>
            <%--</td>--%>
            <%--<td>--%>
            <%--${fns:getDictLabel(cmsPictureInfo.status, 'general_status', '')}--%>
            <%--</td>--%>
            <%--<td>--%>
            <%--${cmsPictureInfo.sort}--%>
            <%--</td>--%>
            <%--<td>--%>
            <%--<shiro:hasPermission name="cms:cmsPictureInfo:view">--%>
            <%--<a href="#"--%>
            <%--onclick="openDialogView('查看图片信息', '${ctx}/cms/cmsPictureInfo/view?id=${cmsPictureInfo.id}','90%', '90%')"--%>
            <%--class="btn btn-info btn-xs"><i class="fa fa-search-plus"></i> 查看</a>--%>
            <%--</shiro:hasPermission>--%>
            <%--<shiro:hasPermission name="cms:cmsPictureInfo:edit">--%>
            <%--<a href="#"--%>
            <%--onclick="edit('${cmsPictureInfo.id}')"--%>
            <%--class="btn btn-success btn-xs"><i class="fa fa-edit"></i> 修改</a>--%>
            <%--</shiro:hasPermission>--%>
            <%--<shiro:hasPermission name="cms:cmsPictureInfo:del">--%>
            <%--<a href="${ctx}/cms/cmsPictureInfo/delete?id=${cmsPictureInfo.id}"--%>
            <%--onclick="return confirmx('确认要删除该图片信息吗？', this.href)" class="btn btn-danger btn-xs"><i--%>
            <%--class="fa fa-trash"></i> 删除</a>--%>
            <%--</shiro:hasPermission>--%>
            <%--</td>--%>
            <%--</tr>--%>
            <%--</c:forEach>--%>
            <%--</tbody>--%>
            <%--</table>--%>

            <!-- 分页代码 -->
            <table:page page="${page}"></table:page>
            <br/>
            <br/>
        </div>
    </div>
</div>
</body>
</html>