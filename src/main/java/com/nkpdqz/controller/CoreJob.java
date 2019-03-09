package com.nkpdqz.controller;

import com.nkpdqz.utils.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

public class CoreJob implements Runnable{


    private int start;
    private String filename;
    public CoreJob() {

    }

    public CoreJob(int start,String filename) {
        this.start = start;
        this.filename = filename;
    }

    public void run() {
        doPart(start,filename);
    }

    public void doPart(int start,String filename) {
        //Properties properties = new Properties();
        try {
            //properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("database_excel.properties"));
            //String filename = properties.getProperty("filename");
            ExportFrom exportFromSQL = new ExportFromSql();
            ExportTo exportToXlsx = new ExportToXlsx();
            exportToXlsx.setExportTo(exportFromSQL);
            int total = exportToXlsx.exportFromList(filename,start,500000);
            System.out.println("一共导出了" + (total - 1) + "条数据");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
