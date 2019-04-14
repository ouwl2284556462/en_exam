$(function(){
  	$("#password-chg-from").on('submit', password_chg_onPasswordChgFormSubmit);
  	
	//检查密码输入焦点消失
	$("#newPasswordConfirm").on("blur", password_chg_onPasswordConfirmBlur);
});

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
	var param = {};
	param.submitBtnId = "password-chg-form-submitBtn";
	param.callback = function(isSuccess, data){
		if(isSuccess){
			if("Success" == data){
				comm_ui_showMessage("修改成功");
			}else{
				comm_ui_showMessage(data);
			}
		}
	}

	comm_Ajax_submitForm("password-chg-from", param);
	return false;
}