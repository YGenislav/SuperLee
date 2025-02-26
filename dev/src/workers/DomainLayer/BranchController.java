package workers.DomainLayer;

import workers.DataAccessLayer.DALFacade;
import java.time.LocalTime;
import java.util.HashMap;

public class BranchController {
    private HashMap<String, Branch> branches; // <address branch, Branch>
    private final DALFacade dalController;
    public BranchController() {
        branches = new HashMap<>();
        dalController= DALFacade.getInstance();
        loadData();
    }

    public void loadData(){
        try{
            setBranches(dalController.getAllBranches());
            HashMap<String,Branch> branch = dalController.getAllBranches();
            for(String branchAddress:branch.keySet()){
                branches.put(branchAddress,branch.get(branchAddress));
            }
        }
        catch (Exception e){
            throw new RuntimeException("DAL Error");
        }
    }
    private void setBranches(HashMap<String, Branch> _branches){
        branches = _branches;
    }
    public HashMap<String, Branch> getBranches() {
        return branches;
    }


    public String getAllBranchNames() throws Exception{
        StringBuilder allBranches = new StringBuilder();

        for (Branch branch : branches.values()) {
            allBranches.append(("Branch name: ")).append(branch.getBranchName()).append(". Branch address: ").append(branch.getBranchAddress()).append("\n");
        }
        if (allBranches.length() == 0) {
            throw new Exception("No branches available.");
        }
        return allBranches.toString();
    }
    public boolean isBranchNameExists(String branchName) {
        String lowerBranchName = branchName.toLowerCase();
        for (Branch branch: branches.values()) {
            if (branch.getBranchName().toLowerCase().equals(lowerBranchName)) {
                return true;
            }
        }
        return false;
    }

    public boolean isBranchAddressExists(String branchAddress) {
        String lowerBranchName = branchAddress.toLowerCase();
        for (String key : branches.keySet()) {
            if (key.toLowerCase().equals(lowerBranchName)) {
                return true;
            }
        }
        return false;
    }

    public boolean isBranchNameAndAddressExists(String branchName, String branchAddress) {
        String lowerCaseBranchAddress = branchAddress.toLowerCase();
        for (Branch branch : branches.values()) {
            if (branch.getBranchName().equalsIgnoreCase(branchName) && branch.getBranchAddress().equalsIgnoreCase(lowerCaseBranchAddress)) {
                return true;
            }
        }
        return false;
    }

    public void setBranchName(String oldBranchName, String branchAddress, String newBranchName) throws Exception {
        if (!isBranchNameAndAddressExists(oldBranchName, branchAddress)) {
            throw new Exception("Branch does not exist");
        }
        if (newBranchName == null || newBranchName.isEmpty()) {
            throw new Exception("Branch name can't be NULL or empty");
        }
        if (isBranchNameExists(newBranchName)) {
            throw new Exception("Branch name already exists");
        }
        // Retrieve the branch object
        Branch branch = branches.get(branchAddress);

        // Update the branch name
        branch.setBranchName(newBranchName);
    }

    public String getBranchName(String branchName) throws Exception {
        if (!isBranchNameExists(branchName)) {
            throw new Exception("Branch does not exist");
        }
        return branches.get(branchName).getBranchName();
    }

    public String getBranchAddress(String branchAddress) throws Exception {
        if (!isBranchAddressExists(branchAddress)) {
            throw new Exception("Branch address does not exist");
        }
        return branches.get(branchAddress).getBranchAddress();
    }

    public Branch getBranch(String branchName, String branchAddress) throws Exception{
        String lowerCaseBranchName = branchName.toLowerCase();
        String lowerCaseBranchAddress = branchAddress.toLowerCase();

        for (Branch branch : branches.values()) {
            if (branch.getBranchName().toLowerCase().equals(lowerCaseBranchName) && branch.getBranchAddress().toLowerCase().equals(lowerCaseBranchAddress)) {
                return branch;
            }
        }
    throw new Exception("Branch does not exist");
    }


    public void setBranchAddress(String branchName, String newBranchAddress) throws Exception {
        if (!isBranchNameExists(branchName)) {
            throw new Exception("Branch does not exist");
        }
        if (newBranchAddress == null || newBranchAddress.isEmpty()) {
            throw new Exception("Branch address can't be NULL or empty");
        }
        if (isBranchNameExists(branchName)) {
            throw new Exception("Branch is already exists");
        }
        Branch branch = branches.get(branchName);
        branch.setBranchAddress(newBranchAddress);
    }

    public void changeStartHourOfBranch(String branchName, boolean isMorningShift, LocalTime shiftStartHourNew) throws Exception {
        if (!isBranchNameExists(branchName)) throw new Exception("Branch does not exist");
        Branch branch = branches.get(branchName);
        if (isMorningShift) {
            branch.setMorningShiftStartHour(shiftStartHourNew);
            dalController.updateBranch(branch,"MorningShiftStartHour");
        }
        else { //isEveningShift
            branch.setEveningShiftStartHour(shiftStartHourNew);
            dalController.updateBranch(branch,"EveningShiftStartHour");
        }
    }

    public void changeEndHourOfBranch(String branchName, boolean isMorningShift, LocalTime shiftEndHourNew) throws Exception {
        if (!isBranchNameExists(branchName)) throw new Exception("Branch does not exist");
        Branch branch = branches.get(branchName);
        if (isMorningShift) {
            if (branch.getMorningShiftStartHour().compareTo(shiftEndHourNew) >= 0) {
                throw new Exception("start of the morning shift should be before the end of the morning shift");
            } else {
                branch.setMorningShiftEndHour(shiftEndHourNew);
                dalController.updateBranch(branch,"MorningShiftEndHour");
            }
        } else { //isEveningShift
            if (branch.getEveningShiftStartHour().compareTo(shiftEndHourNew) >= 0) {
                throw new Exception("start of the evening shift should be before the end of the evening shift");
            } else {
                branch.setEveningShiftEndHour(shiftEndHourNew);
                dalController.updateBranch(branch,"EveningShiftEndHour");
            }
        }
    }
    public void correctTime (int hour, int minute) throws Exception {
        if (hour < 0 || hour > 23) {
            throw new Exception("Hour must be between 0 to 23");
        }
        if (minute < 0 || minute > 59) {
            throw new Exception("Minute must be between 0 to 59");
        }
    }

    public void addBranch(String branchName,String branchAddress, LocalTime morningShiftStartHour, LocalTime eveningShiftStartHour, LocalTime morningShiftEndHour, LocalTime eveningShiftEndHour) throws Exception {


        if(branchName==null || branchName.isEmpty()) throw new Exception("Invalid Branch name");
        if(branchAddress==null || branchAddress.isEmpty()) throw new Exception("Invalid Branch address");
        for (Branch branch : branches.values()) {
            if (branch.getBranchAddress().toLowerCase().equals(branchAddress.toLowerCase())) {
                throw new Exception("Branch with this address already exists");
            }
        }
        if(morningShiftStartHour.compareTo(morningShiftEndHour) == 0 || eveningShiftStartHour.compareTo(eveningShiftEndHour) == 0)
            throw new Exception("Shift start hour and end hour can't be the same");
        if(morningShiftStartHour.compareTo(morningShiftEndHour) > 0 || eveningShiftStartHour.compareTo(eveningShiftEndHour) > 0)
            throw new Exception("Shift start hour must be before end hour");
        if(eveningShiftStartHour.compareTo(morningShiftEndHour) < 0 && !(eveningShiftStartHour.getHour() == 0 && morningShiftEndHour.getHour() == 23))
            throw new Exception("Evening shift hour must be after morning shift hour");

        correctTime(morningShiftStartHour.getHour(),morningShiftStartHour.getMinute());
        correctTime(morningShiftEndHour.getHour(),morningShiftEndHour.getMinute());
        correctTime(eveningShiftStartHour.getHour(),eveningShiftStartHour.getMinute());
        correctTime(morningShiftEndHour.getHour(),morningShiftEndHour.getMinute());

        Branch branch = new Branch(branchName,branchAddress, morningShiftStartHour, eveningShiftStartHour, morningShiftEndHour, eveningShiftEndHour);
        branches.put(branchAddress, branch);
        dalController.insertBranch(branch);
    }

    public void removeBranch(String branchName, String branchAddress) throws Exception {

        String lowerBranchName = branchName.toLowerCase();
        String lowerBranchAddress = branchAddress.toLowerCase();

        Branch foundBranch = null;
        for (Branch branch : branches.values()) {
            if (branch.getBranchName().toLowerCase().equals(lowerBranchName) && branch.getBranchAddress().toLowerCase().equals(lowerBranchAddress)) {
                foundBranch = branch;
                break;
            }
        }

        if (foundBranch == null) {
            throw new Exception("Branch address is not exist");
        }
        branches.remove(foundBranch.getBranchAddress());

    }
    public boolean checkIfAreTableEmpty(){
        return dalController.checkIfAreTableEmpty();
    }
    public void initializeCounter() {
        dalController.initializeCounter();
    }

    public boolean checkIfAreCounerZero() {
        return dalController.checkIfAreCounerZero();
    }
}