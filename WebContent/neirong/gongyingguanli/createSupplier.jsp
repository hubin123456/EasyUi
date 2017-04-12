<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
	<div style="margin: 20px 0;"></div>
	<form id="createsupplierform">
		<div style="margin-bottom: 20px; margin-left: 90px">
			供应商名称:<input id="supplierName" name="supplierName"
				class="easyui-textbox" data-options="prompt:'输入供应商名称'"
				style="width: 200px; height: 32px">
		</div>
		<div style="margin-bottom: 20px; margin-left: 90px">
			联系方式:<input id="supplierPhone" name="supplierPhone"
				class="easyui-textbox" data-options="prompt:'输入联系方式'"
				style="width: 200px; height: 32px">
		</div>
		<div style="margin-bottom: 20px; margin-left: 90px">
			地址:<input id="supplierAddress" name="supplierAddress"
				class="easyui-textbox" data-options="prompt:'输入地址'"
				style="width: 200px; height: 32px">
		</div>
		<div>
			<input type="button" onClick="submit1()" class="easyui-linkbutton"
				iconCls="icon-ok" style="width: 100%; height: 32px" value="创建">
		</div>
	</form>
</body>
</html>