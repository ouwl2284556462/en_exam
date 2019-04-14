package exam.examination.service;

import java.util.List;

import exam.dict.bean.SysDictItemBean;
import exam.examination.bean.ExamApplyInfoBean;
import exam.examination.bean.ExamInfoBean;
import exam.examination.bean.ExamPlaceBean;

public interface ExamService {
	public List<ExamInfoBean> findCurExamInfos();
	
	public List<SysDictItemBean> getCurExamEnumList();

	public List<SysDictItemBean> getExamPlaceEnumList(String regionId);
	
	public List<ExamPlaceBean> findExamPlaceByRegionId(String regionId);

	public String saveExamApplyInfo(ExamApplyInfoBean examApplyInfo, String acctName);

	public ExamInfoBean getExamInfoById(Integer id);

	public ExamPlaceBean getExamPlaceById(Integer id);

	public ExamApplyInfoBean findUserExamApplyByAcctName(String name);
}
