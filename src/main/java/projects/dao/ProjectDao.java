package projects.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.Objects;

import projects.entity.Project;
import projects.exception.DbException;

public class ProjectDao {

		private static final String CATEGORY_TABLE="category";
		private static final String MATERIAL_TABLE="material";
		private static final String PROJECT_TABLE="project";
		private static final String PROJECT_CATEGORY_TABLE="project_category";
		private static final String STEP_TABLE="step";
		
		  

		
		
		 
	public static Project insertproject(Project project) {
		// TODO Auto-generated method stub
		
		String sql=""
				+ "INSERT into "
				+ PROJECT_TABLE +" "
				+ "(project_name,estimated_hours,actual_hours,difficulty,notes)"
				+ "values"
				+"(?,?,?,?,?)";
		
	
		try(Connection conn=DbConnection.getConnection()){
			System.out.println("Inserting 1");
			conn.setAutoCommit(false);
			
			try(PreparedStatement stmt= conn.prepareStatement(sql)){
				System.out.println("Inserting 2");
				
			    if (Objects.isNull(project.getProject_name())) {
				      stmt.setNull(1, Types.VARCHAR);
			    }
			    else {
				 stmt.setString(1, (String)project.getProject_name());
			    }
			    
			    if (Objects.isNull(project.getEstimated_hours())) {
				      stmt.setNull(2, Types.OTHER);
			    }else {
				 stmt.setObject(2, project.getEstimated_hours());
			    }
			    
			    if (Objects.isNull(project.getActual_hours())) {
				      stmt.setNull(3, Types.OTHER);
			    }else {
				 stmt.setObject(3, project.getActual_hours());
			    }
			    
			    if (Objects.isNull(project.getDifficulty())) {
				      stmt.setNull(4, Types.INTEGER);
			    }else {
				 stmt.setInt(4, (Integer)project.getDifficulty());
			    }
			    
			    if (Objects.isNull(project.getNotes())) {
				      stmt.setNull(5, Types.VARCHAR);
			    }else {
				 stmt.setString(5,(String) project.getNotes());
			    }
				 
				 System.out.println("Inserting 3");
				stmt.addBatch();
				System.out.println("Inserting 4");
				stmt.executeBatch();
			
				System.out.println("Inserting 5");
				int project_id=DbConnection.getLastInsertId(conn,PROJECT_TABLE);
				conn.commit();
				
				project.setProject_id(project_id);
				
				return project;
				
			}
			catch(Exception e) {
				conn.rollback();
				throw new DbException(e);
			}
			
			
		}
		catch(Exception e) {
			throw new DbException(e);
		}
		
	}

}
