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
	$(document).ready(
			function() {
				var quanxian_array = new Array();

				$("input[name='rolezong']").click(
						function() {

							if ($(this).prop('checked')) {
								$(this).attr("checked", true);
							} else {
								$(this).attr('checked', false);
							}
							if ($(this).attr('checked')) {
								//alert("1");
								$(this).parent().find('input:checkbox').prop(
										'checked', true);
							} else {
								$(this).parent().find('input:checkbox').prop(
										'checked', false);
							}
						});
				$("input[name='rolezi']").click(
						function() {
							var i = 0;
							var t = "";
							$(this).parent().parent().parent().find(
									"input[name='rolezong']").parent().find(
									"input[name='rolezi']").each(function() {
								// alert($(this).val());
								if ($(this).prop('checked')) {
									i++;
								}

							})
							if (i != 0) {
								$(this).parent().parent().parent().find(
										"input[name='rolezong']").prop(
										'checked', true);
							} else {
								$(this).parent().parent().parent().find(
										"input[name='rolezong']").prop(
										'checked', false);
							}
						});
				role = $("#role").datagrid({

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
					url : 'role.do',
					toolbar : [ {
						id : 'btnupdate',
						text : '赋予权限',
						iconCls : 'icon-quanxian',
						handler : function() {
							role1();
						}
					}, '-', {
						id : 'btnupdate',
						text : '增加',
						iconCls : 'icon-add',
						handler : function() {
							addrole();
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

	function role1() {

		var row = $("#role").datagrid('getSelected');
		if (row) {
			a = row.role;
			$
					.ajax({
						type : 'post',
						url : 'quanxian1.do',
						data : {
							"role" : row.role
						},
						dataType : 'json',
						success : function(data) {

							for (var i = 0; i < data.length; i++) {

								$(
										"input:checkbox[value="
												+ data[i].navigation + "]")
										.prop('checked', 'true');
								for (var a = 0; a < data[i].navigation1.length; a++) {
									$(
											"input:checkbox[value="
													+ data[i].navigation1[a].navigation1
													+ "]").prop('checked',
											'true');
								}
							}
						}
					})

			$("#rolefuyu").window('open');

		} else {
			alert("请先选择角色");
		}
	}
	function addrole() {
		$('#createRole').window('open');
	}

	function save() {
		var array = [];
		$("[name='rolezi']:checked").each(
				function() {
					var object = {
						"navigation" : $(this).parent().parent().parent().find(
								"input[name='rolezong']").val(),
						"navigation1" : $(this).val(),
						"role" : a
					};
					array.push(object);
				})
		if (array.length == 0) {
			alert("不能不选任何权限");
		} else {
			$.ajax({
				type : 'post',
				url : 'fuyuquanxian.do',
				data : {
					"quanxian" : JSON.stringify(array)
				},
				dataType : 'json',
				success : function(data) {
					alert(data.result);

					$("#rolefuyu").window('close');
					role.datagrid('reload');
				}
			})
		}
	}

	function submit1() {
		if ($("#role11").val() == "") {
			alert("信息不能为空");
		} else {
			$.ajax({
				type : 'post',
				data : $("#createroleform").serialize(),
				url : 'createRole.do',
				dataType : 'json',
				success : function(data) {
					if (data.result == 0) {
						alert("创建成功");
						$('#createRole').window('close');
						role.datagrid('reload');
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
		<div title="权限" style="padding-top: 20px; display: none;">
			<table id="role" style="height: 500px;" class="easyui-datagrid">
				<thead style="background: #273045; color: #ffffff;">
					<tr>
						<th align="center" field="role" width="200">角色名</th>
					</tr>
				</thead>

			</table>
			<div id="rolefuyu" class="easyui-window" title="赋予权限"
				style="width: 300px; height: 400px;"
				data-options="iconCls:'icon-save',modal:true,closed:true">
				<div class="easyui-layout" data-options="fit:true">
					<div data-options="region:'center'">
						<ul style="list-style: none">
							<c:forEach var="Navigation" items="${Navigation}">
								<li><input type="checkbox" autocomplete="off"
									value=${Navigation.navigation } name="rolezong"><span>${Navigation.navigation}</span>
									<ul style="list-style: none">
										<c:forEach var="Navigation" items="${Navigation.navigation1}">
											<li><input type="checkbox" autocomplete="off"
												value=${Navigation.navigation1 } name="rolezi"><span>${Navigation.navigation1}</span></li>
										</c:forEach>
									</ul>
							</c:forEach>
						</ul>
						<br>
						<center>
							<input type="button" onClick="save()" value="保存">
						</center>
					</div>
				</div>
			</div>
			<div id="createRole" class="easyui-window" title="创建角色"
				style="width: 400px; height: 200px"
				data-options="iconCls:'icon-save',modal:true,closed:true">
				<div class="easyui-layout" data-options="fit:true">

					<div data-options="region:'center'">
						<div style="margin: 20px 0;"></div>
						<form id="createroleform">
							<div style="margin-bottom: 20px; margin-left: 90px">
								角 色:<input id="role11" name="role" class="easyui-textbox"
									data-options="prompt:'输入角色'" style="width: 200px; height: 32px">

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