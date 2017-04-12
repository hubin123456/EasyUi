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
	$(document).ready(function() {
		renyuan = $("#renyuan").datagrid({

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
			url : 'renyuanselect.do',
			toolbar : [ {
				id : 'btnupdate',
				text : '初始化密码',
				iconCls : 'icon-init',
				handler : function() {
					init();
				}
			}, '-', {
				id : 'btnupdate',
				text : '创建用户',
				iconCls : 'icon-add',
				handler : function() {
					createUser();
				}
			}, '-', {
				id : 'btnupdate',
				text : '启用',
				iconCls : 'icon-qiyong',
				handler : function() {
					startUser();
				}
			}, '-', {
				id : 'btnupdate',
				text : '停用',
				iconCls : 'icon-tingyong',
				handler : function() {
					stopUser();
				}
			}, '-', {
				id : 'btnupdate',
				text : '清空',
				iconCls : 'icon-clear',
				handler : function() {
					clear();
				}
			}, '-', {
				id : 'btnupdate',
				text : '查询',
				iconCls : 'icon-search',
				handler : function() {
					search();
				}
			}, '-' ]

		});

		function clear() {
			$("#username").combobox('setValue', '');
			$("#role").combobox('setValue', '');
			$("#status").combobox('setValue', '');

		}
		function search() {
			renyuan.datagrid('load', {
				'username' : $("#username").combobox('getValue'),
				'role' : $("#role").combobox('getValue'),
				'status' : $("#status").combobox('getValue'),

			});
		}

		function startUser() {
			var row = $('#renyuan').datagrid('getSelected');
			if (row) {
				$.ajax({
					type : 'post',
					data : {
						"username" : row.username
					},
					async : false,
					url : "startUser.do",
					dataType : 'json',
					success : function(data) {

						renyuan.datagrid('reload');
					}
				});
			} else {
				alert("请先选择人员");
			}
		}

		function stopUser() {
			var row = $('#renyuan').datagrid('getSelected');
			if (row) {
				$.ajax({
					type : 'post',
					data : {
						"username" : row.username
					},
					async : false,
					url : "stopUser.do",
					dataType : 'json',
					success : function(data) {

						renyuan.datagrid('reload');
					}
				});
			} else {
				alert("请先选择人员");
			}
		}
		function quanxian() {
			var row = $('#renyuan').datagrid('getSelected');
			if (row) {
				$('#quanxian').window('open');
			} else {
				alert("请先选择人员");
			}
		}

		function init() {
			var row = $('#renyuan').datagrid('getSelected');
			if (row) {
				$.ajax({
					type : 'post',
					data : {
						"username" : row.username
					},
					async : false,
					url : "init.do",
					dataType : 'json',
					success : function(data) {

						renyuan.datagrid('reload');
					}
				});

			}

			else {
				alert("请先选择人员");
			}

		}
		function createUser() {
			$('#createUser').window('open');

		}
		function chuhuoselect() {
			var caozuoId = $("#chukudanid").combobox('getValue');
			if (caozuoId == "") {
				caozuoId = 0;
			}
			var status = $("#chukudanstatus").val();
			var createTime = $("#chukudandate").datebox('getValue');
			var chukuTime = $("#chukudandate1").datebox('getValue');
			grid1.datagrid('load', {
				'caozuoId' : caozuoId,
				'status' : status,
				'createTime' : createTime,
				'lukuTime' : chukuTime
			});
		}
		$("#createUser").window({
			onBeforeClose : function() {
				$("#role").combobox('setValue', '');
				$("#password").textbox("setValue", "");
				$("#address").textbox("setValue", "");
				$("#phone").textbox("setValue", "");
				$("#username").textbox("setValue", "");

			}
		});

	})
	function submit1() {
		if ($("#username").val() == "" || $("#password").val == ""
				|| $("#role").combobox('getValue') == ""
				|| $("#address").val == "" || $("#phone").val == "") {
			alert("信息不能为空");
		} else {
			$.ajax({
				type : 'post',
				data : $("#createuserform").serialize(),
				url : 'createUser.do',
				dataType : 'json',
				success : function(data) {
					if (data.result == 0) {
						alert("创建成功");
						$('#createUser').window('close');
						renyuan.datagrid('reload');
						$("#role").combobox('setValue', '');
						$("#password").textbox("setValue", "");
						$("#address").textbox("setValue", "");
						$("#phone").textbox("setValue", "");
						$("#username").textbox("setValue", "");

					} else {
						alert("创建失败");

					}
				}
			})
		}
	}
	function a(v) {

		return '<img width="50" height="50" src="'+v+'"/>';
	}
</script>
</head>
<body>
	<div class="easyui-tabs" style="width: 100%; height: 600px;">
		<div title="人员管理" style="padding-top: 20px; display: none;">
			<table id="searchTable" align="left" fit="true">
				<tr>
					<td width="110px" align="right">用户名称:</td>
					<td><select id="username" style="width: 100px; height: 21px;"
						data-options="valueField:'username',textField:'username',url:'renyuanselect1.do'"
						class="easyui-combobox" editable="false">
					</select></td>
					<td width="110px" align="right">用户角色:</td>
					<td><select id="role" style="width: 100px; height: 21px;"
						data-options="valueField:'role',textField:'role',url:'roleselect.do'"
						class="easyui-combobox" editable="false">
					</select></td>
					<td width="110px" align="right">用户状态:</td>
					<td><select id="status" style="width: 100px; height: 21px;"
						data-options="valueField:'status',textField:'status',url:'statusselect.do'"
						class="easyui-combobox" editable="false">
					</select></td>


					<td width="110px" align="right"></td>
					<td></td>
			</table>

			<br> <br> <br> <br>
			<table id="renyuan" style="height: 430px;" class="easyui-datagrid">
				<thead style="background: #273045; color: #ffffff;">
					<tr>
						<th align="center" field="username" width="100">用户名</th>
						<th align="center" name="userImage" data-options="formatter:a"
							field="userImage" width="150">头像</th>
						<th align="center" field="password" width="200">密码</th>
						<th field="address" align="center" width="100">地址</th>
						<th align="center" field="phone" width="100">手机号</th>
						<th align="center" field="role" width="100">角色</th>
						<th align="center" field="status" width="100">状态</th>
						<th align="center" field="createtime" width="200">创建时间</th>
					</tr>
				</thead>

			</table>
			<div id="createUser" class="easyui-window" title="创建用户"
				style="width: 400px; height: 300px"
				data-options="iconCls:'icon-save',modal:true,closed:true">
				<div class="easyui-layout" data-options="fit:true">
					<div data-options="region:'center'">
						<%@ include file="createUser.jsp"%>
					</div>
				</div>
			</div>

		</div>
	</div>
</body>
</html>