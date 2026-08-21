package com.johanwork.job.config;

import com.johanwork.job.util.JwtUtil;
import org.springframework.cloud.gateway.server.mvc.filter.LoadBalancerFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class RouteConfig {

    private final JwtUtil jwtUtil;

    public RouteConfig(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public RouterFunction<ServerResponse> authRoutes(){
        return GatewayRouterFunctions.route("auth-route")
                .route(RequestPredicates.path("/api/auth/**"), HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("job-portal-user-service"))
                .build();
    }

    /*  ======================= Admin-Only Routes (JWT + ROLE_ADMIN) ======================= */
    @Bean
    public RouterFunction<ServerResponse> adminRoutes(){
        return GatewayRouterFunctions.route("admin-routes")
                .route(RequestPredicates.path("/api/admin/**"), HandlerFunctions.http())
                .before(this::jwtAuthFilter)
                .before(req -> requiredRoles(req, "ROLE_ADMIN"))
                .filter(LoadBalancerFilterFunctions.lb("job-portal-user-service"))
                .build();
    }

    private ServerRequest requiredRoles(ServerRequest req, String roleAdmin){
        String roles = req.headers().firstHeader("X-User-Role");
        if (roles==null || !roles.contains(roleAdmin)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied for role " + roleAdmin);
        }
        return req;
    }

    /*  ======================= Protected Routes (JWT Required) ======================= */

    @Bean
    public RouterFunction<ServerResponse> userServiceRoutes(){
        return GatewayRouterFunctions.route("user-service-routes")
                .route(RequestPredicates.path("/api/users/**"), HandlerFunctions.http())
                .before(this::jwtAuthFilter)
                .filter(LoadBalancerFilterFunctions.lb("job-portal-user-service"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> companyServiceRoutes(){
        return GatewayRouterFunctions.route("company-service-routes")
                .route(RequestPredicates.path("/api/companies/**"), HandlerFunctions.http())
                .before(this::jwtAuthFilter)
                .filter(LoadBalancerFilterFunctions.lb("job-portal-company-service"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> jobServiceRoutes(){
        return GatewayRouterFunctions.route("job-service-routes")
                .route(RequestPredicates.path("/api/jobs/**")
                        .or(RequestPredicates.path("/api/job-categories/**"))
                        .or(RequestPredicates.path("/api/job-skills/**"))
                        .or(RequestPredicates.path("/api/job-tags/**")), HandlerFunctions.http())
                .before(this::jwtAuthFilter)
                .filter(LoadBalancerFilterFunctions.lb("job-portal-job-service"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> applicationServiceRoutes(){
        return GatewayRouterFunctions.route("application-service-routes")
                .route(RequestPredicates.path("/api/applications/**"), HandlerFunctions.http())
                .before(this::jwtAuthFilter)
                .filter(LoadBalancerFilterFunctions.lb("job-portal-application-service"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> resumeServiceRoutes(){
        return GatewayRouterFunctions.route("resume-service-routes")
                .route(RequestPredicates.path("/api/resumes/**"), HandlerFunctions.http())
                .before(this::jwtAuthFilter)
                .filter(LoadBalancerFilterFunctions.lb("job-portal-resume-service"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> preferenceServiceRoutes(){
        return GatewayRouterFunctions.route("preferences-service-routes")
                .route(RequestPredicates.path("/api/preferences/**"), HandlerFunctions.http())
                .before(this::jwtAuthFilter)
                .filter(LoadBalancerFilterFunctions.lb("job-portal-preferences-service"))
                .build();
    }

    /*  ======================= JWT Filter for TOKEN ======================= */
    private ServerRequest jwtAuthFilter(ServerRequest req){
        String autHeader = req.headers().firstHeader(JwtUtil.JWT_HEADER);
        if (null == autHeader || !autHeader.startsWith(JwtUtil.TOKEN_PREFIX)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing or invalid authorization header");
        }

        String token = autHeader.substring(JwtUtil.TOKEN_PREFIX.length()+1);
        if(!jwtUtil.isTokenValid(token)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid or expired JWT Token");
        }

        String email = jwtUtil.extractEmail(token);
        String authorities = jwtUtil.extractAuthorities(token);
        String userId = jwtUtil.extractUserId(token);

        return ServerRequest.from(req)
                .header("X-User-Id", String.valueOf(userId))
                .header("X-User-Email", String.valueOf(email))
                .header("X-User-Authorities", String.valueOf(authorities))
                .build();
    }

}
