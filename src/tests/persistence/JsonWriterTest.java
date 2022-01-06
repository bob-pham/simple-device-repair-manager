package persistence;

import devices.Desktop;
import devices.Laptop;
import devices.Phone;
import model.ListOfDesktop;
import model.ListOfLaptop;
import model.ListOfPhone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest {

    private ListOfDesktop desktops;
    private ListOfLaptop laptops;
    private ListOfPhone phones;
    JsonWriter writer;
    JsonReader reader;

    @BeforeEach
    void setup() {
        desktops = new ListOfDesktop();
        laptops = new ListOfLaptop();
        phones = new ListOfPhone();
    }

    @Test
    void testWriterInvalidFile() {
        try {
            writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterNoChange() {
        try {
            writer = new JsonWriter("./data/testReaderEmptyDeviceLists.json");
            reader = new JsonReader("./data/testReaderEmptyDeviceLists.json");

            writer.open();
            writer.write(desktops, laptops, phones);
            writer.close();

            reader.read();
            desktops = reader.getDesktops();
            laptops = reader.getLaptops();
            phones = reader.getPhones();

            assertTrue(desktops.getRepairedDesktops().isEmpty());
            assertTrue(desktops.getWipDesktops().isEmpty());
            assertTrue(laptops.getRepairedLaptops().isEmpty());
            assertTrue(laptops.getWipLaptops().isEmpty());
            assertTrue(phones.getRepairedPhones().isEmpty());
            assertTrue(phones.getWipPhones().isEmpty());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterWipChange() {
        try {
            writer = new JsonWriter("./data/testReaderSomeWipDeviceLists.json");

            Laptop laptop1 = new Laptop("Bob");
            laptops.startRepair(laptop1);

            Desktop desktop1 = new Desktop("Bob");
            desktops.startRepair(desktop1);

            Phone phone1 = new Phone("Bob");
            phones.startRepair(phone1);

            writer.open();
            writer.write(desktops, laptops, phones);
            writer.close();

            reader = new JsonReader("./data/testReaderSomeWipDeviceLists.json");

            reader.read();
            desktops = reader.getDesktops();
            laptops = reader.getLaptops();
            phones = reader.getPhones();


            assertTrue(desktops.getRepairedDesktops().isEmpty());
            assertEquals(1, desktops.getWipDesktops().size());
            assertTrue(laptops.getRepairedLaptops().isEmpty());
            assertEquals(1, laptops.getWipLaptops().size());
            assertTrue(phones.getRepairedPhones().isEmpty());
            assertEquals(1, phones.getWipPhones().size());

        } catch (IOException e) {
            fail("Exception thrown");
        }
    }

    @Test
    void testWriterRepairChange() {
        try {
            writer = new JsonWriter("./data/testReaderSomeRepairedDeviceLists.json");

            Laptop laptop1 = new Laptop("Bob");
            laptops.startRepair(laptop1);
            laptops.finishRepairDevice(laptop1.getServiceNum());

            Desktop desktop1 = new Desktop("Bob");
            desktops.startRepair(desktop1);
            desktops.finishRepairDevice(desktop1.getServiceNum());

            Phone phone1 = new Phone("Bob");
            phones.startRepair(phone1);
            phones.finishRepairDevice(phone1.getServiceNum());

            writer.open();
            writer.write(desktops, laptops, phones);
            writer.close();

            reader = new JsonReader("./data/testReaderSomeRepairedDeviceLists.json");
            reader.read();
            desktops = reader.getDesktops();
            laptops = reader.getLaptops();
            phones = reader.getPhones();

            assertEquals(1, desktops.getRepairedDesktops().size());
            assertTrue(desktops.getWipDesktops().isEmpty());
            assertEquals(1, laptops.getRepairedLaptops().size());
            assertTrue(laptops.getWipLaptops().isEmpty());
            assertEquals(1, phones.getRepairedPhones().size());
            assertTrue(phones.getWipPhones().isEmpty());

        } catch (IOException e) {
            fail("Exception thrown");
        }
    }

    @Test
    void testWriterGeneralDevices() {
        try {
            writer = new JsonWriter("./data/testReaderGeneralDevices.json");
            reader = new JsonReader("./data/testReaderGeneralDevices.json");

            Laptop laptop1 = new Laptop("Bob");
            Laptop laptop2 = new Laptop("Bob");
            laptops.startRepair(laptop1);
            laptops.startRepair(laptop2);
            laptops.finishRepairDevice(laptop2.getServiceNum());

            Desktop desktop1 = new Desktop("Bob");
            Desktop desktop2 = new Desktop("Bob");
            desktops.startRepair(desktop1);
            desktops.startRepair(desktop2);
            desktops.finishRepairDevice(desktop2.getServiceNum());

            Phone phone1 = new Phone("Bob");
            Phone phone2 = new Phone("Bob");
            phones.startRepair(phone1);
            phones.startRepair(phone2);
            phones.finishRepairDevice(phone2.getServiceNum());

            writer.open();
            writer.write(desktops, laptops, phones);
            writer.close();

            reader.read();
            desktops = reader.getDesktops();
            laptops = reader.getLaptops();
            phones = reader.getPhones();

            assertEquals(1, desktops.getRepairedDesktops().size());
            assertEquals(1, laptops.getRepairedLaptops().size());
            assertEquals(2, phones.getRepairedPhones().size());
            assertEquals(1, desktops.getWipDesktops().size());
            assertEquals(1, laptops.getWipLaptops().size());
            assertEquals(2, phones.getWipPhones().size());

        } catch (IOException e) {
            fail("Exception thrown");
        }
    }

    @Test
    void testWriterCurrentServiceNums() {
        try {
            writer = new JsonWriter("./data/testReaderCurrentServiceNums.json");
            reader = new JsonReader("./data/testReaderCurrentServiceNums.json");

            Laptop laptopSet = new Laptop("Bob");
            Phone phoneSet = new Phone("Bob");
            Desktop desktopSet = new Desktop("Bob");

            Laptop.setCurrentServiceNum(6);
            Phone.setCurrentServiceNum(5);
            Desktop.setCurrentServiceNum(7);

            writer.open();
            writer.write(desktops, laptops, phones);
            writer.close();

            reader.read();
            desktops = reader.getDesktops();
            laptops = reader.getLaptops();
            phones = reader.getPhones();

            Laptop laptop = new Laptop("Bob");
            Desktop desktop = new Desktop("Bob");
            Phone phone = new Phone("Bob");

            assertEquals(6, phone.getServiceNum());
            assertEquals(7, laptop.getServiceNum());
            assertEquals(8, desktop.getServiceNum());
        } catch (IOException e) {
            fail("Exception thrown");
        }
    }

    @Test
    void testWriterOldCodes() {
        try {
            writer = new JsonWriter("./data/testReaderOldCodes.json");
            reader = new JsonReader("./data/testReaderOldCodes.json");

            Laptop laptopSet = new Laptop("Bob");
            Phone phoneSet = new Phone("Bob");
            Desktop desktopSet = new Desktop("Bob");

            desktops.startRepair(desktopSet);
            laptops.startRepair(laptopSet);
            phones.startRepair(phoneSet);

            Laptop.setCurrentServiceNum(6);
            Phone.setCurrentServiceNum(2);
            Desktop.setCurrentServiceNum(4);

            Laptop laptopDel1 = new Laptop("Bob");
            Laptop laptopDel2 = new Laptop("Bob");
            laptopDel1.terminateDevice();
            laptopDel2.terminateDevice();

            Desktop desktopDel1 = new Desktop("Bob");
            Desktop desktopDel2 = new Desktop("Bob");
            desktopDel1.terminateDevice();
            desktopDel2.terminateDevice();

            Phone phoneDel1 = new Phone("Bob");
            Phone phoneDel2 = new Phone("Bob");
            phoneDel1.terminateDevice();
            phoneDel2.terminateDevice();

            writer.open();
            writer.write(desktops, laptops, phones);
            writer.close();

            Laptop.setCurrentServiceNum(0);
            Phone.setCurrentServiceNum(0);
            Desktop.setCurrentServiceNum(0);

            Laptop.setOldCodes(new ArrayList<>());
            Phone.setOldCodes(new ArrayList<>());
            Desktop.setOldCodes(new ArrayList<>());

            reader.read();

            desktops = reader.getDesktops();
            laptops = reader.getLaptops();
            phones = reader.getPhones();

            Laptop laptop1 = new Laptop("Bob");
            Laptop laptop2 = new Laptop("Bob");
            Laptop laptop3 = new Laptop("Bob");
            Desktop desktop1 = new Desktop("Bob");
            Desktop desktop2 = new Desktop("Bob");
            Desktop desktop3 = new Desktop("Bob");
            Phone phone1 = new Phone("Bob");
            Phone phone2 = new Phone("Bob");
            Phone phone3 = new Phone("Bob");

            assertEquals(3, phone1.getServiceNum());
            assertEquals(4, phone2.getServiceNum());
            assertEquals(5, phone3.getServiceNum());
            assertEquals(7, laptop1.getServiceNum());
            assertEquals(8, laptop2.getServiceNum());
            assertEquals(9, laptop3.getServiceNum());
            assertEquals(5, desktop1.getServiceNum());
            assertEquals(6, desktop2.getServiceNum());
            assertEquals(7, desktop3.getServiceNum());

        } catch (IOException e) {
            fail("Exception thrown");
        }
    }
}
