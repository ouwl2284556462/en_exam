package exam.user.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.github.pagehelper.PageInfo;

import exam.user.bean.UserBean;

/**
 * 用户服务
 *
 */
public interface UserService extends UserDetailsService{
	//添加用户
	public String addUser(UserBean bean);
	//帐号是否已使用
	public boolean isAcctNameUsed(String acctName);
	//用户帐号名查找用户信息
	public UserBean findUserByAcctName(String acctName);
	//更新用户详细信息
	public void updateUserDtl(UserBean bean);
	//更新密码
	public String updatePasswordWithCheck(String acctName, String oldPassword, String newPassword);
	//查询用户列表
	public PageInfo<UserBean> qryUserListByPage(UserBean userBean, int pageNum, int pagePerNum);
	//删除用户
	public void deleteUser(String[] ids);
	//根据id查找用户
	public UserBean findUserById(int id);
	//更新密码
	public void updatePassword(int userId, String newPassword);
}
