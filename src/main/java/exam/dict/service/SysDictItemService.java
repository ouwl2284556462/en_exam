package exam.dict.service;

import java.util.List;

import exam.dict.bean.SysDictItemBean;

public interface SysDictItemService {
	
	public List<SysDictItemBean> findSysDictItemBeansByGroupId(String groupId);

	public SysDictItemBean getDictItem(String groupId, String itemId);
}
