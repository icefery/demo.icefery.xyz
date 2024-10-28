package xyz.icefery.demo.account.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import lombok.Data;

@Data
@TableName(value = "t_account")
public class Account {

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField
    private BigDecimal money;
}
