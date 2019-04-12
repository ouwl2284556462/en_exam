package exam.base.thymeleafdialect;

import java.util.List;
import java.util.Properties;

import org.springframework.context.ApplicationContext;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.spring5.context.SpringContextUtils;
import org.thymeleaf.standard.expression.StandardExpressions;
import org.thymeleaf.templatemode.TemplateMode;

import exam.base.utils.CommStrUtil;
import exam.dict.bean.SysDictItemBean;
import exam.dict.service.SysDictItemService;

/**
 * 标签: owl:selectOp
 * 参数:
 * enumKey（必填）: 枚举值的键，对应sys_dict_item表的group_id
 * require（非必填）: 是否必选， 不是必选时，有“请选择”的选项,默认为false
 * value（非必填）: 选中值 
 * 用法：  
 *1.必选:<select id="identityType"  name="identityType" owl:selectOp="'enumKey=identity_type,require=true'">
 *2.非必选:<select id="identityType"  name="identityType" owl:selectOp="'enumKey=identity_type'">
 *3.带选中值:<select  id="identityType"  name="identityType" owl:selectOp="'enumKey=identity_type,require=true,value=1'" >
 *  动态拼接选中值:<select  id="identityType"  name="identityType" owl:selectOp="'enumKey=identity_type,require=true,value=' + ${userBean.identityType}" >
 *  
 *     

 */
public class SelectOpAttributeTagProcessor extends AbstractAttributeTagProcessor {
	
    private static final String ATTR_NAME = "selectOp";
    private static final int PRECEDENCE = 10000;
    
    //枚举值键
    private static final String PARAM_ENUM_KEY = "enumKey";
    //选中值
    private static final String PARAM_VALUE = "value";
    //是否必填
    private static final String PARAM_REQUIRE = "require";
    //是否必填-必填
    private static final String PARAM_VAL_REQUIRE_YES = "true";    
    

	protected SelectOpAttributeTagProcessor(String dialectPrefix) {
        super(
                TemplateMode.HTML, // This processor will apply only to HTML mode
                dialectPrefix,     // Prefix to be applied to name for matching
                null,              // No tag name: match any tag name
                false,             // No prefix to be applied to tag name
                ATTR_NAME,         // Name of the attribute that will be matched
                true,              // Apply dialect prefix to attribute name
                PRECEDENCE,        // Precedence (inside dialect's precedence)
                true);             // Remove the matched attribute afterwards
	}

	@Override
	protected void doProcess(ITemplateContext context, IProcessableElementTag tag, AttributeName attributeName,
			String attributeValue, IElementTagStructureHandler structureHandler) {
		String paramStr = (String) StandardExpressions.getExpressionParser(context.getConfiguration()).parseExpression(context, attributeValue).execute(context);
		Properties cfg = CommStrUtil.parseSimpleStrCfg(paramStr);
		
		String enumKey = cfg.getProperty(PARAM_ENUM_KEY);
		if(StringUtils.isEmpty(enumKey)) {
			return;
		}
		
		
		StringBuilder html = new StringBuilder();
		String require = cfg.getProperty(PARAM_REQUIRE);
		if(!PARAM_VAL_REQUIRE_YES.equals(require)) {
			html.append("<option value=\"\">").append("请选择").append("</option>");
		}
		
		ApplicationContext appCtx = SpringContextUtils.getApplicationContext(context);
		SysDictItemService sysDictItemService = appCtx.getBean(SysDictItemService.class);
		
		List<SysDictItemBean> sysDictItemList = sysDictItemService.findSysDictItemBeansByGroupId(enumKey);
		if(CollectionUtils.isEmpty(sysDictItemList)) {
			return;
		}
		
		String selectedVal = cfg.getProperty(PARAM_VALUE);
		if("null".equals(selectedVal) || "".equals(selectedVal)) {
			selectedVal = null;
		}
		
		for(SysDictItemBean item : sysDictItemList) {
			String itemId = item.getItemId();
			html.append("<option value=\"").append(item.getItemId()).append("\"");
			
			if(selectedVal != null && selectedVal.equals(itemId)) {
				html.append(" selected = \"selected\" ");
			}
			
			html.append(" >")
			    .append(item.getItemName())
			    .append("</option>");
		}
		
		structureHandler.setBody(html.toString(), false);
	}

}
