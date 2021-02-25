生成jks文件(可参看网上)
keytool -genkeypair  -alias fzp-jwt  -validity 3650  -keyalg  RSA -dname  "CN=jwt,OU=jtw,O=jtw,L=zurich,S=zurich,C=CH"  -keypass  fzp123  -keystore fzp-jwt.jks -storepass fzp123

获取公钥
keytool -list -rfc  --keystore fzp-jwt.jks  |  openssl  x509 -inform pem -pubkey
提示输入密码  为 上面私钥的密码fzp123
将获取到的信息 放入public.cert 的文件中（放resources目录下）
避免maven项目编译时jks文件乱码添加如下配置：
<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-resources-plugin</artifactId>
				<configuration>
					<nonFilteredFileExtensions>
						<nonFilteredFileExtension>cert</nonFilteredFileExtension>
						<nonFilteredFileExtension>jks</nonFilteredFileExtension>
					</nonFilteredFileExtensions>
				</configuration>
			</plugin>
			
			
			资源服务器和认证服务器同时存在时认证服务器不生效
			
		普通项目去请求oauth\token 获取token
		前后端分离下 应该登录后返回token 
		1.创建自定义处理器
		2.登录时带上求请头必须带着提供clientId和clientSecret的base64    
		3.登录成功后走自定义成功处理器 通过oauth2对应的创建token类来封装token
		
		
注意：当认证服务器和资源服务器在一起时 默认资源服务器生效  认证服务器失效
资源服务器默认的优先级为3   认证服务器为100      想让认证服务器生效提升优先级@order
要想同时生效  
认证服务器路径与资源服务器路径错开  且资源的路径不能包含认证
认证
.authorizeRequests()
                .antMatchers("/test/**").authenticated()
                .anyRequest().permitAll()
资源
antMatcher("/pass/**") 
                .authorizeRequests()
                .anyRequest().authenticated()