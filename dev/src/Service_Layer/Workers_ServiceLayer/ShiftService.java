package Service_Layer.Workers_ServiceLayer;


import workers.DomainLayer.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class ShiftService {
    private final ShiftController shiftController;

    public ShiftService(ShiftController _shiftController) {
        shiftController = _shiftController;
    }

    public String getDateTypeBranchForShift(int shiftId) {
        try {
            List<String> shiftDetails = shiftController.getDateTypeBranchForShift(shiftId);
            return shiftId + ":" + shiftDetails.get(0) + "," + shiftDetails.get(1) + "," + shiftDetails.get(2);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String getSchedules(int shiftId) throws Exception{
        return shiftController.getSchedules(shiftId);
    }

    public String getPayment(String employeeId) throws Exception {
        return shiftController.getPayment(employeeId);
    }


    public String showFutureShiftsOfNextWeek() {
        return shiftController.showFutureShiftsOfNextWeek();
    }

    public String showEmployeeFutureShifts(String employeeId) throws Exception {
        return shiftController.showEmployeeFutureShifts(employeeId);
    }

    public String displayEmployeesForShift(int shiftId) throws Exception{
        return shiftController.displayEmployeesForShift(shiftId);
    }


    public String setDate(int shiftId, LocalDateTime date) throws Exception {
        shiftController.setDate(shiftId, date);
        return "Date was changed successfully";
    }


    public String addShift(LocalDateTime date, boolean isMorningShift, int shiftManagersCount, int cashiersCount, int generalEmployeesCount, int storekeeperCount, int driverCount, String branchAddress) throws  Exception{
        shiftController.addShift(date, isMorningShift, shiftManagersCount, cashiersCount, generalEmployeesCount, storekeeperCount, driverCount, branchAddress);
        return "success";
    }

    public String isValidDate(int year, int month, int day) throws Exception{
        shiftController.isValidDate(year, month, day);
        return "success";
    }


    public String removeShift(int shiftId) throws Exception{
        shiftController.removeShift(shiftId);
        return "Shift removed successfully";
    }

    public String removeSchedule(String employeeId, int shiftId, String role) throws Exception{
        shiftController.removeSchedule(employeeId, shiftId, role);
        return "Remove schedule by role successfully";
    }


    public String changeStartHourOfShift(int shiftId, LocalTime newHour) throws Exception{
        shiftController.changeStartHourOfShift(shiftId, newHour);
        return "Change hour of shift successfully";
    }

    public String changeScheduleByRole(String employeeId, int shiftId, String role) throws Exception{
        shiftController.changeScheduleByRole(employeeId, shiftId, role);
        return "Change schedule by role successfully";
    }

    public String validateDate(LocalDateTime date) throws Exception{
        shiftController.validateDate(date);
        return "success";
    }

    public String scheduleEmployeeToRole (String employeeId, int shiftId, String role) throws Exception{
            shiftController.scheduleEmployeeToRole(employeeId, shiftId, role);
            return "Schedule employee to role successfully";
    }


    public String isEmployeeAvailable(int shiftId, String employeeId) throws Exception{
        return shiftController.isEmployeeAvailable(shiftId, employeeId);
    }

    public String availableEmployeesId(int shiftId) throws Exception {
        return shiftController.availableEmployeesId(shiftId);
    }
    public String availableEmployeesIdNotScheduledForShift(int shiftId) throws Exception {
        return shiftController.availableEmployeesIdNotScheduledForShift(shiftId);
    }

    public String showEmployeesChosenForShift(int shiftId) throws Exception {
        return shiftController.showEmployeesChosenForShift(shiftId);
    }

    public String showAllShifts(){
        return shiftController.showAllShifts();
    }

    public String getEmployeeAssignByShiftId(int shiftId) throws Exception {
        return shiftController.getEmployeeAssignByShiftId(shiftId);
    }
    public String showEmployeeAssignmentsByRoleForShift(int shiftId, String _role) throws Exception {
        return shiftController.showEmployeeAssignByRoleForShift(shiftId, _role);
    }
    public String getEmployeeNeededByShiftId(int shiftId) throws Exception {
        return shiftController.getEmployeeNeededByShiftId(shiftId);
    }

    public String addConstraint(String employeeId, int shiftId, String description) throws Exception{
        shiftController.addConstraint(employeeId, shiftId, description);
        return "Constraint added successfully";
    }

    public String deleteConstraint(String employeeId, int shiftId) throws Exception{
        shiftController.deleteConstraintToEmployee(employeeId, shiftId);
        return "Constraint deleted successfully";
    }
    public String showConstraintsForShift(int shiftId) throws Exception{
        return shiftController.showConstraintsForShift(shiftId);
    }


}
