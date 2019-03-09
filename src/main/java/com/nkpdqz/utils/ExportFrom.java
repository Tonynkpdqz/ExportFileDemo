package com.nkpdqz.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

//从什么数据源中获取数据，不同数据源实现此接口，从而实现各自的逻辑
public interface ExportFrom {

    ResultSet exportFrom(int start) throws SQLException;

    List<String[]> exportFrom(int start,int num);
}
