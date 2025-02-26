package workers.DataAccessLayer;

import workers.DomainLayer.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;

public class DALFacade {
    private static DALFacade instance;
    private final BranchDAO branchDAO;
    private final EmployeeDAO employeeDAO;
    private final BranchEmployeeDAO branchEmployeeDAO;
    private final BranchEmployeeRoleDAO branchEmployeeRoleDAO;
    private final ConstraintToEmployeeDAO constraintToEmployeeDAO;
    private final EmployeeConstraintDAO employeeConstraintDAO;
    private final ShiftDAO shiftDAO;
    private final RoleCountDAO roleCountDAO;
    private final ScheduleDAO scheduleDAO;
    private final CountersDAO countersDAO;


    private DALFacade() throws SQLException {
        this.branchDAO = BranchDAO.getInstance();
        this.employeeDAO = EmployeeDAO.getInstance();
        this.branchEmployeeDAO = BranchEmployeeDAO.getInstance();
        this.branchEmployeeRoleDAO = BranchEmployeeRoleDAO.getInstance();
        this.constraintToEmployeeDAO = ConstraintToEmployeeDAO.getInstance();
        this.employeeConstraintDAO = EmployeeConstraintDAO.getInstance();
        this.shiftDAO = ShiftDAO.getInstance();
        this.roleCountDAO = RoleCountDAO.getInstance();
        this.scheduleDAO = ScheduleDAO.getInstance();
        this.countersDAO = CountersDAO.getInstance();

    }

    public static DALFacade getInstance() {
        try {
            if (instance == null) instance = new DALFacade();
            return instance;
        } catch (SQLException e) {
            throw new RuntimeException("DAL error");
        }
    }

    public void deleteInstance() {
        if (instance != null) {
            instance = null;
            BranchDAO.deleteInstance();
            EmployeeDAO.deleteInstance();
            BranchEmployeeDAO.deleteInstance();
            BranchEmployeeRoleDAO.deleteInstance();
            ConstraintToEmployeeDAO.deleteInstance();
            EmployeeConstraintDAO.deleteInstance();
            ShiftDAO.deleteInstance();
            RoleCountDAO.deleteInstance();
            ScheduleDAO.deleteInstance();
            CountersDAO.deleteInstance();

        }
    }

    public boolean checkIfAreTableEmpty() {
        return AbstractDAO.checkIfAreTableEmpty();
    }


    public void updateCounter(String counterName, int id) {
        countersDAO.updateMap(counterName, id);
        countersDAO.update(counterName, id);
    }
    public HashMap<String, Integer> getAllCounters() {
        return countersDAO.findAllCounters();
    }
    public HashMap<String, Branch> getAllBranches() {
        return branchDAO.findAllBranches();
    }

    public HashMap<String, Employee> getAllEmployees() {
        return employeeDAO.findAllEmployees();
    }

    public HashMap<String, BranchEmployee> getAllBranchEmployees() {
        return branchEmployeeDAO.findAllBranchEmployees();
    }


    public void insertBranch(Branch b) {
        branchDAO.addToMap(b.getBranchAddress(), b);
        branchDAO.insert(b.getBranchName(),b.getBranchAddress(), b.getMorningShiftStartHour(), b.getEveningShiftStartHour(), b.getMorningShiftEndHour(), b.getEveningShiftEndHour());
    }

    public void updateBranch(Branch b, String property) {
        String address = b.getBranchAddress();
        switch (property) {
            case "MorningShiftStartHour": {
                branchDAO.update(address, property, b.getMorningShiftStartHour());
                break;
            }
            case "EveningShiftStartHour": {
                branchDAO.update(address, property, b.getEveningShiftStartHour());
                break;
            }
            case "MorningShiftEndHour": {
                branchDAO.update(address, property, b.getMorningShiftEndHour());
                break;
            }
            case "EveningShiftEndHour": {
                branchDAO.update(address, property, b.getEveningShiftEndHour());
                break;
            }
        }
    }


    public void insertEmployee(Employee employee) {
        employeeDAO.addToMap(employee.getEmployeeId(), employee);
        employeeDAO.insert(employee.getEmployeeId(), employee.getPassword(), employee.getFirstName(), employee.getLastName(), employee.getAccountNumber(), employee.getBranchBankNumber(), employee.getSalary(), employee.getTermsOfEmployment(), employee.getHRManager() , employee.getShiftManager(), employee.getJobType(), employee.getBranchAddress(), employee.getVacationDays(), employee.getStartDate());
    }

    public void insertBranchEmployee(BranchEmployee br) {
        employeeDAO.addToMap(br.getEmployeeId(), br);
        branchEmployeeDAO.addToMap(br.getEmployeeId(), br);
        employeeDAO.insert(br.getEmployeeId(),br.getPassword(), br.getFirstName(), br.getLastName(),br.getAccountNumber(), br.getBranchBankNumber(), br.getSalary(), br.getTermsOfEmployment(), br.getHRManager(), br.getShiftManager(), br.getJobType(),br.getBranchAddress(), br.getVacationDays(), br.getStartDate());
        branchEmployeeDAO.insert(br.getEmployeeId(), br.getShiftManager(), br.isCancellations() ? 1 : 0);
    }

    public void insertDriver(Driver d) {
        employeeDAO.addToMap(d.getEmployeeId(), d);
        employeeDAO.insert(d.getEmployeeId(),d.getPassword(), d.getFirstName(), d.getLastName(),d.getAccountNumber(), d.getBranchBankNumber(), d.getSalary(), d.getTermsOfEmployment(), d.getHRManager(), d.getShiftManager(), d.getJobType(),d.getBranchAddress(), d.getVacationDays(), d.getStartDate());
    }

    public void updateEmployee(Employee employee, String property) {
        String employeeId = employee.getEmployeeId();
        switch (property) {
            case "Password": {
                employeeDAO.update(employeeId, property, employee.getPassword());
                break;
            }
            case "FirstName": {
                employeeDAO.update(employeeId, property, employee.getFirstName());
                break;
            }
            case "LastName": {
                employeeDAO.update(employeeId, property, employee.getLastName());
                break;
            }
            case "AccountNumber": {
                employeeDAO.update(employeeId, property, employee.getAccountNumber());
                break;
            }
            case "BranchBankNumber": {
                employeeDAO.update(employeeId, property, employee.getBranchBankNumber());
                break;
            }
            case "Salary": {
                employeeDAO.update(employeeId, property, employee.getSalary());
                break;
            }
            case "TermsOfEmployment": {
                employeeDAO.update(employeeId, property, employee.getTermsOfEmployment());
                break;
            }
            case "IsHRManager": {
                employeeDAO.update(employeeId, property, employee.getHRManager());
                break;
            }
            case "IsShiftManager": {
                employeeDAO.update(employeeId, property, employee.getShiftManager());
                break;
            }
            case "JobType": {
                employeeDAO.update(employeeId, property, employee.getJobType());
                break;
            }
            case "BranchAddress": {
                employeeDAO.update(employeeId, property, employee.getBranchAddress());
                break;
            }
            case "VacationDays": {
                employeeDAO.update(employeeId, property, employee.getVacationDays());
                break;
            }
        }
    }

    public void updateSchedule(int shiftId, String employeeId, String role) {
        scheduleDAO.update(shiftId, employeeId, role);
    }


    public void insertBranchEmployeeRole(BranchEmployee br, String role) {
        branchEmployeeRoleDAO.addToMap(br.getEmployeeId(), role);
        branchEmployeeRoleDAO.insert(br.getEmployeeId(), role);
    }

    public void deleteBranchEmployeeRole(BranchEmployee br, String role) {
        branchEmployeeRoleDAO.removeFromMap(br.getEmployeeId(), role);
        branchEmployeeRoleDAO.delete(br.getEmployeeId(), role);
    }

    public void insertConstraintToEmployee(Employee e, EmployeeConstraint ec) {
        constraintToEmployeeDAO.addToMap(e.getEmployeeId(), ec.getConstraintId());
        constraintToEmployeeDAO.insert(e.getEmployeeId(), ec.getConstraintId());
    }

    public void deleteConstraintToEmployee(Employee e, EmployeeConstraint ec) {
        constraintToEmployeeDAO.removeFromMap(e.getEmployeeId(), ec.getConstraintId());
        constraintToEmployeeDAO.delete(e.getEmployeeId(), ec.getConstraintId());
    }

    public void insertEmployeeConstraint(EmployeeConstraint ec) {
        employeeConstraintDAO.addToMap(ec.getConstraintId(), ec);
        employeeConstraintDAO.insert(ec.getConstraintId(), ec.getShiftId(), ec.getDescription());
    }

    public void updateEmployeeConstraint(EmployeeConstraint ec, String property) {
        int constraintId = ec.getShiftId();
        switch (property) {
            case "ShiftId": {
                employeeConstraintDAO.update(constraintId, property, ec.getShiftId());
                break;
            }
            case "DriversCount": {
                employeeConstraintDAO.update(constraintId, property, ec.getDescription());
                break;
            }
        }
    }

    public void deleteEmployeeConstraint(EmployeeConstraint ec) {
        employeeConstraintDAO.removeFromMap(ec.getConstraintId());
        employeeConstraintDAO.delete(ec.getConstraintId());
    }

    public void updateShift(Shift s, String property) {
        int shiftId = s.getShiftId();
        switch (property) {
            case "Date": {
                shiftDAO.update(shiftId, property, s.getDate().toString());
                break;
            }
        }
    }
    public void insertShift(Shift shift) {
        shiftDAO.addToMap(shift.getShiftId(), shift);
        shiftDAO.insert(shift.getShiftId(), shift.getDate().toString(), shift.getBranchAddress(), shift.isMorningShift());
        HashMap<String, Integer> roleCounts = shift.getNumberOfRolesPerShift();
        for (String role : roleCounts.keySet()) {
            roleCountDAO.addToMap(shift.getShiftId(), role, roleCounts.get(role), 0);
            roleCountDAO.insert(shift.getShiftId(), role, roleCounts.get(role), 0);
        }
    }


    public void updateRoleCount(int shiftId, String role, int newCount, String property) {
        switch (property) {
            case "CountNeeded":
            case "CountAssigned": {
                roleCountDAO.update(shiftId, role, property, newCount);
                break;
            }
        }
    }

    public void insertSchedule(int shiftId, String employeeId, String role) {
        scheduleDAO.addToMap(shiftId, employeeId, role);
        scheduleDAO.insert(shiftId, employeeId, role);
    }

    public void deleteSchedule(int shiftId, String employeeId) {
        scheduleDAO.removeFromMap(shiftId, employeeId);
        scheduleDAO.delete(shiftId, employeeId);
    }

    public HashMap<Integer, Shift> getAllShifts() {
        return shiftDAO.findAllShifts();
    }

    public void initializeCounter() {
        countersDAO.insert("nextShiftId", 0);
    }

    public boolean checkIfAreCounerZero() {
        return AbstractDAO.checkIfAreCounerZero();
    }
}
