package model;

import devices.Desktop;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListOfDesktopTest {

    ListOfDesktop testListOfDesktop;

    @BeforeEach
    public void setup() {
        testListOfDesktop = new ListOfDesktop();
    }

    @Test
    public void testStartRepair() {
        Desktop testDesktop1 = new Desktop("Bob");
        testListOfDesktop.startRepair(testDesktop1);
        assertTrue(testListOfDesktop.findDevice(testDesktop1.getServiceNum(), false) > -1);
    }

    @Test
    public void testFinishRepairDeviceThere() {
        Desktop testDesktop2 = new Desktop("Bob");
        Desktop testDesktop4 = new Desktop("Bob");
        Desktop testDesktop5 = new Desktop("Bob");
        Desktop testDesktop6 = new Desktop("Bob");
        Desktop testDesktop7 = new Desktop("Bob");

        testListOfDesktop.startRepair(testDesktop2);
        testListOfDesktop.startRepair(testDesktop4);
        testListOfDesktop.startRepair(testDesktop5);
        testListOfDesktop.startRepair(testDesktop6);
        testListOfDesktop.startRepair(testDesktop7);


        int delta = testListOfDesktop.getWipDesktops().size();
        int delta2 = testListOfDesktop.getRepairedDesktops().size();

        assertTrue(testListOfDesktop.finishRepairDevice(testDesktop2.getServiceNum()));
        assertTrue(testListOfDesktop.finishRepairDevice(testDesktop5.getServiceNum()));

        assertTrue(testListOfDesktop.getWipDesktops().size() < delta);
        assertTrue(testListOfDesktop.getRepairedDesktops().size() > delta2);

        assertFalse(testListOfDesktop.getRepairedDesktops().isEmpty());
        assertTrue(testListOfDesktop.findDevice(testDesktop2.getServiceNum(), true) > -1);
    }

    @Test
    public void testFinishRepairDeviceNot() {
        Desktop testDesktop5 = new Desktop("Bob");

        assertFalse(testListOfDesktop.finishRepairDevice(testDesktop5.getServiceNum()));

        assertTrue(testListOfDesktop.getWipDesktops().isEmpty());
        assertEquals(-1, testListOfDesktop.findDevice(testDesktop5.getServiceNum(), true));
    }

    @Test
    public void testRemoveRepairedDeviceThere() {
        Desktop testDesktop3 = new Desktop("Bob");
        testListOfDesktop.startRepair(testDesktop3);
        testListOfDesktop.finishRepairDevice(testDesktop3.getServiceNum());

        int i = testDesktop3.getServiceNum();

        assertEquals(1, testListOfDesktop.getRepairedDesktops().size());
        assertTrue(testListOfDesktop.removeRepairedDevice(i));
        assertTrue(testListOfDesktop.getRepairedDesktops().isEmpty());

        Desktop testDesktop4 = new Desktop("Bob");

        assertEquals(i, testDesktop4.getServiceNum());
    }

    @Test
    public void testRemoveRepairedDeviceNot() {
        Desktop testDesktop6 = new Desktop("Bob");

        assertFalse(testListOfDesktop.removeRepairedDevice(testDesktop6.getServiceNum()));
    }

    @Test
    public void TestRemoveWipDeviceThere() {
        Desktop testDesktop3 = new Desktop("gregor");
        testListOfDesktop.startRepair(testDesktop3);

        assertTrue(testListOfDesktop.removeWipDevice(testDesktop3.getServiceNum()));
    }

    @Test
    public void testRemoveWipDeviceNot() {
        Desktop testingDesktop = new Desktop("Karen");

        assertFalse(testListOfDesktop.removeWipDevice(testingDesktop.getServiceNum()));
    }

    @Test
    public void testGetLaptop() {
        Desktop testDesktop7 = new Desktop("Bob");
        testListOfDesktop.startRepair(testDesktop7);

        assertEquals(testDesktop7, testListOfDesktop.getDesktop(testListOfDesktop.findDevice(testDesktop7.getServiceNum(),
                false), false));
        assertTrue(testListOfDesktop.finishRepairDevice(testDesktop7.getServiceNum()));

        assertEquals(testDesktop7, testListOfDesktop.getDesktop(testListOfDesktop.findDevice(testDesktop7.getServiceNum(),
                true), true));

    }

    @Test
    public void testServiceNumIsBrandSomeWip() {
        Desktop testDesktop8 = new Desktop("Bob");
        Desktop testDesktop9 = new Desktop("Bob");
        Desktop testDesktop10 = new Desktop("Bob");
        Desktop testDesktop11 = new Desktop("Bob");

        testDesktop8.setBrand("Apple");
        testDesktop10.setBrand("Apple");
        testDesktop11.setBrand("Apple");

        testListOfDesktop.startRepair(testDesktop8);
        testListOfDesktop.startRepair(testDesktop9);
        testListOfDesktop.startRepair(testDesktop10);
        testListOfDesktop.startRepair(testDesktop11);

        ArrayList<Integer> serviceNums = testListOfDesktop.serviceNumsIsBrand("Apple", false);

        assertEquals(3, serviceNums.size());
        assertTrue(serviceNums.contains(testDesktop8.getServiceNum()));
        assertTrue(serviceNums.contains(testDesktop10.getServiceNum()));
        assertTrue(serviceNums.contains(testDesktop11.getServiceNum()));
        assertFalse(serviceNums.contains(testDesktop9.getServiceNum()));
    }

    @Test
    public void testServiceNumIsBrandNoneWip() {
        Desktop testDesktop12 = new Desktop("Bob");
        Desktop testDesktop13 = new Desktop("Bob");

        testListOfDesktop.startRepair(testDesktop12);
        testListOfDesktop.startRepair(testDesktop13);

        ArrayList<Integer> serviceNums = testListOfDesktop.serviceNumsIsBrand("Samsung",false);

        assertTrue(serviceNums.isEmpty());
    }

    @Test
    public void testServiceNumIsBrandSomeRepaired() {
        Desktop testDesktop14 = new Desktop("Bob");
        Desktop testDesktop15 = new Desktop("Bob");
        Desktop testDesktop16 = new Desktop("Bob");

        testDesktop14.setBrand("OnePlus");
        testDesktop15.setBrand("OnePlus");

        testListOfDesktop.startRepair(testDesktop14);
        testListOfDesktop.finishRepairDevice(testDesktop14.getServiceNum());
        testListOfDesktop.startRepair(testDesktop15);
        testListOfDesktop.finishRepairDevice(testDesktop15.getServiceNum());
        testListOfDesktop.startRepair(testDesktop16);
        testListOfDesktop.finishRepairDevice(testDesktop16.getServiceNum());

        ArrayList<Integer> serviceNums = testListOfDesktop.serviceNumsIsBrand("OnePlus", true);

        assertEquals(2, serviceNums.size());
        assertTrue(serviceNums.contains(testDesktop14.getServiceNum()));
        assertTrue(serviceNums.contains(testDesktop15.getServiceNum()));
        assertFalse(serviceNums.contains(testDesktop16.getServiceNum()));
    }

    @Test
    public void testServiceNumIsBrandNoneRepaired() {
        Desktop testDesktop17 = new Desktop("Bob");
        Desktop testDesktop18 = new Desktop("Bob");

        testListOfDesktop.startRepair(testDesktop17);
        testListOfDesktop.startRepair(testDesktop18);
        testListOfDesktop.finishRepairDevice(testDesktop17.getServiceNum());
        testListOfDesktop.finishRepairDevice(testDesktop18.getServiceNum());

        ArrayList<Integer> serviceNums = testListOfDesktop.serviceNumsIsBrand("LG",true);

        assertTrue(serviceNums.isEmpty());
    }

    @Test
    public void testServiceNumIsCpuSomeWip() {
        Desktop testDesktop19 = new Desktop("Bob");
        Desktop testDesktop20 = new Desktop("Bob");
        Desktop testDesktop21 = new Desktop("Bob");

        testDesktop19.setCpu("i5");
        testDesktop20.setCpu("i5");

        testListOfDesktop.startRepair(testDesktop19);
        testListOfDesktop.startRepair(testDesktop20);
        testListOfDesktop.startRepair(testDesktop21);

        ArrayList<Integer> serviceNums = testListOfDesktop.serviceNumsIsCpu("i5",false);

        assertEquals(2, serviceNums.size());
        assertTrue(serviceNums.contains(testDesktop19.getServiceNum()));
        assertTrue(serviceNums.contains(testDesktop20.getServiceNum()));
        assertFalse(serviceNums.contains(testDesktop21.getServiceNum()));
    }

    @Test
    public void testServiceNumIsCpuNoneWip() {
        Desktop testDesktop22 = new Desktop("Bob");
        Desktop testDesktop23 = new Desktop("Bob");

        testListOfDesktop.startRepair(testDesktop22);
        testListOfDesktop.startRepair(testDesktop23);

        ArrayList<Integer> serviceNums = testListOfDesktop.serviceNumsIsCpu("i9", false);

        assertTrue(serviceNums.isEmpty());
    }

    @Test
    public void testServiceNumIsCpuSomeRepaired() {
        Desktop testDesktop24 = new Desktop("Bob");
        Desktop testDesktop25 = new Desktop("Bob");
        Desktop testDesktop26 = new Desktop("Bob");

        testDesktop24.setCpu("i3");
        testDesktop25.setCpu("i3");

        testListOfDesktop.startRepair(testDesktop24);
        testListOfDesktop.startRepair(testDesktop25);
        testListOfDesktop.startRepair(testDesktop26);

        testListOfDesktop.finishRepairDevice(testDesktop24.getServiceNum());
        testListOfDesktop.finishRepairDevice(testDesktop25.getServiceNum());
        testListOfDesktop.finishRepairDevice(testDesktop26.getServiceNum());

        ArrayList<Integer> serviceNums = testListOfDesktop.serviceNumsIsCpu("i3", true);

        assertEquals(2, serviceNums.size());
        assertTrue(serviceNums.contains(testDesktop24.getServiceNum()));
        assertTrue(serviceNums.contains(testDesktop25.getServiceNum()));
        assertFalse(serviceNums.contains(testDesktop26.getServiceNum()));
    }

    @Test
    public void testServiceNumIsCpuNoneRepaired() {
        Desktop testDesktop27 = new Desktop("Bob");

        testDesktop27.setCpu("pentium");

        testListOfDesktop.startRepair(testDesktop27);
        testListOfDesktop.finishRepairDevice(testDesktop27.getServiceNum());

        ArrayList<Integer> serviceNums = testListOfDesktop.serviceNumsIsCpu("atom", true);

        assertTrue(serviceNums.isEmpty());
    }

    @Test
    public void testServiceNumIsGpuSomeWip() {
        Desktop testDesktop28 = new Desktop("Bob");
        Desktop testDesktop29 = new Desktop("Bob");
        Desktop testDesktop30 = new Desktop("Bob");

        testDesktop28.setGpu("1080");
        testDesktop29.setGpu("1080");

        testListOfDesktop.startRepair(testDesktop28);
        testListOfDesktop.startRepair(testDesktop29);
        testListOfDesktop.startRepair(testDesktop30);

        ArrayList<Integer> serviceNums = testListOfDesktop.serviceNumsIsGpu("1080",false);

        assertEquals(2, serviceNums.size());
        assertTrue(serviceNums.contains(testDesktop28.getServiceNum()));
        assertTrue(serviceNums.contains(testDesktop29.getServiceNum()));
        assertFalse(serviceNums.contains(testDesktop30.getServiceNum()));
    }

    @Test
    public void testServiceNumIsGpuNoneWip() {
        Desktop testDesktop31 = new Desktop("Bob");
        Desktop testDesktop32 = new Desktop("Bob");

        testListOfDesktop.startRepair(testDesktop31);
        testListOfDesktop.startRepair(testDesktop32);

        ArrayList<Integer> serviceNums = testListOfDesktop.serviceNumsIsGpu("1050", false);

        assertTrue(serviceNums.isEmpty());
    }

    @Test
    public void testServiceNumIsGpuSomeRepaired() {
        Desktop testDesktop33 = new Desktop("Bob");
        Desktop testDesktop34 = new Desktop("Bob");
        Desktop testDesktop35 = new Desktop("Bob");

        testDesktop33.setGpu("2080");
        testDesktop34.setGpu("2080");

        testListOfDesktop.startRepair(testDesktop33);
        testListOfDesktop.startRepair(testDesktop34);
        testListOfDesktop.startRepair(testDesktop35);

        testListOfDesktop.finishRepairDevice(testDesktop33.getServiceNum());
        testListOfDesktop.finishRepairDevice(testDesktop34.getServiceNum());
        testListOfDesktop.finishRepairDevice(testDesktop35.getServiceNum());

        ArrayList<Integer> serviceNums = testListOfDesktop.serviceNumsIsGpu("2080", true);

        assertEquals(2, serviceNums.size());
        assertTrue(serviceNums.contains(testDesktop33.getServiceNum()));
        assertTrue(serviceNums.contains(testDesktop34.getServiceNum()));
        assertFalse(serviceNums.contains(testDesktop35.getServiceNum()));
    }

    @Test
    public void testServiceNumIsGpuNoneRepaired() {
        Desktop testDesktop36 = new Desktop("Bob");

        testDesktop36.setGpu("3090");

        testListOfDesktop.startRepair(testDesktop36);
        testListOfDesktop.finishRepairDevice(testDesktop36.getServiceNum());

        ArrayList<Integer> serviceNums = testListOfDesktop.serviceNumsIsGpu("3080", true);

        assertTrue(serviceNums.isEmpty());
    }

    @Test
    public void testGetDeviceFromServiceNumNone() {
        Desktop testDesktop1 = new Desktop("Bruh");
        Desktop testDesktop2 = new Desktop("ASD");

        testListOfDesktop.startRepair(testDesktop1);
        testListOfDesktop.startRepair(testDesktop2);
        testListOfDesktop.finishRepairDevice(testDesktop2.getServiceNum());

        assertNull(testListOfDesktop.getDeviceFromServiceNum(-1,true));
        assertNull(testListOfDesktop.getDeviceFromServiceNum(-1,false));
    }

    @Test
    public void testGetDeviceFromServiceNumHas() {
        Desktop testDesktop1 = new Desktop("Bruh");
        testListOfDesktop.startRepair(testDesktop1);

        assertEquals(testDesktop1, testListOfDesktop.getDeviceFromServiceNum(testDesktop1.getServiceNum(), false));
        assertTrue(testListOfDesktop.finishRepairDevice(testDesktop1.getServiceNum()));

        assertEquals(testDesktop1, testListOfDesktop.getDeviceFromServiceNum(testDesktop1.getServiceNum(), true));
    }

    @Test
    public void testFindDevice() {
        Desktop testDesktopNot = new Desktop("Bob");
        Desktop testDesktop1 = new Desktop("Bob");
        Desktop testDesktop2 = new Desktop("Bob");
        Desktop testDesktop3 = new Desktop("Bob");
        Desktop testDesktop4 = new Desktop("Bob");

        testListOfDesktop.startRepair(testDesktop3);
        testListOfDesktop.startRepair(testDesktop2);
        testListOfDesktop.startRepair(testDesktop1);
        testListOfDesktop.startRepair(testDesktop4);

        assertTrue(testListOfDesktop.finishRepairDevice(testDesktop4.getServiceNum()));
        assertTrue(testListOfDesktop.finishRepairDevice(testDesktop2.getServiceNum()));

        assertTrue(testListOfDesktop.findDevice(testDesktop1.getServiceNum(),false) != -1);
        assertTrue(testListOfDesktop.findDevice(testDesktop2.getServiceNum(),true) != -1);
        assertEquals(-1, testListOfDesktop.findDevice(testDesktopNot.getServiceNum(), false));
        assertEquals(-1, testListOfDesktop.findDevice(testDesktopNot.getServiceNum(), true));
    }
}
