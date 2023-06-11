package org.example.tutorial.core

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

object Requirement1 {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Requirement1").setMaster("local[*]")
    val sc = new SparkContext(conf)
    sc.setLogLevel("WARN")

    val actionRDD = sc.textFile("public/spark-core/user_visit_action.txt").map(UserVisitAction.parse)

    val top10Category = getTop10Category(actionRDD)

    top10Category.foreach(println)
  }

  private def getTop10Category(actionRDD: RDD[UserVisitAction]): Array[(String, (Int, Int, Int))] = {
    actionRDD
      // 数据结构转换
      // > click: (category_id,(1,0,0))
      // > order: (category_id,(0,1,0))
      // > pay:   (category_id,(0,0,1))
      .flatMap(action => {
        if (action.click_category_id != "-1") {
          // 点击的场合
          Array((action.click_category_id, (1, 0, 0)))
        } else if (action.order_category_ids != "null") {
          // 下单的场合
          action.order_category_ids.split(",").map(id => (id, (0, 1, 0)))
        } else if (action.pay_category_ids != "null") {
          // 支付的场合
          action.pay_category_ids.split(",").map(id => (id, (0, 0, 1)))
        } else {
          Nil
        }
      })
      // 将相同品类 ID 的数据进行分组聚合
      // > (category_id,(click_count,order_count,pay_count))
      .reduceByKey((a, b) => (a._1 + b._1, a._2 + b._2, a._3 + b._3))
      // 将统计结果根据数量进行降序处理取前 10 名
      .sortBy(f = it => it._2, ascending = false)
      .take(10)
  }
}
