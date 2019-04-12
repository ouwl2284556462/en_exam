package exam.dict.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import exam.dict.bean.SysDictItemBean;
import exam.dict.mapper.SysDictItemMapper;
import exam.dict.service.SysDictItemService;

@Service
public class SysDictItemServiceImpl implements SysDictItemService{

	@Autowired
	private SysDictItemMapper sysDictItemMapper;
	
	@Override
	public List<SysDictItemBean> findSysDictItemBeansByGroupId(String groupId) {
		return sysDictItemMapper.findSysDictItemBeansByGroupId(groupId);
	}

}
