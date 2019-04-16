package exam.admin.workspace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;

import exam.user.bean.UserBean;
import exam.user.service.UserService;

@Controller
@RequestMapping("/admin/Workspace")
public class AdminWorkSpaceController {
	
	//用户列表每页显示多少条
	private static final int USER_LIST_PAGE_PER_NUM = 10;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/toUserManage.do")
	public String toUserManage() {
		return "admin/workspace/user_manage";
	}
	
	@RequestMapping("/qryUserList.do")
	public String qryUserList(Model model, UserBean userBean, @RequestParam(value="pageNum", defaultValue="1") Integer pageNum) {
		
		PageInfo<UserBean> pageInfo = userService.qryUserListByPage(userBean, pageNum, USER_LIST_PAGE_PER_NUM);
		model.addAttribute("pageInfo", pageInfo);
		return "admin/workspace/user_list";
	}
	
	@RequestMapping("/deleteUserList.do")
	@ResponseBody
	public String deleteUserList(@RequestParam(value = "ids[]") String[] ids) {
		userService.deleteUser(ids);
		return "Success";
	}
	
	@RequestMapping("/toChgUserInfo.do")
	public String toChgUserInfo(Model model, int userId) {
		UserBean userBean = userService.findUserById(userId);
		model.addAttribute("userBean", userBean);
		model.addAttribute("from", "adminChgInfo");
		return "workspace/user_info";
	}
	
	@RequestMapping("/toChgPassword.do")
	public String toChgPassword(Model model, int userId) {
		model.addAttribute("userId", userId);
		model.addAttribute("from", "adminChgInfo");
		return "workspace/password_chg";
	}
	
	@RequestMapping("/changePassword.do")
	@ResponseBody
	public String changePassword(int userId, String newPassword) {
		userService.updatePassword(userId, newPassword);
		return "Success";
	}
	
}
