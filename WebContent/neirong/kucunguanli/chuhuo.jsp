<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page isELIgnored="false"%>
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
	src="${pageContext.request.contextPath}/js/index.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/index.js"></script>
<script type="text/javascript">
	var caozuoId = 0;

	$(document).ready(function() {

		grid = $("#luku").datagrid({

			nowrap : true, //设置为true，当数据长度超出列宽时将会自动截取。
			striped : true,
			pageList : [ 1, 3, 5 ],
			fitColumns : true,
			pageSize : 3,
			loadMsg : '正在加载数据.......', //当从远程站点载入数据时，显示的一条快捷信息
			pagination : true, //设置true将在数据表格底部显示分页工具栏
			idField : 'flowId', //表明该列是一个唯一列。
			rownumbers : true, //设置为true将显示行数 ,
			singleSelect : true,
			url : 'dengdaichuhuo.do',
			toolbar : [ {
				id : 'btnupdate',
				text : '清空',
				iconCls : 'icon-clear',
				handler : function() {
					cleanselect();
				}
			}, '-', {
				id : 'btnupdate',
				text : '出库',
				iconCls : 'icon-chuku',
				handler : function() {
					ruku();
				}
			}, '-', {
				id : 'btnupdate',
				text : '撤销',
				iconCls : 'icon-chexiao',
				handler : function() {
					tuihuo();
				}
			}, '-', {
				id : 'btnupdate',
				text : '查询',
				iconCls : 'icon-search',
				handler : function() {
					jinghuoselect();
				}
			}, '-' ]

		});
		function tuihuo() {
			var row = $('#luku').datagrid('getSelected');
			if (row) {
				$.ajax({
					type : 'post',
					data : row,
					dataType : 'json',
					url : 'chuhuochexiao.do',
					success : function(data) {
						alert(data.result)
						grid.datagrid('reload');
					}

				});
			}
		}

		function ruku() {
			var row = $('#luku').datagrid('getSelected');
			if (row) {

				$.ajax({
					type : 'post',
					data : row,
					dataType : 'json',
					url : 'chuku.do',
					success : function(data) {
						alert(data.result);
						grid.datagrid('reload');
					}

				});
			}

			else {
				alert("请先选择入库单");
			}

		}
		function cleanselect() {

			$("#rukudanstatus").combobox('setValue', '');
			$("#rukudanid").combobox('setValue', '');
		}

		function jinghuoselect() {
			var xiaoshouId = $("#rukudanid").combobox('getValue');
			if (xiaoshouId == "") {
				xiaoshouId = 0;
			}
			var username = $("#rukudanstatus").combobox('getValue');

			grid.datagrid('load', {
				'xiaoshouId' : xiaoshouId,
				'username' : username
			});
		}

		function xiangxi() {
			var row = $("#luku").datagrid('getSelected');
			if (row) {
				$("#xiangxi").datagrid({
					title : '信息',
					iconCls : 'icon-save',
					nowrap : true, //设置为true，当数据长度超出列宽时将会自动截取。
					striped : true,
					pageList : [ 5, 10, 15 ],
					fitColumns : false,
					pageSize : 5,
					loadMsg : '正在加载数据.......', //当从远程站点载入数据时，显示的一条快捷信息
					pagination : true, //设置true将在数据表格底部显示分页工具栏
					idField : 'flowId', //表明该列是一个唯一列。
					rownumbers : true, //设置为true将显示行数 ,
					singleSelect : true,
					url : 'goodschaxun.do'
				});
				$("#xiangxi").datagrid('load', {
					"goodsId" : row.goodsId
				}), $("#xiangxi1").window('open');
			} else {
				alert("请先选择订单");
			}
		}
	})
	function a(v) {

		return '<img width="100" height="50" src="'+v+'"/>';
	}
	function rukuexport() {
		var t_form = $("<form action='rukudanExport.do' method='post' name='form1'></form>");
		$("body").append(t_form);
		$("form[name='form1']").submit();
	}
</script>
</head>
<body class="easyui-layout">
	<!--  <div class="neirong1">
		<input height="20" style="position: relative; top: 20px; left: 40px;"
			type="text" placeholder="搜索">
		<div
			style="width: 120px; height: 30px; background: #0ce3e3; font-size: 14px; color: #ffffff; line-height: 30px; position: relative; left: 950px; top: -5px;">
			<center>新增入库单</center>
		</div>
	</div>
	<div class="neirong2">-->
	<div class="easyui-tabs" style="width: 100%; height: 600px;">
		<div title="出库管理" style="padding-top: 20px; display: none;">
			<table id="searchTable" align="left" fit="true">
				<tr>
					<td width="110px" align="right">出库单编号:</td>
					<td><select id="rukudanid" style="width: 100px; height: 21px;"
						data-options="valueField:'xiaoshouId',textField:'xiaoshouId',url:'chuhuoselect.do'"
						class="easyui-combobox" editable="false">
					</select></td>
					<td width="90px" align="right">出库用户:</td>
					<td><select id="rukudanstatus"
						style="width: 100px; height: 21px;"
						data-options="valueField:'username',textField:'username',url:'renyuanselect1.do'"
						class="easyui-combobox" editable="false">
					</select></td>
					<td width="90px" align="right"></td>
					<td></td>
					<td width="90px" align="right"></td>
					<td></td>

				</tr>
			</table>
			<br>
			<br>
			<br>
			<br>
			<table id="luku" class="easyui-datagrid" style="height: 430px;">
				<thead style="background: #273045; color: #ffffff;">
					<tr>
						<th align="center" width="100" field="xiaoshouId">出库单编号</th>
						<th align="center" name="kucunId" field="kucunId" width="100">库存ID</th>
						<th align="center" field="goodsName" width="100">货物名称</th>
						<th align="center" name="goodsImage" data-options="formatter:a"
							field="goodsImage" width="150">货物图片</th>
						<th align="center" name="goodsNumber" field="goodsNumber"
							width="100">货物数量</th>

						<th align="center" name="sumpPrice" field="sumPrice" width="100">货物总价</th>
						<th align="center" name="username" field="username" width="100">出库用户</th>
						<th field="createTime" align="center" width="100">创建时间</th>
					</tr>
				</thead>
			</table>
			<div id="xiangxi1" class="easyui-window" title="详细信息"
				style="width: 1000px; height: 200px;"
				data-options="iconCls:'icon-save',modal:true,closed:true">

				<table id="xiangxi" class="easyui-datagrid">
					<thead style="background: #273045; color: #ffffff;">
						<tr>
							<th align="center" width="100" field="goodsId">货物编号</th>
							<th align="center" name="goodsName" field="goodsName" width="100">货物名称</th>

							<th align="center" name="goodsImage" field="goodsImage"
								data-options="formatter:a" width="100">货物图片</th>
							<th align="center" name="goodsLei" field="goodsLei" width="100">货物类别</th>

							<th align="center" name="goodsUnit" field="goodsUnit" width="100">货物单位</th>
							<th align="center" name="goodsPrice" field="goodsPrice"
								width="100">货物价格</th>
							<th align="center" name="supplierName" field="supplierName"
								width="100">货物单位</th>
							<th field="createTime" align="center" width="200">创建时间</th>
						</tr>
					</thead>
				</table>

			</div>

		</div>
	</div>
</body>
</html>