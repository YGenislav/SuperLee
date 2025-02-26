package suppliers.DataAccessLayer;
import suppliers.DataAccessLayer.DTO;
import suppliers.DataAccessLayer.DbController;
import suppliers.DataAccessLayer.ProductDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierWorkersController extends DbController {
    private static SupplierWorkersController instance;

    public SupplierWorkersController() {
        super("users");
    }

    public static SupplierWorkersController getInstance() {
        if (instance == null) {
            instance = new SupplierWorkersController() {
            };
        }
        return instance;
    }
    // Methods for handling Product-specific database operations
    @Override
    protected DTO convertReaderToObject(ResultSet resultSet) throws SQLException {
        return new ProductDTO(
                resultSet.getString(ProductDTO.ProductIdColumnName),
                resultSet.getString(ProductDTO.ProductNameColumnName),
                resultSet.getString(ProductDTO.CompanyNameColumnName),
                resultSet.getString(ProductDTO.ProductCategoryColumnName)
        );
    }
    public boolean selectUser(String username, String password) {
        String query = "SELECT * FROM "+ tableName +" WHERE Username = ? AND Password = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    public String deleteUser(String username) {
        String query = "DELETE FROM "+ tableName +" WHERE Username = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                return "*********************************\nUser deleted successfully\n*********************************\n\n";
            } else {
                return "****************\nUser not found\n\"****************\n\n";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "*********************************\nError deleting user\n*********************************\n\n";
        }
    }
    public String createUser(String username, String password, String permission) {
        String query = "INSERT INTO "+ tableName +" (Username, Password, Permission) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, permission);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                return "*********************************\nUser created successfully\n*********************************\n\n";
            } else {
                return "*********************************\nError creating user\n*********************************\n\n";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "*********************************\nError creating user\n*********************************\n\n";
        }
    }
    public String getUserPermission(String username) {
        String query = "SELECT Permission FROM "+ tableName +" WHERE Username = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("Permission");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    public String editPassword(String username, String newPassword) {
        String query = "UPDATE "+ tableName +" SET Password = ? WHERE Username = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, newPassword);
            preparedStatement.setString(2, username);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                return "*********************************\nPassword updated successfully\n*********************************\n\n";
            } else {
                return "*******************\nUser not found\n*******************\n\n";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "*********************************\nError updating password\n*********************************\n\n";
        }
    }
    public String addOrderDriver(String orderId, String driverId, String orderDate, String supplierId, String contactPhone) {
        String query = "INSERT INTO DriverOrders (OrderID, DriverID, OrderDate, SupplierID, ContactPhone) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, orderId);
            preparedStatement.setString(2, driverId);
            preparedStatement.setString(3, orderDate);
            preparedStatement.setString(4, supplierId);
            preparedStatement.setString(5, contactPhone);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                return "*********************************\nOrder added successfully\n*********************************\n\n";
            } else {
                return "*********************************\nError adding order\n*********************************\n\n";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "*********************************\nError adding order\n*********************************\n\n";
        }
    }
    public void delete(String orderId) {
        String query = "DELETE FROM DriverOrders WHERE OrderID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, orderId);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public String driverDetails(String orderId) {
        String query = "SELECT * FROM DriverOrders WHERE OrderID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, orderId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String s = "Order ID: " + resultSet.getString("OrderID") + "\n" +
                            "Driver ID: " + resultSet.getString("DriverID") + "\n" +
                            "Order Date: " + resultSet.getString("OrderDate") + "\n" +
                            "Supplier ID: " + resultSet.getString("SupplierID") + "\n" +
                            "Contact Phone: " + resultSet.getString("ContactPhone") + "\n";
                    return "***********************************************************************\n" + s + "\n***********************************************************************\n\n";
                }
            }

        } catch (SQLException e) {
            return e.getMessage();
        }
        return "*****************************************************************************\nOrder Number "+ orderId+" is not associated with a supplier that requires self-pickup" +
                "\n*****************************************************************************\n\n";
    }
    public String getdriverid(String orderId) {
        String query = "SELECT * FROM DriverOrders WHERE OrderID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, orderId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("DriverID");
                }
            }

        } catch (SQLException e) {
            return null;
        }
        return null;
    }

}