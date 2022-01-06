package persistence;

import devices.Desktop;
import devices.Laptop;
import devices.Phone;
import model.ListOfDesktop;
import model.ListOfLaptop;
import model.ListOfPhone;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private final String destination;

    //EFFECTS: Constructs a writer to write destination file.
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of workroom to file
    public void write(ListOfDesktop desktops, ListOfLaptop laptops, ListOfPhone phones) {
        JSONObject jsonFile = new JSONObject();
        JSONObject jsonDevices = new JSONObject();

        jsonDevices.put("Desktops", desktops.toJson());
        jsonDevices.put("Laptops", laptops.toJson());
        jsonDevices.put("Phones", phones.toJson());

        jsonFile.put("Devices", jsonDevices);
        jsonFile.put("Desktop num", Desktop.getCurrentServiceNum());
        jsonFile.put("Laptop num", Laptop.getCurrentServiceNum());
        jsonFile.put("Phone num", Phone.getCurrentServiceNum());
        jsonFile.put("Desktop codes", oldCodesArray(Desktop.getOldCodes()));
        jsonFile.put("Laptop codes", oldCodesArray(Laptop.getOldCodes()));
        jsonFile.put("Phone codes", oldCodesArray(Phone.getOldCodes()));
        jsonToFile(jsonFile.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void jsonToFile(String s) {
        writer.print(s);
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: returns a JSONArray containing ints from device oldCodes
    private JSONArray oldCodesArray(List<Integer> nums) {
        JSONArray codes = new JSONArray();

        for (int num : nums) {
            codes.put(num);
        }

        return codes;
    }
}
