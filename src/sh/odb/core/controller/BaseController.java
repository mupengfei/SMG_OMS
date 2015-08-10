package sh.odb.core.controller;

import java.io.File;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import sh.odb.core.utils.ControllerLocal;
import sh.odb.core.utils.ResourceBundleReader;

/**
 * @author peeply
 * @version 创建时间：2012-8-2 下午03:32:26 BaseController
 *          此类写一些公用的顶层方法，所有Controller都需要继承此类
 */
public class BaseController {
	protected final Log log = LogFactory.getLog(getClass());
	protected Map<String, HttpSession> sessionMap;

	public String getRealPath(String filePath, HttpServletRequest request) {
		String uploadPath = ResourceBundleReader.getBundle().getString(filePath);
		String realPath = request.getSession().getServletContext().getRealPath(uploadPath);
		File tem = new File(realPath);
		if (!tem.exists()) {
			tem.mkdirs();
		}
		return realPath;
	}

	public Map<String, HttpSession> getSessionMap() {
		return sessionMap;
	}

	public void setSessionMap(Map<String, HttpSession> sessionMap) {
		this.sessionMap = sessionMap;
	}

	protected ModelAndView returnModelAndView(String src) {
		return returnModelAndView(src, "root", ControllerLocal.getControllerLocal());
	}

	protected ModelAndView returnModelAndView(String src, String key, Object object) {
		ModelAndView view = new ModelAndView(src, key, object);
		return view;
	}

}
