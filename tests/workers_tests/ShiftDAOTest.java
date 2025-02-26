package workers_tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import workers.DataAccessLayer.ShiftDAO;

import java.sql.SQLException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShiftDAOTest {

    private ShiftDAO shiftDAO;

    @BeforeEach
    public void setUp() throws SQLException {
        shiftDAO = ShiftDAO.getInstance();
    }

    @AfterEach
    public void tearDown() throws SQLException {
        ShiftDAO.deleteInstance();
    }

    @Test
    public void testInsertShift() {
        // Insert a shift into the Shifts table
        int shiftId = 3;
        String shiftDate = LocalDateTime.now().toString();
        String shiftName = "Rami Levi";
        boolean isMorningShift = true;
        String isMorningShiftString = "true";

        shiftDAO.insert(shiftId, shiftDate, shiftName, isMorningShift);

        assertEquals(shiftId, shiftDAO.getShiftId(shiftId));
        assertEquals(shiftName, shiftDAO.getBranchAddress(shiftId));
        assertEquals(shiftDate, shiftDAO.getShiftDate(shiftId));
        assertEquals(isMorningShiftString, shiftDAO.getIsMorningShift(shiftId));
    }

    @Test
    public void testDeleteShift() {
        // Insert a shift into the Shifts table
        int shiftId = 5;
        String shiftDate = LocalDateTime.now().toString();
        String shiftName = "Rami Levi";
        boolean isMorningShift = true;

        shiftDAO.insert(shiftId, shiftDate, shiftName, isMorningShift);
        shiftDAO.delete(shiftId);
    }
}
