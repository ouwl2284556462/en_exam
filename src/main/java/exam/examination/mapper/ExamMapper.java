package exam.examination.mapper;

import java.util.List;

import exam.examination.bean.ExamApplyInfoBean;
import exam.examination.bean.ExamInfoBean;
import exam.examination.bean.ExamPlaceBean;
import exam.examination.bean.UserExamApplyBean;

public interface ExamMapper {

	public List<ExamInfoBean> findExamInfosByState(String state);

	public List<ExamPlaceBean> findExamPlaceByRegionId(String regionId);

	public void insertExamApplyInfo(ExamApplyInfoBean examApplyInfo);

	public void insertUserExamApply(UserExamApplyBean userExamApply);

	public ExamInfoBean getExamInfoById(Integer id);

	public ExamPlaceBean getExamPlaceById(Integer id);

	public ExamApplyInfoBean findUserExamApplyByUserId(Integer id);
}
