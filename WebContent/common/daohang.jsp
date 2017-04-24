<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>

<script type="text/javascript">
	$(document)
			.ready(
					function() {

						$("#tree")
								.tree(
										{
											
											url : 'treeinit.do?role='
													+ $("#userrole").html(),
											onLoadSuccess : function() {

											},
											onClick : function(node) {
												myIframe.location.href = node.id;
												

											}
										});

					})
</script>
</head>
<body style="height: 635px;">
	<div data-options="region:'west',split:true" title="导航菜单"
		style="width: 200px; display: none;">
		<div class="easyui-panel" style="height: 200px;"
			data-options="fit:true,border:false,animate:true,plain:true">
			<ul id="tree" class="easyui-tree">

			</ul>
		</div>
	</div>
</body>
</html>