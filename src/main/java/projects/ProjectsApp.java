package projects;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import projects.entity.Project;
import projects.exception.DbException;
import projects.service.ProjectService;

public class ProjectsApp {

	private Scanner scanner = new Scanner(System.in);
	private ProjectService projectService = new ProjectService();

	// @formatter:off
	private List<String> operations = List.of(
			"1) Add A Project");
	// @formatter:on

	public static void main(String[] args) {
		new ProjectsApp().processUserSelections();

	}
	/*
	 * Uses a switch in a try/catch block, takes user input to select the next method to engage. In case of a null input, which flags as -1, it exits the menu.
	 * In case of an invalid but not-null input, it will flag a string as an error, or will default an integer to a not-valid selection.
	 */
	private void processUserSelections() {
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

				default:
					System.out.println("\n" + selection + " is not a valid selection. Please try again.");
					break;
				}

			} catch (Exception e) {
				System.out.println("\nError: " + e + " Please Try Again.");
			}
		}
	}
	/*
	 * Takes user input for all the various lines adjusted to type
	 */
	private void createProject() {
		String projectName = getStringInput("Enter the project name");
		BigDecimal estimatedHours = getDecimalInput("Enter the estimated hours");
		BigDecimal actualHours = getDecimalInput("Enter the actual hours");
		Integer difficulty = getIntInput("Enter the project difficulty (1-5)");
		String notes = getStringInput("Enter the project notes");

		Project project = new Project();

		project.setProjectName(projectName);
		project.setEstimatedHours(estimatedHours);
		project.setActualHours(actualHours);
		project.setDifficulty(difficulty);
		project.setNotes(notes);

		Project dbProject = projectService.addProject(project);
		System.out.println("You have successfully created project:\n" + dbProject);
	}

	/*
	 * Converts string input to Big Decimal input with a scale of 2, or two places after the decimal, ie 0.12
	 */
	private BigDecimal getDecimalInput(String prompt) {
		String input = getStringInput(prompt);

		if (Objects.isNull(input)) {
			return null;
		}
		try {
			return new BigDecimal(input).setScale(2);
		} catch (NumberFormatException e) {
			throw new DbException(input + " is not a valid decimal number.");
		}
	}

	/*
	 * Exits the menu by returning a value of True, which ends the While loop that keeps the menu open
	 */
	private boolean exitMenu() {
		System.out.println("\nExiting the menu.");
		return true;
	}

	/*
	 * Takes the collected user input and either flags it as null and marks that as -1, or marks it as an input
	 */
	private int getUserSelection() {
		printOperations();

		Integer input = getIntInput("Enter a menu selection");

		return Objects.isNull(input) ? -1 : input;
	}

	/*
	 * Converts the User Input gathered from getStringInput method into a Integer if
	 * possible. Will attempt to catch errors to prevent system crash.
	 */
	private Integer getIntInput(String prompt) {
		String input = getStringInput(prompt);

		if (Objects.isNull(input)) {
			return null;
		}
		try {
			return Integer.valueOf(input);
		} catch (NumberFormatException e) {
			throw new DbException(input + " is not a valid number.");
		}
	}

	/*
	 * Lowest level input method; All other methods call this method to gather user
	 * Input
	 */
	private String getStringInput(String prompt) {
		System.out.println(prompt + ": ");
		String input = scanner.nextLine();

		return input.isBlank() ? null : input.trim();
	}

	private void printOperations() {
		System.out.println("\nThese are the available selections. Press the Enter key to quit:");
		operations.forEach(line -> System.out.println("   " + line));

	}

}
