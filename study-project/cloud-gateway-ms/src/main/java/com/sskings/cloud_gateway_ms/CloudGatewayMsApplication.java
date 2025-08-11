package com.sskings.cloud_gateway_ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;

@SpringBootApplication
@EnableDiscoveryClient
public class CloudGatewayMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudGatewayMsApplication.class, args);
	}

	public RouteLocator routes(RouteLocatorBuilder builder) {
		return builder
				.routes()
					.route("client-service", r -> r.path("/clients/**")
						.uri("lb://client-ms"))
					.route("card-service", r -> r.path("/cards/**")
						.uri("lb://card-ms"))
				.build();
	}
}
