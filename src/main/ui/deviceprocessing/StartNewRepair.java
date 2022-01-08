package ui.deviceprocessing;

import devices.Desktop;
import devices.Laptop;
import devices.Phone;
import ui.DeviceRepairManager;
import ui.DeviceSelection;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartNewRepair extends DeviceSelection implements ActionListener {
    private final ProcessDesktop processDesktop;
    private final ProcessLaptop processLaptop;
    private final ProcessPhone processPhone;

    public StartNewRepair(DeviceRepairManager deviceRepair, JComponent parent) {
        super(deviceRepair, parent);

        processDesktop = new ProcessDesktop(deviceRepair, parent);
        processLaptop = new ProcessLaptop(deviceRepair, parent);
        processPhone = new ProcessPhone(deviceRepair, parent);
    }

    //MODIFIES: this
    //EFFECTS: processes button clicks for StartNewRepair
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Desktop":
                deviceRepair.resetUi();

                Desktop desktop = new Desktop(deviceRepair.getUserName());
                deviceRepair.getDesktops().startRepair(desktop);
                processDesktop.initiateGraphics(desktop);
                break;
            case "Laptop":
                deviceRepair.resetUi();

                Laptop laptop = new Laptop(deviceRepair.getUserName());
                deviceRepair.getLaptops().startRepair(laptop);
                processLaptop.initiateGraphics(laptop);
                break;
            case "Phone":
                deviceRepair.resetUi();

                Phone phone = new Phone(deviceRepair.getUserName());
                deviceRepair.getPhones().startRepair(phone);
                processPhone.initiateGraphics(phone);
                break;
            case "exit":
                deviceRepair.resetUi();
                deviceRepair.launchDefaultUi();
                break;
        }
    }
}
