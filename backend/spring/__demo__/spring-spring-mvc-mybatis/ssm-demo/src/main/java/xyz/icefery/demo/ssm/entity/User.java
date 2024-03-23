package xyz.icefery.demo.ssm.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import java.time.LocalDate;

@Data
@Accessors(chain = true)
public class User {
    private Long id;
    private String name;
    private LocalDate birthday;
    private Integer age;
}
