/**
 * 弹出提示
 * @param msg
 * @param param
 * @returns
 */
function comm_ui_showMessage(msg, param){
	if(param == null){
		param = {}
	}
	
	var title = param.title;
	if(title == null){
		title = "通知";
	}
	
	
	var noticeWin = $('#comm-notice-window');
	
	noticeWin.on('hide.bs.modal', function () {
		if(param.closeCallback != null){
			param.closeCallback();
		}
	});
	
	
	$("#comm-notice-window-title").text(title);
	$("#comm-notice-window-content").text(msg);
	noticeWin.modal();
}