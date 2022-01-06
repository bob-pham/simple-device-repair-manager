package model;

import devices.Laptop;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListOfLaptopTest {
    Laptop testLaptop;
    ListOfLaptop testListOfLaptop;

    @BeforeEach
    public void setup() {
        testLaptop = new Laptop("Bobby");
        testListOfLaptop = new ListOfLaptop();
    }

    @Test
    public void testStartRepair() {
        testListOfLaptop.startRepair(testLaptop);

        assertEquals(testLaptop, testListOfLaptop.getLaptop(0,false));
        assertTrue(testListOfLaptop.getRepairedLaptops().isEmpty());
    }

    @Test
    public void testFinishRepairDeviceThere() {
        testListOfLaptop.startRepair(testLaptop);

        assertTrue(testListOfLaptop.finishRepairDevice(testLaptop.getServiceNum()));

        assertTrue(testListOfLaptop.getWipLaptops().isEmpty());
        assertEquals(testLaptop, testListOfLaptop.getLaptop(0,true));
    }

    @Test
    public void testFinishRepairDeviceWipEmpty() {
        assertFalse(testListOfLaptop.finishRepairDevice(testLaptop.getServiceNum()));
    }

    @Test
    public void testFinishRepairDeviceNotInWip() {
        Laptop testLaptop1 = new Laptop("gregor");

        testListOfLaptop.startRepair(testLaptop1);
        assertFalse(testListOfLaptop.finishRepairDevice(testLaptop.getServiceNum()));

        assertTrue(testListOfLaptop.getRepairedLaptops().isEmpty());
    }

    @Test
    public void testRemoveRepairedDeviceThere() {
        testListOfLaptop.startRepair(testLaptop);

        assertTrue(testListOfLaptop.finishRepairDevice(testLaptop.getServiceNum()));

        int i = testLaptop.getServiceNum();

        assertTrue(testListOfLaptop.removeRepairedDevice(i));
        assertTrue(testListOfLaptop.getRepairedLaptops().isEmpty());

        Laptop testLaptop4 = new Laptop("Bob");

        assertEquals(i, testLaptop4.getServiceNum());
    }

    @Test
    public void testRemoveRepairedDeviceEmpty() {
        assertFalse(testListOfLaptop.removeRepairedDevice(testLaptop.getServiceNum()));
    }

    @Test
    public void testRemoveRepairedDeviceNotThere() {
        Laptop testLaptop1 = new Laptop("bruh");

        testListOfLaptop.startRepair(testLaptop);
        assertTrue(testListOfLaptop.finishRepairDevice(testLaptop.getServiceNum()));

        assertFalse(testListOfLaptop.removeRepairedDevice(testLaptop1.getServiceNum()));
    }

    @Test
    public void TestRemoveWipDeviceThere() {
        testListOfLaptop.startRepair(testLaptop);

        int oldCode = testLaptop.getServiceNum();

        assertFalse(testListOfLaptop.getWipLaptops().isEmpty());
        assertTrue(testListOfLaptop.removeWipDevice(testLaptop.getServiceNum()));
        assertTrue(testListOfLaptop.getWipLaptops().isEmpty());

        Laptop testLaptop1 = new Laptop("gregor");

        assertEquals(oldCode, testLaptop1.getServiceNum());
    }

    @Test
    public void testRemoveWipDeviceEmpty() {
        assertFalse(testListOfLaptop.removeWipDevice(testLaptop.getServiceNum()));
    }

    @Test
    public void testRemoveWipDeviceNotThere() {
        Laptop testingLaptop = new Laptop("bruh");

        testListOfLaptop.startRepair(testLaptop);
        assertFalse(testListOfLaptop.removeWipDevice(testingLaptop.getServiceNum()));
    }

    @Test
    public void testGetLaptop() {
        Laptop testLaptop7 = new Laptop("Bob");
        testListOfLaptop.startRepair(testLaptop7);

        assertEquals(testLaptop7, testListOfLaptop.getLaptop(testListOfLaptop.findDevice(testLaptop7.getServiceNum(),
                false), false));
        assertTrue(testListOfLaptop.finishRepairDevice(testLaptop7.getServiceNum()));

        assertEquals(testLaptop7, testListOfLaptop.getLaptop(testListOfLaptop.findDevice(testLaptop7.getServiceNum(),
                true), true));

    }

    @Test
    public void testServiceNumIsBrandSomeWip() {
        Laptop testLaptop8 = new Laptop("Bob");
        Laptop testLaptop9 = new Laptop("Bob");
        Laptop testLaptop10 = new Laptop("Bob");
        Laptop testLaptop11 = new Laptop("Bob");

        testLaptop8.setBrand("Apple");
        testLaptop10.setBrand("Apple");
        testLaptop11.setBrand("Apple");

        testListOfLaptop.startRepair(testLaptop8);
        testListOfLaptop.startRepair(testLaptop9);
        testListOfLaptop.startRepair(testLaptop10);
        testListOfLaptop.startRepair(testLaptop11);

        ArrayList<Integer> serviceNums = testListOfLaptop.serviceNumsIsBrand("Apple", false);

        assertEquals(3, serviceNums.size());
        assertTrue(serviceNums.contains(testLaptop8.getServiceNum()));
        assertTrue(serviceNums.contains(testLaptop10.getServiceNum()));
        assertTrue(serviceNums.contains(testLaptop11.getServiceNum()));
        assertFalse(serviceNums.contains(testLaptop9.getServiceNum()));
    }

    @Test
    public void testServiceNumIsBrandNoneWip() {
        Laptop testLaptop12 = new Laptop("Bob");
        Laptop testLaptop13 = new Laptop("Bob");

        testListOfLaptop.startRepair(testLaptop12);
        testListOfLaptop.startRepair(testLaptop13);

        ArrayList<Integer> serviceNums = testListOfLaptop.serviceNumsIsBrand("Samsung",false);

        assertTrue(serviceNums.isEmpty());
    }

    @Test
    public void testServiceNumIsBrandSomeRepaired() {
        Laptop testLaptop14 = new Laptop("Bob");
        Laptop testLaptop15 = new Laptop("Bob");
        Laptop testLaptop16 = new Laptop("Bob");

        testLaptop14.setBrand("OnePlus");
        testLaptop15.setBrand("OnePlus");

        testListOfLaptop.startRepair(testLaptop14);
        testListOfLaptop.finishRepairDevice(testLaptop14.getServiceNum());
        testListOfLaptop.startRepair(testLaptop15);
        testListOfLaptop.finishRepairDevice(testLaptop15.getServiceNum());
        testListOfLaptop.startRepair(testLaptop16);
        testListOfLaptop.finishRepairDevice(testLaptop16.getServiceNum());

        ArrayList<Integer> serviceNums = testListOfLaptop.serviceNumsIsBrand("OnePlus", true);

        assertEquals(2, serviceNums.size());
        assertTrue(serviceNums.contains(testLaptop14.getServiceNum()));
        assertTrue(serviceNums.contains(testLaptop15.getServiceNum()));
        assertFalse(serviceNums.contains(testLaptop16.getServiceNum()));
    }

    @Test
    public void testServiceNumIsBrandNoneRepaired() {
        Laptop testLaptop17 = new Laptop("Bob");
        Laptop testLaptop18 = new Laptop("Bob");

        testListOfLaptop.startRepair(testLaptop17);
        testListOfLaptop.startRepair(testLaptop18);
        testListOfLaptop.finishRepairDevice(testLaptop17.getServiceNum());
        testListOfLaptop.finishRepairDevice(testLaptop18.getServiceNum());

        ArrayList<Integer> serviceNums = testListOfLaptop.serviceNumsIsBrand("LG",true);

        assertTrue(serviceNums.isEmpty());
    }

    @Test
    public void testServiceNumIsCpuSomeWip() {
        Laptop testLaptop19 = new Laptop("Bob");
        Laptop testLaptop20 = new Laptop("Bob");
        Laptop testLaptop21 = new Laptop("Bob");

        testLaptop19.setCpu("i5");
        testLaptop20.setCpu("i5");

        testListOfLaptop.startRepair(testLaptop19);
        testListOfLaptop.startRepair(testLaptop20);
        testListOfLaptop.startRepair(testLaptop21);

        ArrayList<Integer> serviceNums = testListOfLaptop.serviceNumsIsCpu("i5",false);

        assertEquals(2, serviceNums.size());
        assertTrue(serviceNums.contains(testLaptop19.getServiceNum()));
        assertTrue(serviceNums.contains(testLaptop20.getServiceNum()));
        assertFalse(serviceNums.contains(testLaptop21.getServiceNum()));
    }

    @Test
    public void testServiceNumIsCpuNoneWip() {
        Laptop testLaptop22 = new Laptop("Bob");
        Laptop testLaptop23 = new Laptop("Bob");

        testListOfLaptop.startRepair(testLaptop22);
        testListOfLaptop.startRepair(testLaptop23);

        ArrayList<Integer> serviceNums = testListOfLaptop.serviceNumsIsCpu("i9", false);

        assertTrue(serviceNums.isEmpty());
    }

    @Test
    public void testServiceNumIsCpuSomeRepaired() {
        Laptop testLaptop24 = new Laptop("Bob");
        Laptop testLaptop25 = new Laptop("Bob");
        Laptop testLaptop26 = new Laptop("Bob");

        testLaptop24.setCpu("i3");
        testLaptop25.setCpu("i3");

        testListOfLaptop.startRepair(testLaptop24);
        testListOfLaptop.startRepair(testLaptop25);
        testListOfLaptop.startRepair(testLaptop26);

        testListOfLaptop.finishRepairDevice(testLaptop24.getServiceNum());
        testListOfLaptop.finishRepairDevice(testLaptop25.getServiceNum());
        testListOfLaptop.finishRepairDevice(testLaptop26.getServiceNum());

        ArrayList<Integer> serviceNums = testListOfLaptop.serviceNumsIsCpu("i3", true);

        assertEquals(2, serviceNums.size());
        assertTrue(serviceNums.contains(testLaptop24.getServiceNum()));
        assertTrue(serviceNums.contains(testLaptop25.getServiceNum()));
        assertFalse(serviceNums.contains(testLaptop26.getServiceNum()));
    }

    @Test
    public void testServiceNumIsCpuNoneRepaired() {
        Laptop testLaptop27 = new Laptop("Bob");

        testLaptop27.setCpu("pentium");

        testListOfLaptop.startRepair(testLaptop27);
        testListOfLaptop.finishRepairDevice(testLaptop27.getServiceNum());

        ArrayList<Integer> serviceNums = testListOfLaptop.serviceNumsIsCpu("atom", true);

        assertTrue(serviceNums.isEmpty());
    }

    @Test
    public void testServiceNumIsGpuSomeWip() {
        Laptop testLaptop28 = new Laptop("Bob");
        Laptop testLaptop29 = new Laptop("Bob");
        Laptop testLaptop30 = new Laptop("Bob");

        testLaptop28.setGpu("1080");
        testLaptop29.setGpu("1080");

        testListOfLaptop.startRepair(testLaptop28);
        testListOfLaptop.startRepair(testLaptop29);
        testListOfLaptop.startRepair(testLaptop30);

        ArrayList<Integer> serviceNums = testListOfLaptop.serviceNumsIsGpu("1080",false);

        assertEquals(2, serviceNums.size());
        assertTrue(serviceNums.contains(testLaptop28.getServiceNum()));
        assertTrue(serviceNums.contains(testLaptop29.getServiceNum()));
        assertFalse(serviceNums.contains(testLaptop30.getServiceNum()));
    }

    @Test
    public void testServiceNumIsGpuNoneWip() {
        Laptop testLaptop31 = new Laptop("Bob");
        Laptop testLaptop32 = new Laptop("Bob");

        testListOfLaptop.startRepair(testLaptop31);
        testListOfLaptop.startRepair(testLaptop32);

        ArrayList<Integer> serviceNums = testListOfLaptop.serviceNumsIsGpu("1050", false);

        assertTrue(serviceNums.isEmpty());
    }

    @Test
    public void testServiceNumIsGpuSomeRepaired() {
        Laptop testLaptop33 = new Laptop("Bob");
        Laptop testLaptop34 = new Laptop("Bob");
        Laptop testLaptop35 = new Laptop("Bob");

        testLaptop33.setGpu("2080");
        testLaptop34.setGpu("2080");

        testListOfLaptop.startRepair(testLaptop33);
        testListOfLaptop.startRepair(testLaptop34);
        testListOfLaptop.startRepair(testLaptop35);

        testListOfLaptop.finishRepairDevice(testLaptop33.getServiceNum());
        testListOfLaptop.finishRepairDevice(testLaptop34.getServiceNum());
        testListOfLaptop.finishRepairDevice(testLaptop35.getServiceNum());

        ArrayList<Integer> serviceNums = testListOfLaptop.serviceNumsIsGpu("2080", true);

        assertEquals(2, serviceNums.size());
        assertTrue(serviceNums.contains(testLaptop33.getServiceNum()));
        assertTrue(serviceNums.contains(testLaptop34.getServiceNum()));
        assertFalse(serviceNums.contains(testLaptop35.getServiceNum()));
    }

    @Test
    public void testServiceNumIsGpuNoneRepaired() {
        Laptop testLaptop36 = new Laptop("Bob");

        testLaptop36.setGpu("3090");

        testListOfLaptop.startRepair(testLaptop36);
        testListOfLaptop.finishRepairDevice(testLaptop36.getServiceNum());

        ArrayList<Integer> serviceNums = testListOfLaptop.serviceNumsIsGpu("3080", true);

        assertTrue(serviceNums.isEmpty());
    }

    @Test
    public void testGetDeviceFromServiceNumNone() {
        Laptop testLaptop1 = new Laptop("Bruh");
        Laptop testLaptop2 = new Laptop("ASD");

        testListOfLaptop.startRepair(testLaptop1);
        testListOfLaptop.startRepair(testLaptop2);
        testListOfLaptop.finishRepairDevice(testLaptop2.getServiceNum());

        assertNull(testListOfLaptop.getDeviceFromServiceNum(-1,true));
        assertNull(testListOfLaptop.getDeviceFromServiceNum(-1,false));
    }

    @Test
    public void testGetDeviceFromServiceNumHas() {
        Laptop testLaptop1 = new Laptop("Bruh");
        testListOfLaptop.startRepair(testLaptop1);

        assertEquals(testLaptop1, testListOfLaptop.getDeviceFromServiceNum(testLaptop1.getServiceNum(), false));
        assertTrue(testListOfLaptop.finishRepairDevice(testLaptop1.getServiceNum()));

        assertEquals(testLaptop1, testListOfLaptop.getDeviceFromServiceNum(testLaptop1.getServiceNum(), true));
    }

    @Test
    public void testFindDevice() {
        Laptop testLaptopNot = new Laptop("Bob");
        Laptop testLaptop1 = new Laptop("Bob");
        Laptop testLaptop2 = new Laptop("Bob");
        Laptop testLaptop3 = new Laptop("Bob");
        Laptop testLaptop4 = new Laptop("Bob");

        testListOfLaptop.startRepair(testLaptop3);
        testListOfLaptop.startRepair(testLaptop2);
        testListOfLaptop.startRepair(testLaptop1);
        testListOfLaptop.startRepair(testLaptop4);

        assertTrue(testListOfLaptop.finishRepairDevice(testLaptop4.getServiceNum()));
        assertTrue(testListOfLaptop.finishRepairDevice(testLaptop2.getServiceNum()));

        assertTrue(testListOfLaptop.findDevice(testLaptop1.getServiceNum(),false) != -1);
        assertTrue(testListOfLaptop.findDevice(testLaptop2.getServiceNum(),true) != -1);
        assertEquals(-1, testListOfLaptop.findDevice(testLaptopNot.getServiceNum(), false));
        assertEquals(-1, testListOfLaptop.findDevice(testLaptopNot.getServiceNum(), true));
    }

}
