package exam.workspace.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import exam.user.bean.UserBean;
import exam.user.service.UserService;

@Controller
@RequestMapping("/Workspace")
public class WorkspaceController {
	
	@Autowired
	private UserService userService;

	@RequestMapping("/toMain.do")
	public String toMain() {
		return "workspace/main";
	}
	
	
	@RequestMapping("/toCurUserInfo.do")
	public String toCurUserInfo(Principal principal, Model model) {
		String acctName = principal.getName();
		UserBean userBean = userService.findUserByAcctName(acctName);
		model.addAttribute("userBean", userBean);
		return "workspace/user_info";
	}
	
}
