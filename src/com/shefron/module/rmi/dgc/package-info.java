/**
 * Created by Administrator on 2014/12/1.
 */
package com.shefron.module.rmi.dgc;

/**
 * DGC:
 * 分布式垃圾收集机制
 *
 * 1. 远程对象的租约期限可以通过
 * java.rmi.dgc.leaseValue来设置单位：毫秒
 *
 * 2. 远程对象的生命周期（释放资源）
 *
 *  实现 java.rmi.server.Unreferenced接口
 */