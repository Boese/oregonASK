package org.oregonask.mysqlService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.oregonask.utils.DataSourceFactory;
import org.oregonask.utils.ReturnMessage;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;

public class DatabaseDriver {
	private DataSource dataSource;
    
	private JSONArray tables = new JSONArray();
	private JSONObject properties = new JSONObject();
	private JSONArray fks = new JSONArray();
	
	private ObjectMapper mapper = new ObjectMapper();
	
	public DatabaseDriver() {
		dataSource = DataSourceFactory.getMySQLDataSource(true);
		mapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		mapper.setSerializationInclusion(Include.NON_NULL);
		initialize();
	}
	
	// Initialize Database
	private void initialize() {
		// Initialize tables
		initializeTables();
		
		// Initialize each table properties
		for(int i = 0; i < tables.length(); i++) {
			initializeProperties(tables.getJSONObject(i).getString("TABLE_NAME"));
		}
		
		// Initialize FK's relations
		initializeFKs();
	}
	
	//********************* MYSQL 'CRUD' OPERATIONS OF DATABASE *********************//
	
	// GET AVAILABLE DATABASE TABLES
	public Object getTables() {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			con = dataSource.getConnection();
			JSONArray arr = new JSONArray();
			for(int i = 0; i < tables.length(); i++) {
				if(!tables.getJSONObject(i).getString("TABLE_NAME").equalsIgnoreCase("USER")) {
					arr.put(tables.getJSONObject(i).getString("TABLE_NAME"));
				}
			}
			return arr;
		} catch(Exception e) {
			return new JSONObject(new ReturnMessage("Couldn't load database tables"));
		} finally{
	       try {
	    	   if(rs != null) {rs.close();}
			   if(pst != null) {pst.close();}
			   if(con != null) {con.close();}
			} catch (SQLException e) {}
	    }
	}
	
	// GET PROPERTIES FOR TABLE WITH NESTED CHILDREN RECURSIVELY
	public Object getProperties(String tableName, JSONObject object) {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			con = dataSource.getConnection();
			// SET PROPERTIES
			for(int i = 0; i < JSONObject.getNames(properties).length; i++) {
				if(JSONObject.getNames(properties)[i].equalsIgnoreCase(tableName))
					tableName = JSONObject.getNames(properties)[i];
			}
			JSONArray arr = properties.getJSONArray(tableName);
			for(int i = 0; i < arr.length(); i++)
				object.put(arr.getJSONObject(i).getString("COLUMN_NAME"), "");
			
			// REMOVE UNNECESSARY FIELDS
			object.remove("LAST_EDIT_BY");
			object.remove("TIME_STAMP");
			object.remove("id");
			
			// GET CHILDREN TABLES, RECURSE TO INSERT NESTED CHILDREN PROPERTIES
			JSONArray children = getChildrenNames(tableName);
			for(int i = 0; i < children.length(); i++)
				object.put(children.getString(i), getProperties(children.getString(i), new JSONObject()));
			
			return object;
		} catch(Exception e) {
			e.printStackTrace();
			return new JSONObject(new ReturnMessage(tableName + " doesn't exist"));
		} finally{
		       try {
		    	   if(rs != null) {rs.close();}
				   if(pst != null) {pst.close();}
				   if(con != null) {con.close();}
				} catch (SQLException e) {}
		    }
	}
	
	// SEARCH FOR ANY DATA
	public Object search(Object object) {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			con = dataSource.getConnection();
			JSONObject obj = new JSONObject(object.toString());
			JSONArray tables = obj.optJSONArray("tables");
			JSONArray filters = obj.optJSONArray("filters");
			
			List<String> _tableNames = new ArrayList<String>();
			for(int i = 0; i < tables.length(); i++) {
				_tableNames.add(tables.getJSONObject(i).getString("name"));
			}
			
			List<String> tableSearch = new ArrayList<String>();
			List<String> filterSearch = new ArrayList<String>();
			
			String search = "SELECT ";
			
			for(int i = 0; i < tables.length(); i++) {
				JSONObject table = tables.getJSONObject(i);
				String tableName = table.getString("name");
				for(int j = 0; j < table.getJSONArray("columns").length(); j++) {
					String columnName = table.getJSONArray("columns").getString(j);
					tableSearch.add(tableName + "." + columnName + " AS '" + tableName.toUpperCase() + "." + columnName.toUpperCase() + "'");
				}
			}
			
			search += tableSearch;
			search += " FROM ";
			
			tableSearch = new ArrayList<String>();
			for(String s : _tableNames) {
				tableSearch.add(s);
			}
			
			search += tableSearch;
			search = search.replaceAll("\\[", "").replaceAll("\\]","");
			
			if(tables.length() > 1) {
				for(int i = 0; i < tables.length(); i++) {
					String filter = getFilter(_tableNames.get(i), _tableNames);
					if(!filter.equalsIgnoreCase(""))
						filterSearch.add(filter);
				}
			}
			
			if(filters != null) {
				for(int i = 0; i < filters.length(); i++) {
					filterSearch.add(filters.getString(i));
				}
			}
			
			if(filterSearch.size() > 0) {
				search += " WHERE ";
				for(String s : filterSearch) {
					search += s + " && ";
				}
				if(search.endsWith(" && "))
					search = search.substring(0, search.length() - 4);
			}
			
			search += ";";
			
			pst = con.prepareStatement(search);
			rs = pst.executeQuery();
			
			new ResultSetConverter();
			return ResultSetConverter.convert(rs,true);
		} catch(Exception e) {
			e.printStackTrace();
			return new JSONObject(new ReturnMessage("invalid search"));
		} finally{
		       try {
		    	   if(rs != null) {rs.close();}
				   if(pst != null) {pst.close();}
				   if(con != null) {con.close();}
				} catch (SQLException e) {}
		    }
	}
	
	// Get * IN TABLE
	public Object get(String tableName) {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try{
			con = dataSource.getConnection();
			String query = "Select * from " + tableName;
			pst = con.prepareStatement(query);
			rs = pst.executeQuery();
			new ResultSetConverter();
			return ResultSetConverter.convert(rs,false);
		} catch(Exception e) {
			e.printStackTrace();
			return new JSONObject(new ReturnMessage(tableName + " doesn't exist"));
		} finally{
		       try {
		    	   if(rs != null) {rs.close();}
				   if(pst != null) {pst.close();}
				   if(con != null) {con.close();}
				} catch (SQLException e) {}
		    }
	}
	
	// GET * WITH ID PLUS NESTED CHILDREN
	public Object getOne(String tableName, int id) {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try{
			con = dataSource.getConnection();
			String query = "Select * from " + tableName + " where id=?";
			pst = con.prepareStatement(query);
			pst.setInt(1, id);
			rs = pst.executeQuery();

			new ResultSetConverter();
			JSONObject object = ResultSetConverter.convert(rs,false).getJSONObject(0);


			// add One to Many relationships
			addChildren(object,tableName);


			// return object
			return object;

		} catch(Exception e) {
			e.printStackTrace();
			return new JSONObject(new ReturnMessage(tableName + " or record doesn't exist"));
		} finally{
		       try {
		    	   if(rs != null) {rs.close();}
				   if(pst != null) {pst.close();}
				   if(con != null) {con.close();}
				} catch (SQLException e) {}
		    }
	}
	
	// GET NESTED CHILDREN
	private void addChildren(JSONObject object, Object table) {
		String tableName = table.toString();
		
		for(int i = 0; i < fks.length(); i++) {
			if(fks.getJSONObject(i).getString("REFERENCED_TABLE_NAME").equalsIgnoreCase(tableName)) {
				String childTableName = fks.getJSONObject(i).getString("TABLE_NAME");
				String refColName = fks.getJSONObject(i).getString("COLUMN_NAME");
				String colName = fks.getJSONObject(i).getString("REFERENCED_COLUMN_NAME");
				
				int id = object.getInt(colName.toLowerCase());
				object.put(childTableName, getOneToMany(childTableName, id, refColName));
			}
		}
	}
	
	// RECURSIVELY GET CHILDRENS CHILDREN
	private Object getOneToMany(Object tableName, Object id, Object refColName) {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try{
			con = dataSource.getConnection();
			String query = "Select * from " + tableName + " where " + refColName + "=?";
			pst = con.prepareStatement(query);
			pst.setObject(1, id);
			rs = pst.executeQuery();
			
			JSONArray arr = ResultSetConverter.convert(rs,false);
			
			// add One to Many relationships
			for(int i = 0; i < arr.length(); i++) {
				addChildren(arr.getJSONObject(i),tableName);
			}
			
			return arr;
		} catch(Exception e) {
			e.printStackTrace();
			return new JSONObject(new ReturnMessage("failed"));
		} finally{
		       try {
		    	   if(rs != null) {rs.close();}
				   if(pst != null) {pst.close();}
				   if(con != null) {con.close();}
				} catch (SQLException e) {}
		    }
	}
	
	// CREATE OR UPDATE * TABLE
	public Object post(Object object, Object table) {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Map<String,JSONArray> children = new HashMap<String,JSONArray>();
		
		JSONObject obj = new JSONObject(object.toString());
		String tableName = table.toString();
		
		List<Object> columns = new ArrayList<Object>();
		
		for(Object s : JSONObject.getNames(obj)) {
			JSONArray arr = obj.optJSONArray(s.toString());
			if(arr != null && arr.length() > 0) {
				children.put(s.toString(), arr);
				obj.remove(s.toString());
			}
			else {
				columns.add(s);
			}
		}
		
		String insert = "INSERT INTO " + tableName  + " SET";
		for(int i = 0; i < columns.size(); i++) {
			insert += " " + columns.get(i) + " = ?,";
		}
		
		if(insert.endsWith(","))
			insert = insert.substring(0,insert.length() - 1);
		
		insert += " ON DUPLICATE KEY UPDATE";
		for(int i = 0; i < columns.size(); i++) {
			insert += " " + columns.get(i) + " = VALUES(" + columns.get(i) + "),";
		}
		
		if(insert.endsWith(","))
			insert = insert.substring(0,insert.length() - 1);
		
		try {
			con = dataSource.getConnection();
			pst = con.prepareStatement(insert,PreparedStatement.RETURN_GENERATED_KEYS);
			for(int i = 1; i < columns.size()+1; i++) {
				pst.setObject(i, obj.get(columns.get(i-1).toString()));
			}
			pst.executeUpdate();
			rs = pst.getGeneratedKeys();
			int parentId = 0;
			if (rs.next()){
				parentId = rs.getInt(1);
			}
			
			for(Map.Entry<String, JSONArray> child : children.entrySet()) {
				postChildren(child.getValue(), tableName, child.getKey(), parentId);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return new JSONObject(new ReturnMessage("failed"));
		} finally{
		       try {
		    	   if(rs != null) {rs.close();}
				   if(pst != null) {pst.close();}
				   if(con != null) {con.close();}
				} catch (SQLException e) {}
		    }
		return new JSONObject(new ReturnMessage("success"));
	}
	
	// RECURSIVELY CREATE OR UPDATE NESTED CHILDREN
	private void postChildren(JSONArray arr, String parentTable, String childTable, int parentId) {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Map<String,JSONArray> children = new HashMap<String,JSONArray>();
		
		for(int i = 0; i < arr.length(); i++) {
			JSONObject obj = arr.getJSONObject(i);
			obj.put(parentTable + "_id", parentId);
			
			List<Object> columns = new ArrayList<Object>();
			
			for(Object s : JSONObject.getNames(obj)) {
				JSONArray arr2 = obj.optJSONArray(s.toString());
				if(arr2 != null && arr2.length() > 0) {
					children.put(s.toString(), arr2);
					obj.remove(s.toString());
				}
				else {
					columns.add(s);
				}
			}
			
			String insert = "INSERT INTO " + childTable + " SET";
			
			for(int j = 0; j < columns.size(); j++) {
				insert += " " + columns.get(j) + " = ?,";
			}
			
			if(insert.endsWith(","))
				insert = insert.substring(0,insert.length() - 1);
			
			insert += " ON DUPLICATE KEY UPDATE";
			for(int j = 0; j < columns.size(); j++) {
				insert += " " + columns.get(j) + " = VALUES(" + columns.get(j) + "),";
			}
			
			if(insert.endsWith(","))
				insert = insert.substring(0,insert.length() - 1);
			
			try {
				con = dataSource.getConnection();
				pst = con.prepareStatement(insert,PreparedStatement.RETURN_GENERATED_KEYS);
				for(int j = 1; j < columns.size()+1; j++) {
					pst.setObject(j, obj.get(columns.get(j-1).toString()));
				}
				pst.executeUpdate();
				rs = pst.getGeneratedKeys();
				int childParentId = 0;
				if (rs.next()){
					childParentId = rs.getInt(1);
				}
				for(Map.Entry<String, JSONArray> child : children.entrySet()) {
					postChildren(child.getValue(), childTable, child.getKey(), childParentId);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally{
			       try {
			    	   if(rs != null) {rs.close();}
					   if(pst != null) {pst.close();}
					   if(con != null) {con.close();}
					} catch (SQLException e) {}
			    }
		}
	}
	
	// * DELETE TABLE W/ID
	public Object delete(int id, Object table) {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			con = dataSource.getConnection();
			String tableName = table.toString();
			String delete = "DELETE FROM " + tableName + " WHERE ID = ?";
			pst = con.prepareStatement(delete);
			pst.setInt(1, id);
	        pst.executeUpdate();
	        return new JSONObject(new ReturnMessage("success"));
		} catch(Exception e) {
			e.printStackTrace();
			return new JSONObject(new ReturnMessage("failed"));
		} finally{
		       try {
		    	   if(rs != null) {rs.close();}
				   if(pst != null) {pst.close();}
				   if(con != null) {con.close();}
				} catch (SQLException e) {}
		    }
	}
	
	//********************* MYSQL 'CRUD' OPERATIONS OF NEW TABLES *********************//
	
	// ADD NEW TABLE TO DATABASE
	public Object addTable(Object object) {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			con = dataSource.getConnection();
			JSONObject o = new JSONObject(object.toString());
			String createTable = "CREATE TABLE " + o.getString("name") + " (id int(11) NOT NULL AUTO_INCREMENT,";
			
			// COLUMNS ( for now varchar(500) )
			if(o.optJSONArray("columns") != null) {
				for(int i = 0; i < o.getJSONArray("columns").length(); i++) {
					createTable += o.getJSONArray("columns").getString(i) + " varchar(500),";
				}
			}
			
			// FORIEGN KEY TO PARENTS
			if(o.optJSONArray("parents") != null) {
				for(int i = 0; i < o.getJSONArray("parents").length(); i++) {
					createTable += o.getJSONArray("parents").getString(i) + "_id" + " int DEFAULT NULL,";
					createTable += "CONSTRAINT FK_" + o.getString("name") + "_" + o.getJSONArray("parents").getString(i)
								+ " FOREIGN KEY (" + o.getJSONArray("parents").getString(i) + "_id)"
								+ " REFERENCES " + o.getJSONArray("parents").getString(i) + " (id) ON DELETE SET NULL ON UPDATE NO ACTION,";
				}
			}
			
			createTable += "PRIMARY KEY (id) );";
			
			pst = con.prepareStatement(createTable);
			pst.executeUpdate();
			initialize();
			return new JSONObject(new ReturnMessage("success"));
		} catch (SQLException e) {
			e.printStackTrace();
			return new JSONObject(new ReturnMessage("failed"));
		} finally{
		       try {
		    	   if(rs != null) {rs.close();}
				   if(pst != null) {pst.close();}
				   if(con != null) {con.close();}
				} catch (SQLException e) {}
		    }
	}
	
	// ALTER EXISTING TABLE IN DATABASE
	public Object alterTable(Object object) {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			con = dataSource.getConnection();
			JSONObject o = new JSONObject(object.toString());
			String alterTable = "ALTER TABLE " + o.getString("name");
			
			// ADD COLUMNS ( for now varchar(500) )
			if(o.optJSONArray("add") != null) {
				for(int i = 0; i < o.getJSONArray("add").length(); i++) {
					alterTable += " ADD " + o.getJSONArray("add").getString(i) + " varchar(500),";
				}
			}
			
			// DROP COLUMNS ( for now varchar(500) )
			if(o.optJSONArray("drop") != null) {
				for(int i = 0; i < o.getJSONArray("drop").length(); i++) {
					alterTable += " DROP " + o.getJSONArray("drop").getString(i) + ",";
				}
			}
			
			// NEW FORIEGN KEY TO PARENTS
			if(o.optJSONArray("addParents") != null) {
				for(int i = 0; i < o.getJSONArray("addParents").length(); i++) {
					alterTable += " ADD " + o.getJSONArray("addParents").getString(i) + "_id" + " int DEFAULT NULL,";
					alterTable += " ADD CONSTRAINT FK_" + o.getString("name") + "_" + o.getJSONArray("addParents").getString(i)
								+ " FOREIGN KEY (" + o.getJSONArray("addParents").getString(i) + "_id)"
								+ " REFERENCES " + o.getJSONArray("addParents").getString(i) + " (id) ON DELETE SET NULL ON UPDATE NO ACTION,";
				}
			}
			
			// DROP FORIEGN KEYS | ALTER TABLE assignmentStuff DROP FOREIGN KEY assignmentIDX;
			if(o.optJSONArray("dropParents") != null) {
				for(int i = 0; i < o.getJSONArray("dropParents").length(); i++) {
					alterTable += " DROP FOREIGN KEY " + getConstraintKey(o.getString("name"), o.getJSONArray("dropParents").getString(i))  + ",";
					alterTable += " DROP " + o.getJSONArray("dropParents").getString(i) + "_id,";
				}
			}
			
			if(alterTable.endsWith(","))
			{
				alterTable = alterTable.substring(0,alterTable.length() - 1);
				alterTable += ";";
			}
			
			pst = con.prepareStatement(alterTable);
			pst.executeUpdate();
			initialize();
			return new JSONObject(new ReturnMessage("success"));
		} catch (SQLException e) {
			e.printStackTrace();
			return new JSONObject(new ReturnMessage("failed"));
		} finally{
		       try {
		    	   if(rs != null) {rs.close();}
				   if(pst != null) {pst.close();}
				   if(con != null) {con.close();}
				} catch (SQLException e) {}
		    }
	}
	
	// DELETE EXISTING TABLE IN DATABASE
	public Object deleteTable(Object table) {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			con = dataSource.getConnection();
			String deleteTable = "DROP TABLE " + table.toString();
			pst = con.prepareStatement(deleteTable);
			pst.executeUpdate();
			initialize();
			return new JSONObject(new ReturnMessage("success"));
		} catch (SQLException e) {
			e.printStackTrace();
			return new JSONObject(new ReturnMessage("failed"));
		} finally{
		       try {
		    	   if(rs != null) {rs.close();}
				   if(pst != null) {pst.close();}
				   if(con != null) {con.close();}
				} catch (SQLException e) {}
		    }
	}
	
	//********************* HELPER FUNCTIONS *********************//
	
	// GET CONTRAINT_NAME BASED ON TABLE AND REFERENCED TABLE, NEEDED TO REMOVE FK CONSTRAINT
	private String getConstraintKey(String tableName, String refTableName) {
		for(int i = 0; i < fks.length(); i++) {
			if(fks.getJSONObject(i).getString("TABLE_NAME").equalsIgnoreCase(tableName)  &&
					fks.getJSONObject(i).getString("REFERENCED_TABLE_NAME").equalsIgnoreCase(refTableName))
				return fks.getJSONObject(i).getString("CONSTRAINT_NAME");
		}
		return null;
	}
	
	// GET CHILDREN BASED ON REFERENCED PARENT TABLE NAME
	private JSONArray getChildrenNames(String tableName) {
		JSONArray arr = new JSONArray();
		for(int i = 0; i < fks.length(); i++) {
			if(fks.getJSONObject(i).getString("REFERENCED_TABLE_NAME").equalsIgnoreCase(tableName))
				arr.put(fks.getJSONObject(i).getString("TABLE_NAME"));
		}
		return arr;
	}
	
	// GET FILTER STRING BASED OFF CHILD TABLE AND SEARCH TABLES
	public String getFilter(String tableName, List<String> tables) {
		String filter = "";
		for(int i = 0; i < fks.length(); i++) {
			if(fks.getJSONObject(i).getString("TABLE_NAME").equalsIgnoreCase(tableName)) {
				for(int j = 0; j < tables.size(); j++) {
					if(tables.get(j).equalsIgnoreCase(fks.getJSONObject(i).getString("REFERENCED_TABLE_NAME"))) {
						filter = tableName + "." 
								+ fks.getJSONObject(i).getString("COLUMN_NAME") 
								+ "=" + fks.getJSONObject(i).getString("REFERENCED_TABLE_NAME") 
								+ "." + fks.getJSONObject(i).getString("REFERENCED_COLUMN_NAME");
						break;
					}
				}
			}
		}
		return filter;
	}
	
	//********************* INITIALIZATION OF DATABASE INFORMATION *********************//
	
	// SET UP TABLES
	private void initializeTables() {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			con = dataSource.getConnection();
			String query = "SHOW TABLES";
			pst = con.prepareStatement(query);
	        rs = pst.executeQuery();
	        
	        new ResultSetConverter();
			tables = ResultSetConverter.convert(rs,false);
		
		} catch(Exception e) {
			e.printStackTrace();
		} finally{
		       try {
		    	   if(rs != null) {rs.close();}
				   if(pst != null) {pst.close();}
				   if(con != null) {con.close();}
				} catch (SQLException e) {}
		    }
	}
	
	// ** SET UP PROPERTIES
	private void initializeProperties(String tableName) {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			con = dataSource.getConnection();
			String query = "DESC " + tableName;
			pst = con.prepareStatement(query);
	        rs = pst.executeQuery();
	        
	        new ResultSetConverter();
	        properties.put(tableName, ResultSetConverter.convert(rs,false));
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally{
		       try {
		    	   if(rs != null) {rs.close();}
				   if(pst != null) {pst.close();}
				   if(con != null) {con.close();}
				} catch (SQLException e) {}
		    }
	}
	
	// ** SET UP FOREIGN KEYS
	private void initializeFKs() {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			con = dataSource.getConnection();
			String query = "Select table_name,"
					+ "constraint_name,"
					+ "column_name,"
					+ "referenced_table_name,"
					+ "referenced_column_name "
					+ "from information_schema.key_column_usage where POSITION_IN_UNIQUE_CONSTRAINT = 1;";
			pst = con.prepareStatement(query);
	        rs = pst.executeQuery();
	        
	        new ResultSetConverter();
			fks = ResultSetConverter.convert(rs,false);
		} catch(Exception e) {
			e.printStackTrace();
		} finally{
		       try {
		    	   if(rs != null) {rs.close();}
				   if(pst != null) {pst.close();}
				   if(con != null) {con.close();}
				} catch (SQLException e) {}
		    }
	}
}
