/**
 * 
 */

function shang(currentpage){
	if(currentpage == 1){
		alert("当前已为首页")
	}
	else{
		currentpage--;
		window.location.href = "/EasyUi/jinghuo.do?currentpage="+currentpage+"&pagenum="+$("#pagenum").val()+"" ;
	}
	}
function xia(currentpage){
	var count = $("#count").html();
	var pagenum = $("#pagenum").val();
    if(pagenum * currentpage<count)
    	{
	currentpage++;
	window.location.href = "/EasyUi/jinghuo.do?currentpage="+currentpage+"&pagenum="+$("#pagenum").val()+"" ;
    	}
    else{
    	alert("当前已为尾页")
    }
}

function change(currentpage){
	//alert(currentpage);
	window.location.href = "/EasyUi/jinghuo.do?currentpage="+currentpage+"&pagenum="+$("#pagenum").val()+"" ;
}

function shou(){
	window.location.href = "/EasyUi/jinghuo.do?currentpage=1&pagenum="+$("#pagenum").val()+"" ;
    	
}
function wei(){
	//alert(currentpage);
	var yeshu = 0;
	var count = $("#count").html();
	var pagenum = $("#pagenum").val();
	if(count == pagenum){
		yeshu = count / pagenum;
	}
	else{
		//alert(count+"=="+pagenum);
		yeshu = parseInt(count / pagenum) + 1;
		//alert(yeshu);
	}
	window.location.href = "/EasyUi/jinghuo.do?currentpage="+yeshu+"&pagenum="+$("#pagenum").val()+"" ;
}