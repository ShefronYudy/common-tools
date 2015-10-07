/**
 * Created by Administrator on 2014/12/1.
 */
package com.shefron.module.rmi.securityManger;
/**
 * RMI 中指定安全策略和使用指定安全管理器
 *
 * 1. 创建安全策略文件 client.policy
 * 2. 使用安全策略文件和指定RMISecurityManager安全管理器
 *
 *
 * 代码实现
 *
 * 1.  java -Djava.security.policy=/opt/BOCO/chengxu/conf/client.policy SimpleClient
 *
 *
 * 2.
 *  System.setProperty("java.security.policy",SimpleClient.class.getResouce("client.policy).toString());
 *  System.setSecurityManager(new RMISecurityManager());
 */