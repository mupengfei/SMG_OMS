package sh.odb.plugin.globalLanguage.controller_test;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import sh.odb.core.cache.CacheConfig;

public class DemoService {

	@Cacheable(value = CacheConfig.DEMO_CACHE, key = "#userId + 'DemoService.printUserId'")
	public void printUserId(String userId) {
		System.out.println("save cache |" + userId + "| DemoService.printUserId");
	}

	@Cacheable(value = CacheConfig.DEMO_CACHE, key = "#userId + 'DemoService.printUserId2'")
	public void printUserId2(String userId) {
		System.out.println("save cache |" + userId + "| DemoService.printUserId2");
	}

	@CacheEvict(value = CacheConfig.DEMO_CACHE, key = "#userId + 'DemoService.printUserId'")
	public void flushUserId(String userId) {
		System.out.println("flush cache |" + userId + "| DemoService.printUserId");
	}

	@CacheEvict(value = CacheConfig.DEMO_CACHE, key = "#userId + 'DemoService.printUserId2'")
	public void flushUserId2(String userId) {
		System.out.println("flush cache |" + userId + "| DemoService.printUserId2");
	}

	@CacheEvict(value = CacheConfig.DEMO_CACHE, allEntries = true)
	public void flushAll() {
		System.out.println("flush cache all");
	}

}