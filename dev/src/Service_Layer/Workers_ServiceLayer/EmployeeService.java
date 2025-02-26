package Service_Layer.Workers_ServiceLayer;


import workers.DomainLayer.*;

import java.time.LocalDateTime;

public class EmployeeService {
    private final EmployeeController employeeController;

    public EmployeeService(EmployeeController employeeController) {
        this.employeeController = employeeController;
    }

    public String showEmployees() throws Exception{
        return employeeController.showEmployees();
    }

    public String getFirstName(String employeeId) throws Exception {
        return employeeController.getFirstName(employeeId);
    }

    public String setFirstName(String employeeId, String _firstName) throws Exception{
        employeeController.setFirstName(employeeId, _firstName);
        return "success";
    }

    public String getLastName(String employeeId) throws Exception {
        return employeeController.getLastName(employeeId);
    }

    public String setLastName(String employeeId, String _lastName) throws Exception{
        employeeController.setLastName(employeeId, _lastName);
        return "success";
    }

    public String registerDetails(String _employeeId) throws Exception{
        return employeeController.returnRegisterDetails(_employeeId);
    }

    public String editPassword(String employeeId, String password) throws Exception{
        employeeController.editPassword(employeeId, password);
        return "success";
    }

    public String getAccountNumber(String employeeId) throws Exception{
        return employeeController.getAccountNumber(employeeId);
    }

    public String setAccountNumber(String employeeId, int _accountNumber) throws Exception{
        employeeController.setAccountNumber(employeeId, _accountNumber);
        return "success";
    }

    public String getBranchBankNumber(String employeeId) throws Exception{
        return employeeController.getBranchBankNumber(employeeId);
    }

    public String setBranchBankNumber(String employeeId, int _branchBankNumber) throws Exception{
        employeeController.setBranchBankNumber(employeeId, _branchBankNumber);
        return "success";
    }

    public String getSalary(String employeeId) throws Exception{
        return employeeController.getSalary(employeeId);
    }

    public String setSalary(String employeeId, int _salary) throws Exception{
        employeeController.setSalary(employeeId, _salary);
        return "success";
    }

    public String getTermsOfEmployment(String employeeId) throws Exception{
        return employeeController.getTermsOfEmployment(employeeId);
    }

    public String setTermsOfEmployment(String employeeId, String _termsOfEmployment) {
        try {
            employeeController.setTermsOfEmployment(employeeId, _termsOfEmployment);
            return "success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String getJobType(String employeeId) throws Exception{
        return employeeController.getJobType(employeeId);
    }

    public String setJobType(String employeeId, String _jobType) throws Exception {
        employeeController.setJobType(employeeId, _jobType);
        return "success";
    }

    public String getStartDate(String employeeId) throws Exception {
        return employeeController.getStartDate(employeeId);
    }

    public String getHRManager(String employeeId) throws Exception {
        boolean answer = employeeController.getHRManager(employeeId);
        if (answer) return "true";
        else return "false";
    }

    public String setHRManager(String employeeId, Boolean isHR) throws Exception {
        employeeController.setHRManager(employeeId, isHR);
        return "Employee's HR status was edited successfully";
    }

    public String getManagement(String employeeId) throws Exception {
        return employeeController.getShiftManager(employeeId) ? "true" : "false";
    }

    public String setManagement(String employeeId, boolean isShiftManager) throws Exception {
        employeeController.setShiftManager(employeeId, isShiftManager);
        return "success";
    }

    public String getRolesOfEmployee(String employeeId) throws Exception {
        return employeeController.getRolesOfEmployee(employeeId);
    }

    public String editConstraintDescription(String employeeId, int constraintId, String description) throws Exception {
        employeeController.editConstraintDescription(employeeId, constraintId, description);
        return "Constraint has been edited successfully";
    }

    public String registerBranchEmployee(String _employeeId, String _password, String _firstName, String _lastName, int _accountNumber, int _branchBankNumber, int _salary, String _termsOfEmployment, boolean _isHRManager, boolean _isShiftManager, String _jobType, String _branchAddress, int _vacationDays, LocalDateTime _startDate, boolean _cancellations, String startRole) throws Exception {
        employeeController.registerBranchEmployee(_employeeId, _password, _firstName, _lastName, _accountNumber, _branchBankNumber, _salary, _termsOfEmployment, _isHRManager, _isShiftManager, _jobType, _branchAddress, _vacationDays, _startDate, _cancellations, startRole);
        return "success";
    }

    public String registerDriver(String _employeeId, String _password, String _firstName, String _lastName, int _accountNumber, int _branchBankNumber, int _salary, String _termsOfEmployment, boolean _isHRManager, boolean _isShiftManager, String _jobType, String _branchAddress, int _vacationDays, LocalDateTime _startDate, int _licenses) throws Exception {
        employeeController.registerDriver(_employeeId, _password, _firstName, _lastName, _accountNumber, _branchBankNumber, _salary, _termsOfEmployment, _isHRManager, _isShiftManager, _jobType, _branchAddress, _vacationDays, _startDate, _licenses);
        return "success";
    }

    public String removeEmployee(String employeeId) throws Exception{
            employeeController.removeEmployee(employeeId);
            return "Employee removed successfully";
    }

    public String removeRoleFromEmployee(String employeeId, String role) throws Exception {
        employeeController.removeRoleFromEmployee(employeeId, role);
        return "Role removed successfully";
    }

    public String isEmployeeExists(String employeeId) {
        return employeeController.employeeExists(employeeId) ? "true" : "false";
    }

    public String isConstraintExist(String employeeId, int constraintId) throws Exception {
        Employee employee = employeeController.getEmployeeById(employeeId);
        boolean exists = employee.isConstraintExist(constraintId);
        return exists ? "Constraint exists." : "Constraint does not exist.";
    }

    public String isRoleExist(String role) {
        return employeeController.isRoleExist(role);
    }

    public String login(String employeeId, String password) throws Exception {
        employeeController.login(employeeId, password);
        return "success";
    }

    public String logout(String employeeId) throws Exception {
        employeeController.logout(employeeId);
        return "Logout Successfully";
    }

    public String editConstraintShift(String employeeId, int constraintId, String description) throws Exception {
        employeeController.editConstraintDescription(employeeId, constraintId, description);
        return "Constraint has been edited successfully";
    }
    public void addRoleToEmployee(String employeeId, String role) throws Exception {
        employeeController.addRoleToEmployee(employeeId, role);
    }
    public void showDetailsOnEmployee(String employeeId) throws Exception {
        employeeController.showDetailsOnEmployee(employeeId);
    }
    public String setBranchAddress(String employeeId, String branchAddress) throws Exception{
        employeeController.setBranchAddress(employeeId, branchAddress);
        return "success";
    }
    public String setVacationDays(String employeeId, int _vacationDays) throws Exception{
        employeeController.setVacationDays(employeeId, _vacationDays);
        return "success";
    }

    public String setCancellations(String employeeId, boolean cancellations) throws Exception {
        employeeController.setCancellations(employeeId, cancellations);
        return "success";
    }

//    public String setLicense(String employeeId, int license) throws Exception {
//        employeeController.setLicense(employeeId, license);
//        return "success";
//    }

}
