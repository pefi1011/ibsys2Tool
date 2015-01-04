package de.supplyTool.dao;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

public class ConnectionPool {

    private final static BasicDataSource dataSource;

    static {
        dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        // dataSource.setUrl("jdbc:mysql://h2271741.stratoserver.net/ibsys");
        dataSource.setUrl("jdbc:mysql://localhost/ibsys");
        dataSource.setUsername("root");
        dataSource.setPassword("");
        // dataSource.setUsername("ibsysuser");
        // dataSource.setPassword("hDZM7Mv51oSAVacApXe8");
        dataSource.setMaxActive(100);
        dataSource.setMaxWait(10000);
        dataSource.setMaxIdle(10);

    }

    public static DataSource getDataSource() {
        return dataSource;
    }
}
