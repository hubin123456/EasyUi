<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<script src="http://code.highcharts.com/highcharts-3d.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		var warehouseName = 0;
		$("#warehouseName").combobox({
			valueField : 'warehouseName',
			textField : 'warehouseName',
			url : 'cangkuselect.do',
			onLoadSuccess : function() {
				var data = $('#warehouseName').combobox('getData');
				$("#warehouseName").combobox('select', data[0].warehouseName);
				bingtu($('#warehouseName').combobox('getValue'));

			},
			onSelect : function(record) {

				bingtu(record.warehouseName);

			}
		});
		// Build the chart

	});
	function bingtu(warehouseName) {
		$
				.ajax({
					type : 'post',
					data : {
						"warehouseName" : warehouseName
					},
					async : false,
					url : "goods1.do",
					dataType : 'json',
					success : function(data) {

						$('#bingtu2')
								.highcharts(
										{
											chart : {
												plotBackgroundColor : null,
												plotBorderWidth : null,
												plotShadow : false,
												type : 'pie',
												options3d : {
													enabled : true,
													alpha : 45,
													beta : 0
												}
											},
											title : {
												text : '仓库货物分布饼图'
											},
											tooltip : {
												pointFormat : '{series.name}: <b>{point.percentage:.1f}%</b>'
											},
											plotOptions : {
												pie : {
													allowPointSelect : true,
													cursor : 'pointer',
													depth : 35,
													dataLabels : {
														enabled : true,
														format : '{point.name}'
													},
													showInLegend : true,
													events : {

														click : function(e) {
															$("#kucun")
																	.datagrid(
																			{
																				title : '信息',
																				iconCls : 'icon-save',
																				nowrap : true, //设置为true，当数据长度超出列宽时将会自动截取。
																				striped : true,
																				pageList : [
																						5,
																						10,
																						15 ],
																				fitColumns : false,
																				pageSize : 5,
																				queryParams : {
																					"kucunId" : e.point.name
																				},
																				loadMsg : '正在加载数据.......', //当从远程站点载入数据时，显示的一条快捷信息
																				pagination : true, //设置true将在数据表格底部显示分页工具栏
																				//idField : 'flowId', //表明该列是一个唯一列。
																				rownumbers : true, //设置为true将显示行数 ,
																				singleSelect : true,
																				url : 'kucun1.do'
																			})
															$("#kucunxiangxi")
																	.window(
																			'open');
														}

													},
												}
											},

											series : [ {
												name : '库存占比',
												colorByPoint : true,
												data : data,

											} ]
										});

					}
				});
	}

	function a(v) {

		return '<img width="100" height="50" src="'+v+'"/>';
	}
</script>

</head>
<body>

	<center>
		仓库名称:&nbsp;&nbsp;<select id="warehouseName"
			style="width: 100px; height: 21px;" editable="false"></select>
	</center>

	<div id="bingtu2"
		style="position: absolute; left: 400px; top: 100px; width: 300px; height: 300px"></div>
	<div id="kucunxiangxi" class="easyui-window" title="详细库存"
		style="width: 1000px; height: 200px;"
		data-options="iconCls:'icon-save',modal:true,closed:true">

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

	</div>

</body>
</html>