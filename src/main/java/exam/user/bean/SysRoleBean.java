package exam.user.bean;

/**
 *	用户角色信息 
 *
 */
public class SysRoleBean {
	private Integer id;
	//角色名
	private String name;
	//备注
	private String mark;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}
}
