package exam.examination.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import exam.base.constants.ExamConstants;
import exam.dict.bean.SysDictItemBean;
import exam.examination.bean.ExamApplyInfoBean;
import exam.examination.bean.ExamInfoBean;
import exam.examination.bean.ExamPlaceBean;
import exam.examination.bean.ExamScoreBean;
import exam.examination.bean.ExamSearch;
import exam.examination.bean.UserExamApplyBean;
import exam.examination.mapper.ExamMapper;
import exam.examination.service.ExamService;
import exam.user.bean.UserBean;
import exam.user.service.UserService;

@Service
/**
 * 考试服务类
 */
public class ExamServiceImpl implements ExamService{

	@Autowired
	private ExamMapper examMapper;
	
	@Autowired
	private UserService userService;
	
	@Override
	/**
	 * 获取当期可报考的考试
	 */	
	public List<ExamInfoBean> findCurExamInfos() {
		//获取当期可报考的考试
		return examMapper.findExamInfosByState(ExamConstants.STATE_CUR_VALID_EXAM);
	}

	@Override
	/**
	 * 获取当期可报考的考试
	 */
	public List<SysDictItemBean> getCurExamEnumList() {
		//获取当期可报考的考试
		List<ExamInfoBean> examList = findCurExamInfos();
		if(CollectionUtils.isEmpty(examList)) {
			return Collections.emptyList();
		}
		
		//转成统一的SysDictItemBean结构，方便让页面处理
		List<SysDictItemBean> result = new ArrayList<SysDictItemBean>(examList.size());
		for(ExamInfoBean exam : examList) {
			SysDictItemBean bean = new SysDictItemBean();
			bean.setItemId(String.valueOf(exam.getId()));
			bean.setItemName(exam.getExamName());
			result.add(bean);
		}
		
		return result;
	}
	
	@Override
	/**
	 * 根据地区id获取考点列表
	 */
	public List<ExamPlaceBean> findExamPlaceByRegionId(String regionId){
		return examMapper.findExamPlaceByRegionId(regionId);
	}

	@Override
	/**
	 * 获取考点枚举
	 */
	public List<SysDictItemBean> getExamPlaceEnumList(String regionId) {
		//获取考点列表
		List<ExamPlaceBean> placeList = findExamPlaceByRegionId(regionId);
		if(CollectionUtils.isEmpty(placeList)) {
			return Collections.emptyList();
		}
		
		//转成统一的SysDictItemBean结构，方便让页面处理
		List<SysDictItemBean> result = new ArrayList<SysDictItemBean>(placeList.size());
		for(ExamPlaceBean place : placeList) {
			SysDictItemBean bean = new SysDictItemBean();
			bean.setItemId(String.valueOf(place.getId()));
			bean.setItemName(place.getName());
			result.add(bean);
		}
		
		return result;
	}

	@Transactional
	@Override
	/**
	 * 考试报名
	 */
	public String saveExamApplyInfo(ExamApplyInfoBean examApplyInfo, String acctName) {
		UserBean userBean = userService.findUserByAcctName(acctName);
		if(null == userBean) {
			return "当前用户信息不存在。";
		}
		
		if(null != examMapper.findUserExamApplyByUserId(userBean.getId())) {
			return "不能重复报名。";
		}
		
		
		
		LocalDate today = LocalDate.now();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyMM");
        //设置编码
		examApplyInfo.setCode(dateFormat.format(today) + "N");
		//插入考生详细信息
		examMapper.insertExamApplyInfo(examApplyInfo);

		//插入考生与用户的关系
		UserExamApplyBean userExamApply = new UserExamApplyBean();
		userExamApply.setUserId(userBean.getId());
		userExamApply.setExamApplyId(examApplyInfo.getId());
		examMapper.insertUserExamApply(userExamApply);
		return null;
	}

	@Override
	/**
	 * 根据id获取考试信息
	 */
	public ExamInfoBean getExamInfoById(Integer id) {
		return examMapper.getExamInfoById(id);
	}

	@Override
	/**
	 * 根据id获取考点信息
	 */
	public ExamPlaceBean getExamPlaceById(Integer id) {
		return examMapper.getExamPlaceById(id);
	}

	@Override
	/**
	 * 根据用户名获取报考信息
	 */
	public ExamApplyInfoBean findUserExamApplyByAcctName(String acctName) {
		UserBean userBean = userService.findUserByAcctName(acctName);
		if(null == userBean) {
			return null;
		}
		
		return examMapper.findUserExamApplyByUserId(userBean.getId());
	}

	@Override
	/**
	 * 修改分数
	 */
	public void saveExamScore(int tickNum, int score, int operatorId) {
		ExamScoreBean bean = new ExamScoreBean();
		bean.setExamApplyId(tickNum);
		bean.setScore(score);
		bean.setOperatorId(operatorId);
		
		examMapper.saveExamScore(bean);
	}

	@Override
	/**
	 * 获取考生列表
	 */
	public List<ExamApplyInfoBean> findExamListByMultiCondi(ExamSearch search) {
		return examMapper.findExamListByMultiCondi(search);
	}

	@Override
	/**
	 * 获取考生列表，分页
	 */
	public PageInfo<ExamApplyInfoBean> findExamListByMultiCondiByPage(ExamSearch search, Integer pageNum, int pagePerNum) {
		//根据准考证号获取id
		changeExamSearchCode(search);
		//设置分页信息
		PageHelper.startPage(pageNum, pagePerNum);
		//查找
		List<ExamApplyInfoBean> result = examMapper.findExamListByMultiCondi(search);
		PageInfo<ExamApplyInfoBean> pageInfo = new PageInfo<ExamApplyInfoBean>(result);
		return pageInfo;
	}

	/**
	 * 根据准考证号获取id
	 * @param search
	 */
	private void changeExamSearchCode(ExamSearch search) {
		String code = search.getCode();
		if(StringUtils.isEmpty(code)) {
			return;
		}
		
		search.setId(-1);
		int lastSpliter = code.lastIndexOf("N");
		if(lastSpliter < 0) {
			return;
		}
		
		int subStrStartIndex = lastSpliter + 1;
		if(code.length() < subStrStartIndex) {
			return;
		}
		
		try {
			search.setId(Integer.parseInt(code.substring(subStrStartIndex)));
		}catch (Exception e) {
			search.setCode(null);
		}
	}

	@Transactional
	@Override
	/**
	 * 删除报考信息
	 */
	public void deleteStudentExam(int[] ids) {
		examMapper.deleteExamApplyInfo(ids);
		examMapper.deleteUserExam(ids);
	}

	@Override
	/**
	 * 根据id查找报考信息
	 */
	public ExamApplyInfoBean findExamApplyById(int applyId) {
		return examMapper.findExamApplyById(applyId);
	}

	@Transactional
	@Override
	/**
	 * 跟新考生信息
	 */
	public String updateExamApplyInfo(ExamApplyInfoBean examApplyInfo, Integer operatorId) {
		//更新详细信息
		examMapper.updateExamApplyInfo(examApplyInfo);
		
		ExamScoreBean scoreBean = examApplyInfo.getExamScore();
		if(null != scoreBean) {
			//更新分数
			scoreBean.setExamApplyId(examApplyInfo.getId());
			scoreBean.setOperatorId(operatorId);
			examMapper.saveExamScore(scoreBean);
		}
		return null;
	}

	@Override
	/**
	 * 根据分数统计
	 */
	public Map<String, Object> statisticsScore(Integer examId) {
		return examMapper.statisticsScore(examId);
	}

	@Override
	/**
	 * 根据地区统计
	 */
	public Map<String, Object> statisticsRegion(Integer examId) {
		return examMapper.statisticsRegion(examId);
	}

}
