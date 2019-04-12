package exam.dict.mapper;

import java.util.List;

import exam.dict.bean.SysDictItemBean;

public interface SysDictItemMapper {
	public List<SysDictItemBean> findSysDictItemBeansByGroupId(String groupId);
}
