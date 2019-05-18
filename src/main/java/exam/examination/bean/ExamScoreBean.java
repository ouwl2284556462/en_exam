package exam.examination.bean;

import java.sql.Timestamp;

/**
 * 考试分数
 */
public class ExamScoreBean {
	//报名ID
	private Integer examApplyId;
	//分数
	private Integer score;
	//录入成绩操作员id
	private Integer operatorId;
	//录入时间
	private Timestamp opTime;
	//备注
	private String mark;

	public Integer getExamApplyId() {
		return examApplyId;
	}

	public void setExamApplyId(Integer examApplyId) {
		this.examApplyId = examApplyId;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Integer getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}

	public Timestamp getOpTime() {
		return opTime;
	}

	public void setOpTime(Timestamp opTime) {
		this.opTime = opTime;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}


}
