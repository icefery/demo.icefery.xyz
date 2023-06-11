package org.example.tutorial.streaming

import com.alibaba.druid.pool.DruidDataSourceFactory
import java.sql.PreparedStatement
import java.util.Properties
import javax.sql.DataSource
import scala.collection.mutable.ListBuffer
import scala.language.postfixOps
import scala.reflect.ClassTag

object JDBCUtil {
  private val datasource: DataSource = init()

  // insert | update | delete
  def update(sql: String, args: Array[Any] = Array[Any]()): Int = {
    // 定义返回值
    var result = 0
    // 获取连接
    val connection = datasource.getConnection()
    try {
      val ps = connection.prepareStatement(sql)
      // 绑定参数
      bind(ps, args)
      // 执行语句
      result = ps.executeUpdate()
      // 释放资源
      ps.close()
      connection.close()
    } catch {
      case e: Exception => e.printStackTrace()
    }
    result
  }

  // select
  def queryForValList[T](sql: String, args: Array[Any] = Array[Any]()): ListBuffer[T] = {
    val result = ListBuffer[T]()
    val connection = datasource.getConnection()
    try {
      val ps = connection.prepareStatement(sql)
      bind(ps, args)
      val rs = ps.executeQuery()
      while (rs.next()) {
        val item = rs.getString(1).asInstanceOf[T]
        result.append(item)
      }
      ps.close()
      connection.close()
    } catch {
      case e: Exception => e.printStackTrace()
    }
    result
  }

  // select
  def queryForCaseList[T](sql: String, args: Array[Any] = Array[Any]())(implicit ct: ClassTag[T]): ListBuffer[T] = {
    // 定义返回值
    val result = ListBuffer[T]()
    // 反射获取类型信息
    val fields = ct.runtimeClass.getDeclaredFields
    val constructor = ct.runtimeClass.getConstructors.head
    // 获取连接
    val connection = datasource.getConnection()
    try {
      val ps = connection.prepareStatement(sql)
      bind(ps, args)
      val rs = ps.executeQuery()
      while (rs.next()) {
        val parameters = fields.map(field => rs.getString(field.getName))
        val instance = constructor.newInstance(parameters: _*).asInstanceOf[T]
        result.append(instance)
      }
    } catch {
      case e: Exception => e.printStackTrace()
    }
    result
  }

  private def bind(ps: PreparedStatement, args: Array[Any]): Unit = {
    if (args != null && args.length > 0) {
      for (i <- args.indices) {
        ps.setObject(i + 1, args(i))
      }
    }
  }

  private def init(): DataSource = {
    val properties = new Properties()
    properties.setProperty(DruidDataSourceFactory.PROP_DRIVERCLASSNAME, "com.mysql.cj.jdbc.Driver")
    properties.setProperty(DruidDataSourceFactory.PROP_URL, "jdbc:mysql://vm101:3306/demo")
    properties.setProperty(DruidDataSourceFactory.PROP_USERNAME, "root")
    properties.setProperty(DruidDataSourceFactory.PROP_PASSWORD, "root")
    DruidDataSourceFactory.createDataSource(properties)
  }
}
