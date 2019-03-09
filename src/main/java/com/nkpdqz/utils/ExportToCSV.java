package com.nkpdqz.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


//导出到.csv文件
public class ExportToCSV extends ExportTo {

    private static final int[] width = {2000,2000};

    public int exportTo(String filename, int start) throws SQLException, IOException {

        /*List<String[]> dataList = exportFrom.exportFrom(start, 10000000);
        int sum = exportCsv(filename, dataList, start);*/
        return exportFromList(filename,start);

        //List<String[]> dataList = new ArrayList<String[]>();
        /*ResultSet resultSet = exportFrom.exportFrom(start+1);
        return exportCsvFromRS(filename,resultSet);*/
        /*ResultSetMetaData metaData = resultSet.getMetaData();
        int index = 0;
        while (resultSet.next()){
            String[] line = new String[width.length];
            for (int i = 0; i < width.length; i++) {
                String columnLabel = metaData.getColumnLabel(i + 1);
            }
            dataList.add(line);
            index++;
        }
        exportCsv(filename,dataList,start);
        return index;*/
        //return sum;
    }

    public int exportFromList(String filename, int start) throws IOException {
        List<String[]> dataList = exportFrom.exportFrom(start, 10000000);
        int index=0;
        try {
            index = exportCsv(filename, dataList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return index;
    }

    //这里主要使用了commons-csv这个工具包里的一些工具类，可以让我们把数据导出到.csv文件中
    public int exportCsv(String filename,List<String[]> dataList) throws SQLException, IOException {
        CSVFormat format = CSVFormat.DEFAULT.withRecordSeparator("\n");
        FileWriter fileWriter = new FileWriter(filename);
        CSVPrinter printer = new CSVPrinter(fileWriter,format);
        int index = 0;
        if (null!=dataList && !dataList.isEmpty()){
            for (String[] line:dataList){
                printer.printRecord(line);
                System.out.println(line[0]);
                index++;
            }
        }
        printer.flush();
        printer.close();
        return index;
    }

    public int exportCsvFromRS(String filename,ResultSet resultSet) throws SQLException, IOException {
        CSVFormat format = CSVFormat.DEFAULT.withRecordSeparator("\n");
        FileWriter fileWriter = new FileWriter(filename);
        CSVPrinter printer = new CSVPrinter(fileWriter,format);
        printer.printRecords(resultSet);
        printer.flush();
        printer.close();
        return 1;
    }
}
