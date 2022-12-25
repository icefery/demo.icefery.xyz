package xyz.icefery.demo.stock.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "t_stock")
public class Stock {
    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField
    private Long commodityId;

    @TableField
    private Integer count;
}
