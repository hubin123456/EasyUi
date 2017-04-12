<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<script type="text/javascript">
	$(document).ready(function() {
		$("#exit").click(function() {
			location.href = "/EasyUi/exit.do";
		})

		$("#xiugaimima").click(function() {
			$("#xiugaimima1").window('open');

		})
		$('#xiugaimima1').window({
			onBeforeClose : function() {

				$("#jiumima").textbox('setValue', '');
				$("#xinmima").textbox('setValue', '');
				$("#xinmima1").textbox('setValue', '');
			}
		});
	})
	function xiugai1() {
		var jiumima = $("#jiumima").textbox('getValue');
		var xinmima = $("#xinmima").textbox('getValue');
		var xinmima1 = $("#xinmima1").textbox('getValue');
		$.ajax({
			type : 'post',
			data : {
				"username" : $("#username").val(),
				"jiumima" : jiumima,
				"xinmima" : xinmima,
				"xinmima1" : xinmima1
			},
			url : 'xiugaimima.do',
			dataType : 'json',
			success : function(data) {
				if (data.result == 0) {
					alert("修改成功");
					$("#xiugaimima1").window('close');
				} else if (data.result == 1) {
					alert("旧密码错误");
				} else if (data.result == 2) {
					alert("两次密码不同");
				}

			}

		})

	}
</script>
</head>
<body>
	<div class="tou" region="north">
		&nbsp;&nbsp;&nbsp; <img src="images/Computer.png"
			style="position: relative; top: 5px;" width="50" height="50"></img>
		&nbsp;&nbsp;&nbsp;<span
			style="font-size: 24px; position: relative; top: -10px;">进销存管理系统</span>
		<input type="hidden" value=${user.username } id="username"> <span
			style="font-size: 14px; position: relative; left: 350px; top: -10px;">当前用户:${user.username}</span>&nbsp;<span
			style="position: relative; left: 350px;"><img
			src="${user.userImage}" width="50" height="50"></img></span> <span
			style="position: relative; left: 450px;"><img
			src="images/juese.png" width="30" height="30"></img></span><span
			style="font-size: 14px; position: relative; left: 450px; top: -10px;">角色:</span><span
			style="font-size: 14px; left: 450px; position: relative; top: -10px;"
			id="userrole">${user.role}</span> <span
			style="position: relative; left: 550px;"><img
			src="images/xiugaimima.png" width="30" height="30"></img></span><span
			id="xiugaimima"
			style="font-size: 14px; position: relative; left: 550px; top: -10px;">修改密码</span>
		<span style="position: relative; left: 580px;"><img
			src="images/tuichu.png" width="30" height="30"></img></span><span id="exit"
			style="font-size: 14px; position: relative; top: -10px; left: 580px;">退出</span>

	</div>
	<div id="xiugaimima1" class="easyui-window" title="修改密码"
		style="width: 400px; height: 250px; display: none;"
		data-options="iconCls:'icon-save',modal:true,closed:true">

		<center>

			<div style="margin-bottom: 20px;"></div>

			输入旧密码:<input type="password" style="width: 150px; height: 25px;"
				class="easyui-textbox" id="jiumima">
			<div style="margin-bottom: 20px;"></div>
			输入新密码:<input type="password" style="width: 150px; height: 25px;"
				class="easyui-textbox" id="xinmima">
			<div style="margin-bottom: 20px;"></div>
			确认新密码:<input type="password" style="width: 150px; height: 25px;"
				class="easyui-textbox" id="xinmima1">
			<div style="margin-bottom: 20px;"></div>
			<input type="button" onClick="xiugai1()" class="easyui-linkbutton"
				iconCls="icon-ok" style="width: 100%; height: 32px" value="修改">
		</center>

	</div>
</body>
</html>