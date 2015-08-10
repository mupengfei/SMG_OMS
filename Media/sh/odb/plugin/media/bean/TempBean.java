package sh.odb.plugin.media.bean;

import java.util.Collection;
import java.util.Map;



public class TempBean extends MediaBean{
	
	public TempBean() {
		super();
	}
	
	
	private Map<String, Collection<String>> paras  ;

	public Map<String, Collection<String>> getParas() {
		return paras;
	}

	public void setParas(Map<String, Collection<String>> paras) {
		this.paras = paras;
	}
	
	
}