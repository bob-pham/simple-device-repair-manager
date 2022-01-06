package devices;

import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class Phone extends RepairDevice implements MobileDevice {

    private static int currentServiceNum = 0; // tracks the current device num
    private static List<Integer> oldCodes = new LinkedList<>(); //list of available codes

    private int storage;
    private boolean battery; // true if the device has a functional battery
    private int screenCondition; // 0 if screen works, 1 if black screen, 2 if broken screen

    // EFFECTS: Constructs new repair device assigning name to userName, sets all parameters as default besides code.
    public Phone(String name) {
        super(name);
        deviceCode = setDeviceCode();
        storage = DEFAULT_START_VAL;
        battery = false;
        screenCondition = 0;
    }

    @Override
    public int getTotalStorage() {
        return storage;
    }

    @Override
    public int getScreenCondition() {
        return screenCondition;
    }

    @Override
    public boolean hasBattery() {
        return battery;
    }

    @Override
    public void setBattery(boolean battery) {
        this.battery = battery;
    }

    @Override
    public void setScreenCondition(int condition) {
        screenCondition = condition;
    }

    public void setStorage(int size) {
        this.storage = size;
    }

    /*
    REQUIRES: currentServiceNum > oldCode.get(x), currentServiceNum is greater than any code in oldCode
    MODIFIES: currentServiceNum, oldCode
    EFFECTS: returns an int +1 than currentServiceNum and updates currentServiceNum +1, if there is an unused previously
    generated code it will prioritize using that.
     */
    private int setDeviceCode() {
        if (oldCodes.isEmpty()) {
            currentServiceNum++;
            return currentServiceNum;
        } else {
            int code = oldCodes.get(0);
            oldCodes.remove(0);
            return code;
        }
    }

    //MODIFIES: this
    //EFFECTS: adds current code to list of oldCodes
    public void terminateDevice() {
        oldCodes.add(deviceCode);
    }

    //EFFECTS: Creates JSONObject representing Phone
    @Override
    public JSONObject toJson() {
        super.toJson();
        jsonObject.put("bat", battery);
        jsonObject.put("screen", screenCondition);
        jsonObject.put("storage", storage);

        return jsonObject;
    }

    public static int getCurrentServiceNum() {
        return currentServiceNum;
    }

    public static List<Integer> getOldCodes() {
        return oldCodes;
    }

    public static void setCurrentServiceNum(int num) {
        currentServiceNum = num;
    }

    public static void setOldCodes(List<Integer> nums) {
        oldCodes = nums;
    }


}
