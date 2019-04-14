package exam.examination.bean;

import java.sql.Date;
import java.sql.Timestamp;

public class ExamApplyInfoBean {
	private Integer id;
	private String code;
	private String photeUrl;
	private String name;
	private String nameSpell;
	private String identityNum;
	private String identityType;
	private String sex;
	private String country;
	private Date birthday;
	private Timestamp applyTime;
	
	private ExamInfoBean examInfo;
	private ExamPlaceBean examPlace;
	
	public String getExamIdCode() {
		if(code == null) {
			return null;
		}
		
		return code + id;
	}

	public Timestamp getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Timestamp applyTime) {
		this.applyTime = applyTime;
	}

	public ExamInfoBean getExamInfo() {
		return examInfo;
	}

	public void setExamInfo(ExamInfoBean examInfo) {
		this.examInfo = examInfo;
	}

	public ExamPlaceBean getExamPlace() {
		return examPlace;
	}

	public void setExamPlace(ExamPlaceBean examPlace) {
		this.examPlace = examPlace;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}


	public String getPhoteUrl() {
		return photeUrl;
	}

	public void setPhoteUrl(String photeUrl) {
		this.photeUrl = photeUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameSpell() {
		return nameSpell;
	}

	public void setNameSpell(String nameSpell) {
		this.nameSpell = nameSpell;
	}

	public String getIdentityNum() {
		return identityNum;
	}

	public void setIdentityNum(String identityNum) {
		this.identityNum = identityNum;
	}

	public String getIdentityType() {
		return identityType;
	}

	public void setIdentityType(String identityType) {
		this.identityType = identityType;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

}
