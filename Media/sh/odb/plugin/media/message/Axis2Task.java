package sh.odb.plugin.media.message;

import javax.xml.namespace.QName;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.Constants;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;

import sh.odb.plugin.media.bean.BeanFactory;
import sh.odb.plugin.media.bean.MediaBean;

public class Axis2Task extends AbstractTask {

	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	protected String execut() throws Exception {
		// TODO Auto-generated method stub
		Options options = new Options();
		options.setTo(new EndpointReference("http://218.242.251.203:80/services/ShJaWebservice"));

		// 客户端REST方式调用服务跟普通服务的区别，REST调用必须加上下面这个代码。
		options.setProperty(Constants.Configuration.ENABLE_REST,
				Constants.VALUE_TRUE);
		options.setTimeOutInMilliSeconds(60000L);
		ServiceClient sender = new ServiceClient();
		// axis2-1.5.4不需要下面这句代码，否则会报错
		// sender.engageModule(new QName(Constants.MODULE_ADDRESSING));
		sender.setOptions(options);
		OMElement result = sender.sendReceive(getPayload(
				super.getMeta("ParasXml"), super.getMeta("methodName")));
		// try {
		// XMLStreamWriter writer = XMLOutputFactory.newInstance()
		// .createXMLStreamWriter(System.out);
		// result.serialize(writer);
		// ;
		String resultStr = result.toString();
		System.out.println(resultStr);
		return resultStr.charAt(resultStr.indexOf("<ns:return>")
				+ "<ns:return>".length())
				+ "";
		// writer.flush();
		// } catch (XMLStreamException e) {
		// e.printStackTrace();
		// } catch (FactoryConfigurationError e) {
		// e.printStackTrace();
		// }
	}

	@Override
	public void done() throws Exception {
		// TODO Auto-generated method stub

	}

	public void dsssone() {

	}

	private static OMElement getPayload(String xml, String methodName) {

		OMFactory fac = OMAbstractFactory.getOMFactory();
		OMNamespace omNs = fac.createOMNamespace("http://server.mobile.axis2",
				"");
		OMElement method = fac.createOMElement("AddSPXWVideo", omNs);
		OMElement value = fac.createOMElement("ParasXml", omNs);

		value.addChild(fac.createOMText(value, xml));
		method.addChild(value);

		return method;
	}

	public static String testXml = "<?xml version='1.0' encoding='UTF-8'?>"
			+ "<videolist>"
			+ "<video>"
			+ "	<title>"
			+ "		<![CDATA[test]]>"
			+ "	</title>"
			+ "	<infodate>"
			+ "		<![CDATA[2015-08-07]]>"
			+ "	</infodate>"
			+ "	<ImageUrl>"
			+ "		<![CDATA[http://static.statickksmg.com/image/2015/08/07/2e4c190e0459388b95d3d4a1ba854fd6.jpg]]>"
			+ "	</ImageUrl>"
			+ "	<VideoUrl>"
			+ "		<![CDATA[http://domhttp.kksmg.com/2015/08/07/h264_450k_mp4_2ceda1cf200b1e1ad038f7765d6d989e_ncd.mp4]]>"
			+ "	</VideoUrl>" + "</video>" + "</videolist>";

	public static void main(String[] args) throws Exception {
		Axis2Task task = new Axis2Task();
		MediaBean bean = new MediaBean();
		bean.pushMeta("ParasXml", testXml);
		bean.pushMeta("url", "http://218.242.251.203/services/ShJaWebservice");
		bean.pushMeta("methodName", "AddSPXWVideo");
		task.setBean(bean);
		task.execut(); 
	}
}
