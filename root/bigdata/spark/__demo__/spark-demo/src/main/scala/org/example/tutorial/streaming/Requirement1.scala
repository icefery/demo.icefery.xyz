package org.example.tutorial.streaming

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}
import java.text.SimpleDateFormat
import java.util.Date

object Requirement1 {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Requirement1").setMaster("local[*]")
    val ssc = new StreamingContext(conf, Seconds(5))
    ssc.sparkContext.setLogLevel("WARN")

    val kafkaParams = Map[String, Object](
      ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG -> "vm101:9092",
      ConsumerConfig.GROUP_ID_CONFIG -> "consumer_demo",
      ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG -> "org.apache.kafka.common.serialization.StringDeserializer",
      ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG -> "org.apache.kafka.common.serialization.StringDeserializer"
    )

    KafkaUtils
      .createDirectStream[String, String](ssc, LocationStrategies.PreferConsistent, ConsumerStrategies.Subscribe[String, String](Set("topic_demo"), kafkaParams))
      // filter + map
      .flatMap(record => record.value().split(" ") match {
        case Array(ts, area, city, user, ad) => Array[ADClick](ADClick(ts, area, city, user, ad))
        case _ => Array[ADClick]()
      })
      .transform(rdd => {
        // 过滤黑名单内数据
        val blacklistList = JDBCUtil.queryForValList[String]("SELECT user FROM blacklist")
        rdd
          .filter(it => !blacklistList.contains(it.user))
          .map(it => {
            val date = new SimpleDateFormat("yyyy-MM-dd").format(new Date(it.ts.toLong))
            val user = it.user
            val ad = it.ad
            ((date, user, ad), 1)
          })
          .reduceByKey((a, b) => a + b)
      })
      .foreachRDD(rdd => {
        rdd.foreach(it => it match {
          case ((date, user, ad), count) => {
            // 统计次数
            JDBCUtil.update(
              s"""
                 |INSERT INTO user_ad_count (date, user, ad, count)
                 |VALUES ('${date}', '${user}', '${ad}', ${count})
                 |ON DUPLICATE KEY UPDATE count = count + ${count}
                 |""".stripMargin
            )
            // 刷新黑名单
            JDBCUtil.update(
              s"""
                 |INSERT INTO blacklist (user)
                 |SELECT user FROM user_ad_count t WHERE count > 100
                 |ON DUPLICATE KEY UPDATE user = t.user
                 |""".stripMargin
            )
          }
        })
      })

    ssc.start()
    ssc.awaitTermination()
  }

  case class ADClick(ts: String, area: String, city: String, user: String, ad: String)
}
