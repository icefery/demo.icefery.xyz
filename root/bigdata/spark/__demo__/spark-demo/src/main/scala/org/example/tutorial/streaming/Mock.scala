package org.example.tutorial.streaming

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}
import java.util.Properties
import java.util.concurrent.{CompletableFuture, TimeUnit}
import scala.collection.mutable.ListBuffer
import scala.util.Random

object Mock {
  def main(args: Array[String]): Unit = {
    val properties = new Properties()
    properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "vm101:9092")
    properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
    properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
    val producer = new KafkaProducer[String, String](properties)

    val areaList = List[String]("华东", "华南", "华西", "华北")
    val cityList = List[String]("北京", "上海", "广州", "深圳")
    val random = new Random()
    while (true) {
      // 一次性生成多条消息
      val messageList = ListBuffer[String]()
      for (i <- 1 to random.nextInt(100) + 1) {
        val ts = System.currentTimeMillis()
        val area = areaList(random.nextInt(areaList.length))
        val city = cityList(random.nextInt(cityList.length))
        val user = random.nextInt(5) + 1
        val ad = random.nextInt(5) + 1
        messageList.append(s"${ts} ${area} ${city} ${user} ${ad}")
      }
      // 一次性发送多条消息
      val futureList = messageList.map(message => {
        val record = new ProducerRecord[String, String]("topic_demo", message)
        val future = producer.send(record)
        CompletableFuture.supplyAsync(() => future.get())
      })
      CompletableFuture.allOf(futureList: _*).get(1000L, TimeUnit.MILLISECONDS)
      // 打印消息数量
      println(messageList.length)
      Thread.sleep(1000L)
    }
  }
}
