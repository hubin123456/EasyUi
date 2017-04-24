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
							fitColumns : true,
							pageSize : 3,
							loadMsg : '正在加载数据.......', //当从远程站点载入数据时，显示的一条快捷信息
							pagination : true, //设置true将在数据表格底部显示分页工具栏
							//idField : 'flowId', //表明该列是一个唯一列。
							rownumbers : true, //设置为true将显示行数 ,
							singleSelect : true,
							url : 'kucun1.do',
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
								text : '库存调拨',
								iconCls : 'icon-kucundiaobo',
								handler : function() {
									kucuntiaobo();
								}
							}, '-', {
								id : 'btnupdate',
								text : '导出Excel',
								iconCls : 'icon-daochu',
								handler : function() {
									excelExport();
								}
							}, '-', {
								id : 'btnupdate',
								text : '设置最小库存预警',
								iconCls : 'icon-edit',
								handler : function() {
									kucunyujing();
								}
							}, '-', {
								id : 'btnupdate',
								text : '设置最大库存预警',
								iconCls : 'icon-edit',
								handler : function() {
									kucunyujing1();
								}
							}, '-', {
								id : 'btnupdate',
								text : '修改库存',
								iconCls : 'icon-edit',
								handler : function() {
									xiugaikucun();
								}
							}, '-' ]

						});

						function kucunyujing() {
							var row = $('#kucun').datagrid('getSelected');
							if (row) {
								$("#kucunyujing").window('open');
								$("#editgoodsyujing").textbox('setValue',
										row.kucunyujing);
								$("#editgoodsId").textbox('setValue',
										row.kucunId);
							} else {
								alert("请先选择货物单");
							}
						}

						function kucunyujing1() {
							var row = $('#kucun').datagrid('getSelected');
							if (row) {
								$("#kucunyujing1").window('open');
								$("#editgoodsyujing1").textbox('setValue',
										row.kucunyujing1);
								$("#editgoodsId1").textbox('setValue',
										row.kucunId);
							} else {
								alert("请先选择货物单");
							}
						}

						function cleanselect() {
							$("#goodsId").combobox('setValue', '');
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

						$('#warehouseNamediaochu')
								.combobox(
										{
											valueField : 'warehouseName', //值字段  
											textField : 'warehouseName', //显示的字段  
											url : 'cangkuselect.do',
											panelHeight : 'auto',
											required : true,
											editable : true,//不可编辑，只能选择  
											onChange : function(warehouseName) {

												$('#goodsNamediaochu')
														.combobox(
																{
																	valueField : 'kucunId', //值字段  
																	textField : 'kucunId', //显示的字段  
																	url : 'kucunselect1.do?warehouseName='
																			+ warehouseName,
																	panelHeight : 'auto',
																	required : true,
																	editable : true,//不可编辑，只能选择  
																	onChange : function(
																			kucunId) {
																		$
																				.ajax({
																					url : 'kucunselect.do',
																					type : 'post',
																					data : {
																						"warehouseName" : warehouseName,
																						"kucunId" : kucunId
																					},
																					dataType : 'json',
																					success : function(
																							data) {

																						$(
																								"#goodsNumberdiaochu")
																								.html(
																										"库存量"
																												+ data[0].kucunNumber);
																					}
																				})

																	}
																});

											}
										});

						$('#warehouseNamediaoru').combobox({
							valueField : 'warehouseName', //值字段  
							textField : 'warehouseName', //显示的字段  
							url : 'cangkuselect.do',
							panelHeight : 'auto',
							required : true,
							editable : true,//不可编辑，只能选择  
							onChange : function(warehouseName1) {

							}
						});
						$('#kucundiaoru').window(
								{
									onBeforeClose : function() {

										$('#warehouseNamediaochu').combobox(
												'setValue', '');
										//alert($('#goodsNamediaochu').combobox('getValue'));
										$('#goodsNamediaochu').combobox(
												'setValue', '');
										$('#warehouseNamediaoru').combobox(
												'setValue', '');
										$("#diaoruNumber").textbox('setValue',
												'');
										$("#goodsNumberdiaoru").html("");
										$("#goodsNumberdiaochu").html("");

									}
								});
					})
	function a(v) {

		return '<img width="100" height="50" src="'+v+'"/>';
	}

	function excelExport() {
		//alert("1");
		var t_form = $("<form action='kucunExport.do' method='post' name='form1'></form>");
		$("body").append(t_form);
		$("form[name='form1']").submit();
	}
	function editkucunyujing1() {
		if (!isNaN($("#editgoodsyujing1").val())) {
			if ($("#editgoodsyujing1").val() == "") {
				alert("信息不能为空");
			} else {
				$.ajax({
					type : 'post',
					data : $("#editkucunyujing1").serialize(),
					url : 'editkucunyujing1.do',
					dataType : 'json',
					success : function(data) {
						if (data.result == 0) {
							alert("修改成功");
							$("#kucunyujing1").window('close');
							kucun.datagrid('reload');

						} else {
							alert("修改失败");

						}
					}
				})
			}
		} else {
			alert("必须为数字");
		}
	}

	function editkucunyujing2() {
		if (!isNaN($("#editgoodsyujing2").val())) {
			if ($("#editgoodsyujing2").val() == "") {
				alert("信息不能为空");
			} else {
				$.ajax({
					type : 'post',
					data : $("#editkucunyujing2").serialize(),
					url : 'editkucunyujing1.do',
					dataType : 'json',
					success : function(data) {
						if (data.result == 0) {
							alert("修改成功");
							$("#kucunyujing2").window('close');
							kucun.datagrid('reload');

						} else {
							alert("修改失败");

						}
					}
				})
			}
		} else {
			alert("必须为数字");
		}
	}

	function xiugaikucun() {
		var row = $('#kucun').datagrid('getSelected');
		if (row) {
			$("#kucunyujing2").window('open');
			$("#editgoodsyujing2").textbox('setValue', row.kucunNumber);
			$("#editgoodsId2").textbox('setValue', row.kucunId);
		} else {
			alert("请先选择货物单");
		}
	}
	function editkucunyujing() {
		if (!isNaN($("#editgoodsyujing").val())) {
			if ($("#editgoodsyujing").val() == "") {
				alert("信息不能为空");
			} else {
				$.ajax({
					type : 'post',
					data : $("#editkucunyujing").serialize(),
					url : 'editkucunyujing.do',
					dataType : 'json',
					success : function(data) {
						if (data.result == 0) {
							alert("修改成功");
							$("#kucunyujing").window('close');
							kucun.datagrid('reload');
						} else {
							alert("修改失败");

						}
					}
				})
			}
		} else {
			alert("必须为数字");
		}
	}
	function kucuntiaobo() {

		$("#kucundiaoru").window('open');

	}
	function kucundiaobosubmit() {

		if ($('#warehouseNamediaochu').combobox('getValue') == ""
				|| $('#goodsNamediaochu').combobox('getValue') == ""
				|| $('#warehouseNamediaoru').combobox('getValue') == ""
				|| $("#diaoruNumber").textbox("getValue") == "") {
			alert("信息不能为空");
		} else if ($('#warehouseNamediaochu').combobox('getValue') == $(
				'#warehouseNamediaoru').combobox('getValue')) {
			alert("调出的仓库和调入的仓库不能相同");
		}

		else {

			$.ajax({
				type : 'post',
				data : {
					"warehouseNamediaochu" : $('#warehouseNamediaochu')
							.combobox('getValue'),
					"warehouseNamediaoru" : $('#warehouseNamediaoru').combobox(
							'getValue'),
					"goodsNamediaochu" : $('#goodsNamediaochu').combobox(
							'getValue'),
					"diaoruNumber" : $("#diaoruNumber").val()
				},
				url : 'kucundiaobo.do',
				dataType : 'json',
				success : function(data) {
					alert(data.result);
					if (data.result == "调入成功") {
						$("#kucundiaoru").window('close');
						kucun.datagrid('reload');
					}
				}
			})
		}
	}
</script>
</head>
<body>
	<div class="easyui-tabs" style="width: 100%; height: 600px;">
		<div title="库存管理" style="padding-top: 20px; display: none;">
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
			</table>
			<br> <br> <br> <br>
			<table id="kucun" class="easyui-datagrid" style="height: 430px;">
				<thead style="background: #273045; color: #ffffff;">
					<tr>
						<th align="center" width="100" field="kucunId">库存编号</th>
						<th align="center" field="goodsName" width="100">货物名称</th>
						<th align="center" name="goodsImage" data-options="formatter:a"
							field="goodsImage" width="150">货物图片</th>
						<th align="center" field="goodsLei" width="100">货物类别</th>
						<th align="center" field="goodsUnit" width="100">货物单位</th>
						<th align="center" field="goodsPrice" width="150">货物价格</th>
						<th align="center" field="supplierName" width="100">供应商</th>

						<th align="center" field="kucunNumber" width="100">货物数量</th>
						<th align="center" field="warehouseName" width="100">所在仓库</th>
						<th align="center" field="createTime" width="150">入库时间</th>
						<th align="center" field="kucunyujing" width="100">最小库存预警</th>
						<th align="center" field="kucunyujing1" width="100">最大库存预警</th>
					</tr>
				</thead>

			</table>
			<div id="kucunyujing" class="easyui-window" title="修改最小库存预警"
				style="width: 400px; height: 200px;"
				data-options="iconCls:'icon-save',modal:true,closed:true">
				<div class="easyui-layout" data-options="fit:true">
					<div data-options="region:'center'">
						<div style="margin-bottom: 20px;"></div>
						<form id="editkucunyujing" method="post">
							<div style="margin-bottom: 20px; margin-left: 30px">
								库存编号:<input id="editgoodsId" name="kucunId"
									class="easyui-textbox" readonly="true"
									style="width: 100px; height: 32px">
							</div>
							<div style="margin-bottom: 20px; margin-left: 30px">
								最小库存预警:<input id="editgoodsyujing" name="kucunyujing"
									class="easyui-textbox" data-options="prompt:'输入库存预警'"
									style="width: 100px; height: 32px">
							</div>
							<div>
								<input type="button" onClick="editkucunyujing()"
									class="easyui-linkbutton" iconCls="icon-ok"
									style="width: 100%; height: 32px" value="修改">
							</div>
						</form>
					</div>
				</div>
			</div>
			<div id="kucunyujing1" class="easyui-window" title="修改最大库存预警"
				style="width: 400px; height: 200px;"
				data-options="iconCls:'icon-save',modal:true,closed:true">
				<div class="easyui-layout" data-options="fit:true">
					<div data-options="region:'center'">
						<div style="margin-bottom: 20px;"></div>
						<form id="editkucunyujing1" method="post">
							<div style="margin-bottom: 20px; margin-left: 30px">
								库存编号:<input id="editgoodsId1" name="kucunId"
									class="easyui-textbox" readonly="true"
									style="width: 100px; height: 32px">
							</div>
							<div style="margin-bottom: 20px; margin-left: 30px">
								最大库存预警:<input id="editgoodsyujing1" name="kucunyujing1"
									class="easyui-textbox" data-options="prompt:'输入库存预警'"
									style="width: 100px; height: 32px">
							</div>
							<div>
								<input type="button" onClick="editkucunyujing1()"
									class="easyui-linkbutton" iconCls="icon-ok"
									style="width: 100%; height: 32px" value="修改">
							</div>
						</form>
					</div>
				</div>
			</div>
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
			<div id="kucunyujing2" class="easyui-window" title="修改库存"
				style="width: 400px; height: 200px;"
				data-options="iconCls:'icon-save',modal:true,closed:true">
				<div class="easyui-layout" data-options="fit:true">
					<div data-options="region:'center'">
						<div style="margin-bottom: 20px;"></div>
						<form id="editkucunyujing2" method="post">
							<div style="margin-bottom: 20px; margin-left: 30px">
								库存编号:<input id="editgoodsId2" name="kucunId"
									class="easyui-textbox" readonly="true"
									style="width: 100px; height: 32px">
							</div>
							<div style="margin-bottom: 20px; margin-left: 30px">
								库存:<input id="editgoodsyujing2" name="kucunNumber"
									class="easyui-textbox" data-options="prompt:'输入库存'"
									style="width: 100px; height: 32px">
							</div>
							<div>
								<input type="button" onClick="editkucunyujing2()"
									class="easyui-linkbutton" iconCls="icon-ok"
									style="width: 100%; height: 32px" value="修改">
							</div>
						</form>
					</div>
				</div>
				<div id="kucundiaoru" class="easyui-window" title="库存调拨"
					style="width: 600px; height: 300px;"
					data-options="iconCls:'icon-save',modal:true,closed:true">
					<div class="easyui-layout" data-options="fit:true">
						<div data-options="region:'center'">
							<div style="margin-bottom: 20px;"></div>
							<form id="editkucunyujing1" method="post">
								<div style="margin-bottom: 20px; margin-left: 30px">
									要调出的仓库:<select id="warehouseNamediaochu"
										style="width: 100px; height: 21px;"></select>&nbsp;&nbsp;
									要调出的库存Id:<select id="goodsNamediaochu"
										style="width: 100px; height: 21px;"></select>&nbsp;&nbsp;<span
										id="goodsNumberdiaochu"></span>
								</div>
								<div style="margin-bottom: 20px; margin-left: 30px">
									要调入的仓库:<select id="warehouseNamediaoru"
										style="width: 100px; height: 21px;"></select> &nbsp;&nbsp;<span
										id="goodsNumberdiaoru"></span>
								</div>
								<div style="margin-bottom: 20px; margin-left: 30px">
									要调入的数量:<input id="diaoruNumber" name="diaoruNumber"
										class="easyui-textbox" style="width: 100px; height: 32px">

								</div>
								<div>
									<input type="button" onClick="kucundiaobosubmit()"
										class="easyui-linkbutton" iconCls="icon-ok"
										style="width: 100%; height: 32px" value="调拨">
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
</body>
</html>
