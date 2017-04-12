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
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/index.js"></script>
<script type="text/javascript">
	$(document).ready(
			function() {
				cangku = $("#cangku").datagrid({
					striped : true, //奇行不同颜色
					pageList : [ 1, 3, 5 ],
					fitColumns : true, //列自适应
					pageSize : 3,
					loadMsg : '正在加载数据.......', //当从远程站点载入数据时，显示的一条快捷信息
					pagination : true, //设置true将在数据表格底部显示分页工具栏
					idField : 'flowId', //表明该列是一个唯一列。
					rownumbers : true, //设置为true将显示行数 ,
					singleSelect : true,
					url : 'warehouse.do',
					toolbar : [ {
						id : 'btnupdate',
						text : '增加',
						iconCls : 'icon-add',
						handler : function() {
							addwarehouse();
						}
					}, '-',

					{
						id : 'btnupdate',
						text : '编辑',
						iconCls : 'icon-edit',
						handler : function() {
							editwarehouse();
						}
					}, '-' ]

				});

				function cleanselect() {
					$("#warehouseName").combobox('setValue', '');
					$("#warehouseAddress").combobox('setValue', '');
					$("#warehouseCompany").combobox('setValue', '');
					$("#warehouseSize").combobox('setValue', '');
				}

				function cangkuselect() {

					var warehouseName = $("#warehouseName")
							.combobox('getValue');
					var warehouseAddress = $("#warehouseAddress").combobox(
							'getValue');
					var warehouseCompany = $("#warehouseCompany").combobox(
							'getValue');
					var warehouseSize = $("#warehouseSize")
							.combobox('getValue');
					cangku.datagrid('load', {
						'warehouseName' : warehouseName,
						'warehouseAddress' : warehouseAddress,
						'warehouseCompany' : warehouseCompany,
						'warehouseSize' : warehouseSize
					});
				}

				function addwarehouse() {
					$("#addwarehouse").window('open');
				}
				function editwarehouse() {
					var row = $("#cangku").datagrid('getSelected');
					if (row) {
						$("#editwarehouse").window('open');
						$("#edit-warehouseName").textbox('setValue',
								row.warehouseName);
						$("#edit-warehouseAddress").textbox('setValue',
								row.warehouseAddress);
						$("#edit-warehouseSize").textbox('setValue',
								row.warehouseSize);
						$("#edit-warehouseCompany").textbox('setValue',
								row.warehouseCompany);
					} else {
						alert("请先选择仓库");
					}
				}

			})
	function addwarehousesubmit() {
		if ($("#warehouseName").val() == "" || $("#warehouseAddress").val == ""
				|| $("#warehouseSize").val == ""
				|| $("#warehouseCompany").val == "") {
			alert("信息不能为空");
		} else {
			$("#addwarehousesubmit").submit();
			$("#addwarehouse").window('close');
		}
	}
	function editwarehousesubmit() {
		if ($("#edit-warehouseName").val() == ""
				|| $("#edit-warehouseAddress").val == ""
				|| $("#edit-warehouseSize").val == ""
				|| $("#edit-warehouseCompany").val == "") {
			alert("信息不能为空");
		} else {
			$("#editwarehousesubmit").submit();
			$("#editwarehouse").window('close');
		}
	}
</script>
</head>
<body>
	<div class="easyui-tabs" style="width: 100%; height: 635px;">
		<div title="仓库管理" style="display: none;">
			<br>
			<br>

			<table id="cangku" style="height: 435px" class="easyui-datagrid">
				<thead>
					<tr>
						<th align="center" field="warehouseName" width="200">仓库名</th>
						<th align="center" field="warehouseAddress" width="200">仓库地址</th>
						<th field="warehouseSize" align="center" width="200">仓库大小</th>
						<th align="center" field="createTime" width="200">创建时间</th>
					</tr>
				</thead>

			</table>
			<div id="addwarehouse" class="easyui-window" title="添加仓库"
				style="width: 400px; height: 200px;"
				data-options="iconCls:'icon-save',modal:true,closed:true">
				<div class="easyui-layout" data-options="fit:true">
					<div data-options="region:'center'"><%@ include
							file="cangku/addwarehouse.jsp"%></div>
				</div>
			</div>
			<div id="editwarehouse" class="easyui-window" title="编辑仓库"
				style="width: 400px; height: 200px;"
				data-options="iconCls:'icon-save',modal:true,closed:true">
				<div class="easyui-layout" data-options="fit:true">
					<div data-options="region:'center'"><%@ include
							file="cangku/editwarehouse.jsp"%></div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>