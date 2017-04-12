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
	<div id="tt" class="easyui-tabs" style="width: 1100px; height: 400px;">
		<div title="人员管理" data-options="iconCls:'icon-reload'"
			style="padding: 20px; display: none;">
			<%@ include file="richangguanli/renyuan.jsp"%>
		</div>
		<div title="仓库管理" data-options="iconCls:'icon-reload'"
			style="overflow: auto; padding: 20px; display: none;">
			<%@ include file="richangguanli/cangku.jsp"%>
		</div>
		<div title="权限管理" data-options="iconCls:'icon-reload'"
			style="padding: 20px; display: none;">
			<%@ include file="richangguanli/renyuan.jsp"%>
		</div>
	</div>
</body>
</html>