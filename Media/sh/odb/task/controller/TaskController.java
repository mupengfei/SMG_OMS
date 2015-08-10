package sh.odb.task.controller;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jms.FutureManager;
import com.jms.SMGMessageListenerContainer;

import sh.odb.core.controller.BaseController;

@Controller
public class TaskController extends BaseController {

	@Resource
	private FutureManager futureManager;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "/plugin/taskJson.htm")
	@ResponseBody
	public Object taskJson() {
		return futureManager.getList();
	}

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "/plugin/taskClose.htm")
	@ResponseBody
	public Object taskClose(int hashCode) {
		futureManager.close(hashCode);
		return taskJson();
	}

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "/index.htm")
	public ModelAndView pluginList(HttpServletRequest request, String username) {
		ModelAndView view = new ModelAndView("/taskList");
		return view;
	}

}
