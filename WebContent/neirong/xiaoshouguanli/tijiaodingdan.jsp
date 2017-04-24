<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
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
<title>Insert title here</title>
<script type="text/javascript">
	$(document).ready(function() {
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
					fitColumns : true,
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
	})
	function a(v) {

		return '<img width="100" height="50" src="'+v+'"/>';
	}
	function tijiao() {

		if (!isNaN($("#goodsNumber").textbox('getValue'))
				&& $("#sumPrice").textbox('getValue')) {
			if ($("#kucunId").combobox('getValue') == ""
					|| $("#goodsNumber").textbox('getValue') == ""
					|| $("#username1").combobox('getValue') == ""
					|| $("#kehuName").combobox('getValue') == ""
					|| $("#sumPrice").textbox('getValue') == "") {

				alert("信息不能为空");
			} else {

				$.ajax({
					type : 'post',
					data : $("#tijiaodingdan").serialize(),
					url : 'chuku11.do',
					dataType : 'json',
					success : function(data) {
						alert(data.result);
						if (data.result == "提交成功") {
							location.reload();
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
		<div title="提交订单" style="padding: 20px; display: none;">
			<center>

				<input type="hidden" id="username"
					value=<%=request.getSession().getAttribute("user")%>>
				<center>
					<form id="tijiaodingdan">
						选择客户: <select id="kehuName" style="width: 100px; height: 21px;"
							name="kehuName"
							data-options="valueField:'kehuName',textField:'kehuName',url:'kehuselect.do'"
							class="easyui-combobox" editable="false">
						</select> &nbsp;&nbsp;&nbsp;选择库存: <select id="kucunId" name="kucunId"
							style="width: 100px; height: 21px;"></select>
						&nbsp;&nbsp;&nbsp;输入数量: <input id="goodsNumber" name="goodsNumber"
							style="width: 100px; height: 21px;" class="easyui-textbox"
							data-options="prompt:'输入数量'"> &nbsp;&nbsp;&nbsp;经办人: <select
							id="username1" style="width: 100px; height: 21px;"
							name="username"
							data-options="valueField:'username',textField:'username',url:'renyuanselect1.do'"
							class="easyui-combobox" editable="false"></select>
						&nbsp;&nbsp;&nbsp;客户报价: <input id="sumPrice" name="sumPrice"
							style="width: 100px; height: 21px;" class="easyui-textbox"
							data-options="prompt:'输入价格'"> &nbsp;&nbsp;&nbsp; <input
							style="width: 100px; height: 30px;" type="button"
							onClick="tijiao()" class="easyui-linkbutton" value="提交">
						</td>



					</form>
				</center>
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
			</center>


		</div>
	</div>
</body>
</html>