package devices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LaptopTest extends ComputerTest{

    Laptop testLaptop;

    @BeforeEach
    public void setup() {
        testDevice = new Laptop("Bob");
        testComputer = new Laptop("Bob");
        testLaptop = new Laptop("Bob");
    }

    @Override
    public void testConstructor() {
        assertTrue(testLaptop.getServiceNum() > 0);
        assertEquals(0, testLaptop.checkRepairProgress());
        assertEquals("N/A", testLaptop.getBrand());
        assertFalse(testLaptop.doesPowerOn());
        assertFalse(testLaptop.hasBattery());
        assertEquals(0, testLaptop.getHardDrive());
        assertEquals(0, testLaptop.getSolidState());
        assertFalse(testLaptop.getHardDriveCaddy());
        assertEquals(0, testLaptop.getRam());
        assertEquals(0, testLaptop.getScreenCondition());
        assertEquals("N/A", testLaptop.getOS());
        assertEquals("N/A", testLaptop.getOtherNotes());
    }

    @Override
    public void testTerminateDevice() {
        Laptop l1 = new Laptop("Bob");

        testDevice.terminateDevice();
        testComputer.terminateDevice();

        Laptop l2 = new Laptop("Bob");
        Laptop l3 = new Laptop("Bob");
        Laptop l4 = new Laptop("Bob");

        assertEquals(testDevice.getServiceNum(), l2.getServiceNum());
        assertEquals(testComputer.getServiceNum(), l3.getServiceNum());
        assertTrue(l4.getServiceNum() > l1.getServiceNum());
    }

    @Test
    public void testScreen() {
        assertEquals(0, testLaptop.getScreenCondition());
        testLaptop.setScreenCondition(1);

        assertEquals(1, testLaptop.getScreenCondition());
    }

    @Test
    public void testBattery() {
        assertFalse(testLaptop.hasBattery());
        testLaptop.setBattery(true);

        assertTrue(testLaptop.hasBattery());
    }
}
