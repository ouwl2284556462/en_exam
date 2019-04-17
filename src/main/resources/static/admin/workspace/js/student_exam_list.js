$(function(){
	$("#student-exam-list-printTicketBtn").click(student_exam_list_onprintTicketBtnClick);
	$("#student-exam-list-deleteBtn").click(student_exam_list_onDeleteBtnClick);
	$("#student-exam-list-ChgStudentExamInfoBtn").click(student_exam_list_onChgStudentExamInfoBtnClick);
	$("#student-exam-list-qryStudentExamDtlBtn").click(student_exam_list_onQryStudentExamDtlBtnClick);
	
	
	$(".student-exam-list-table-col-selectCheckBox").click(function(even){
		//阻止事件冒泡
		event.stopPropagation();
	});
	
	$(".student-exam-list-table-row").click(function(even){
		student_exam_list_onTableRowClick(this);
	});
});

function student_exam_list_onQryStudentExamDtlBtnClick(){
	var record = student_exam_list_getOnlyOneRecord();
	if(null == record){
		return;
	}
	
	var applyId = record.data("apply-id");
	
	var param = {};
	param.submitBtnId = "student-exam-list-qryStudentExamDtlBtn";
	param.reqData = {applyId : applyId};
	
	param.callback = function(isSuccess, data){
		comm_ui_hide($("#student-manage-list-div"));
		$("#student-manage-second-page-div").html(data);
	};
	
	
	var url = $("#student-exam-list-qryStudentExamDtlBtn").data("target-url");
	comm_Ajax_post(url, param);
}

function student_exam_list_onChgStudentExamInfoBtnClick(){
	var record = student_exam_list_getOnlyOneRecord();
	if(null == record){
		return;
	}
	
	var applyId = record.data("apply-id");
	
	var param = {};
	param.submitBtnId = "student-exam-list-ChgStudentExamInfoBtn";
	param.reqData = {applyId : applyId};
	
	param.callback = function(isSuccess, data){
		comm_ui_hide($("#student-manage-list-div"));
		$("#student-manage-second-page-div").html(data);
	};
	
	
	var url = $("#student-exam-list-ChgStudentExamInfoBtn").data("target-url");
	comm_Ajax_post(url, param);
}


function student_exam_list_onprintTicketBtnClick(){
	var record = student_exam_list_getOnlyOneRecord();
	if(null == record){
		return;
	}
	
	var applyId = record.data("apply-id");
	var param = {};
	param.reqData = {applyId: applyId};
	param.callback = function(isSuccess, data){
		if(!isSuccess){
			return;
		}
		
		$(data).printArea(); 
	}
	
	var url = $("#student-exam-list-printTicketBtn").data("target-url");
	comm_Ajax_post(url, param);
}

function student_exam_list_getOnlyOneRecord(){
	var targetItem = $(".student-exam-list-table-col-selectCheckBox").filter(function(){
		return $(this).prop('checked');
	}); 

	if(targetItem.length < 1){
		comm_ui_showMessage("请选择");
		return null;
	}
	
	if(targetItem.length > 1){
		comm_ui_showMessage("只能选择一个。");
		return null;
	}
	
	return targetItem;
}


function student_exam_list_onTableRowClick(curRow){
	var checkBox = $(curRow).find(".student-exam-list-table-col-selectCheckBox");
	checkBox.prop('checked', !checkBox.prop('checked'));
}

function student_exam_list_onDeleteBtnClick(){
	var needRemoveItem = [];
	$(".student-exam-list-table-col-selectCheckBox").each(function(){
	    if($(this).prop('checked')){
	    	needRemoveItem.push($(this).data("apply-id"));
	    }
	});
	
	if(needRemoveItem.length < 1){
		comm_ui_showMessage("请选择");
		return;
	}
	
	var param = {};
	param.confirmCallback = function(){
		student_exam_list_remove(needRemoveItem);
	};
	
	comm_ui_showConfirm("删除数据后，将无法恢复，您确定删除？", param);
}

function student_exam_list_remove(ids){
	var param = {};
	param.submitBtnId = "student-exam-list-deleteBtn";
	param.reqData = {ids : ids};
	param.callback = function(isSuccess, data){
		if(data != "Success"){
			comm_ui_showMessage(data);
			return;
		}
		
		comm_ui_showMessage("删除成功");
		student_manage_onQryFormSubmit();
	};
	
	
	var url = $("#student-exam-list-deleteBtn").data("target-url");
	comm_Ajax_post(url, param);
}





