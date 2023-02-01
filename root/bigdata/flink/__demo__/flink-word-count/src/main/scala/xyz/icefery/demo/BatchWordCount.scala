package xyz.icefery.demo

import org.apache.flink.api.scala.ExecutionEnvironment

object BatchWordCount {
  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment

    val dataSet = env.readTextFile("input/words.txt")

    dataSet.print()
  }
}
