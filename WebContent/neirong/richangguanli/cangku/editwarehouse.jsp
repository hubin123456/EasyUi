<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
	<div style="margin-bottom: 20px;"></div>
	<form id="editwarehousesubmit" action="editwarehouse.do" method="post">
		<div style="margin-bottom: 20px; margin-left: 30px">
			仓库名称:<input id="edit-warehouseName" name="warehouseName"
				class="easyui-textbox" data-options="prompt:'输入仓库名'"
				style="width: 100px; height: 32px"> &nbsp;仓库地址:<input
				id="edit-warehouseAddress" name="warehouseAddress"
				class="easyui-textbox" data-options="prompt:'输入仓库地址'"
				style="width: 100px; height: 32px">
		</div>
		<div style="margin-bottom: 20px; margin-left: 30px">
			仓库大小:<input id="edit-warehouseSize" name="warehouseSize"
				class="easyui-textbox" data-options="prompt:'输入仓库大小'"
				style="width: 100px; height: 32px">

		</div>
		<div>
			<input type="button" onClick="editwarehousesubmit()"
				class="easyui-linkbutton" iconCls="icon-ok"
				style="width: 100%; height: 32px" value="修改">
		</div>
	</form>
</body>
</html>