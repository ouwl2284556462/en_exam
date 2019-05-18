package exam.examination.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;

import exam.dict.bean.SysDictItemBean;
import exam.examination.bean.ExamApplyInfoBean;
import exam.examination.bean.ExamInfoBean;
import exam.examination.bean.ExamPlaceBean;
import exam.examination.bean.ExamSearch;

/**
 * 考试相关服务
 */
public interface ExamService {
	//获取当前可以报考的考试
	public List<ExamInfoBean> findCurExamInfos();
	
	//获取考试列表
	public List<SysDictItemBean> getCurExamEnumList();

	//获取考点列表
	public List<SysDictItemBean> getExamPlaceEnumList(String regionId);
	
	//根据地区获取考点信息
	public List<ExamPlaceBean> findExamPlaceByRegionId(String regionId);

	//保存报考信息
	public String saveExamApplyInfo(ExamApplyInfoBean examApplyInfo, String acctName);

	//根据id获取报考信息
	public ExamInfoBean getExamInfoById(Integer id);

	//根据id获取考点信息
	public ExamPlaceBean getExamPlaceById(Integer id);

	//根据用户名获取报考信息
	public ExamApplyInfoBean findUserExamApplyByAcctName(String name);

	//保存分数
	public void saveExamScore(int tickNum, int score, int operatorId);

	//根据多条件获取考生列表
	public List<ExamApplyInfoBean> findExamListByMultiCondi(ExamSearch search);

	//根据多条件获取考生列表，分页
	public PageInfo<ExamApplyInfoBean> findExamListByMultiCondiByPage(ExamSearch search, Integer pageNum,
			int userListPagePerNum);

	//删除考生信息
	public void deleteStudentExam(int[] ids);

	//根据id查找考生信息
	public ExamApplyInfoBean findExamApplyById(int applyId);

	//根据考生信息
	public String updateExamApplyInfo(ExamApplyInfoBean examApplyInfo, Integer operatorId);

	//根据分数统计
	public Map<String, Object> statisticsScore(Integer examId);

	//根据地区统计
	public Map<String, Object> statisticsRegion(Integer examId);

}
