<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<script type="text/javascript">
	
</script>
</head>
<body>
	<div style="margin: 20px 0;"></div>
	<form id="createuserform">
		<div style="margin-bottom: 20px; margin-left: 90px">
			用户名:<input id="username" name="username" class="easyui-textbox"
				data-options="prompt:'输入用户名'" style="width: 200px; height: 32px">
		</div>
		<div style="margin-bottom: 20px; margin-left: 90px">
			密 码:<input id="password" name="password" class="easyui-textbox"
				data-options="prompt:'输入密码'" style="width: 200px; height: 32px">
		</div>
		<div style="margin-bottom: 20px; margin-left: 90px">
			角 色:<select id="role" name="role" style="width: 100px; height: 21px;"
				data-options="valueField:'role',textField:'role',url:'roleselect.do'"
				class="easyui-combobox" editable="false"></select>
		</div>
		<div style="margin-bottom: 20px; margin-left: 90px">
			地 址:<input id="address" name="address" class="easyui-textbox"
				data-options="prompt:'输入地址'" style="width: 200px; height: 32px">
		</div>
		<div style="margin-bottom: 20px; margin-left: 90px">
			电 话:<input id="phone" name="phone" class="easyui-textbox"
				data-options="prompt:'输入联系方式'" style="width: 200px; height: 32px">
		</div>
		<div>
			<input type="button" onClick="submit1()" class="easyui-linkbutton"
				iconCls="icon-ok" style="width: 100%; height: 32px" value="创建">
		</div>
	</form>
</body>
</html>