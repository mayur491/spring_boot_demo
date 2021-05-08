package com.codemayur.microservices.apigateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {

	// http://localhost:8765/get

	@Bean
	public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {

		return builder.routes()

				// http://localhost:8765/get rout this to URI mentioned below
				.route(p -> p.path("/get")
						.filters(f -> f.addRequestHeader("MyHeader",
								"MyURI")
								.addRequestParameter("MyParameter",
										"MyParam"))
						.uri("http://httpbin.org:80"))

				// currency-exchange service
				.route(p -> p.path("/currency-exchange/**")
						.uri("lb://currency-exchange"))

				// currency-conversion-service
				.route(p -> p.path("/currency-conversion/**")
						.uri("lb://currency-conversion"))

				// different URL in currency-conversion service
				.route(p -> p.path("/currency-conversion-feign/**")
						.uri("lb://currency-conversion"))

				// URL rewrite to existing URL in currency-conversion service
				.route(p -> p.path("/currency-conversion-new/**")
						.filters(f -> f.rewritePath("/currency-conversion-new/(?<segment>.*)",
								"/currency-conversion-feign/${segment}"))
						.uri("lb://currency-conversion"))

				.build();
	}

}
