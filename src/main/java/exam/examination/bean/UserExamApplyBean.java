package exam.examination.bean;

/**
 * 报名信息 
 */
public class UserExamApplyBean {
	//用户id
	private Integer userId;
	//报名id
	private Integer examApplyId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getExamApplyId() {
		return examApplyId;
	}

	public void setExamApplyId(Integer examApplyId) {
		this.examApplyId = examApplyId;
	}
}
