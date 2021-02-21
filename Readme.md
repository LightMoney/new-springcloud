采用springboot  2.1.0 release
springcloud    Greenwich.release


uaa-service 和user-service是使用 spring security oauth2和jwt的登录demo   
（负责授权）    （作为资源服务器）
该方式的优点：一次获取token 多次使用 无需每次都请求授权服务
缺点：如果token没有过期，就一直可以使用（即使用户权限发生变化）（需要根据具体业务场景设置过期时间）


前端           后端
登录（用户密码） 用秘钥创建jwt（返回给前端）
在header中添加jwt  检查jwt解密，获取用户信息（给客户端响应）