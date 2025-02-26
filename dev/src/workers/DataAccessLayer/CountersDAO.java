package workers.DataAccessLayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class CountersDAO extends AbstractDAO {

    private static CountersDAO instance;
    private final HashMap<Identifier, Integer> identityMap;

    private CountersDAO() {
        this.identityMap = new HashMap<>();
    }

    public static CountersDAO getInstance() throws SQLException {
        if (instance == null)
            instance = new CountersDAO();
        return instance;
    }

    public static void deleteInstance() {
        if (instance != null)
            instance = null;
    }

    public int getNextId(String counterName) {
        String query = "SELECT NextId FROM Counters WHERE CounterName=" + counterName;
        try {
            connect();
            int id = -1;
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery(query);
            while (set.next()) {
                id = set.getInt("NextId");
                identityMap.put(new Identifier(counterName), id);
            }
            return id;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            disconnect();
        }
    }

    public void insert(String counterName, int id) {
        String query = "INSERT INTO Counters VALUES('" + counterName + "','" + id + "')";
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

    public void update(String counterName, int val) {
        //TODO update identity map?
        String query = "UPDATE Counters SET NextId=" + val + " WHERE CounterName = '" + counterName + "'";
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

    public void delete(String counterName) {
        try {
            connect();
            String query = "DELETE from Counters WHERE CounterName=" + counterName;
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


    public HashMap<String, Integer> findAllCounters() {
        HashMap<String, Integer> counters = new HashMap<>();
        String query = "SELECT * FROM Counters";
        try {
            connect();
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery(query);
            while (set.next()) {
                String counterName = set.getString("CounterName");
                int id = set.getInt("NextId");
                if (!containsString(counterName))
                    identityMap.put(new Identifier(counterName), id);
            }
            for (Identifier i : identityMap.keySet())
                counters.put(i.name, identityMap.get(i));
            return counters;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            disconnect();
        }
    }

    public void updateMap(String counterName, int next) {
        for (Identifier i : identityMap.keySet())
            if (i.same(counterName))
                identityMap.put(i, next);
    }

    private boolean containsString(String name) {
        for (Identifier i : identityMap.keySet()) {
            if (i.same(name)) return true;
        }
        return false;
    }

    private static class Identifier {
        private final String name;

        public Identifier(String name) {
            this.name = name;
        }

        public boolean same(String name) {
            return this.name.equals(name);
        }

    }
}
