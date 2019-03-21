<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<%@ include file="/webpage/include/echarts.jsp"%>
<html>
<head>
	<title>首页</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
		     WinMove();
		});
	</script>
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content">
        <div class="row">

        </div>
    </div>

    <!-- 全局js -->
  
    <!-- Flot -->
    <script src="${ctxStatic}/flot/jquery.flot.js"></script>
    <script src="${ctxStatic}/flot/jquery.flot.tooltip.min.js"></script>
    <script src="${ctxStatic}/flot/jquery.flot.spline.js"></script>
    <script src="${ctxStatic}/flot/jquery.flot.resize.js"></script>
    <script src="${ctxStatic}/flot/jquery.flot.pie.js"></script>

    <!-- Peity -->
    <script src="${ctxStatic}/peity/jquery.peity.min.js"></script>
    <script src="${ctxStatic}/demo/peity-demo.js"></script>

    <!-- 自定义js -->
    <script src="${ctxStatic}/demo/content.js?v=1.0.0"></script>


    <!-- jQuery UI -->
    <script src="${ctxStatic}/jquery-ui/jquery-ui.min.js"></script>

    <!-- GITTER -->
    <script src="${ctxStatic}/gritter/jquery.gritter.min.js"></script>

    <!-- EayPIE -->
    <script src="${ctxStatic}/easypiechart/jquery.easypiechart.js"></script>

    <!-- Sparkline -->
    <script src="${ctxStatic}/sparkline/jquery.sparkline.min.js"></script>

    <!-- Sparkline demo data  -->
    <script src="${ctxStatic}/demo/sparkline-demo.js"></script>

    <script>
        $(document).ready(function () {
            WinMove();

            $('.chart').easyPieChart({
                barColor: '#f8ac59',
                //                scaleColor: false,
                scaleLength: 5,
                lineWidth: 4,
                size: 80
            });

            $('.chart2').easyPieChart({
                barColor: '#1c84c6',
                //                scaleColor: false,
                scaleLength: 5,
                lineWidth: 4,
                size: 80
            });

            var data1 = [
                [0, 4], [1, 8], [2, 5], [3, 10], [4, 4], [5, 16], [6, 5], [7, 11], [8, 6], [9, 11], [10, 30], [11, 10], [12, 13], [13, 4], [14, 3], [15, 3], [16, 6]
            ];
            var data2 = [
                [0, 1], [1, 0], [2, 2], [3, 0], [4, 1], [5, 3], [6, 1], [7, 5], [8, 2], [9, 3], [10, 2], [11, 1], [12, 0], [13, 2], [14, 8], [15, 0], [16, 0]
            ];
            $("#flot-dashboard-chart").length && $.plot($("#flot-dashboard-chart"), [
                data1, data2
            ], {
                series: {
                    lines: {
                        show: false,
                        fill: true
                    },
                    splines: {
                        show: true,
                        tension: 0.4,
                        lineWidth: 1,
                        fill: 0.4
                    },
                    points: {
                        radius: 0,
                        show: true
                    },
                    shadowSize: 2
                },
                grid: {
                    hoverable: true,
                    clickable: true,
                    tickColor: "#d5d5d5",
                    borderWidth: 1,
                    color: '#d5d5d5'
                },
                colors: ["#1ab394", "#464f88"],
                xaxis: {},
                yaxis: {
                    ticks: 4
                },
                tooltip: false
            });
        });
    </script>

  
</body>

</html>