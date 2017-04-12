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
	src="${pageContext.request.contextPath}/js/guanli.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		//$(window).resize(function () {
		//$('#goods').datagrid('resize', {
		//  width: $(window).width() - 10,
		// height: $(window).height() - 35
		// }).datagrid('resize', {
		//     width: $(window).width() - 10,
		//    height: $(window).height() - 35
		// });
		// });
		goods = $("#goods").datagrid({

			striped : true, //奇行不同颜色
			pageList : [ 1, 3, 5 ],
			fitColumns : true, //列自适应
			queryParams : {
				"status" : "上架"
			},
			pageSize : 3,
			loadMsg : '正在加载数据.......', //当从远程站点载入数据时，显示的一条快捷信息
			pagination : true, //设置true将在数据表格底部显示分页工具栏
			//idField : 'flowId', //表明该列是一个唯一列。
			rownumbers : true, //设置为true将显示行数 ,
			singleSelect : true,

			url : 'goodschaxun.do',
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
					select();
				}
			}, {
				id : 'btnupdate',
				text : '采购',
				iconCls : 'icon-caigou',
				handler : function() {
					caigou();
				}
			}, '-' ]

		});

		$('#caigou').window({
			onBeforeClose : function() {

				$('#number').textbox('setValue', '');
				$("#warehouseName").combobox('setValue', '');

			}
		});
		function cleanselect() {
			$("#goodsId").combobox('setValue', '');
		
			$("#supplierName").combobox('setValue', '');
		}
		function select() {
			var goodsId = $("#goodsId").combobox('getValue');
			if (goodsId == "") {
				goodsId = 0;
			}
			
			var supplierName = $("#supplierName").combobox('getValue');
			goods.datagrid('load', {
				'goodsId' : goodsId,
				
				'supplierName' : supplierName,

			});
		}
		function caigou() {
			var row = $("#goods").datagrid('getSelected');
			if (row) {
				$("#caigou").window('open');
			} else {
				alert("请先选择商品");
			}
		}

	})
	function caigou1() {

		var row = $("#goods").datagrid('getSelected');
		var sumPrice = row.goodsPrice * $("#number").textbox('getValue');
		$.ajax({
			type : 'post',
			url : 'jinghuo3.do',
			dataType : 'json',
			data : {
				"goodsName" : row.goodsName,
				"goodsImage" : row.goodsImage,
				"goodsLei" : row.goodsLei,
				"goodsUnit" : row.goodsUnit,
				"goodsPrice" : row.goodsPrice,
				"goodsbeizhu" : row.goodsbeizhu,
				"supplierName" : row.supplierName,
				"goodsNumber" : $("#number").textbox('getValue'),
				"sumPrice" : sumPrice,
				"warehouseName" : $("#warehouseName").combobox('getValue'),
				"username" : $("#username").val()
			},
			success : function(data) {
				if (data.result == 1) {
					alert("采购成功");
					$("#caigou").window('close');
				} else {
					alert("采购失败");
				}
			}
		})
	}
	function jia() {
		var number = $("#number").html();
		number++;
		$("#number").html(number);
	}
	function jian() {
		var number = $("#number").html();
		if (number == 1) {
			alert("不能为0")
		} else {
			number--;
			$("#number").html(number);
		}
	}
	function a(v) {

		return '<img width="100" height="50" src="'+v+'"/>';
	}
</script>
</head>
<body>
	<div class="easyui-tabs" style="width: 100%; height: 600px;">
		<div title="采购" style="display: none;">

			<input type="hidden" id="username"
				value=<%=request.getSession().getAttribute("user")%>> <br>
			<br>
			<table id="searchTable" align="left" fit="true">
				<tr>
					<td width="110px" align="right">货物编号:</td>
					<td><select id="goodsId" style="width: 100px; height: 21px;"
						data-options="valueField:'goodsId',textField:'goodsId',url:'goodsselect.do'"
						class="easyui-combobox" editable="false">
					</select></td>


					<td width="110px" align="right">供应商:</td>
					<td><select id="supplierName"
						style="width: 100px; height: 21px;"
						data-options="valueField:'supplierName',textField:'supplierName',url:'gongyingshangselect.do'"
						class="easyui-combobox" editable="false">
					</select></td>
					<td width="110px" align="right"></td>
					<td></td>

					<td width="110px" align="right"></td>
					<td></td>
			</table>

			<br> <br> <br> <br>
			<table id="goods" style="height: 435px;">
				<thead>
					<tr>
						<th align="center" width="100" field="goodsId">货物编号</th>
						<th align="center" field="goodsName" width="100">货物名称</th>
						<th align="center" name="goodsImage" data-options="formatter:a"
							field="goodsImage" width="150">货物图片</th>
						<th align="center" field="goodsLei" width="100">货物类别</th>
						<th align="center" field="goodsUnit" width="100">货物单位</th>
						<th align="center" field="goodsPrice" width="150">货物价格</th>
						<th align="center" field="supplierName" width="100">供应商</th>
						<th align="center" field="createTime" width="100">创建时间</th>
					</tr>
				</thead>

			</table>
			<div id="caigou" class="easyui-window" title="采购"
				style="width: 500px; height: 100px"
				data-options="iconCls:'icon-save',modal:true,closed:true">
				<div class="easyui-layout" data-options="fit:true">
					<div data-options="region:'center'">
						<div style="margin-bottom: 20px;"></div>
						<center>
							输入数量:<input type="text" id="number" class="easyui-textbox"
								style="width: 100px; height: 21px;"> &nbsp;&nbsp;选择仓库:<select
								id="warehouseName" style="width: 100px; height: 21px;"
								data-options="valueField:'warehouseName',textField:'warehouseName',url:'cangkuselect.do'"
								class="easyui-combobox" editable="false">
							</select>&nbsp;&nbsp;<input type="button" onClick="caigou1()"
								class="easyui-linkbutton" iconCls="icon-ok"
								style="width: 50px; height: 21px;" value="采购">
						</center>
					</div>
				</div>
			</div>


		</div>

	</div>
</body>
</html>