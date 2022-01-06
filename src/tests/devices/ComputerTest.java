package devices;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public abstract class ComputerTest extends RepairDeviceTest {

    Computer testComputer;

    @Test
    public void testHardDrive() {
        assertEquals(0, testComputer.getHardDrive());
        testComputer.setHardDrive(1000);

        assertEquals(1000, testComputer.getHardDrive());
    }

    @Test
    public void testSolidState() {
        assertEquals(0, testComputer.getSolidState());
        testComputer.setSolidState(250);

        assertEquals(250, testComputer.getSolidState());
    }

    @Test
    public void testRam() {
        assertEquals(0, testComputer.getRam());
        testComputer.setRam(256);

        assertEquals(256, testComputer.getRam());
    }

    @Test
    public void testHDC() {
        assertFalse(testComputer.getHardDriveCaddy());
        testComputer.setHardDriveCaddy(true);

        assertTrue(testComputer.getHardDriveCaddy());
    }

    @Test
    public void testOs() {
        assertEquals("N/A", testComputer.getOS());
        testComputer.setOS("Linux");

        assertEquals("Linux", testComputer.getOS());
    }


    @Test
    public void testGetTotalStorage() {
        testComputer.setHardDrive(1000);
        testComputer.setSolidState(1000);

        assertEquals(2000, testComputer.getTotalStorage());
    }

    @Test
    public void testSetCpu() {
        testComputer.setCpu("i5");

        assertEquals("i5", testComputer.getCpu());
    }

    @Test
    public void testSetGpu() {
        testComputer.setGpu("1080");

        assertEquals("1080", testComputer.getGpu());
    }
}
