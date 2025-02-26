package Service_Layer.Suppliers_ServiceLayer;
import suppliers.DomainLayer.Supplier_Controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.time.format.DateTimeFormatter;
public class Supplier_Service {
    private static Supplier_Service instance;
    private Supplier_Controller supplierController;

    private Supplier_Service() {
        this.supplierController = Supplier_Controller.getInstance();
    }

    public static Supplier_Service getInstance() {
        if (instance == null) {
            instance = new Supplier_Service();
        }
        return instance;
    }

    public String getSuppliers() {
        return supplierController.getSuppliers();
    }

    public String addFixedDaySupplier(String supplier_Name, String private_company_id, String contact_name, String contact_phone, String bank_account, String payment_method, String delivering_method, ArrayList<String> days, ArrayList<ArrayList<String>> list_of_products, ArrayList<String> products_Category) {
        return supplierController.add_Fixed_day_Supplier(supplier_Name, private_company_id, contact_name, contact_phone, bank_account, payment_method, delivering_method, days, list_of_products, products_Category);
    }

    public String addByOrderSupplier(String supplier_Name, String private_company_id, String contact_name, String contact_phone, String bank_account, String payment_method, String delivering_method, ArrayList<ArrayList<String>> list_of_products, ArrayList<String> products_Category) {
        return supplierController.add_By_Order_Supplier(supplier_Name, private_company_id, contact_name, contact_phone, bank_account, payment_method, delivering_method, list_of_products, products_Category);
    }

    public String addOrder(String shipmentDate, String supplierID, HashMap<String, Integer> products_in_order) {
        if (!ExistedSupplierIDChecking(supplierID)) {
            return "*****************************************************************************************\nThere is no Supplier in the system that has the followed BIN details - " + supplierID+
                    "\n*****************************************************************************************\n\n";
        }
        return supplierController.addOrder(shipmentDate, supplierID, products_in_order);

    }
    public String addOrderByShortage( HashMap<String, Integer> products_in_order) {
        return supplierController.addOrderByShortage(products_in_order);
    }
    public String driverDetails(String orderId) {
        return supplierController.driverDetails(orderId);
    }
    public String supplier_card(String supplierID) {
        if (!ExistedSupplierIDChecking(supplierID)) {

            return "*****************************************************************************************\nThere is no Supplier in the system that has the followed BIN details - " + supplierID+
                    "\n*****************************************************************************************\n\n";
        }
        return supplierController.supplier_card(supplierID);
    }

    public String LastOrderSupplier(String supplierID) {
        if (!ExistedSupplierIDChecking(supplierID)) {

            return "*****************************************************************************************\nThere is no Supplier in the system that has the followed BIN details - " + supplierID +
                    "\n*****************************************************************************************\n\n";
        }
        return supplierController.LastOrderSupplier(supplierID);
    }

    public String allSupplierOrders(String supplierID) {
        if (!ExistedSupplierIDChecking(supplierID)) {
            return "****************************************************************************************\nThere is no Supplier in the system that has the followed BIN details - " + supplierID +
                    "\n****************************************************************************************\n\n";
        }
        return supplierController.allSupplierOrders(supplierID);
    }


    public String editcontact(String supplier_id, String contact_name, String contact_phone) {
        if (!ExistedSupplierIDChecking(supplier_id)) {

            return "****************************************************************************************\nThere is no Supplier in the system that has the followed BIN details - " + supplier_id +
                    "\n****************************************************************************************\n\n";
        }
        return supplierController.editcontact(supplier_id, contact_name, contact_phone);
    }

    public String EditOrder(String orderID, String product_Number, String quantity) {

        return supplierController.EditOrder(orderID, product_Number, quantity);
    }

    public String getcontact(String supplier_id) {
        if (!ExistedSupplierIDChecking(supplier_id)) {
            return "There is no Supplier in the system that has the followed BIN details - " + supplier_id;
        }
        return supplierController.getcontact(supplier_id);
    }

    public String Remove_product_from_order(String orderID, String product_Number) {
        return supplierController.Remove_product_from_order(orderID, product_Number);
    }

    public String removeOrder(Integer orderID) {
        return supplierController.removeOrder(orderID);
    }

    public String BilOfQuantities(String supplier_id){
        if (!ExistedSupplierIDChecking(supplier_id)) {
            return "****************************************************************************************\nThere is no Supplier in the system that has the followed BIN details - " + supplier_id +
                    "\n****************************************************************************************\n\n";
        }
        return supplierController.BilOfQuantities(supplier_id);
    }
    public boolean Check_Phone(String phone){return supplierController.Check_Phone(phone);}
    public boolean IsFixedDay(String SupplyingMethod){return supplierController.IsFixedDay(SupplyingMethod);}
    public boolean ExistedOrderChecking(String orderID){return supplierController.ExistedOrderChecking(orderID);}
    public boolean Contains_product(HashMap<String, Integer> products_in_order, String product_Number){return supplierController.Contains_product(products_in_order,product_Number);}
    public String loadSuppliers() {return supplierController.loadSuppliers();}
    public String loadOrders() {return supplierController.loadOrders();}
    public boolean ExistedSupplierIDChecking(String SupplierID) {
        return supplierController.ExistedSupplierIDChecking(SupplierID);
    }
    public String ExitSuppliers() {return supplierController.ExitSuppliers();}
    public String ExitOrders() {return supplierController.ExitOrders();}
    public boolean login(String Username, String Password) {return supplierController.login(Username, Password);}
    public String getpermission(String Username){return supplierController.getpermission(Username);}
    public String addUser(String Username, String Password, String permission) {return supplierController.addUser(Username, Password, permission);}
    public String deleteUser(String Username) {return supplierController.deleteUser(Username);}
    public String EditPassword(String Username,String Password) {return supplierController.EditPassword(Username, Password);}
    public String addOrderDriver(String orderId, String DriverId, String orderDate, String supplierId, String contactPhone) {;return supplierController.addOrderDriver(orderId, DriverId, orderDate, supplierId, contactPhone);}

}