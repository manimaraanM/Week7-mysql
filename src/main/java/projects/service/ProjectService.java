package projects.service;

import java.util.List;
import java.util.NoSuchElementException;

import projects.dao.ProjectDao;
import projects.entity.Project;

public class ProjectService {

	public static Project addProject(Project project) {
		// TODO Auto-generated method stub

		return ProjectDao.insertproject(project);
	}

	public static List<Project> fecthAllProjects() {
		// TODO Auto-generated method stub

		return ProjectDao.fetchAllProjects();
	}

	public static Project fetchProjectById(int projectId) {
		// TODO Auto-generated method stub
		return ProjectDao.fetchProjectById(projectId).orElseThrow(
				() -> new NoSuchElementException("Project with projectId :" + projectId + " does not exist"));
	}

	public void modifyProjectDetails(Project project) {
	// TODO Auto-generated method stub
	 if(!ProjectDao.modifyProjectDetail(project)) {
		 System.out.println("Enter project id " +project.getProject_id() +" does not exist");
	 };
		
	}

	public static void deleteProject(Integer project_id) {
		// TODO Auto-generated method stub
		if(!ProjectDao.deleteProject(project_id)) {
		System.out.println("Entered Project id" +project_id +"does not exist");
		}
	}

}
