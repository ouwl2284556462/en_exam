package exam.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import exam.user.bean.SysRoleBean;
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

	public void updateUserPassword(UserBean userBean);

	public void insertSysUserRole(@Param("roleId") Integer roleId, @Param("userId")  Integer userId);

	public List<UserBean> qryUserList(UserBean userBean);

	public void deleteAccount(String[] ids);

	public UserBean findUserById(int id);

	public void updateUserPasswordById(@Param("userId") int userId, @Param("newPassword")  String newPassword);

	
}
