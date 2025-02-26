package workers.DomainLayer;

import workers.DataAccessLayer.DALFacade;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.*;



public class ShiftController {
    private final BranchController branchController;
    private final EmployeeController employeeController;
    private int shiftId = 1;
    private HashMap<Integer, Shift> shifts; // <shiftId, Shift>
    private Map<Map<String, String>, Map<String, Boolean>> availabilityMap; // <<Day, TypeOfShift>, <EmployeeId, Availability>>
    private final DALFacade dalController;


    public ShiftController(EmployeeController _employeeController, BranchController _branchController) {
        shifts = new HashMap<>();
        employeeController = _employeeController;
        branchController = _branchController;
        availabilityMap = new HashMap<>();
        dalController= DALFacade.getInstance();
        loadData();



        // Initialize the availability for each day of the week and shift type
        String[] daysOfWeekWithoutSaturday = {"sunday", "monday", "tuesday", "wednesday", "thursday", "friday"};
        String[] shiftTypes = {"Morning", "Evening"};


        for (String day : daysOfWeekWithoutSaturday) {
            for (String shiftType : shiftTypes) {
                Map<String, String> dayAndShiftType = new HashMap<>();
                dayAndShiftType.put(day, shiftType);
                dayAndShiftType = Collections.unmodifiableMap(dayAndShiftType);  // Make the map immutable

                Map<String, Boolean> employeeAvailability = new HashMap<>();
                // Initialize availability for each employee to false by default
                for (String employeeId : employeeController.getEmployees().keySet()) {
                    employeeAvailability.put(employeeId, true); // Default to true (employee can work)
                }

                availabilityMap.put(dayAndShiftType, employeeAvailability);
            }
        }

    }

    public void loadData(){
        try{
            HashMap <String,Integer> counters = dalController.getAllCounters();
            setShiftId(counters.get("nextShiftId"));

            HashMap<Integer,Shift> shift = dalController.getAllShifts();
            for(Integer id:shift.keySet()){
                shifts.put(id,shift.get(id));
            }
        }
        catch (Exception e){
            throw new RuntimeException("DAL ERROR - ShiftController");
        }
    }


    public Map<Map<String, String>, Map<String, Boolean>> getAvailabilityMap() {
        return availabilityMap;
    }


    public void setAvailability(String day, String typeOfShift, String employeeId, boolean availability) throws Exception {
        String dayLower = day.toLowerCase();

        for (Map.Entry<Map<String, String>, Map<String, Boolean>> entry : availabilityMap.entrySet()) {
            Map<String, String> dayAndShiftType = entry.getKey();
            if (dayAndShiftType.containsKey(dayLower) && dayAndShiftType.get(dayLower).equals(typeOfShift)) {
                Map<String, Boolean> employeeAvailability = entry.getValue();
                if (employeeAvailability.containsKey(employeeId)) {
                    employeeAvailability.put(employeeId, availability);
                    return;
                } else {
                    throw new Exception("Employee ID not found in availability map");
                }
            }
        }
        throw new Exception("Day and Shift type combination not found in availability map");
    }

    public HashMap<Integer, Shift> getShifts() {
        return shifts;
    }


    public void InitializingTheMap() {
        for (Map<String, Boolean> employeeAvailability : availabilityMap.values()) {
            for (String employeeId : employeeAvailability.keySet()) {
                employeeAvailability.put(employeeId, true);
            }
        }
    }

    public Shift getshiftByDateAndIsMorningShift(LocalDateTime date, boolean isMorningShift) throws Exception {
        for (Shift shift : shifts.values()) {
            if (shift.getDate().isEqual(date) && shift.isMorningShift() == isMorningShift) {
                return shift;
            }
        }
        throw new Exception("Shift not found");
    }


    public boolean isShiftExists(int shiftId) {
        return shifts.containsKey(shiftId);
    }

    public List<String> getDateTypeBranchForShift(int shiftId) throws Exception {
        if (!isShiftExists(shiftId)) throw new Exception("Shift does not exist");
        Shift shift = shifts.get(shiftId);
        List<String> shiftDetails = new LinkedList<>();
        shiftDetails.add(shift.getDate().toString());
        if (shift.isMorningShift()) {
            shiftDetails.add("Morning");
        } else shiftDetails.add("Evening");
        shiftDetails.add(shift.getBranchName());
        return shiftDetails;
    }

    private String getSchedulesRoleAndId(int shiftId, String role) throws Exception {
        if (!isShiftExists((shiftId))) throw new Exception("Shift does not exist");
        if (employeeController.isRoleExist(role).equals("false")) throw new Exception("Role doesn't exist");
        Shift shift = shifts.get(shiftId);
        for (String id : shift.getSchedules().keySet()) {
            if (shift.getSchedules().get(id).equals(role)) return role + ":" + id;
        }
        return "";
    }

    // StringBuilder for role and id for a specific shift
    public String getSchedules(int shiftId) throws Exception {
        StringBuilder schedules = new StringBuilder();
        if (!isShiftExists((shiftId))) throw new Exception("Shift does not exist");
        Shift shift = shifts.get(shiftId);
        for (String role : shift.getNumberOfRolesPerShift().keySet()) {
            schedules.append(getSchedulesRoleAndId(shiftId, role)).append("\n");
        }
        return schedules.toString();
    }

    private int getShiftHours(int shiftId) throws Exception {
        if (!isShiftExists(shiftId)) throw new Exception("Shift does not exist");
        Shift shift = shifts.get(shiftId);
        String branchName = shift.getBranchName();
        if (!branchController.getBranches().containsKey(branchName)) throw new Exception("Branch is not exist");
        Branch branch = branchController.getBranches().get(branchName);
        if (shift.isMorningShift()) {
            int startHour = shift.getDate().getHour();
            int endHour = branch.getMorningShiftEndHour().getHour();
            return endHour - startHour;
        } else {
            int startHour = shift.getDate().getHour();
            int endHour = branch.getEveningShiftEndHour().getHour();
            return endHour - startHour;
        }
    }

    public String shiftHistoryPerEmployee(String employeeId) throws Exception {
        if (!employeeController.employeeExists(employeeId)) throw new Exception("Employee does not exist");
        StringBuilder history = new StringBuilder();
        for (Shift shift : shifts.values()) {
            if (shift.getSchedules().containsKey(employeeId) && !shift.getSchedules().get(employeeId).isEmpty()) {
                String role = shift.getSchedules().get(employeeId);
                String shiftType = shift.isMorningShift() ? "Morning" : "Evening";
                history.append("Date: ").append(shift.getDate())
                        .append(" Type: ").append(shiftType)
                        .append(" Branch: ").append(shift.getBranchName())
                        .append(" Role: ").append(role)
                        .append("\n");
            }
        }
        return history.toString();
    }

    public String shiftHistoryOfAllEmployees() throws Exception {
        StringBuilder allEmployeesHistory = new StringBuilder();
        for (Employee employee : employeeController.getEmployees().values()) {
            String employeeId = employee.getEmployeeId();
            String employeeHistory = shiftHistoryPerEmployee(employeeId);
            allEmployeesHistory.append("Employee ID: ").append(employeeId).append(employeeHistory)
                    .append("\n");
        }
        return allEmployeesHistory.toString();
    }


    public String getPayment(String employeeId) throws Exception {
        if (!employeeController.employeeExists(employeeId)) throw new Exception("Employee does not exist");

        int payment = 0;
        LocalDateTime now = LocalDateTime.now();
        int monthToPay = now.getMonthValue();
        if (now.getDayOfMonth() == 1)
            if (monthToPay == 1) monthToPay = 12;
            else monthToPay -= 1;

        Employee employee = employeeController.getEmployees().get(employeeId);
        for (int id : shifts.keySet()) {
            Shift shift = shifts.get(id);
            if (shift.getSchedules().containsKey(employeeId) && shift.getDate().getMonthValue() == monthToPay && shift.getDate().isAfter(now.withDayOfMonth(1)))
                payment = payment + employee.getSalary() * getShiftHours(id);
        }
        return Integer.toString(payment);
    }

    public String showFutureShiftsOfNextWeek() {
        StringBuilder allShifts = new StringBuilder();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfNextWeek = now.with(TemporalAdjusters.next(DayOfWeek.SUNDAY)).withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endOfNextWeek = startOfNextWeek.plusDays(7);

        for (int id : shifts.keySet()) {
            Shift shift = shifts.get(id);
            LocalDateTime shiftDate = shift.getDate();
            if (shiftDate.compareTo(startOfNextWeek) >= 0 && shiftDate.compareTo(endOfNextWeek) < 0) {
                String shiftType = shift.isMorningShift() ? "Morning" : "Evening";
                allShifts.append(id)
                        .append(" Date: ").append(shiftDate)
                        .append(" Type: ").append(shiftType)
                        .append(" Branch: ").append(shift.getBranchName())
                        .append("\n");
            }
        }
        return allShifts.toString();
    }

    public String displayEmployeesForShift(int shiftId) throws Exception {
        if (!isShiftExists(shiftId))
            throw new Exception("Shift does not exist");

        StringBuilder chosenEmployees = new StringBuilder();
        Shift chosenShift = shifts.get(shiftId);

        for (String employeeId : chosenShift.getSchedules().keySet()) {
            Employee employee = employeeController.getEmployees().get(employeeId);
            chosenEmployees.append("ID: ").append(employeeId)
                    .append(" First name: ").append(employee.getFirstName())
                    .append(" Last Name: ").append(employee.getLastName())
                    .append("\n");
        }

        return chosenEmployees.toString();
    }


    public String showEmployeeFutureShifts(String employeeId) throws Exception {
        StringBuilder futureShifts = new StringBuilder();
        LocalDateTime now = LocalDateTime.now();

        for (Shift shift : shifts.values()) {
            if (shift.getDate().compareTo(now) > 0 && shift.getSchedules().containsKey(employeeId)) {
                String role = shift.getSchedules().get(employeeId);
                String shiftType = shift.isMorningShift() ? "Morning" : "Evening";
                futureShifts.append("Date: ").append(shift.getDate())
                        .append(" Type: ").append(shiftType)
                        .append(" Branch: ").append(shift.getBranchName())
                        .append(" Role: ").append(role)
                        .append("\n");
            }
        }
        if (futureShifts.length() == 0) {
            throw new Exception("No future shifts.");
        }
        return futureShifts.toString();
    }

    // Method to validate the date
    public void validateDate(LocalDateTime date) throws Exception {
        if (date == null) throw new Exception("Date cannot be null");

        int year = date.getYear();
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();

        if (year < 1 || month < 1 || month > 12 || day < 1 || day > LocalDate.of(year, month, 1).lengthOfMonth()) {
            throw new Exception("Invalid date");
        }
    }

    public void setDate(int shiftId, LocalDateTime newDate) throws Exception {
        if (!isShiftExists(shiftId)) throw new Exception("Shift does not exist");

        validateDate(newDate);

        Shift shift = shifts.get(shiftId);
        String branchName = shift.getBranchName();
        Branch branch = branchController.getBranches().get(branchName);

        LocalTime shiftStartTime;
        LocalTime shiftEndTime;
        if (shift.isMorningShift()) {
            shiftStartTime = branch.getMorningShiftStartHour();
            shiftEndTime = branch.getMorningShiftEndHour();
        } else {
            shiftStartTime = branch.getEveningShiftStartHour();
            shiftEndTime = branch.getEveningShiftEndHour();
        }

        LocalTime newTime = newDate.toLocalTime();
        if (newTime.isBefore(shiftStartTime) || newTime.isAfter(shiftEndTime)) {
            throw new Exception("Branch is not working at this time");
        }

        for (Shift existingShift : shifts.values()) {
            LocalDateTime existingShiftDate = existingShift.getDate();
            String existShiftType = existingShift.isMorningShift() ? "Morning" : "Evening";
            String shitType = shift.isMorningShift() ? "Morning" : "Evening";
            if (existingShiftDate.toLocalDate().isEqual(newDate.toLocalDate()) && !existShiftType.equals(shitType)) {
                throw new Exception("Shift already exist for the same time");
            }
        }
        shift.setDate(newDate);
        dalController.updateShift(shifts.get(shiftId),"Date");
    }


    public void addShift(LocalDateTime date, boolean isMorningShift, int shiftManagersCount, int cashiersCount, int generalEmployeesCount, int storekeepersCount, int driversCount, String branchAddress) throws Exception {
        validateDate(date);
        if (!branchController.isBranchAddressExists(branchAddress))
            throw new Exception("Branch address does not exist");
        Branch branch = branchController.getBranches().get(branchAddress);
        LocalDateTime shiftStart;
        if (isMorningShift) {
            shiftStart = date.withHour(branch.getMorningShiftStartHour().getHour()).withMinute(branch.getMorningShiftStartHour().getMinute());
        } else {
            shiftStart = date.withHour(branch.getEveningShiftStartHour().getHour()).withMinute(branch.getEveningShiftStartHour().getMinute());
        }
        if (shiftManagersCount < 1) throw new Exception("Shift managers count must be one or more");
        if (cashiersCount < 0) throw new Exception("Cashiers count must be zero or more");
        if (generalEmployeesCount < 0) throw new Exception("General employees count must be zero or more");
        if (driversCount > 0 && storekeepersCount < 1) throw new Exception("There must be a storekeeper to accept the delivery from the driver");
        for (Shift existingShift : shifts.values()) {
            if (existingShift.getDate().toLocalDate().isEqual(date.toLocalDate()) && (existingShift.isMorningShift() == isMorningShift)) {
                throw new Exception("Shift already exists for the same time");
            }
        }
        Shift newShift = new Shift(shiftId, shiftStart, branch, isMorningShift);
        newShift.createSchedulesMap(shiftManagersCount, cashiersCount, generalEmployeesCount, storekeepersCount, driversCount);
        shifts.put(shiftId, newShift);
        dalController.insertShift(newShift);
        shiftId++;
        dalController.updateCounter("nextShiftId",shiftId);
    }

    public String availableEmployeesId(int shiftId) throws Exception {
        if (!isShiftExists(shiftId)) throw new Exception("Shift does not exist");
        StringBuilder result = new StringBuilder();
        for (String employeeId : employeeController.getEmployees().keySet()) {
            boolean existShiftId = false;
            Employee employee = employeeController.getEmployees().get(employeeId);
            for (int i = 0; i < employee.getConstraints().size() && !existShiftId; i++) {
                if (employee.getConstraints().get(i).getShiftId() == shiftId) existShiftId = true;
            }
            if (!existShiftId)
                result.append("Id: " + employeeId + " Name: " + employee.getFirstName() + " " + employee.getLastName() + "\n");

        }
        return result.toString();
    }
    public String availableEmployeesIdNotScheduledForShift(int shiftId) throws Exception {
        if (!isShiftExists(shiftId)) throw new Exception("Shift does not exist");
        StringBuilder result = new StringBuilder();
        for (String employeeId : employeeController.getEmployees().keySet()) {
            if (shifts.get(shiftId).getSchedules().containsKey(employeeId)) continue;
            boolean existShiftId = false;
            Employee employee = employeeController.getEmployees().get(employeeId);
            for (int i = 0; i < employee.getConstraints().size() && !existShiftId; i++) {
                if (employee.getConstraints().get(i).getShiftId() == shiftId) existShiftId = true;
            }
            if (!existShiftId)
                result.append("Id: " + employeeId + " Name: " + employee.getFirstName() + " " + employee.getLastName() + "\n");

        }
        return result.toString();
    }



    public void removeSchedule(String employeeId, int shiftId, String role) throws Exception {
        if (!employeeController.getEmployees().containsKey(employeeId)) throw new Exception("Employee does not exist");
        if (!isShiftExists(shiftId)) throw new Exception("Shift does not exist");

        String normalizedRole = normalizeRole(role);
        if (employeeController.isRoleExist(normalizedRole) == "false") throw new Exception("Role does not exist");

        Shift shift = shifts.get(shiftId);
        int schedulesSize = shift.getSchedules().size();
        for (String id : shift.getSchedules().keySet()) {
            if (employeeController.getEmployees().get(employeeId).getEmployeeId().equals(id)){ //the employee work in this shift
                int countRoleBeforeSchedule=shift.getEmployeeAssign().get(normalizedRole);
                dalController.deleteSchedule(shiftId,employeeId);
                dalController.updateRoleCount(shiftId,normalizedRole,countRoleBeforeSchedule - 1,"CountAssigned");
                shift.removeSchedule(employeeId, normalizedRole);
            }

        }
        if (schedulesSize == shift.getSchedules().size())
            throw new Exception("Employee doesn't exist in this shift");
    }

    public void removeShift(int shiftId) throws Exception {
        if (!isShiftExists(shiftId)) throw new Exception("Shift does not exist");
        Shift shift = shifts.get(shiftId);
        shift.getNumberOfRolesPerShift().clear();
        shift.getSchedules().clear();
        shift.getEmployeeAssign().clear();
        shifts.remove(shiftId);
    }

    public void changeStartHourOfShift(int shiftId, LocalTime newHour) throws Exception {
        if (!isShiftExists(shiftId)) throw new Exception("Shift does not exist");
        Shift shift = shifts.get(shiftId);
        shift.changeHour(newHour);
    }

    public void changeScheduleByRole(String employeeId, int shiftId, String role) throws Exception {
        String _role;

        if (!employeeController.employeeExists(employeeId)) throw new Exception("Employee does not exist");
        if (!isShiftExists(shiftId)) throw new Exception("Shift does not exist");
        if (!shifts.get(shiftId).getSchedules().containsKey(employeeId))
            throw new Exception("Employee does not work at this shift");
        if (shifts.get(shiftId).getSchedules().containsKey(employeeId) && shifts.get(shiftId).getSchedules().get(employeeId).equals(""))
            throw new Exception("Employee was not scheduled yet");
        if (employeeController.isRoleExist(role).equals("false")) throw new Exception("Role does not exist");

        switch (role.toLowerCase()) {
            case "shift manager":
                _role = "Shift manager";
                break;
            case "general employee":
                _role = "General employee";
                break;
            case "cashier":
                _role = "Cashier";
                break;
            case "storekeeper":
                _role = "Storekeeper";
                break;
            case "driver":
                _role = "Driver";
                break;
            default:
                if (Character.isLowerCase(role.charAt(0)))
                    _role = Character.toUpperCase(role.charAt(0)) + role.substring(1);
                _role = role;
        }


        Employee employee = employeeController.getEmployees().get(employeeId);
        if (!employee.getRoles().contains(_role)) throw new Exception("Employee cannot work as " + _role);

        Shift shift = shifts.get(shiftId);
        shift.changeSchedule(employeeId, _role);
    }


    public boolean isShiftExistForEmployeeAtDate(String employeeId, LocalDateTime date) {
        for (Shift shift : shifts.values()) {
            if (shift.getSchedules().containsKey(employeeId) && !shift.getSchedules().get(employeeId).isEmpty() && shift.getDate().isEqual(date)) {
                return true;
            }
        }
        return false;
    }

    public void scheduleEmployeeToRole(String employeeId, int shiftId, String role) throws Exception {
        if (!employeeController.employeeExists(employeeId)) throw new Exception("Employee does not exist");

        if (!shifts.containsKey(shiftId)) throw new Exception("Shift does not exist");

        String normalizedRole = normalizeRole(role);

        if (employeeController.isRoleExist(normalizedRole) == "false") throw new Exception("Role does not exist");

        Shift shift = shifts.get(shiftId);
        Employee employee = employeeController.getEmployees().get(employeeId);

        if (shift.getSchedules().containsKey(employeeId) && !shift.getSchedules().get(employeeId).equals(""))
            throw new Exception("Employee already exist in this shift");

        LocalDateTime now = LocalDateTime.now();
        if (shift.getDate().isBefore(now)) throw new Exception("Your time to schedule is up");

        if (shift.getDate().compareTo(employee.getStartDate()) < 0)
            throw new Exception("Employee cannot work at shifts yet");

        String availableEmployees = availableEmployeesId(shiftId);
        if (!availableEmployees.contains(employeeId))
            throw new Exception("The employee " + employee.getFirstName() + " " + employee.getLastName() + " cannot work at this shift during their constraints");

        int countRoleBeforeSchedule=shift.getEmployeeAssign().get(normalizedRole);

        dalController.insertSchedule(shiftId,employeeId,normalizedRole);
        dalController.updateRoleCount(shiftId,normalizedRole,countRoleBeforeSchedule + 1,"CountAssigned");
        shift.addSchedule(employeeId, normalizedRole);
    }

    private String normalizeRole(String role) {
        switch (role.toLowerCase()) {
            case "shift manager":
                return "Shift manager";
            case "cashier":
                return "Cashier";
            case "general employee":
                return "General employee";
            case "storekeeper":
                return  "Storekeeper";
            case "driver":
                return  "Driver";
            default:
                return Character.toUpperCase(role.charAt(0)) + role.substring(1);
        }
    }

    public String isEmployeeAvailable(int shiftId, String employeeId) throws Exception {
        boolean result = availableEmployeesId(shiftId).contains(employeeId);
        return Boolean.toString(result);
    }

    private boolean isLeapYear(int year) {
        return ((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0);
    }

    public void isValidDate(int year, int month, int day) throws Exception {
        if (year > 9999 || year < 1000) throw new Exception("Year is not valid");
        if (month < 1 || month > 12) throw new Exception("Month is not valid");
        if (day < 1 || day > 31) throw new Exception("Day is not valid");
        if (month == 2) {
            if (isLeapYear(year) && day > 29) throw new Exception("Day is not valid during leap year at February");
            else if (day > 28) throw new Exception("Day is not valid at February");
        }
        if ((month == 4 || month == 6 || month == 9 || month == 11) && day > 30)
            throw new Exception("Day Or Month is not valid and needs to be 30 and less");
    }

    public String showEmployeesChosenForShift(int shiftId) {
        StringBuilder resultBuilder = new StringBuilder();
        try {
            if (!shifts.containsKey(shiftId)) {
                throw new Exception("Shift does not exist");
            }

            Shift shift = shifts.get(shiftId);
            Map<String, String> schedules = shift.getSchedules();

            if (schedules.isEmpty()) {
                resultBuilder.append("No employees scheduled for this shift.");
            } else {
                resultBuilder.append("Employees scheduled for shift ").append(shiftId).append(":\n");
                for (Map.Entry<String, String> entry : schedules.entrySet()) {
                    String employeeId = entry.getKey();
                    Employee employee = employeeController.getEmployees().get(employeeId);
                    String name = employee.getFirstName();
                    String lastName = employee.getLastName();
                    String role = employee.getRoles().toString();
                    resultBuilder.append("Employee ID: ").append(employeeId).append(", Name: ").append(name).append(" ").append(lastName).append(", Role: ").append(role).append("\n");
                }
            }
        } catch (Exception e) {
            resultBuilder.append("Error: ").append(e.getMessage());
        }
        return resultBuilder.toString();
    }


    public String showAllShifts() {
        StringBuilder allShifts = new StringBuilder();

        if (shifts.isEmpty()) {
            return "No shifts available.";
        }

        for (Map.Entry<Integer, Shift> entry : shifts.entrySet()) {
            int shiftId = entry.getKey();
            Shift shift = entry.getValue();
            String shiftType = shift.isMorningShift() ? "Morning" : "Evening";
            allShifts.append("Shift ID: ").append(shiftId)
                    .append("\nDate: ").append(shift.getDate())
                    .append("\nType: ").append(shiftType)
                    .append("\nBranch name: ").append(shift.getBranchName())
                    .append("\nBranch address: ").append(shift.getBranchAddress())
                    .append("\n\n");
        }

        return allShifts.toString();
    }

    public String getEmployeeAssignByShiftId(int shiftId) throws Exception {
        Shift shift = shifts.get(shiftId);
        if (shift == null) {
            throw new Exception("Shift with ID " + shiftId + " not found.");
        }
        HashMap<String, Integer> employeeAssign = shift.getEmployeeAssign();
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, Integer> entry : employeeAssign.entrySet()) {
            result.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        return result.toString();
    }
    public String getEmployeeNeededByShiftId(int shiftId) throws Exception {
        Shift shift = shifts.get(shiftId);
        if (shift == null) {
            throw new Exception("Shift with ID " + shiftId + " not found.");
        }
        HashMap<String, Integer> employeeNeeded = shift.getNumberOfRolesPerShift();
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, Integer> entry : employeeNeeded.entrySet()) {
            result.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        return result.toString();
    }
    public void addConstraint(String employeeId, int shiftId, String description) throws Exception {
        if (!employeeController.employeeExists(employeeId)) throw new Exception("Employee does not exist");
        if (!shifts.containsKey(shiftId)) throw new Exception("Shift does not exist");
        if (shifts.get(shiftId).getSchedules().containsKey(employeeId)) throw new Exception("Employee is already scheduled for this shift");
        if (employeeController.getEmployees().get(employeeId).containsConstraint(shiftId)) throw new Exception("A constraint already existed for this employee in this shift");

        LocalDate currentDate = LocalDate.now();
        LocalDate shiftDate = LocalDate.from(shifts.get(shiftId).getDate());
        long daysBetween = Math.abs(currentDate.toEpochDay() - shiftDate.toEpochDay());

        switch (currentDate.getDayOfWeek()) {
            case SUNDAY:
                if (daysBetween <= 13) {
                    throw new Exception("It is not possible to enter constraint for shifts until about a week before the start of the shift week.");
                }
                break;
            case MONDAY:
                if (daysBetween <= 12) {
                    throw new Exception("It is not possible to enter constraint for shifts until about a week before the start of the shift week.");
                }
                break;
            case TUESDAY:
                if (daysBetween <= 11) {
                    throw new Exception("It is not possible to enter constraint for shifts until about a week before the start of the shift week.");
                }
                break;
            case WEDNESDAY:
                if (daysBetween <= 10) {
                    throw new Exception("It is not possible to enter constraint for shifts until about a week before the start of the shift week.");
                }
                break;
            case THURSDAY:
                if (daysBetween <= 9) {
                    throw new Exception("It is not possible to enter constraint for shifts until about a week before the start of the shift week.");
                }
                break;
            case FRIDAY:
                if (daysBetween <= 8) {
                    throw new Exception("It is not possible to enter constraint for shifts until about a week before the start of the shift week.");
                }
                break;
            case SATURDAY:
                if (daysBetween <= 7) {
                    throw new Exception("It is not possible to enter constraint for shifts until about a week before the start of the shift week.");
                }
                break;
        }
        EmployeeConstraint ec = new EmployeeConstraint(shiftId, description);
        employeeController.getEmployees().get(employeeId).addConstraint(ec);
        dalController.insertEmployeeConstraint(ec);
        dalController.insertConstraintToEmployee(employeeController.getEmployees().get(employeeId),ec);
    }

    public void deleteConstraintToEmployee(String employeeId, int shiftId) throws Exception {
        if (!employeeController.employeeExists(employeeId)) throw new Exception("Employee does not exist");
        if (!shifts.containsKey(shiftId)) throw new Exception("Shift does not exist");

        Employee employee = employeeController.getEmployees().get(employeeId);
        if (!employee.containsConstraint(shiftId)) throw new Exception("No constraint found for the employee in this shift");

        for (EmployeeConstraint employeeConstraint : employeeController.getEmployees().get(employeeId).getConstraints()) {
            if (employeeConstraint.getShiftId() == shiftId) {
                dalController.deleteConstraintToEmployee(employeeController.getEmployees().get(employeeId), employeeConstraint);
                dalController.deleteEmployeeConstraint(employeeConstraint);
                employee.removeConstraint(employeeConstraint.getConstraintId());
            }
        }
    }

    public String showConstraintsForShift(int shiftId) throws Exception {
        if (!shifts.containsKey(shiftId)) throw new Exception("Shift does not exist");

        StringBuilder constraintsList = new StringBuilder();
        constraintsList.append("Constraints for Shift ID: ").append(shiftId).append("\n");

        for (Employee employee : employeeController.getEmployees().values()) {
            if (employee.containsConstraint(shiftId)) {
                constraintsList.append("Employee ID: ").append(employee.getEmployeeId()).append("\n");
            }
        }

        return constraintsList.toString();
    }


    public String showEmployeeAssignByRoleForShift(int shiftId, String role) throws Exception {
        if (!isShiftExists(shiftId)) throw new Exception("Shift does not exist");
        StringBuilder result = new StringBuilder();

        String normalizedRole = normalizeRole(role);
        if (employeeController.isRoleExist(normalizedRole) == "false") throw new Exception("Role does not exist");

        for (String employeeId : employeeController.getEmployees().keySet()) {
            if (shifts.get(shiftId).getSchedules().containsKey(employeeId))
                continue;
            boolean existShiftId = false;
            boolean existRole = false;
            Employee employee = employeeController.getEmployees().get(employeeId);

            for (int i = 0; i < employee.getConstraints().size() && !existShiftId; i++) {
                if (employee.getConstraints().get(i).getShiftId() == shiftId ) existShiftId = true;
            }

            for (int i = 0; i < employee.getRoles().size() && !existRole; i++) {
                if (employee.getRoles().contains(normalizedRole)) existRole = true;
            }

            if (!existShiftId && existRole)
                result.append("Id: " + employeeId + " Name: " + employee.getFirstName() + " " + employee.getLastName() + "\n");

        }
        return result.toString();
    }
    public String chooseDriverAvailableForShift(int shiftId) throws Exception {
        if (!isShiftExists(shiftId)) throw new Exception("Shift does not exist");

        List<String> availableDrivers = new ArrayList<>();

        for (String employeeId : employeeController.getEmployees().keySet()) {
            Employee employee = employeeController.getEmployees().get(employeeId);

            boolean existRole = false;
            boolean existShiftId = false;

            if (employee.getRoles().contains("Driver")) existRole = true;

            for (int i = 0; i < employee.getConstraints().size() && !existShiftId; i++) {
                if (employee.getConstraints().get(i).getShiftId() == shiftId ) existShiftId = true;
            }

            if (shifts.get(shiftId).getSchedules().containsKey(employeeId)) {
                if(existRole = true) {
                    return employeeId+"exist";
                }
                continue;
            }


            if (!existShiftId && existRole) {
                availableDrivers.add(employeeId);
            }

        }

        if (availableDrivers.isEmpty()) {
            throw new Exception("No available drivers for this shift");
        }

        Random random = new Random();
        String randomDriverId = availableDrivers.get(random.nextInt(availableDrivers.size()));

        return randomDriverId;
    }
    public String addDriverToShift(LocalDateTime date) throws Exception {
        if(!isShiftExists(date, true)) {
            addShift(date, true, 1, 1, 1, 1, 1, "Ramot 1");
        };
        int shiftId = getshiftByDateAndIsMorningShift(date, true).getShiftId();
        String driverId = chooseDriverAvailableForShift(shiftId);
        if(isNumeric(driverId)){
            scheduleEmployeeToRole(driverId, shiftId, "Driver");
        }
        else{
            driverId = driverId.substring(0,9);
        }
        return driverId;
    }
    public boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public boolean isShiftExists(LocalDateTime dateTime, boolean isMorningShift) {
        for (Shift shift : shifts.values()) {
            LocalDateTime shiftTime = shift.getDate().withYear(shift.getDate().getYear()).withMonth(shift.getDate().getMonthValue()).withDayOfMonth(shift.getDate().getDayOfMonth());
            LocalDateTime dateTimeWithoutHour = dateTime.withYear(dateTime.getYear()).withMonth(dateTime.getMonthValue()).withDayOfMonth(dateTime.getDayOfMonth());
            if (shiftTime.isEqual(dateTimeWithoutHour)){
                if(shift.isMorningShift() == isMorningShift) {
                    return true;
                }
            }
        }
        return false;
    }
    private void setShiftId(int shiftId) {
        this.shiftId = shiftId;
    }


}
