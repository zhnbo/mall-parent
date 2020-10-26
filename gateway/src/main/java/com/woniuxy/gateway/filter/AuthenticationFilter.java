package com.woniuxy.gateway.filter;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woniuxy.commons.jwt.util.JwtUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 自定义认证过滤器
 * @author zh_o
 * @date 2020-10-24
 */
@Slf4j
@Data
@Component
@ConfigurationProperties(prefix = "include")
public class AuthenticationFilter implements GlobalFilter, Ordered {

    /**
     * 存储需要认证的请求
     */
    private List<String> paths = new ArrayList<>();

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 定义 Map 存储响应信息
        HashMap<String, Object> responseMap = new HashMap<>(2);
        log.info("需认证列表:  {}", paths);
        // 获取响应对象
        ServerHttpResponse response = exchange.getResponse();
        // 指定响应编码集
        response.getHeaders().add("Content-Type", "text/plain;charset=UTF-8");
        // 获取请求路径
        String path = exchange.getRequest().getPath().toString();
        // 判断是否需要认证
        if (!paths.contains(path)) {
            log.info("无需认证");
            return chain.filter(exchange);
        }
        // 获取 Token
        String token = exchange.getRequest().getHeaders().getFirst("access-token");
        try {
            // 验证 Token
            JwtUtils.verify(token);
            responseMap.put("message", "验证通过!");
            // 放行请求
            return chain.filter(exchange);
        } catch (SignatureVerificationException e) {
            e.printStackTrace();
            responseMap.put("message", "无效签名!");
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.writeWith(Mono.just(response.bufferFactory().wrap(responseMap.toString().getBytes())));
        } catch (TokenExpiredException e) {
            e.printStackTrace();
            responseMap.put("message", "Token 过期!");
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.writeWith(Mono.just(response.bufferFactory().wrap(responseMap.toString().getBytes())));
        } catch (AlgorithmMismatchException e) {
            e.printStackTrace();
            responseMap.put("message", "算法不一致!");
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.writeWith(Mono.just(response.bufferFactory().wrap(responseMap.toString().getBytes())));
        } catch (Exception e) {
            responseMap.put("message", "Token 无效!");
            e.printStackTrace();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.writeWith(Mono.just(response.bufferFactory().wrap(responseMap.toString().getBytes())));
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
