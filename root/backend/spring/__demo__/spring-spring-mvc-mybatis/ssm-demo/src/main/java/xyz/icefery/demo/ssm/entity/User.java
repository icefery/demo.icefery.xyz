package xyz.icefery.demo.ssm.entity;

import java.time.LocalDate;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class User {

    private Long id;
    private String name;
    private LocalDate birthday;
    private Integer age;
}
