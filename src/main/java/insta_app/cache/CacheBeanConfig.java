package insta_app.cache;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import insta_app.entity.User;

@Configuration
public class CacheBeanConfig {

	@Bean
	 CacheStore<String> otpCache()
	 {
		 return new CacheStore<String>(5);
	 } 
	
	@Bean
	CacheStore<User> userCache(){
		return new CacheStore<User>(30);
	}
}

