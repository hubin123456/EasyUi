<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page isELIgnored="false"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf—8">
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
<script type="text/javascript">
	$(document)
			.ready(
					function() {

						kucun = $("#kucun").datagrid({

							nowrap : true, //设置为true，当数据长度超出列宽时将会自动截取。
							striped : true,
							pageList : [ 1, 3, 5 ],
							fitColumns : false,
							pageSize : 3,
							loadMsg : '正在加载数据.......', //当从远程站点载入数据时，显示的一条快捷信息
							pagination : true, //设置true将在数据表格底部显示分页工具栏
							//idField : 'flowId', //表明该列是一个唯一列。
							rownumbers : true, //设置为true将显示行数 ,
							singleSelect : false,
							url : 'kucunyujing1.do',
							toolbar : [ {
								id : 'btnupdate',
								text : '清空',
								iconCls : 'icon-clear',
								handler : function() {
									cleanselect();
								}
							}, '-', {
								id : 'btnupdate',
								text : '查询',
								iconCls : 'icon-search',
								handler : function() {
									kucunselect();
								}
							}, {
								id : 'btnupdate',
								text : '导出Excel',
								iconCls : 'icon-daochu',
								handler : function() {
									excelExport();
								}
							}, '-' ]

						});

						function excelExport() {
							var t_form = $("<form action='kucunyujingExport.do' method='post' name='form1'></form>");
							$("body").append(t_form);
							$("form[name='form1']").submit();
						}
						function cleanselect() {

							$("#kucunId").combobox('setValue', '');
							$("#warehouseName").combobox('setValue', '');

						}
						function kucunselect() {
							var kucunId = $("#kucunId").combobox('getValue');
							if (kucunId == "") {
								kucunId = 0;
							}

							var warehouseName = $("#warehouseName").combobox(
									'getValue');

							kucun.datagrid('load', {
								'kucunId' : kucunId,

								'warehouseName' : warehouseName,

							});
						}

					})
	function a(v) {

		return '<img width="100" height="50" src="'+v+'"/>';
	}
</script>
</head>
<body>
	<div class="easyui-tabs" style="width: 100%; height: 600px;">
		<div title="库存预警" style="padding-top: 20px; display: none;">
			<table id="searchTable" align="left" fit="true">
				<tr>
					<td width="110px" align="right">库存编号:</td>
					<td><select id="kucunId" style="width: 100px; height: 21px;"
						data-options="valueField:'kucunId',textField:'kucunId',url:'kucunselect.do'"
						class="easyui-combobox" editable="false">
					</select></td>

					<td width="110px" align="right">仓库名:</td>
					<td><select id="warehouseName"
						style="width: 100px; height: 21px;"
						data-options="valueField:'warehouseName',textField:'warehouseName',url:'cangkuselect.do'"
						class="easyui-combobox" editable="false">
					</select></td>
					<td width="110px" align="right"></td>
					<td></td>
					<td width="110px" align="right"></td>
					<td></td>
				</tr>
			</table>
			<br> <br> <br> <br>
			<table id="kucun" style="height: 430px;" class="easyui-datagrid">
				<thead style="background: #273045; color: #ffffff;">
					<tr>
						<th align="center" width="100" field="kucunId">库存编号</th>

						<th align="center" field="warehouseName" width="100">仓库名</th>
						<th align="center" field="goodsName" width="100">货物名称</th>
						<th align="center" name="goodsImage" data-options="formatter:a"
							field="goodsImage" width="150">货物图片</th>

						<th align="center" field="status" width="100">库存状态</th>
					</tr>
				</thead>

			</table>
		</div>
	</div>
</body>
</html>