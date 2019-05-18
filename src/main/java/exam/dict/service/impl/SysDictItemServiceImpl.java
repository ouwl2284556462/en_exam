package exam.dict.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import exam.dict.bean.SysDictItemBean;
import exam.dict.mapper.SysDictItemMapper;
import exam.dict.service.SysDictItemService;

@Service
/**
 * 字段相关服务
 */
public class SysDictItemServiceImpl implements SysDictItemService{

	@Autowired
	private SysDictItemMapper sysDictItemMapper;
	
	@Override
	//根据groupId查询
	public List<SysDictItemBean> findSysDictItemBeansByGroupId(String groupId) {
		return sysDictItemMapper.findSysDictItemBeansByGroupId(groupId);
	}

	@Override
	//根据groupId和itemId查询
	public SysDictItemBean getDictItem(String groupId, String itemId) {
		return sysDictItemMapper.getDictItem(groupId, itemId);
	}

}
