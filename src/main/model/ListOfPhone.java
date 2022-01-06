package model;

import devices.Phone;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListOfPhone implements ListOfDevice {

    private static List<Phone> wipPhones; // represent list of phones that are work in progress
    private static List<Phone> repairedPhones; //represents a list of phones that are repaired

    //EFFECT: constructor for ListOfPhone
    public ListOfPhone() {
        wipPhones = new ArrayList<>();
        repairedPhones = new ArrayList<>();
    }

    // EFFECTS: adds Phone to the wipPhone list
    public void startRepair(Phone phone) {
        wipPhones.add(phone);
    }

    // MODIFIES: this
    // EFFECTS: adds Phone with serviceCode to repairedPhone and removes it from wipPhone and returns true,
    //          if there isn't a Phone with serviceCode returns false
    @Override
    public boolean finishRepairDevice(int serviceCode) {
        int position = findDevice(serviceCode, false);

        if (position > -1) {
            repairedPhones.add(wipPhones.get(position));
            wipPhones.remove(position);
            return true;
        } else {
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: terminates and removes Phone with serviceCode from repairedPhones and returns true, returns false if
    //          there is no Phone with serviceCode in repairedPhones
    @Override
    public boolean removeRepairedDevice(int serviceCode) {
        int position = findDevice(serviceCode, true);

        if (position > -1) {
            repairedPhones.get(position).terminateDevice();
            repairedPhones.remove(position);
            return true;
        } else {
            return false;
        }
    }

    @Override
    // MODIFIES: this
    // EFFECTS: terminates and removes laptop with serviceCode from wipPhones and returns true, returns false if
    //          there is no phone with serviceCode in wipPhones
    public boolean removeWipDevice(int serviceCode) {
        int position = findDevice(serviceCode, false);

        if (position > -1) {
            wipPhones.get(position).terminateDevice();
            wipPhones.remove(position);
            return true;
        } else {
            return false;
        }
    }

    // EFFECTS: if repaired is true, returns position of Phone in repairedPhone, returns -1 otherwise.
    //          if repaired false returns position of Phone in wipPhone, returns -1 otherwise.
    @Override
    public int findDevice(int serviceNum, boolean repaired) {

        int position = -1;

        if (repaired) {
            for (int i = 0; i < repairedPhones.size(); i++) {
                if (repairedPhones.get(i).getServiceNum() == serviceNum) {
                    position = i;
                    break;
                }
            }
        } else {
            for (int i = 0; i < wipPhones.size(); i++) {
                if (wipPhones.get(i).getServiceNum() == serviceNum) {
                    position = i;
                    break;
                }
            }
        }
        return position;
    }

    // REQUIRES: if repaired is true, position < repairedPhone.size(), else position < wipPhone.size()
    // EFFECTS: if repaired is true, returns Phone at position in repairedPhone
    //          if repaired false returns Phone at position in wipPhones
    public Phone getPhone(int position, boolean repaired) {
        if (repaired) {
            return repairedPhones.get(position);
        } else {
            return wipPhones.get(position);
        }
    }

    //EFFECTS: if repaired is true, returns laptop with that serviceNum from repairedLaptops,
    //         if false returns laptop from wipLaptops, if not found returns null.
    public Phone getDeviceFromServiceNum(int serviceNum, boolean repaired) {
        if (repaired) {
            for (Phone p : repairedPhones) {
                if (p.getServiceNum() == serviceNum) {
                    return p;
                }
            }
        } else {
            for (Phone p : wipPhones) {
                if (p.getServiceNum() == serviceNum) {
                    return p;
                }
            }
        }
        return null;
    }

    // EFFECTS: returns a list of service numbers of phones of that brand name
    @Override
    public ArrayList<Integer> serviceNumsIsBrand(String name, boolean repaired) {

        ArrayList<Integer> serviceNums = new ArrayList<>();

        if (repaired) {
            for (Phone repairedPhone : repairedPhones) {
                if (repairedPhone.getBrand().equals(name)) {
                    serviceNums.add(repairedPhone.getServiceNum());
                }
            }
        } else {
            for (Phone wipPhone : wipPhones) {
                if (wipPhone.getBrand().equals(name)) {
                    serviceNums.add(wipPhone.getServiceNum());
                }
            }
        }
        return serviceNums;
    }

    public List<Phone> getRepairedPhones() {
        return repairedPhones;
    }

    public List<Phone> getWipPhones() {
        return wipPhones;
    }

    //EFFECTS: creates JSONObject that is populated with all WIP and Repaired Phones
    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("wip Phones", phonesToJson('w'));
        jsonObject.put("repaired Phones", phonesToJson('r'));

        return jsonObject;
    }

    //EFFECTS: creates JSONArray with JSONObjects representing each wip or repaired phone
    private JSONArray phonesToJson(char status) {
        JSONArray jsonArray = new JSONArray();

        if (status == 'w') {
            for (Phone p: wipPhones) {
                jsonArray.put(p.toJson());
            }
        } else {
            for (Phone p : repairedPhones) {
                jsonArray.put(p.toJson());
            }
        }
        return jsonArray;
    }

    //MODIFIES: this
    //EFFECTS: adds desktop to repaired phones
    public void addRepairedPhone(Phone p) {
        repairedPhones.add(p);
    }
}
