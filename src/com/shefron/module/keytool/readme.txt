tomcat证书验证
1. 本机域名指定修改C:\Windows\System32\drivers\etc\hosts 添加127.0.0.1 www.shefron.cn
    ping www.shefron.cn

2. 修改tomcat/conf/server.xml
<Connector
			SSLEnabled="true"
			URIEncoding="UTF-8"
			clientAuth="false"
			keystoreFile="conf/zlex.keystore"
			keystorePass="123456"
			maxThreads="150"
			port="443"
			protocol="HTTP/1.1"
			scheme="https"
			secure="true"
			sslProtocol="TLS" />
注意clientAuth="false"测试阶段，置为false，正式使用时建议使用true。现在启动tomcat，访问https://www.zlex.org/。各种Java加密算法
显然，证书未能通过认证，这个时候你可以选择安装证书（上文中的zlex.cer文件就是证书），作为受信任的根证书颁发机构导入，再次重启浏览器（IE，其他浏览器对于域名www.zlex.org不支持本地方式访问），访问https://www.zlex.org/，你会看到地址栏中会有个小锁各种Java加密算法，就说明安装成功。所有的浏览器联网操作已经在RSA加密解密系统的保护之下了。但似乎我们感受不到


