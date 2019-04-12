package exam.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import exam.user.bean.UserBean;
import exam.user.mapper.UserMapper;
import exam.user.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserMapper userMapper;	
	
	@Autowired
	private PasswordEncoder passwrodEncoder;

	@Transactional
	@Override
	public String addUser(UserBean bean) {
		if(isAcctNameUsed(bean.getAccountName())) {
			return "该电子邮箱已经注册过";
		}
		
		//密码加密
		bean.setPassword(passwrodEncoder.encode(bean.getPassword()));
		//插入账户信息
		userMapper.addAcctInfo(bean);
		//插入用户信息
		userMapper.addUseDtlInfo(bean);
		return null;
	}

	@Override
	public boolean isAcctNameUsed(String acctName) {
		return null != userMapper.findUserInfoByAccountName(acctName);
	}
	
	@Override
	public UserBean findUserByAcctName(String acctName) {
		return userMapper.findUserInfoByAccountName(acctName);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserBean userBean = findUserByAcctName(username);
        if (null == userBean) {
            throw new UsernameNotFoundException(username);
        }
        
        
        List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
//        
//        for (SysRole role : sysUser.getRoleList()) {
//            for (SysPermission permission : role.getPermissionList()) {
//                authorities.add(new SimpleGrantedAuthority(permission.getCode()));
//            }
//        }
        return new User(userBean.getAccountName(), userBean.getPassword(), authorities);
	}

	@Transactional
	@Override
	public void updateUserDtl(UserBean bean) {
		userMapper.updateUserDtl(bean);
	}


}
