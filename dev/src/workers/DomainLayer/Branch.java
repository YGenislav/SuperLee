package workers.DomainLayer;

import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

public class Branch {
    private String branchName;
    private String branchAddress;
    private LocalTime morningShiftStartHour;
    private LocalTime eveningShiftStartHour;
    private LocalTime morningShiftEndHour;
    private LocalTime eveningShiftEndHour;
    private final List<String> timeOfShifts;

    public Branch(String _branchName, String _branchAddress, LocalTime _morningShiftStartHour, LocalTime _eveningShiftStartHour, LocalTime _morningShiftEndHour, LocalTime _eveningShiftEndHour) {
        branchName = _branchName;
        branchAddress = _branchAddress;
        morningShiftStartHour = _morningShiftStartHour;
        eveningShiftStartHour = _eveningShiftStartHour;
        morningShiftEndHour = _morningShiftEndHour;
        eveningShiftEndHour = _eveningShiftEndHour;
        timeOfShifts = new LinkedList<>();
    }

    public Branch(String _branchName, String _branchAddress, LocalTime _morningShiftStartHour, LocalTime _eveningShiftStartHour, LocalTime _morningShiftEndHour, LocalTime _eveningShiftEndHour,List<String> _timeOfShifts) {
        branchName = _branchName;
        branchAddress = _branchAddress;
        morningShiftStartHour = _morningShiftStartHour;
        eveningShiftStartHour = _eveningShiftStartHour;
        morningShiftEndHour = _morningShiftEndHour;
        eveningShiftEndHour = _eveningShiftEndHour;
        timeOfShifts = _timeOfShifts;
    }

    public LocalTime getEveningShiftEndHour() {
        return eveningShiftEndHour;
    }

    public void setEveningShiftEndHour(LocalTime eveningShiftEndHour) {
        this.eveningShiftEndHour = eveningShiftEndHour;
    }

    public LocalTime getMorningShiftEndHour() {
        return morningShiftEndHour;
    }

    public void setMorningShiftEndHour(LocalTime morningShiftEndHour) {
        this.morningShiftEndHour = morningShiftEndHour;
    }

    public LocalTime getEveningShiftStartHour() {
        return eveningShiftStartHour;
    }

    public void setEveningShiftStartHour(LocalTime eveningShiftStartHour) {
        this.eveningShiftStartHour = eveningShiftStartHour;
    }

    public LocalTime getMorningShiftStartHour() {
        return morningShiftStartHour;
    }

    public void setMorningShiftStartHour(LocalTime morningShiftStartHour) {
        this.morningShiftStartHour = morningShiftStartHour;
    }

    public String getBranchAddress() {
        return branchAddress;
    }

    public void setBranchAddress(String branchAddress) {
        this.branchAddress = branchAddress;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public List<String> getShiftTime() {
        return timeOfShifts;
    }

    public void addShiftTime(String day, boolean isMorningShift) throws Exception {
        if (timeOfShifts.size() == 12) throw new Exception("All shift times already existed"); //In Saturday the branches are closed
        String day_=day.toUpperCase();
        if (!day_.equals("SUNDAY") && !day_.equals("MONDAY") && !day_.equals("TUESDAY") && !day_.equals("WEDNESDAY") && !day_.equals("THURSDAY") && !day_.equals("FRIDAY"))
            throw new Exception("String is not day");
        if(isMorningShift){
            if(timeOfShifts.contains(day_ + " Morning")) throw new Exception("Shift time already exist");
            timeOfShifts.add(day_ + " Morning");
        }
        else{
            if(timeOfShifts.contains(day_ + " Evening")) throw new Exception("Shift time already exist");
            timeOfShifts.add(day_ + " Evening");
        }
    }
    public void deleteShiftTime(String day, boolean isMorningShift) throws Exception {
        if (timeOfShifts.size() == 0) throw new Exception("Empty list of shift times");
        if(isMorningShift){
            timeOfShifts.remove(day + " Morning");
        }
        else{
            timeOfShifts.remove(day + " Evening");
        }
    }
}
