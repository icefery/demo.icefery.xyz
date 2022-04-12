package xyz.icefery.demo.util.sql;

import com.google.common.base.CaseFormat;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class NativeSQLBuilder {
    /**
     * @param <T>     类型
     * @param dialect 方言
     * @param cls     类型
     * @param data    数据
     * @return <code>INSERT</code>
     */
    public static <T> String buildInsert(String dialect, Class<T> cls, List<T> data) {
        if (dialect == null || dialect.isBlank() || cls == null || data == null || data.isEmpty()) {
            throw new IllegalArgumentException();
        }
        String table = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, cls.getSimpleName());
        List<String> columns = buildColumns(cls, NativeSQLConverter.COLUMN_CONVERTER, NativeSQLConverter.VALUE_CONVERTER_MAP);
        List<List<String>> values = buildValues(dialect, cls, NativeSQLConverter.VALUE_CONVERTER_MAP, data);
        return buildInsert(dialect, table, columns, values);
    }

    /**
     * @param dialect 方言
     * @param table   表名
     * @param columns 列
     * @param values  值
     * @return <code>INSERT</code>
     */
    public static String buildInsert(String dialect, String table, List<String> columns, List<List<String>> values) {
        if (dialect == null || dialect.isBlank() || table == null || table.isBlank() || columns == null || columns.isEmpty() || values == null || values.isEmpty()) {
            throw new IllegalArgumentException();
        }
        String sql;
        switch (dialect.toLowerCase()) {
            case "oracle":
                sql = String.join("\n", List.of(
                    "INSERT ALL",
                    values.stream().map(it -> String.format("INTO %s ( %s ) VALUES ( %s )", table, String.join(", ", columns), String.join(", ", it))).collect(Collectors.joining("\n")),
                    "SELECT 1 FROM dual"
                ));
                break;
            case "mysql":
                sql = String.join("\n", List.of(
                    String.format("INSERT INTO %s ( %s ) VALUES", table, String.join(", ", columns)),
                    values.stream().map(it -> String.format("( %s )", String.join(", ", it))).collect(Collectors.joining(",\n"))
                ));
                break;
            default:
                throw new UnsupportedOperationException();
        }
        return sql;
    }

    /**
     * @param <T>               类型
     * @param cls               类型
     * @param columnConverter   列转换器
     * @param valueConverterMap 值转换器
     * @return <code>${table} ()</code>
     */
    public static <T> List<String> buildColumns(Class<T> cls, Function<Field, String> columnConverter, Map<Class<?>, BiFunction<String, Object, String>> valueConverterMap) {
        if (cls == null || columnConverter == null || valueConverterMap == null || valueConverterMap.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return Arrays
            .stream(cls.getDeclaredFields())
            .filter(field -> valueConverterMap.containsKey(field.getType()))
            .peek(field -> field.setAccessible(true))
            .map(columnConverter)
            .collect(Collectors.toList());
    }

    /**
     * @param <T>               类型
     * @param dialect           方言
     * @param cls               类型
     * @param valueConverterMap 类型转换器
     * @param data              数据
     * @return <code>VALUES ()</code>
     */
    public static <T> List<List<String>> buildValues(String dialect, Class<T> cls, Map<Class<?>, BiFunction<String, Object, String>> valueConverterMap, List<T> data) {
        if (dialect == null || dialect.isBlank() || cls == null || valueConverterMap == null || valueConverterMap.isEmpty() || data == null || data.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return data.stream().map(it -> Arrays.stream(cls.getDeclaredFields()).filter(field -> valueConverterMap.containsKey(field.getType())).map(field -> {
            try {
                field.setAccessible(true);
                Class<?> type = field.getType();
                Object value = field.get(it);
                // 视 null 值的类型也为 null
                if (value == null) {
                    type = null;
                }
                return NativeSQLConverter.VALUE_CONVERTER_MAP.get(type).apply(dialect, value);
            } catch (Exception e) {
                return "NULL";
            }
        }).collect(Collectors.toList())).collect(Collectors.toList());
    }
}
