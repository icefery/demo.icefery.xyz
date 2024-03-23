package xyz.icefery.demo.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;

@Data
@TableName(value = "t_order")
public class Order {
    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField
    private Long userId;

    @TableField
    private Long commodityId;

    @TableField
    private BigDecimal money;

    @TableField
    private Integer count;
}
