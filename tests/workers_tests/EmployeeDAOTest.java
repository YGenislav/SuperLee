package workers_tests;

import workers.DataAccessLayer.EmployeeDAO;
import workers.DomainLayer.Employee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EmployeeDAOTest {

    private EmployeeDAO employeeDAO;

    @BeforeEach
    public void setUp() throws SQLException {
        employeeDAO = EmployeeDAO.getInstance();
    }

    @AfterEach
    public void tearDown() {
        EmployeeDAO.deleteInstance();
    }

    @Test
    public void testFindAllEmployees() {
        employeeDAO.insert("100000000", "Testpassword1", "Rom", "Service", 22222, 3333, 55000,
                "Part time", true, false, "Cashier", "Ramot 1", 10, LocalDateTime.now());
        employeeDAO.insert("123412341", "Testpassword1", "Eve", "Devil", 33333, 4444, 70000,
                "Full time", false, true, "Cashier", "Ramot 1", 25, LocalDateTime.now());

        HashMap<String, Employee> employees = employeeDAO.findAllEmployees();
        assertNotNull(employees);
        assertEquals(4, employees.size());
    }

    @Test
    public void testUpdateFirstName() {
        String employeeId = "200000000";
        String originalFirstName = "Room";
        String updatedFirstName = "Roman";

        employeeDAO.insert(employeeId, "Testpassword1", originalFirstName, "Service", 22222, 3333, 55000,
                "Part time", true, false, "Cashier", "Ramot 1", 10, LocalDateTime.now());

        employeeDAO.update(employeeId, "FirstName", updatedFirstName);

        Employee updatedEmployee = employeeDAO.getEmployee(employeeId);

        // Assert the update was successful
        assertNotNull(updatedEmployee);
        assertEquals(updatedFirstName, updatedEmployee.getFirstName());
    }

    @Test
    public void testUpdateLastName() {
        String employeeId = "400000000";
        String originalLastName = "Gulmanm";
        String updatedLastName = "hadar";

        employeeDAO.insert(employeeId, "Testpassword1", "Monika", originalLastName, 22222, 3333, 55000,
                "Part time", true, false, "Cashier", "Ramot 1", 10, LocalDateTime.now());

        employeeDAO.update(employeeId, "LastName", updatedLastName);

        Employee updatedEmployee = employeeDAO.getEmployee(employeeId);

        // Assert the update was successful
        assertNotNull(updatedEmployee);
        assertEquals(updatedLastName, updatedEmployee.getLastName());
    }

    @Test
    public void testUpdatePassword() {
        String employeeId = "500000000";
        String originalPassword = "Testpassword1";
        String updatedPassword = "Testpassword2";

        employeeDAO.insert(employeeId, originalPassword, "Room", "Service", 22222, 3333, 55000,
                "Part time", true, false, "Cashier", "Ramot 1", 10, LocalDateTime.now());

        employeeDAO.update(employeeId, "Password", updatedPassword);

        Employee updatedEmployee = employeeDAO.getEmployee(employeeId);

        // Assert the update was successful
        assertNotNull(updatedEmployee);
        assertEquals(updatedPassword, updatedEmployee.getPassword());
    }

    @Test
    public void testUpdateAccountNumber() {
        String employeeId = "600000000";
        int originalAccountNumber = 22;
        int updatedAccountNumber = 33;

        employeeDAO.insert(employeeId, "Testpassword1", "Room", "Service", originalAccountNumber, 3333, 55000,
                "Part time", true, false, "Cashier", "Ramot 1", 10, LocalDateTime.now());

        employeeDAO.update(employeeId, "AccountNumber", updatedAccountNumber);

        Employee updatedEmployee = employeeDAO.getEmployee(employeeId);

        // Assert the update was successful
        assertNotNull(updatedEmployee);
        assertEquals(updatedAccountNumber, updatedEmployee.getAccountNumber());
    }

    @Test
    public void testUpdateBranchBankNumber() {
        String employeeId = "700000000";
        int originalBankNumber = 22;
        int updatedBankNumber = 33;

        employeeDAO.insert(employeeId, "Testpassword1", "Room", "Service", 22, originalBankNumber, 55000,
                "Part time", true, false, "Cashier", "Ramot 1", 10, LocalDateTime.now());

        employeeDAO.update(employeeId, "BranchBankNumber", updatedBankNumber);

        Employee updatedEmployee = employeeDAO.getEmployee(employeeId);

        // Assert the update was successful
        assertNotNull(updatedEmployee);
        assertEquals(updatedBankNumber, updatedEmployee.getBranchBankNumber());
    }

    @Test
    public void testUpdateSalary() {
        String employeeId = "800000000";
        int originalSalary = 222;
        int updatedSalary = 333;

        employeeDAO.insert(employeeId, "Testpassword1", "Room", "Service", 22, 22, originalSalary,
                "Part time", true, false, "Cashier", "Ramot 1", 10, LocalDateTime.now());

        employeeDAO.update(employeeId, "Salary", updatedSalary);

        Employee updatedEmployee = employeeDAO.getEmployee(employeeId);

        // Assert the update was successful
        assertNotNull(updatedEmployee);
        assertEquals(updatedSalary, updatedEmployee.getSalary());
    }

    @Test
    public void testUpdateTermOfEmployment() {
        String employeeId = "900000000";
        String originalTerm = "Part time";
        String updatedTerm = "Full time";

        employeeDAO.insert(employeeId, "Testpassword1", "Room", "Service", 22222, 3333, 55000,
                originalTerm, true, false, "Cashier", "Ramot 1", 10, LocalDateTime.now());

        employeeDAO.update(employeeId, "TermsOfEmployment", updatedTerm);

        Employee updatedEmployee = employeeDAO.getEmployee(employeeId);

        // Assert the update was successful
        assertNotNull(updatedEmployee);
        assertEquals(updatedTerm, updatedEmployee.getTermsOfEmployment());
    }

    @Test
    public void testUpdateISHRManager() {
        String employeeId = "110000000";
        boolean originalIsHRManager = true;
        boolean updatedIsHRManager = false;

        employeeDAO.insert(employeeId, "Testpassword1", "Room", "Service", 22222, 3333, 55000,
                "Full time", originalIsHRManager, false, "Cashier", "Ramot 1", 10, LocalDateTime.now());

        employeeDAO.update(employeeId, "IsHRManager", updatedIsHRManager);

        Employee updatedEmployee = employeeDAO.getEmployee(employeeId);

        // Assert the update was successful
        assertNotNull(updatedEmployee);
        assertEquals(updatedIsHRManager, updatedEmployee.getHRManager());
    }

    @Test
    public void testUpdateIsShiftManager() {
        String employeeId = "120000000";
        boolean originalIsShiftManager = true;
        boolean updatedIsShiftManager = false;

        employeeDAO.insert(employeeId, "Testpassword1", "Room", "Service", 22222, 3333, 55000,
                "Full time", false, originalIsShiftManager, "Cashier", "Ramot 1", 10, LocalDateTime.now());

        employeeDAO.update(employeeId, "IsShiftManager", updatedIsShiftManager);

        Employee updatedEmployee = employeeDAO.getEmployee(employeeId);

        // Assert the update was successful
        assertNotNull(updatedEmployee);
        assertEquals(updatedIsShiftManager, updatedEmployee.getShiftManager());
    }

    @Test
    public void testUpdateJobType() {
        String employeeId = "130000000";
        String originalJobType = "Cashier";
        String updatedJobType = "Driver";

        employeeDAO.insert(employeeId, "Testpassword1", "Room", "Service", 22222, 3333, 55000,
                "Full time", false, false, originalJobType, "Ramot 1", 10, LocalDateTime.now());

        employeeDAO.update(employeeId, "JobType", updatedJobType);

        Employee updatedEmployee = employeeDAO.getEmployee(employeeId);

        // Assert the update was successful
        assertNotNull(updatedEmployee);
        assertEquals(updatedJobType, updatedEmployee.getJobType());
    }

    @Test
    public void testUpdateBranchAddress() {
        String employeeId = "140000000";
        String originalBranchAddress = "Ramot 1";
        String updatedBranchAddress = "Ramot 2";

        employeeDAO.insert(employeeId, "Testpassword1", "Room", "Service", 22222, 3333, 55000,
                "Full time", false, false, "Cashier", originalBranchAddress, 10, LocalDateTime.now());

        employeeDAO.update(employeeId, "BranchAddress", updatedBranchAddress);

        Employee updatedEmployee = employeeDAO.getEmployee(employeeId);

        // Assert the update was successful
        assertNotNull(updatedEmployee);
        assertEquals(updatedBranchAddress, updatedEmployee.getBranchAddress());
    }

    @Test
    public void testUpdateVacationDays() {
        String employeeId = "150000000";
        int originalVacationDays = 0;
        int updatedVacationDays = 10;

        employeeDAO.insert(employeeId, "Testpassword1", "Room", "Service", 22222, 3333, 55000,
                "Full time", false, false, "Cashier", "Ramot 1", originalVacationDays, LocalDateTime.now());

        employeeDAO.update(employeeId, "VacationDays", updatedVacationDays);

        Employee updatedEmployee = employeeDAO.getEmployee(employeeId);

        // Assert the update was successful
        assertNotNull(updatedEmployee);
        assertEquals(updatedVacationDays, updatedEmployee.getVacationDays());
    }

    @Test
    public void testgetEmployee() {
        employeeDAO.insert("300000000", "Testpassword1", "Rom", "Service", 22222, 3333, 55000,
                "Part time", true, false, "Cashier", "Ramot 1", 10, LocalDateTime.now());
        Employee employee = employeeDAO.getEmployee("300000000");
        assertNotNull(employee);
    }
}
