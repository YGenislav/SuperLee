package workers.DomainLayer;


import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class BranchEmployee extends Employee {
    private boolean cancellations;
    private final List<String> roles;


    public BranchEmployee(String _employeeId, String _password, String _firstName, String _lastName, int _accountNumber, int _branchBankNumber, int _salary, String _termsOfEmployment, boolean _isHRManager, boolean _isShiftManager, String _jobType, String _branchAddress, int _vacationDays, LocalDateTime _startDate, boolean _cancellations) {
        super(_employeeId, _password, _firstName, _lastName, _accountNumber, _branchBankNumber, _salary, _termsOfEmployment, _isHRManager, _isShiftManager, _jobType, _branchAddress, _vacationDays, _startDate);
        cancellations = _cancellations;
        roles = new LinkedList<>();
    }


    public boolean isCancellations() {
        return cancellations;
    }

    @Override
    public void setCancellations(boolean _cancellations) {
        cancellations = _cancellations;
    }


    public List<String> getRoles() {
        return roles;
    }

    public void addRole(String _role) throws Exception {
        String role_;
        String lowerRole = _role.toLowerCase();
        List<String> validRoles = Arrays.asList("shift manager", "cashier", "general employee", "storekeeper", "driver");
        if (validRoles.contains(lowerRole)) {
            if (lowerRole.equals("shift manager")) {
                role_ = "Shift manager";
                setCancellations(true);
                setShiftManager(true);
            } else if (lowerRole.equals("cashier")) {
                role_ = "Cashier";
            } else if (lowerRole.equals("general employee")) {
                role_ = "General employee";
            } else if (lowerRole.equals("driver")) {
            role_ = "Driver";
            } else {
                role_ = "Storekeeper";
            }

            if (lowerRole.equals("shift manager") && (!getShiftManager() || !isCancellations())) {
                throw new Exception("Shift manager must have training in management and cancellations");
            }
            roles.add(role_);
        } else
            throw new Exception("Invalid role: " + _role);
    }

public void removeRole(String _role) throws Exception {
    String role_;
    String lowerRole = _role.toLowerCase(); // Convert the input role to lowercase

    if (lowerRole.equals("shift manager")) {
        role_ = "Shift manager";
    } else if (lowerRole.equals("Cashier")) {
        role_ = "Cashier";
    } else if (lowerRole.equals("general employee")) {
        role_ = "General employee";
    } else if (lowerRole.equals("storekeeper")) {
        role_ = "Storekeeper";
    } else if (Character.isLowerCase(_role.charAt(0))) {
        role_ = Character.toUpperCase(_role.charAt(0)) + _role.substring(1);
    } else {
        role_ = _role;
    }

    if (role_.equals("Shift manager")) {
        setCancellations(false);
        setShiftManager(false);
    }

    if (role_.equals("Shift manager") && (getShiftManager() || isCancellations())) {
        throw new Exception("The employee still holds shift manager permissions");
    }

    roles.remove(_role);
}


}
