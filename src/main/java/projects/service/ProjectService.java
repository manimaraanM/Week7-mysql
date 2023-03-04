package projects.service;

import projects.dao.ProjectDao;
import projects.entity.Project;

public class ProjectService {

	public static Project addProject(Project project) {
		// TODO Auto-generated method stub
		
		return ProjectDao.insertproject(project);
	}

}
