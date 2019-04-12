$(function(){
  	$("#user-info-dtl-from").on('submit', user_info_onUserDtlFormSubmit);
});


function user_info_onUserDtlFormSubmit(){
	var param = {};
	param.submitBtnId = "user-info-dtl-form-submitBtn";
	param.callback = function(isSuccess, data){
		if(isSuccess){
			if("Success" == data){
				comm_ui_showMessage("修改成功");
			}else{
				comm_ui_showMessage(data);
			}
		}
	}

	comm_Ajax_submitForm("user-info-dtl-from", param);
	return false;
}
