package com.nkpdqz.utils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

//导出到.xlsx中
public class ExportToXlsx extends ExportTo {

    private static final int[] width = {2000,2000};
    private volatile SXSSFWorkbook workbook;

    public ExportToXlsx() {
        workbook = new SXSSFWorkbook(10000);
    }

    public int exportFromList(String filename,int start,int num) throws IOException {
        List<String[]> datas = exportFrom.exportFrom(start, num);
        Sheet sheet = workbook.createSheet();
        int index = 0;
        if (null!=datas && !datas.isEmpty()){
            for (String[] line:datas){
                Row row = sheet.createRow(index);
                for (int i = 0; i < line.length; i++) {
                    row.createCell(i).setCellValue(line[i]);
                }
                index++;
            }
        }
        FileOutputStream fos = new FileOutputStream(filename);
        workbook.write(fos);
        fos.flush();
        fos.close();
        return index;
    }

    public int exportTo(String filename,int start) throws SQLException, IOException {

        ResultSet resultSet = exportFrom.exportFrom(start+1);
        ResultSetMetaData metaData = resultSet.getMetaData();
        Sheet sheet = workbook.createSheet();
        Row row = sheet.createRow(0);
        for (int i = 0; i < width.length; i++) {
            row.createCell(i).setCellValue(metaData.getColumnLabel(i+1));
        }
        int index = 1;
        while(resultSet.next()){
            row = sheet.createRow(index);
            for (int i = 0; i < width.length; i++) {
                row.createCell(i).setCellValue(resultSet.getString(i+1));
            }
            index++;
        }
        FileOutputStream fos = new FileOutputStream(filename);
        workbook.write(fos);
        fos.flush();
        fos.close();
        resultSet.close();
        return index;
    }
}
