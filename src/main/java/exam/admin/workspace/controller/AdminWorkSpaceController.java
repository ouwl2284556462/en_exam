package exam.admin.workspace.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;

import exam.dict.bean.SysDictItemBean;
import exam.examination.bean.ExamApplyInfoBean;
import exam.examination.bean.ExamSearch;
import exam.examination.service.ExamService;
import exam.user.bean.UserBean;
import exam.user.service.UserService;

@Controller
@RequestMapping("/admin/Workspace")
/**
 * 管理员相关 
 *
 */
public class AdminWorkSpaceController {
	
	//成绩导入模板文件路径
	@Value("${exam.importScoreTemplateFilePath}")
    private String importScoreTemplateFilePath;
	
	//成绩导入模板文件路径名
	@Value("${exam.importScoreTemplateFileName}")
    private String importScoreTemplateFileName;	
	
	//用户列表每页显示多少条
	private static final int USER_LIST_PAGE_PER_NUM = 10;
	
	//考生列表每页显示多少条
	private static final int STUDENT_EXAM_LIST_PAGE_PER_NUM = 10;
	
	//用户服务类
	@Autowired
	private UserService userService;
	
	//考试信息服务类
	@Autowired
	private ExamService examService;
	
	@RequestMapping("/toUserManage.do")
	/**
	 * 跳转到用户管理页面
	 * @return
	 */
	public String toUserManage() {
		return "admin/workspace/user_manage";
	}
	
	@RequestMapping("/qryUserList.do")
	/**
	 * 获取用户列表
	 * @param model
	 * @param userBean
	 * @param pageNum
	 * @return
	 */
	public String qryUserList(Model model, UserBean userBean, @RequestParam(value="pageNum", defaultValue="1") Integer pageNum) {
		
		PageInfo<UserBean> pageInfo = userService.qryUserListByPage(userBean, pageNum, USER_LIST_PAGE_PER_NUM);
		model.addAttribute("pageInfo", pageInfo);
		return "admin/workspace/user_list";
	}
	
	@RequestMapping("/deleteUserList.do")
	@ResponseBody
	/**
	 * 删除用户
	 * @param ids
	 * @return
	 */
	public String deleteUserList(@RequestParam(value = "ids[]") String[] ids) {
		userService.deleteUser(ids);
		return "Success";
	}
	
	@RequestMapping("/toChgUserInfo.do")
	/**
	 * 获取用户详细信息，并跳转到用户详细信息页面
	 * @param model
	 * @param userId
	 * @return
	 */
	public String toChgUserInfo(Model model, int userId) {
		//获取用户详细信息
		UserBean userBean = userService.findUserById(userId);
		model.addAttribute("userBean", userBean);
		model.addAttribute("from", "adminChgInfo");
		return "workspace/user_info";
	}
	
	@RequestMapping("/toChgPassword.do")
	/**
	 * 跳转到密码修改页面
	 * @param model
	 * @param userId
	 * @return
	 */
	public String toChgPassword(Model model, int userId) {
		model.addAttribute("userId", userId);
		model.addAttribute("from", "adminChgInfo");
		return "workspace/password_chg";
	}
	
	@RequestMapping("/changePassword.do")
	@ResponseBody
	/**
	 * 修改密码
	 * @param userId
	 * @param newPassword
	 * @return
	 */
	public String changePassword(int userId, String newPassword) {
		userService.updatePassword(userId, newPassword);
		return "Success";
	}
	
	@RequestMapping("/toImportScore.do")
	/**
	 * 跳转到导入成绩信息页面
	 * @return
	 */
	public String toImportScore() {
		return "admin/workspace/import_score";
	}
	
	
	@RequestMapping("/toStudentManager.do")
	/**
	 * 获取考生列表
	 * @param model
	 * @return
	 */
	public String toStudentManager(Model model) {
		//考试列表
		List<SysDictItemBean> examList = examService.getCurExamEnumList();
		model.addAttribute("examList", examList);
		return "admin/workspace/student_manager";
	}
	
	@RequestMapping("/downloadImportScoreTemplate.do")
	/**
	 * 下载导入成绩模板
	 * @param request
	 * @param response
	 * @return
	 */
	public String downloadImportScoreTemplate(HttpServletRequest request, HttpServletResponse response) {
		// 设置文件路径
		File file = new File(importScoreTemplateFilePath, importScoreTemplateFileName);
		// 设置强制下载打开
		response.setContentType("application/force-download");
		// 设置文件名
		response.addHeader("Content-Disposition", "attachment;fileName=" + importScoreTemplateFileName);
		byte[] buffer = new byte[1024];
		//发送模板到客户端
		try(FileInputStream fis  = new FileInputStream(file);
			BufferedInputStream bis = new BufferedInputStream(fis);) {
			OutputStream os = response.getOutputStream();
			int readCount;
			while ((readCount = bis.read(buffer)) != -1) {
				os.write(buffer, 0, readCount);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/importScore.do")
    @ResponseBody
    /**
     * 导入成绩
     * @param file
     * @param principal
     * @return
     */
    public String importScore(@RequestParam("file") MultipartFile file, Principal principal) {
		int operatorId = userService.findUserByAcctName(principal.getName()).getId();
		
		try(InputStream inputStream = file.getInputStream()){
	        //创建Excel工作薄
	        Workbook work = getWorkbook(inputStream, file.getOriginalFilename());
	        if (null == work) {
	        	return "文件为空";
	        }
	        
	        if(work.getNumberOfSheets() < 1) {
	        	return "文件为空";
	        }
	        
	        Sheet sheet = work.getSheetAt(0);
            if (sheet == null) {
            	return "文件为空";
            }	        

			for (int j = sheet.getFirstRowNum() + 1; j <= sheet.getLastRowNum(); j++) {
				//导入Excel中的成绩信息
				Row row = sheet.getRow(j);
				String tickNum = row.getCell(0).getStringCellValue();
				int applyId = Integer.parseInt(tickNum.substring( tickNum.lastIndexOf("N") + 1));
				int score = Integer.parseInt(row.getCell(1).getStringCellValue());
				//保存成绩
				examService.saveExamScore(applyId, score, operatorId);
			}
			work.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			return "导入失败:" + e.getMessage();
		}
		
		return "Success";
	}
	
	/**
	 * 获取Excel的工作本
	 * @param inStr
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
    private Workbook getWorkbook(InputStream inStr, String fileName) throws Exception {
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if (".xls".equals(fileType)) {
            return new HSSFWorkbook(inStr);
        }
        
        if (".xlsx".equals(fileType)) {
            return new XSSFWorkbook(inStr);
        }
        
        return null;
    }
    
    @RequestMapping("/qryExamList.do")
    /**
     * 查询考生列表
     * @param search
     * @param model
     * @param pageNum
     * @return
     */
    public String qryExamList(ExamSearch search, Model model,  @RequestParam(value="pageNum", defaultValue="1") Integer pageNum) {
		PageInfo<ExamApplyInfoBean> pageInfo = examService.findExamListByMultiCondiByPage(search, pageNum, STUDENT_EXAM_LIST_PAGE_PER_NUM);
    	model.addAttribute("pageInfo", pageInfo);
    	return "admin/workspace/student_exam_list";
    }
    
    
	@RequestMapping("/deleteStudentExamList.do")
	@ResponseBody
	/**
	 * 删除考生信息
	 * @param ids
	 * @return
	 */
	public String deleteStudentExamList(@RequestParam(value = "ids[]") int[] ids) {
		examService.deleteStudentExam(ids);
		return "Success";
	}
	
	@RequestMapping("/printTicket.do")
	/**
	 * 打印准考证
	 * @param applyId
	 * @param model
	 * @return
	 */
	public String printTicket(Integer applyId, Model model) {
		ExamApplyInfoBean examApplyInfo = examService.findExamApplyById(applyId);
		model.addAttribute("examApplyInfo", examApplyInfo);
		return "workspace/exam_ticket_info";
	}
	
	@RequestMapping("/toChgStudentExamInfo.do")
	/**
	 * 跳转到考生信息详细页面
	 * @param applyId
	 * @param model
	 * @return
	 */
	public String toChgStudentExamInfo(Integer applyId, Model model) {
		//获取考生信息
		ExamApplyInfoBean examApplyInfo = examService.findExamApplyById(applyId);
		model.addAttribute("examApplyInfo", examApplyInfo);
		
		//考试列表
		List<SysDictItemBean> examList = examService.getCurExamEnumList();
		model.addAttribute("examList", examList);
		
		//考点名称列表
		List<SysDictItemBean> examPlaceIdList = examService.getExamPlaceEnumList(examApplyInfo.getExamPlace().getRegion());
		model.addAttribute("examPlaceIdList", examPlaceIdList);
		
		model.addAttribute("from", "adminChgInfo");
		return "workspace/exam_apply";
	}
	
	@RequestMapping("/saveExamApplyInfo.do")
    @ResponseBody
    /**
     * 修改考生信息
     * @param examApplyInfo
     * @param principal
     * @return
     */
    public String saveExamApplyInfo(ExamApplyInfoBean examApplyInfo, Principal principal) {
		Integer operatorId = userService.findUserByAcctName(principal.getName()).getId();
		String errMsg = examService.updateExamApplyInfo(examApplyInfo, operatorId);
		if(!StringUtils.isEmpty(errMsg)) {
			return errMsg;
		}
		
		return "Success";
	}
	
	
	@RequestMapping("/toQryStudentExamDtl.do")
	/**
	 * 跳转到考生详细信息，用户显示
	 * @param applyId
	 * @param model
	 * @return
	 */
	public String toQryStudentExamDtl(Integer applyId, Model model) {
		ExamApplyInfoBean examApplyInfo = examService.findExamApplyById(applyId);
		model.addAttribute("examApplyInfo", examApplyInfo);
		
		//考试列表
		List<SysDictItemBean> examList = examService.getCurExamEnumList();
		model.addAttribute("examList", examList);
		
		//考点名称列表
		List<SysDictItemBean> examPlaceIdList = examService.getExamPlaceEnumList(examApplyInfo.getExamPlace().getRegion());
		model.addAttribute("examPlaceIdList", examPlaceIdList);
		
		model.addAttribute("from", "adminChgInfo");
		model.addAttribute("readonly", true);
		return "workspace/exam_apply";
	}
	
	
	@RequestMapping("/toExamStatistics.do")
	/**
	 * 跳转到统计页面
	 * @param model
	 * @return
	 */
	public String toQryStudentExamDtl(Model model) {
		//考试列表
		List<SysDictItemBean> examList = examService.getCurExamEnumList();
		model.addAttribute("examList", examList);
		return "admin/workspace/exam_statistics";
	}
	
	@RequestMapping("/statisticsScore.do")
	@ResponseBody
	/**
	 * 根据成绩统计
	 * @param examId
	 * @return
	 */
	public Map<String, Object> statisticsScore(Integer examId) {
		return examService.statisticsScore(examId);
	}
	
	@RequestMapping("/statisticsRegion.do")
	@ResponseBody
	/**
	 * 根据地区统计
	 * @param examId
	 * @return
	 */
	public Map<String, Object> statisticsRegion(Integer examId) {
		return examService.statisticsRegion(examId);
	}
	
}
