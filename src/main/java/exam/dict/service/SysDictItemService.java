package exam.dict.service;

import java.util.List;

import exam.dict.bean.SysDictItemBean;

/**
 * 字段相关服务 
 */
public interface SysDictItemService {
	//根据groupId查询
	public List<SysDictItemBean> findSysDictItemBeansByGroupId(String groupId);

	//根据groupId和itemId查询
	public SysDictItemBean getDictItem(String groupId, String itemId);
}
