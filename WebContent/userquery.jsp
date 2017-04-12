<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<script type="text/javascript" src="js/userquery.js"></script>
<script type="text/javascript" src="js/jquery-2.0.0.js"></script>
</head>
<body>
<form action = "login.do" method="post">
用户id:<input type="text" name="userId">
<br><br>用户名:<input type="text" name="userName">
<br><br>地址:<input type="text" name="address">
<input type="hidden" value="1" id="currentpage1" name="currentpage">
<input type="hidden" value="10" id="pagenum1" name="pagenum">
<input type="submit" value="查询">
</form>
<br><br>
<table>
<tr><th>用户Id</th><th>用户名</th><th>用户地址</th><th>用户电话</th><th>创建时间</th></tr>
<c:forEach items="${User}"  var="user">
 <tr><td><center>${user.userId}</center>
 <td><center>${user.userName}</center></td>
 <td><center>${user.address}</center></td>
 <td><center>${user.phone}</center></td>
 <td><center>${user.createTime}</center></td></tr>
</c:forEach>
</table>
<br><br>每页记录数<select id="pagenum" onChange="change(${page.currentpage})"><option>${page.pagenum}</option><option>10</option><option>20</option><option>30</option></select>,总记录数<span id="count">${page.count}</span>,当前页数<span id="currentpage">${page.currentpage}</span>。<a href="#"  onClick="shou()">首页</a><a href="#"  onClick="shang(${page.currentpage})">上一页</a><a href="#"  onClick="xia(${page.currentpage})">下一页</a><a href="#"  onClick="wei()">尾页</a>
</body>
</html>