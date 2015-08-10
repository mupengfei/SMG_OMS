package sh.odb.plugin.globalLanguage.interceptor;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import sh.odb.core.utils.ControllerLocal;

public class LanguageInterceptor implements HandlerInterceptor {

	private List<String> languages;
	private static final String DefaultLanguage = "cn";
	private static final String LanguageTag = "language";

	private Map<String, Properties> languagemap;

	public LanguageInterceptor() {
		languagemap = new HashMap<String, Properties>();
	}

	public List<String> getLanguages() {
		return languages;
	}

	public void setLanguages(List<String> languages) {
		this.languages = languages;
	}

	public void init() {
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		try {
			for (String key : languages) {
				Resource resources[] = resolver.getResources("classpath*:language/language_*_" + key + ".properties");
				languagemap.put(key, formartProperties(resources));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		handler.toString();
		String accept_language = request.getHeader("accept-language");

		HttpSession session = request.getSession();
		String lan = request.getParameter(LanguageTag);
		if (lan != null) {
			session.setAttribute(LanguageTag, lan);
		} else if (session.getAttribute(LanguageTag) != null) {
			lan = (String) session.getAttribute(LanguageTag);
		} else if ((lan = checkWebBrows(accept_language)) != null) {

		} else {
			lan = DefaultLanguage;
		}
		ControllerLocal.getControllerLocal().put(LanguageTag, languagemap.get(lan));
		return true;
	}

	private Properties formartProperties(Resource[] resources) {
		Properties properties = new Properties();
		for (Resource resource : resources) {
			try {
				properties.load(resource.getInputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return properties;
	}

	private String checkWebBrows(String accept_language) {
		for (String language : languages) {
			if (accept_language.toLowerCase().contains(language)) {
				return language;
			}
		}
		return null;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
	}

}
