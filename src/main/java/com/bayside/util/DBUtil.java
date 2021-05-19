package com.bayside.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DBUtil {
	
    // 获得连接
    public static Connection getConnection(String url,String username,String password) throws SQLException {
    	try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return DriverManager.getConnection(url, username, password);
    }
    public static List<Map<String,Object>> getResultMap(String sql,Connection conn){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		PreparedStatement ps =null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			//ps.setFetchSize(20);
			//ps.setFetchDirection(ResultSet.FETCH_REVERSE);
			rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			// 取得结果集列数
            int columnCount = rsmd.getColumnCount();
         // 循环结果集
            while (rs.next()) {
            	Map<String,Object> map = new HashMap<String,Object>();
            	
            	 // 每循环一条将列名和列值存入Map
                for (int i = 1; i <=columnCount; i++) {
                	map.put(rsmd.getColumnLabel(i), rs.getObject(rsmd
                            .getColumnLabel(i)));
                }
                //System.out.println(map);
                list.add(map);
            }
           
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.close(rs, ps, conn);
		}
		return list;
	}
    // 释放连接
    public static void close(ResultSet rs, Statement st, Connection conn) {
        try {
            if (rs != null) {
                rs.close(); // 关闭结果集
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (st != null) {
                    st.close(); // 关闭Statement
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (conn != null) {
                        conn.close(); // 关闭连接
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }

        }

    }

}
