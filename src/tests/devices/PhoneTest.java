package devices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PhoneTest extends RepairDeviceTest {

    Phone testPhone;

    @BeforeEach
    public void setup() {
        testDevice = new Phone("Bob");
        testPhone = new Phone("Bob");
    }

    @Test
    public void testConstructor() {
        assertTrue(testPhone.getServiceNum() > 0);
        assertEquals(0, testPhone.checkRepairProgress());
        assertEquals("N/A", testPhone.getBrand());
        assertFalse(testPhone.doesPowerOn());
        assertFalse(testPhone.hasBattery());
        assertEquals(0, testPhone.getTotalStorage());
        assertEquals("N/A", testPhone.getOtherNotes());
    }

    @Test
    public void testGetTotalStorage() {
        Phone p1 = new Phone("Bob");
        assertEquals(0, p1.getTotalStorage());
        p1.setStorage(32);

        assertEquals(32,p1.getTotalStorage());
    }

    @Test
    public void testTerminateDevice() {
        Phone p1 = new Phone("Bob");

        testDevice.terminateDevice();
        testPhone.terminateDevice();

        Phone p2 = new Phone("Bob");
        Phone p3 = new Phone("Bob");
        Phone p7 = new Phone("Bob");

        assertEquals(testDevice.getServiceNum(), p2.getServiceNum());
        assertEquals(testPhone.getServiceNum(), p3.getServiceNum());
        assertTrue(p7.getServiceNum() > p1.getServiceNum());
    }


    @Test
    public void testScreen() {
        assertEquals(0, testPhone.getScreenCondition());
        testPhone.setScreenCondition(1);

        assertEquals(1, testPhone.getScreenCondition());
    }

    @Test
    public void testBattery() {
        assertFalse(testPhone.hasBattery());
        testPhone.setBattery(true);

        assertTrue(testPhone.hasBattery());
    }

}
