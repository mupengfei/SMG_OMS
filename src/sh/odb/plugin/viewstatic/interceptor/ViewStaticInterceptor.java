package sh.odb.plugin.viewstatic.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sh.odb.core.utils.ControllerLocal;
import sh.odb.plugin.viewstatic.service.ViewStaticService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class ViewStaticInterceptor implements HandlerInterceptor {

	private final Log log = LogFactory.getLog(getClass());

	private ViewStaticService viewStaticService;

	@Override
	public boolean preHandle(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse, Object obj) throws Exception {
		ControllerLocal.getControllerLocal().put("StaticFilePath", getViewStaticService().getFileURI("test"));
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse, Object obj, Exception exception) throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse, Object obj, ModelAndView modelandview) throws Exception {

	}

	/**
	 * @param viewStaticService
	 *            the viewStaticService to set
	 */
	public void setViewStaticService(ViewStaticService viewStaticService) {
		this.viewStaticService = viewStaticService;
	}

	/**
	 * @return the viewStaticService
	 */
	public ViewStaticService getViewStaticService() {
		return viewStaticService;
	}

}
