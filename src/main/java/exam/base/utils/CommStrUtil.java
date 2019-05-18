package exam.base.utils;

import java.util.Properties;

import org.springframework.util.StringUtils;

/**
 * 公共工具类
 *
 */
public class CommStrUtil {
	
	/**
	 * 将字符串的配置转成Map
	 * @param cfgStr 格式   a=1,b=2,c=hhh
	 * @return
	 */
	public static Properties parseSimpleStrCfg(String cfgStr){
		String[] cfgList = StringUtils.commaDelimitedListToStringArray(cfgStr);
		return StringUtils.splitArrayElementsIntoProperties(cfgList, "=");
	}
}
