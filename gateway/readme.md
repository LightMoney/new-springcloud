gateway  先断言（Predicate）决定路由到哪里，之后进入过滤链逻辑

断言：  datetime   cookie   header  host  method  path  query  remoteAddr
过滤器工厂
自定义网管过滤器  和  自定义全局过滤器