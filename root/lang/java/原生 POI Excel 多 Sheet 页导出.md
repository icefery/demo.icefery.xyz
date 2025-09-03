## 依赖

```xml
<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi</artifactId>
    <version>4.1.0</version>
</dependency>
<dependency>
    <groupId>fr.opensagres.xdocreport</groupId>
    <artifactId>xdocreport</artifactId>
    <version>2.0.2</version>
</dependency>
<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi-ooxml</artifactId>
    <version>4.1.0</version>
</dependency>
<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi-ooxml-schemas</artifactId>
    <version>4.1.0</version>
</dependency>
<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>ooxml-schemas</artifactId>
    <version>1.4</version>
</dependency>
```

## 工具类

```java
@Target(value = {ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface ExcelColumn {
    String zh();
}
```

```java
import groovy.lang.Tuple3;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class ExcelUtil {
    public static Workbook multiSheet(List<Tuple3<String, Class<?>, List<?>>> list) {
        Workbook workbook = new SXSSFWorkbook();

        Font font = workbook.createFont();
        font.setBold(true);
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(font);

        for (Tuple3<String, Class<?>, List<?>> tuple3 : list) {
            String sheetName = tuple3.getV1();
            Class<?> sheetClass = tuple3.getV2();
            List<?> sheetData = tuple3.getV3();

            Sheet sheet = workbook.createSheet(sheetName);

            List<Field> fields = Arrays.stream(sheetClass.getDeclaredFields()).filter(key -> key.isAnnotationPresent(ExcelColumn.class)).peek(key -> key.setAccessible(true)).collect(Collectors.toList());
            // title
            Row row = sheet.createRow(0);
            for (int c = 0; c < fields.size(); c++) {
                String value = fields.get(c).getAnnotation(ExcelColumn.class).zh();
                Cell cell = row.createCell(c, CellType.STRING);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(value);
            }
            // value
            for (int r = 0; r < sheetData.size(); r++) {
                Object obj = sheetData.get(r);
                row = sheet.createRow(r + 1);
                for (int c = 0; c < fields.size(); c++) {
                    Object value = null;
                    try { value = fields.get(c).get(obj); } catch (IllegalAccessException ignored) { }
                    Cell cell = row.createCell(c, CellType.STRING);
                    cell.setCellValue(value == null ? "" : value.toString());
                }
            }
        }

        return workbook;
    }
}
```

## 使用

```java
try (
    ServletOutputStream os = response.getOutputStream();
    Workbook wb = ExcelUtil.multiSheet(Arrays.asList(
        new Tuple3<>("Sheet1", Sheet1DTO.class, sheet1Data),
        new Tuple3<>("Sheet2", Sheet2DTO.class, sheet2Data),
        new Tuple3<>("Sheet3", Sheet3DTO.class, sheet3Data)
    ));
) {
    response.setHeader("Content-Disposition", String.format("attachment;filename=%s.xlsx", URLEncoder.encode("XXX", "UTF-8")));
    response.setContentType("application/octet-stream");
    wb.write(os);
} catch (Exception ignored) {

}
```
