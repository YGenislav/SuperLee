package workers.DomainLayer;

import java.time.LocalDateTime;

public class EmployeeConstraint {
    private static int lastConstraintId = 0;
    private int constraintId = 0;
    private int shiftId;
    private String description;

    public EmployeeConstraint(int _shiftId, String _description) {
        this.constraintId = lastConstraintId++;
//        this.constraintId = constraintId + 1;
        shiftId = _shiftId;
        description = _description;
    }

    public EmployeeConstraint(int _constraintId, int _shiftId, String _description) {
        constraintId = _constraintId;
        shiftId = _shiftId;
        description = _description;
    }

    public int getConstraintId() {
        return constraintId;
    }

    public int getShiftId() {
        return shiftId;
    }

    public void setShiftId(int _shiftId) {
        shiftId = _shiftId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String _description) {
        description = _description;
    }

    //public LocalDateTime getShiftDate() {
      //  return shiftDate;
    //}
}

