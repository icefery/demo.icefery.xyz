package org.example.tutorial.streaming

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}
import java.text.SimpleDateFormat
import java.util.Date

object Requirement2 {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Requirement2").setMaster("local[*]")
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
      .flatMap(message => message.value().split(" ") match {
        case Array(ts, area, city, user, ad) => {
          val date = new SimpleDateFormat("yyyy-MM-dd").format(new Date(ts.toLong))
          Array[((String, String, String, String), Int)](((date, area, city, ad), 1))
        }
        case _ => Array[((String, String, String, String), Int)]()
      })
      .reduceByKey((a, b) => a + b)
      .foreachRDD(rdd => {
        rdd.foreachPartition(values => {
          values.foreach(it => it match {
            case ((date, area, city, ad), count) => {
              JDBCUtil.update(
                s"""
                   |INSERT INTO area_city_ad_count (date, area, city, ad, count)
                   |VALUES ('${date}', '${area}', '${city}', '${ad}', ${count})
                   |ON DUPLICATE KEY UPDATE count = ${count}
                   |""".stripMargin
              )
            }
          })
        })
      })

    ssc.start()
    ssc.awaitTermination()
  }
}
