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
	
	
<style type="text/css">
</style>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-2.0.0.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/guanli.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.imgbox.pack.js"></script>
<script type="text/javascript">
	$(document).ready(function() {

		

		goods = $("#goods").datagrid({

			striped : true, //奇行不同颜色
			pageList : [ 1, 3, 5 ],
			fitColumns : true, //列自适应

			pageSize : 3,
			loadMsg : '正在加载数据.......', //当从远程站点载入数据时，显示的一条快捷信息
			pagination : true, //设置true将在数据表格底部显示分页工具栏
			//idField : 'flowId', //表明该列是一个唯一列。
			rownumbers : true, //设置为true将显示行数 ,
			singleSelect : true,
			onMouseOverRow : function(e, rowIndex, rowData) {
				
			},
			onMouseOutRow : function(e, rowIndex, rowData) {
				
			},
			url : 'goodschaxun.do',
			toolbar : [ {
				id : 'btnupdate',
				text : '增加',
				iconCls : 'icon-add',
				handler : function() {
					add();
				}
			}, '-', {
				id : 'btnupdate',
				text : '修改',
				iconCls : 'icon-edit',
				handler : function() {
					xiugai();
				}
			}, '-', {
				id : 'btnupdate',
				text : '上架',
				iconCls : 'icon-shangjia',
				handler : function() {
					shangjia();
				}
			}, '-', {
				id : 'btnupdate',
				text : '下架',
				iconCls : 'icon-xiajia',
				handler : function() {
					xiajia();
				}
			}, '-' ]

		});
		
		


	})

	function xiugai() {
		var row = $('#goods').datagrid('getSelected');
		if (row) {

			$("#goodsName1").textbox('setValue', row.goodsName);
			$("#goodsLei1").textbox('setValue', row.goodsLei);
			$("#goodsUnit1").textbox('setValue', row.goodsUnit);
			$("#goodsPrice1").textbox('setValue', row.goodsPrice);
			$("#supplierName1").textbox('setValue', row.supplierName);

			$("#goodsId").textbox('setValue', row.goodsId);
			$("#editgoods").window('open');
		} else {
			alert("请先选择商品");
		}
	}
	function tijiao() {
		var path = $("#goodsImage1").val();
		var houzhui = path.substring(path.indexOf(".") + 1, path.length);
		if (houzhui == "png" || houzhui == "jpg" || houzhui == "gif") {
			$("#editgoodsform").submit();
		} else {
			alert("必须为图片格式");
		}
	}
	function tijiao1() {
		var path = $("#goodsImage").val();
		var houzhui = path.substring(path.indexOf(".") + 1, path.length);
		if (houzhui == "png" || houzhui == "jpg" || houzhui == "gif") {
			$("#addgoodsform").submit();
		} else {
			alert("必须为图片格式");
		}
	}
	function shangjia() {
		var row = $('#goods').datagrid('getSelected');
		if (row) {
			$.ajax({
				type : 'post',
				data : {
					"goodsId" : row.goodsId,
					"status" : "上架"
				},
				url : 'goodsxiajia.do',
				dataType : 'json',
				success : function(data) {
					if (data.result == 0) {
						alert("成功");

						goods.datagrid('reload');

					} else {
						alert("失败");
						goods.datagrid('reload');
					}
				}
			});
		} else {
			alert("请先选择商品");
		}

	}
	function xiajia() {
		var row = $('#goods').datagrid('getSelected');
		if (row) {
			$.ajax({
				type : 'post',
				data : {
					"goodsId" : row.goodsId,
					"status" : "下架"
				},
				url : 'goodsxiajia.do',
				dataType : 'json',
				success : function(data) {
					if (data.result == 0) {
						alert("成功");

						goods.datagrid('reload');

					} else {
						alert("失败");
						goods.datagrid('reload');
					}
				}
			});
		} else {
			alert("请先选择商品");
		}

	}
	function add() {
		$("#addgoods").window('open');
	}
	
	function a(v) {
      
		return '<img width="100" id="a" height="50" onClick="imagebig(\''+ v + '\')"  src="'+v+'"/>';
	}
</script>
</head>
<body>
	<div class="easyui-tabs" style="width: 100%; height: 600px;">
		<div title="商品信息管理" style="display: none;">
          
			<input type="hidden" id="username"
				value=<%=request.getSession().getAttribute("user")%>> <br>
			<br>


			<table id="goods" class="goods" style="height: 480px;">
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
						<th align="center" field="status" width="100">状态</th>
						<th align="center" field="createTime" width="100">创建时间</th>
					</tr>
				</thead>

			</table>
			<div id="addgoods" class="easyui-window" title="增加商品"
				style="width: 600px; height: 250px"
				data-options="iconCls:'icon-save',modal:true,closed:true">
				<div class="easyui-layout" data-options="fit:true">
					<div data-options="region:'center'">
						<div style="margin-bottom: 40px;"></div>

						<form id="addgoodsform" action="addgoods.do" method="post"
							enctype="multipart/form-data">
							<div style="margin-left: 60px;">
								商品名称:<input type="text" class="easyui-textbox" name="goodsName"
									id="goodsName" style="width: 100px; heigt: 20px;">
								&nbsp;&nbsp; &nbsp;&nbsp;选择图片:<input type="file" name="file"
									id="goodsImage"> <br> <br>输入类别:<input
									type="text" id="goodsLei" name="goodsLei"
									class="easyui-textbox" style="width: 100px; height: 20px;">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;输入单位:<input name="goodsUnit"
									id="goodsUnit" type="text" class="easyui-textbox"
									style="width: 100px; height: 20px;"> <br> <br>输入价格:<input
									name="goodsPrice" type="text" id="goodsPrice"
									class="easyui-textbox" style="width: 100px; height: 20px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;选择供应商:<select
									id="supplierName" style="width: 100px; height: 21px;"
									name="supplierName"
									data-options="valueField:'supplierName',textField:'supplierName',url:'gongyingshangselect.do'"
									class="easyui-combobox" editable="false">
								</select> <br> <br> <input type="button" onClick="tijiao1()"
									value="提交" style="width: 400px; height: 30px;"
									class="easyui-linkbutton">
							</div>
						</form>

					</div>
				</div>


				<div id="editgoods" class="easyui-window" title="修改商品"
					style="width: 600px; height: 300px;"
					data-options="iconCls:'icon-save',modal:true,closed:true">
					<div class="easyui-layout" data-options="fit:true">
						<div data-options="region:'center'">
							<div style="margin-bottom: 40px;"></div>

							<form id="editgoodsform" action="editgoods.do" method="post"
								enctype="multipart/form-data">
								<div style="margin-left: 60px;">
									商品编号:<input type="text" class="easyui-textbox" name="goodsId"
										id="goodsId" style="width: 100px; heigt: 20px;"
										readonly="true"> <br> <br>商品名称:<input
										type="text" class="easyui-textbox" name="goodsName"
										id="goodsName1" style="width: 100px; heigt: 20px;">
									&nbsp;&nbsp; &nbsp;&nbsp;选择新图片:<input type="file" name="file1"
										id="goodsImage1"> <br> <br>输入类别:<input
										type="text" id="goodsLei1" name="goodsLei"
										class="easyui-textbox" style="width: 100px; height: 20px;">
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;输入单位:<input name="goodsUnit"
										id="goodsUnit1" type="text" class="easyui-textbox"
										style="width: 100px; height: 20px;"> <br> <br>输入价格:<input
										name="goodsPrice" type="text" id="goodsPrice1"
										class="easyui-textbox" style="width: 100px; height: 20px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;选择供应商:<select
										id="supplierName1" style="width: 100px; height: 21px;"
										name="supplierName"
										data-options="valueField:'supplierName',textField:'supplierName',url:'gongyingshangselect.do'"
										class="easyui-combobox" editable="false">
									</select> <br> <br> <input type="button" onClick="tijiao()"
										value="修改" style="width: 400px; height: 30px;"
										class="easyui-linkbutton">
								</div>
							</form>

						</div>
					</div>
				</div>
				
				<div id="imagebigwindow" class="easyui-window" title="查看图片"
					style="width: 600px; height: 300px;"
					data-options="iconCls:'icon-save',modal:true,closed:true">
					<div class="easyui-layout" data-options="fit:true">
						<div data-options="region:'center'">
							<div style="margin-bottom: 40px;"></div>
                             <span id="image"></span>  
							
						</div>
					</div>
				</div>
			</div>
		</div>

	</div>
</body>
</html>