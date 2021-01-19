gateway  先断言（Predicate）决定路由到哪里，之后进入过滤链逻辑

断言：  datetime   cookie   header  host  method  path  query  remoteAddr
过滤器工厂
自定义网管过滤器  和  自定义全局过滤器
spring:
   cloud:
    gateway:
      discovery:
        locator:
          enabled: true  #开启从注册中心动态创建路由，利用微服务的名称进行路由  可以网关/服务名/接口路径访问
          lower-case-service-id: true  #将服务名转化为小写
   例如：http://localhost:8083/eureka-client/hi       就可以访问到eureka-client
   
   