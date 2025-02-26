package workers.DataAccessLayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class BranchEmployeeRoleDAO extends AbstractDAO {

    private static BranchEmployeeRoleDAO instance;
    private final HashMap<Identifier, String> identityMap;

    private BranchEmployeeRoleDAO() {
        identityMap = new HashMap<>();
    }

    public static BranchEmployeeRoleDAO getInstance() throws SQLException {
        if (instance == null)
            instance = new BranchEmployeeRoleDAO();
        return instance;
    }

    public static void deleteInstance() {
        if (instance != null)
            instance = null;
    }

    public void insert(String _employeeId, String _role) {
        String query = "INSERT INTO BranchEmployeeRoles VALUES('" + _employeeId + "','" + _role + "')";
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

    public List<String> getBranchEmployeeRolesDB(String _employeeId) {
        String query = "SELECT EmployeeId,Role from BranchEmployeeRoles WHERE EmployeeId='" + _employeeId + "'";
        try {
            connect();
            List<String> roles = new LinkedList<>();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String _role = rs.getString("Role");
                roles.add(_role);
                Identifier identifier = new Identifier(_employeeId, _role);
                identityMap.put(identifier, _role);
            }
            return roles;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            disconnect();
        }
    }

    public void deleteForEmployee(String _employeeId) {
        try {
            connect();
            connection.setAutoCommit(false);
            String query = "DELETE FROM BranchEmployeeRoles WHERE EmployeeId='" + _employeeId + "'";
            Statement stmt = connection.createStatement();
            stmt.executeQuery(query);
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

    public void delete(String _employeeId, String _role) {
        try {
            connect();
            connection.setAutoCommit(false);
            String query = "DELETE FROM BranchEmployeeRoles WHERE EmployeeId='" + _employeeId + "' AND Role='" + _role + "'";
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

    public void addToMap(String employeeId, String role) {
        identityMap.put(new Identifier(employeeId, role), role);
    }

    public void removeFromMap(String employeeId, String role) {
        for (Identifier i : identityMap.keySet())
            if (i.same(employeeId, role)) {
                identityMap.remove(i);
                break;
            }
    }

    public void loadData() {
        String query = "SELECT * FROM BranchEmployeeRoles";
        try {
            connect();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                String employeeId = rs.getString("EmployeeId");
                String role = rs.getString("Role");
                identityMap.put(new Identifier(employeeId, role), role);
            }
            disconnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            disconnect();
        }
    }

    public LinkedList<String> getBranchEmployeeRolesIM(String employeeId) {
        LinkedList<String> roles = new LinkedList<>();
        for (Identifier i : identityMap.keySet()) {
            if (i.employeeId.equals(employeeId))
                roles.add(i.role);
        }
        return roles;
    }


    private static class Identifier {
        private final String employeeId;
        private final String role;

        public Identifier(String _employeeId, String _role) {
            employeeId = _employeeId;
            role = _role;
        }

        public boolean same(String _employeeId, String _role) {
            return employeeId.equals(_employeeId) && role.equals(_role);
        }
    }
}
