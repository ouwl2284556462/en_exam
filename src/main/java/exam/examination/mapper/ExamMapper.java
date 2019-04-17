package exam.examination.mapper;

import java.util.List;
import java.util.Map;

import exam.examination.bean.ExamApplyInfoBean;
import exam.examination.bean.ExamInfoBean;
import exam.examination.bean.ExamPlaceBean;
import exam.examination.bean.ExamScoreBean;
import exam.examination.bean.ExamSearch;
import exam.examination.bean.UserExamApplyBean;

public interface ExamMapper {

	public List<ExamInfoBean> findExamInfosByState(String state);

	public List<ExamPlaceBean> findExamPlaceByRegionId(String regionId);

	public void insertExamApplyInfo(ExamApplyInfoBean examApplyInfo);

	public void insertUserExamApply(UserExamApplyBean userExamApply);

	public ExamInfoBean getExamInfoById(Integer id);

	public ExamPlaceBean getExamPlaceById(Integer id);

	public ExamApplyInfoBean findUserExamApplyByUserId(Integer id);

	public void saveExamScore(ExamScoreBean bean);
	
	public List<ExamApplyInfoBean> findExamListByMultiCondi(ExamSearch searchBean);

	public void deleteExamApplyInfo(int[] ids);

	public void deleteUserExam(int[] ids);

	public ExamApplyInfoBean findExamApplyById(int applyId);

	public void updateExamApplyInfo(ExamApplyInfoBean examApplyInfo);

	public Map<String, Object> statisticsScore(Integer examId);

	public Map<String, Object> statisticsRegion(Integer examId);
}
