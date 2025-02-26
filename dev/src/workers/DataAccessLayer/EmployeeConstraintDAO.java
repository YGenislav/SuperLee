package workers.DataAccessLayer;

import workers.DomainLayer.EmployeeConstraint;
import workers.DataAccessLayer.AbstractDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class EmployeeConstraintDAO extends AbstractDAO {
    private static EmployeeConstraintDAO instance;
    private final HashMap<Identifier, EmployeeConstraint> identityMap;

    private EmployeeConstraintDAO() {
        identityMap = new HashMap<>();
    }

    public static EmployeeConstraintDAO getInstance() throws SQLException {
        if (instance == null)
            instance = new EmployeeConstraintDAO();
        return instance;
    }

    public static void deleteInstance() {
        if (instance != null)
            instance = null;
    }

    public void insert(int _constraintId, int _shiftId, String description) {
        String query = "INSERT INTO EmployeeConstraints VALUES(" + _constraintId + "," + _shiftId + ",'" + description + "')";
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

    public EmployeeConstraint getEmployeeConstraintIM(int _constraintId) {
        for (Identifier i : identityMap.keySet()) {
            if (i.constraintId == _constraintId)
                return identityMap.get(i);
        }
        return null;
    }

    public void loadData() {
        String query = "SELECT * FROM EmployeeConstraints";
        try {
            connect();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                int constraintId = rs.getInt("ConstraintId");
                int shiftId = rs.getInt("ShiftId");
                String description = rs.getString("Description");
                EmployeeConstraint employeeConstraint = new EmployeeConstraint(constraintId, shiftId, description);
                Identifier identifier = new Identifier(constraintId);
                identityMap.put(identifier, employeeConstraint);
            }
            statement.close();
            disconnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            disconnect();
        }
    }

    public EmployeeConstraint getEmployeeConstraint(int _constraintId) {
        for (Identifier curr : identityMap.keySet()) {
            if (curr.same(_constraintId))
                return identityMap.get(curr);
        }
        String query = "SELECT ConstraintId, ShiftId, Description from EmployeeConstraints WHERE ConstraintId=" + _constraintId;
        try {
            connect();
            EmployeeConstraint employeeConstraint = null;
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int constraintId = rs.getInt("ConstraintId");
                int shiftId = rs.getInt("ShiftId");
                String description = rs.getString("     ");
                employeeConstraint = new EmployeeConstraint(constraintId, shiftId, description);
                Identifier identifier = new Identifier(constraintId);
                identityMap.put(identifier, employeeConstraint);
            }
            stmt.close();
            return employeeConstraint;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            disconnect();
        }
    }

    public void delete(int _constraintId) {
        try {
            connect();
            connection.setAutoCommit(false);
            String query = "DELETE from EmployeeConstraints WHERE ConstraintId=" + _constraintId;
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

    public void update(int id, String column, Object val) {
        String query = "UPDATE EmployeeConstraints SET " + column + "=" + val + " WHERE ConstraintId = " + id;
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

    public void addToMap(int constraintId, EmployeeConstraint ec) {
        identityMap.put(new Identifier(constraintId), ec);
    }

    public void removeFromMap(int constraintId) {
        for (Identifier i : identityMap.keySet())
            if (i.same(constraintId))
                identityMap.remove(i);
    }

    private static class Identifier {
        private final int constraintId;

        public Identifier(int _constraintId) {
            constraintId = _constraintId;
        }

        public boolean same(int _constraintId) {
            return constraintId == _constraintId;
        }
    }
}
