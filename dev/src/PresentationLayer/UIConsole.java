package PresentationLayer;

import Service_Layer.Union_Service_Controller;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UIConsole{
    private Union_Service_Controller hrService;
    private Scanner scanner;

    public UIConsole() throws Exception {
        hrService = Union_Service_Controller.getInstance();
        scanner = new Scanner(System.in);
    }

    public static void main(String[] args) throws Exception {
        UIConsole uiConsole = new UIConsole();
        uiConsole.start();
    }

    public void start() throws Exception {
        while (true) {
            try {
                System.out.println("Choose an option:");
                System.out.println("1. login");
                System.out.println("2. Exit");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        login();
                        break;
                    case 2:
                        System.out.println("Exiting...");
                        return;
                    default:
                }

            }catch (InputMismatchException e) {
                System.out.println("Invalid input.");
                scanner.nextLine();
            }catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
    }
    private void EmployeeNoHRMenu(String loggedInEmployeeId) throws Exception {
        while (true) {
            try {

                System.out.println("Choose an option:");
                System.out.println("1. Show my employee's details");
                System.out.println("2. Show my future shifts");
                System.out.println("3. Add Constraint to shift");
                System.out.println("4. Remove Constraint from shift");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        showDetailsOnEmployee_EmployeeMenu(loggedInEmployeeId);
                        break;
                    case 2:
                        showEmployeeFutureShifts_EmployeeMenu(loggedInEmployeeId);
                        break;
                    case 3:
                        addConstraintToEmployee_EmployeeMenu(loggedInEmployeeId);
                        break;
                    case 4:
                        deleteConstraintToEmployee_EmployeeMenu(loggedInEmployeeId);
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }catch (InputMismatchException e) {
                System.out.println("Invalid input.");
                scanner.nextLine();
            }catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
    }
    private void HRMenu() throws Exception {
        while (true) {
            try {
                System.out.println("Choose a category:");
                System.out.println("1. Branch Management");
                System.out.println("2. Employee Management");
                System.out.println("3. Shift Management");
                System.out.println("4. Exit");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        branchMenu();
                        break;
                    case 2:
                        employeeMenu();
                        break;
                    case 3:
                        shiftMenu();
                        break;
                    case 4:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }catch (InputMismatchException e) {
                System.out.println("Invalid input.");
                scanner.nextLine();
            }catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
    }
    private void branchMenu() throws Exception {
        while (true) {
            try {
                System.out.println("Branch Management:");
                System.out.println("1. Show All Branches");
                System.out.println("2. Add Branch");
                System.out.println("3. Remove Branch");
                System.out.println("4. Back to Main Menu");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        showAllBranches();
                        break;
                    case 2:
                        addBranch();
                        break;
                    case 3:
                        removeBranch();
                        break;
                    case 4:
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }catch (InputMismatchException e) {
                System.out.println("Invalid input.");
                scanner.nextLine();
            }catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }

        }
    }

    private void employeeMenu() throws Exception {
        while (true) {
            try {
                System.out.println("Employee Management:");
                System.out.println("1. Show Employees");
                System.out.println("2. Show the employee's details");
                System.out.println("3. Show Employee Future Shifts");
                System.out.println("4. Add Employee");
                System.out.println("5. Remove Employee");
                System.out.println("6. Add Role to Employee");
                System.out.println("7. Remove Role from Employee");
                System.out.println("8. Add Constraint to Employee");
                System.out.println("9. Remove Constraint to Employee");
                System.out.println("10. Add driver");
                System.out.println("11. Change details of employee");
                System.out.println("12. Back to Main Menu");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        showEmployees();
                        break;
                    case 2:
                        showDetailsOnEmployee();
                        break;
                    case 3:
                        showEmployeeFutureShifts();
                        break;
                    case 4:
                        registerEmployee();
                        break;
                    case 5:
                        removeEmployee();
                        break;
                    case 6:
                        addRoleToEmployee();
                        break;
                    case 7:
                        removeRoleFromEmployee();
                        break;
                    case 8:
                        addConstraintToEmployee();
                        break;
                    case 9:
                        deleteConstraintToEmployee();
                        break;
                    case 10:
                        registerDriver();
                        break;
                    case 11:
                        changeDitailsOfEmployee();
                    case 12:
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }catch (InputMismatchException e) {
                System.out.println("Invalid input.");
                scanner.nextLine();
            }catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
    }

    private void shiftMenu() throws Exception {
        while (true) {
            try {
                System.out.println("Shift Management:");
                System.out.println("1. Show All Shifts");
                System.out.println("2. Show Employees Chosen for Shift");
                System.out.println("3. Show available employees for specific shift");
                System.out.println("4. Show all constrains to specific shift");
                System.out.println("5. Show employee needed for specific Shift");
                System.out.println("6. Show Employee Assignments for specific shift");
                System.out.println("7. Add Shift");
                System.out.println("8. Remove Shift");
                System.out.println("9. Choose Employees for Shift");
                System.out.println("10. Change Role of Employee in Shift");
                System.out.println("11. Delete Employee from Shift");
                System.out.println("12. Show available employees for specific shift by role");
                System.out.println("13. Back to Main Menu");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        showAllShifts();
                        break;
                    case 2:
                        showEmployeesChosenForShift();
                        break;
                    case 3:
                        returnAvailableEmployeesNames();
                        break;
                    case 4:
                        showConstraintsForShift();
                        break;
                    case 5:
                        showNumberOfEmployeesNeededForShift();
                        break;
                    case 6:
                        showEmployeeAssignmentsForShift();
                        break;
                    case 7:
                        addShift();
                        break;
                    case 8:
                        removeShift();
                        break;
                    case 9:
                        chooseEmployeesForShift();
                        break;
                    case 10:
                        changeRoleToEmployeeInShift();
                        break;
                    case 11:
                        DeleteEmployeeFromShift();
                        break;
                    case 12:
                        showEmployeeAssignmentsByRoleForShift();
                        break;
                    case 13:
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }

            }catch (InputMismatchException e) {
                System.out.println("Invalid input.");
                scanner.nextLine();
            }catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
    }

    private void changeDitailsOfEmployee() throws Exception {
        while (true) {
            try {
                System.out.println("The employee is:");
                System.out.println("1. Cashier / General employee / Storekeeper / Shift manager");
                System.out.println("2. Driver");
                System.out.println("3. Back to Main Menu");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        changeDitailsOfBranchEmployee();
                        break;
                    case 2:
                        changeDitailsOfDriver();
                        break;
                    case 3:
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }

            }catch (InputMismatchException e) {
                System.out.println("Invalid input.");
                scanner.nextLine();
            }catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
    }
    private void changeDitailsOfBranchEmployee() throws Exception {
        while (true) {
            try {

                System.out.println("Which of the following employee details would you want to change?");
                System.out.println("1.First name");
                System.out.println("2. Last name");
                System.out.println("3. Password");
                System.out.println("4. Account number");
                System.out.println("5. Branch bank number");
                System.out.println("6. Salary");
                System.out.println("7. Terms of employment");
                System.out.println("8. Is HR Manager");
                System.out.println("9. Is shift Manager");
                System.out.println("10. Is shift Manager");
                System.out.println("11. Branch address");
                System.out.println("12. Vacation days");
                System.out.println("13. Cancelations");
                System.out.println("14. Return to the previous menu");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        setFirstName();
                        start();
                        break;
                    case 2:
                        setLastName();
                        start();
                        break;
                    case 3:
                        setPassword();
                        start();
                        break;
                    case 4:
                        setAccountNumber();
                        start();
                        break;
                    case 5:
                        setBranchBankNumber();
                        start();
                        break;
                    case 6:
                        setSalary();
                        start();
                        break;
                    case 7:
                        setTermsOfEmployment();
                        start();
                        break;
                    case 8:
                        setHRManager();
                        start();
                        break;
                    case 9:
                        setShiftManager();
                        start();
                        break;
                    case 10:
                        setJobType();
                        start();
                        break;
                    case 11:
                        setBranchAddress();
                        start();
                        break;
                    case 12:
                        setVacationDays();
                        start();
                        break;
                    case 13:
                        setCancellations();
                        start();
                        break;
                    case 14:
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }catch (InputMismatchException e) {
                System.out.println("Invalid input.");
                scanner.nextLine();
            }catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
    }


    private void changeDitailsOfDriver() throws Exception {
        while (true) {
            try {
                System.out.println("Which of the following driver details would you want to change?");
                System.out.println("1.First name");
                System.out.println("2. Last name");
                System.out.println("3. Password");
                System.out.println("4. Account number");
                System.out.println("5. Branch bank number");
                System.out.println("6. Salary");
                System.out.println("7. Terms of employment");
                System.out.println("8. Is HR Manager");
                System.out.println("9. Is shift Manager");
                System.out.println("10. Is shift Manager");
                System.out.println("11. Branch address");
                System.out.println("12. Vacation days");
                System.out.println("13. Licenses");
                System.out.println("14. Return to the previous menu");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        setFirstName();
                        start();
                        break;
                    case 2:
                        setLastName();
                        start();
                        break;
                    case 3:
                        setPassword();
                        start();
                        break;
                    case 4:
                        setAccountNumber();
                        start();
                        break;
                    case 5:
                        setBranchBankNumber();
                        start();
                        break;
                    case 6:
                        setSalary();
                        start();
                        break;
                    case 7:
                        setTermsOfEmployment();
                        start();
                        break;
                    case 8:
                        setHRManager();
                        start();
                        break;
                    case 9:
                        setShiftManager();
                        start();
                        break;
                    case 10:
                        setJobType();
                        start();
                        break;
                    case 11:
                        setBranchAddress();
                        start();
                        break;
                    case 12:
                        setVacationDays();
                        start();
                        break;
                    case 13:
//                        setLicense();
                        start();
                        break;
                    case 14:
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }

            }catch (InputMismatchException e) {
                System.out.println("Invalid input.");
                scanner.nextLine();
            }catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
    }

    private void showEmployeeAssignmentsByRoleForShift() {
        try {
            System.out.print("Enter shift ID: ");
            int shiftId = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Enter role: ");
            String role = scanner.nextLine();

            String result = hrService.getShiftService().showEmployeeAssignmentsByRoleForShift(shiftId, role);
            System.out.println("Employee Assignments for Shift " + shiftId + ":\n" + result);
        }catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    private void returnAvailableEmployeesNames() {
        try {
            System.out.print("Enter shift ID: ");
            int shiftId = scanner.nextInt();
            scanner.nextLine();

            String result = hrService.getShiftService().availableEmployeesIdNotScheduledForShift(shiftId);
            System.out.println("Available employees for Shift " + shiftId + ":" + "\n" +result);

        }catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.nextLine();
        }catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private void showDetailsOnEmployee() {
        try {
            System.out.print("Enter employee ID: ");
            String employeeId = scanner.nextLine();

            hrService.getEmployeeService().showDetailsOnEmployee(employeeId);

        }catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            scanner.nextLine();
        }catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }
    private void showDetailsOnEmployee_EmployeeMenu(String employeeId) {
        try {
            hrService.getEmployeeService().showDetailsOnEmployee(employeeId);
        }catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            scanner.nextLine();
        }catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private void addConstraintToEmployee() {
        try {
        System.out.print("Enter employee ID: ");
        String employeeId = scanner.nextLine();

        System.out.print("Enter shift ID: ");
        int shiftId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter description: ");
        String description = scanner.nextLine();

            hrService.getShiftService().addConstraint(employeeId, shiftId, description);
            System.out.println("Constraint added successfully.");
        }catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            scanner.nextLine();
        }catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private void addConstraintToEmployee_EmployeeMenu(String employeeId) {
        try {

            System.out.print("Enter shift ID: ");
            int shiftId = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Enter description: ");
            String description = scanner.nextLine();

            hrService.getShiftService().addConstraint(employeeId, shiftId, description);
            System.out.println("Constraint added successfully.");
        }catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            scanner.nextLine();
        }catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private void deleteConstraintToEmployee() throws Exception {
        try {
            System.out.print("Enter employee ID: ");
            String employeeId = scanner.nextLine();
            System.out.print("Enter shift ID: ");
            int shiftId = scanner.nextInt();

            hrService.getShiftService().deleteConstraint(employeeId, shiftId);
            System.out.println("Constraint deleted successfully.");
        }catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            scanner.nextLine();
        }catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private void deleteConstraintToEmployee_EmployeeMenu(String employeeId) throws Exception {
        try {

            System.out.print("Enter shift ID: ");
            int shiftId = scanner.nextInt();

            hrService.getShiftService().deleteConstraint(employeeId, shiftId);
            System.out.println("Constraint deleted successfully.");
        }catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            scanner.nextLine();
        }catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }



    private void showEmployeeAssignmentsForShift() {
        try {
            System.out.print("Enter shift ID: ");
            int shiftId = scanner.nextInt();
            scanner.nextLine();

            String result = hrService.getShiftService().getEmployeeAssignByShiftId(shiftId);
            System.out.println("Employee Assignments for Shift " + shiftId + ":\n" + result);
        }catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            scanner.nextLine();
        }catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private void showNumberOfEmployeesNeededForShift() {
        try {
            System.out.print("Enter shift ID: ");
            int shiftId = scanner.nextInt();
            scanner.nextLine();

            String result = hrService.getShiftService().getEmployeeNeededByShiftId(shiftId);
            System.out.println("Employee needed for Shift " + shiftId + ":\n" + result);
        }catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            scanner.nextLine();
        }catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }
    private void showAllShifts() {
        try {
        String result = hrService.getShiftService().showAllShifts();
        System.out.println(result);
        }catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            scanner.nextLine();
        }catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private void showAllBranches() throws Exception {
        try {
            String result = hrService.getBranchService().showAllBranches();
            System.out.println(result);
        }catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            scanner.nextLine();
        }catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private void addBranch() throws Exception {
        try {
            System.out.print("Enter branch name: ");
            String branchName = scanner.nextLine();
            System.out.print("Enter branch address: ");
            String branchAddress = scanner.nextLine();

            System.out.print("Enter morning shift start hour (HH:mm): ");
            LocalTime morningShiftStartHour = LocalTime.parse(scanner.nextLine());
            System.out.print("Enter morning shift end hour (HH:mm): ");
            LocalTime morningShiftEndHour = LocalTime.parse(scanner.nextLine());
            System.out.print("Enter evening shift start hour (HH:mm): ");
            LocalTime eveningShiftStartHour = LocalTime.parse(scanner.nextLine());
            System.out.print("Enter evening shift end hour (HH:mm): ");
            LocalTime eveningShiftEndHour = LocalTime.parse(scanner.nextLine());

            String result = hrService.getBranchService().addBranch(branchName, branchAddress, morningShiftStartHour, eveningShiftStartHour, morningShiftEndHour, eveningShiftEndHour);
            System.out.println(result);
        }catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            scanner.nextLine();
        }catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private void removeBranch() throws Exception {
        try {
            System.out.print("Enter branch name: ");
            String branchName = scanner.nextLine();
            System.out.print("Enter branch address: ");
            String branchAddress = scanner.nextLine();
            String result = hrService.getBranchService().removeBranch(branchName, branchAddress);
            System.out.println(result);

        }catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            scanner.nextLine();
        }catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private void showEmployees() throws Exception {
        try {
            String result = hrService.getEmployeeService().showEmployees();
            System.out.println(result);

        }catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            scanner.nextLine();
        }catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private void registerEmployee() throws Exception {
        try {
            System.out.print("Enter employee ID: ");
            String employeeId = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            System.out.print("Enter first name: ");
            String firstName = scanner.nextLine();
            System.out.print("Enter last name: ");
            String lastName = scanner.nextLine();
            System.out.print("Enter account number: ");
            int accountNumber = scanner.nextInt();
            System.out.print("Enter branch bank number: ");
            int branchBankNumber = scanner.nextInt();
            System.out.print("Enter salary: ");
            int salary = scanner.nextInt();
            scanner.nextLine();  // Consume newline
            System.out.print("Enter terms of employment: ");
            String termsOfEmployment = scanner.nextLine();
            System.out.print("Is HR Manager (true / false): ");
            boolean isHRManager = scanner.nextBoolean();
            System.out.print("Is Shift Manager (true / false): ");
            boolean isShiftManager = scanner.nextBoolean();
            scanner.nextLine();  // Consume newline
            System.out.print("Enter job type (Full time / Part time): ");
            String jobType = scanner.nextLine();
            System.out.print("Enter branch address: ");
            String branchAddress = scanner.nextLine();
            System.out.print("Enter vacation days: ");
            int vacationDays = scanner.nextInt();
            scanner.nextLine();

            int year, month, day;
            String message;

            do {
                System.out.println("Start date:");
                System.out.println("Enter year:");
                year = scanner.nextInt();
                System.out.println("Enter month:");
                month = scanner.nextInt();
                System.out.println("Enter day:");
                day = scanner.nextInt();

                message = hrService.getShiftService().isValidDate(year, month, day);
                if (!message.contains("success")) {
                    System.out.println(message);
                }
            } while (!message.contains("success"));

            LocalDateTime startDate = LocalDateTime.of(year, month, day, 0, 0);

            System.out.print("Allow cancellations (true/false): ");
            boolean cancellations = scanner.nextBoolean();
            scanner.nextLine();
            System.out.print("Enter start role: ");
            String startRole = scanner.nextLine();

            String result = hrService.getEmployeeService().registerBranchEmployee(employeeId, password, firstName, lastName, accountNumber, branchBankNumber, salary, termsOfEmployment, isHRManager, isShiftManager, jobType, branchAddress, vacationDays, startDate, cancellations, startRole);
            System.out.println(result);
        }catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            scanner.nextLine();
        }catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private void registerDriver() throws Exception {
        try {
            System.out.print("Enter employee ID: ");
            String employeeId = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            System.out.print("Enter first name: ");
            String firstName = scanner.nextLine();
            System.out.print("Enter last name: ");
            String lastName = scanner.nextLine();
            System.out.print("Enter account number: ");
            int accountNumber = scanner.nextInt();
            System.out.print("Enter branch bank number: ");
            int branchBankNumber = scanner.nextInt();
            System.out.print("Enter salary: ");
            int salary = scanner.nextInt();
            scanner.nextLine();  // Consume newline
            System.out.print("Enter terms of employment: ");
            String termsOfEmployment = scanner.nextLine();
            scanner.nextLine();  // Consume newline
            System.out.print("Enter job type (Full time / Part time): ");
            String jobType = scanner.nextLine();
            System.out.print("Enter branch address: ");
            String branchAddress = scanner.nextLine();
            System.out.print("Enter vacation days: ");
            int vacationDays = scanner.nextInt();
            scanner.nextLine();

            int year, month, day;
            String message;

            do {
                System.out.println("Start date:");
                System.out.println("Enter year:");
                year = scanner.nextInt();
                System.out.println("Enter month:");
                month = scanner.nextInt();
                System.out.println("Enter day:");
                day = scanner.nextInt();

                message = hrService.getShiftService().isValidDate(year, month, day);
                if (!message.contains("success")) {
                    System.out.println(message);
                }
            } while (!message.contains("success"));

            LocalDateTime startDate = LocalDateTime.of(year, month, day, 0, 0);

            System.out.print("Licenses id: ");
            int licenses = scanner.nextInt();
            scanner.nextLine();

            String result = hrService.getEmployeeService().registerDriver(employeeId, password, firstName, lastName, accountNumber, branchBankNumber, salary, termsOfEmployment, false, false, jobType, branchAddress, vacationDays, startDate, licenses);
            System.out.println(result);
        }catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            scanner.nextLine();
        }catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private void removeEmployee() throws Exception {
        try {
            System.out.print("Enter employee ID: ");
            String employeeId = scanner.nextLine();
            String result = hrService.getEmployeeService().removeEmployee(employeeId);
            System.out.println(result);
    }catch (InputMismatchException e) {
            System.out.println("Invalid input.");
        scanner.nextLine();
    }catch (Exception e) {
        System.out.println("An unexpected error occurred: " + e.getMessage());
    }
    }

    private void showEmployeeFutureShifts() throws Exception {
        try {
            System.out.print("Enter employee ID: ");
            String employeeId = scanner.nextLine();
            String result = hrService.getShiftService().showEmployeeFutureShifts(employeeId);
            System.out.println(result);
        }catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            scanner.nextLine();
        }catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private void showEmployeeFutureShifts_EmployeeMenu(String employeeId) throws Exception {
        try {

            String result = hrService.getShiftService().showEmployeeFutureShifts(employeeId);
            System.out.println(result);
        }catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            scanner.nextLine();
        }catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private void addShift() throws Exception {
        try {
            int year, month, day;
            String message;

            do {
                System.out.println("Enter year:");
                year = scanner.nextInt();
                System.out.println("Enter month:");
                month = scanner.nextInt();
                System.out.println("Enter day:");
                day = scanner.nextInt();

                message = hrService.getShiftService().isValidDate(year, month, day);
                if (!message.contains("success")) {
                    System.out.println(message);
                }
            } while (!message.contains("success"));

            LocalDateTime startDate = LocalDateTime.of(year, month, day, 0, 0);

            System.out.print("Is morning shift (true/false): ");
            boolean isMorningShift = scanner.nextBoolean();
            System.out.print("Enter shift managers count: ");
            int shiftManagersCount = scanner.nextInt();
            System.out.print("Enter cashiers count: ");
            int cashiersCount = scanner.nextInt();
            System.out.print("Enter general employees count: ");
            int generalEmployeesCount = scanner.nextInt();
            scanner.nextLine();  // Consume newline
            System.out.print("Enter storekeepers count: ");
            int storekeepersCount = scanner.nextInt();
            scanner.nextLine();  // Consume newline
            System.out.print("Enter drivers count: ");
            int driversCount = scanner.nextInt();
            scanner.nextLine();  // Consume newline
            System.out.print("Enter branch Address: ");
            String branchAddress = scanner.nextLine();

            String result = hrService.getShiftService().addShift(startDate, isMorningShift, shiftManagersCount, cashiersCount, generalEmployeesCount, storekeepersCount, driversCount, branchAddress);
            System.out.println(result);
        }catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            scanner.nextLine();
        }catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private void removeShift() {
        try {
            System.out.print("Enter shift ID: ");
            int shiftId = scanner.nextInt();

            hrService.getShiftService().removeShift(shiftId);
            System.out.println("Shift removed successfully.");
        }catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            scanner.nextLine();
        }catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private void chooseEmployeesForShift() throws Exception {
        try {
            System.out.println("Enter employee ID: ");
            String employeeIdsInput = scanner.nextLine();

            System.out.print("Enter shift ID: ");
            int shiftId = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Enter employee role: ");
            String employeeRoleInput = scanner.nextLine();

            hrService.getShiftService().scheduleEmployeeToRole(employeeIdsInput, shiftId, employeeRoleInput);
            System.out.println("Employee scheduled successfully.");

        }catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            scanner.nextLine();
        }catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private void showEmployeesChosenForShift() {
        try {
            System.out.print("Enter shift ID: ");
            int shiftId = scanner.nextInt();
            scanner.nextLine();

            String result = hrService.getShiftService().showEmployeesChosenForShift(shiftId);
            System.out.println(result);
        }catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            scanner.nextLine();
        }catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }


    private void changeRoleToEmployeeInShift() {
        try {
            System.out.print("Enter employee ID: ");
            String employeeId = scanner.nextLine();

            System.out.print("Enter shift ID: ");
            int shiftId = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Enter new role: ");
            String role = scanner.nextLine();
            String result = hrService.getShiftService().changeScheduleByRole(employeeId, shiftId, role);
            System.out.println(result);
        }catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            scanner.nextLine();
        }catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private void DeleteEmployeeFromShift() {
        try {
            System.out.print("Enter employee ID: ");
            String employeeId = scanner.nextLine();

            System.out.print("Enter shift ID: ");
            int shiftId = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Enter role: ");
            String role = scanner.nextLine();

            hrService.getShiftService().removeSchedule(employeeId, shiftId, role);
            System.out.println("Schedule removed successfully.");
        }catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            scanner.nextLine();
        }catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }


    public void addRoleToEmployee() {
        try {
            System.out.print("Enter employee ID: ");
            String employeeId = scanner.nextLine();
            System.out.print("Enter role to add: ");
            String role = scanner.nextLine();

            hrService.getEmployeeService().addRoleToEmployee(employeeId, role);
            System.out.println("Role added successfully.");
        }catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.nextLine();
        }catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    public void removeRoleFromEmployee() {
        try {

            System.out.print("Enter employee ID: ");
            String employeeId = scanner.nextLine();
            System.out.print("Enter role to remove: ");
            String role = scanner.nextLine();

            hrService.getEmployeeService().removeRoleFromEmployee(employeeId, role);
            System.out.println("Role removed successfully.");
        }catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            scanner.nextLine();
        }catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private void showConstraintsForShift() throws Exception {
        try {
            System.out.print("Enter shift ID: ");
            int shiftId = scanner.nextInt();

            String result = hrService.getShiftService().showConstraintsForShift(shiftId);
            System.out.println(result);

        }catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            scanner.nextLine();
        }catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }

    }
    private void setFirstName(){
        try{
            System.out.print("Enter employee ID: ");
            String employeeId = scanner.nextLine();
            System.out.print("Enter new first name: ");
            String newFirstName = scanner.nextLine();

            String result = hrService.setFirstName(employeeId, newFirstName);
            System.out.println(result);
        }catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            scanner.nextLine();
        }catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }
    private void setLastName(){
        try{
            System.out.print("Enter employee ID: ");
            String employeeId = scanner.nextLine();
            System.out.print("Enter new last name: ");
            String newLastName = scanner.nextLine();

            String result = hrService.setLastName(employeeId, newLastName);
            System.out.println(result);
        }catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            scanner.nextLine();
        }catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }
    private void setPassword(){
        try{
            System.out.print("Enter employee ID: ");
            String employeeId = scanner.nextLine();
            System.out.print("Enter new password: ");
            String newPassword = scanner.nextLine();

            String result = hrService.getEmployeeService().editPassword(employeeId, newPassword);
            System.out.println(result);
        }catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            scanner.nextLine();
        }catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }
    private void setAccountNumber(){
        try{
            System.out.print("Enter employee ID: ");
            String employeeId = scanner.nextLine();
            System.out.print("Enter new account number: ");
            int newAccountNumber = scanner.nextInt();

            String result = hrService.setAccountNumber(employeeId, newAccountNumber);
            System.out.println(result);
        }catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            scanner.nextLine();
        }catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }
    private void setBranchBankNumber(){
        try{
            System.out.print("Enter employee ID: ");
            String employeeId = scanner.nextLine();
            System.out.print("Enter new branch bank number: ");
            int newBranchBankNumber = scanner.nextInt();

            String result = hrService.getEmployeeService().setBranchBankNumber(employeeId, newBranchBankNumber);
            System.out.println(result);
        }catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            scanner.nextLine();
        }catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }
    private void setSalary(){
        try {
            System.out.print("Enter employee ID: ");
            String employeeId = scanner.nextLine();
            System.out.print("Enter new salary: ");
            int newSalary = scanner.nextInt();

            String result = hrService.getEmployeeService().setSalary(employeeId, newSalary);
            System.out.println(result);
        } catch (InputMismatchException e) {
            System.out.println("Invalid input.");
                scanner.nextLine();
        }catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }
    private void setTermsOfEmployment(){
        try{
            System.out.print("Enter employee ID: ");
            String employeeId = scanner.nextLine();
            System.out.print("Enter new terms of employment: ");
            String newTerm = scanner.nextLine();

            String result = hrService.getEmployeeService().setTermsOfEmployment(employeeId, newTerm);
            System.out.println(result);
        }catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            scanner.nextLine();
        }catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }
    private void setHRManager(){
        try{
            System.out.print("Enter employee ID: ");
            String employeeId = scanner.nextLine();
            System.out.print("Is HR Manager (true / false): ");
            boolean change = scanner.nextBoolean();

            String result = hrService.getEmployeeService().setHRManager(employeeId, change);
            System.out.println(result);
        }catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            scanner.nextLine();
        }catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }
    private void setShiftManager(){
        try{
            System.out.print("Enter employee ID: ");
            String employeeId = scanner.nextLine();
            System.out.print("Is Shift Manager (true / false): ");
            boolean change = scanner.nextBoolean();

            String result = hrService.getEmployeeService().setManagement(employeeId, change);
            System.out.println(result);
        }catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            scanner.nextLine();
        }catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private void setJobType(){
        try{
            System.out.print("Enter employee ID: ");
            String employeeId = scanner.nextLine();
            System.out.print("Enter new job type (Full time / Part time): ");
            String newJobType = scanner.nextLine();

            String result = hrService.getEmployeeService().setJobType(employeeId, newJobType);
            System.out.println(result);
        }catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            scanner.nextLine();
        }catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }


    private void setBranchAddress(){
        try{
            System.out.print("Enter employee ID: ");
            String employeeId = scanner.nextLine();
            System.out.print("Enter new branch address: ");
            String newBranchAddress = scanner.nextLine();

            String result = hrService.getEmployeeService().setBranchAddress(employeeId, newBranchAddress);
            System.out.println(result);
        }catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            scanner.nextLine();
        }catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private void setVacationDays(){
        try{
            System.out.print("Enter employee ID: ");
            String employeeId = scanner.nextLine();
            System.out.print("Enter new vacation days: ");
            int newVactionDays = scanner.nextInt();

            String result = hrService.getEmployeeService().setVacationDays(employeeId, newVactionDays);
            System.out.println(result);
        }catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            scanner.nextLine();
        }catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }
    private void setCancellations() {
        try{
            System.out.print("Enter employee ID: ");
            String employeeId = scanner.nextLine();
            System.out.print("Allow cancellations (true/false): ");
            boolean cancellations = scanner.nextBoolean();

            String result = hrService.getEmployeeService().setCancellations(employeeId, cancellations);
            System.out.println(result);

        }catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            scanner.nextLine();
        }catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }
//    private void setLicense() {
//        try{
//            System.out.print("Enter employee ID: ");
//            String employeeId = scanner.nextLine();
//            System.out.print("new licenses id: ");
//            int license = scanner.nextInt();
//
//            String result = hrService.getEmployeeService().setLicense(employeeId, license);
//            System.out.println(result);
//
//        }catch (InputMismatchException e) {
//            System.out.println("Invalid input.");
//            scanner.nextLine();
//        }catch (Exception e) {
//            System.out.println("An unexpected error occurred: " + e.getMessage());
//        }
//    }



    private boolean askRetry() {
        while (true) {
            System.out.print("Do you want to try again? (yes/no): ");
            String tryAgain = scanner.nextLine();
            if (tryAgain.equalsIgnoreCase("no")) {
                return false;
            } else if (tryAgain.equalsIgnoreCase("yes")) {
                return true;
            } else {
                System.out.println("Invalid input. Please enter yes or no.");
            }
        }
    }

    private void login() {

        String loggedInEmployeeId = null;

        while (true) {
            try {
                System.out.print("Login: \n");
                System.out.print("Enter employee ID: ");
                String employeeId = scanner.nextLine();
                System.out.print("Enter password: ");
                String password = scanner.nextLine();

                try {
                    String result = hrService.getEmployeeService().login(employeeId, password);

                } catch (Exception e) {
                    System.out.println("Login failed: " + e.getMessage());
                    if (!askRetry()) {
                        System.out.println("Exiting...");
                        return;
                    }
                    continue;
                }

                String isHRManager = hrService.getEmployeeService().getHRManager(employeeId);

                System.out.println("Login successful.");
                loggedInEmployeeId = employeeId;
                if (isHRManager.equals("true")) {
                    HRMenu();
                } else {
                    EmployeeNoHRMenu(loggedInEmployeeId);
                }
                return; // Exit the login method after successful login


            }
            catch (InputMismatchException e) {
                System.out.println("Invalid input.");
                scanner.nextLine();
            }catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());

            }
        }
    }



}
