package sh.odb.proxy.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jms.producer.PtpMessageProducer;

import sh.odb.core.controller.BaseController;

@Controller
public class ProxyController extends BaseController {

	@Resource
	PtpMessageProducer ptpMessageProducer;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "/plugin/proxy.htm")
	@ResponseBody
	public Object plugin(String message) throws JsonGenerationException, JsonMappingException, IOException, SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", true);
		try {
			ptpMessageProducer.sendMessage(message);

		} catch (Exception ex) {
			result.put("result", false);
			result.put("message", ex.getMessage());
		}

		return result;
	}
}
