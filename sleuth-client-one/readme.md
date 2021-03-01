sleuth-client-one 和sleuth-client-two 为sleuth  和zipkin 的链路追踪
E版本以后 zipkin就通过jar形式启动
java -jar zipkin-server-2.10.4-exec.jar    默认同http来收集数据

rabbitmq来传输
添加依赖
<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-stream-binder-rabbit</artifactId>
		</dependency>
java -jar zipkin-server-2.10.4-exec.jar  --zipkin.collector.rabbitmq.addresses=localhost  通过rabbitmq来收集数据
启动后 会在rabbitmq创建默认的zipkin 队列

下面配置就不生效了
zipkin:
    base-url: http://localhost:9411/ # 指定了 Zipkin 服务器的地址   
    
    
默认的存储是在内存中  可在mysql  es中  需要在启动时 添加相关环境变量