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

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest {
    ListOfDesktop desktops;
    ListOfLaptop laptops;
    ListOfPhone phones;

    @BeforeEach
    void setup() {
        desktops = new ListOfDesktop();
        laptops = new ListOfLaptop();
        phones = new ListOfPhone();
    }

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderNoDevices() {

        JsonReader reader = new JsonReader("./data/testReaderEmptyDeviceLists.json");
        try {
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
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralDevices() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralDevices.json");

        try {
            reader.read();
            desktops = reader.getDesktops();
            laptops = reader.getLaptops();
            phones = reader.getPhones();
            assertFalse(desktops.getRepairedDesktops().isEmpty());
            assertFalse(desktops.getWipDesktops().isEmpty());
            assertFalse(laptops.getRepairedLaptops().isEmpty());
            assertFalse(laptops.getWipLaptops().isEmpty());
            assertFalse(phones.getRepairedPhones().isEmpty());
            assertFalse(phones.getWipPhones().isEmpty());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderCurrentServiceNums() {
        try {
            JsonWriter writer = new JsonWriter("./data/testReaderCurrentServiceNums.json");
            Laptop laptopSet = new Laptop("Bob");
            Phone phoneSet = new Phone("Bob");
            Desktop desktopSet = new Desktop("Bob");

            Laptop.setCurrentServiceNum(6);
            Phone.setCurrentServiceNum(5);
            Desktop.setCurrentServiceNum(7);

            writer.open();
            writer.write(desktops, laptops, phones);
            writer.close();

            JsonReader reader = new JsonReader("./data/testReaderCurrentServiceNums.json");
            reader.read();
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
}
