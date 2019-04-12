package exam.user.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import exam.user.bean.UserBean;

public interface UserService extends UserDetailsService{

	public String addUser(UserBean bean);
	
	public boolean isAcctNameUsed(String acctName);
	
	public UserBean findUserByAcctName(String acctName);
	
	public void updateUserDtl(UserBean bean);
}
