package projects;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.lang.Boolean;

import projects.entity.Project;
import projects.entity.material;
import projects.exception.DbException;
import projects.service.ProjectService;

public class ProjectsApp {

	//@formatter:off
	private  List<String> operations= List.of(
			"1. Add a project",
			"2. List project",
			"3. Select a Project ",
			"4. Update Project details",
			"5. Delete Project");
	//@formatter:on

	private Scanner scanner = new Scanner(System.in);
	private ProjectService projectservice = new ProjectService();
	private Project currProject = new Project();

	public static void main(String[] args) {
		new ProjectsApp().processUserSelections();

	}

	private void processUserSelections() {
		// TODO Auto-generated method stub
		boolean done = false;
		while (!done) {
			try {
				int selection = getUserSelection();
				switch (selection) {
				case -1:
					done = exitMenu();
					break;
				case 1:
					createProject();
					break;
				case 2:
					listProject();
					break;
				case 3:
					selectProject();
					break;
				case 4:
					updateProjectDetails();
					break;
				case 5:
					deleteProject();
					break;
				default:
					System.out.println("\n" + selection + " is not a valid selection. Try again.");
					break;
				}

			} catch (Exception e) {
				System.out.println(e.toString());
				e.printStackTrace();
			}
		}

	}

	private void deleteProject() {
		// TODO Auto-generated method stub
		listProject();
		Integer project_id=getIntInput("Enter project id to be deleted");
		ProjectService.deleteProject(project_id);
		if(currProject.getProject_id() == project_id) {
			currProject=null;
		}
		
		System.out.println("Projects after deletion");
		listProject();
	}

	private void updateProjectDetails() {
		// TODO Auto-generated method stub
		
		if(Objects.isNull(currProject)) {
			System.out.println("Please select a project");
		}else {
			Project project=new Project();
			Integer project_id=getIntInput("Enter project id [" +currProject.getProject_id() +"]");
			String project_name=getStringInput("Enter the project name [" +currProject.getProject_name() +"]");
			BigDecimal estimated_hours=getDecimalInput("Enter estimated hours [" +currProject.getEstimated_hours());
			BigDecimal actual_hours=getDecimalInput("Enter estimated hours [" +currProject.getActual_hours());
			Integer difficulty=getIntInput("Enter difficulty [" +currProject.getDifficulty() +"]");
			String notes=getStringInput("Enter  project notes [" +currProject.getNotes() +"]");
		
			
			project.setProject_id(Objects.isNull(project_id)?currProject.getProject_id():project_id);
			if(Objects.isNull(project_name)) {
				project.setProject_name(currProject.getProject_name());
			}else {
				project.setProject_name(project_name);
			}
		
		
			project.setEstimated_hours(Objects.isNull(estimated_hours)?currProject.getEstimated_hours():estimated_hours);	
			project.setActual_hours(Objects.isNull(actual_hours)?currProject.getActual_hours():actual_hours);
			project.setDifficulty(Objects.isNull(difficulty)?currProject.getDifficulty():difficulty);
			project.setNotes(Objects.isNull(notes)?currProject.getNotes():notes);
			
			projectservice.modifyProjectDetails(project);
			
			currProject=ProjectService.fetchProjectById(project.getProject_id());
			
		}	
	}

	private void selectProject() {
		// TODO Auto-generated method stub
		currProject = null;
		listProject();
		int projectId = getIntInput("Please enter the project id you would like to view");
		currProject = ProjectService.fetchProjectById(projectId);

		// Objects.isNull(currProject) ? System.out.println("Given project id is not
		// available"):System.out.println(currProject);

		if (Objects.isNull(currProject)) {
			System.out.println("Given project id is not available");
		} else {
			System.out.println("\n Select project details: \n" + currProject);
		}

	}

	private void listProject() {
		// TODO Auto-generated method stub
		List<Project> projectdisp = new LinkedList<>();
		projectdisp = ProjectService.fecthAllProjects();
		System.out.println("\n Projects: ");
		projectdisp.forEach(projectdisplay -> System.out
				.println(projectdisplay.getProject_id() + " : " + projectdisplay.getProject_name()));

	}

	private void createProject() {
		// TODO Auto-generated method stub
		String projectName = getStringInput("Enter the project name");
		BigDecimal estimatedhours = getDecimalInput("Enter estimated hours");
		BigDecimal actualhours = getDecimalInput("Enter actual hours");
		Integer Difficulty = getIntInput("Enter Difficulty Level");
		String notes = getStringInput("Enter  project notes");

		Project project = new Project();
		project.setProject_name(projectName);
		project.setEstimated_hours(estimatedhours);
		project.setActual_hours(actualhours);
		project.setDifficulty(Difficulty);
		project.setNotes(notes);

		Project dbProject = ProjectService.addProject(project);
		System.out.println("You have succesfully created project" + dbProject);

	}

	private BigDecimal getDecimalInput(String string) {
		// TODO Auto-generated method stub
		String input = getStringInput(string);

		if (Objects.isNull(input))
			return null;
		try {

			return new BigDecimal(input).setScale(2);

		} catch (NumberFormatException e) {
			throw new DbException(input + " is not a valid number.Try again");
		}
	}

	private boolean exitMenu() {
		// TODO Auto-generated method stub
		System.out.println("\n" + " Exiting Menu.");
		return true;
	}

	private int getUserSelection() {
		// TODO Auto-generated method stub

		printOperations();
		Integer input = getIntInput("Enter a menu selection");
		return Objects.isNull(input) ? -1 : input;
	}

	private Integer getIntInput(String prompt) {
		// TODO Auto-generated method stub
		String input = getStringInput(prompt);

		if (Objects.isNull(input))
			return null;
		try {

			return Integer.parseInt(input);

		} catch (NumberFormatException e) {
			throw new DbException(input + " is not a valid number.Try again");
		}

	}

	private String getStringInput(String prompt) {
		// TODO Auto-generated method stub
		System.out.print(prompt + ":");
		String input = scanner.nextLine();
		return input.isBlank() ? null : input;
	}

	private void printOperations() {
		// TODO Auto-generated method stub
		System.out.println("\n These are the available selections.Press the Enter key to quit");
		operations.forEach(op -> System.out.println(" " + op));

	}

}
