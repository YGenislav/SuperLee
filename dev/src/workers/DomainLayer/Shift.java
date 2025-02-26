package workers.DomainLayer;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Shift {
    private int shiftId;
    protected LocalDateTime date;
    private Branch branch;
    private boolean isMorningShift;
    private final HashMap<String, Integer> numberOfRolesPerShift;//<Role ,numberOfRole>
    private final HashMap<String, Integer> employeeAssign; //<Role, numberOfAssignEmployee> number of employees were assigned to a shift from each position
    private final HashMap<String, String> schedules; //<employeeId ,Role>


    public Shift(int _shiftId, LocalDateTime _date, Branch _branch, boolean _isMorningShift) {
        shiftId = _shiftId;
        date = _date;
        branch = _branch;
        isMorningShift = _isMorningShift;
        numberOfRolesPerShift = new HashMap<>();
        employeeAssign = new HashMap<>();
        schedules = new HashMap<>();;
        initEmployeeAssign();
    }


    public Shift(int _shiftId, LocalDateTime _date,HashMap<String,Integer> _roleCounts,HashMap<String,Integer> _scheduleToRoleCount,HashMap<String, String> _schedules, String _branchAddress, boolean _isMorningShift) {
        shiftId = _shiftId;
        date = _date;
        numberOfRolesPerShift = _roleCounts;
        employeeAssign = _scheduleToRoleCount;
        schedules=_schedules;
        isMorningShift = _isMorningShift;
    }




    public void createSchedulesMap(int _shiftManagersCount, int _cashiersCount, int _generalEmployeesCount, int _storekeeperCount, int _driverCount) throws Exception{

        numberOfRolesPerShift.put("Shift Manager", _shiftManagersCount);
        numberOfRolesPerShift.put("Cashier", _cashiersCount);
        numberOfRolesPerShift.put("General Employee", _generalEmployeesCount);
        numberOfRolesPerShift.put("Storekeeper", _storekeeperCount);
        numberOfRolesPerShift.put("Driver", _driverCount);

    }

    public void initEmployeeAssign(){

        employeeAssign.put("Shift Manager", 0);
        employeeAssign.put("Cashier", 0);
        employeeAssign.put("General Employee", 0);
        employeeAssign.put("Storekeeper", 0);
        employeeAssign.put("Driver", 0);
    }

    public int getShiftId() {
        return shiftId;
    }

    public void setShiftId(int shiftId) {
        this.shiftId = shiftId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getBranchName() {
        return branch.getBranchName();
    }
    public String getBranchAddress() {
        return branch.getBranchAddress();
    }

    public boolean isMorningShift() {
        return isMorningShift;
    }

    public void setMorningShift(boolean morningShift) {
        isMorningShift = morningShift;
    }

    public HashMap<String, Integer> getNumberOfRolesPerShift() {
        return numberOfRolesPerShift;
    }

    public HashMap<String, String> getSchedules() {
        return schedules;
    }
    public HashMap<String, Integer> getEmployeeAssign() {
        return employeeAssign;
    }


    public void addEmployeeToSchedule(String employeeId) {
        schedules.put(employeeId, "");
    }

    public void removeSchedule(String employeeId, String role) {
        schedules.remove(employeeId);
        employeeAssign.put(role, employeeAssign.get(role) - 1);
    }

    public void changeHour(LocalTime hour) {
        date = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), hour.getHour(), hour.getMinute());
    }
    public void changeSchedule(String employeeId, String role) throws Exception {
        removeSchedule(employeeId, role);
        addSchedule(employeeId, role);
    }
    public void addSchedule(String employeeId, String role) throws Exception {
        if (schedules.containsKey(employeeId) && !schedules.get(employeeId).equals("")) {
            throw new Exception("Employee already existed");
        }
        switch (role) {
            case "Shift Manager":
                addShiftManager(employeeId);
                employeeAssign.put("Shift Manager", employeeAssign.get("Shift Manager") + 1);
                break;
            case "Cashier":
                addCashier(employeeId);
                employeeAssign.put("Cashier", employeeAssign.get("Cashier") + 1);
                break;
            case "General Employee":
                addGeneralEmployee(employeeId);
                employeeAssign.put("General Employee", employeeAssign.get("General Employee") + 1);
                break;
            case "Storekeeper":
                addStorekeeper(employeeId);
                employeeAssign.put("Storekeeper", employeeAssign.get("Storekeeper") + 1);
                break;
            case "Driver":
                addDriver(employeeId);
                employeeAssign.put("Driver", employeeAssign.get("Driver") + 1);
                break;
            default:
                throw new Exception("Invalid role: " + role);
        }

    }

    private void addShiftManager(String employeeId) throws Exception {
        if (employeeAssign.get("Shift Manager") < numberOfRolesPerShift.get("Shift Manager")) schedules.put(employeeId, "Shift Manager");
        else throw new Exception("Shift already full with shift managers");
    }


    private void addCashier(String employeeId) throws Exception {
        if (employeeAssign.get("Cashier") < numberOfRolesPerShift.get("Cashier")) schedules.put(employeeId, "Cashier");
        else throw new Exception("Shift already full with cashiers");
    }
    private void addGeneralEmployee(String employeeId) throws Exception {
        if (employeeAssign.get("General Employee") < numberOfRolesPerShift.get("General Employee"))
            schedules.put(employeeId, "General Employee");
        else throw new Exception("Shift already full with general employees");
    }
    private void addStorekeeper(String employeeId) throws Exception {
        if (employeeAssign.get("Storekeeper") < numberOfRolesPerShift.get("Storekeeper"))
            schedules.put(employeeId, "Storekeeper");
        else throw new Exception("Shift already full with storekeepers");
    }
    private void addDriver(String employeeId) throws Exception {
        if (employeeAssign.get("Driver") < numberOfRolesPerShift.get("Driver"))
            schedules.put(employeeId, "Driver");
        else throw new Exception("Shift already full with drivers");
    }
}

