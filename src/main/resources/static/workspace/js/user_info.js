$(function(){
  	$("#user-info-dtl-from").on('submit', user_info_onUserDtlFormSubmit);
  	
  	
  	$("#user-info-dtl-form-returnBtn").click(user_info_onReturnBtnClick);
});

function user_info_onReturnBtnClick(){
	user_info_checkAndChgToManageUserList();
	return false;
}

function user_info_checkAndChgToManageUserList(){
	if($("#user-info-dtl-form-flag").val() != 'adminChgInfo'){
		return;
	}
	
	comm_ui_show($("#user-manage-user-list-div"));
	$("#user-manage-second-page-div").empty();
}

function user_info_onUserDtlFormSubmit(){
	var param = {};
	param.submitBtnId = "user-info-dtl-form-submitBtn";
	param.callback = function(isSuccess, data){
		if(isSuccess){
			if("Success" == data){
				comm_ui_showMessage("修改成功");
				user_info_checkAndChgToManageUserList();
				
			}else{
				comm_ui_showMessage(data);
			}
		}
	}

	comm_Ajax_submitForm("user-info-dtl-from", param);
	return false;
}
