package main.model;

import main.devices.Desktop;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListOfDesktop implements ListOfComputer, ListOfDevice {


    private final List<Desktop> wipDesktops; // List of desktops that are Work in progress
    private final List<Desktop> repairedDesktops; // List of repaired desktops

    //EFFECT LostOfDesktop Constructor
    public ListOfDesktop() {
        wipDesktops = new ArrayList<>();
        repairedDesktops = new ArrayList<>();
    }

    // EFFECTS: adds desktop to the wipDesktops list
    public void startRepair(Desktop desktop) {
        wipDesktops.add(desktop);
    }

    @Override
    // MODIFIES: this
    // EFFECTS: adds desktop with serviceCode to repairedDesktops and removes it from wipDesktops and returns true,
    //          if there isn't a desktop with serviceCode returns false
    public boolean finishRepairDevice(int serviceCode) {
        int position = findDevice(serviceCode, false);

        if (position > -1) {
            repairedDesktops.add(wipDesktops.get(position));
            wipDesktops.remove(position);
            return true;
        } else {
            return false;
        }
    }

    @Override
    // MODIFIES: this
    // EFFECTS: terminates and removes desktop with serviceCode from repairedDesktops and returns true, returns false if
    //          there is no desktop with serviceCode in repairedDesktops
    public boolean removeRepairedDevice(int serviceCode) {
        int position = findDevice(serviceCode, true);

        if (position > -1) {
            repairedDesktops.get(position).terminateDevice();
            repairedDesktops.remove(position);
            return true;
        } else {
            return false;
        }
    }

    @Override
    // MODIFIES: this
    // EFFECTS: terminates and removes desktop with serviceCode from wipDesktops and returns true, returns false if
    //          there is no desktop with serviceCode in wipDesktops
    public boolean removeWipDevice(int serviceCode) {
        int position = findDevice(serviceCode, false);

        if (position > -1) {
            wipDesktops.get(position).terminateDevice();
            wipDesktops.remove(position);
            return true;
        } else {
            return false;
        }
    }

    @Override
    // EFFECTS: if repaired is true, returns position of desktop in repairedDesktops, returns -1 otherwise.
    //          if repaired false returns position of desktop in wipDesktops, returns -1 otherwise.
    public int findDevice(int serviceNum, boolean repaired) {

        int position = -1;

        if (repaired) {
            for (int i = 0; i < repairedDesktops.size(); i++) {
                if (repairedDesktops.get(i).getServiceNum() == serviceNum) {
                    position = i;
                    break;
                }
            }
        } else {
            for (int i = 0; i < wipDesktops.size(); i++) {
                if (wipDesktops.get(i).getServiceNum() == serviceNum) {
                    position = i;
                    break;
                }
            }
        }
        return position;
    }

    // REQUIRES: if repaired is true, position < repairedDesktops.size(), else position < wipDesktops.size()
    // EFFECTS: if repaired is true, returns desktop at position in repairedDesktops
    //          if repaired false returns desktop at position in wipDesktops
    public Desktop getDesktop(int position, boolean repaired) {
        if (repaired) {
            return repairedDesktops.get(position);
        } else {
            return wipDesktops.get(position);
        }
    }

    //EFFECTS: if repaired is true, returns desktop with that serviceNum from repairedDesktops,
    //         if false returns desktop from wipDesktops, if not found returns null.
    public Desktop getDeviceFromServiceNum(int serviceNum, boolean repaired) {
        if (repaired) {
            for (Desktop d : repairedDesktops) {
                if (d.getServiceNum() == serviceNum) {
                    return d;
                }
            }
        } else {
            for (Desktop d : wipDesktops) {
                if (d.getServiceNum() == serviceNum) {
                    return d;
                }
            }
        }
        return null;
    }

    @Override
    //EFFECTS: returns a list of service numbers of desktops that have that brand name
    public ArrayList<Integer> serviceNumsIsBrand(String name, boolean repaired) {
        ArrayList<Integer> serviceNums = new ArrayList<>();

        if (repaired) {
            for (Desktop repairedDesktop : repairedDesktops) {
                if (repairedDesktop.getBrand().equals(name)) {
                    serviceNums.add(repairedDesktop.getServiceNum());
                }
            }
        } else {
            for (Desktop wipDesktop : wipDesktops) {
                if (wipDesktop.getBrand().equals(name)) {
                    serviceNums.add(wipDesktop.getServiceNum());
                }
            }
        }
        return serviceNums;
    }

    //EFFECTS: returns a list of service numbers of desktops that have that CPU
    @Override
    public ArrayList<Integer> serviceNumsIsCpu(String name, boolean repaired) {

        ArrayList<Integer> serviceNums = new ArrayList<>();

        if (repaired) {
            for (Desktop repairedDesktop : repairedDesktops) {
                if (repairedDesktop.getCpu().equals(name)) {
                    serviceNums.add(repairedDesktop.getServiceNum());
                }
            }
        } else {
            for (Desktop wipDesktop : wipDesktops) {
                if (wipDesktop.getCpu().equals(name)) {
                    serviceNums.add(wipDesktop.getServiceNum());
                }
            }
        }
        return serviceNums;
    }

    //EFFECTS: returns a list of service numbers of desktops that have that GPU
    @Override
    public ArrayList<Integer> serviceNumsIsGpu(String name, boolean repaired) {

        ArrayList<Integer> serviceNums = new ArrayList<>();

        if (repaired) {
            for (Desktop repairedDesktop : repairedDesktops) {
                if (repairedDesktop.getGpu().equals(name)) {
                    serviceNums.add(repairedDesktop.getServiceNum());
                }
            }
        } else {
            for (Desktop wipDesktop : wipDesktops) {
                if (wipDesktop.getGpu().equals(name)) {
                    serviceNums.add(wipDesktop.getServiceNum());
                }
            }
        }
        return serviceNums;
    }

    public List<Desktop> getRepairedDesktops() {
        return repairedDesktops;
    }

    public List<Desktop> getWipDesktops() {
        return wipDesktops;
    }

    //EFFECTS: creates JSONObject that is populated with all WIP and Repaired Desktops
    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("wip Desktops", desktopsToJson('w'));
        jsonObject.put("repaired Desktops", desktopsToJson('r'));

        return jsonObject;
    }

    //EFFECTS: creates JSONArray with JSONObjects representing each wip or repaired desktop
    private JSONArray desktopsToJson(char status) {
        JSONArray jsonArray = new JSONArray();

        if (status == 'w') {
            for (Desktop d : wipDesktops) {
                jsonArray.put(d.toJson());
            }
        } else {
            for (Desktop d : repairedDesktops) {
                jsonArray.put(d.toJson());
            }
        }
        return jsonArray;
    }

    //MODIFIES: this
    //EFFECTS: adds desktop to repaired desktops
    public void addRepairedDesktop(Desktop desktop) {
        repairedDesktops.add(desktop);
    }
}
