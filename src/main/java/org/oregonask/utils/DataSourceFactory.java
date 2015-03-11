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
        	mySqlDataSource.setUrl("jdbc:mysql://us-cdbr-iron-east-01.cleardb.net/heroku_81301b928a7b951?reconnect=true");
        	mySqlDataSource.setUser("bb463ffa3c6c83");
        	mySqlDataSource.setPassword("ae14b11f");
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
