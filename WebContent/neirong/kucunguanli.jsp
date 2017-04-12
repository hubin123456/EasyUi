<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/jquery-easyui-1.4.1/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/jquery-easyui-1.4.1/themes/icon.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/index.css" type="text/css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-2.0.0.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/guanli.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
</head>
<body>
	<div class="easyui-tabs" style="width: 1100px; height: 400px;">
		<div title="入库管理" data-options="iconCls:'icon-reload'"
			style="padding: 20px; display: none;">
			<%@ include file="kucunguanli/jinghuo.jsp"%>
		</div>
		<div title="出库管理" data-options="iconCls:'icon-reload'"
			style="overflow: auto; padding: 20px; display: none;">
			<%@ include file="kucunguanli/chuhuo.jsp"%>
		</div>
		<div title="库存管理" data-options="iconCls:'icon-reload'"
			style="padding: 20px; display: none;">
			<%@ include file="kucunguanli/kucun.jsp"%>
		</div>
	</div>
</body>
</html>