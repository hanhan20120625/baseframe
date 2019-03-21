<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>产品信息管理</title>
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
                elem: '#promoteExpireTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
                event: 'focus' //响应事件。如果没有传入event，则按照默认的click
            });startTime
            laydate({
                elem: '#startTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
                event: 'focus' //响应事件。如果没有传入event，则按照默认的click
            });
            laydate({
                elem: '#expireTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
                event: 'focus' //响应事件。如果没有传入event，则按照默认的click
            });
            laydate({
                elem: '#reviewTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
                event: 'focus' //响应事件。如果没有传入event，则按照默认的click
            });
        });
    </script>
</head>
<body>
<%--@elvariable id="bmsProduct" type="com.msframe.modules.bms.entity.BmsProduct"--%>
<form:form id="inputForm" modelAttribute="bmsProduct" action="${ctx}/bms/bmsProduct/save" method="post"
           class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
        <tbody>

        <tr>
            <td class="width-15 active"><label class="pull-right">名称：</label></td>
            <td class="width-35">
                <form:input path="name" htmlEscape="false" maxlength="128" class="form-control reque required"/>
            </td>
            <td class="width-15 active"><label class="pull-right">产品标识：</label></td>
            <td class="width-35">
                <form:input path="productIdent" htmlEscape="false" maxlength="64" class="form-control required"/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">描述：</label></td>
            <td class="width-35">
                <form:hidden path="description" id="files" htmlEscape="false" maxlength="255" cssClass="form-control"/>
                <sys:ckfinder input="files" type="images" uploadPath="/test"/>
            </td>
            <td class="width-15 active"><label class="pull-right">产品类型：</label></td>
            <td class="width-35">
                <form:select path="productType" class="form-control required">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('bms_product_product_type')}" itemLabel="label"
                                  itemValue="value" htmlEscape="false"/>
                </form:select>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">服务商：</label></td>
            <td class="width-35">
                <form:input path="cpspId" htmlEscape="false" maxlength="64" class="form-control required "/>
            </td>
            <td class="width-15 active"><label class="pull-right">业务类型：</label></td>
            <td class="width-35">
                <form:input path="bizType" htmlEscape="false" maxlength="4" class="form-control  digits required"/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">租用有效期：</label></td>
            <td class="width-35">
                <form:input path="renderPeriod" htmlEscape="false" maxlength="11" class="form-control  digits"/>
            </td>
            <td class="width-15 active"><label class="pull-right">tariff_policy_code：</label></td>
            <td class="width-35">
                <form:input path="tariffPolicyCode" htmlEscape="false" maxlength="64" class="form-control "/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">优惠策略编号：</label></td>
            <td class="width-35">
                <form:input path="discountPolicyCode" htmlEscape="false" maxlength="64" class="form-control "/>
            </td>
            <td class="width-15 active"><label class="pull-right">来源：</label></td>
            <td class="width-35">
                <form:select path="createSrc" class="form-control ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('bms_product_create_src')}" itemLabel="label"
                                  itemValue="value" htmlEscape="false"/>
                </form:select>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">计费类别：</label></td>
            <td class="width-35">
                <form:select path="feeType" class="form-control ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('bms_product_fee_type')}" itemLabel="label" itemValue="value"
                                  htmlEscape="false"/>
                </form:select>
            </td>
            <td class="width-15 active"><label class="pull-right">包月封顶费：</label></td>
            <td class="width-35">
                <form:input path="fixedFee" htmlEscape="false" maxlength="11" class="form-control  digits"/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">价格：</label></td>
            <td class="width-35">
                <form:input path="listPrice" htmlEscape="false" maxlength="11" class="form-control  digits"/>
            </td>
            <td class="width-15 active"><label class="pull-right">包次：</label></td>
            <td class="width-35">
                <form:input path="limitTimes" htmlEscape="false" maxlength="11" class="form-control  digits"/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">推广期截止时间：</label></td>
            <td class="width-35">
                <input id="promoteExpireTime" name="promoteExpireTime" type="text" maxlength="20"
                       class="laydate-icon form-control layer-date "
                       value="<fmt:formatDate value="${bmsProduct.promoteExpireTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
            </td>
            <td class="width-15 active"><label class="pull-right">使用方式：</label></td>
            <td class="width-35">
                <form:select path="usageMethod" class="form-control ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('bms_product_usage_method')}" itemLabel="label"
                                  itemValue="value" htmlEscape="false"/>
                </form:select>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">自动续订：</label></td>
            <td class="width-35">
                <form:select path="rollCharge" class="form-control ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('bms_product_roll_charge')}" itemLabel="label"
                                  itemValue="value" htmlEscape="false"/>
                </form:select>
            </td>
            <td class="width-15 active"><label class="pull-right">信用度：</label></td>
            <td class="width-35">
                <form:select path="creditLevel" class="form-control ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('bms_product_credit_level')}" itemLabel="label"
                                  itemValue="value" htmlEscape="false"/>
                </form:select>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">级别：</label></td>
            <td class="width-35">
                <form:select path="level" class="form-control ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('bms_product_level')}" itemLabel="label" itemValue="value"
                                  htmlEscape="false"/>
                </form:select>
            </td>
            <td class="width-15 active"><label class="pull-right">多屏产品：</label></td>
            <td class="width-35">
                <form:select path="multiScreenPd" class="form-control ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('bms_product_multi_screen_pd')}" itemLabel="label"
                                  itemValue="value" htmlEscape="false"/>
                </form:select>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">定购生效方式：</label></td>
            <td class="width-35">
                <form:select path="subEffectType" class="form-control ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('bms_product_sub_effect_type')}" itemLabel="label"
                                  itemValue="value" htmlEscape="false"/>
                </form:select>
            </td>
            <td class="width-15 active"><label class="pull-right">试用时间（天）：</label></td>
            <td class="width-35">
                <form:input path="trialDuration" htmlEscape="false" maxlength="11" class="form-control  digits"/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">定购付款方式：</label></td>
            <td class="width-35">
                <form:select path="subPaymentType" class="form-control ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('bms_product_sub_payment_type')}" itemLabel="label"
                                  itemValue="value" htmlEscape="false"/>
                </form:select>
            </td>
            <td class="width-15 active"><label class="pull-right">取消定购生效方式：</label></td>
            <td class="width-35">
                <form:select path="unsubEffectType" class="form-control ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('bms_product_unsub_effect_type')}" itemLabel="label"
                                  itemValue="value" htmlEscape="false"/>
                </form:select>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">退定退款方式：</label></td>
            <td class="width-35">
                <form:select path="unsubRefundType" class="form-control ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('bms_product_unsub_refund_type')}" itemLabel="label"
                                  itemValue="value" htmlEscape="false"/>
                </form:select>
            </td>
            <td class="width-15 active"><label class="pull-right">开始时间：</label></td>
            <td class="width-35">
                <input id="startTime" name="startTime" type="text" maxlength="20"
                       class="laydate-icon form-control layer-date required"
                       value="<fmt:formatDate value="${bmsProduct.startTime}" pattern="yyyy-MM-dd"/>"/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">过期时间：</label></td>
            <td class="width-35">
                <input id="expireTime" name="expireTime" type="text" maxlength="20"
                       class="laydate-icon form-control layer-date required"
                       value="<fmt:formatDate value="${bmsProduct.expireTime}" pattern="yyyy-MM-dd"/>"/>
            </td>
            <td class="width-15 active"><label class="pull-right">审核时间：</label></td>
            <td class="width-35">
                <input id="reviewTime" name="reviewTime" type="text" maxlength="20"
                       class="laydate-icon form-control layer-date "
                       value="<fmt:formatDate value="${bmsProduct.reviewTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">状态：</label></td>
            <td class="width-35">
                <form:select path="status" class="form-control required">
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
        </tbody>
    </table>
</form:form>
</body>
</html>