package workers.DomainLayer;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class Driver extends workers.DomainLayer.Employee {
    private int licenses;
    private List<String> trainings;

    public Driver(String _employeeId, String _password, String _firstName, String _lastName, int _accountNumber, int _branchBankNumber, int _salary, String _termsOfEmployment, boolean _isHRManager, boolean _isShiftManager, String _jobType, String _branchAddress, int _vacationDays, LocalDateTime _startDate, int _licenses) {
        super(_employeeId, _password, _firstName, _lastName, _accountNumber, _branchBankNumber, _salary, _termsOfEmployment, _isHRManager, _isShiftManager, _jobType, _branchAddress, _vacationDays, _startDate);
        licenses = 0; // Default value
        trainings = new LinkedList<>();
    }

    public int getLicenses() {
        return licenses;
    }

    public void setLicense(int _licenses) {
        licenses = _licenses;
    }
    public void addTraining(String _training) {
        trainings.add(_training);
    }

    public void removeTraining(String _training) {
        trainings.remove(_training);
    }
    public boolean isManagement() {
        return false;
    }

    @Override
    public void setCancellations(boolean _cancellations) {

    }

    public List<String> getRoles() {
        List<String> driver = new LinkedList<>();
        driver.add("Driver");
        return driver;
    }

    public void setManagement(boolean _management) {
    }
    public void removeRole(String role) {}

    public RoleType getType() {return RoleType.valueOf("Driver");}
    public void addRole(String _role) throws Exception {
        throw new Exception("The driver can not have additional roles");
    }

}