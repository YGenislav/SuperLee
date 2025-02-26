package integration_tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import suppliers.DomainLayer.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderDriverTest {
    private Supplier_Controller supplierController;


    @BeforeEach
    void setUp() {
        supplierController = Supplier_Controller.getInstance();
    }

    @Test
    public void testAddOrderDriver() {
        String DriverId = supplierController.getdriverid("16");
        assertEquals(null, DriverId);
        supplierController.addOrderDriver("16", "000000004", "2024-09-10", "4", "0541234567");
        DriverId = supplierController.getdriverid("16");
        assertEquals("000000004", DriverId);
        supplierController.deleteOrderDriver("16");
    }
    @Test
    public void testDeleteOrderDriver() {
        supplierController.addOrderDriver("16", "000000004", "2024-09-10", "4", "0541234567");
        String DriverId = supplierController.getdriverid("16");
        assertEquals("000000004", DriverId);
        supplierController.deleteOrderDriver("16");
        DriverId = supplierController.getdriverid("16");
        assertEquals(null, DriverId);
    }
    @Test
    public void testGetDriverDetails() {
        supplierController.addOrderDriver("16", "000000004", "2024-09-10", "4", "0541234567");
        String DriverDetails = supplierController.driverDetails("16");
        assertEquals("***********************************************************************\n"+ "Order ID: 16\n" + "Driver ID: 000000004\n" + "Order Date: 2024-09-10\n" + "Supplier ID: 4\n" + "Contact Phone: 0541234567\n"+"\n***********************************************************************\n\n", DriverDetails);
        supplierController.deleteOrderDriver("16");
    }
}
