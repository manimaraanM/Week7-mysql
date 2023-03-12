package projects;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import projects.entity.Project;
import projects.exception.DbException;
import projects.service.ProjectService;


public class ProjectsApp {

	
	//@formatter:off
	private  List<String> operations= List.of(
			"1. Add a project",
			"2. List project",
			"3. Select a Project ");
	//@formatter:on
	
	private Scanner scanner = new Scanner(System.in);
	private ProjectService projectservice=new ProjectService();
	private Project currProject = new Project();
	

	
	public static void main(String[] args) {
	new ProjectsApp().processUserSelections();

	}

	private void processUserSelections() {
		// TODO Auto-generated method stub
		boolean done=false;
		while(!done) {
			try{
				int selection=getUserSelection();
				switch(selection) {
				case -1:
					done=exitMenu();
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
				default:
					System.out.println("\n" + selection + " is not a valid selection. Try again.");
					break;
				}
				
			}
			catch(Exception e) {
				System.out.println(e.toString());
				e.printStackTrace();
			}
		}
		
	}

	private void selectProject() {
		// TODO Auto-generated method stub
		currProject=null;
		listProject();
		int projectId=getIntInput("Please enter the project id you would like to view");
		currProject=ProjectService.fetchProjectById(projectId);
		
		//Objects.isNull(currProject) ? System.out.println("Given project id is not available"):System.out.println(currProject);
		
		if(Objects.isNull(currProject)) {
			System.out.println("Given project id is not available");
		}
		else
		{
			System.out.println("\n Select project details: \n" +currProject);
		}
		
		
	}

	private void listProject() {
		// TODO Auto-generated method stub
List<Project> projectdisp=new LinkedList<>();
projectdisp=ProjectService.fecthAllProjects();
System.out.println("\n Projects: ");
projectdisp.forEach(projectdisplay->System.out.println(projectdisplay.getProject_id() 
		+" : " +projectdisplay.getProject_name()));
		
	}

	private void createProject() {
		// TODO Auto-generated method stub
		String projectName=getStringInput("Enter the project name");
		BigDecimal estimatedhours=getDecimalInput("Enter estimated hours");
		BigDecimal actualhours=getDecimalInput("Enter actual hours");
		Integer Difficulty= getIntInput("Enter Difficulty Level");
		String notes = getStringInput("Enter  project notes");
		
		Project project = new Project();
		project.setProject_name(projectName);
		project.setEstimated_hours(estimatedhours);
		project.setActual_hours(actualhours);
		project.setDifficulty(Difficulty);
		project.setNotes(notes);
		
		Project dbProject= ProjectService.addProject(project);
		System.out.println("You have succesfully created project" +dbProject);
		
		
		
		
	}
	
	
	private BigDecimal getDecimalInput(String string) {
		// TODO Auto-generated method stub
		String input=getStringInput(string);
		
		if(Objects.isNull(input)) 
			return null;
		try{
			
			return new BigDecimal(input).setScale(2);
			
		}catch(NumberFormatException e) {
			throw new DbException(input +" is not a valid number.Try again");
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
			Integer input=getIntInput("Enter a menu selection");
		return Objects.isNull(input)?-1:input;
	}

	private Integer getIntInput(String prompt) {
		// TODO Auto-generated method stub
		String input=getStringInput(prompt);
		
		if(Objects.isNull(input)) 
			return null;
		try{
			
			return Integer.parseInt(input);
			
		}catch(NumberFormatException e) {
			throw new DbException(input +" is not a valid number.Try again");
		}	
		
	}

	private String getStringInput(String prompt) {
		// TODO Auto-generated method stub
		System.out.print(prompt +":");
		String input=scanner.nextLine();
		return input.isBlank()?null:input;
	}

	private void printOperations() {
		// TODO Auto-generated method stub
		System.out.println("\n These are the available selections.Press the Enter key to quit");
		operations.forEach(op -> System.out.println(" " + op));
		
	}
	
		
		

}
