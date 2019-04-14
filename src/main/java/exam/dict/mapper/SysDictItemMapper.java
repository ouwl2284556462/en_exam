package exam.dict.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import exam.dict.bean.SysDictItemBean;

public interface SysDictItemMapper {
	public List<SysDictItemBean> findSysDictItemBeansByGroupId(String groupId);

	public SysDictItemBean getDictItem(@Param("groupId") String groupId, @Param("itemId") String itemId);
}
