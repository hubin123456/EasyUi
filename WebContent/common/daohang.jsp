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
												if (node.text == ("库存管理")) {
													myIframe.location.href = "/EasyUi/kucun.do?";
												} else if (node.text == ("库存预警")) {
													myIframe.location.href = "/EasyUi/kucunyujing.do";
												} else if (node.text == ("出库管理")) {
													myIframe.location.href = "/EasyUi/chuhuo11.do";
												} else if (node.text == ("入库管理")) {
													myIframe.location.href = "/EasyUi/jinghuo.do";
												} else if (node.text == ("库存盘点")) {
													myIframe.location.href = "/EasyUi/kucunpandian1.do";
												} else if (node.text == ("仓库管理")) {
													myIframe.location.href = "/EasyUi/cangku.do";
												} else if (node.text == ("人员管理")) {
													myIframe.location.href = "/EasyUi/renyuan.do";
												} else if (node.text == ("权限管理")) {
													myIframe.location.href = "/EasyUi/quanxian.do";
												}

												else if (node.text == ("采购")) {
													myIframe.location.href = "/EasyUi/caigoudingdan.do";
												} else if (node.text == ("采购订单")) {
													myIframe.location.href = "/EasyUi/caigoulukudan.do";
												} else if (node.text == ("货物统计报表")) {
													myIframe.location.href = "/EasyUi/baobiao.do";
												} else if (node.text == ("供应商交易明细")) {
													myIframe.location.href = "/EasyUi/gongyingshangjiaoyimingxi.do";
												} else if (node.text == ("供应商管理")) {
													myIframe.location.href = "/EasyUi/gongyingshangguanli.do";
												} else if (node.text == ("客户管理")) {
													myIframe.location.href = "/EasyUi/kehuguanli.do";
												} else if (node.text == ("财务统计报表")) {
													myIframe.location.href = "/EasyUi/caiwutongjibaobiao.do";
												} else if (node.text == ("客户订单")) {
													myIframe.location.href = "/EasyUi/kehudingdan.do";
												} else if (node.text == ("客户交易明细")) {
													myIframe.location.href = "/EasyUi/kehujiaoyimingxi.do";
												} else if (node.text == ("提交订单")) {
													myIframe.location.href = "/EasyUi/tijiaodingdan.do";
												} else if (node.text == ("商品管理")) {
													myIframe.location.href = "/EasyUi/shangpingguanli.do";
												} else if (node.text == ("类别管理")) {
													myIframe.location.href = "/EasyUi/leiguanli.do";
												}

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