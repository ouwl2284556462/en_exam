/**
 * 提交表单
 * @returns
 */
function comm_Ajax_submitForm(formId, param){
	if(param == null){
		param = {};
	}
	
	var submitBtnObj = null;
	if(param.submitBtnId != null){
		submitBtnObj = $("#" + param.submitBtnId);
	}
	
	if(submitBtnObj != null){
		//点击确定后按钮禁用
		submitBtnObj.button('loading');
	}
	
	
	var fromObj = $("#" + formId);
	//将表单数据表单序列化
	var formParam;
	if(!param.isUploadFile){
		formParam = fromObj.serialize();
	}else{
		formParam = new FormData(fromObj[0]);
	}
	
	var url = fromObj.attr("action");
	var ajaxParam = {url: url, 
					data: formParam, 
					type: "POST",
				    beforeSend: function(xhr){
				    	__comm_Ajax_setCsrfRequestHeader(xhr);
				    },
				    success: function(result){
						//解除禁用
						if(submitBtnObj != null){
							submitBtnObj.button('reset');
						}
						if(param.callback != null){
							param.callback(true, result);
						}
				    },
				    error : function(xhr,status,error){
				    	comm_ui_showMessage("请求出错");
				    	if(param.callback != null){
							param.callback(false, null);
						}  
				    },
				    complete: function(xhr, status){
				    	//解除禁用
						if(submitBtnObj != null){
							submitBtnObj.button('reset');
						}  	
				    }
				};
	
	
	if(param.isUploadFile){
		ajaxParam.processData = false;
		ajaxParam.contentType = false;
	}
	
	$.ajax(ajaxParam);
}


function comm_Ajax_post(url, param){
	if(param == null){
		param = {};
	}
	
	var submitBtnObj = null;
	if(param.submitBtnId != null){
		submitBtnObj = $("#" + param.submitBtnId);
	}
	
	if(submitBtnObj != null){
		//点击确定后按钮禁用
		submitBtnObj.button('loading');
	}
	
	var reqData = param.reqData;
	if(null == reqData){
		reqData = {};
	}
	
	
	var ajaxParam = {url: url, 
			data: reqData, 
			type: "POST",
		    beforeSend: function(xhr){
		    	__comm_Ajax_setCsrfRequestHeader(xhr);
		    },
		    success: function(result){
    			//解除禁用
    			if(submitBtnObj != null){
    				submitBtnObj.button('reset');
    			}
    			if(param.callback != null){
    				param.callback(true, result);
    			}
		    },
		    error : function(xhr,status,error){
		    	comm_ui_showMessage("请求出错");
		    	if(param.callback != null){
    				param.callback(false, null);
    			}  
		    },
		    complete: function(xhr, status){
		    	//解除禁用
    			if(submitBtnObj != null){
    				submitBtnObj.button('reset');
    			}  	
		    }
	};
	
	if(param.isUploadFile){
		ajaxParam.processData = false;
		ajaxParam.contentType = false;
	}
	
	$.ajax(ajaxParam);
}

function __comm_Ajax_setCsrfRequestHeader(xhr){
	var header = $("meta[name='_csrf_header']").attr("content");		    	
	var token = $("meta[name='_csrf']").attr("content");
	xhr.setRequestHeader(header, token);
}





