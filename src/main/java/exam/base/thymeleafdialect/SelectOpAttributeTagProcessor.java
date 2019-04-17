package exam.base.thymeleafdialect;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.spring5.context.SpringContextUtils;
import org.thymeleaf.standard.expression.IStandardExpressionParser;
import org.thymeleaf.standard.expression.StandardExpressions;
import org.thymeleaf.templatemode.TemplateMode;

import exam.dict.bean.SysDictItemBean;
import exam.dict.service.SysDictItemService;

/**
 * 标签: owl:selectOp   用在<select>标签上
 * 参数:
 * enumKey（enumList为空时，必填，两者必须填一个）: 枚举值的键，对应sys_dict_item表的group_id
 * require（非必填）: 是否必选， 不是必选时，有“请选择”的选项,默认为false
 * defaultVal（非必填）: 默认值 
 * 
 * enumList(enumKey为空时，必填，两者必须填一个)：直接传枚举值列表进来
 * 用法：  
 *1.必选:<select owl:selectOp owl:enumKey="'identity_type'" owl:require="'true'">
 *2.非必选:<select owl:selectOp owl:enumKey="'identity_type'">
 *3.带选中值:<select owl:selectOp owl:enumKey="'identity_type'" owl:require="'true'" owl:defaultVal="'1'" >
 *        动态拼接选中值: <select owl:selectOp owl:enumKey="'identity_type'" owl:require="'true'" owl:defaultVal="${userBean.identityType}" >
 *  
 *     

 */
public class SelectOpAttributeTagProcessor extends AbstractAttributeTagProcessor {
	
    private static final String ATTR_NAME = "selectOp";
    private static final int PRECEDENCE = 10000;
    
    //枚举值列表
    private static final String PARAM_ENUM_LIST = "enumList";
    //枚举值键
    private static final String PARAM_ENUM_KEY = "enumKey";
    //默认值
    private static final String PARAM_VALUE = "defaultVal";
    //是否必填
    private static final String PARAM_REQUIRE = "require";
    //是否必填-必填
    private static final String PARAM_VAL_REQUIRE_YES = "true";   
    
    private String dialectPrefix;
    

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
        
        this.dialectPrefix = dialectPrefix; 
	}

	@Override
	protected void doProcess(ITemplateContext context, IProcessableElementTag tag, AttributeName attributeName,
			String attributeValue, IElementTagStructureHandler structureHandler) {
		IStandardExpressionParser parser = StandardExpressions.getExpressionParser(context.getConfiguration());
		StringBuilder html = new StringBuilder();
		
		String require = parserString(context, parser, tag.getAttributeValue(dialectPrefix, PARAM_REQUIRE));
		if(!PARAM_VAL_REQUIRE_YES.equals(require)) {
			html.append("<option value=\"\">").append("请选择").append("</option>");
		}
		
		List<SysDictItemBean> sysDictItemList = null;
		String enumKey = parserString(context, parser, tag.getAttributeValue(dialectPrefix, PARAM_ENUM_KEY));
		if(StringUtils.isEmpty(enumKey)) {
			try {
				sysDictItemList = (List<SysDictItemBean>) parser.parseExpression(context, tag.getAttributeValue(dialectPrefix, PARAM_ENUM_LIST)).execute(context);
			}catch (Exception e) {
			}
		}else {
			ApplicationContext appCtx = SpringContextUtils.getApplicationContext(context);
			SysDictItemService sysDictItemService = appCtx.getBean(SysDictItemService.class);
			sysDictItemList = sysDictItemService.findSysDictItemBeansByGroupId(enumKey);
		}
		
		if(CollectionUtils.isEmpty(sysDictItemList)) {
			return;
		}
		
		String defaultVal = parserString(context, parser, tag.getAttributeValue(dialectPrefix, PARAM_VALUE));
		if("null".equals(defaultVal) || "".equals(defaultVal)) {
			defaultVal = null;
		}
		
		for(SysDictItemBean item : sysDictItemList) {
			String itemId = item.getItemId();
			html.append("<option value=\"").append(item.getItemId()).append("\"");
			
			if(defaultVal != null && defaultVal.equals(itemId)) {
				html.append(" selected = \"selected\" ");
			}
			
			html.append(" >")
			    .append(item.getItemName())
			    .append("</option>");
		}
		
		structureHandler.setBody(html.toString(), false);
	}

	private String parserString(ITemplateContext context, IStandardExpressionParser parser, String input) {
		if(StringUtils.isEmpty(input)) {
			return null;
		}
		
		Object value = parser.parseExpression(context, input).execute(context);
		if(value == null) {
			return null;
		}
		
		return String.valueOf(value);
	}

}
