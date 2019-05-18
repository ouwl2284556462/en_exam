package exam.dict.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import exam.dict.bean.SysDictItemBean;

/**
 * 字典Mapper
 *
 */
public interface SysDictItemMapper {
	//根据groupId查询
	public List<SysDictItemBean> findSysDictItemBeansByGroupId(String groupId);

	//根据groupId和itemId查询
	public SysDictItemBean getDictItem(@Param("groupId") String groupId, @Param("itemId") String itemId);
}
