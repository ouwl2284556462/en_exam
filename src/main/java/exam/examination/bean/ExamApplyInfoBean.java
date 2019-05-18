package exam.examination.bean;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * 报名信息 
 */
public class ExamApplyInfoBean {
	//ID
	private Integer id;
	//编码
	private String code;
	//照片url
	private String photeUrl;
	//名字
	private String name;
	//名字拼音
	private String nameSpell;
	//证件号码
	private String identityNum;
	//证件类型
	private String identityType;
	//性别
	private String sex;
	//国家
	private String country;
	//生日
	private Date birthday;
	//报名时间
	private Timestamp applyTime;
	//考试信息
	private ExamInfoBean examInfo;
	//考点信息
	private ExamPlaceBean examPlace;
	//考试分数
	private ExamScoreBean examScore;
	
	public ExamScoreBean getExamScore() {
		return examScore;
	}

	public void setExamScore(ExamScoreBean examScore) {
		this.examScore = examScore;
	}

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
