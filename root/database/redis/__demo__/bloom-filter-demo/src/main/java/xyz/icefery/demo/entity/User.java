package xyz.icefery.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName(value = "t_user")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @TableField
    private String username;

    @TableField
    private String email;
}
