package model;

import devices.Phone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListOfPhoneTest {

    ListOfPhone testListOfPhone;

    @BeforeEach
    public void setup() {
        testListOfPhone = new ListOfPhone();
    }

    @Test
    public void testStartRepair() {
        Phone testPhone1 = new Phone("Bob");
        testListOfPhone.startRepair(testPhone1);
        assertTrue(testListOfPhone.findDevice(testPhone1.getServiceNum(), false) > -1);
    }

    @Test
    public void testFinishRepairDeviceThere() {
        Phone testPhone2 = new Phone("Bob");
        testListOfPhone.startRepair(testPhone2);

        assertTrue(testListOfPhone.finishRepairDevice(testPhone2.getServiceNum()));

        assertEquals(-1, testListOfPhone.findDevice(testPhone2.getServiceNum(), false));
        assertTrue(testListOfPhone.findDevice(testPhone2.getServiceNum(), true) > -1);
    }

    @Test
    public void testFinishRepairDeviceNot() {
        Phone testPhone5 = new Phone("Bob");

        assertFalse(testListOfPhone.finishRepairDevice(testPhone5.getServiceNum()));

        assertEquals(-1, testListOfPhone.findDevice(testPhone5.getServiceNum(), false));
        assertEquals(-1, testListOfPhone.findDevice(testPhone5.getServiceNum(), true));
    }

    @Test
    public void testRemoveRepairedDeviceThere() {
        Phone testPhone3 = new Phone("Bob");
        testListOfPhone.startRepair(testPhone3);
        testListOfPhone.finishRepairDevice(testPhone3.getServiceNum());

        int i = testPhone3.getServiceNum();

        assertTrue(testListOfPhone.removeRepairedDevice(i));
        assertEquals(-1, testListOfPhone.findDevice(i, true));

        Phone testPhone4 = new Phone("Bob");

        assertEquals(i, testPhone4.getServiceNum());
    }

    @Test
    public void testRemoveRepairedDeviceNot() {
        Phone testPhone6 = new Phone("Bob");

        assertFalse(testListOfPhone.removeRepairedDevice(testPhone6.getServiceNum()));
    }

    @Test
    public void TestRemoveWipDeviceThere() {
        Phone testPhone3 = new Phone("gregor");
        testListOfPhone.startRepair(testPhone3);

        assertTrue(testListOfPhone.removeWipDevice(testPhone3.getServiceNum()));
    }

    @Test
    public void testRemoveWipDeviceNot() {
        Phone testingPhone = new Phone("Karen");

        assertFalse(testListOfPhone.removeWipDevice(testingPhone.getServiceNum()));
    }

    @Test
    public void testGetPhone() {
        Phone testPhone7 = new Phone("Bob");
        testListOfPhone.startRepair(testPhone7);

        assertEquals(testPhone7, testListOfPhone.getPhone(testListOfPhone.findDevice(testPhone7.getServiceNum(),
                false), false));
        assertTrue(testListOfPhone.finishRepairDevice(testPhone7.getServiceNum()));

        assertEquals(testPhone7, testListOfPhone.getPhone(testListOfPhone.findDevice(testPhone7.getServiceNum(),
                true), true));

    }

    @Test
    public void testServiceNumIsBrandSomeWip() {
        Phone testPhone8 = new Phone("Bob");
        Phone testPhone9 = new Phone("Bob");
        Phone testPhone10 = new Phone("Bob");
        Phone testPhone11 = new Phone("Bob");

        testPhone8.setBrand("Apple");
        testPhone10.setBrand("Apple");
        testPhone11.setBrand("Apple");

        testListOfPhone.startRepair(testPhone8);
        testListOfPhone.startRepair(testPhone9);
        testListOfPhone.startRepair(testPhone10);
        testListOfPhone.startRepair(testPhone11);

        ArrayList<Integer> serviceNums = testListOfPhone.serviceNumsIsBrand("Apple", false);

        assertEquals(3, serviceNums.size());
        assertTrue(serviceNums.contains(testPhone8.getServiceNum()));
        assertTrue(serviceNums.contains(testPhone10.getServiceNum()));
        assertTrue(serviceNums.contains(testPhone11.getServiceNum()));
        assertFalse(serviceNums.contains(testPhone9.getServiceNum()));
    }

    @Test
    public void testServiceNumIsBrandNoneWip() {
        Phone testPhone12 = new Phone("Bob");
        Phone testPhone13 = new Phone("Bob");

        testListOfPhone.startRepair(testPhone12);
        testListOfPhone.startRepair(testPhone13);

        ArrayList<Integer> serviceNums = testListOfPhone.serviceNumsIsBrand("Samsung",false);

        assertTrue(serviceNums.isEmpty());
    }

    @Test
    public void testServiceNumIsBrandSomeRepaired() {
        Phone testPhone14 = new Phone("Bob");
        Phone testPhone15 = new Phone("Bob");
        Phone testPhone16 = new Phone("Bob");

        testPhone14.setBrand("OnePlus");
        testPhone15.setBrand("OnePlus");

        testListOfPhone.startRepair(testPhone14);
        testListOfPhone.finishRepairDevice(testPhone14.getServiceNum());
        testListOfPhone.startRepair(testPhone15);
        testListOfPhone.finishRepairDevice(testPhone15.getServiceNum());
        testListOfPhone.startRepair(testPhone16);
        testListOfPhone.finishRepairDevice(testPhone16.getServiceNum());

        ArrayList<Integer> serviceNums = testListOfPhone.serviceNumsIsBrand("OnePlus", true);

        assertEquals(2, serviceNums.size());
        assertTrue(serviceNums.contains(testPhone14.getServiceNum()));
        assertTrue(serviceNums.contains(testPhone15.getServiceNum()));
        assertFalse(serviceNums.contains(testPhone16.getServiceNum()));
    }

    @Test
    public void testServiceNumIsBrandNoneRepaired() {
        Phone testPhone17 = new Phone("Bob");
        Phone testPhone18 = new Phone("Bob");

        testListOfPhone.startRepair(testPhone17);
        testListOfPhone.startRepair(testPhone18);
        testListOfPhone.finishRepairDevice(testPhone17.getServiceNum());
        testListOfPhone.finishRepairDevice(testPhone18.getServiceNum());

        ArrayList<Integer> serviceNums = testListOfPhone.serviceNumsIsBrand("LG",true);

        assertTrue(serviceNums.isEmpty());
    }

    @Test
    public void testGetListOfPhoneWip() {
        Phone p1 = new Phone("Gregor");
        testListOfPhone.startRepair(p1);
        assertTrue(testListOfPhone.getWipPhones().size() > 0);
    }

    @Test
    public void testGetListOfPhoneRepaired() {
        Phone p1 = new Phone("Gregor");
        testListOfPhone.startRepair(p1);
        testListOfPhone.finishRepairDevice(p1.getServiceNum());
        assertTrue(testListOfPhone.getRepairedPhones().size() > 0);
    }

    @Test
    public void testGetDeviceFromServiceNumNone() {
        Phone testPhone1 = new Phone("Bruh");
        Phone testPhone2 = new Phone("ASD");

        testListOfPhone.startRepair(testPhone1);
        testListOfPhone.startRepair(testPhone2);
        testListOfPhone.finishRepairDevice(testPhone2.getServiceNum());

        assertNull(testListOfPhone.getDeviceFromServiceNum(-1,true));
        assertNull(testListOfPhone.getDeviceFromServiceNum(-1,false));
    }

    @Test
    public void testGetDeviceFromServiceNumHas() {
        Phone testPhone1 = new Phone("Bruh");
        testListOfPhone.startRepair(testPhone1);

        assertEquals(testPhone1, testListOfPhone.getDeviceFromServiceNum(testPhone1.getServiceNum(), false));
        assertTrue(testListOfPhone.finishRepairDevice(testPhone1.getServiceNum()));

        assertEquals(testPhone1, testListOfPhone.getDeviceFromServiceNum(testPhone1.getServiceNum(), true));
    }

    @Test
    public void testFindDevice() {
        Phone testPhoneNot = new Phone("Bob");
        Phone testPhone1 = new Phone("Bob");
        Phone testPhone2 = new Phone("Bob");
        Phone testPhone3 = new Phone("Bob");
        Phone testPhone4 = new Phone("Bob");

        testListOfPhone.startRepair(testPhone3);
        testListOfPhone.startRepair(testPhone2);
        testListOfPhone.startRepair(testPhone1);
        testListOfPhone.startRepair(testPhone4);

        assertTrue(testListOfPhone.finishRepairDevice(testPhone4.getServiceNum()));
        assertTrue(testListOfPhone.finishRepairDevice(testPhone2.getServiceNum()));

        assertTrue(testListOfPhone.findDevice(testPhone1.getServiceNum(),false) != -1);
        assertTrue(testListOfPhone.findDevice(testPhone2.getServiceNum(),true) != -1);
        assertEquals(-1, testListOfPhone.findDevice(testPhoneNot.getServiceNum(), false));
        assertEquals(-1, testListOfPhone.findDevice(testPhoneNot.getServiceNum(), true));
    }
}
