package xyz.icefery.demo.jsp.bean;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Student {

    private String stuId;
    private String name;
    private String pwd;
    private Integer sex;
    private Timestamp dob;
    private String nativePlace;
    private String addr;
    private String email;
    private Integer enabled;
}
