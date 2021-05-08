package com.codemayur.microservices.currencyexchangeservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
public class CircuitBreakerController {

	private Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);

	@GetMapping("/test-api")
	
	// @Retry(name = "default")
	
	// @Retry(name = "test-api", fallbackMethod = "testApiFallback")
	
	// @CircuitBreaker(name = "default", fallbackMethod = "testApiFallback")
	
	@RateLimiter(name = "default")
	// 10s -> 10000calls
	
	@Bulkhead(name = "default")
	// how many concurrent calls are allowed
	public String testApi() {
//		logger.info("Test Api call received!!!");
//		int i = 777 / 0;
		return "Test API";
	}

	// This method is used as a fallback for method: "testApi"
	@SuppressWarnings("unused")
	private String testApiFallback(Exception exception) {
		return "Test Api Fallback!!!";
	}

}
