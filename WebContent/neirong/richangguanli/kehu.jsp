<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
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

<script type="text/javascript">
	$(document)
			.ready(
					function() {
						kehu = $("#kehu").datagrid({
							//设置为true，当数据长度超出列宽时将会自动截取。
							striped : true,
							pageList : [ 1, 3, 5 ],
							fitColumns : false,
							pageSize : 3,
							loadMsg : '正在加载数据.......', //当从远程站点载入数据时，显示的一条快捷信息
							pagination : true, //设置true将在数据表格底部显示分页工具栏
							idField : 'flowId', //表明该列是一个唯一列。
							rownumbers : true, //设置为true将显示行数 ,
							singleSelect : true,
							url : 'kehu.do',
							toolbar : [ {
								id : 'btnupdate',
								text : '导出',
								iconCls : 'icon-daochu',
								handler : function() {
									export1();
								}
							}, '-',

							{
								id : 'btnupdate',
								text : '创建客户',
								iconCls : 'icon-add',
								handler : function() {
									createKehu();
								}
							}, '-', {
								id : 'btnupdate',
								text : '修改信息',
								iconCls : 'icon-edit',
								handler : function() {
									xiugai();
								}
							}, '-' ]

						});

						$('#createKehu').window({
							onBeforeClose : function() {

								$("#kehuPhone").textbox("setValue", "");
								$("#kehuAddress").textbox("setValue", "");
								$("#kehuName").textbox("setValue", "");

							}
						});

						function export1() {
							var t_form = $("<form action='kehuExport.do' method='post' name='form1'></form>");
							$("body").append(t_form);
							$("form[name='form1']").submit();
						}

						function createKehu() {

							$('#createKehu').window('open');

						}

						function xiugai() {
							var row = $("#kehu").datagrid('getSelected');
							if (row) {
								$("#editkehu").window('open');
								$("#edit-kehuName").textbox('setValue',
										row.kehuName);
								$("#edit-kehuAddress").textbox('setValue',
										row.kehuAddress);
								$("#edit-kehuPhone").textbox('setValue',
										row.kehuPhone);

							} else {
								alert("请先选择客户");
							}
						}
					})
	function submit1() {
		if ($("#kehuPhone").val() == "" || $("#kehuAddress").val() == ""
				|| $("#kehuName").val() == "") {
			alert("信息不能为空");
		} else {
			$.ajax({
				type : 'post',
				data : $("#createkehuform").serialize(),
				url : 'createKehu.do',
				dataType : 'json',
				success : function(data) {
					if (data.result == 0) {
						alert("创建成功");
						$("#createKehu").window('close');
						kehu.datagrid('reload');
						$("#kehuPhone").combobox('setValue', '');
						$("#kehuAddress").textbox("setValue", "");
						$("#kehuName").textbox("setValue", "");

					} else {
						alert("创建失败");

					}
				}
			})
		}
	}

	function editkehusubmit() {
		if ($("#edit-kehuName").val() == ""
				|| $("#edit-kehuAddress").val() == ""
				|| $("#edit-kehuPhone").val() == "") {
			alert("信息不能为空");
		} else {
			$.ajax({
				type : 'post',
				data : $("#editkehuform").serialize(),
				url : 'editKehu.do',
				dataType : 'json',
				success : function(data) {
					if (data.result == 0) {
						alert("修改成功");
						$("#editkehu").window('close');
						kehu.datagrid('reload');
						$("#kehuPhone").combobox('setValue', '');
						$("#kehuAddress").textbox("setValue", "");
						$("#kehuName").textbox("setValue", "");

					} else {
						alert("修改失败");

					}
				}
			})
		}
	}
</script>
</head>
<body>
	<div class="easyui-tabs" style="width: 100%; height: 600px;">
		<div title="客户管理" style="display: none;">
			<br>
			<br>

			<table id="kehu" style="height: 430px" class="easyui-datagrid">
				<thead>
					<tr>
						<th align="center" field="kehuName" width="200">客户</th>
						<th align="center" field="kehuPhone" width="200">联系方式</th>
						<th field="kehuAddress" align="center" width="200">地址</th>
						<th align="center" field="createTime" width="200">创建时间</th>
					</tr>
				</thead>

			</table>
			<div id="createKehu" class="easyui-window" title="创建客户"
				style="width: 400px; height: 300px"
				data-options="iconCls:'icon-save',modal:true,closed:true">
				<div class="easyui-layout" data-options="fit:true">
					<div data-options="region:'center'">
						<%@ include file="kehu/addkehu.jsp"%>
					</div>
				</div>
			</div>

			<div id="editkehu" class="easyui-window" title="修改客户"
				style="width: 400px; height: 300px"
				data-options="iconCls:'icon-save',modal:true,closed:true">
				<div class="easyui-layout" data-options="fit:true">
					<div data-options="region:'center'">
						<%@ include file="kehu/editkehu.jsp"%>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>

</html>