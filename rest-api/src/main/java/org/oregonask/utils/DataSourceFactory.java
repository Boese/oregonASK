package org.oregonask.utils;

import javax.sql.DataSource;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.zaxxer.hikari.HikariDataSource;
 

public class DataSourceFactory {
	 
	public static DataSource getMySQLDataSource(Boolean local) {
		HikariDataSource dataSource = null;
		MysqlDataSource mySqlDataSource = new MysqlDataSource();
        
        // Local instance
        if(local) {
        	mySqlDataSource.setUrl("jdbc:mysql://localhost/OREGONASKDB");
        	mySqlDataSource.setUser("root");
        	mySqlDataSource.setPassword("");
        }
        
        // Heroku instance
        else {
        	mySqlDataSource.setUrl("jdbc:mysql://us-cdbr-iron-east-02.cleardb.net/heroku_a986a0c0bbeefbc?reconnect=true");
        	mySqlDataSource.setUser("baa8dbaf38d26d");
        	mySqlDataSource.setPassword("1775ae61");
        }
    	mySqlDataSource.setCachePrepStmts(true);
    	mySqlDataSource.setPrepStmtCacheSize(250);
    	mySqlDataSource.setPrepStmtCacheSqlLimit(2048);
    	mySqlDataSource.setUseServerPrepStmts(true);
    	
    	dataSource = new HikariDataSource();
		dataSource.setDataSource(mySqlDataSource);
	    dataSource.setMaximumPoolSize(20);
	    dataSource.setMinimumIdle(5);
	    dataSource.setConnectionTimeout(1000);
        return dataSource;
    }
}
