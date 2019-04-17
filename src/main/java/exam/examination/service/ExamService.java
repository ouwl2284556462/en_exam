package exam.examination.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;

import exam.dict.bean.SysDictItemBean;
import exam.examination.bean.ExamApplyInfoBean;
import exam.examination.bean.ExamInfoBean;
import exam.examination.bean.ExamPlaceBean;
import exam.examination.bean.ExamSearch;

public interface ExamService {
	public List<ExamInfoBean> findCurExamInfos();
	
	public List<SysDictItemBean> getCurExamEnumList();

	public List<SysDictItemBean> getExamPlaceEnumList(String regionId);
	
	public List<ExamPlaceBean> findExamPlaceByRegionId(String regionId);

	public String saveExamApplyInfo(ExamApplyInfoBean examApplyInfo, String acctName);

	public ExamInfoBean getExamInfoById(Integer id);

	public ExamPlaceBean getExamPlaceById(Integer id);

	public ExamApplyInfoBean findUserExamApplyByAcctName(String name);

	public void saveExamScore(int tickNum, int score, int operatorId);

	public List<ExamApplyInfoBean> findExamListByMultiCondi(ExamSearch search);

	public PageInfo<ExamApplyInfoBean> findExamListByMultiCondiByPage(ExamSearch search, Integer pageNum,
			int userListPagePerNum);

	public void deleteStudentExam(int[] ids);

	public ExamApplyInfoBean findExamApplyById(int applyId);

	public String updateExamApplyInfo(ExamApplyInfoBean examApplyInfo, Integer operatorId);

	public Map<String, Object> statisticsScore(Integer examId);

	public Map<String, Object> statisticsRegion(Integer examId);

}
