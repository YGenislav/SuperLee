package workers.DataAccessLayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ScheduleDAO extends AbstractDAO {
    private static ScheduleDAO instance;
    private final HashMap<Identifier, String> identityMap;

    private ScheduleDAO() {
        identityMap = new HashMap<>();
    }

    public static ScheduleDAO getInstance() throws SQLException {
        if (instance == null)
            instance = new ScheduleDAO();
        return instance;
    }

    public static void deleteInstance() {
        if (instance != null)
            instance = null;
    }

    public void insert(int _shiftId, String _employeeId, String _role) {
        String query = "INSERT INTO Schedules VALUES(" + _shiftId + ",'" + _employeeId + "','" + _role + "')";
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

    public HashMap<String, String> getSchedules(int _shiftId) {
        String query = "SELECT ShiftId, EmployeeId from Schedules WHERE ShiftId=" + _shiftId;
        try {
            connect();
            HashMap<String, String> schedule = new HashMap<>();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String employeeId = rs.getString("EmployeeId");
                String role = rs.getString("Role");
                schedule.put(employeeId, role);
                Identifier identifier = new Identifier(_shiftId, employeeId);
                identityMap.put(identifier, role);
            }
            return schedule;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            disconnect();
        }
    }

    public HashMap<String, String> getSchedule(int _shiftId, String _employeeId) {
        String query = "SELECT ShiftId, EmployeeId from Schedules WHERE ShiftId=" + _shiftId + " AND EmployeeId'=" + _employeeId + "'";
        try {
            connect();
            HashMap<String, String> schedule = new HashMap<>();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String role = rs.getString("Role");
                schedule.put(_employeeId, role);
                Identifier identifier = new Identifier(_shiftId, _employeeId);
                identityMap.put(identifier, role);
            }
            return schedule;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            disconnect();
        }
    }

    public void delete(int _shiftId, String _employeeId) {
        try {
            connect();
            connection.setAutoCommit(false);
            String query = "DELETE from Schedules WHERE ShiftId=" + _shiftId + " AND EmployeeId='" + _employeeId + "'";
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(query);
            connection.commit();
        } catch (Exception e) {
            try {
                connection.rollback();
                throw new RuntimeException(e);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } finally {
            disconnect();
        }
    }

    public void update(int _shiftId, String _employeeId, Object val) {
        String query = "UPDATE Schedules SET Role='" + val + "' WHERE ShiftId=" + _shiftId + " AND EmployeeId='" + _employeeId + "'";
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

    public void loadData() {
        String query = "SELECT * FROM Schedules";
        try {
            connect();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                int shiftId = rs.getInt("ShiftId");
                String employeeId = rs.getString("EmployeeId");
                String role = rs.getString("Role");
                identityMap.put(new Identifier(shiftId, employeeId), role);
            }
            disconnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            disconnect();
        }
    }

    public HashMap<String, String> getSchedulesIM(int _shiftId) {
        List<String> employeesS = new LinkedList<>();
        HashMap<String, String> schedules = new HashMap<>();
        for (Identifier i : identityMap.keySet()) {
            if (i.shiftId == _shiftId) {
                employeesS.add(i.employeeId);
            }
        }
        for (Identifier i : identityMap.keySet()) {
            for (String e : employeesS) {
                if (i.same(_shiftId, e)) {
                    schedules.put(e, identityMap.get(i));
                }
            }
        }
        return schedules;
    }

    public void addToMap(int shiftId, String employeeId, String role) {
        identityMap.put(new Identifier(shiftId, employeeId), role);
    }

    public void removeFromMap(int shiftId, String employeeId) {
        for (Identifier i : identityMap.keySet())
            if (i.same(shiftId, employeeId)) {
                identityMap.remove(i);
                break;
            }
    }


    private static class Identifier {
        private final int shiftId;
        private final String employeeId;

        public Identifier(int _shiftId, String _employeeId) {
            shiftId = _shiftId;
            employeeId = _employeeId;
        }

        public boolean same(int _shiftId, String _employeeId) {
            return shiftId == _shiftId && employeeId.equals(_employeeId);
        }
    }
}
