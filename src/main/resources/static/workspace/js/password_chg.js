$(function(){
  	$("#password-chg-from").on('submit', password_chg_onPasswordChgFormSubmit);
  	$("#password-chg-form-returnBtn").click(password_chg_onReturnBtnClick);
  	
  	
	//检查密码输入焦点消失
	$("#newPasswordConfirm").on("blur", password_chg_onPasswordConfirmBlur);
});

function password_chg_onReturnBtnClick(){
	password_chg_checkAndChgToManageUserList();
	return false;
}

function password_chg_checkAndChgToManageUserList(){
	if(!password_chg_isFromAdminChgInfo()){
		return;
	}
	
	comm_ui_show($("#user-manage-user-list-div"));
	$("#user-manage-second-page-div").empty();
}

function password_chg_isFromAdminChgInfo(){
	return $("#password-chg-from-flag").val() == 'adminChgInfo';
}

function password_chg_onPasswordConfirmBlur(){
	var password = $("#newPassword").val();
	var passwordConfirm = $("#newPasswordConfirm").val();
	
	if(password != passwordConfirm){
		$("#newPasswordConfirm")[0].setCustomValidity("两次密码不相同。");
	}else{
		$("#newPasswordConfirm")[0].setCustomValidity("");
	}
}


function password_chg_onPasswordChgFormSubmit(){
	//是否来自系统管理员用户管理页面
	if(password_chg_isFromAdminChgInfo()){
		var submitFormObj = $("#password-chg-from");
		submitFormObj.attr("action", submitFormObj.data("admin-userpasswordchg-url"));
	}
	
	
	var param = {};
	param.submitBtnId = "password-chg-form-submitBtn";
	param.callback = function(isSuccess, data){
		if(isSuccess){
			if("Success" == data){
				comm_ui_showMessage("修改成功");
				password_chg_checkAndChgToManageUserList();
			}else{
				comm_ui_showMessage(data);
			}
		}
	}

	comm_Ajax_submitForm("password-chg-from", param);
	return false;
}



