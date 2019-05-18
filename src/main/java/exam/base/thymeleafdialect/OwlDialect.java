package exam.base.thymeleafdialect;

import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;

/**
 * 本项目自定义方言
 */
public class OwlDialect extends AbstractProcessorDialect{
	
	//方言名
	private static final String DIALECT_NAME = "owl dialect";
	//标签前缀
	private static final String PREFIX = "owl";
	//优先级
    public static final int PROCESSOR_PRECEDENCE = 1000;
	
	public OwlDialect() {
		super(DIALECT_NAME, PREFIX, PROCESSOR_PRECEDENCE);
	}

	@Override
	/**
	 * 设置处理器
	 */
	public Set<IProcessor> getProcessors(String dialectPrefix) {
		Set<IProcessor> processors = new HashSet<IProcessor>();
		//下拉选标签
		processors.add(new SelectOpAttributeTagProcessor(dialectPrefix));
		//枚举翻译标签
		processors.add(new EnumTranslateTagProcessor(dialectPrefix));
		return processors;
	}

}
