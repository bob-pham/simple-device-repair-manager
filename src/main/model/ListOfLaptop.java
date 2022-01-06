package model;

import devices.Laptop;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListOfLaptop implements ListOfDevice, ListOfComputer {

    private final List<Laptop> wipLaptops; // represents laptops that are work in progress
    private final List<Laptop> repairedLaptops; // represents laptops that are repaired

    public ListOfLaptop() {
        wipLaptops = new ArrayList<>();
        repairedLaptops = new ArrayList<>();
    }

    // EFFECTS: adds laptop to the wipLaptops list
    public void startRepair(Laptop laptop) {
        wipLaptops.add(laptop);
    }

    @Override
    // MODIFIES: this
    // EFFECTS: adds laptop with serviceCode to repairedLaptops and removes it from wipLaptops and returns true,
    //          if there isn't a laptop with serviceCode returns false
    public boolean finishRepairDevice(int serviceCode) {
        int position = findDevice(serviceCode, false);

        if (position > -1) {
            repairedLaptops.add(wipLaptops.get(position));
            wipLaptops.remove(position);
            return true;
        } else {
            return false;
        }
    }

    @Override
    // MODIFIES: this
    // EFFECTS: terminates and removes laptop with serviceCode from repairedLaptops and returns true, returns false if
    //          there is no laptop with serviceCode in repairedLaptops
    public boolean removeRepairedDevice(int serviceCode) {
        int position = findDevice(serviceCode, true);

        if (position > -1) {
            repairedLaptops.get(position).terminateDevice();
            repairedLaptops.remove(position);
            return true;
        } else {
            return false;
        }
    }

    @Override
    // MODIFIES: this
    // EFFECTS: terminates and removes laptop with serviceCode from wipLaptops and returns true, returns false if
    //          there is no laptop with serviceCode in wipLaptops
    public boolean removeWipDevice(int serviceCode) {
        int position = findDevice(serviceCode, false);

        if (position > -1) {
            wipLaptops.get(position).terminateDevice();
            wipLaptops.remove(position);
            return true;
        } else {
            return false;
        }
    }

    @Override
    // EFFECTS: if repaired is true, returns position of laptop in repairedLaptops, returns -1 otherwise.
    //          if repaired false returns position of laptop in wipLaptops, returns -1 otherwise.
    public int findDevice(int serviceNum, boolean repaired) {

        int position = -1;

        if (repaired) {
            for (int i = 0; i < repairedLaptops.size(); i++) {
                if (repairedLaptops.get(i).getServiceNum() == serviceNum) {
                    position = i;
                    break;
                }
            }
        } else {
            for (int i = 0; i < wipLaptops.size(); i++) {
                if (wipLaptops.get(i).getServiceNum() == serviceNum) {
                    position = i;
                    break;
                }
            }
        }
        return position;
    }

    // REQUIRES: if repaired is true, position < repairedLaptops.size(), else position < wipLaptops.size()
    // EFFECTS: if repaired is true, returns laptop at position in repairedLaptops
    //          if repaired false returns laptop at position in wipLaptops
    public Laptop getLaptop(int position, boolean repaired) {
        if (repaired) {
            return repairedLaptops.get(position);
        } else {
            return wipLaptops.get(position);
        }
    }

    //EFFECTS: if repaired is true, returns laptop with that serviceNum from repairedLaptops,
    //         if false returns laptop from wipLaptops, if not found returns null.
    public Laptop getDeviceFromServiceNum(int serviceNum, boolean repaired) {
        if (repaired) {
            for (Laptop l : repairedLaptops) {
                if (l.getServiceNum() == serviceNum) {
                    return l;
                }
            }
        } else {
            for (Laptop l : wipLaptops) {
                if (l.getServiceNum() == serviceNum) {
                    return l;
                }
            }
        }
        return null;
    }

    @Override
    //EFFECTS: returns a list of service numbers of laptops that have that brand name
    public ArrayList<Integer> serviceNumsIsBrand(String name, boolean repaired) {

        ArrayList<Integer> serviceNums = new ArrayList<>();

        if (repaired) {
            for (Laptop repairedLaptop : repairedLaptops) {
                if (repairedLaptop.getBrand().equals(name)) {
                    serviceNums.add(repairedLaptop.getServiceNum());
                }
            }
        } else {
            for (Laptop wipLaptop : wipLaptops) {
                if (wipLaptop.getBrand().equals(name)) {
                    serviceNums.add(wipLaptop.getServiceNum());
                }
            }
        }
        return serviceNums;
    }

    @Override
    //EFFECTS: returns a list of service numbers of laptops that have that CPU
    public ArrayList<Integer> serviceNumsIsCpu(String name, boolean repaired) {

        ArrayList<Integer> serviceNums = new ArrayList<>();

        if (repaired) {
            for (Laptop repairedLaptop : repairedLaptops) {
                if (repairedLaptop.getCpu().equals(name)) {
                    serviceNums.add(repairedLaptop.getServiceNum());
                }
            }
        } else {
            for (Laptop wipLaptop : wipLaptops) {
                if (wipLaptop.getCpu().equals(name)) {
                    serviceNums.add(wipLaptop.getServiceNum());
                }
            }
        }
        return serviceNums;
    }

    @Override
    //EFFECTS: returns a list of service numbers of laptops that have that GPU
    public ArrayList<Integer> serviceNumsIsGpu(String name, boolean repaired) {

        ArrayList<Integer> serviceNums = new ArrayList<>();

        if (repaired) {
            for (Laptop repairedLaptop : repairedLaptops) {
                if (repairedLaptop.getGpu().equals(name)) {
                    serviceNums.add(repairedLaptop.getServiceNum());
                }
            }
        } else {
            for (Laptop wipLaptop : wipLaptops) {
                if (wipLaptop.getGpu().equals(name)) {
                    serviceNums.add(wipLaptop.getServiceNum());
                }
            }
        }
        return serviceNums;
    }

    public List<Laptop> getRepairedLaptops() {
        return repairedLaptops;
    }

    public List<Laptop> getWipLaptops() {
        return wipLaptops;
    }

    //EFFECTS: creates JSONObject that is populated with all WIP and Repaired Laptops
    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("wip Laptops", laptopsToJson('w'));
        jsonObject.put("repaired Laptops", laptopsToJson('r'));

        return jsonObject;
    }

    //EFFECTS: creates JSONArray with JSONObjects representing each wip or repaired Laptop
    private JSONArray laptopsToJson(char status) {
        JSONArray jsonArray = new JSONArray();

        if (status == 'w') {
            for (Laptop l : wipLaptops) {
                jsonArray.put(l.toJson());
            }
        } else {
            for (Laptop l : repairedLaptops) {
                jsonArray.put(l.toJson());
            }
        }
        return jsonArray;
    }

    //MODIFIES: this
    //EFFECTS: adds desktop to repaired desktops
    public void addRepairedLaptop(Laptop l) {
        repairedLaptops.add(l);
    }


}
