package xyz.icefery.demo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentLengthException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentTypeException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDTF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Description(
    name = "json_array_flat",
    value = "_FUNC_(json_array) - Explode 2-level json array",
    extended = "Example:\n"
        + "  > SELECT _FUNC_('[1,2,[3,4],5,6]');\n"
        + "  1\n"
        + "  2\n"
        + "  3\n"
        + "  4\n"
        + "  5\n"
        + "  6"
)
public class JSONArrayFlat extends GenericUDTF {
    private static final String USAGE = String.format("USAGE: %s(json_array: string)", JSONArrayFlat.class.getSimpleName().toLowerCase());
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public StructObjectInspector initialize(ObjectInspector[] argOIs) throws UDFArgumentException {
        if (argOIs.length != 1) {
            throw new UDFArgumentLengthException(USAGE);
        }
        if (!Objects.equals(argOIs[0].getCategory(), ObjectInspector.Category.PRIMITIVE) || !Objects.equals(((PrimitiveObjectInspector) argOIs[0]).getPrimitiveCategory(), PrimitiveObjectInspector.PrimitiveCategory.STRING)) {
            throw new UDFArgumentTypeException(0, USAGE);
        }
        return ObjectInspectorFactory.getStandardStructObjectInspector(
            List.of("item"),
            List.of(PrimitiveObjectInspectorFactory.javaStringObjectInspector)
        );
    }

    @Override
    public void process(Object[] args) throws HiveException {
        try {
            JsonNode jsonArray = OBJECT_MAPPER.readTree(args[0].toString());
            for (JsonNode l1 : jsonArray) {
                if (l1.isValueNode()) {
                    String[] tuple = new String[]{l1.toString()};
                    super.forward(tuple);
                } else if (l1.isArray()) {
                    for (JsonNode l2 : l1) {
                        String[] tuple = new String[]{l2.toString()};
                        super.forward(tuple);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws HiveException {}
}