package workers.DataAccessLayer;
import workers.DomainLayer.BranchEmployee;
import workers.DomainLayer.EmployeeConstraint;
import workers.DataAccessLayer.AbstractDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public class BranchEmployeeDAO extends AbstractDAO {

    private static BranchEmployeeDAO instance;
    private final HashMap<Identifier, BranchEmployee> identityMap;
    private final BranchEmployeeRoleDAO branchEmployeeRoleDAO;
    private final ConstraintToEmployeeDAO constraintToEmployeeDAO;

    private BranchEmployeeDAO() throws SQLException {
        identityMap = new HashMap<>();
        branchEmployeeRoleDAO = BranchEmployeeRoleDAO.getInstance();
        constraintToEmployeeDAO = ConstraintToEmployeeDAO.getInstance();
    }

    public static BranchEmployeeDAO getInstance() throws SQLException {
        if (instance == null)
            instance = new BranchEmployeeDAO();
        return instance;
    }

    public static void deleteInstance() {
        if (instance != null)
            instance = null;
    }

    public void insert(String _employeeId, boolean _isShiftManager, int _cancellations) {
        String query = "INSERT INTO BranchEmployees VALUES('" + _employeeId + "'," + _isShiftManager + "," + _cancellations + ")";
        try {
            connect();
            connection.setAutoCommit(false);
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(query);
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            disconnect();
        }
    }

    public BranchEmployee getBranchEmployee(String _employeeId) {
        for (Identifier curr : identityMap.keySet()) {
            if (curr.same(_employeeId))
                return identityMap.get(curr);
        }
        String query = "SELECT BranchEmployees.EmployeeId,Employees.Password,Employees.FirstName,Employees.LastName,Employees.AccountNumber,Employees.BranchBankNumber,Employees.Salary,Employees.TermsOfEmployment,Employees.IsHRManager,Employees.IsShiftManager,Employees.JobType,Employees.BranchAddress,Employees.VacationDays,Employees.StartDate,BranchEmployees.Cancellation FROM Employees,BranchEmployees WHERE EmployeeId='" + _employeeId + "'";
        try {
            connect();
            BranchEmployee branchEmployee = null;
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String employeeId = rs.getString("EmployeeId");
                String password = rs.getString("Password");
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                int accountNumber = rs.getInt("AccountNumber");
                int branchBankNumber = rs.getInt("BranchBankNumber");
                int salary = rs.getInt("Salary");
                String termsOfEmployment = rs.getString("TermsOfEmployment");
                boolean isHRManager = rs.getBoolean("IsHRManager");
                boolean isShiftManager = rs.getBoolean("IsShiftManager");
                String jobType = rs.getString("JobType");
                String branchAddress = rs.getString("BranchAddress");
                int vacationDays = rs.getInt("VacationDays");
                LocalDateTime startDate = LocalDateTime.parse(rs.getString("StartDate"));
                boolean cancellations = rs.getInt("Cancellations") == 1;
                List<EmployeeConstraint> constraints = constraintToEmployeeDAO.getConstraintsToEmployeeIM(employeeId);
                List<String> roles = branchEmployeeRoleDAO.getBranchEmployeeRolesIM(employeeId);
                branchEmployee = new BranchEmployee(employeeId,password,firstName,lastName ,accountNumber,branchBankNumber,salary ,termsOfEmployment ,isHRManager,isShiftManager,jobType, branchAddress ,vacationDays ,startDate, cancellations);
                Identifier identifier = new Identifier(employeeId);
                identityMap.put(identifier, branchEmployee);
            }
            return branchEmployee;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            disconnect();
        }
    }

    public void update(String id, String column, Object val) {
        String query = "UPDATE BranchEmployees SET " + column + "=" + val + " WHERE EmployeeId = '" + id + "'";
        try {
            connect();
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            disconnect();
        }
    }

    public void addToMap(String employeeId, BranchEmployee be) {
        identityMap.put(new Identifier(employeeId), be);
    }

    public HashMap<String, BranchEmployee> findAllBranchEmployees() {
        HashMap<String, BranchEmployee> branchEmployees = new HashMap<>();
        String query = "SELECT Employees.EmployeeId,Employees.Password,Employees.FirstName,Employees.LastName,Employees.AccountNumber,Employees.BranchBankNumber,Employees.Salary,Employees.TermsOfEmployment,Employees.IsHRManager,Employees.IsShiftManager,Employees.JobType,Employees.BranchAddress,Employees.VacationDays,Employees.StartDate, BranchEmployees.Cancellation\n" +
                " FROM Employees,BranchEmployees\n" +
                "where Employees.EmployeeId=BranchEmployees.EmployeeId;";
        try {
            connect();
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery(query);
            while (set.next()) {
                String employeeId = set.getString("EmployeeId");
                String password = set.getString("Password");
                String firstName = set.getString("FirstName");
                String lastName = set.getString("LastName");
                int accountNumber = set.getInt("AccountNumber");
                int branchBankNumber = set.getInt("BranchBankNumber");
                int salary = set.getInt("Salary");
                String termsOfEmployment = set.getString("TermsOfEmployment");
                boolean isHRManager = set.getInt("IsHRManager") == 1;
                boolean isShiftManager = set.getInt("IsShiftManager") == 1;
                String jobType = set.getString("JobType");
                String branchAddress = set.getString("BranchAddress");
                int vacationDays = set.getInt("VacationDays");
                LocalDateTime startDate = LocalDateTime.parse(set.getString("StartDate"));
                List<EmployeeConstraint> constraints = constraintToEmployeeDAO.getConstraintsToEmployeeIM(employeeId);
                boolean cancellations = set.getInt("Cancellation") == 1;
                List<String> roles = branchEmployeeRoleDAO.getBranchEmployeeRolesIM(employeeId);
                BranchEmployee branchEmployee = new BranchEmployee(employeeId,password,firstName,lastName ,accountNumber,branchBankNumber,salary ,termsOfEmployment ,isHRManager,isShiftManager,jobType, branchAddress ,vacationDays ,startDate, cancellations);
                Identifier identifier = new Identifier(employeeId);
                identityMap.put(identifier, branchEmployee);
                branchEmployees.put(employeeId, branchEmployee);
            }
            return branchEmployees;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            disconnect();
        }
    }

    private static class Identifier {
        private final String employeeId;

        public Identifier(String _employeeId) {
            employeeId = _employeeId;
        }

        public boolean same(String _employeeId) {
            return employeeId.equals(_employeeId);
        }
    }
}

