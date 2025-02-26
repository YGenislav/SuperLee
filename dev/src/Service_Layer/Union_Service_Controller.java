package Service_Layer;

import Service_Layer.Suppliers_ServiceLayer.Service_Controller;
import Service_Layer.Workers_ServiceLayer.BranchService;
import Service_Layer.Workers_ServiceLayer.EmployeeService;
import Service_Layer.Workers_ServiceLayer.HRService;
import Service_Layer.Workers_ServiceLayer.ShiftService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class Union_Service_Controller {
    private static Union_Service_Controller instance;
    private Service_Controller serviceController;
    private HRService hrService;

    private Union_Service_Controller() throws Exception {
        this.serviceController = Service_Controller.getInstance();
        this.hrService = new HRService();
    }

    public static Union_Service_Controller getInstance() throws Exception {
        if (instance == null) {
            instance = new Union_Service_Controller();
        }
        return instance;
    }

    // Service_Controller methods
    public String addProduct(String product_Name, String company_name, String product_Category) {
        try {
            return serviceController.addProduct(product_Name, company_name, product_Category);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String findProductById(String id) {
        try {
            return serviceController.findProductById(id);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String getProducts() {
        try {
            return serviceController.getProducts();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String removeProduct(String ProductID) {
        try {
            return serviceController.removeProduct(ProductID);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String AddFixedDaySupplier(String supplier_Name, String private_company_id, String contact_name, String contact_phone, String bank_account, String payment_method, String delivering_method, ArrayList<String> days, ArrayList<ArrayList<String>> list_of_products) {
        try {
            return serviceController.AddFixedDaySupplier(supplier_Name, private_company_id, contact_name, contact_phone, bank_account, payment_method, delivering_method, days, list_of_products);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String addByOrderSupplier(String supplier_Name, String private_company_id, String contact_name, String contact_phone, String bank_account, String payment_method, String delivering_method, ArrayList<ArrayList<String>> list_of_products) {
        try {
            return serviceController.addByOrderSupplier(supplier_Name, private_company_id, contact_name, contact_phone, bank_account, payment_method, delivering_method, list_of_products);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String getSuppliers() {
        try {
            return serviceController.getSuppliers();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String supplier_card(String id) {
        try {
            return serviceController.supplier_card(id);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String editcontact(String supplier_id, String contact_name, String contact_phone) {
        try {
            return serviceController.editcontact(supplier_id, contact_name, contact_phone);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String getcontact(String supplier_id) {
        try {
            return serviceController.getcontact(supplier_id);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String editOrder(String orderID, String product_Number, String quantity) {
        try {
            return serviceController.editOrder(orderID, product_Number, quantity);
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    public String addOrder(String shipmentDate, String supplierID, HashMap<String, Integer> products_in_order) {
        try {
            String result = serviceController.addOrder(shipmentDate, supplierID, products_in_order);
            if(result.contains("*")){
                return result;
            }
            else{
                // Split the string into components based on spaces and colon
                String[] parts = result.split("[ :]+");

                // Extract information using array indices
                String orderDateStr = parts[1];
                String supplierId = parts[3];
                String orderIdStr = parts[5];
                String contactPhone = parts[7];

                //convert String to LocalDateTime
                // Parse the date string to a LocalDate
                LocalDate localDate = LocalDate.parse(orderDateStr);

                // Convert LocalDate to LocalDateTime at the start of the day (00:00:00)
                LocalDateTime orderDate = localDate.atTime(10, 0);
                // Convert to appropriate types

                String DriverId = addDriverToShift(orderDate); //return a driverId
                serviceController.addOrderDriver(orderIdStr, DriverId, orderDateStr, supplierId, contactPhone);
                return "*********************************************\nOrder number " +  orderIdStr + " has been opened successfully. \n" +
                        "*********************************************\n\n";
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String addOrderByShortage(HashMap<String, Integer> products_in_order) {
        try {
            return serviceController.addOrderByShortage(products_in_order);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String Remove_product_from_order(String orderID, String product_Number) {
        try {
            return serviceController.Remove_product_from_order(orderID, product_Number);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String removeOrder(Integer orderID) {
        try {
            return serviceController.removeOrder(orderID);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String allSupplierOrders(String supplierID) {
        try {
            return serviceController.allSupplierOrders(supplierID);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String LastOrderSupplier(String supplierID) {
        try {
            return serviceController.LastOrderSupplier(supplierID);
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    public String driverDetails(String orderId) {
        try {
            return serviceController.driverDetails(orderId);
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    public boolean Check_Phone(String phone) {
        return serviceController.Check_Phone(phone);
    }

    public boolean Check_Product_Price(String product_Price) {
        return serviceController.Check_Product_Price(product_Price);
    }

    public boolean Check_product_Quantity(String product_Quantity) {
        return serviceController.Check_product_Quantity(product_Quantity);
    }

    public boolean Check_product_Discount(String product_Discount) {
        return serviceController.Check_product_Discount(product_Discount);
    }

    public String BilOfQuantities(String supplier_id) {
        return serviceController.BilOfQuantities(supplier_id);
    }

    public boolean IsFixedDay(String Supplying_Method) {
        return serviceController.IsFixedDay(Supplying_Method);
    }

    public Integer Check_String_Greater_than_zero(String number) {
        return serviceController.Check_String_Greater_than_zero(number);
    }

    public boolean ExistedOrderChecking(String orderID) {
        return serviceController.ExistedOrderChecking(orderID);
    }

    public boolean Check_Product_ID(ArrayList<ArrayList<String>> list_of_products, String product_ID) {
        return serviceController.Check_Product_ID(list_of_products, product_ID);
    }

    public boolean Contains_product(HashMap<String, Integer> products_in_order, String product_Number) {
        return serviceController.Contains_product(products_in_order, product_Number);
    }

    public boolean check_if_there_is_more_products(int size) {
        return serviceController.check_if_there_is_more_products(size);
    }

    public String loadData() {
        try {
            return serviceController.loadData();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String ExitData() {
        try {
            return serviceController.ExitData();
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    public boolean login(String Username, String Password) {
        return serviceController.login(Username, Password);
    }
    public String getpermission(String Username){
        return serviceController.getpermission(Username);
    }
    public String addUser(String Username, String Password, String permission) {
        return serviceController.addUser(Username, Password, permission);
    }
    public String deleteUser(String Username) {
        return serviceController.deleteUser(Username);
    }
    public String EditPassword(String Username,String Password) {
        return serviceController.EditPassword(Username, Password);
    }
    // HRService methods
    public String showEmployees() throws Exception {
        return hrService.showEmployees();
    }

    public String getFirstName(String employeeId) throws Exception {
        return hrService.getFirstName(employeeId);
    }

    public String setFirstName(String employeeId, String _firstName) throws Exception {
        return hrService.setFirstName(employeeId, _firstName);
    }

    public String getLastName(String employeeId) throws Exception {
        return hrService.getLastName(employeeId);
    }

    public String setLastName(String employeeId, String _lastName) throws Exception {
        return hrService.setLastName(employeeId, _lastName);
    }

    public String registerDetails(String _employeeId) throws Exception {
        return hrService.registerDetails(_employeeId);
    }

    public String editPassword(String employeeId, String password) throws Exception {
        return hrService.editPassword(employeeId, password);
    }

    public String getAccountNumber(String employeeId) throws Exception {
        return hrService.getAccountNumber(employeeId);
    }

    public String setAccountNumber(String employeeId, int _accountNumber) throws Exception {
        return hrService.setAccountNumber(employeeId, _accountNumber);
    }

    public String getBranchBankNumber(String employeeId) throws Exception {
        return hrService.getBranchBankNumber(employeeId);
    }

    public String setBranchBankNumber(String employeeId, int _branchBankNumber) throws Exception {
        return hrService.setBranchBankNumber(employeeId, _branchBankNumber);
    }

    public String getSalary(String employeeId) throws Exception {
        return hrService.getSalary(employeeId);
    }

    public String setSalary(String employeeId, int _salary) throws Exception {
        return hrService.setSalary(employeeId, _salary);
    }

    public String getTermsOfEmployment(String employeeId) throws Exception {
        return hrService.getTermsOfEmployment(employeeId);
    }

    public String setTermsOfEmployment(String employeeId, String _termsOfEmployment) {
        return hrService.setTermsOfEmployment(employeeId, _termsOfEmployment);
    }

    public String getJobType(String employeeId) throws Exception {
        return hrService.getJobType(employeeId);
    }

    public String setJobType(String employeeId, String _jobType) throws Exception {
        return hrService.setJobType(employeeId, _jobType);
    }

    public String getStartDate(String employeeId) throws Exception {
        return hrService.getStartDate(employeeId);
    }

    public String getHRManager(String employeeId) throws Exception {
        return hrService.getHRManager(employeeId);
    }

    public String setHRManager(String employeeId, boolean isHR) throws Exception {
        return hrService.setHRManager(employeeId, isHR);
    }
    public String addDriverToShift(LocalDateTime date) throws Exception {
        try {
            String driverId = hrService.addDriverToShift(date);
            Integer.parseInt(driverId);
            return driverId;
        }
        catch (Exception e) {
            return e.getMessage();
        }
    }
    // Additional HRService methods
    public String getShowAllShifts() {
        return hrService.getShowAllShifts();
    }
    public ShiftService getShiftService() {
        return hrService.getShiftService();
    }
    public EmployeeService getEmployeeService() {
        return hrService.getEmployeeService();
    }
    public BranchService getBranchService() {
        return hrService.getBranchService();
    }
}
