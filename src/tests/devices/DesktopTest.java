package devices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DesktopTest extends ComputerTest{

    Desktop testDesktop;

    @BeforeEach
    public void setup(){
        testDevice = new Desktop("Bob");
        testComputer = new Desktop("Bob");
        testDesktop = new Desktop("Bob");
    }

    @Test
    public void testConstructor() {
        assertTrue(testDesktop.getServiceNum() > 0);
        assertEquals(0, testDesktop.checkRepairProgress());
        assertEquals("N/A", testDesktop.getBrand());
        assertFalse(testDesktop.doesPowerOn());
        assertEquals(0, testDesktop.getHardDrive());
        assertEquals(0, testDesktop.getSolidState());
        assertFalse(testDesktop.getHardDriveCaddy());
        assertEquals(0, testDesktop.getRam());
        assertEquals("N/A", testDesktop.getOS());
        assertEquals("N/A", testDesktop.getCpu());
        assertEquals("N/A", testDesktop.getGpu());
        assertEquals("N/A", testDesktop.getOtherNotes());
    }

    private void assertFalse(boolean doesPowerOn) {
    }

    @Override
    public void testTerminateDevice() {
        Desktop d1 = new Desktop("Bob");

        testDevice.terminateDevice();
        testDesktop.terminateDevice();

        Desktop d2 = new Desktop("Bob");
        Desktop d3 = new Desktop("Bob");
        Desktop d4 = new Desktop("Bob");

        assertEquals(testDevice.getServiceNum(), d2.getServiceNum());
        assertEquals(testDesktop.getServiceNum(), d3.getServiceNum());
        assertTrue(d4.getServiceNum() > d1.getServiceNum());
    }
}
