package workers.DataAccessLayer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class RoleCountDAO extends AbstractDAO {
    private static RoleCountDAO instance;
    private final HashMap<RoleCountDAO.Identifier, List<Integer>> identityMap;

    private RoleCountDAO() {
        identityMap = new HashMap<>();
    }

    public static RoleCountDAO getInstance() throws SQLException {
        if (instance == null)
            instance = new RoleCountDAO();
        return instance;
    }

    public static void deleteInstance() {
        if (instance != null)
            instance = null;
    }

    public static void insert(int _shiftId, String _role, int _countNeeded, int _countAssign) {
        String query = "INSERT INTO RoleCounts VALUES(" + _shiftId + ",'" + _role + "'," + _countNeeded + "," + _countAssign + ")";
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

    public void loadData() {
        String query = "SELECT * FROM RoleCounts";
        try {
            connect();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                int shiftId = rs.getInt("ShiftId");
                String role = rs.getString("Role");
                int countNeeded = rs.getInt("CountNeeded");
                int countAssigned = rs.getInt("CountAssigned");
                List<Integer> counts = new LinkedList<>();
                counts.add(countNeeded);
                counts.add(countAssigned);
                identityMap.put(new RoleCountDAO.Identifier(shiftId, role), counts);
            }
            disconnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            disconnect();
        }
    }

    public HashMap<String, Integer> getRoleCountNeededIM(int _shiftId) {
        List<String> roles = new LinkedList<>();
        HashMap<String, Integer> roleCounts = new HashMap<>();
        for (RoleCountDAO.Identifier i : identityMap.keySet()) {
            if (i.shiftId == _shiftId) {
                roles.add(i.role);
            }
        }
        for (RoleCountDAO.Identifier i : identityMap.keySet()) {
            for (String role : roles) {
                if (i.same(_shiftId, role)) {
                    roleCounts.put(role, identityMap.get(i).get(0));
                }
            }
        }
        return roleCounts;
    }

    public HashMap<String, Integer> getRoleCountAssignedIM(int _shiftId) {
        List<String> roles = new LinkedList<>();
        HashMap<String, Integer> roleCounts = new HashMap<>();
        for (RoleCountDAO.Identifier i : identityMap.keySet()) {
            if (i.shiftId == _shiftId) {
                roles.add(i.role);
            }
        }
        for (RoleCountDAO.Identifier i : identityMap.keySet()) {
            for (String role : roles) {
                if (i.same(_shiftId, role)) {
                    roleCounts.put(role, identityMap.get(i).get(1));
                }
            }
        }
        return roleCounts;
    }

    public HashMap<String, Integer> getRoleCountNeeded(int _shiftId) {
        String query = "SELECT ShiftId, Role,CountNeeded from RoleCounts WHERE ShiftId=" + _shiftId;
        try {
            connect();
            HashMap<String, Integer> roleCountNeeded = new HashMap<>();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String role = rs.getString("Role");
                int countNeeded = rs.getInt("CountNeeded");
                roleCountNeeded.put(role, countNeeded);
                // RoleCountDAO.Identifier identifier = new RoleCountDAO.Identifier(_shiftId,role);
                // identityMap.put(identifier, countRole);
            }
            return roleCountNeeded;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            disconnect();
        }
    }

    public HashMap<String, Integer> getRoleCountAssigned(int _shiftId) {
        String query = "SELECT ShiftId, Role,CountAssigned from RoleCounts WHERE ShiftId=" + _shiftId;
        try {
            connect();
            HashMap<String, Integer> roleCountAssigned = new HashMap<>();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String role = rs.getString("Role");
                int countAssigned = rs.getInt("CountAssigned");
                roleCountAssigned.put(role, countAssigned);
            }
            return roleCountAssigned;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            disconnect();
        }
    }

    public void update(int id, String role, String column, Object val) {
        String query = "UPDATE RoleCounts SET " + column + " = " + val + " WHERE ShiftId = " + id + " AND Role='" + role + "'";
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

    public void addToMap(int shiftId, String role, int countNeeded, int countAssigned) {
        List<Integer> counts = new LinkedList<>();
        counts.add(countNeeded);
        counts.add(countAssigned);
        identityMap.put(new RoleCountDAO.Identifier(shiftId, role), counts);
    }

    private static class Identifier {
        private final int shiftId;
        private final String role;

        public Identifier(int _shiftId, String _role) {
            shiftId = _shiftId;
            role = _role;
        }

        public boolean same(int _shiftId, String _role) {
            return shiftId == _shiftId && role.equals(_role);
        }
    }

    public static String getCountNeededOf(int shiftId, String role) {
        String query = "SELECT CountNeeded FROM RoleCounts WHERE ShiftId = ? and Role = ?";
        try {
            connect();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, shiftId);
            stmt.setString(2, role);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("CountNeeded");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            disconnect();
        }
        return null;
    }
}
