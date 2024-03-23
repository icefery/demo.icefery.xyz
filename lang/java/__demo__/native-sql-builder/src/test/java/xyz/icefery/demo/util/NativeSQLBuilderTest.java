package xyz.icefery.demo.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import xyz.icefery.demo.util.sql.NativeSQLBuilder;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class NativeSQLBuilderTest {
    static String table;
    static List<String> columns;
    static List<List<String>> values;
    static List<User> data = new ArrayList<>();

    @BeforeAll
    public static void beforeAll() throws DatatypeConfigurationException {
        // table
        table = "user";
        // columns
        columns = List.of("a", "b", "c");
        // values
        values = List.of(List.of("1", "2", "3"), List.of("4", "5", "6"));
        // data
        data = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            data.add(new User(null, i, (long) i, new BigDecimal(i), String.valueOf(i), new Date(), LocalDateTime.now(), DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar())));
        }
    }

    @Test
    public void test1() {
        String expected = "INSERT ALL INTO user ( a, b, c ) VALUES ( 1, 2, 3 ) INTO user ( a, b, c ) VALUES ( 4, 5, 6 ) SELECT 1 FROM dual";
        String dialect = "oracle";
        String actual = NativeSQLBuilder.buildInsert(dialect, table, columns, values);
        actual = actual.replaceAll("\n", " ").replaceAll("\\s+", " ").trim();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void test2() {
        String expected = "INSERT INTO user ( a, b, c ) VALUES ( 1, 2, 3 ), ( 4, 5, 6 )";
        String dialect = "mysql";
        String actual = NativeSQLBuilder.buildInsert(dialect, table, columns, values);
        actual = actual.replaceAll("\n", " ").replaceAll("\\s+", " ").trim();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void test3() {
        String actual = NativeSQLBuilder.buildInsert("oracle", User.class, data);
        System.out.println(actual);
    }

    @Test
    public void test4() {
        String actual = NativeSQLBuilder.buildInsert("mysql", User.class, data);
        System.out.println(actual);
    }

    public static class User {
        Integer nullField;
        Integer integerField;
        Long longField;
        BigDecimal bigDecimalField;
        String stringField;
        Date dateField;
        LocalDateTime localDateTimeField;
        XMLGregorianCalendar xmlGregorianCalendarField;
        User(Integer nullField, Integer integerField, Long longField, BigDecimal bigDecimalField, String stringField, Date dateField, LocalDateTime localDateTimeField, XMLGregorianCalendar xmlGregorianCalendarField) {
            this.nullField = nullField;
            this.integerField = integerField;
            this.longField = longField;
            this.bigDecimalField = bigDecimalField;
            this.stringField = stringField;
            this.dateField = dateField;
            this.localDateTimeField = localDateTimeField;
            this.xmlGregorianCalendarField = xmlGregorianCalendarField;
        }
    }
}
