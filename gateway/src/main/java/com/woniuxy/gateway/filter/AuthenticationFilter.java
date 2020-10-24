package com.woniuxy.gateway.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义认证过滤器
 * @author zh_o
 * @date 2020-10-24
 */
@Slf4j
@Data
@Component
@ConfigurationProperties(prefix = "list")
public class AuthenticationFilter implements GlobalFilter, Ordered {

    /**
     * 存储需要认证的请求
     */
    private List<String> items = new ArrayList<>();

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("需要认证列表:  {}", items);
        // 获取响应对象
        ServerHttpResponse response = exchange.getResponse();
        // 指定响应编码集
        response.getHeaders().add("Content-Type", "text/plain;charset=UTF-8");
        // 定义认证失败响应
        String message = "权限不足";
        DataBuffer buffer = response.bufferFactory().wrap(message.getBytes());
        // 获取请求路径
        String path = exchange.getRequest().getPath().toString();
        // 切割路径
        String[] split = path.split("/");
        // 获取最后一位
        String str = split[split.length - 1];
        // 判断是否需要认证
        if (!items.contains(str)) {
            log.info("无需认证");
            return chain.filter(exchange);
        }
        // 获取自定义认证信息
        List<String> token = exchange.getRequest().getHeaders().get("token");
         //判断是否存在
        if (token == null) {
            log.info("认证失败");
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.writeWith(Mono.just(buffer));
        }
        // 判断值是否正确
        boolean flag = false;
        for (String item : token) {
            String tokenValue = "94a08da1fecbb6e8b46990538c7b50b2";
            if (tokenValue.equals(item)) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            log.info("认证失败");
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.writeWith(Mono.just(buffer));
        }
        log.info("认证通过");
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
