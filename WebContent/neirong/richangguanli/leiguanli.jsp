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
	var a;
	$(document).ready(function() {

		lei = $("#lei").datagrid({

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
			url : 'leiselect1.do',
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
					edit();
				}
			}, '-' ]

		});

		$("#rolefuyu").window({
			onBeforeClose : function() {
				$("input[type='checkbox']").prop("checked", false);

			}
		});

		$("#createRole").window({
			onBeforeClose : function() {
				$("#role11").textbox('setValue', '');

			}
		});
	});

	function add() {
		$('#createRole').window('open');
	}

	function submit1() {
		if ($("#role11").val() == "") {
			alert("信息不能为空");
		} else {
			$.ajax({
				type : 'post',
				data : $("#createroleform").serialize(),
				url : 'createLei.do',
				dataType : 'json',
				success : function(data) {
					if (data.result == 0) {
						alert("创建成功");
						$('#createRole').window('close');
						lei.datagrid('reload');
						$("#role11").textbox('setValue', '');
					} else {
						alert("创建失败");
					}
				}
			});
		}
	}
</script>
</head>
<body>
<body class="easyui-layout">
	<div class="easyui-tabs" style="width: 100%; height: 600px;">
		<div title="类别" style="padding-top: 20px; display: none;">
			<table id="lei" style="height: 500px;" class="easyui-datagrid">
				<thead>
					<tr>
						<th align="center" field="goodsLei" width="200">类别</th>
					</tr>
				</thead>

			</table>

			<div id="createRole" class="easyui-window" title="创建角色"
				style="width: 400px; height: 200px"
				data-options="iconCls:'icon-save',modal:true,closed:true">
				<div class="easyui-layout" data-options="fit:true">

					<div data-options="region:'center'">
						<div style="margin: 20px 0;"></div>
						<form id="createroleform">
							<div style="margin-bottom: 20px; margin-left: 90px">
								类别:<input id="role11" name="goodsLei" class="easyui-textbox"
									data-options="prompt:'输入类别'" style="width: 200px; height: 32px">

							</div>
							<div>
								<input type="button" onClick="submit1()"
									class="easyui-linkbutton" iconCls="icon-ok"
									style="width: 100%; height: 32px" value="创建">
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

</body>
</html>