package com.nkpdqz.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

//不同的导出格式继承此抽象类，重写导出方法
public abstract class ExportTo {

    ExportFrom exportFrom;

    public void setExportTo(ExportFrom exportFrom) {
        this.exportFrom = exportFrom;
    }

    public abstract int exportTo(String filename,int start) throws SQLException, IOException;

    public int exportFromList(String filename,int start,int num) throws IOException{
        return 0;
    }

    public int exportFromList(String filename,int start) throws IOException{
        return 0;
    }
}
