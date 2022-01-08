package ui.deviceprocessing;

import devices.RepairDevice;
import ui.DeviceRepairManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import static java.util.regex.Pattern.matches;

public abstract class ProcessDevice implements ActionListener {
    protected final DeviceRepairManager deviceRepair;
    protected final JComponent parent;
    protected JButton repProg;
    protected JButton powerStat;
    protected JButton brand;
    protected JButton notes;
    protected JButton exit;
    protected JPanel buttonsPanel;
    protected final List<JButton> buttons;

    public ProcessDevice(DeviceRepairManager deviceRepair, JComponent parent) {
        this.deviceRepair = deviceRepair;
        this.parent = parent;
        buttons = new ArrayList<>();

        constructButtons();
    }

    //MODIFIES: this
    //EFFECTS: constructs buttons for repair progress, power, brand, and notes, then adds them to buttons
    private void constructButtons() {
        repProg = new JButton("Repair Progress");
        repProg.setActionCommand("Repair Progress");
        repProg.addActionListener(this);

        powerStat = new JButton("Power");
        powerStat.setActionCommand("Power");
        powerStat.addActionListener(this);

        brand = new JButton("Brand");
        brand.setActionCommand("Brand");
        brand.addActionListener(this);

        notes = new JButton("Notes");
        notes.setActionCommand("Notes");
        notes.addActionListener(this);

        exit = new JButton("Exit");
        exit.setActionCommand("Exit");
        exit.addActionListener(this);

        buttons.add(repProg);
        buttons.add(powerStat);
        buttons.add(brand);
        buttons.add(notes);
        buttons.add(exit);
    }

    // REQUIRES: user inputs an int
    // MODIFIES: this, RepairDevice
    // EFFECTS: prompts user to set repair process, throws DoneRepair when repair complete.
    protected void setDeviceRepairProgress(RepairDevice device) {
        String[] options = new String[]{"0%", "25%", "50%", "75%", "Complete"};
        int progress = JOptionPane.showOptionDialog(null, "Set Device Repair Progress",
                null, JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, 0);

        if (progress >= 1 && progress <= 3) {
            device.setRepairProgress(progress);
            repProg.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
        } else if (progress == 4) {
            if (checkDoneRepair()) {
                processDeviceRepair(device);
            }
        } else {
            device.setRepairProgress(0);
        }
        JOptionPane.showMessageDialog(null, "Device Repair Progress Set", null,
                JOptionPane.INFORMATION_MESSAGE, null);
    }

    // MODIFIES: this, RepairDevice
    // EFFECTS: processes RepairDevice repair progress input and sets it, throws DoneRepair when repair complete.
    protected abstract void processDeviceRepair(RepairDevice device);

    // MODIFIES: this
    // EFFECTS: prompt that confirms if user wants to complete the repair, returns true if yes false otherwise.
    protected boolean checkDoneRepair() {
        return JOptionPane.showOptionDialog(null, "Confirm Repair Completed? (Cannot be undone)",
                null, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                null, 1) == 0;
    }

    // MODIFIES: this, RepairDevice
    // EFFECTS: prompts user to input device brand, then sets device's brand to input.
    protected void setDeviceBrand(RepairDevice device) {
        String brand = JOptionPane.showInputDialog("Please Enter The Device Brand");

        if (brand == null || brand.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No Brand Set", null,
                    JOptionPane.INFORMATION_MESSAGE);
            device.setBrand("N/A");
        } else {
            JOptionPane.showMessageDialog(null, "Device Brand set to " + brand, null,
                    JOptionPane.INFORMATION_MESSAGE);
            device.setBrand(brand);
            this.brand.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        }
    }

    // MODIFIES: this, RepairDevice
    // EFFECTS: prompts user to select if device powers or not, then sets device to power input.
    protected void setDevicePower(RepairDevice device) {
        int powers = JOptionPane.showOptionDialog(null, "Does the device power on?", null,
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

        JOptionPane.showMessageDialog(null, "Device Power Status set to " + (powers == 0));

        device.setPower(powers == 0);

        if (powers == 0) {
            powerStat.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        } else {
            powerStat.setBorder(BorderFactory.createLineBorder(Color.RED));
        }
    }

    // MODIFIES: this, RepairDevice
    // EFFECTS: prompts user to input device notes, then sets notes to input
    protected void setDeviceNotes(RepairDevice device) {
        String notes = JOptionPane.showInputDialog("Please Enter The Device Notes");

        if (notes == null || notes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No Notes Set", null,
                    JOptionPane.INFORMATION_MESSAGE);
            device.setOtherNotes("N/A");
        } else {
            JOptionPane.showMessageDialog(null, "Device Notes Set", null,
                    JOptionPane.INFORMATION_MESSAGE);
            device.setOtherNotes(notes);
            this.notes.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        }
    }

    //EFFECT: exits out of repair screen and goes back to main menu
    protected void exit() {
        deviceRepair.resetUi();
        deviceRepair.launchDefaultUi();

        for (JButton button : buttons) {
            button.setBorder(BorderFactory.createEtchedBorder());
        }
    }

    //EFFECT: returns true if all characters in string are numbers, false otherwise
    protected boolean isInputInt(String command) {
        return matches("^[0-9]+", command);
    }
}
