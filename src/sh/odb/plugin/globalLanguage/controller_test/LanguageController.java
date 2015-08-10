package sh.odb.plugin.globalLanguage.controller_test;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import sh.odb.core.controller.BaseController;
import sh.odb.core.utils.ControllerLocal;
import sh.odb.plugin.viewstatic.ODBFreemarkerView;
import sh.odb.plugin.viewstatic.service.ViewStaticService;

@Controller
public class LanguageController extends BaseController {
	private final Log log = LogFactory.getLog(getClass());

	@Resource
	private ViewStaticService viewStaticService;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "/sys/test/test/language.htm")
	public ModelAndView pluginList(HttpServletRequest request, String username) {
		viewStaticService.getFileURI("test");
		ModelAndView view = new ModelAndView("/globalLanguage/controller_test/index", "root", ControllerLocal.getControllerLocal());
		view.addObject(ODBFreemarkerView.STATIC_PAGE, true);
		return view;
	}

}
