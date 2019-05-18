package exam.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import exam.user.bean.UserBean;
import exam.user.service.UserService;

@Controller
@RequestMapping("/login")
/**
 *登录相关 
 */
public class LoginController {
	
	@Autowired
	/**
	 * 用户信息服务
	 */
	private UserService userService;
	
	
	
	@RequestMapping("/loginErr.do")
	/**
	 * 跳转到登录页面，并带有错误信息
	 * @param model
	 * @return
	 */
	public String loginErr(Model model) {
		model.addAttribute("loginError", true);
		return "/login/login";
	}
	
	
	@RequestMapping("/toCreateAccount.do")
	/**
	 * 跳转到创建帐号页面
	 * @return
	 */
	public String toCreateAccount() {
		return "login/create_account";
	}
	
	@RequestMapping("/createAccount.do")
	/**
	 * 注册账号
	 * @return
	 */
	public String createAccount(Model model, UserBean userBean) {
		String errMsg;
		try {
			//注册账号
			errMsg = userService.addUser(userBean);
		}catch (Exception e) {
			e.printStackTrace();
			errMsg = "创建帐号失败，请重试!";
		}
		
		if(null == errMsg) {
			errMsg = "-";
		}
		
		model.addAttribute("errMsg", errMsg);
		return "login/create_account";
	}
	
	
	@RequestMapping("/isAcctNameUsed.do")
	@ResponseBody
	/**
	 * 检查帐号是否已经被使用
	 * @param acctName
	 * @return
	 */
	public String isAcctNameUsed(String acctName) {
		if(userService.isAcctNameUsed(acctName)) {
			return "Y";
		}
		
		return "N";
	}
	
}
