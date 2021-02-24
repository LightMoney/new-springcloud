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