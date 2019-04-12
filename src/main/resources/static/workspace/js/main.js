$(function(){
	//注销被点击
	$("#main-logout-link").click(main_logout);
	
	//右侧导航菜单退出系统
	$("#main-side-nav-logout-link").click(main_logout);
	
	//右侧个人信息
	$("#main-side-nav-userinfo-link").click(main_userInfoLinkClick);
	
	
	//默认选择左侧导航栏第一个
	main_initClickLeftNavItem();
});

function main_initClickLeftNavItem(){
	$(".main-side-nav-container .side-nav .side-nav-link").children(":first").click();
}

function main_userInfoLinkClick(){
	var linkObj = $("#main-side-nav-userinfo-link");
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
	comm_Ajax_post(url, param)
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



