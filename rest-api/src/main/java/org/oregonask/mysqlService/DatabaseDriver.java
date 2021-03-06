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
		dataSource = DataSourceFactory.getMySQLDataSource(false);
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
			return new JSONArray(arr.toString().toUpperCase());
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
	
	// GET PROPERTIES FOR TABLE USE RECURSIVE METHOD BELOW
	public Object getProperties(String tableName) {
		try {
			return new JSONObject(getProperties(tableName, new JSONArray()).get(0).toString().toUpperCase());
		} catch(Exception e) {
			return new JSONObject(new ReturnMessage(tableName + " doesn't exist"));
		}
	}
	
	// GET PROPERTIES FOR TABLE WITH NESTED CHILDREN RECURSIVELY
	private JSONArray getProperties(String tableName, JSONArray object) {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			con = dataSource.getConnection();
			JSONObject obj = new JSONObject();
			tableName = getTableName(tableName);
			JSONArray arr = properties.getJSONArray(tableName);
			for(int i = 0; i < arr.length(); i++)
				obj.put(arr.getJSONObject(i).getString("COLUMN_NAME"), "");
			
			// REMOVE UNNECESSARY FIELDS
			obj.remove("LAST_EDIT_BY");
			obj.remove("TIME_STAMP");
			obj.remove("id");
			obj.remove("ID");
			obj.remove("iD");
			obj.remove("Id");
			
			// GET CHILDREN TABLES, RECURSE TO INSERT NESTED CHILDREN PROPERTIES
			JSONArray children = getChildrenNames(tableName);
			for(int i = 0; i < children.length(); i++)
				obj.put(children.getString(i), getProperties(children.getString(i), new JSONArray()));
			object.put(obj);
			return object;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		} finally{
		       try {
		    	   if(rs != null) {rs.close();}
				   if(pst != null) {pst.close();}
				   if(con != null) {con.close();}
				} catch (SQLException e) {}
		    }
	}
	
	// SEARCH FOR ANY DATA
	/*
		 {
			"tables":[
	        	{
	            	"name":"school",
	                "columns":["name","middle","county"]
	            },
	            {
	            	"name":"program",
	                "columns":["name","county","city"]
	            }
	        ],
	        "filters":[
	          {
	            "name":"school.county",
	            "values":["Washington","marion"]
	          }
	        ]
		  }
	 */
	public Object search(Object object) {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			JSONObject obj = new JSONObject(object.toString());
			JSONArray tables = obj.optJSONArray("tables");
			JSONArray filters = obj.optJSONArray("filters");
			
			List<String> _tableNames = new ArrayList<String>();
			for(int i = 0; i < tables.length(); i++) {
				_tableNames.add(tables.getJSONObject(i).getString("name"));
			}
			
			List<String> tableSearch = new ArrayList<String>();
			List<JSONObject> filterSearch = new ArrayList<JSONObject>();
			List<String> joinSearch = new ArrayList<String>();
			
			String search = "SELECT ";
			
			for(int i = 0; i < tables.length(); i++) {
				JSONObject table = tables.getJSONObject(i);
				String tableName = table.getString("name");
				if(table.getJSONArray("columns").length() < 1)
					throw new Exception("invalid search");
				else {
					for(int j = 0; j < table.getJSONArray("columns").length(); j++) {
						String columnName = table.getJSONArray("columns").getString(j);
						tableSearch.add(tableName + "." + columnName + " AS '" + tableName.toUpperCase() + "." + columnName.toUpperCase() + "'");
					}
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
						joinSearch.add(filter);
				}
			}
			
			if(filters != null) {
				for(int i = 0; i < filters.length(); i++) {
					filterSearch.add(filters.getJSONObject(i));
				}
			}
			
			if(filterSearch.size() > 0) {
				search += " WHERE";
				for(JSONObject j : filterSearch) {
					search += "(";
					for(int i = 0; i < j.getJSONArray("values").length(); i++) {
						search += j.getString("name") + " LIKE ";
						if(i == j.getJSONArray("values").length() - 1)
							search += "'%" + j.getJSONArray("values").getString(i) + "%'";
						else
							search += "'%" + j.getJSONArray("values").getString(i) + "%'" + " OR ";
					}
					search += ") && ";
				}
				if(search.endsWith(" && "))
					search = search.substring(0, search.length() - 4);
			}
			
			if(joinSearch.size() > 0) {
				if(filterSearch.size() < 1)
					search += " WHERE ";
				else
					search += " && ";
				for(String join : joinSearch) {
					search += join + " && ";
				}
				if(search.endsWith(" && "))
					search = search.substring(0, search.length() - 4);
			}
			
			search += ";";
			con = dataSource.getConnection();
			pst = con.prepareStatement(search);
			rs = pst.executeQuery();
			
			new ResultSetConverter();
			return new JSONArray(ResultSetConverter.convert(rs,true).toString().toUpperCase());
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
			return new JSONArray(ResultSetConverter.convert(rs,false).toString().toUpperCase());
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
			return new JSONObject(object.toString().toUpperCase());

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
				
				int id = 0;
				for(int j = 0; j < object.names().length(); j++) {
					if(object.names().getString(j).equalsIgnoreCase(colName))
						id = object.getInt(object.names().getString(j));
				}
				if(id != 0)
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
	
	// UPLOAD JSON TO SAVE TO DATABASE
	public Object uploadData(Object object, Object table, String email) {
		try {
			JSONArray arr = new JSONArray(object.toString());
			
			for(int i = 0; i < arr.length(); i++) {
				buildInsert(arr.getJSONObject(i), table.toString(), email);
			}
			
			return new JSONObject(new ReturnMessage("success"));
		} catch(Exception e) {
			return new JSONObject(new ReturnMessage("failed"));
		}
	}
	
	private int insertData(JSONObject obj, String tableName, String email) {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int id = 0;
		
		try {
			obj.put("LAST_EDIT_BY", email);
			List<Object> columns = new ArrayList<Object>();
			
			for(Object s : JSONObject.getNames(obj)) {
				columns.add(s);
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
			
			insert = insert.toUpperCase();
			con = dataSource.getConnection();
			pst = con.prepareStatement(insert,PreparedStatement.RETURN_GENERATED_KEYS);
			for(int i = 1; i < columns.size()+1; i++) {
				if(getTypeFromField(tableName, columns.get(i-1).toString()) != null) {
					if(getTypeFromField(tableName, columns.get(i-1).toString()).contains("varchar"))
						pst.setString(i, obj.getString(columns.get(i-1).toString()).toUpperCase());
					else if(getTypeFromField(tableName, columns.get(i-1).toString()).contains("tiny")) {
						if(obj.get(columns.get(i-1).toString()).toString().trim().isEmpty()) {
							pst.setObject(i, null);
						} else {
							pst.setBoolean(i, obj.getBoolean(columns.get(i-1).toString()));
						}
					}
					else if (getTypeFromField(tableName, columns.get(i-1).toString()).contains("int"))
					{
						int val = 0;
						try {
							val = Integer.parseInt(obj.get(columns.get(i-1).toString()).toString());
						} catch(Exception e) {}
						if(val != 0)
							pst.setInt(i, obj.getInt(columns.get(i-1).toString()));
						else
							pst.setObject(i, null);
					}
					else
						pst.setObject(i, obj.get(columns.get(i-1).toString()));
				}
			}
			pst.executeUpdate();
			rs = pst.getGeneratedKeys();
			if (rs.next()){
				id = rs.getInt(1);
			} else {
				for(int i = 0; i < obj.names().length(); i++) {
					if(obj.names().getString(i).equalsIgnoreCase("id"))
						id = obj.getInt(obj.names().getString(i));
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		finally{
		       try {
		    	   if(rs != null) {rs.close();}
				   if(pst != null) {pst.close();}
				   if(con != null) {con.close();}
				} catch (SQLException e) {}
		    }
		
		return id;
	}
	
	private int getParentId(String query) {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int id = 0;
		
		try {
			con = dataSource.getConnection();
			pst = con.prepareStatement(query);
			rs = pst.executeQuery();
			JSONArray arr = ResultSetConverter.convert(rs,false);
			
			if(arr != null) {
				if(arr.length() > 0) {
					JSONObject temp = arr.getJSONObject(0);
					for(int i = 0; i < temp.names().length(); i++) {
						if(temp.names().getString(i).equalsIgnoreCase("id"))
							id = temp.getInt(temp.names().getString(i));
					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally{
		       try {
		    	   if(rs != null) {rs.close();}
				   if(pst != null) {pst.close();}
				   if(con != null) {con.close();}
				} catch (SQLException e) {}
		    }
		return id;
	}
	
	private void buildInsert(JSONObject obj, String tableName, String email) {
		try {
			JSONObject tableFields = new JSONObject();
			JSONObject parentFields = getParentFields(tableName);
			JSONObject childrenFields = new JSONObject();
			
			// SET UP OBJECTS
			JSONArray keys = obj.names();
			for(int i = 0; i < keys.length(); i++) {
				String table = keys.getString(i).substring(0, keys.getString(i).indexOf("."));
				String key = keys.getString(i).substring(keys.getString(i).indexOf(".") + 1, keys.getString(i).length());
				String val = obj.getString(keys.getString(i));
				
				if(containsString(parentFields.names(), table)) {
					if(val != null && val.length() > 0)
						parentFields.getJSONObject(table.toLowerCase()).put(key, obj.getString(keys.getString(i)));	
				}
				else if(!table.equalsIgnoreCase(tableName)) {
					if(childrenFields.optJSONObject(table) == null)
						childrenFields.put(table, new JSONObject());
					childrenFields.getJSONObject(table).put(key, val);
				}
				else {
					tableFields.put(key, val);
				}
					
			}
			
			// LOOKUP PARENT FIELDS AND SET FK_IDS
			if(parentFields.names() != null) {
				for(int i = 0; i < parentFields.names().length(); i++) {
					JSONObject temp = parentFields.getJSONObject(parentFields.names().getString(i));
						if(temp.names() != null) {
							String query = "SELECT ID FROM " + parentFields.names().getString(i) + " WHERE ";
							for(int j = 0; j < temp.names().length(); j++) {
								query += temp.names().getString(j) + " LIKE " + "\"%" + temp.getString(temp.names().getString(j)) + "%\"" + " && ";
							}
							if(query.endsWith(" && "))
								query = query.substring(0, query.length() - 4);
							query += " LIMIT 1;";
							
							int id = getParentId(query);
							if(id != 0) {
								tableFields.put(parentFields.names().getString(i) + "_ID", id);
							}
						}
				}
			}
			
			// INSERT INTO TABLE get last insert id
			int parentId = insertData(tableFields, tableName, email);
			
			// SET CHILD PARENT FKS to last insert id
			if(parentId != 0) {
				if(childrenFields.names() != null) {
					for(int i = 0; i < childrenFields.names().length(); i++) {
						childrenFields.getJSONObject(childrenFields.names().getString(i)).put(tableName + "_ID", parentId);
					}
				}
			}
			
			// INSERT INTO CHILD TABLES
			if(childrenFields.names() != null) {
				for(int i = 0; i < childrenFields.names().length(); i++) {
					insertData(childrenFields.getJSONObject(childrenFields.names().getString(i)), childrenFields.names().getString(i), email);
				}
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// CREATE OR UPDATE * TABLE
	public Object post(Object object, Object table, String email) {
		Map<String,JSONArray> children = new HashMap<String,JSONArray>();
		
		JSONObject obj = new JSONObject(object.toString());
		String tableName = table.toString();
		
		for(Object s : JSONObject.getNames(obj)) {
			JSONArray arr = obj.optJSONArray(s.toString());
			if(arr != null && arr.length() > 0) {
				children.put(s.toString(), arr);
				obj.remove(s.toString());
			}
		}
		
		// INSERT INTO TABLE get last insert id
		int parentId = insertData(obj, tableName, email);
		
		if(parentId != 0) {
			for(Map.Entry<String, JSONArray> child : children.entrySet()) {
				postChildren(child.getValue(), tableName, child.getKey(), parentId, email);
			}
		}
		return new JSONObject(new ReturnMessage("success"));
	}
	
	// RECURSIVELY CREATE OR UPDATE NESTED CHILDREN
	private void postChildren(JSONArray arr, String parentTable, String childTable, int parentId, String email) {
		Map<String,JSONArray> children = new HashMap<String,JSONArray>();
		
		for(int i = 0; i < arr.length(); i++) {
			JSONObject obj = arr.getJSONObject(i);
			obj.put(parentTable.toUpperCase() + "_ID", parentId);
			
			for(Object s : JSONObject.getNames(obj)) {
				JSONArray arr2 = obj.optJSONArray(s.toString());
				if(arr2 != null && arr2.length() > 0) {
					children.put(s.toString(), arr2);
					obj.remove(s.toString());
				}
			}
			
			// INSERT INTO TABLE get last insert id
			int childParentId = insertData(obj, childTable, email);
			
			if(childParentId != 0) {
				for(Map.Entry<String, JSONArray> child : children.entrySet()) {
					postChildren(child.getValue(), childTable, child.getKey(), childParentId, email);
				}
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
			if(o.optJSONArray("add") != null) {
				for(int i = 0; i < o.getJSONArray("add").length(); i++) {
					createTable += o.getJSONArray("add").getString(i) + " varchar(500),";
				}
			}
			
			createTable += "LAST_EDIT_BY varchar(100), ";
			createTable += "TIME_STAMP TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,"; 
			
			// FORIEGN KEY TO PARENTS
			if(o.optJSONArray("addParents") != null) {
				for(int i = 0; i < o.getJSONArray("addParents").length(); i++) {
					createTable += o.getJSONArray("addParents").getString(i) + "_id" + " int DEFAULT NULL,";
					createTable += "CONSTRAINT FK_" + o.getString("name") + "_" + o.getJSONArray("addParents").getString(i)
								+ " FOREIGN KEY (" + o.getJSONArray("addParents").getString(i) + "_id)"
								+ " REFERENCES " + o.getJSONArray("addParents").getString(i) + " (id) ON DELETE CASCADE ON UPDATE NO ACTION,";
				}
			}
			
			createTable += "PRIMARY KEY (id) );";
			createTable = createTable.toUpperCase();
			
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
			
			// RENAME COLUMNS
			if(o.optJSONArray("rename") != null) {
				for(int i = 0; i < o.getJSONArray("rename").length(); i++) {
					String oldCol = o.getJSONArray("rename").getJSONObject(i).getString("old");
					String newCol = o.getJSONArray("rename").getJSONObject(i).getString("new");
					alterTable += " CHANGE " + oldCol + " " + newCol + " " + getPropertyType(o.getString("name"), oldCol) + ","; 
				}
			}
			
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
								+ " REFERENCES " + o.getJSONArray("addParents").getString(i) + " (id) ON DELETE CASCADE ON UPDATE NO ACTION,";
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
			
			alterTable = alterTable.toUpperCase();
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
	
	// CHECK IF LIST OF STRINGS CONTAINS STRING OP
	private Boolean containsString(JSONArray strings, String op) {
		if(strings != null && op != null) {
			for(int i = 0; i < strings.length(); i++) {
				if(strings.getString(i).equalsIgnoreCase(op))
					return true;
			}
		}
		return false;
	}
	
	// GET PARENTS FK OBJECT BASED ON TABLE NAME
	private JSONObject getParentFields(String tableName) {
		JSONObject parentFields = new JSONObject();
		for(int i = 0; i < fks.length(); i++) {
			if(fks.getJSONObject(i).getString("TABLE_NAME").equalsIgnoreCase(tableName)) {
				parentFields.put(fks.getJSONObject(i).getString("REFERENCED_TABLE_NAME").toLowerCase(), new JSONObject());
			}
		}
		return parentFields;
	}
	
	// GET PROPERTY TYPE FROM PROPERTY NAME AND TABLE
	private String getPropertyType(String tableName, String property) {
		JSONArray arr = properties.getJSONArray(getTableName(tableName));
		for(int i = 0; i < arr.length(); i++) {
			JSONObject obj = arr.getJSONObject(i);
			if(obj.getString("COLUMN_NAME").equalsIgnoreCase(property))
				return obj.getString("COLUMN_TYPE");
		}
		return null;
	}
	
	// GET JAVA PROPERTY TYPE FROM PROPERTY NAME AND TABLE
	private String getTypeFromField(String tableName, String property) {
		JSONArray arr = properties.getJSONArray(getTableName(tableName));
		for(int i = 0; i < arr.length(); i++) {
			JSONObject obj = arr.getJSONObject(i);
			if(obj.getString("COLUMN_NAME").equalsIgnoreCase(property)) {
				return obj.getString("COLUMN_TYPE");
			}
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
	private String getFilter(String tableName, List<String> tables) {
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
	
	// GET TABLE FROM STRING
	private String getTableName(String table) {
		String tableName = "";
		for(int i = 0; i < JSONObject.getNames(properties).length; i++) {
			if(JSONObject.getNames(properties)[i].equalsIgnoreCase(table))
				tableName = JSONObject.getNames(properties)[i];
		}
		return tableName;
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
