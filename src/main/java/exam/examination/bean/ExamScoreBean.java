package exam.examination.bean;

import java.sql.Timestamp;

public class ExamScoreBean {
	private Integer examApplyId;
	private Integer score;
	private Integer operatorId;
	private Timestamp opTime;
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
