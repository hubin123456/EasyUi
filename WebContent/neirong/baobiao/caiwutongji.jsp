<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<script type="text/javascript">
	$(document).ready(function() {
		zhexiantu(new Date().getUTCFullYear(), "", "");

	});
	function chaxun() {
		var year = $("#yearChoose").combobox('getValue');
		var month = $("#monthChoose").combobox('getValue');
		var renyuan = $("#renyuanChoose").combobox('getValue');
		if (year != "") {
			zhexiantu(year, month, renyuan);
		} else if (month != "") {
			alert("年份不能为空");
		} else {
			zhexiantu(new Date().getUTCFullYear(), "", renyuan);
		}
	}
	function clear1() {
		$("#yearChoose").combobox('setValue', '');
		$("#monthChoose").combobox('setValue', '');
		$("#renyuanChoose").combobox('setValue', '');
	}
	function zhexiantu(year, month, renyuan) {
		$
				.ajax({
					type : 'post',
					async : false,
					data : {
						"year" : year,
						"month" : month,
						"renyuan" : renyuan
					},
					url : "caiwuzhexiantu.do",
					dataType : 'json',
					success : function(data) {

						var chart = new Highcharts.Chart(
								'container',
								{
									title : {
										text : data.year + '收支记录统计报表',
										x : -20
									},
									xAxis : {
										categories : data.x
									},
									yAxis : {
										title : {
											text : '钱 (￥)'
										},
										plotLines : [ {
											value : 0,
											width : 1,
											color : '#808080'
										} ]
									},
									tooltip : {
										valueSuffix : '￥'
									},
									legend : {
										layout : 'vertical',
										align : 'right',
										verticalAlign : 'middle',
										borderWidth : 0
									},
									plotOptions : {
										series : {

											lineWidth : 2,
											cursor : 'pointer',
											events : {
												click : function(event) {

													var year = $("#yearChoose")
															.combobox(
																	'getValue');
													var month = $(
															"#monthChoose")
															.combobox(
																	'getValue');
													var renyuan = $(
															"#renyuanChoose")
															.combobox(
																	'getValue');
													if (year == "") {
														year = new Date()
																.getUTCFullYear();
													}
													$("#caozuo")
															.datagrid(
																	{
																		title : '信息',
																		iconCls : 'icon-save',
																		nowrap : true, //设置为true，当数据长度超出列宽时将会自动截取。
																		striped : true,
																		pageList : [
																				1,
																				2,
																				3 ],
																		fitColumns : true,
																		pageSize : 1,
																		queryParams : {
																			"x" : event.point.x,
																			"year" : year,
																			"month" : month,
																			"renyuan" : renyuan,
																			"tiaojian" : event.point.series.name
																		},
																		loadMsg : '正在加载数据.......', //当从远程站点载入数据时，显示的一条快捷信息
																		pagination : true, //设置true将在数据表格底部显示分页工具栏
																		//idField : 'flowId', //表明该列是一个唯一列。
																		rownumbers : true, //设置为true将显示行数 ,
																		singleSelect : true,
																		url : 'caozuozhexiantu.do'
																	})
													$("#caozuoxiangxi").window(
															'open');
												}
											}
										}

									},
									series : data.data
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


	<br>
	<br>
	<center>
		年份选择:<select style="height: 20px; width: 100px;" id="yearChoose"
			data-options="valueField:'year',textField:'year',url:'yearselect.do'"
			editable="false" class="easyui-combobox"></select> &nbsp; &nbsp;
		&nbsp;月份选择: <select
			data-options="valueField:'month',textField:'month',url:'monthselect.do'"
			style="height: 20px; width: 100px;" id="monthChoose"
			class="easyui-combobox" editable="false">
		</select> &nbsp; &nbsp; &nbsp; 操作员选择: <select id="renyuanChoose"
			style="height: 20px; width: 80px;"
			data-options="valueField:'username',textField:'username',url:'renyuanselect1.do'"
			class="easyui-combobox" editable="false"></select>&nbsp; &nbsp;
		&nbsp; <input type="button" onClick="chaxun()"
			style="height: 21px; width: 100px;" value="查询"
			class="easyui-linkbutton">&nbsp; &nbsp;<input type="button"
			onClick="clear1()" style="height: 21px; width: 100px;" value="清空"
			class="easyui-linkbutton">
	</center>
	<br>
	<br>
	<div id="container" style="width: 100%; height: 400px; margin: 0 auto"></div>

	<div id="caozuoxiangxi" class="easyui-window" title="详细信息"
		style="width: 1000px; height: 200px;"
		data-options="iconCls:'icon-save',modal:true,closed:true">

		<table id="caozuo" class="easyui-datagrid">
			<thead style="background: #273045; color: #ffffff;">
				<tr>
					<th align="center" width="100" field="xiaoshouId">单子编号</th>
					<th align="center" field="kehuName" width="100">商家</th>
					<th align="center" name="goodsImage" data-options="formatter:a"
						field="goodsImage" width="150">货物图片</th>
					<th align="center" field="goodsName" width="100">货物名称</th>
					<th align="center" field="goodsNumber" width="100">货物数量</th>
					<th align="center" field="sumPrice" width="100">货物总价</th>


					<th align="center" field="username" width="100">操作员</th>
					<th align="center" field="createTime" width="100">完成时间</th>


				</tr>
			</thead>

		</table>

	</div>

</body>
</html>