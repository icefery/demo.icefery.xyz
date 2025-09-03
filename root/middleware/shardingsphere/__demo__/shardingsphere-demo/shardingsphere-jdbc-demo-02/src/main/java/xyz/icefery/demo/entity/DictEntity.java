package xyz.icefery.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "t_dict")
public class DictEntity {

    @TableId
    private Long id;

    @TableField
    private String dictKey;

    @TableField
    private String dictValue;
}
