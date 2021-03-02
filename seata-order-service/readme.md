项目可参看：https://www.jianshu.com/p/21782150f043
或有道云笔记：使用Seata彻底解决Spring Cloud中的分布式事务问题！

Failed to fetch schema of `order`的解决办法
只需要在Url里添加useInformationSchema=false

连接报null  连接8.0.22mysql 使用5.1.37   mysql-connector-java
解决办法：换成8.几的mysql-connector-java
连高版本 时区问题： url  添加serverTimezone=GMT%2B8




tcc模式参看有道云：Spring Cloud集成seata分布式事务-TCC模式

默认采用at模式
给需要分布式事务的方法配上全局事务
使用Seata对数据源进行代理 见DataSourceProxyConfig类
@GlobalTransactional(name = "fsp-create-order",rollbackFor = Exception.class)
配置 file.conf和registry.conf


服务的server从https://github.com/seata/seata/releases 下载zip 解压后 可在conf 目录下readme.md
找到需要配置的sql文件和配置文件git地址
