package com.naveenj;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@SpringBootApplication
@EnableDiscoveryClient
public class ApigatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApigatewayApplication.class, args);
    }

    @Bean
    public RouteLocator routerBuilder(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route("Microservice1", r -> r.path("/management/**").and().method("GET", "POST").uri("http://localhost:8083/"))
                .route("Microservice2", r -> r.path("/patient/**").filters(f -> f.filter(authenticationFilter("Patient authentication required", "/management/")))
                        .uri("http://localhost:9091/"))
                .route("Microservice3", r -> r.path("/doctor/**").filters(f -> f.filter(authenticationFilter("Doctor authentication required", "/management/")))
                        .uri("http://localhost:8085/"))
                .route("Microservice4", r -> r.path("/disease/**").filters(f -> f.filter(authenticationFilter("Doctor authentication required", "/management/")))
                        .uri("http://localhost:8082/"))
                .route("Microservice5", r -> r.path("/user/**").filters(f -> f.filter(authenticationFilter("Doctor authentication required", "/management/")))
                        .uri("http://localhost:8080/"))
                .route("Microservice6", r -> r.path("/doctors/**").filters(f -> f.filter(authenticationFilter("Doctor authentication required", "/management/")))
                        .uri("http://localhost:8086/"))
                .route("Microservice7", r -> r.path("/doctor/register/**").filters(f -> f.filter(authenticationFilter("Authentication required", "/doctor/register/")))
                	    .uri("http://localhost:8888/"))

                .build();
    }

    private GatewayFilter authenticationFilter(String errorMessage, String redirectUrl) {
        return (exchange, chain) -> {
            // Check if the previous URL was "/disease/"
            String previousPath = exchange.getRequest().getHeaders().getFirst("Referer");
            if (previousPath != null &&
            	    (previousPath.contains("/disease/") ||
            	     previousPath.contains("/management/") ||
            	     previousPath.contains("/doctor/") ||
            	     previousPath.contains("/doctors/") ||
            	     previousPath.contains("/user/") ||
            	     previousPath.contains("/patient/")) 
            	    ) {
                // If the previous URL matches any of the specified paths, allow access without authentication
                return chain.filter(exchange);
            } else {
                // If not, check for authentication
                if (!isAuthenticated(exchange)) {
                    // If not authenticated, redirect to the specified URL
                    exchange.getResponse().setStatusCode(HttpStatus.SEE_OTHER);
                    exchange.getResponse().getHeaders().set("Location", redirectUrl);
                    return Mono.empty();
                }
                // If authenticated, proceed with the request
                return chain.filter(exchange);
            }

        };
    }
    
    private boolean isAuthenticated(ServerWebExchange exchange) {
        // Implement your authentication logic here
        // For example, you might check for a session token in the request
        // Return true if authenticated, false otherwise
        return false; // Placeholder logic, replace with your actual authentication logic
    }
}
