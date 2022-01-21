package ui.deviceprocessing;

import devices.Desktop;
import devices.RepairDevice;
import ui.DeviceRepairManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ProcessDesktop extends ProcessComputer implements ActionListener {
    private static final Dimension BUTTON_DIMENSION = new Dimension(150,30);
    private devices.Desktop desktop;
    private JLabel desktopPic;

    //MODIFIES: this
    //EFFECT: constructor for ProcessDesktop
    public ProcessDesktop(DeviceRepairManager deviceRep, JComponent parent) {
        super(deviceRep, parent);
        buttonsPanel = new JPanel(new GridLayout(4,3, 30,30));
        buttonsPanel.setBackground(DeviceRepairManager.BACKGROUND_COLOR);
        initiateButtons();
        try {
            BufferedImage pic = ImageIO.read(new File("data/pc_tower.png"));
            Image towerPic = pic.getScaledInstance(500,450,Image.SCALE_DEFAULT);
            desktopPic = new JLabel(new ImageIcon(towerPic));
        } catch (IOException io) {
            // do nothing
        }
    }

    //EFFECTS: initiates desktop ui
    public void initiateGraphics(Desktop desktop) {
        this.desktop = desktop;

        parent.setLayout(new BorderLayout());
        parent.setForeground(DeviceRepairManager.BACKGROUND_COLOR);
        parent.add(buttonsPanel, BorderLayout.PAGE_END);
        parent.add(desktopPic, BorderLayout.PAGE_START);
    }

    //MODIFIES: this
    //EFFECTS: initiates the buttons, makes them size BUTTON_DIMENSION and adds them to buttonPanel
    private void initiateButtons() {
        for (JButton button : buttons) {
            button.setMinimumSize(BUTTON_DIMENSION);
            button.setPreferredSize(BUTTON_DIMENSION);
            button.setMaximumSize(BUTTON_DIMENSION);
            button.setForeground(Color.white);
            button.setBackground(DeviceRepairManager.BUTTON_COLOR);
        }

        buttonsPanel.add(repProg);
        buttonsPanel.add(ram);
        buttonsPanel.add(brand);
        buttonsPanel.add(hardDriveCaddy);
        buttonsPanel.add(powerStat);
        buttonsPanel.add(operatingSystem);
        buttonsPanel.add(hardDrive);
        buttonsPanel.add(gpu);
        buttonsPanel.add(cpu);
        buttonsPanel.add(solidState);
        buttonsPanel.add(notes);
        buttonsPanel.add(exit);
        buttonsPanel.setForeground(DeviceRepairManager.BACKGROUND_COLOR);
    }

    //MODIFIES: this
    //EFFECT: finishes the laptop and moves it to repaired Desktops, exits to main menu
    @Override
    protected void processDeviceRepair(RepairDevice device) {
        device.setRepairProgress(4);
        deviceRepair.getDesktops().finishRepairDevice(device.getServiceNum());
        exit();
    }

    //MODIFIES: this
    //EFFECT: exits processing ui
    @Override
    public void exit() {
        desktop = null;
        super.exit();
    }

    //EFFECT: processes action event of buttons
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Repair Progress":
                setDeviceRepairProgress(desktop);
                break;
            case "Power":
                setDevicePower(desktop);
                break;
            case "Brand":
                setDeviceBrand(desktop);
                break;
            case "Notes":
                setDeviceNotes(desktop);
                break;
            case "Hard Drive":
                setHardDrive(desktop);
                break;
            case "Solid State":
                setSolidState(desktop);
                break;
            case "Hard Drive Caddy":
                setHardDriveCaddy(desktop);
                break;
            case "Ram":
                setRam(desktop);
                break;
            case "CPU":
                setCpu(desktop);
                break;
            case "GPU":
                setGpu(desktop);
                break;
            case "Operating System":
                setOS(desktop);
                break;
            case "Exit":
                exit();
                break;
        }
    }
}
