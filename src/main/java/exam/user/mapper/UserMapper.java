package exam.user.mapper;

import exam.user.bean.UserBean;

public interface UserMapper {
	/**
	 * 插入账户信息
	 * @param bean
	 */
	public void addAcctInfo(UserBean bean);
	
	/**
	 * 插入用户详细信息
	 * @param bean
	 */
	public void addUseDtlInfo(UserBean bean);
	
	/**
	 * 根据ID找用户信息
	 * @param id
	 * @return
	 */
	public UserBean findUserInfoByAccountName(String accountName);

	/**
	 * 更新用户详细信息
	 * @param bean
	 */
	public void updateUserDtl(UserBean bean);
	
}
