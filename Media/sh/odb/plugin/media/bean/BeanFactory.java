package sh.odb.plugin.media.bean;

import java.io.IOException;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class BeanFactory {

	public static MediaBean creatMediaBean(String message) throws JsonParseException, JsonMappingException, IOException {
		return fromJSON(message, MediaBean.class);
	}

	public static String creatMediaStr(MediaBean bean) throws JsonGenerationException, JsonMappingException, IOException {
		return toJSON(bean);
	}

	public static <T> T fromJSON(String json, Class<T> clazz) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(json, clazz);
	}

	public static String toJSON(Object object) throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(object);
	}

}
