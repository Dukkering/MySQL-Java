package projects.service;

import java.util.List;
import java.util.NoSuchElementException;


import projects.dao.ProjectDao;
import projects.entity.Project;
import projects.exception.DbException;

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

	public List<Project> fetchAllProjects() {
		return projectDao.fetchAllProjects();
	}

	public Project fetchProjectById(Integer projectId) {
			return projectDao.fetchProjectById(projectId).orElseThrow(
					() -> new NoSuchElementException(
							"Project with project ID=" + projectId + " does not exist."));
	}

	public void modifyProjectDetails(Project project) {
		if(!projectDao.modifyProjectDetails(project)) {
			throw new DbException("Project with ID =" + project.getProjectId() + " does not exist.");
		}
		
	}

	public void deleteProject(Integer projectId) {
		if(!projectDao.deleteProject(projectId)) {
			throw new DbException("Project with ID=" + projectId + " does not exist.");
		}
		
	}

}
