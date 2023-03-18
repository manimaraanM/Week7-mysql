package projects.dao;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import projects.entity.Project;
import projects.entity.category;
import projects.entity.material;
import projects.entity.step;
import projects.exception.DbException;


public class ProjectDao {

		private static final String CATEGORY_TABLE="category";
		private static final String MATERIAL_TABLE="material";
		private static final String PROJECT_TABLE="project";
		private static final String PROJECT_CATEGORY_TABLE="project_category";
		private static final String STEP_TABLE="step";
		
		
		public static boolean deleteProject(Integer project_id) {
			// TODO Auto-generated method stub
			String s="DELETE FROM " +PROJECT_TABLE +" where project_id=?";
			try(Connection conn=DbConnection.getConnection()){
				conn.setAutoCommit(false);
				try(PreparedStatement ps=conn.prepareStatement(s)){
					ps.setInt(1, project_id);
					ps.addBatch();
					int[] res=ps.executeBatch();
					boolean result=res[0]==1;
				
					conn.commit();
					return result;
					
				}catch(Exception e) {
					conn.rollback();
					throw new DbException(e);
				}
				
			}catch(Exception e) {
				throw new DbException(e);
			}
			
		}


		public static boolean modifyProjectDetail(Project project) {
			// TODO Auto-generated method stub
			
			String s="UPDATE " +PROJECT_TABLE 
					+" set project_name = ?" +" ,"
					+" estimated_hours =? " +" ,"
					+" actual_hours=? ,"
					+" difficulty=? ,"
					+" notes=? "
					+" where project_id=?";
			
			try(Connection conn=DbConnection.getConnection()){
				conn.setAutoCommit(false);
				try(PreparedStatement ps=conn.prepareStatement(s)){
					
					ps.setString(1, project.getProject_name());
					ps.setObject(2,project.getEstimated_hours());
					ps.setObject(3,project.getActual_hours());
					ps.setInt(4, project.getDifficulty());
					ps.setString(5, project.getNotes());
					
					ps.setInt(6, project.getProject_id());
					
					ps.addBatch();
					int[] result=ps.executeBatch();
					System.out.println("value is " +result[0]);
					boolean res=result[0]==1;
					
					conn.commit();
					return res;
					
				}catch(Exception e) {
					conn.rollback();
					throw new DbException(e);
				}
				
			}catch(Exception e) {
				throw new DbException(e);
			}
			
			
		}

		
		public static Optional<Project> fetchProjectById(int projectId) {
			String s="SELECT * FROM " +PROJECT_TABLE +" where project_id=?";
			
			try(Connection con=DbConnection.getConnection()){
				con.setAutoCommit(false);
				Project project=null;
				try(PreparedStatement ps=con.prepareStatement(s)){
					ps.setInt(1, projectId);
					try(ResultSet rs=ps.executeQuery()){
						while(rs.next()) {
							project=DbConnection.extract(rs,Project.class);
						}
					}
					
					
					if(Objects.nonNull(project)) {
					project.getMaterial().addAll(fetchMaterialByProjectId(con,projectId));
				 project.getStep().addAll(fetchStepByProjectId(con,projectId));
				 project.getCategory().addAll(fetchCategoryByProjectId(con,projectId));
					
					}
					con.commit();
					return Optional.ofNullable(project);
				}catch(Exception e){
					con.rollback();
					throw new DbException(e);
				}
				
			}catch(Exception e){
				throw new DbException(e);
			}
		
			
			
			
		}
		
		
		
		
		
		private static List<category> fetchCategoryByProjectId(Connection con, int projectId) throws SQLException {
			// TODO Auto-generated method stub
			String s =" SELECT c.* FROM " +CATEGORY_TABLE +" c "
					+" JOIN " +PROJECT_CATEGORY_TABLE +" pc "
					+" USING (category_id) "
					+" WHERE pc.project_id=?";
			List<category> category=new LinkedList<>();
			
			try(PreparedStatement ps=con.prepareStatement(s)){
				ps.setInt(1, projectId);
				try(ResultSet rs=ps.executeQuery()){
					while(rs.next()) {
						category.add(DbConnection.extract(rs,category.class));
					}
				}
			}
					
					
					
			return category;
		}





		private static List<step> fetchStepByProjectId(Connection con, int projectId) throws SQLException {
			// TODO Auto-generated method stub
			String s="SELECT s.* from "
					+STEP_TABLE +" s " 
					+ " JOIN " +PROJECT_TABLE +" p "
					+ " ON s.project_id=p.project_id"
					+" where s.project_id=?";
			List<step> step=new LinkedList<>();
			try(PreparedStatement ps=con.prepareStatement(s)){
				ps.setInt(1, projectId);
				try(ResultSet rs=ps.executeQuery()){
					while(rs.next()) {
					step.add(DbConnection.extract(rs,step.class));
					}
				}
			}
					
			return step;
		}





		private static List<material> fetchMaterialByProjectId(Connection con, int projectId) throws SQLException {
			// TODO Auto-generated method stub
			String s=""
					+"SELECT m.* FROM "
					+ MATERIAL_TABLE +" m " + " , " 
					+PROJECT_TABLE +" p "
					+" where m.project_id = p.project_id"
					+" and m.project_id=?";
			List<material> material=new LinkedList<>();
			
			try(PreparedStatement ps=con.prepareStatement(s)){
				ps.setInt(1, projectId);
				try(ResultSet rs=ps.executeQuery()){
					while(rs.next()) {
					material.add(DbConnection.extract(rs,material.class));
					}
				}
			}
			return material;
		}





		public static Project sqfetchProjectById(int projectId) {

			
			String s = ""
					+"select * from "
					+PROJECT_TABLE
					+" where project_id = ?";
			
			try(Connection conn = DbConnection.getConnection()){
				conn.setAutoCommit(false);
				
				try(PreparedStatement ps=conn.prepareStatement(s)){
					
					System.out.println("Value of project id is :" +projectId);			
					ps.setInt(1,projectId);
					Project project=new Project();
					
					try(ResultSet rs=ps.executeQuery(s)){
						while(rs.next()) {
							
							project=DbConnection.extract(rs,Project.class);
						}
					}
					conn.commit();
					return project;
				
				}catch(Exception e) {
					conn.rollback();
					throw new DbException(e);
				}
				
			}catch(Exception e) {
				throw new DbException(e);
			}
			
		}



		public static List<Project> fetchAllProjects() {
			String sql="SELECT * FROM " +PROJECT_TABLE 
					+" ORDER BY project_id";
			
			try(Connection conn=DbConnection.getConnection()){
				conn.setAutoCommit(false);
				try(PreparedStatement pre=conn.prepareStatement(sql)){
					List<Project> project = new LinkedList<>();
					try(ResultSet rs= pre.executeQuery(sql)){
						while(rs.next()) {
							project.add(DbConnection.extract(rs,Project.class));
							
						}
						
					}
					
					conn.commit();
					return project;
				}catch(Exception e){
					conn.rollback();
					throw new DbException(e);
				}
				
			}catch(Exception e) {
				throw new DbException(e);
			}
		
		}

		
		 
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
