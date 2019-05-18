package exam.base.thymeleafdialect;

import org.springframework.context.ApplicationContext;
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
 * 翻译字典表的值
 *<td owl:enumVal="${examApplyInfo.examPlace.region}" owl:enumKey="'exam_place_region'">
 */
public class EnumTranslateTagProcessor extends AbstractAttributeTagProcessor {
	
	//标签属性
    private static final String ATTR_NAME = "enumVal";
    
    //优先级
    private static final int PRECEDENCE = 90000;
    
    //枚举值键
    private static final String PARAM_ENUM_KEY = "enumKey";
    
    //标签前缀
    private String dialectPrefix;
	
	protected EnumTranslateTagProcessor(String dialectPrefix) {
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
	/**
	 * 处理
	 */
	protected void doProcess(ITemplateContext context, IProcessableElementTag tag, AttributeName attributeName,
			String attributeValue, IElementTagStructureHandler structureHandler) {
		//获取解析器
		IStandardExpressionParser parser = StandardExpressions.getExpressionParser(context.getConfiguration());
		//获取枚举值Key
		String enumKey = parserString(context, parser, tag.getAttributeValue(dialectPrefix, PARAM_ENUM_KEY));
		//获取枚举值Val
		String enumVal = parserString(context, parser, attributeValue);
		
		ApplicationContext appCtx = SpringContextUtils.getApplicationContext(context);
		SysDictItemService sysDictItemService = appCtx.getBean(SysDictItemService.class);
		
		//获取字典表中的name
		SysDictItemBean item = sysDictItemService.getDictItem(enumKey, enumVal);
		if(item == null) {
			return;
		}
		
		//设置生成的内容
		structureHandler.setBody(item.getItemName(), false);
	}
	
	/**
	 * 解析字符串
	 * @param context
	 * @param parser
	 * @param input
	 * @return
	 */
	private String parserString(ITemplateContext context, IStandardExpressionParser parser, String input) {
		if(StringUtils.isEmpty(input)) {
			return null;
		}
		
		//根据表达式解析
		Object value = parser.parseExpression(context, input).execute(context);
		if(value == null) {
			return null;
		}
		
		//转成字符串
		return String.valueOf(value);		
	}

}
