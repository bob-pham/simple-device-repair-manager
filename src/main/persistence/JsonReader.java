package persistence;

import devices.Desktop;
import devices.Laptop;
import devices.Phone;
import model.ListOfDesktop;
import model.ListOfLaptop;
import model.ListOfPhone;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class JsonReader {

    private final String source;
    private final ListOfDesktop desktops;
    private final ListOfLaptop laptops;
    private final ListOfPhone phones;

    //EFFECT: constructs a JsonReader
    public JsonReader(String source) {
        desktops = new ListOfDesktop();
        laptops = new ListOfLaptop();
        phones = new ListOfPhone();
        this.source = source;
    }

    //EFFECT: returns the constructed ListOfDesktop
    public ListOfDesktop getDesktops() {
        return desktops;
    }

    //EFFECT: returns the constructed ListOfLaptop
    public ListOfLaptop getLaptops() {
        return laptops;
    }

    //EFFECT: return the constructed ListOfPhone
    public ListOfPhone getPhones() {
        return phones;
    }

    //MODIFIES: this, ListOfDesktop, ListOfLaptop, ListOfPhone
    //EFFECT: populates ListOfDesktop, ListOfLaptop, ListOfPhone from source JSON file
    public void read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject  = new JSONObject(jsonData);
        JSONObject jsonDevices = jsonObject.getJSONObject("Devices");
        List<Integer> codes;

        parseDesktopLists(jsonDevices.getJSONObject("Desktops"));
        codes = getOldCodes(jsonObject.getJSONArray("Desktop codes"));
        desktopCurrentNum(jsonObject.getInt("Desktop num"), codes);

        parseLaptopLists(jsonDevices.getJSONObject("Laptops"));
        codes = getOldCodes(jsonObject.getJSONArray("Laptop codes"));
        laptopCurrentNum(jsonObject.getInt("Laptop num"), codes);

        parsePhoneLists(jsonDevices.getJSONObject("Phones"));
        codes = getOldCodes(jsonObject.getJSONArray("Phone codes"));
        phoneCurrentNum(jsonObject.getInt("Phone num"), codes);

    }

    //EFFECT: reads source fle and returns it.
    // modeled from JsonSerializationDemo https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    //EFFECTS: parses an LinkedList of int that represent oldCodes from source and returns it
    private List<Integer> getOldCodes(JSONArray oldCodes) {
        List<Integer> codes = new LinkedList<>();

        for (Object num : oldCodes) {
            codes.add((Integer) num);
        }
        return codes;
    }

    //MODIFIES: this, ListOfDesktop
    //EFFECTS: parses wip and repaired desktops from JSONObject and adds it to appropriate list.
    private void parseDesktopLists(JSONObject jsonDesktop) {
        JSONArray repairedDesktops = jsonDesktop.getJSONArray("repaired Desktops");
        JSONArray wipDesktops = jsonDesktop.getJSONArray("wip Desktops");

        if (!(repairedDesktops.isEmpty())) {
            for (Object json : repairedDesktops) {
                JSONObject nextRepairedDesktop = (JSONObject) json;
                desktops.addRepairedDesktop(constructDesktop(nextRepairedDesktop));
            }
        }

        if (!(wipDesktops.isEmpty())) {
            for (Object json : wipDesktops) {
                JSONObject nextWipDesktop = (JSONObject) json;
                desktops.startRepair(constructDesktop(nextWipDesktop));
            }
        }
    }

    // MODIFIES: this, Desktop
    // EFFECTS: constructs a desktop from JSON object
    private Desktop constructDesktop(JSONObject desktop) {
        Desktop newDesktop = new Desktop(desktop.getString("user"));

        newDesktop.setServiceNum(desktop.getInt("code"));
        newDesktop.setRepairProgress(desktop.getInt("repair progress"));
        newDesktop.setBrand(desktop.getString("brand"));
        newDesktop.setPower(desktop.getBoolean("power"));
        newDesktop.setOtherNotes(desktop.getString("notes"));
        newDesktop.setHardDrive(desktop.getInt("hdd"));
        newDesktop.setSolidState(desktop.getInt("ssd"));
        newDesktop.setHardDriveCaddy(desktop.getBoolean("hdc"));
        newDesktop.setRam(desktop.getInt("ram"));
        newDesktop.setCpu(desktop.getString("cpu"));
        newDesktop.setGpu(desktop.getString("gpu"));
        newDesktop.setOS(desktop.getString("os"));

        return newDesktop;
    }

    //MODIFIES: this, Desktop
    //EFFECT: sets Desktop CurrentServiceNum and OldCodes
    private void desktopCurrentNum(int currentNum, List<Integer> codes) {
        Desktop.setCurrentServiceNum(currentNum);
        Desktop.setOldCodes(codes);
    }

    //MODIFIES: this, ListOfLaptop
    //EFFECT: parses wip and repaired laptops from JSONObject, and populates the appropriate list.
    private void parseLaptopLists(JSONObject jsonObject) {
        JSONArray repairedLaptops = jsonObject.getJSONArray("repaired Laptops");
        JSONArray wipLaptops = jsonObject.getJSONArray("wip Laptops");

        for (Object json : repairedLaptops) {
            JSONObject nextRepairedLaptop = (JSONObject) json;
            laptops.addRepairedLaptop(constructLaptop(nextRepairedLaptop));
        }

        for (Object json: wipLaptops) {
            JSONObject nextWipLaptop = (JSONObject) json;
            laptops.startRepair(constructLaptop(nextWipLaptop));
        }
    }

    // MODIFIES: this, Laptop
    // EFFECTS: constructs a Laptop from JSON object
    private Laptop constructLaptop(JSONObject laptop) {
        Laptop newLaptop = new Laptop(laptop.getString("user"));

        newLaptop.setServiceNum(laptop.getInt("code"));
        newLaptop.setRepairProgress(laptop.getInt("repair progress"));
        newLaptop.setBrand(laptop.getString("brand"));
        newLaptop.setPower(laptop.getBoolean("power"));
        newLaptop.setOtherNotes(laptop.getString("notes"));
        newLaptop.setHardDrive(laptop.getInt("hdd"));
        newLaptop.setSolidState(laptop.getInt("ssd"));
        newLaptop.setHardDriveCaddy(laptop.getBoolean("hdc"));
        newLaptop.setRam(laptop.getInt("ram"));
        newLaptop.setCpu(laptop.getString("cpu"));
        newLaptop.setGpu(laptop.getString("gpu"));
        newLaptop.setOS(laptop.getString("os"));
        newLaptop.setScreenCondition(laptop.getInt("screen"));
        newLaptop.setBattery(laptop.getBoolean("bat"));

        return newLaptop;
    }

    //MODIFIES: this, Laptop
    //EFFECTS: sets Laptop CurrentServiceNum and OldCodes
    private void laptopCurrentNum(int currentNum, List<Integer> codes) {
        Laptop.setCurrentServiceNum(currentNum);
        Laptop.setOldCodes(codes);
    }

    //MODIFIES: this, ListOfPhone
    //EFFECT: parses wip and repaired phones from JSONObject and populates the appropriate list.
    private void parsePhoneLists(JSONObject jsonObject) {
        JSONArray repairedPhones = jsonObject.getJSONArray("repaired Phones");
        JSONArray wipPhones = jsonObject.getJSONArray("wip Phones");

        for (Object json : repairedPhones) {
            JSONObject nextRepairedPhone = (JSONObject) json;
            phones.addRepairedPhone(constructPhone(nextRepairedPhone));
        }

        for (Object json: wipPhones) {
            JSONObject nextWipPhone = (JSONObject) json;
            phones.startRepair(constructPhone(nextWipPhone));
        }
    }

    // MODIFIES: this, Phone
    // EFFECTS: constructs a Phone from JSON object
    private Phone constructPhone(JSONObject phone) {
        Phone newPhone = new Phone(phone.getString("user"));

        newPhone.setServiceNum(phone.getInt("code"));
        newPhone.setRepairProgress(phone.getInt("repair progress"));
        newPhone.setBrand(phone.getString("brand"));
        newPhone.setPower(phone.getBoolean("power"));
        newPhone.setOtherNotes(phone.getString("notes"));
        newPhone.setScreenCondition(phone.getInt("screen"));
        newPhone.setBattery(phone.getBoolean("bat"));
        newPhone.setStorage(phone.getInt("storage"));

        return newPhone;
    }

    //MODIFIES: this, Phone
    //EFFECT: sets Phone CurrentServiceNum and oldCodes.
    private void phoneCurrentNum(int currentNum, List<Integer> codes) {
        Phone.setCurrentServiceNum(currentNum);
        Phone.setOldCodes(codes);
    }
}
