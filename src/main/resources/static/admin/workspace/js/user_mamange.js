$(function(){
	var moreConditionDiv = $("#user-manage-more-condition");
	moreConditionDiv.on('hide.bs.collapse', function () {
		$("#user-manage-more-condition-text").text('更多');
		$("#user-manage-more-condition-icon").removeClass("glyphicon-menu-up").addClass("glyphicon-menu-down");
		
	});
	
	moreConditionDiv.on('show.bs.collapse', function () {
		$("#user-manage-more-condition-text").text('收起');
		$("#user-manage-more-condition-icon").removeClass("glyphicon-menu-down").addClass("glyphicon-menu-up");
	});
	
	$("#user-manage-qry-form").on('submit', function(){
		user_manage_onQryUserListFormSubmit(null);
		return false;
	});
});


function user_manage_onQryUserListFormSubmit(pageNum){
	//pageNum等于是点击查询按钮出发
	$("#user-manage-qry-form-pageNum").val(pageNum);
	var param = {};
	param.submitBtnId = "user-manage-qry-list-btn";
	param.callback = function(isSuccess, data){
		if(!isSuccess){
			return;
		}
		
		$("#user-manage-user-list-root-div").html(data);
	}
	
	comm_Ajax_submitForm("user-manage-qry-form", param);	
}



