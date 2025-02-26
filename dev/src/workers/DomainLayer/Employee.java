package workers.DomainLayer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Employee {

    private String employeeId;
    private String firstName;
    private String lastName;
    private String password;
    private boolean isLogged;
    private int accountNumber;
    private int branchBankNumber;
    private int salary;
    private String termsOfEmployment;
    private String jobType;
    private boolean isHRManager;
    private boolean isShiftManager;
    private RoleType type;
    private ArrayList<String> jobTitles;
    private int vacationDays;
    private String branchAddress;
    private final LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<EmployeeConstraint> constraints;



    public Employee(String _employeeId, String _password, String _firstName, String _lastName ,int _accountNumber, int _branchBankNumber, int _salary ,String _termsOfEmployment , boolean _isHRManager, boolean _isShiftManager, String _jobType,String _branchAddress ,int _vacationDays ,LocalDateTime _startDate) {
        employeeId = _employeeId;
        password = _password;
        firstName = _firstName;
        lastName = _lastName;
        isLogged = false;
        accountNumber = _accountNumber;
        branchBankNumber = _branchBankNumber;
        salary = _salary;
        termsOfEmployment = _termsOfEmployment;
        jobType = _jobType;
        isHRManager = _isHRManager;
        isShiftManager = _isShiftManager;
        jobTitles = new ArrayList<>();
        branchAddress = _branchAddress;
        vacationDays = _vacationDays;
        startDate = _startDate;
        endDate = null;
        constraints = new ArrayList<>();

    }


    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String _employeeId) {
        employeeId = _employeeId;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String _password) { //Will be change
        password = _password;
    }
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String _firstName) {
        firstName = _firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String _lastName) {
        lastName = _lastName;
    }

    public void login(String password) throws Exception {
        if (isLogged) throw new Exception("User already logged in");
        if (password == null) throw new Exception("Password is null");
        if (password.equals(this.password)) isLogged = true;
        else throw new Exception("Password wrong");
    }

    public void logout() throws Exception {
        if (isLogged) isLogged = false;
        else throw new Exception("User is already logged out");
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int _accountNumber) { //Will be change
        accountNumber = _accountNumber;
    }
    public int getBranchBankNumber() {
        return branchBankNumber;
    }

    public void setBranchBankNumber(int _branchBankNumber) { //Will be change
        branchBankNumber = _branchBankNumber;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int _salary) {
        salary = _salary;
    }

    public String getTermsOfEmployment() {
        return termsOfEmployment;
    }

    public void setTermsOfEmployment(String _termsOfEmployment) {
        termsOfEmployment = _termsOfEmployment;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String _jobType) {
        jobType = _jobType;
    }
    public boolean getHRManager() {
        return isHRManager;
    }

    public void setHRManager(boolean _hRManager) {
        isHRManager = _hRManager;
    }
    public boolean getShiftManager() {
        return isShiftManager;
    }

    public void setShiftManager(boolean _isShiftManager) {
        isShiftManager = _isShiftManager;
    }
    public ArrayList<String> getJobTitles() {
        return jobTitles;
    }
    public void addJobTitles(String JobTitle) throws Exception{
        if (!jobTitles.contains(JobTitle)) {
            jobTitles.add(JobTitle);
        }
        else{
            throw new Exception("Job title already exists");
        }
    }
    public void deleteJobTitles(String JobTitle) throws Exception{
        if (!jobTitles.contains(JobTitle)) {
            throw new Exception("Job title not found");
        }
        else{
            jobTitles.remove(JobTitle);
        }
    }
    public void deleteAllJobTitles(){
        jobTitles.clear();
    }
    public String getBranchAddress() {
        return branchAddress;
    }

    public void set_branchAddress(String _branchAddress) {
        branchAddress = _branchAddress;
    }

    public int getVacationDays() {
        return vacationDays;
    }

    public void setVacationDays(int _vacationDays) {
        vacationDays = _vacationDays;
    }
    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDateTime _endDate){
        endDate = _endDate;
    }

    public List<EmployeeConstraint> getConstraints() {
        return constraints;
    }

    public void setConstraints(List<EmployeeConstraint> _constraints) {
        constraints = _constraints;
    }
    public boolean isConstraintExist(int constraintId) {
        for (EmployeeConstraint employeeConstraint : constraints) {
            if (employeeConstraint.getConstraintId() == constraintId) return true;
        }
        return false;
    }

    public boolean isCancellations() {
        return false;
    }

    public void setCancellations(boolean _cancellations) {
    }

    public RoleType getType() {
        return type;
    }
    public List<String> getRoles() {
        List<String> driver = new LinkedList<>();
        driver.add("Driver");
        return driver;
    }

    public void addRole(String role) throws Exception {
    }
    public void removeRole(String role) throws Exception {
    }

    public void removeConstraint(int constraintId) {
        constraints.removeIf(employeeConstraint -> employeeConstraint.getConstraintId() == constraintId);
    }

    public void addConstraint(EmployeeConstraint _constraint) {
        constraints.add(_constraint);
    }

    public boolean containsConstraint(int shiftId) {
        for (EmployeeConstraint employeeConstraint : constraints) {
            if (employeeConstraint.getShiftId() == shiftId) return true;
        }
        return false;
    }
    enum RoleType {BRANCHEMPLOYEE, DRIVER}
}
