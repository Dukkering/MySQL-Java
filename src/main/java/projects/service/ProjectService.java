package projects.service;

import projects.dao.ProjectDao;
import projects.entity.Project;

public class ProjectService {
/*
 * This class, the service layer, primarily acts as a pass-through layer between the main application of ProjectsApp.java and the ProjectDAO.java file, which is the data
 * layer. The primary function is to adjust the information taken in the ProjectsApp.java and adjusting it for proper entry into the DAO file to be applied correctly
 * into the MySQL Database.
 */
	private ProjectDao projectDao = new ProjectDao();
	
	public Project addProject(Project project) {
		return projectDao.insertProject(project);
	}

}
