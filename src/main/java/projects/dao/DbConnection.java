package projects.dao;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

import projects.exception.DbException;


public class DbConnection {
	private static String HOST="localhost";
	private static String PASSWORD="projects";;
	private static int PORT=3306;
	private static String SCHEMA="projects";
	private static String USER="projects";;
		
	public static Connection getConnection() {
		String uri=String.format("jdbc:mysql://%s:%d/%s?user=%s&password=%s", HOST,PORT,SCHEMA,USER,PASSWORD);
		
		try{
			Connection con = DriverManager.getConnection(uri);
			System.out.println("Connection successful");
			return con;
		}
		catch(Exception e){
			System.out.println("Connection failure");
		throw new DbException(e);
		}
		
		
		
	}
	
	 public static Integer getLastInsertId(Connection conn, String table)
		      throws SQLException {
		    String sql = String.format("SELECT LAST_INSERT_ID() FROM %s", table);
		    try (Statement stmt = conn.createStatement()) {
				try (ResultSet rs = stmt.executeQuery(sql)) {
			        if (rs.next()) {
			        	return rs.getInt(1);
			        }
			        throw new SQLException(
				            "Unable to retrieve the primary key value. No result set!");
				      }
				    }
				  }
	 
	 
	  protected static <T> T extract(ResultSet rs, Class<T> classType) {
		    try {
		      /* Obtain the constructor and create an object of the correct type. */
		      Constructor<T> con = classType.getConstructor();
		      T obj = con.newInstance();

		      /* Get the list of fields and loop through them. */
		      for (Field field : classType.getDeclaredFields()) {
		        String colName = camelCaseToSnakeCase(field.getName());
		        Class<?> fieldType = field.getType();

		        /*
		         * Set the field accessible flag which means that we can populate even
		         * private fields without using the setter.
		         */
		        field.setAccessible(true);
		        Object fieldValue = null;

		        try {
		          fieldValue = rs.getObject(colName);
		        } catch (SQLException e) {
		          /*
		           * An exception caught here means that the field name isn't in the
		           * result set. Don't take any action.
		           */
		        }

		        /*
		         * Only set the value in the object if there is a value with the same
		         * name in the result set. This will preserve instance variables (like
		         * lists) that are assigned values when the object is created.
		         */
		        if (Objects.nonNull(fieldValue)) {
		          /*
		           * Convert the following types: Time -> LocalTime, and Timestamp ->
		           * LocalDateTime.
		           */
		          if (fieldValue instanceof Time && fieldType.equals(LocalTime.class)) {
		            fieldValue = ((Time) fieldValue).toLocalTime();
		          } else if (fieldValue instanceof Timestamp
		              && fieldType.equals(LocalDateTime.class)) {
		            fieldValue = ((Timestamp) fieldValue).toLocalDateTime();
		          }

		          field.set(obj, fieldValue);
		        }
		      }

		      return obj;

		    } catch (Exception e) {
		    	throw new DbException(e);
		    }
		  }

		  /**
		   * This converts a camel case value (rowInsertTime) to snake case
		   * (row_insert_time).
		   * 
		   * @param identifier The name in camel case to convert.
		   * @return The name converted to snake case.
		   */
		  private static String camelCaseToSnakeCase(String identifier) {
		    StringBuilder nameBuilder = new StringBuilder();

		    for (char ch : identifier.toCharArray()) {
		      if (Character.isUpperCase(ch)) {
		        nameBuilder.append('_').append(Character.toLowerCase(ch));
		      } else {
		        nameBuilder.append(ch);
		      }
		    }

		    return nameBuilder.toString();
		  }

		  protected static void setParameter(PreparedStatement stmt, int parameterIndex,
			      Object value, Class<?> classType) throws SQLException {
			    int sqlType = convertJavaClassToSqlType(classType);

			    if (Objects.isNull(value)) {
			      stmt.setNull(parameterIndex, sqlType);
			    } else {
			      switch (sqlType) {
			        case Types.DOUBLE:
			          stmt.setDouble(parameterIndex, (Double) value);
			          break;

			        case Types.INTEGER:
			          stmt.setInt(parameterIndex, (Integer) value);
			          break;

			        case Types.OTHER:
			          stmt.setObject(parameterIndex, value);
			          break;

			        case Types.VARCHAR:
			          stmt.setString(parameterIndex, (String) value);
			          break;

			        default:
			          throw new DbException("Unknown parameter type: " + classType);
			      }
			    }
			  }

		  private static int convertJavaClassToSqlType(Class<?> classType) {
			    if (Integer.class.equals(classType)) {
			      return Types.INTEGER;
			    }

			    if (String.class.equals(classType)) {
			      return Types.VARCHAR;
			    }

			    if (Double.class.equals(classType)) {
			      return Types.DOUBLE;
			    }

			    if (LocalTime.class.equals(classType)) {
			      return Types.OTHER;
			    }

			    throw new DbException("Unsupported class type: " + classType.getName());
			  }


}
