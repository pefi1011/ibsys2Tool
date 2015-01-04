package de.supplyTool.dao.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbHelper {

	public static boolean closeAll(Connection conn, ResultSet rs, Statement stmt) {
		return close(stmt) && close(rs) && close(conn);
	}
	
	public static boolean close(Statement stmt){
		try {
			if(stmt != null && !stmt.isClosed())
				stmt.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static  boolean close(Connection conn){
		try {
			if(conn != null && !conn.isClosed())
				conn.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean close(ResultSet rs){
		try {
			if(rs != null && !rs.isClosed())
				rs.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
