package ui;

import javax.swing.*;

public abstract class Menu {
    protected final DeviceRepairManager deviceRepair;
    protected final JComponent parent;

    public Menu(DeviceRepairManager deviceRepair, JComponent parent) {
        this.deviceRepair = deviceRepair;
        this.parent = parent;
    }

    //EFFECTS: launches the graphics of the menu
    protected abstract void launchGraphics();
}
