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
	src="${pageContext.request.contextPath}/js/index.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		kucunpandian = $("#kucunpandian").datagrid({

			nowrap : true, //设置为true，当数据长度超出列宽时将会自动截取。
			striped : true,
			pageList : [ 1, 3, 5 ],
			fitColumns : true,
			pageSize : 3,
			loadMsg : '正在加载数据.......', //当从远程站点载入数据时，显示的一条快捷信息
			pagination : true, //设置true将在数据表格底部显示分页工具栏
			//idField : 'flowId', //表明该列是一个唯一列。
			rownumbers : true, //设置为true将显示行数 ,
			singleSelect : true,
			url : 'kucunpandian2.do',
			toolbar : [ {
				id : 'btnupdate',
				text : '新增盘点表单',
				iconCls : 'icon-add',
				handler : function() {
					addpandian();
				}
			}, '-', {
				id : 'btnupdate',
				text : '删除',
				iconCls : 'icon-clear',
				handler : function() {
					shanchu();
				}
			}, '-' ]

		});

		$('#kucunId').combobox({
			valueField : 'kucunId', //值字段  
			textField : 'kucunId', //显示的字段  
			url : 'kucunselect.do',
			panelHeight : 'auto',
			required : true,
			editable : true,//不可编辑，只能选择  
			onChange : function(kucunId) {
				kucun = $("#kucun").datagrid({
					title : '信息',
					iconCls : 'icon-save',
					nowrap : true, //设置为true，当数据长度超出列宽时将会自动截取。
					striped : true,
					pageList : [ 5, 10, 15 ],
					queryParams : {
						"kucunId" : kucunId
					},
					fitColumns : false,
					pageSize : 5,
					loadMsg : '正在加载数据.......', //当从远程站点载入数据时，显示的一条快捷信息
					pagination : true, //设置true将在数据表格底部显示分页工具栏
					//idField : 'flowId', //表明该列是一个唯一列。
					rownumbers : true, //设置为true将显示行数 ,
					singleSelect : true,
					url : 'kucun1.do'
				})
			}
		});

		$('#kucunyujing2').window({
			onBeforeClose : function() {

				$('#kucunId').combobox('setValue', '');
				//alert($('#goodsNamediaochu').combobox('getValue'));
				$('#kucunpandianNumber').textbox('setValue', '');
				$('#username').combobox('setValue', '');

			}
		});
	})
	function addpandian() {
		$("#kucunyujing2").window('open');
	}
	function shanchu() {
		var row = $('#kucunpandian').datagrid('getSelected');
		if (row) {
			$.ajax({
				url : 'deletepandian.do',
				type : 'post',
				data : {
					"kucunId" : row.kucunId
				},
				dataType : 'json',
				success : function(data) {

					alert(data.result);
					$("#kucunpandian").datagrid('reload');
				}
			})
		} else {
			alert("请先选择盘点单");
		}
	}
	function a(v) {

		return '<img width="100" height="50" src="'+v+'"/>';
	}
	function xinzeng() {
		if (!isNaN($("#kucunpandianNumber").textbox('getValue'))) {
			if ($("#kucunId").combobox('getValue') == ""
					|| $("#kucunpandianNumber").textbox('getValue') == ""
					|| $("#username").combobox('getValue') == "") {

				alert("信息不能为空");
			} else {
				$.ajax({
					type : 'post',
					data : $("#editkucunyujing2").serialize(),
					url : 'addkucunpandian.do',
					dataType : 'json',
					success : function(data) {
						alert(data.result);
						if (data.result == "提交成功") {
							$("#kucunyujing2").window('close');
							$("#kucunpandian").datagrid('reload');
						}
					}
				})
			}
		} else {
			alert("必须为数字");
		}
	}
</script>
</head>
<body>
	<div class="easyui-tabs" style="width: 100%; height: 600px;">
		<div title="库存盘点" style="padding-top: 20px; display: none;">

			<br> <br>
			<table id="kucunpandian" class="easyui-datagrid"
				style="height: 450px;">
				<thead>
					<tr>
						<th align="center" width="100" field="kucunpandianId">库存盘点编号</th>
						<th align="center" field="kucunId" width="100">库存编号</th>
						<th align="center" field="goodsName" width="100">货物名称</th>
						<th align="center" name="goodsImage" data-options="formatter:a"
							field="goodsImage" width="150">货物图片</th>
						<th align="center" field="kucunpandianNumber" width="150">实际数量</th>
						<th align="center" field="numbercha" width="150">数量差</th>
						<th align="center" field="username" width="100">盘点人员</th>

						<th align="center" field="createTime" width="100">盘点时间</th>

					</tr>
				</thead>

			</table>
			<div id="kucunyujing2" class="easyui-window" title="新增盘点表单"
				style="width: 1000px; height: 300px;"
				data-options="iconCls:'icon-save',modal:true,closed:true">

				<div data-options="region:'center'">
					<div style="margin-bottom: 20px;"></div>
					<form id="editkucunyujing2" method="post">
						<center>
							选择要盘点的库存:<select name="kucunId" id="kucunId"
								style="width: 100px; height: 21px;"></select>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 经办人:<select
								id="username" style="width: 100px; height: 21px;"
								name="username"
								data-options="valueField:'username',textField:'username',url:'renyuanselect1.do'"
								class="easyui-combobox" editable="false"></select>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 实际数量:<input
								id="kucunpandianNumber" name="kucunpandianNumber"
								style="width: 100px; height: 21px;" class="easyui-textbox"
								data-options="prompt:'输入数量'">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input
								style="width: 100px; height: 21px;" class="easyui-linkbutton"
								onClick="xinzeng()" value="新增">
						</center>


					</form>
					<br>
					<br>
					<br>
					<table id="kucun" class="easyui-datagrid">
						<thead style="background: #273045; color: #ffffff;">
							<tr>
								<th align="center" width="100" field="kucunId">库存编号</th>
								<th align="center" field="goodsName" width="100">货物名称</th>
								<th align="center" name="goodsImage" data-options="formatter:a"
									field="goodsImage" width="150">货物图片</th>
								<th align="center" field="goodsLei" width="100">货物类别</th>
								<th align="center" field="goodsUnit" width="100">货物单位</th>
								<th align="center" field="goodsPrice" width="100">货物价格</th>
								<th align="center" field="supplierName" width="100">供应商</th>
								<th align="center" field="kucunNumber" width="100">货物数量</th>
								<th align="center" field="warehouseName" width="100">所在仓库</th>

							</tr>
						</thead>

					</table>

				</div>


			</div>
		</div>
	</div>
</body>
</html>