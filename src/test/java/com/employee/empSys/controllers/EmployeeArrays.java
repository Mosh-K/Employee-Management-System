package com.employee.empSys.controllers;

import com.employee.model.Employee;

/**
 * This class provides arrays of valid and invalid employees for testing
 * purposes.
 */
public class EmployeeArrays {

	/**
	 * Represents an employee with a dynamically generated ID for testing purposes.
	 */
	private static final String ID_N = "33";

	/**
	 * Array of valid employees.
	 */
	public static final Employee[] validEmployees = {
			new Employee("1", "John Doe", "Engineer", 50000),
			new Employee("2", "Jane Doe", "Manager", 60000),
			new Employee("3", "Alice Smith", "Analyst", 40000),
			new Employee("4", "Bob Brown", "Developer", 70000),
			new Employee("5", "Charlie Brown", "Designer", 80000),
			new Employee("6", "David White", "Tester", 30000)
	};

	/**
	 * Array of invalid ID employees.
	 */
	public final static String[] invalidIdEmployees = {
			"{\"name\":\"Alice Smith\",\"position\":\"Analyst\",\"salary\":40000}",
			"{\"id\":\"\",\"name\":\"Alice Smith\",\"position\":\"Analyst\",\"salary\":40000}",
			"{\"id\":\"123456\",\"name\":\"Alice Smith\",\"position\":\"Analyst\",\"salary\":40000}"
	};

	/**
	 * Array of invalid name employees.
	 */
	public final static String[] invalidNameEmployees = {
			"{\"id\":\"" + ID_N + "\",\"position\":\"Analyst\",\"salary\":40000}",
			"{\"id\":\"" + ID_N + "\",\"name\":\"\",\"position\":\"Analyst\",\"salary\":40000}",
			"{\"id\":\"" + ID_N
					+ "\",\"name\":\"Alice Smith Alice Smith Alice Smith Alice Smith Alice Smith\",\"position\":\"Analyst\",\"salary\":40000}"
	};

	/**
	 * Array of invalid position employees.
	 */
	public final static String[] invalidPositionEmployees = {
			"{\"id\":\"" + ID_N + "\",\"name\":\"Alice Smith\",\"salary\":40000}",
			"{\"id\":\"" + ID_N + "\",\"name\":\"Alice Smith\",\"position\":\"\",\"salary\":40000}",
			"{\"id\":\"" + ID_N
					+ "\",\"name\":\"Alice Smith\",\"position\":\"Analyst Analyst Analyst Analyst Analyst\",\"salary\":40000}"
	};

	/**
	 * Array of invalid salary employees.
	 */
	public final static String[] invalidSalaryEmployees = {
			"{\"id\":\"" + ID_N + "\",\"name\":\"Alice Smith\",\"position\":\"Analyst\"}",
			"{\"id\":\"" + ID_N + "\",\"name\":\"Alice Smith\",\"position\":\"Analyst\",\"salary\":-40000}",
			"{\"id\":\"" + ID_N + "\",\"name\":\"Alice Smith\",\"position\":\"Analyst\",\"salary\":1000000}"
	};
}
