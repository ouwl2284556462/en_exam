$(function(){
	var moreConditionDiv = $("#student-manage-more-condition");
	moreConditionDiv.on('hide.bs.collapse', function () {
		$("#student-manage-more-condition-text").text('更多');
		$("#student-manage-more-condition-icon").removeClass("glyphicon-menu-up").addClass("glyphicon-menu-down");
		
	});
	
	moreConditionDiv.on('show.bs.collapse', function () {
		$("#student-manage-more-condition-text").text('收起');
		$("#student-manage-more-condition-icon").removeClass("glyphicon-menu-down").addClass("glyphicon-menu-up");
	});
	
	$("#student-manage-qry-form").on('submit', function(){
		student_manage_onQryFormSubmit(null);
		return false;
	});
});


function student_manage_onQryFormSubmit(pageNum){
	//pageNum等于是点击查询按钮出发
	$("#student-manage-qry-form-pageNum").val(pageNum);
	var param = {};
	param.submitBtnId = "student-manage-qry-list-btn";
	param.callback = function(isSuccess, data){
		if(!isSuccess){
			return;
		}
		
		$("#student-manage-list-root-div").html(data);
	}
	
	comm_Ajax_submitForm("student-manage-qry-form", param);	
}