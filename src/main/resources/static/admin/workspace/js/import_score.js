$(function(){
	$("#import-score-Form").on('submit', import_score_onImportScoreFormSubmit);
});



function import_score_onImportScoreFormSubmit(){
	var param = {};
	param.submitBtnId = "import-score-SubmitBtn";
	param.isUploadFile = true;
	param.callback = function(isSuccess, data){
		if(!isSuccess){
			return;
		}
		
		if(data == "Success"){
			comm_ui_showMessage("导入成功");
		}else{
			comm_ui_showMessage(data);
		}
	}
	
	comm_Ajax_submitForm("import-score-Form", param);
	return false;
}