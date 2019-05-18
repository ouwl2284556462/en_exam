package exam.examination.mapper;

import java.util.List;
import java.util.Map;

import exam.examination.bean.ExamApplyInfoBean;
import exam.examination.bean.ExamInfoBean;
import exam.examination.bean.ExamPlaceBean;
import exam.examination.bean.ExamScoreBean;
import exam.examination.bean.ExamSearch;
import exam.examination.bean.UserExamApplyBean;

/**
 * 考试信息查询数据库使用
 */
public interface ExamMapper {

	//根据状态查询考试
	public List<ExamInfoBean> findExamInfosByState(String state);

	//根据地区查询考点信息
	public List<ExamPlaceBean> findExamPlaceByRegionId(String regionId);

	//插入报名信息
	public void insertExamApplyInfo(ExamApplyInfoBean examApplyInfo);

	//插入用户报考关系信息
	public void insertUserExamApply(UserExamApplyBean userExamApply);

	//根据id获取考试信息
	public ExamInfoBean getExamInfoById(Integer id);

	//根据Id获取考点信息
	public ExamPlaceBean getExamPlaceById(Integer id);

	//根据用户id获取报考信息
	public ExamApplyInfoBean findUserExamApplyByUserId(Integer id);

	//修改分数
	public void saveExamScore(ExamScoreBean bean);
	
	//根据多个跳转获取考生信息
	public List<ExamApplyInfoBean> findExamListByMultiCondi(ExamSearch searchBean);

	//删除报名信息
	public void deleteExamApplyInfo(int[] ids);

	//删除用户与报名信息的关系
	public void deleteUserExam(int[] ids);

	//根据id获取报名信息
	public ExamApplyInfoBean findExamApplyById(int applyId);

	//更新报名信息
	public void updateExamApplyInfo(ExamApplyInfoBean examApplyInfo);

	//根据成绩统计
	public Map<String, Object> statisticsScore(Integer examId);

	//根据地区统计
	public Map<String, Object> statisticsRegion(Integer examId);
}
