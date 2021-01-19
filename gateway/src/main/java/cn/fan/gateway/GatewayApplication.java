package cn.fan.gateway;

import cn.fan.gateway.filter.RequestTimeFilter;
import cn.fan.gateway.filter.RequestTimeGatewayFilterFactory;
import cn.fan.gateway.resolver.HostAddrKeyResolver;
import cn.fan.gateway.resolver.UriKeyResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    //    自定义过滤器注册到路由中（这里配了高优先级）
    @Bean
    public RouteLocator customerRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/get")
                        .filters(f -> f.filter(new RequestTimeFilter())
                                .addResponseHeader("X-Response-Default-Foo", "Default-Bar"))
                        .uri("http://httpbin.org:80")
                        .order(0)
                        .id("customer_filter_router")
                )
                .build();
    }

    //注入自定义的过滤工厂，再在配置文件中配置
    @Bean
    public RequestTimeGatewayFilterFactory elapsedGatewayFilterFactory() {
        return new RequestTimeGatewayFilterFactory();
    }

    //激活全局自定义过滤器
    //    @Bean
//    public TokenFilter tokenFilter(){
//        return new TokenFilter();
//    }

//限流的键要注入  配置文件中好获取
    @Bean
    public HostAddrKeyResolver hostAddrKeyResolver() {
        return new HostAddrKeyResolver();
    }

//    @Bean
//    public UriKeyResolver uriKeyResolver() {
//        return new UriKeyResolver();
//    }

//    @Bean
//    KeyResolver userKeyResolver() {
//        return exchange -> Mono.just(exchange.getRequest().getQueryParams().getFirst("user"));
//    }
}
