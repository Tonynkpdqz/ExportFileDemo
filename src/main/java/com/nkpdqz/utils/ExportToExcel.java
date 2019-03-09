package com.nkpdqz.utils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

//此类废弃，应使用ExportToXlsx类
@Deprecated
public class ExportToExcel extends ExportTo {

    private static final int[] width = {2000,2000};
    private HSSFWorkbook workbook;
    private HSSFSheet sheet;

    public ExportToExcel() {
        workbook = new HSSFWorkbook();
        sheet = workbook.createSheet("table");
        for (int i = 0; i < width.length; i++) {
            sheet.setColumnWidth(i,width[i]);
        }
    }

    public int exportTo(String filename,int start) throws SQLException, IOException {
        ResultSet resultSet = exportFrom.exportFrom(start);
        ResultSetMetaData metaData = resultSet.getMetaData();
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = null;
        for (int i = 0; i < width.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(metaData.getColumnLabel(i+1));
        }
        int index = 1;
        while(resultSet.next()){
            row = sheet.createRow(index);
            for (int i = 0; i < width.length; i++) {
                cell = row.createCell(i);
                cell.setCellValue(resultSet.getString(i+1));
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

    public int exportFromList(String filename, int start, int num) throws IOException {
        return 0;
    }
}
