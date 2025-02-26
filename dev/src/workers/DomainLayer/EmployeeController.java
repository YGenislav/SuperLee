package workers.DomainLayer;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import workers.DataAccessLayer.DALFacade;

public class EmployeeController {

    private HashMap<String, Employee> employees; // <employeeId, Employee>
    public static final int MIN_PASS_LENGTH = 6;
    public static final int MAX_PASS_LENGTH = 20;
    private final HashMap<String, Employee> formerEmployees;
    private BranchController branchController;
    private final DALFacade dalController;


    public EmployeeController(BranchController _branchController) {
        employees = new HashMap<>();
        formerEmployees = new HashMap<>();
        branchController = _branchController;
        dalController= DALFacade.getInstance();
        loadData();
    }

    public void loadData(){
        try{
            setEmployees(dalController.getAllEmployees());
            HashMap<String,BranchEmployee> branchEmployees=dalController.getAllBranchEmployees();
            for(String employeeId:branchEmployees.keySet()){
                employees.put(employeeId,branchEmployees.get(employeeId));
            }
        }
        catch (Exception e){
            throw new RuntimeException("DAL Error");
        }
    }

    private void setEmployees(HashMap<String, Employee> _employees){
        employees=_employees;
    }

    public boolean employeeExists(String employeeId) {
        return employees.containsKey(employeeId);
    }

    public void addEmployee(Employee employee) throws Exception {
        if (employeeExists(employee.getEmployeeId())) {
            throw new Exception("EmployeeId:" + employee.getEmployeeId() + " already exists");
        } else if (employee.getPassword().length() >= MIN_PASS_LENGTH && employee.getPassword().length() <= MAX_PASS_LENGTH) {
            checkPassword(employee.getPassword());
            employees.put(employee.getEmployeeId(), employee);
            dalController.insertEmployee(employee);
        } else {
            throw new Exception("Password length must be between " + MIN_PASS_LENGTH + " and " + MAX_PASS_LENGTH);
        }
    }


    public Employee getEmployeeById(String employeeId) throws Exception {
        if (employeeExists(employeeId)) {
            return employees.get(employeeId);
        } else {
            throw new Exception("Employee does not exist");
        }
    }
    public void removeEmployee(String employeeId) throws Exception {

        if (employees.isEmpty()) {
            throw new Exception("No employees available to remove.");
        }
        if (!employeeExists(employeeId)) {
            throw new Exception("EmployeeId:" + employeeId + " does not exist");
        }
        Employee removedEmployee = employees.remove(employeeId);
        if (removedEmployee != null) {
            formerEmployees.put(employeeId, removedEmployee);
        }
    }

    public String showEmployees() {
        StringBuilder allEmployees = new StringBuilder();
        for (Employee employee : employees.values()) {
            if(!employee.getHRManager())
                allEmployees.append("Name: ").append(employee.getFirstName()).append(" " + employee.getLastName()).append(". ID: ").append(employee.getEmployeeId()).append(". Role: ").append(employee.getRoles()).append("\n");
        }
        return allEmployees.toString();
    }

    public String getFirstName(String employeeId) throws Exception {
        if (!employeeExists(employeeId)) throw new Exception("Employee does not exist");
        return employees.get(employeeId).getFirstName();
    }

    public void setFirstName(String employeeId, String _firstName) throws Exception {
        if (!employeeExists(employeeId)) throw new Exception("Employee does not exist");
        if (_firstName == null || _firstName.isEmpty()) throw new Exception("First name can not be empty");
        employees.get(employeeId).setFirstName(_firstName);
        dalController.updateEmployee(employees.get(employeeId),"FirstName");
    }
    public String getLastName(String employeeId) throws Exception {
        if (!employeeExists(employeeId)) throw new Exception("Employee does not exist");
        return employees.get(employeeId).getLastName();
    }

    public void setLastName(String employeeId, String _lastName) throws Exception {
        if (!employeeExists(employeeId)) throw new Exception("Employee does not exist");
        if (_lastName == null || _lastName.isEmpty()) throw new Exception("Last name can not be empty");
        employees.get(employeeId).setLastName(_lastName);
        dalController.updateEmployee(employees.get(employeeId),"LastName");
    }
    public String getAccountNumber(String employeeId) throws Exception {
        if (!employeeExists(employeeId)) throw new Exception("Employee does not exist");
        return Integer.toString(employees.get(employeeId).getAccountNumber());
    }

    public void setAccountNumber(String employeeId, int _accountNumber) throws Exception {
        if (!employeeExists(employeeId)) throw new Exception("Employee does not exist");
        if(_accountNumber<=0) {
            throw new Exception("Account number can not be negative");
        }
        employees.get(employeeId).setAccountNumber(_accountNumber);
        dalController.updateEmployee(employees.get(employeeId),"AccountNumber");

    }

    public String getBranchBankNumber(String employeeId) throws Exception {
        if (!employeeExists(employeeId)) throw new Exception("Employee does not exist");
        return Integer.toString(employees.get(employeeId).getBranchBankNumber());
    }

    public void setBranchBankNumber(String employeeId, int _branchBankNumber) throws Exception {
        if (!employeeExists(employeeId)) throw new Exception("Employee does not exist");
        if(_branchBankNumber<=0){
            throw new Exception("Branch Bank number can not be negative");
        }
        employees.get(employeeId).setBranchBankNumber(_branchBankNumber);
        dalController.updateEmployee(employees.get(employeeId),"BranchBankNumber");
    }
    public String getSalary(String employeeId) throws Exception {
        if (!employeeExists(employeeId)) throw new Exception("Employee does not exist");
        return Integer.toString(employees.get(employeeId).getSalary());
    }

    public void setSalary(String employeeId, int _salary) throws Exception {
        if (!employeeExists(employeeId)) throw new Exception("Employee does not exist");
        if(_salary<0){
            throw new Exception("Salary can not be negative");
        }
        employees.get(employeeId).setSalary(_salary);
        dalController.updateEmployee(employees.get(employeeId),"Salary");
    }

    public String getTermsOfEmployment(String employeeId) throws Exception {
        if (!employeeExists(employeeId)) throw new Exception("Employee does not exist");
        return employees.get(employeeId).getTermsOfEmployment();
    }

    public void setTermsOfEmployment(String employeeId, String _termsOfEmployment) throws Exception {
        if (!employeeExists(employeeId)) throw new Exception("Employee does not exist");
        employees.get(employeeId).setTermsOfEmployment(_termsOfEmployment);
        dalController.updateEmployee(employees.get(employeeId),"TermsOfEmployment");
    }
    public boolean getHRManager(String employeeId) throws Exception {
        if (!employeeExists(employeeId)) throw new Exception("Employee does not exist");
        return employees.get(employeeId).getHRManager();
    }

    public void setHRManager(String employeeId, Boolean isHRManager) throws Exception {
        if (!employeeExists(employeeId)) throw new Exception("Employee does not exist");
        if (employees.get(employeeId).getHRManager())
            throw new Exception("The Employee is already HR manager");
        employees.get(employeeId).setHRManager(isHRManager);
        dalController.updateEmployee(employees.get(employeeId),"IsHRManager");
    }
    public boolean getShiftManager(String employeeId) throws Exception {
        if (!employeeExists(employeeId)) throw new Exception("Employee does not exist");
        return employees.get(employeeId).getShiftManager();
    }

    public void setShiftManager(String employeeId, boolean isShiftManager) throws Exception {
        if (!employeeExists(employeeId)) throw new Exception("Employee does not exist");
        if (employees.get(employeeId).getShiftManager())
            throw new Exception("The Employee is already shift manager");
        employees.get(employeeId).setShiftManager(isShiftManager);
        dalController.updateEmployee(employees.get(employeeId),"IsShiftManager");
    }
    public void editConstraintDescription(String employeeId, int constraintId, String description) throws Exception {
        if (!employeeExists(employeeId)) throw new Exception("Employee does not exist");
        if (!employees.get(employeeId).isConstraintExist(constraintId))
            throw new Exception("Constraint does not exist at employee");
        for (EmployeeConstraint employeeConstraint : employees.get(employeeId).getConstraints()) {
            if (employeeConstraint.getConstraintId() == constraintId) {
                employeeConstraint.setDescription(description);
                dalController.updateEmployeeConstraint(employees.get(employeeId).getConstraints().get(constraintId - 1), "Description");
            }
        }
    }

    public String getJobType(String employeeId) throws Exception {
        if (!employeeExists(employeeId)) throw new Exception("Employee does not exist");
        return employees.get(employeeId).getJobType();
    }

    public void setJobType(String employeeId, String _jobType) throws Exception {
        if (!employeeExists(employeeId)) throw new Exception("Employee does not exist");
        if (_jobType.equals("Full time") || _jobType.equals("Part time")) {
            employees.get(employeeId).setJobType(_jobType);
            dalController.updateEmployee(employees.get(employeeId), "JobType");
        }
        else{
            throw new Exception("Job-Type is incorrect");
        }
    }
    public String getVacationDays(String employeeId) throws Exception {
        if (!employeeExists(employeeId)) throw new Exception("Employee does not exist");
        return Integer.toString(employees.get(employeeId).getVacationDays());
    }

    public void setVacationDays(String employeeId, int _vacationDays) throws Exception {
        if (!employeeExists(employeeId)) throw new Exception("Employee does not exist");
        if(_vacationDays<0){
            throw new Exception("Number of vacation days can not be negative");
        }
        employees.get(employeeId).setVacationDays(_vacationDays);
        dalController.updateEmployee(employees.get(employeeId),"VacationDays");
    }

    public String getBranchAddress(String employeeId) throws Exception {
        if (!employeeExists(employeeId)) throw new Exception("Employee does not exist");
        return employees.get(employeeId).getBranchAddress();
    }

    public void setBranchAddress(String employeeId, String _branchAddress) throws Exception {
        if (!employeeExists(employeeId)) throw new Exception("Employee does not exist");
        if(_branchAddress==null || _branchAddress.isEmpty()){
            throw new Exception("Invalid branch name");
        }
        employees.get(employeeId).set_branchAddress(_branchAddress);
    }
    public String getStartDate(String employeeId) throws Exception {
        if (!employeeExists(employeeId)) throw new Exception("Employee does not exist");
        return employees.get(employeeId).getStartDate().toString();
    }
    public String getEndDate(String employeeId) throws Exception {
        if (!employeeExists(employeeId)) throw new Exception("Employee does not exist");
        return employees.get(employeeId).getEndDate().toString();
    }
    public void setEndDate(String employeeId, LocalDateTime _endDate) throws Exception {
        if (!employeeExists(employeeId)) throw new Exception("Employee does not exist");
        if(_endDate.isBefore(employees.get(employeeId).getStartDate())){
            throw new Exception("Invalid end date");
        }
        employees.get(employeeId).setEndDate(_endDate);
    }
    public HashMap<String, Employee> getEmployees() {
        return employees;
    }

    private static boolean isValidID(String str) {
        if (str == null || str.length() != 9) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
    public void checkPassword(String password) throws Exception {
        if (password == null) throw new Exception("Password can not be empty");
        if (password.length() < MIN_PASS_LENGTH || password.length() > MAX_PASS_LENGTH)
            throw new Exception("The password must be 6 to 20 characters");
        boolean isUpperCase = false;
        boolean isLowerCase = false;
        boolean isDigit = false;
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isUpperCase(c)) isUpperCase = true;
            if (Character.isLowerCase(c)) isLowerCase = true;
            if (Character.isDigit(c)) isDigit = true;
        }
        if (!isDigit) throw new Exception("Password must be with at least one digit");
        if (!isUpperCase) throw new Exception("Password must be with at least one upper letter");
        if (!isLowerCase) throw new Exception("Password must be with at least one lower letter");
    }
    public void editPassword(String employeeId, String password) throws Exception {
        if (!employeeExists(employeeId)) throw new Exception("Employee does not exist");
        checkPassword(password);
        employees.get(employeeId).setPassword(password);
        dalController.updateEmployee(employees.get(employeeId),"Password");
    }
    private void registerChecks(String _employeeId, String _firstName, String _lastName, String _password,  int _accountNumber, int _branchBankNumber,int _salary ,String _jobType,String branchAddress ,int _vacationDays) throws Exception {
        if (!isValidID(_employeeId))     throw new Exception("Employee ID incorrect");
        if (_firstName == null)          throw new Exception("First name is null");
        if (_firstName.isEmpty())    throw new Exception("First name is empty");
        if (_lastName == null)           throw new Exception("Last name is null");
        if (_lastName.isEmpty())     throw new Exception("Last name is empty");
        checkPassword(_password);
        if(_accountNumber<0 |_branchBankNumber<0) throw new Exception("account number or branch bank number is invalid");
        if(_salary<0) throw new Exception("Invalid salary");
        if(!_jobType.equals("Full time") && !_jobType.equals("Part time")) throw new Exception("Invalid job type");
        if(_vacationDays<0) throw new Exception("Invalid salary");
        if (employeeExists(_employeeId)) throw new Exception("Employee Id already exists");
        if (!branchController.isBranchAddressExists(branchAddress)) throw new Exception("Branch does not exist");

    }

    public void registerBranchEmployee(String _employeeId, String _password, String _firstName, String _lastName, int _accountNumber, int _branchBankNumber, int _salary, String _termsOfEmployment, boolean _isHRManager, boolean _isShiftManager, String _jobType, String _branchAddress, int _vacationDays, LocalDateTime _startDate, boolean _cancellations, String startRole) throws Exception {
        registerChecks(_employeeId, _firstName, _lastName, _password,_accountNumber,_branchBankNumber,_salary,_jobType, _branchAddress, _vacationDays);
        BranchEmployee branchEmployee = new BranchEmployee(_employeeId, _password, _firstName, _lastName, _accountNumber, _branchBankNumber, _salary, _termsOfEmployment, _isHRManager, _isShiftManager, _jobType, _branchAddress, _vacationDays, _startDate, _cancellations);
        branchEmployee.addRole(startRole);
        employees.put(_employeeId, branchEmployee);
        dalController.insertBranchEmployee(branchEmployee);
    }

    public void registerDriver(String _employeeId, String _password, String _firstName, String _lastName, int _accountNumber, int _branchBankNumber, int _salary, String _termsOfEmployment, boolean _isHRManager, boolean _isShiftManager, String _jobType, String _branchAddress, int _vacationDays, LocalDateTime _startDate, int _licenses) throws Exception {
        registerChecks(_employeeId, _firstName, _lastName, _password,_accountNumber,_branchBankNumber,_salary,_jobType,_branchAddress, _vacationDays);
        Driver driver = new Driver(_employeeId, _password, _firstName, _lastName, _accountNumber, _branchBankNumber, _salary, _termsOfEmployment, _isHRManager, _isShiftManager, _jobType, _branchAddress, _vacationDays, _startDate, _licenses);
        employees.put(_employeeId, driver);
        dalController.insertDriver(driver);
    }
    public String isRoleExist(String role) {
        String _role;
        switch (role.toLowerCase()) {
            case "shift manager":
                _role = "Shift manager";
                break;
            case "cashier":
                _role = "Cashier";
                break;
            case "general employee":
                _role = "General employee";
                break;
            case "storekeeper":
                _role = "Storekeeper";
                break;
            case "driver":
                _role = "Driver";
                break;
            default:
                if (Character.isLowerCase(role.charAt(0))) {
                    _role = Character.toUpperCase(role.charAt(0)) + role.substring(1);
                } else {
                    _role = role;
                }
                break;
        }

        List<String> roles = new LinkedList<>();
        roles.add("Shift manager");
        roles.add("Cashier");
        roles.add("General employee");
        roles.add("Storekeeper");
        roles.add("Driver");
        boolean isExist = roles.contains(_role);
        return Boolean.toString(isExist);
    }

    public void removeRoleFromEmployee(String employeeId, String role) throws Exception {

        String _role;

        if (!employeeExists(employeeId)) throw new Exception("Employee does not exist");
        System.out.println(employees.get(employeeId).getRoles().size());
        if (employees.get(employeeId).getRoles().size() == 1) throw new Exception("Role cannot be empty");

        switch (role.toLowerCase()) {
            case "shift manager":
                _role = "Shift manager";
                break;
            case "cashier":
                _role = "Cashier";
                break;
            case "general employee":
                _role = "General employee";
                break;
            case "storekeeper":
                _role = "Storekeeper";
                break;
            case "driver":
                _role = "Driver";
                break;
            default:
                if (Character.isLowerCase(role.charAt(0))) {
                    _role = Character.toUpperCase(role.charAt(0)) + role.substring(1);
                } else {
                    _role = role;
                }
                dalController.deleteBranchEmployeeRole(getBranchEmployee(employeeId),"Role");
                break;
        }


        if (!employeeExists(employeeId)) throw new Exception("Employee does not exist");
        if (isRoleExist(_role).equals("false")) throw new Exception("Role does not exist");
        employees.get(employeeId).removeRole(_role);
    }


    public BranchEmployee getBranchEmployee(String employeeId) throws Exception{
        if (!employees.containsKey(employeeId))  throw new Exception("Employee doesn't exist");
        if(employees.get(employeeId).getType() == Employee.RoleType.BRANCHEMPLOYEE)
            return (BranchEmployee) employees.get(employeeId);
        else throw new IllegalArgumentException("Employee is not branch employee");
    }

    public void login(String employeeId, String password) throws Exception {
        if (!employeeExists(employeeId)) throw new Exception("Employee does not exist");
        employees.get(employeeId).login(password);
        dalController.updateEmployee(employees.get(employeeId),"IsLogged");
    }

    public void logout(String employeeId) throws Exception {
        if (!employeeExists(employeeId)) throw new Exception("Employee does not exist");
        employees.get(employeeId).logout();
        dalController.updateEmployee(employees.get(employeeId),"IsLogged");
    }

    public String returnRegisterDetails(String employeeId) throws Exception {
        if (!employees.containsKey(employeeId)) throw new Exception("Employee doesn't exist");
        return "Employee Id: " + employeeId + "\n" + "Password: " + employees.get(employeeId).getPassword();
    }

    public String getRolesOfEmployee(String employeeId) throws Exception {
        StringBuilder roles= new StringBuilder();
        if (!employees.containsKey(employeeId)) throw new Exception("Employee doesn't exist");
        if(employees.get(employeeId).getType().equals("Driver"))
            roles = new StringBuilder("Driver");
        else {
            for(String role:employees.get(employeeId).getRoles()){
                roles.append(role).append("\n");
            }
        }
        return roles.toString();
    }

    public void addRoleToEmployee(String employeeId, String role) throws Exception {
        String _role;

        if (!employeeExists(employeeId)) throw new Exception("Employee does not exist");

        switch (role.toLowerCase()) {
            case "shift manager":
                _role = "Shift manager";
                break;
            case "cashier":
                _role = "Cashier";
                break;
            case "general employee":
                _role = "General employee";
                break;
            case "storekeeper":
                _role = "Storekeeper";
                break;
            case "driver":
                _role = "Driver";
                break;
            default:
                if (Character.isLowerCase(role.charAt(0))) {
                    _role = Character.toUpperCase(role.charAt(0)) + role.substring(1);
                } else {
                    _role = role;
                }
                break;
        }

        if (isRoleExist(_role).equals("false")) {throw new Exception("Role does not exist");}

        if (employees.get(employeeId).getRoles().contains(_role)) {throw new Exception("Employee already has this role");}

        if (employees.get(employeeId).getType().equals("Driver")) {throw new Exception("The driver can have only one role");}

        employees.get(employeeId).addRole(_role);
        dalController.insertBranchEmployeeRole(getBranchEmployee(employeeId),role);
    }


    public void showDetailsOnEmployee(String employeeId) throws Exception{
        if (!employeeExists(employeeId)) throw new Exception("Employee does not exist");
        else {
            System.out.println("Employee ID: " + employeeId);
            System.out.println("First name: " + employees.get(employeeId).getFirstName());
            System.out.println("Last name: " + employees.get(employeeId).getLastName());
            System.out.println("Account number: " + employees.get(employeeId).getAccountNumber());
            System.out.println("Branch bank number: " + employees.get(employeeId).getBranchBankNumber());
            System.out.println("Salary: " + employees.get(employeeId).getSalary());
            System.out.println("Terms of employment: " + employees.get(employeeId).getTermsOfEmployment());
            System.out.println("HR Manager: " + employees.get(employeeId).getHRManager());
            System.out.println("Shift Manager: " + employees.get(employeeId).getShiftManager());
            System.out.println("Job type: " + employees.get(employeeId).getJobType());
            System.out.println("Branch address: " + employees.get(employeeId).getBranchAddress());
            System.out.println("Vacation days: " + employees.get(employeeId).getVacationDays());
            System.out.println("Start date: " + employees.get(employeeId).getStartDate());
            System.out.println("End date: " + employees.get(employeeId).getEndDate());
            System.out.println("Roles: " + employees.get(employeeId).getRoles());
        }
    }

    public void setCancellations(String employeeId, boolean cancellations) throws Exception{
        if (!employeeExists(employeeId)) throw new Exception("Employee does not exist");
       employees.get(employeeId).setCancellations(cancellations);
    }


}