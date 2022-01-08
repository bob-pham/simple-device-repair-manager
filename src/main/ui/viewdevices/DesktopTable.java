package ui.viewdevices;

public class DesktopTable extends DeviceTable {
    //EFFECT: Constructor for DesktopTable
    public DesktopTable() {
        header = new String[] {"Service Num", "Repaired By", "Repair Progress", "Brand", "Power",
                "Storage (Hard drive + Solid State)", "Hard Drive Caddy", "Ram", "CPU", "GPU", "OS",
                "Additional Notes"};
    }
}
