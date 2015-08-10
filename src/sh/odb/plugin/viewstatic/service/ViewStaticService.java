package sh.odb.plugin.viewstatic.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cache.annotation.Cacheable;
import sh.odb.core.cache.CacheConfig;

public class ViewStaticService {

	private final Log log = LogFactory.getLog(getClass());
	private String staicPath;

	@Cacheable(value = CacheConfig.DEMO_CACHE, key = "#xPath + '_path'")
	public String getFileURI(String xPath) {
		System.out.println("getFileURI" + xPath);
		return staicPath + xPath;
	}

	/**
	 * @param staicPath
	 *            the staicPath to set
	 */
	public void setStaicPath(String staicPath) {
		this.staicPath = staicPath;
	}

	/**
	 * @return the staicPath
	 */
	public String getStaicPath() {
		return staicPath;
	}

}
