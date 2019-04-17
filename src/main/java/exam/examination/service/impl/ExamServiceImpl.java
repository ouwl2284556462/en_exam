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
public class ExamServiceImpl implements ExamService{

	@Autowired
	private ExamMapper examMapper;
	
	@Autowired
	private UserService userService;
	
	@Override
	public List<ExamInfoBean> findCurExamInfos() {
		return examMapper.findExamInfosByState(ExamConstants.STATE_CUR_VALID_EXAM);
	}

	@Override
	public List<SysDictItemBean> getCurExamEnumList() {
		List<ExamInfoBean> examList = findCurExamInfos();
		if(CollectionUtils.isEmpty(examList)) {
			return Collections.emptyList();
		}
		
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
	public List<ExamPlaceBean> findExamPlaceByRegionId(String regionId){
		return examMapper.findExamPlaceByRegionId(regionId);
	}

	@Override
	public List<SysDictItemBean> getExamPlaceEnumList(String regionId) {
		List<ExamPlaceBean> placeList = findExamPlaceByRegionId(regionId);
		if(CollectionUtils.isEmpty(placeList)) {
			return Collections.emptyList();
		}
		
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
		examApplyInfo.setCode(dateFormat.format(today) + "N");
		examMapper.insertExamApplyInfo(examApplyInfo);
		

		
		
		UserExamApplyBean userExamApply = new UserExamApplyBean();
		userExamApply.setUserId(userBean.getId());
		userExamApply.setExamApplyId(examApplyInfo.getId());
		examMapper.insertUserExamApply(userExamApply);
		return null;
	}

	@Override
	public ExamInfoBean getExamInfoById(Integer id) {
		return examMapper.getExamInfoById(id);
	}

	@Override
	public ExamPlaceBean getExamPlaceById(Integer id) {
		return examMapper.getExamPlaceById(id);
	}

	@Override
	public ExamApplyInfoBean findUserExamApplyByAcctName(String acctName) {
		UserBean userBean = userService.findUserByAcctName(acctName);
		if(null == userBean) {
			return null;
		}
		
		return examMapper.findUserExamApplyByUserId(userBean.getId());
	}

	@Override
	public void saveExamScore(int tickNum, int score, int operatorId) {
		ExamScoreBean bean = new ExamScoreBean();
		bean.setExamApplyId(tickNum);
		bean.setScore(score);
		bean.setOperatorId(operatorId);
		
		examMapper.saveExamScore(bean);
	}

	@Override
	public List<ExamApplyInfoBean> findExamListByMultiCondi(ExamSearch search) {
		return examMapper.findExamListByMultiCondi(search);
	}

	@Override
	public PageInfo<ExamApplyInfoBean> findExamListByMultiCondiByPage(ExamSearch search, Integer pageNum, int pagePerNum) {
		changeExamSearchCode(search);
		PageHelper.startPage(pageNum, pagePerNum);
		List<ExamApplyInfoBean> result = examMapper.findExamListByMultiCondi(search);
		PageInfo<ExamApplyInfoBean> pageInfo = new PageInfo<ExamApplyInfoBean>(result);
		return pageInfo;
	}

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
	public void deleteStudentExam(int[] ids) {
		examMapper.deleteExamApplyInfo(ids);
		examMapper.deleteUserExam(ids);
	}

	@Override
	public ExamApplyInfoBean findExamApplyById(int applyId) {
		return examMapper.findExamApplyById(applyId);
	}

	@Transactional
	@Override
	public String updateExamApplyInfo(ExamApplyInfoBean examApplyInfo, Integer operatorId) {
		examMapper.updateExamApplyInfo(examApplyInfo);
		
		ExamScoreBean scoreBean = examApplyInfo.getExamScore();
		if(null != scoreBean) {
			scoreBean.setExamApplyId(examApplyInfo.getId());
			scoreBean.setOperatorId(operatorId);
			examMapper.saveExamScore(scoreBean);
		}
		return null;
	}

	@Override
	public Map<String, Object> statisticsScore(Integer examId) {
		return examMapper.statisticsScore(examId);
	}

	@Override
	public Map<String, Object> statisticsRegion(Integer examId) {
		return examMapper.statisticsRegion(examId);
	}

}
