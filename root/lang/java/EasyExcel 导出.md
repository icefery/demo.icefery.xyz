## 依赖

```xml
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>easyexcel</artifactId>
    <version>3.2.1</version>
</dependency>
```

## 快速开始

```java
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.write.metadata.WriteSheet;
import lombok.AllArgsConstructor;
import lombok.Cleanup;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

public class App {
    public static void main(String[] args) {
        WriteSheet sheet1 = EasyExcel.writerSheet("sheet1").head(User.class).build();
        List<User> sheet1Data = List.of(
            new User("a1", "b1", "c1"),
            new User("a2", "b2", "c2"),
            new User("a3", "b3", "c3")
        );

        WriteSheet sheet2 = EasyExcel.writerSheet("sheet2").head(Role.class).build();
        List<Role> sheet2Data = List.of(
            new Role("x1", "y1"),
            new Role("x2", "y2"),
            new Role("x3", "y3")
        );

        @Cleanup ExcelWriter excelWriter = EasyExcel.write("a.xlsx").build();
        excelWriter
            .write(() -> sheet1Data, sheet1)
            .write(() -> sheet2Data, sheet2);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class User {
        @ExcelProperty(value = "列一")
        private String col1;
        @ExcelProperty(value = "列二")
        private String col2;
        @ExcelProperty(value = "列三")
        private String col3;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Role {
        @ExcelProperty(value = "字段一")
        private String field1;
        @ExcelProperty(value = "字段二")
        private String field2;
    }
}
```
