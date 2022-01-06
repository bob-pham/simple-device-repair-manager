package devices;

import org.json.JSONObject;

// Represents a device(has or hasn't been repaired) with a code, repair progress, brand, power status, notes, and user
public abstract class RepairDevice {

    protected static final int DEFAULT_START_VAL = 0;
    protected static final boolean DEFAULT_START_STATUS = false;
    protected static final String DEFAULT_BLANK = "N/A";

    protected int deviceCode; // code that is assigned to this device
    protected int repairProgress; // tracks the stages of device repair, 0 for incomplete through to 4 which is complete
    protected String brand; // the device's brand
    protected boolean power; // true if the device can power on, false if it does not
    protected String notes; // additional device notes
    protected String userName; // name of person that repaired the device
    protected JSONObject jsonObject;

    // EFFECTS: Constructs new repair device assigning name to userName, sets all parameters as default besides code.
    public RepairDevice(String name) {
        repairProgress = DEFAULT_START_VAL;
        brand = DEFAULT_BLANK;
        power = DEFAULT_START_STATUS;
        notes = DEFAULT_BLANK;
        this.userName = name;
    }

    public boolean doesPowerOn() {
        return power;
    }

    public String getBrand() {
        return brand;
    }

    public int checkRepairProgress() {
        return repairProgress;
    }

    public int getServiceNum() {
        return deviceCode;
    }

    public abstract int getTotalStorage();

    public String getOtherNotes() {
        return notes;
    }

    public void setOtherNotes(String notes) {
        this.notes = notes;
    }

    public void setRepairProgress(int rp) {
        repairProgress = rp;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setPower(boolean pwr) {
        power = pwr;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public abstract void terminateDevice();

    //EFFECTS: constructs JSONObject of RepairDevice
    public JSONObject toJson() {
        jsonObject = new JSONObject();
        jsonObject.put("code", deviceCode);
        jsonObject.put("repair progress", repairProgress);
        jsonObject.put("brand", brand);
        jsonObject.put("power", power);
        jsonObject.put("notes", notes);
        jsonObject.put("user", userName);

        return jsonObject;
    }

    // MODIFIES: this
    // EFFECTS: sets device service num
    public void setServiceNum(int num) {
        deviceCode = num;
    }


}
