package xyz.icefery.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;

@Data
@TableName(value = "t_order_item")
public class OrderItemEntity {
    @TableId
    private Long id;

    @TableField
    private String orderCode;

    @TableField
    private BigDecimal productPrice;

    @TableField
    private Long productCount;
}
