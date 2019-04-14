$(function(){
	//注销被点击
	$("#main-logout-link").click(main_logout);
	
	//右侧导航菜单退出系统
	$("#main-side-nav-logout-link").click(main_logout);
	
	//打印
	$("#main-side-nav-print-link").click(main_print);
	
	$(".main-side-nav-click-chg-page-link").click(function(){
		var linkObj = $(this);
		//改变选择状态
		main_chgSideNavSelected(linkObj);
		var param = {};
		param.callback = function(isSuccess, data){
			if(!isSuccess){
				return;
			}
			
			main_setMainContainerDivHtml(data);
		}
		
		var url = linkObj.data("tar-link");
		comm_Ajax_post(url, param);	
	});
	
	
	//默认选择左侧导航栏第一个
	main_initClickLeftNavItem();
});

function main_print(){
	var linkObj = $("#main-side-nav-print-link");
	//改变选择状态
	main_chgSideNavSelected(linkObj);
	
	var param = {};
	param.callback = function(isSuccess, data){
		if(!isSuccess){
			return;
		}
		
		main_setMainContainerDivHtml(data);
		
		if($("#exam-tick-wrapper").length == 0){
			comm_ui_showMessage("请先报名。");
		}else{
			$("#exam-tick-wrapper").printArea();  
		}
	}
	
	var url = linkObj.data("tar-link");
	comm_Ajax_post(url, param);	
}


function main_initClickLeftNavItem(){
	$(".main-side-nav-container .side-nav .side-nav-link").children(":first").click();
}


function main_chgMainContainerDivByLinkId(linkId){
	var linkObj = $("#" + linkId);
	//改变选择状态
	main_chgSideNavSelected(linkObj);
	
	var param = {};
	param.callback = function(isSuccess, data){
		if(!isSuccess){
			return;
		}
		
		main_setMainContainerDivHtml(data);
	}
	
	var url = linkObj.data("tar-link");
	comm_Ajax_post(url, param);	
}

function main_setMainContainerDivHtml(htmlData){
	var contentDiv = main_getMainContainerDiv();
	contentDiv.html(htmlData);
}

function main_getMainContainerDiv(){
	return $("#main-content");
}

function main_logout(){
	$("#main-logout-form").submit();
}

function main_chgSideNavSelected(curNav){
	$(".side-nav-link").removeClass("active");
	curNav.addClass("active");
}



