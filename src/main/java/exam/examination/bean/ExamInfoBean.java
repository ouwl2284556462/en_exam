package exam.examination.bean;

import java.sql.Timestamp;

public class ExamInfoBean {
	private Integer id;
	private String examName;
	private String examLevel;
	private Timestamp applyStartTime;
	private Timestamp applyEndTime;
	private Timestamp examTime;
	private Timestamp createTime;
	private String state;
	private String mark;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getExamName() {
		return examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}

	public String getExamLevel() {
		return examLevel;
	}

	public void setExamLevel(String examLevel) {
		this.examLevel = examLevel;
	}

	public Timestamp getApplyStartTime() {
		return applyStartTime;
	}

	public void setApplyStartTime(Timestamp applyStartTime) {
		this.applyStartTime = applyStartTime;
	}

	public Timestamp getApplyEndTime() {
		return applyEndTime;
	}

	public void setApplyEndTime(Timestamp applyEndTime) {
		this.applyEndTime = applyEndTime;
	}

	public Timestamp getExamTime() {
		return examTime;
	}

	public void setExamTime(Timestamp examTime) {
		this.examTime = examTime;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

}
