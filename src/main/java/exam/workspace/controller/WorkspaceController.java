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
public class WorkspaceController {
	
	@Value("${user.imagesFilePath}")
    private String userImagesPath;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ExamService examService;

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
	
	@RequestMapping("/updateUserDtl.do")
	@ResponseBody
	public String updateUserDtl(UserBean userBean, Model model) {
		userService.updateUserDtl(userBean);
		model.addAttribute("userBean", userBean);
		return "Success";
	}
	
	@RequestMapping("/toChgCurUserPassword.do")
	public String toChgCurUserPassword() {
		return "workspace/password_chg";
	}
	
	@RequestMapping("/updateCurUserPassword.do")
	@ResponseBody
	public String updateCurUserPassword(Authentication authentication, String oldPassword, String newPassword) {
		String acctName = authentication.getName();
		String retMsg = userService.updatePasswordWithCheck(acctName, oldPassword, newPassword);
		
		if(StringUtils.isEmpty(retMsg)) {
			retMsg = "Success";
		}
		
		return retMsg;
	}
	
	
	@RequestMapping("/toScoreQry.do")
	public String toScoreQry(Principal principal, Model model) {
		ExamApplyInfoBean examApplyInfo = examService.findUserExamApplyByAcctName(principal.getName());
		model.addAttribute("examApplyInfo", examApplyInfo);
		return "workspace/score_result";
	}
	
	@RequestMapping("/toExamApply.do")
	public String toExamApply(Principal principal, Model model) {
		ExamApplyInfoBean examApplyInfo = examService.findUserExamApplyByAcctName(principal.getName());
		if(null != examApplyInfo) {
			model.addAttribute("examApplyInfo", examApplyInfo);
			return "workspace/exam_ticket_info";
		}
		
		
		//考试列表
		List<SysDictItemBean> examList = examService.getCurExamEnumList();
		model.addAttribute("examList", examList);
		
		//考点名称列表
		List<SysDictItemBean> examPlaceIdList = examService.getExamPlaceEnumList(ExamConstants.REGION_BEIJING);
		model.addAttribute("examPlaceIdList", examPlaceIdList);
		
		//个人信息
		String acctName = principal.getName();
		UserBean userBean = userService.findUserByAcctName(acctName);
		examApplyInfo = new ExamApplyInfoBean();
		examApplyInfo.setName(userBean.getName());
		examApplyInfo.setIdentityNum(userBean.getIdentityNum());
		examApplyInfo.setIdentityType(userBean.getIdentityType());
		model.addAttribute("examApplyInfo", examApplyInfo);
		
		return "workspace/exam_apply";
	}
	
	@RequestMapping("/getExamPlaceByRegionId.do")
    @ResponseBody
    public List<ExamPlaceBean> getExamPlaceByRegionId(String regionId) {
		return examService.findExamPlaceByRegionId(regionId);
	}
	
	
	
	@RequestMapping("/uploadHeadPhoto.do")
    @ResponseBody
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
        
        

        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        String newFileName = UUID.randomUUID() + suffixName;
        try {
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
    public String saveExamApplyInfo(ExamApplyInfoBean examApplyInfo, Principal principal) {
		String errMsg = examService.saveExamApplyInfo(examApplyInfo, principal.getName());
		if(!StringUtils.isEmpty(errMsg)) {
			return errMsg;
		}
		
		return "Success";
	}
	
	@RequestMapping("/toConfirmExamApplyInfo.do")
	public String toConfirmExamApplyInfo(ExamApplyInfoBean examApplyInfo, Model model) {
		ExamInfoBean examInfo = examService.getExamInfoById(examApplyInfo.getExamInfo().getId());
		examApplyInfo.setExamInfo(examInfo);
		
		ExamPlaceBean placeBean = examService.getExamPlaceById(examApplyInfo.getExamPlace().getId());
		examApplyInfo.setExamPlace(placeBean);
		
		model.addAttribute("examApplyInfo", examApplyInfo);
		return "workspace/confirm_exam_apply_info";
	}
	
}
