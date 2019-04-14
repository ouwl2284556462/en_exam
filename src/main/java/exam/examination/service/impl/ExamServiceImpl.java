package exam.examination.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import exam.base.constants.ExamConstants;
import exam.dict.bean.SysDictItemBean;
import exam.examination.bean.ExamApplyInfoBean;
import exam.examination.bean.ExamInfoBean;
import exam.examination.bean.ExamPlaceBean;
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

}
