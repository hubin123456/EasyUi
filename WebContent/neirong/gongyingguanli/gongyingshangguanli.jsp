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

<script type="text/javascript">
	$(document)
			.ready(
					function() {
						gongyingshang = $("#gongyingshang").datagrid({

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
							url : 'gongyingshang.do',
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
								text : '创建供应商',
								iconCls : 'icon-add',
								handler : function() {
									createSupplier();
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

						$('#createSupplier').window({
							onBeforeClose : function() {

								$("#supplierPhone").textbox("setValue", "");
								$("#supplierAddress").textbox("setValue", "");
								$("#supplierName").textbox("setValue", "");

							}
						});

						function export1() {
							var t_form = $("<form action='gongyingshangExport.do' method='post' name='form1'></form>");
							$("body").append(t_form);
							$("form[name='form1']").submit();
						}

						function createSupplier() {

							$('#createSupplier').window('open');

						}
						function createUser() {
							$('#createUser').window('open');

						}
						function chuhuoselect() {
							var caozuoId = $("#chukudanid")
									.combobox('getValue');
							if (caozuoId == "") {
								caozuoId = 0;
							}
							var status = $("#chukudanstatus").val();
							var createTime = $("#chukudandate").datebox(
									'getValue');
							var chukuTime = $("#chukudandate1").datebox(
									'getValue');
							grid1.datagrid('load', {
								'caozuoId' : caozuoId,
								'status' : status,
								'createTime' : createTime,
								'lukuTime' : chukuTime
							});
						}

						function xiugai() {
							var row = $("#gongyingshang").datagrid(
									'getSelected');
							if (row) {
								$("#editsupplier").window('open');
								$("#edit-supplierName").textbox('setValue',
										row.supplierName);
								$("#edit-supplierAddress").textbox('setValue',
										row.supplierAddress);
								$("#edit-supplierPhone").textbox('setValue',
										row.supplierPhone);

							} else {
								alert("请先选择供应商");
							}
						}
					})
	function submit1() {
		if ($("#supplierPhone").val() == ""
				|| $("#supplierAddress").val() == ""
				|| $("#supplierName").val() == "") {
			alert("信息不能为空");
		} else {
			$.ajax({
				type : 'post',
				data : $("#createsupplierform").serialize(),
				url : 'createSupplier.do',
				dataType : 'json',
				success : function(data) {
					if (data.result == 0) {
						alert("创建成功");
						$("#createSupplier").window('close');
						gongyingshang.datagrid('reload');
						$("#supplierPhone").combobox('setValue', '');
						$("#supplierAddress").textbox("setValue", "");
						$("#supplierName").textbox("setValue", "");

					} else {
						alert("创建失败");

					}
				}
			})
		}
	}

	function editwarehousesubmit() {
		if ($("#edit-supplierName").val() == ""
				|| $("#edit-supplierAddress").val() == ""
				|| $("#edit-supplierPhone").val() == "") {
			alert("信息不能为空");
		} else {
			$.ajax({
				type : 'post',
				data : $("#editsuppliersubmit").serialize(),
				url : 'editSupplier.do',
				dataType : 'json',
				success : function(data) {
					if (data.result == 0) {
						alert("修改成功");
						$("#editsupplier").window('close');
						gongyingshang.datagrid('reload');
						$("#supplierPhone").combobox('setValue', '');
						$("#supplierAddress").textbox("setValue", "");
						$("#supplierName").textbox("setValue", "");

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
		<div title="供应商管理" style="display: none;">
			<br>
			<br>

			<table id="gongyingshang" style="height: 430px;"
				class="easyui-datagrid">
				<thead style="background: #273045; color: #ffffff;">
					<tr>
						<th align="center" field="supplierName" width="200">供应商</th>
						<th align="center" field="supplierPhone" width="200">联系方式</th>
						<th field="supplierAddress" align="center" width="200">地址</th>
						<th align="center" field="createTime" width="200">创建时间</th>
					</tr>
				</thead>

			</table>
			<div id="createSupplier" class="easyui-window" title="创建供应商"
				style="width: 400px; height: 300px"
				data-options="iconCls:'icon-save',modal:true,closed:true">
				<div class="easyui-layout" data-options="fit:true">
					<div data-options="region:'center'">
						<%@ include file="createSupplier.jsp"%>
					</div>
				</div>
			</div>

			<div id="editsupplier" class="easyui-window" title="修改供应商"
				style="width: 400px; height: 300px"
				data-options="iconCls:'icon-save',modal:true,closed:true">
				<div class="easyui-layout" data-options="fit:true">
					<div data-options="region:'center'">
						<%@ include file="editSupplier.jsp"%>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>