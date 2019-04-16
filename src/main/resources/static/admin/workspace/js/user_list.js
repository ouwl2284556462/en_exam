$(function(){
	$("#user-list-deleteBtn").click(user_list_onDeleteBtnClick);
	$("#user-list-chginfoBtn").click(user_list_onchgInfoBtnClick);
	$("#user-list-chgpasswordBtn").click(user_list_onChgpasswordBtnClick);
	
	
	
	$(".user-list-table-col-selectCheckBox").click(function(even){
		//阻止事件冒泡
		event.stopPropagation();
	});
	
	$(".user-list-table-row").click(function(even){
		user_list_onTableRowClick(this);
	});
});

function user_list_onChgpasswordBtnClick(){
	var targetItem = $(".user-list-table-col-selectCheckBox").filter(function(){
		return $(this).prop('checked');
	}); 

	if(targetItem.length < 1){
		comm_ui_showMessage("请选择");
		return;
	}
	
	if(targetItem.length > 1){
		comm_ui_showMessage("只能选择一个。");
		return;
	}
	
	var userId = $(targetItem).data("user-id");
	var param = {};
	param.submitBtnId = "user-list-chgpasswordBtn";
	param.reqData = {userId : userId};
	param.callback = function(isSuccess, data){
		comm_ui_hide($("#user-manage-user-list-div"));
		$("#user-manage-second-page-div").html(data);
	};
	
	var url = $("#user-list-chgpasswordBtn").data("target-url");
	comm_Ajax_post(url, param);
}

function user_list_onchgInfoBtnClick(){
	var targetItem = $(".user-list-table-col-selectCheckBox").filter(function(){
							return $(this).prop('checked');
						}); 
	
	if(targetItem.length < 1){
		comm_ui_showMessage("请选择");
		return;
	}
	
	if(targetItem.length > 1){
		comm_ui_showMessage("只能选择一个。");
		return;
	}
	
	var userId = $(targetItem).data("user-id");
	
	var param = {};
	param.submitBtnId = "user-list-chginfoBtn";
	param.reqData = {userId : userId};
	param.callback = function(isSuccess, data){
		comm_ui_hide($("#user-manage-user-list-div"));
		$("#user-manage-second-page-div").html(data);
	};
	
	
	var url = $("#user-list-chginfoBtn").data("target-url");
	comm_Ajax_post(url, param);
}


function user_list_onTableRowClick(curRow){
	var checkBox = $(curRow).find(".user-list-table-col-selectCheckBox");
	checkBox.prop('checked', !checkBox.prop('checked'));
}

function user_list_onDeleteBtnClick(){
	var needRemoveItem = [];
	$(".user-list-table-col-selectCheckBox").each(function(){
	    if($(this).prop('checked')){
	    	needRemoveItem.push($(this).data("user-id"));
	    }
	});
	
	if(needRemoveItem.length < 1){
		comm_ui_showMessage("请选择");
		return;
	}
	
	var param = {};
	param.confirmCallback = function(){
		user_list_removeUser(needRemoveItem);
	};
	
	comm_ui_showConfirm("删除数据后，将无法恢复，您确定删除？", param);
}


function user_list_removeUser(ids){
	var param = {};
	param.submitBtnId = "user-list-deleteBtn";
	param.reqData = {ids : ids};
	param.callback = function(isSuccess, data){
		if(data != "Success"){
			comm_ui_showMessage(data);
			return;
		}
		
		comm_ui_showMessage("删除成功");
		user_manage_onQryUserListFormSubmit();
	};
	
	
	var url = $("#user-list-deleteBtn").data("target-url");
	comm_Ajax_post(url, param);
}
