package org.example.tutorial.core

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

object Requirement2 {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Requirement2").setMaster("local[*]")
    val sc = new SparkContext(conf)
    sc.setLogLevel("WARN")

    val actionRDD = sc.textFile("public/spark-core/user_visit_action.txt").map(UserVisitAction.parse)

    val top10Category = getTop10Category(actionRDD)

    val top10CategoryTop10Session = getTop10CategoryTop10Session(
      actionRDD,
      top10Category.map(it => it._1)
    )

    top10CategoryTop10Session.foreach(println)
  }

  private def getTop10Category(actionRDD: RDD[UserVisitAction]): Array[(String, (Int, Int, Int))] = ???

  private def getTop10CategoryTop10Session(actionRDD: RDD[UserVisitAction], top10Category: Array[String]): Array[(String, List[(String, Int)])] = {
    actionRDD
      // 过滤原始数据保留点击和前 10 品类
      .filter(action => action.click_category_id != "-1" && top10Category.contains(action.click_category_id))
      // 根据品类 ID 和会话 ID 进行点击量的统计
      .map(action => ((action.click_category_id, action.session_id), 1))
      .reduceByKey((a, b) => a + b)
      // 数据结构转换
      // > ((category_id,session_id),sum) => (category_id,(session_id,sum))
      .map(it => it match {
        case ((cid, sid), sum) => (cid, (sid, sum))
      })
      // 根据相同的品类进行分组
      .groupByKey()
      // 将分组后的数据进行点击量的排序取前 10 名
      .mapValues(values =>
        values
          .toList
          .sortBy(it => it._2)(Ordering.Int.reverse)
          .take(10)
      )
      .collect()
  }
}
