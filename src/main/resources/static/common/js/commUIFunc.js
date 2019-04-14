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
	
	noticeWin.unbind('hide.bs.modal').on('hide.bs.modal', function () {
		if(param.closeCallback != null){
			param.closeCallback();
		}
	});
	
	
	$("#comm-notice-window-title").text(title);
	$("#comm-notice-window-content").html(msg);
	noticeWin.modal();
}

function comm_ui_showConfirm(msg, param){
	if(param == null){
		param = {}
	}
	
	var title = param.title;
	if(title == null){
		title = "请确认";
	}
	
	
	var confirmWin = $('#comm-confirm-window');
	confirmWin.unbind('hide.bs.modal').on('hide.bs.modal', function () {
		if(param.closeCallback != null){
			param.closeCallback();
		}
	});
	
	$("#comm-confirm-window-okBtn").unbind("click").click(function(){
		if(param.confirmCallback != null){
			param.confirmCallback();
		}
	});
	
	
	$("#comm-confirm-window-title").text(title);
	$("#comm-confirm-window-content").html(msg);
	confirmWin.modal();
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

function comm_ui_show(obj){
	obj.removeClass("hidden");
}

function comm_ui_hide(obj){
	obj.addClass("hidden");
}


