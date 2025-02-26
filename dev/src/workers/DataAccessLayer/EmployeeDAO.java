package workers.DataAccessLayer;

import workers.DomainLayer.Employee;
import workers.DomainLayer.EmployeeConstraint;
import workers.DataAccessLayer.AbstractDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class EmployeeDAO extends AbstractDAO {

    private static EmployeeDAO instance;
    private final HashMap<Identifier, Employee> identityMap;
    private final ConstraintToEmployeeDAO constraintToEmployeeDAO;

    private EmployeeDAO() throws SQLException {
        identityMap = new HashMap<>();
        constraintToEmployeeDAO = ConstraintToEmployeeDAO.getInstance();
    }

    public static EmployeeDAO getInstance() throws SQLException {
        if (instance == null)
            instance = new EmployeeDAO();
        return instance;
    }

    public static void deleteInstance() {
        if (instance != null)
            instance = null;
    }

    public void insert(String _employeeId, String _password, String _firstName, String _lastName ,int _accountNumber, int _branchBankNumber, int _salary ,String _termsOfEmployment , boolean _isHRManager, boolean _isShiftManager, String _jobType,String _branchAddress ,int _vacationDays ,LocalDateTime _startDate) {
        String query = "INSERT INTO Employees VALUES('" + _employeeId + "','" + _password +"','" + _firstName + "','" + _lastName + "'," + _accountNumber + "," + _branchBankNumber + "," + _salary + ",'" + _termsOfEmployment + "'," + _isHRManager + "," + _isShiftManager +",'"+ _jobType +"','"+ _branchAddress+ "'," +_vacationDays+",'"+ _startDate+ "')";
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


    public Employee getEmployee(String _employeeId) {
        for (Identifier curr : identityMap.keySet()) {
            if (curr.same(_employeeId))
                return identityMap.get(curr);
        }
        String query = "SELECT EmployeeId ,Password ,FirstName ,LastName ,AccountNumber ,BranchBankNumber ,Salary ,TermsOfEmployment ,IsHRManager ,IsShiftManager ,JobType ,BranchAddress ,VacationDays ,StartDate FROM Employees WHERE EmployeeId=" + _employeeId;
        try {
            connect();
            Employee employee = null;
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
                employee = new Employee(employeeId,password,firstName,lastName ,accountNumber,branchBankNumber,salary ,termsOfEmployment ,isHRManager,isShiftManager,jobType, branchAddress ,vacationDays ,startDate);
                Identifier identifier = new Identifier(employeeId);
                identityMap.put(identifier, employee);
            }
            return employee;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            disconnect();
        }
    }

    public void update(String id, String column, Object val) {
        List<String> numberColumns = Arrays.asList("FirstName","Password", "LastName", "AccountNumber", "BranchBankNumber", "Salary", "TermsOfEmployment", "IsHRManager", "IsShiftManager","JobType","BranchAddress","StatusOfEmployment", "VacationDays");
        String query = "UPDATE Employees SET '" + column + "',='" + val + "' WHERE EmployeeId = '" + id + "'";
        if (numberColumns.contains(column))
            query = "UPDATE Employees SET '" + column + "'='" + val + "' WHERE EmployeeId = '" + id + "'";
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

    public void addToMap(String employeeId, Employee e) {
        identityMap.put(new Identifier(employeeId), e);
    }

    public HashMap<String, Employee> findAllEmployees() {
        HashMap<String, Employee> employees = new HashMap<>();
        String query = "SELECT * From Employees";
        try {
            constraintToEmployeeDAO.loadData();
            connect();
            Employee employee;
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
                List<EmployeeConstraint> constraints = constraintToEmployeeDAO.getConstraintsToEmployeeIM(employeeId);
                employee = new Employee(employeeId,password,firstName,lastName ,accountNumber,branchBankNumber,salary ,termsOfEmployment ,isHRManager,isShiftManager,jobType, branchAddress ,vacationDays ,startDate);
                Identifier identifier = new Identifier(employeeId);
                identityMap.put(identifier, employee);
                employees.put(employeeId, employee);
            }
            return employees;
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
