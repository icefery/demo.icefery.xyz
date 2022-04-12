package xyz.icefery.demo.util.sql;

import com.google.common.base.CaseFormat;
import javax.xml.datatype.XMLGregorianCalendar;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public class NativeSQLConverter {
    public static final Function<Field, String> COLUMN_CONVERTER = field -> CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field.getName());

    public static final Map<Class<?>, BiFunction<String, Object, String>> VALUE_CONVERTER_MAP = new HashMap<>();

    static {
        // null 类型
        VALUE_CONVERTER_MAP.put(null, (dialect, value) -> "NULL");
        // 数字类型
        VALUE_CONVERTER_MAP.put(Integer.class, (dialect, value) -> String.valueOf(value));
        VALUE_CONVERTER_MAP.put(Long.class, (dialect, value) -> String.valueOf(value));
        VALUE_CONVERTER_MAP.put(BigDecimal.class, (dialect, value) -> value.toString());
        // 字符类型
        VALUE_CONVERTER_MAP.put(String.class, (dialect, value) -> String.format("'%s'", value));
        // 日期类型
        VALUE_CONVERTER_MAP.put(Date.class, (dialect, value) -> datetime(dialect, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Date) value)));
        VALUE_CONVERTER_MAP.put(LocalDateTime.class, (dialect, value) -> datetime(dialect, ((LocalDateTime) value).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        VALUE_CONVERTER_MAP.put(XMLGregorianCalendar.class, (dialect, value) -> datetime(dialect, ((XMLGregorianCalendar) value).toGregorianCalendar().toZonedDateTime().toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
    }

    private static String datetime(String dialect, String value) {
        String placeholder;
        switch (dialect.toLowerCase()) {
            case "oracle":
                placeholder = String.format("TO_DATE('%s', 'yyyy-mm-dd hh24:mi:ss')", value);
                break;
            case "mysql":
                placeholder = String.format("'%s'", value);
                break;
            default:
                throw new UnsupportedOperationException();
        }
        return placeholder;
    }
}
