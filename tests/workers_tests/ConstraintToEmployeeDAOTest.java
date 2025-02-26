package workers_tests;

import workers.DataAccessLayer.ConstraintToEmployeeDAO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConstraintToEmployeeDAOTest {

    private ConstraintToEmployeeDAO dao;

    @BeforeEach
    public void setUp() throws SQLException {
        dao = ConstraintToEmployeeDAO.getInstance();
    }

    @AfterEach
    public void tearDown() throws SQLException {
        ConstraintToEmployeeDAO.deleteInstance();
    }

    @Test
    public void testInsert() {
        String employeeIdString = "123698741";
        int constraintId = 1;
        int employeeId = 123698741;

        // Insert a constraint to the database
        dao.insert(employeeIdString, constraintId);

        assertEquals(employeeIdString, dao.getEmployeeId(employeeId));
        assertEquals(constraintId, dao.getContraintId(constraintId));
    }

    @Test
    public void testDeleteConstraintToEmployee() {
        String employeeIdString = "123698741";
        int constraintId = 1;

        dao.insert(employeeIdString, constraintId);

        dao.delete(employeeIdString, constraintId);

        // Verify that the constraint was deleted
        assertFalse(dao.getConstraintsToEmployee(employeeIdString).size() > 0);
    }
}
