package main.devices;

import java.util.LinkedList;
import java.util.List;

public class Desktop extends Computer {

    private static int currentServiceNum = 0; // tracks the current device num
    private static List<Integer> oldCodes = new LinkedList<>(); //list of available codes

    public Desktop (String name) {
        super(name);
        deviceCode = setDeviceCode();
    }

    //MODIFIES: this
    //EFFECTS: adds current code to list of oldCodes
    public void terminateDevice() {
        oldCodes.add(deviceCode);
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
