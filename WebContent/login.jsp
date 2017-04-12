<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page isELIgnored="false"%>
<html>
<head>
<style type="text/css">
.kuang {
	width: 100%;
	height: 650px;
	background-size: 100% 650px;
	background-attachment: fixed;
	background-image: url('images/beijing.jpg');
	background-repeat: no-repeat;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-2.0.0.js"></script>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/jquery-easyui-1.4.1/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/jquery-easyui-1.4.1/themes/icon.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/index.css" type="text/css"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/index.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
<script type="text/javascript">
  $(document).ready(function(){
	  var result = ${result};
	  if(result.result != ""){
		  alert(result.result);
	  }
  })
</script>
</head>
<body>
	<div class="kuang">
          <span style="font-size:24px;color:#ffffff;position:absolute;top:20%;left: 45%;">进销存管理系统</span>
		<div
			style="width: 300px; height: 300px; background: #ffffff; top: 30%; left: 40%; position: absolute;">

			<div style="margin-bottom: 40px;"></div>
			<div style="margin-left: 40px;">
				<span style="color: #3366cc; font-size: 20px;">账户登录</span>
				<div style="margin-bottom: 20px;"></div>
				<form action="login.do" method="post">
					<input type="text" style="width:200px;height:30px; border:1px solid #cccccc; padding-left:10px;" name="username" placeholder="账户名">
					<div style="margin-bottom: 20px;"></div>
					<input type="password"  style="width:200px;height:30px;border:1px solid #cccccc;padding-left:10px;"  name="password" placeholder="密码"> <input type="hidden"
						value="1" name="currentpage"> <input type="hidden"
						value="10" name="pagenum">
					<div style="margin-bottom: 20px;"></div>
					<input type="submit" style="width:200px;height:30px;background:#3366cc;color:#ffffff;font-size:18px;" value="登录">
				</form>


			</div>
		</div>
		</div>
</body>
</html>