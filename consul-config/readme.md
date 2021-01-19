consul分布式配置中心

访问consul 界面的Key/Value存储
key:
config/consul-config:dev/data

value:

foo:
 bar: bar1
server:
 port: 8081
 
 注意consul的kv的value值不能超过512kb，在dev模式下存在内存中，重启会丢失数据
 非dev下会持久化
 

