package Service_Layer.Workers_ServiceLayer;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import workers.DomainLayer.*;

public class HRService {
    public final ShiftController shiftController;
    public final EmployeeController employeeController;
    public final BranchController branchController;
    public EmployeeService employeeService;
    public ShiftService shiftService;
    public BranchService branchService;

    public HRService() throws Exception {
        branchController = new BranchController();
        branchService = new BranchService(branchController);
        if (checkIfAreCounerZero()){
            initializeCounter();
        }
        employeeController = new EmployeeController(branchController);
        shiftController = new ShiftController(employeeController, branchController);
        employeeService = new EmployeeService(employeeController);
        shiftService = new ShiftService(shiftController);
        if (checkIfAreTableEmpty()){
            insertData();
            initialize();}
        }

    private boolean checkIfAreCounerZero() {
        return branchService.checkIfAreCounerZero();
    }


    public BranchService getBranchService() {
        return branchService;
    }

    public EmployeeService getEmployeeService() {
        return employeeService;
    }

    public ShiftService getShiftService() {
        return shiftService;
    }
    private void initializeCounter() throws Exception{
        branchController.initializeCounter();
    }

    private void initialize() throws Exception {
        employeeService.registerBranchEmployee("123456789", "Aa1111", "Yuval", "Genislav", 20, 20, 30, "term", true,false,  "Full time", "Ramot 1", 0, LocalDateTime.now(), false, "Cashier");
    }

    public void insertData() throws Exception{
        LocalTime startMorningHour = LocalTime.of(10, 0);
        LocalTime endMorningHour = LocalTime.of(14, 0);
        LocalTime startEveningHour = LocalTime.of(14, 0);
        LocalTime endEveningHour = LocalTime.of(22, 0);

        branchService.addBranch("Rami Levi", "Ramot 1", startMorningHour, endMorningHour, startEveningHour, endEveningHour);
        branchService.addBranch("Shufersal", "Ramot 2", startMorningHour, endMorningHour, startEveningHour, endEveningHour);

        LocalDateTime date1 = LocalDateTime.of(2024, 12, 17, 10, 0);
        LocalDateTime date2 = LocalDateTime.of(2024, 12, 18, 10, 0);
        LocalDateTime date3 = LocalDateTime.of(2024, 07, 4, 10, 0);

        shiftService.addShift(date1, true,  1, 2, 3,1,1, "Ramot 1");
        shiftService.addShift(date2, true,  1, 5, 5,1,1, "Ramot 1");
        shiftService.addShift(date3, true,  1, 2, 2,2,2, "Ramot 1");

        employeeService.registerBranchEmployee("000000001", "Aa1111", "Avi", "Ron", 23, 23, 8000, "term", false, false, "Full time","Ramot 1",0,LocalDateTime.of(2023,1,1,0,0), false, "Cashier");
        employeeService.registerBranchEmployee("000000002", "TestPassword2", "Tiki", "Por", 24, 24, 9000, "term", false, false, "Full time","Ramot 1",0, LocalDateTime.now(),false,"General employee");
        employeeService.registerBranchEmployee("000000003", "TestPassword3", "Eli", "Kopter", 25, 25, 10000, "term", false,true, "Full time","Ramot 2", 0, LocalDateTime.now(),  true, "Shift manager");
        employeeService.registerBranchEmployee("987654321", "Aa1111", "Nir", "Dvori", 23, 23, 8000, "term", false, false, "Full time","Ramot 1",0,LocalDateTime.of(2023,1,1,0,0), false, "Cashier");

        employeeService.registerDriver("000000004", "TestPassword4", "Miki", "Geva", 26, 26, 6000, "term", false,false,"Full time","Ramot 1",0, LocalDateTime.now(), 123456);

        shiftService.scheduleEmployeeToRole("000000001", 1, "cashier");
    }


    public String showEmployees() throws Exception {
        return employeeService.showEmployees();
    }

//    public String showAllConstraintToEmployee(String employeeId) throws Exception {
//        return employeeService.showAllConstraintToEmployee(employeeId);
//    }

    public String getFirstName(String employeeId) throws Exception {
        return employeeService.getFirstName(employeeId);
    }

    public String setFirstName(String employeeId, String _firstName) throws Exception {
        return employeeService.setFirstName(employeeId,_firstName);
    }

    public String getLastName(String employeeId) throws Exception {
        return employeeService.getLastName(employeeId);
    }

    public String setLastName(String employeeId, String _lastName) throws Exception {
        return employeeService.setLastName(employeeId,_lastName);
    }

    public String registerDetails(String _employeeId) throws Exception {
        return employeeService.registerDetails(_employeeId);
    }

    public String editPassword(String employeeId, String password) throws Exception {
        return employeeService.editPassword(employeeId,password);
    }

    public String getAccountNumber(String employeeId) throws Exception {
        return employeeService.getAccountNumber(employeeId);
    }

    public String setAccountNumber(String employeeId,int _accountNumber) throws Exception {
        return employeeService.setAccountNumber(employeeId,_accountNumber);
    }

    public String getBranchBankNumber(String employeeId) throws Exception {
        return employeeService.getBranchBankNumber(employeeId);
    }

    public String setBranchBankNumber(String employeeId, int _branchBankNumber) throws Exception {
        return employeeService.setBranchBankNumber(employeeId,_branchBankNumber);
    }

    public String getSalary(String employeeId) throws Exception {
        return employeeService.getSalary(employeeId);
    }

    public String setSalary(String employeeId, int _salary) throws Exception {
        return employeeService.setSalary(employeeId,_salary);
    }

    public String getTermsOfEmployment(String employeeId) throws Exception {
        return employeeService.getTermsOfEmployment(employeeId);
    }

    public String setTermsOfEmployment(String employeeId, String _termsOfEmployment) {
        return  employeeService.setTermsOfEmployment(employeeId,_termsOfEmployment);
    }

    public String getJobType(String employeeId) throws Exception {
        return employeeService.getJobType(employeeId);
    }

    public String setJobType(String employeeId, String _jobType) throws Exception {
        return employeeService.setJobType(employeeId,_jobType);
    }

    public String getStartDate(String employeeId) throws Exception {
        return employeeService.getStartDate(employeeId);
    }

    public String getHRManager(String employeeId) throws Exception {
        return employeeService.getHRManager(employeeId);
    }

    public String setHRManager(String employeeId, boolean isHR) throws Exception {
        return employeeService.setHRManager(employeeId, isHR);
    }
    public String addDriverToShift(LocalDateTime date) throws Exception {
        return  shiftController.addDriverToShift(date);
    }

    public String getShowAllShifts(){
            return shiftService.showAllShifts();
    }
    public String getEmployeeAssignByShiftId(int shiftId) throws Exception {
        return shiftController.getEmployeeAssignByShiftId(shiftId);
    }

    public String getShowConstraintsForShift(int shiftId) throws Exception {
        return shiftService.showConstraintsForShift(shiftId);
    }
    public boolean checkIfAreTableEmpty(){
        return branchService.checkIfAreTableEmpty();
    }

}
