package exam.user.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.github.pagehelper.PageInfo;

import exam.user.bean.UserBean;

public interface UserService extends UserDetailsService{

	public String addUser(UserBean bean);
	
	public boolean isAcctNameUsed(String acctName);
	
	public UserBean findUserByAcctName(String acctName);
	
	public void updateUserDtl(UserBean bean);

	public String updatePasswordWithCheck(String acctName, String oldPassword, String newPassword);

	public PageInfo<UserBean> qryUserListByPage(UserBean userBean, int pageNum, int pagePerNum);

	public void deleteUser(String[] ids);

	public UserBean findUserById(int id);

	public void updatePassword(int userId, String newPassword);
}
