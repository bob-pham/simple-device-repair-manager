package devices;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public abstract class RepairDeviceTest {

    RepairDevice testDevice;

    @Test
    public abstract void testConstructor();

    @Test
    public void testPower() {
        assertFalse(testDevice.doesPowerOn());
        testDevice.setPower(true);

        assertTrue(testDevice.doesPowerOn());
    }

    @Test
    public void testBrand() {
        assertEquals("N/A", testDevice.getBrand());
        testDevice.setBrand("Apple");

        assertEquals("Apple", testDevice.getBrand());
    }

    @Test
    public void testRepairProcess() {
        assertEquals(0, testDevice.checkRepairProgress());
        testDevice.setRepairProgress(4);

        assertEquals(4, testDevice.checkRepairProgress());
    }

    @Test
    public void testServiceNum() {
        assertTrue(testDevice.getServiceNum() > 0);
    }

    @Test
    public abstract void testGetTotalStorage();

    @Test
    public void testGetName() {
        assertEquals("Bob", testDevice.getUserName());
        testDevice.setUserName("Gregor");

        assertEquals("Gregor", testDevice.getUserName());
    }

    @Test
    public void testOtherNotes() {
        assertEquals("N/A", testDevice.getOtherNotes());
        testDevice.setOtherNotes("Squishy buttons");

        assertEquals("Squishy buttons", testDevice.getOtherNotes());
    }

    @Test
    public abstract void testTerminateDevice();

}
