package com.shefron.module.keytool;

/**
 * 1. 生成yushengqiang.keystore
 *
 * keytool -genkey -alias yushengqiang -keypass shefron4ym -keyalg RSA -keysize 1024 -validity 365 -keystore e:\temp\yushengqiang.keystore -storepass shefron4ym -dname "CN=shsp.news.cn,OU=116.213.107.20,O=116.213.107.20,L=BJ,ST=BJ,C=CN"
 *
 * 2. 查看yushengqiang.keystore
 *
 * keytool -list -V -keystore e:\temp\yushengqiang.keystore -storepass shefron4ym
 * keytool -list -rfc -keystore e:\temp\yushengqiang.keystore -storepass shefron4ym
 *
 * 3. 导出证书
 *
 * keytool -export -alias yushengqiang -keystore e:\temp\yushengqiang.keystore -file e:\temp\yushengiang.cer -storepass shefron4ym
 *
 * 4. 打印证书
 *
 * keytool -printcert -file e:\temp\yushengqiang.cer
 *
 * 5. 将证书导入密钥库
 *
 * keytool -import -alias yushengqiang2 -file e:/temp/yushengqiang.cer -keystore e:/temp/yushengqiang.keystore
 *
 * 附加使用
 * 1. 对jar文件签名
 *    jarsigner -storetype jks -keystore yushengqiang.keystore -verbose tools.jar yushengqiang
 *
 * 2. 验证jar
 *    jarsigner -verify -verbose -certs tools.jar
 *
 *
 *
 *
 *
 *
 *
 */
