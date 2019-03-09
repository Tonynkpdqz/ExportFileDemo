package com.nkpdqz.controller;

import com.nkpdqz.utils.*;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {


    public static void main(String[] args) {

        Properties properties = new Properties();

        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("database_excel.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String filename = properties.getProperty("filename");
        String className = properties.getProperty("exportto");
        if (className.equals("xlsx")){
            ExecutorService executorService = Executors.newFixedThreadPool(20);
            for (int i = 0; i < 20; i++) {
                executorService.execute(new CoreJob(i*500000,filename+i+".xlsx"));
            }
            executorService.shutdown();
        }else if (className.equals("csv")){
            ExportFrom exportFromSQL = new ExportFromSql();
            ExportTo csv = new ExportToCSV();
            csv.setExportTo(exportFromSQL);
            int total = 0;
            try {
                total = csv.exportTo(filename,0);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("导出了"+(total-1)+"条数据");
        }


        /*Properties properties = new Properties();
        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("database_excel.properties"));
            String filename = properties.getProperty("filename");
            ExportFrom exportFromSQL = new ExportFromSql();
            ExportTo exportToExcel = new ExportToExcel();
            exportToExcel.setExportTo(exportFromSQL);
            int total = exportToExcel.exportTo(filename);
            System.out.println("一共导出了"+(total-1)+"条数据");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }*/


    }
}
