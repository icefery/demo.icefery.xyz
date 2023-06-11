package org.example.tutorial.core

// @formatter:off
case class UserVisitAction(
  date: String,                 // 用户点击行为的日期
  user_id: String,              // 用户的 ID
  session_id: String,           // 会话的 ID
  page_id: String,              // 页面的 ID
  action_time: String,          // 动作的时间点
  search_keyword: String,       // 用户搜索的关键词
  click_category_id: String,    // 商品品类的 ID
  click_product_id: String,     // 商品的 ID
  order_category_ids: String,   // 一次订单中所有品类的 ID 集合
  order_product_ids: String,    // 一次订单中所有品类的 ID 集合
  pay_category_ids: String,     // 一次支付中所有品类的 ID 集合
  pay_product_ids: String,      // 一次支付中所有商品的 ID 集合
  city_id: String               // 城市的 ID
)
// @formatter:on

case object UserVisitAction {
  def parse(line: String): UserVisitAction = {
    val fields = line.split("_")
    UserVisitAction(
      fields(0),
      fields(1),
      fields(2),
      fields(3),
      fields(4),
      fields(5),
      fields(6),
      fields(7),
      fields(8),
      fields(9),
      fields(10),
      fields(11),
      fields(12)
    )
  }
}
