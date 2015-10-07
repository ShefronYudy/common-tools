/**
 * Created by Administrator on 2014/12/2.
 */
package com.shefron.module.jdbc;
/*
* java JDBC 访问数据库
*
* 一、DriverManager方法
* 1. registerDriver(Driver driver)
* 2. getConnection(url, user, pass)
* 3. setLoginTimeout(int seconds)
* 4. setLogWriter(PrintWriter out)
*
* 二、Statement 执行execute()或executeQuery()
* 同一Statement执行上述方法时都会关闭上次打开的ResultSet
*
* 三、Statement execute(String sql)若返回true则表示具有结果集
*
* 四、ResultSet结果集封装ResultSetMetaData
*
* 五、SQLException,SQLWarning的运用
*
* 六、获取数据库自动生成的主键值（MYSql）、设置批量抓取属性
* stmt.executeUpdate(updateSql, Statement.RETURN_GENERATED_KEYS)
* ResultSet rs = stmt.getGeneratedKeys()
* rs.getInt(1)
*
* Connection, Statement, ResultSet
* setFetchSize(int size)
* setFetchDirection(int direction) ResultSet.FETCH_FORWARD|ResultSet.FETCH_REVERSE|ResultSet.FETCH_UNKNOWN
*
* 七、检测数据库驱动实现的java JDBC 版本
*
* DatabaseMetaData.getJDBCMajorVersion() and DatabaseMetaData.getJDBCMinorVersion()
*
* 八、元数据
*
* 1. DatabaseMetaData ResultSetMetaData ParameterMetaData
*
* 九、可滚动和可更新的结果集ResultSet有Connection创建的Statement, PreparedStatement决定
*
* createStatement(int type, int concurrency)
* prepareStatement(String sql,int type, int concurrency)
*
* type:ResultSet.TYPE_FORWARD_ONLY|ResultSet.TYPE_SCROLL_INSENSITIVE|ResultSet.TYPE_SCROLL_SENSITIVE
* concurrency:CONCUR_READ_ONLY|CONCUR_UPDATETABLE
*
* 十、行集 RowSet SUN进行了参考实现 com.sun.rowset包
*
* 1. CachedRowSet 缓存的结果集，可由用户决定同步时机
* 2. WebRowSet 可保存到XML文件中
* 3. FilteredRowSet 过滤缓存的结果集
* 4. JdbcRowSet 结果集可转化javabean
*
*
* 十一、调用存储过程
*
*
*
* */