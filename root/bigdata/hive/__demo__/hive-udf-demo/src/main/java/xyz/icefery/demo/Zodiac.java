package xyz.icefery.demo;

import cn.hutool.core.date.DateUtil;
import java.util.Calendar;
import java.util.Objects;
import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentLengthException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentTypeException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;

@Description(
    name = "zodiac",
    value = "_FUNC_(birthday) - Returns chinese zodiac and zodiac",
    extended = "Example:\n" + "  > SELECT _FUNC_(to_date('2022-12-12'));\n" + "  虎 射手座"
)
public class Zodiac extends GenericUDF {

    private static final String USAGE = String.format("USAGE: %s(birthday: date)", Zodiac.class.getSimpleName().toLowerCase());

    @Override
    public ObjectInspector initialize(ObjectInspector[] arguments) throws UDFArgumentException {
        if (arguments.length != 1) {
            throw new UDFArgumentLengthException(USAGE);
        }
        if (
            !Objects.equals(ObjectInspector.Category.PRIMITIVE, arguments[0].getCategory()) ||
            !((PrimitiveObjectInspector) arguments[0]).getPrimitiveCategory().equals(PrimitiveObjectInspector.PrimitiveCategory.DATE)
        ) {
            throw new UDFArgumentTypeException(0, USAGE);
        }
        return PrimitiveObjectInspectorFactory.javaStringObjectInspector;
    }

    @Override
    public Object evaluate(DeferredObject[] arguments) throws HiveException {
        Calendar birthday = DateUtil.parseByPatterns(arguments[0].get().toString(), "yyyy-MM-dd");
        String chineseZodiac = DateUtil.getChineseZodiac(birthday.get(Calendar.YEAR));
        String zodiac = DateUtil.getZodiac(birthday.get(Calendar.MONTH), birthday.get(Calendar.DAY_OF_MONTH));
        return chineseZodiac + " " + zodiac;
    }

    @Override
    public String getDisplayString(String[] children) {
        return super.getStandardDisplayString(Zodiac.class.getSimpleName().toLowerCase(), children);
    }
}
