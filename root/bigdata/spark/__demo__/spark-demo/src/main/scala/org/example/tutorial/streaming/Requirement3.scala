package org.example.tutorial.streaming

import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.Date


object Requirement3 {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Requirement3").setMaster("local[*]")
    val ssc = new StreamingContext(conf, Seconds(1L))
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
        case Array(ts, area, city, user, ad) => {
          // 分钟向下取整
          // 12:01 => 12:00 | 01 / 10 * 10 => 00
          // 12:11 => 12:10 | 10 / 10 * 10 => 10
          // 12:19 => 12:10 | 19 / 10 * 10 => 10
          // 12:25 => 12:20 | 25 / 10 * 10 => 20
          // 12:59 => 12:50 | 59 / 10 * 10 => 50
          val newTS = ts.toLong / 10000 * 10000
          Array[(Long, Int)]((newTS, 1))
        }
        case _ => Array[(Long, Int)]()
      })
      // 最近一分钟每 10 秒计算一次
      .reduceByKeyAndWindow(
        reduceFunc = (a, b) => a + b,
        windowDuration = Seconds(60),
        slideDuration = Seconds(10)
      )
      .foreachRDD(rdd => {
        val jsonMapper = JsonMapper.builder().addModule(DefaultScalaModule).build()
        val list = rdd
          .sortByKey()
          .map(it => it match {
            case (ts, count) => {
              val time = new SimpleDateFormat("HH:mm:ss").format(new Date(ts))
              // (time -> count)
              Map("x" -> time, "y" -> count)
            }
          })
          .collect()
        val json = jsonMapper.writeValueAsString(list)
        val fr = new FileWriter("public/spark-streaming/requirement3/data.json")
        fr.write(json)
        fr.flush()
        println(json)
      })

    ssc.start()
    ssc.awaitTermination()
  }
}
