package xyz.icefery.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

public interface HBaseTemplate {
    // create '${table}', {NAME => '${family}'}
    default void create(Connection connection, String table, String[] columnFamilies) throws IOException {
        Admin admin = connection.getAdmin();
        // Table Builder
        TableDescriptorBuilder tb = TableDescriptorBuilder.newBuilder(TableName.valueOf(table));
        for (String s : columnFamilies) {
            // Column Family Builder
            ColumnFamilyDescriptorBuilder cfb = ColumnFamilyDescriptorBuilder.newBuilder(Bytes.toBytes(s));
            // Column Family Builder ==> build()
            ColumnFamilyDescriptor cf = cfb.build();
            tb.setColumnFamily(cf);
        }
        // Table Builder ==> build()
        TableDescriptor t = tb.build();
        admin.createTable(t);
    }

    // exists '${table}'
    default boolean exists(Connection connection, String table) throws IOException {
        Admin admin = connection.getAdmin();
        return admin.tableExists(TableName.valueOf(table));
    }

    // put '${table}', '${rowKey}', '${columnFamily}:${column}', '${value}'
    default void put(Connection connection, String table, String rowKey, String columnFamily, String column, String value) throws IOException {
        Table t = connection.getTable(TableName.valueOf(table));
        Put put = new Put(Bytes.toBytes(rowKey)).addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(column), Bytes.toBytes(value));
        t.put(put);
        t.close();
    }

    // get '${table}', '${rowKey}', {COLUMN => '${columnFamily}:${column}'}
    default String get(Connection connection, String table, String rowKey, String columnFamily, String column) throws IOException {
        Table t = connection.getTable(TableName.valueOf(table));
        Get get = new Get(Bytes.toBytes(rowKey)).addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(column));
        Result result = t.get(get);
        return Arrays.stream(result.rawCells())
            .map(cell -> Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength()))
            .collect(Collectors.joining("\t"));
    }

    // scan '${table}'
    default List<String> scan(Connection connection, String table) throws IOException {
        Table t = connection.getTable(TableName.valueOf(table));
        Scan scan = new Scan();
        ResultScanner rs = t.getScanner(scan);
        List<String> list = new ArrayList<>();
        for (Result r : rs) {
            String row = Arrays.stream(r.rawCells())
                .map(cell -> {
                    String rowKey = Bytes.toString(cell.getRowArray(), cell.getRowOffset(), cell.getRowLength());
                    String columnFamily = Bytes.toString(cell.getFamilyArray(), cell.getFamilyOffset(), cell.getFamilyLength());
                    String column = Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength());
                    String value = Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
                    return rowKey + "|" + columnFamily + ":" + column + "|" + value;
                })
                .collect(Collectors.joining("\t"));
            list.add(row);
        }
        return list;
    }
}
