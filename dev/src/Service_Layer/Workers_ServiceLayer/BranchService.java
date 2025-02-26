package Service_Layer.Workers_ServiceLayer;

import workers.DomainLayer.*;
import java.time.LocalTime;

public class BranchService {
    private final BranchController branchController;

    public BranchService(BranchController _branchController) {
        branchController = _branchController;
    }

    public String showAllBranches() throws Exception {
        return branchController.getAllBranchNames();
    }


    public String setBranchName(String _oldBranchName, String _branchAddress, String _newBranchName) throws Exception{
        branchController.setBranchName(_oldBranchName, _branchAddress, _newBranchName);
        return "success";
    }

    public String changeStartHourOfBranch(String branchName, boolean shiftType, LocalTime shiftStartHour) throws Exception {
        branchController.changeStartHourOfBranch(branchName, shiftType, shiftStartHour);
        return "Change Start hour of branch successfully";
    }

    public String changeEndHourOfBranch(String branchName, boolean shiftType, LocalTime shiftEndHour) throws Exception{
        branchController.changeEndHourOfBranch(branchName, shiftType, shiftEndHour);
        return "Change End hour of branch successfully";
    }

    public String addBranch(String branchName,String branchAddress ,LocalTime morningShiftStartHour, LocalTime eveningShiftStartHour, LocalTime morningShiftEndHour, LocalTime eveningShiftEndHour) throws Exception{
        branchController.addBranch(branchName, branchAddress,morningShiftStartHour, eveningShiftStartHour, morningShiftEndHour, eveningShiftEndHour);
        return "Branch was inserted successfully";
    }

    public String removeBranch(String branchName, String branchAddress) throws Exception{
        branchController.removeBranch(branchName, branchAddress);
        return "Delete branch successfully";
    }

    public String isBranchAddressExists(String branchAddress) throws Exception{
        return branchController.isBranchAddressExists(branchAddress) ? "Branch address exists" : "Branch address does not exist";
    }


    public String isBranchNameExists(String branchName) throws Exception{
        return branchController.isBranchNameExists(branchName) ? "Branch exists" : "Branch does not exist";
    }
    public boolean checkIfAreTableEmpty(){
        return branchController.checkIfAreTableEmpty();
    }

    public boolean checkIfAreCounerZero() {
        return branchController.checkIfAreCounerZero();

    }
}
