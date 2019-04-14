package exam.base.thymeleafdialect;

import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;

/**
 * 本项目自定义方言
 */
public class OwlDialect extends AbstractProcessorDialect{
	
	private static final String DIALECT_NAME = "owl dialect";
	private static final String PREFIX = "owl";
    public static final int PROCESSOR_PRECEDENCE = 1000;
	
	public OwlDialect() {
		super(DIALECT_NAME, PREFIX, PROCESSOR_PRECEDENCE);
	}

	@Override
	public Set<IProcessor> getProcessors(String dialectPrefix) {
		Set<IProcessor> processors = new HashSet<IProcessor>();
		processors.add(new SelectOpAttributeTagProcessor(dialectPrefix));
		processors.add(new EnumTranslateTagProcessor(dialectPrefix));
		return processors;
	}

}
