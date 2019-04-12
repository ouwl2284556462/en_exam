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

/**
 * 动态加载css
 * @param href
 * @returns
 */
function comm_ui_loadCSS(href){
	if($("link[href='" + href + "']").length > 0){
		return;
	}
	
	
	var linkObj = $("<link>");
	linkObj.attr("rel", "stylesheet");
	linkObj.attr("type", "text/css");
	linkObj.attr("href", href);
	linkObj.appendTo("head"); 
}
