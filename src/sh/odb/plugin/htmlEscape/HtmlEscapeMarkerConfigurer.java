package sh.odb.plugin.htmlEscape;

import freemarker.cache.TemplateLoader;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import java.util.List;

/**
 * HtmlEscapeMarkerConfigurer 使用该Configurer，让所有模版都自动套用<#escape x as x?html>
 * 防止XSS
 * 
 * @author peeply
 * 
 */
public class HtmlEscapeMarkerConfigurer extends FreeMarkerConfigurer {

	@Override
	protected TemplateLoader getAggregateTemplateLoader(List<TemplateLoader> templateLoaders) {
		return new HtmlTemplateLoader(super.getAggregateTemplateLoader(templateLoaders));
	}
}