package main.devices;

import org.json.JSONObject;

public abstract class Computer extends RepairDevice {

    protected int hardDrive; // harddrive size in gb, 0 if no harddrive
    protected int solidState; // solid state drive size in gb, 0 if no ssd
    protected boolean hardDriveCaddy; // true if laptop contains a hard drive caddy, false otherwise
    protected int ram; // ram size in mb, 0 if no ram
    protected String cpu;
    protected String gpu;
    protected String operatingSystem; // the name of the operating system used

    // EFFECTS: Constructs new repair device assigning name to userName, sets all parameters as default besides code.
    public Computer (String name) {
        super(name);
        hardDrive = DEFAULT_START_VAL;
        solidState = DEFAULT_START_VAL;
        hardDriveCaddy = DEFAULT_START_STATUS;
        ram = DEFAULT_START_VAL;
        operatingSystem = DEFAULT_BLANK;
        cpu = DEFAULT_BLANK;
        gpu = DEFAULT_BLANK;
    }

    public int getHardDrive() {
        return hardDrive;
    }

    public int getSolidState() {
        return solidState;
    }

    public boolean getHardDriveCaddy() {
        return hardDriveCaddy;
    }

    public int getRam() {
        return ram;
    }

    public String getOS() {
        return operatingSystem;
    }

    public String getCpu() {
        return cpu;
    }

    public String getGpu() {
        return gpu;
    }

    public void setHardDrive(int storage) {
        hardDrive = storage;
    }

    public void setSolidState(int storage) {
        solidState = storage;
    }

    public void setHardDriveCaddy(boolean hdc) {
        hardDriveCaddy = hdc;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    public void setOS(String os) {
        operatingSystem = os;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public void setGpu(String gpu) {
        this.gpu = gpu;
    }

    //EFFECTS: gets the total storage in GB of a device by summing ssd and hdd
    @Override
    public int getTotalStorage() {
        return solidState + hardDrive;
    }

    //EFFECTS: Creates a JSONObject from Desktop
    @Override
    public JSONObject toJson() {
        super.toJson();
        jsonObject.put("hdd", hardDrive);
        jsonObject.put("ssd", solidState);
        jsonObject.put("hdc", hardDriveCaddy);
        jsonObject.put("ram", ram);
        jsonObject.put("cpu", cpu);
        jsonObject.put("gpu", gpu);
        jsonObject.put("os", operatingSystem);

        return jsonObject;
    }

}
