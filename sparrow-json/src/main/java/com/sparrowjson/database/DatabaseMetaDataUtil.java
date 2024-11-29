package com.sparrowjson.database;

/**
 * Description:
 * Company    : 上海黄豆网络科技有限公司
 *
 * @author : hll
 * Date       : 2024/11/8
 * Modify     : 修改日期          修改人员        修改说明          JIRA编号
 * v1.0.0       2024/11/8       hll    新增              1001
 ********************************************************************/


import com.sparrowjson.vo.ColumnVO;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseMetaDataUtil {
    public static List<ColumnVO> getCoulumns(String tableName) {
        String url = "jdbc:mysql://localhost:3306/test3"; // 数据库 URL
        String user = "root"; // 数据库用户名
        String password = "root"; // 数据库密码
        //String tableName = "your_table_name"; // 要查询的表名

        List<ColumnVO> columnList = new ArrayList<>();
        // 连接数据库并获取元数据
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            // 获取数据库元数据
            DatabaseMetaData metaData = conn.getMetaData();

            // 获取表的列信息
            ResultSet columns = metaData.getColumns(null, null, tableName,
                    null);


            System.out.println("字段信息：");
            while (columns.next()) {
                String columnName = columns.getString("COLUMN_NAME"); // 字段名
                String columnType = columns.getString("TYPE_NAME"); // 数据类型
                String columnSize = columns.getString("COLUMN_SIZE"); // 字段大小
                System.out.println("字段名: " + columnName + ", 类型: " + columnType + ", 大小: " + columnSize);


                // 尝试获取列的注释
                String columnComment = columns.getString("REMARKS");
                if (columnComment == null) {
                    // 如果没有通过元数据获取到注释，尝试通过 SQL 查询获取
                    columnComment = getColumnCommentFromSQL(conn, tableName, columnName);
                }

                System.out.println("字段名: " + columnName + ", 注释: " + columnComment);
                columnList.add(new ColumnVO(columnName, columnType, columnComment));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return columnList;
    }

    // 使用 SQL 查询获取列注释（适用于 MySQL）
    private static String getColumnCommentFromSQL(Connection conn, String tableName, String columnName) throws SQLException {
        String comment = null;
        String query = "SHOW FULL COLUMNS FROM " + tableName + " LIKE ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, columnName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    comment = rs.getString("Comment");
                }
            }
        }
        return comment != null ? comment : "无注释"; // 如果没有注释，返回 "无注释"
    }
}
