package org.example.demo

import org.apache.spark.sql.{Row, SaveMode, SparkSession}
import org.apache.spark.sql.catalyst.encoders.RowEncoder
import org.apache.spark.sql.catalyst.expressions.GenericRowWithSchema
import org.apache.spark.sql.types.{StringType, StructField, StructType}
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import scala.collection.{immutable, mutable}

object App1 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("App1").master("local[*]").getOrCreate()

    val sourceDS = spark
      .read
      .format("jdbc")
      .options(immutable.Map(
        "driver" -> "org.postgresql.Driver",
        "url" -> "jdbc:postgresql://192.168.8.36:5432/demo",
        "user" -> "demo",
        "password" -> "demo",
        "dbtable" -> "stg.stg_erp_t1"
      ))
      .load()
      .alias("")

    val sinkSchema = StructType(
      sourceDS
        .schema
        .toBuffer
        .+=:(StructField("__ctime__", StringType))
    )
    sinkSchema.printTreeString()

    val sinkDS = sourceDS.map(sourceRow => {
      val ctime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
      val columns = Row
        .unapplySeq(sourceRow)
        .get
        .toBuffer
        .asInstanceOf[mutable.Buffer[String]]
        .map(col => if (col == null || col.trim().isEmpty) null else col.trim())
        .+=:(ctime)
      val sinkRow = new GenericRowWithSchema(columns.toArray, sinkSchema).asInstanceOf[Row]
      sinkRow
    })(RowEncoder(sinkSchema))
    sinkDS.show(10)

    sinkDS
      .write
      .mode(SaveMode.Overwrite)
      .format("jdbc")
      .options(immutable.Map(
        "driver" -> "org.postgresql.Driver",
        "url" -> "jdbc:postgresql://192.168.8.36:5432/demo",
        "user" -> "demo",
        "password" -> "demo",
        "dbtable" -> "ods.ods_erp_t1"
      ))
      .save()
  }
}
