package exam.workspace.controller;

import java.io.File;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import exam.base.constants.ExamConstants;
import exam.dict.bean.SysDictItemBean;
import exam.examination.bean.ExamApplyInfoBean;
import exam.examination.bean.ExamInfoBean;
import exam.examination.bean.ExamPlaceBean;
import exam.examination.service.ExamService;
import exam.user.bean.UserBean;
import exam.user.service.UserService;

@Controller
@RequestMapping("/Workspace")
/**
 * 普通用户页面主页 
 */
public class WorkspaceController {
	
	@Value("${user.imagesFilePath}")
	/**
	 * 保存报名考试的照片的路径
	 */
    private String userImagesPath;
	
	@Autowired
	/**
	 * 用户管理的服务类
	 */
	private UserService userService;
	
	@Autowired
	/**
	 * 考试信息的管理服务类
	 */
	private ExamService examService;

	@RequestMapping("/toMain.do")
	/**
	 * 跳转到主页
	 * @return
	 */
	public String toMain() {
		return "workspace/main";
	}
	
	
	@RequestMapping("/toCurUserInfo.do")
	/**
	 * 跳转到当前用户的详细信息页面
	 * @param principal
	 * @param model
	 * @return
	 */
	public String toCurUserInfo(Principal principal, Model model) {
		String acctName = principal.getName();
		//获取当前用户的详细信息
		UserBean userBean = userService.findUserByAcctName(acctName);
		model.addAttribute("userBean", userBean);
		return "workspace/user_info";
	}
	
	@RequestMapping("/updateUserDtl.do")
	@ResponseBody
	/**
	 * 更新用户详细信息
	 * @param userBean
	 * @param model
	 * @return
	 */
	public String updateUserDtl(UserBean userBean, Model model) {
		//更新用户详细信息
		userService.updateUserDtl(userBean);
		model.addAttribute("userBean", userBean);
		return "Success";
	}
	
	@RequestMapping("/toChgCurUserPassword.do")
	/**
	 * 跳转到改变用户密码的页面
	 * @return
	 */
	public String toChgCurUserPassword() {
		return "workspace/password_chg";
	}
	
	@RequestMapping("/updateCurUserPassword.do")
	@ResponseBody
	/**
	 * 更新密码
	 * @param authentication
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	public String updateCurUserPassword(Authentication authentication, String oldPassword, String newPassword) {
		String acctName = authentication.getName();
		//执行更新密码
		String retMsg = userService.updatePasswordWithCheck(acctName, oldPassword, newPassword);
		
		if(StringUtils.isEmpty(retMsg)) {
			retMsg = "Success";
		}
		
		return retMsg;
	}
	
	
	@RequestMapping("/toScoreQry.do")
	/**
	 * 跳转到成绩页面
	 * @param principal
	 * @param model
	 * @return
	 */
	public String toScoreQry(Principal principal, Model model) {
		//获取成绩信息
		ExamApplyInfoBean examApplyInfo = examService.findUserExamApplyByAcctName(principal.getName());
		model.addAttribute("examApplyInfo", examApplyInfo);
		return "workspace/score_result";
	}
	
	@RequestMapping("/toExamApply.do")
	/**
	 * 跳转到报名页面
	 * @param principal
	 * @param model
	 * @return
	 */
	public String toExamApply(Principal principal, Model model) {
		//查询是否已经报名了
		ExamApplyInfoBean examApplyInfo = examService.findUserExamApplyByAcctName(principal.getName());
		if(null != examApplyInfo) {
			//如果已经报名了，跳转到准考证页面
			model.addAttribute("examApplyInfo", examApplyInfo);
			return "workspace/exam_ticket_info";
		}
		
		
		//获取考试列表(如六级考试、四级考试)
		List<SysDictItemBean> examList = examService.getCurExamEnumList();
		model.addAttribute("examList", examList);
		
		//获取考点名称列表，如北京、上海
		List<SysDictItemBean> examPlaceIdList = examService.getExamPlaceEnumList(ExamConstants.REGION_BEIJING);
		model.addAttribute("examPlaceIdList", examPlaceIdList);
		
		//获取个人信息
		String acctName = principal.getName();
		UserBean userBean = userService.findUserByAcctName(acctName);
		examApplyInfo = new ExamApplyInfoBean();
		examApplyInfo.setName(userBean.getName());
		examApplyInfo.setIdentityNum(userBean.getIdentityNum());
		examApplyInfo.setIdentityType(userBean.getIdentityType());
		model.addAttribute("examApplyInfo", examApplyInfo);
		
		//跳转到报名页面
		return "workspace/exam_apply";
	}
	
	@RequestMapping("/getExamPlaceByRegionId.do")
    @ResponseBody
    /**
     	* 获取考试地点信息列表
     * @param regionId
     * @return
     */
    public List<ExamPlaceBean> getExamPlaceByRegionId(String regionId) {
		return examService.findExamPlaceByRegionId(regionId);
	}
	
	
	
	@RequestMapping("/uploadHeadPhoto.do")
    @ResponseBody
    /**
     * 上传报名的照片
     * @param file
     * @param request
     * @return
     */
    public Map<String, String> uploadHeadPhoto(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		
		Map<String, String> result = new HashMap<String, String>();
		
        if (file.isEmpty()) {
        	result.put("errMsg", "上传失败，请选择文件");
            return result;
        }
        
        if (file.getSize() / 1024  > 1024){
        	result.put("errMsg", "图片大小不能超过1MB");
            return result;
        }
        
        
        //生成唯一文件名，防止被覆盖
        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        String newFileName = UUID.randomUUID() + suffixName;
        try {
        	//保存上传的照片到硬盘
        	File dest = new File(userImagesPath + newFileName);
        	file.transferTo(dest);
        	
        	String srcUrl = request.getContextPath() + "/userImages/" + newFileName;
            result.put("url", srcUrl);
            return result;
        } catch (Exception e) {
        	e.printStackTrace();
        }
        
        result.put("errMsg", "上传失败");
        return result;
    }
	
	
	
	@RequestMapping("/saveExamApplyInfo.do")
    @ResponseBody
    /**
     * 保存报名信息
     * @param examApplyInfo
     * @param principal
     * @return
     */
    public String saveExamApplyInfo(ExamApplyInfoBean examApplyInfo, Principal principal) {
		//保存报名信息
		String errMsg = examService.saveExamApplyInfo(examApplyInfo, principal.getName());
		if(!StringUtils.isEmpty(errMsg)) {
			return errMsg;
		}
		
		return "Success";
	}
	
	@RequestMapping("/toConfirmExamApplyInfo.do")
	/**
	 * 跳转到报名确认页面
	 * @param examApplyInfo
	 * @param model
	 * @return
	 */
	public String toConfirmExamApplyInfo(ExamApplyInfoBean examApplyInfo, Model model) {
		//获取考试信息
		ExamInfoBean examInfo = examService.getExamInfoById(examApplyInfo.getExamInfo().getId());
		examApplyInfo.setExamInfo(examInfo);
		
		//获取地点信息
		ExamPlaceBean placeBean = examService.getExamPlaceById(examApplyInfo.getExamPlace().getId());
		examApplyInfo.setExamPlace(placeBean);
		
		model.addAttribute("examApplyInfo", examApplyInfo);
		return "workspace/confirm_exam_apply_info";
	}
	
}
