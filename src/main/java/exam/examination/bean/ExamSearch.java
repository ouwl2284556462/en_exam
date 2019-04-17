package exam.examination.bean;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class ExamSearch {
	
	private Integer id;

	// 准考证号码
	private String code;

	private Integer examId;

	private String identityType;

	private String identityNum;

	private String name;

	private String examRegion;

	@DateTimeFormat(pattern = "yyyy-MM-dd") 
	private Date applyStartDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd") 
	private Date applyEndDate;

	public String getCode() {
		return code;
	}
	

	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public void setCode(String code) {
		this.code = code;
	}

	public Integer getExamId() {
		return examId;
	}

	public void setExamId(Integer examId) {
		this.examId = examId;
	}

	public String getIdentityType() {
		return identityType;
	}

	public void setIdentityType(String identityType) {
		this.identityType = identityType;
	}

	public String getIdentityNum() {
		return identityNum;
	}

	public void setIdentityNum(String identityNum) {
		this.identityNum = identityNum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExamRegion() {
		return examRegion;
	}

	public void setExamRegion(String examRegion) {
		this.examRegion = examRegion;
	}

	public Date getApplyStartDate() {
		return applyStartDate;
	}

	public void setApplyStartDate(Date applyStartDate) {
		this.applyStartDate = applyStartDate;
	}

	public Date getApplyEndDate() {
		return applyEndDate;
	}

	public void setApplyEndDate(Date applyEndDate) {
		this.applyEndDate = applyEndDate;
		
		if(null != this.applyEndDate) {
			this.applyEndDate.setHours(23);
			this.applyEndDate.setMinutes(59);
			this.applyEndDate.setSeconds(59);	
		}
	}



}
