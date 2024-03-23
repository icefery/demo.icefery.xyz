package xyz.icefery.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "t_order")
public class OrderEntity {
    @TableId
    private Long id;

    @TableField
    private String orderCode;
}
