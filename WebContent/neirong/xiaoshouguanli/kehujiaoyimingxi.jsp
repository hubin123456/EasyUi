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
		$("#kehuName").combobox({
			valueField : 'kehuName',
			textField : 'kehuName',
			url : 'kehuselect.do',
			onLoadSuccess : function() {
				var data = $('#kehuName').combobox('getData');
				$("#kehuName").combobox('select', data[0].kehuName);
				select($('#kehuName').combobox('getValue'));

			},
			onSelect : function(record) {
				select(record.kehuName);
				select(record.kehuName);
			}
		});

	})

	function select(kehuName) {
		grid = $("#luku").datagrid({

			nowrap : true, //设置为true，当数据长度超出列宽时将会自动截取。
			striped : true,
			pageList : [ 1, 3, 5 ],
			fitColumns : false,
			pageSize : 3,
			loadMsg : '正在加载数据.......', //当从远程站点载入数据时，显示的一条快捷信息
			pagination : true, //设置true将在数据表格底部显示分页工具栏
			idField : 'flowId', //表明该列是一个唯一列。
			rownumbers : true, //设置为true将显示行数 ,
			singleSelect : true,
			queryParams : {
				"kehuName" : kehuName
			},
			url : 'kehujiaoyi1.do',
			toolbar : [ {
				id : 'btnupdate',
				text : '导出',
				iconCls : 'icon-daochu',
				handler : function() {
					export2(kehuName);
				}
			}, '-' ]

		});
	}
	function a(v) {

		return '<img width="100" height="50" src="'+v+'"/>';
	}
	function export2(kehuName) {

		var t_form = $("<form action='kehujiaoyiExport.do' method='post' name='form1'><input type='hidden' name='kehuName' value="+kehuName+"> </form>");
		$("body").append(t_form);
		$("form[name='form1']").submit();
	}
</script>
</head>
<body class="easyui-layout">
	<div class="easyui-tabs" style="width: 100%; height: 600px;">
		<div title="客户交易明细" style="padding-top: 20px; display: none;">
			&nbsp;客户名称:&nbsp;<select id="kehuName"
				style="width: 100px; height: 21px;" editable="false"></select> <br>
			<br>
			<table id="luku" class="easyui-datagrid" style="height: 450px;">
				<thead style="background: #273045; color: #ffffff;">
					<tr>
						<th align="center" width="100" field="caozuoId">单子编号</th>
						<th align="center" width="100" field="kucunId">库存编号</th>
						<th align="center" field="goodsName" width="100">货物名称</th>
						<th align="center" name="goodsImage" data-options="formatter:a"
							field="goodsImage" width="150">货物图片</th>
						<th align="center" name="goodsNumber" field="goodsNumber"
							width="100">货物数量</th>

						<th align="center" name="username" field="username" width="100">销售人员</th>
						<th align="center" name="status" field="status" width="100">状态</th>
						<th align="center" name="sumPrice" field="sumPrice" width="100">交易总额</th>
						<th field="createTime" align="center" width="200">交易时间</th>
					</tr>
				</thead>
			</table>


		</div>
	</div>
</body>
</html>