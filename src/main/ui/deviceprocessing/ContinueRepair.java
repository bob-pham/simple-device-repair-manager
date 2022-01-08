package ui.deviceprocessing;

import devices.Desktop;
import devices.Laptop;
import devices.Phone;
import ui.DeviceRepairManager;
import ui.DeviceSelection;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.util.regex.Pattern.matches;

public class ContinueRepair extends DeviceSelection implements ActionListener {
    private final ProcessDesktop processDesktop;
    private final ProcessLaptop processLaptop;
    private final ProcessPhone processPhone;

    public ContinueRepair(DeviceRepairManager deviceRepair, JComponent parent) {
        super(deviceRepair, parent);

        processDesktop = new ProcessDesktop(deviceRepair, parent);
        processLaptop = new ProcessLaptop(deviceRepair, parent);
        processPhone = new ProcessPhone(deviceRepair, parent);
    }

    //MODIFIES: this
    //EFFECTS: processes desktop button
    private void contDesktop() {
        int serviceNum = enterServiceNum();
        if (serviceNum != -1) {
            Desktop desktop = deviceRepair.getDesktops().getDeviceFromServiceNum(serviceNum, false);
            if (desktop == null) {
                JOptionPane.showMessageDialog(null,"Desktop With Service Number Not Found",null,
                        JOptionPane.WARNING_MESSAGE,null);
            } else {
                deviceRepair.resetUi();
                processDesktop.initiateGraphics(desktop);
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: processes laptop button
    private void contLaptop() {
        int serviceNum = enterServiceNum();
        if (serviceNum != -1) {
            Laptop laptop = deviceRepair.getLaptops().getDeviceFromServiceNum(serviceNum, false);
            if (laptop == null) {
                JOptionPane.showMessageDialog(null,"Laptop With Service Number Not Found",null,
                        JOptionPane.WARNING_MESSAGE,null);
            } else {
                deviceRepair.resetUi();
                processLaptop.initiateGraphics(laptop);
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: processes laptop button
    private void contPhone() {
        int serviceNum = enterServiceNum();
        if (serviceNum != -1) {
            Phone phone = deviceRepair.getPhones().getDeviceFromServiceNum(serviceNum, false);
            if (phone == null) {
                JOptionPane.showMessageDialog(null,"Phone With Service Number Not Found",null,
                        JOptionPane.WARNING_MESSAGE,null);
            } else {
                deviceRepair.resetUi();
                processPhone.initiateGraphics(phone);
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: opens up menu to enter device serviceNum
    private int enterServiceNum() {
        String input = JOptionPane.showInputDialog("Please Enter Device Service Number");

        if (input == null) {
            return -1;
        } else if (input.isEmpty() || !(isInputInt(input))) {
            JOptionPane.showMessageDialog(null,"Please Enter A Valid Service Number",null,
                    JOptionPane.WARNING_MESSAGE,null);
            return enterServiceNum();
        } else {
            return Integer.parseInt(input);
        }
    }

    //EFFECT: returns true if all characters in string are numbers, false otherwise
    private boolean isInputInt(String command) {
        return matches("^[0-9]+", command);
    }

    //MODIFIES: this
    //EFFECTS: processes action events from buttons
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Desktop":
                contDesktop();
                break;
            case "Laptop":
                contLaptop();
                break;
            case "Phone":
                contPhone();
                break;
            case "exit":
                deviceRepair.resetUi();
                deviceRepair.launchDefaultUi();
                break;
        }
    }
}
