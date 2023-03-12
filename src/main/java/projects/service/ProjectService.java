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

	public static Project fetchProjectById(int projectId){
		// TODO Auto-generated method stub
		return ProjectDao.fetchProjectById(projectId).orElseThrow(
				() -> new NoSuchElementException("Project with projectId :" +projectId +" does not exist"));
	}

}
