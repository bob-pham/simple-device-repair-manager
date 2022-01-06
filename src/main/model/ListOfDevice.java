package main.model;

import org.json.JSONObject;

import java.util.ArrayList;

public interface ListOfDevice {

    //EFFECT: if device is in wip, moves it to repaired and returns true, else returns false
    boolean finishRepairDevice(int serviceCode);

    //EFFECT: removes repaired device from repaired devices
    boolean removeRepairedDevice(int serviceCode);

    //EFFECT: removes wip device from WipDevice
    boolean removeWipDevice(int serviceCode);

    //EFFECT: if true returns the position of device with service num in repaired device
    // if false returns position of device in wip device, returns -5 if device is not found
    int findDevice(int serviceNum, boolean repaired);

    //EFFECT: returns an ArrayList of service numbers of devices with brand.
    ArrayList<Integer> serviceNumsIsBrand(String name, boolean repaired);

    //EFFECT: creates a JSON object that stores all wip and repaired devices
    JSONObject toJson();
}
