$(function(){
	//提交按钮点击不能再点
  	$("#createAccountFrom").on('submit', function(){
			$("#createAccountSubmitBtn").button('loading');
		 }
  	);
	
	//检查密码输入焦点消失
	$("#passwordConfirm").on("blur", onPasswordConfirmBlur);
	
	//检查电子邮箱输入焦点消失, 检查该邮箱是否注册过
	$("#account").on("blur", onAccountNameBlur);	
	
	//检查是否注册成功
	var createAccountErrMsg = $("#createAccountErrMsg").val();
	if("-" == createAccountErrMsg){
		comm_ui_showMessage("注册成功", {closeCallback:function(){$("#backToIndexPageLink span").click();}});
	}
});


function onPasswordConfirmBlur(){
	var password = $("#password").val();
	var passwordConfirm = $("#passwordConfirm").val();
	
	if(password != passwordConfirm){
		$("#passwordConfirm")[0].setCustomValidity("两次密码不相同。");
	}else{
		$("#passwordConfirm")[0].setCustomValidity("");
	}
}

function onAccountNameBlur(){
	
	var accountObj = $("#account");
	
	var param = {};
	param.reqData = {acctName: accountObj.val()};
	param.callback = function(isSuccess, data){
		if(isSuccess){
			if("Y" == data){
				accountObj[0].setCustomValidity("该电子邮箱已经注册过。");
			}else{
				accountObj[0].setCustomValidity("");
			}
		}
	};
	
	comm_Ajax_post("/login/isAcctNameUsed.do", param);
}


