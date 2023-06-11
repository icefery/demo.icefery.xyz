package org.example.tutorial.core

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

object Requirement3 {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Requirement3").setMaster("local[*]")
    val sc = new SparkContext(conf)
    sc.setLogLevel("WARN")

    val actionRDD = sc.textFile("public/spark-core/user_visit_action.txt").map(UserVisitAction.parse)

    val pageflow = getPageflow(actionRDD)

    pageflow.foreach(it => it match {
      case ((page1, page2), sum) => println(s"从页面 ${page1} 跳转到页面 ${page2} 的单跳转化率为 ${sum}")
    })
  }

  private def getPageflow(actionRDD: RDD[UserVisitAction]): Array[((String, String), Double)] = {
    // 计算分母
    val down = actionRDD
      .map(action => (action.page_id, 1))
      .reduceByKey((a, b) => a + b)
      .collect()
      .toMap

    // 计算分子
    val up = actionRDD
      // 根据会话分组
      .groupBy(action => action.session_id)
      // 分组后根据访问时间进行排序
      .mapValues(values => {
        val ids = values
          .toList
          .sortBy(action => action.action_time)
          .map(action => action.page_id)
        ids
          .zip(ids.tail)
          .map(it => (it, 1))
      })
      .flatMap(it => it._2)
      .reduceByKey((a, b) => a + b)

    // 计算单跳转率
    up
      .map(it => it match {
        case ((page1, page2), sum) => {
          val d = down.getOrElse(page1, 0)
          ((page1, page2), sum.toDouble / d)
        }
      })
      .collect()
  }
}
