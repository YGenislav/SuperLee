package workers.DataAccessLayer;


import workers.DomainLayer.Shift;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.HashMap;

public class ShiftDAO extends AbstractDAO {
    private static ShiftDAO instance;
    private final RoleCountDAO roleCountDAO;
    private final ScheduleDAO scheduleDAO;
    private final HashMap<Identifier, Shift> identityMap;

    private ShiftDAO() throws SQLException {
        identityMap = new HashMap<>();
        roleCountDAO = RoleCountDAO.getInstance();
        scheduleDAO = ScheduleDAO.getInstance();
    }

    public static ShiftDAO getInstance() throws SQLException {
        if (instance == null)
            instance = new ShiftDAO();
        return instance;
    }

    public static void deleteInstance() {
        if (instance != null)
            instance = null;
    }

    public void insert(int shiftId, String date, String branchAddress, boolean isMorningShift) {
        String query = "INSERT INTO Shifts VALUES(" + shiftId + ",'" + date + "', '" + branchAddress + "', '" + isMorningShift + "')";

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

    public void update(int id, String column, Object val) {
        String query = "UPDATE Shifts SET " + column + "=" + val + " WHERE ShiftId = " + id;

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

    public void delete(int shiftId) {
        String query = "DELETE FROM Shifts WHERE ShiftId = ?";
        try {
            connect();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, shiftId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting shift with ShiftId: " + shiftId, e);
        } finally {
            disconnect();
        }
    }


    public void addToMap(int shiftId, Shift s) {
        identityMap.put(new Identifier(shiftId), s);
    }


    private static class Identifier {
        private final int shiftId;

        public Identifier(int _shiftId) {
            shiftId = _shiftId;
        }

        public boolean same(int _shiftId) {
            return shiftId == _shiftId;
        }
    }

    public int getShiftId(int shiftId) {
        String query = "SELECT ShiftId FROM Shifts WHERE ShiftId = ?";
        try {
            connect();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, shiftId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("ShiftId");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            disconnect();
        }
        return -1;
    }

    public String getBranchAddress(int shiftId) {
        String query = "SELECT BranchAddress FROM Shifts WHERE ShiftId = ?";
        try {
            connect();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, shiftId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("BranchAddress");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            disconnect();
        }
        return null;
    }

    public String getShiftDate(int shiftId) {
        String query = "SELECT Date FROM Shifts WHERE ShiftId = ?";
        try {
            connect();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, shiftId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("Date");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            disconnect();
        }
        return null;
    }

    public String getIsMorningShift(int shiftId) {
        String query = "SELECT IsMorningShift FROM Shifts WHERE ShiftId = ?";
        try {
            connect();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, shiftId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("IsMorningShift");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            disconnect();
        }
        return null;
    }

    public HashMap<Integer, Shift> findAllShifts() {
        HashMap<Integer, Shift> shifts = new HashMap<>();
        String query = "SELECT ShiftId,Date,BranchAddress,IsMorningShift FROM Shifts";
        try {
            roleCountDAO.loadData();
            scheduleDAO.loadData();
            connect();
            Shift shift;
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int shiftId = rs.getInt("ShiftId");
                LocalDateTime date = LocalDateTime.parse(rs.getString("Date"));
                String branchAddress = rs.getString("BranchAddress");
                String isMorningShift = rs.getString("IsMorningShift");
                HashMap<String, Integer> roleCounts = roleCountDAO.getRoleCountNeededIM(shiftId);
                HashMap<String, Integer> scheduleToRoleCounts = roleCountDAO.getRoleCountAssignedIM(shiftId);
                HashMap<String, String> schedules = scheduleDAO.getSchedulesIM(shiftId);
                boolean isMorningShiftBoolean;
                if(isMorningShift == "true"){
                    isMorningShiftBoolean = false;
                }
                else{
                    isMorningShiftBoolean = true;
                }
                shift = new Shift(shiftId, date, roleCounts, scheduleToRoleCounts, schedules, branchAddress, isMorningShiftBoolean);
                ShiftDAO.Identifier identifier = new ShiftDAO.Identifier(shiftId);
                identityMap.put(identifier, shift);
                shifts.put(shiftId, shift);
            }
            return shifts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            disconnect();
        }
    }
}
