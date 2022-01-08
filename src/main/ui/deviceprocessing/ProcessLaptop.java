package ui.deviceprocessing;

import devices.Laptop;
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

public class ProcessLaptop extends ProcessComputer implements ActionListener {
    private static final Dimension BUTTON_DIMENSION = new Dimension(75, 30);
    private Laptop laptop;
    private JButton setScreen;
    private JButton setBat;
    private JLabel laptopPic;

    public ProcessLaptop(DeviceRepairManager deviceRep, JComponent parent) {
        super(deviceRep, parent);
        buttonsPanel = new JPanel(new GridLayout(0, 3, 30, 30));
        buttonsPanel.setBackground(DeviceRepairManager.BACKGROUND_COLOR);
        constructButtons();
        try {
            BufferedImage pic = ImageIO.read(new File("data/devArtLaptop.png"));
            laptopPic = new JLabel(new ImageIcon(pic));
        } catch (IOException io) {
            // do nothing
        }
    }

    //MODIFIES: this
    //EFFECTS: constructs buttons for screen status and battery
    private void constructButtons() {
        setScreen = new JButton("Screen Condition");
        setScreen.setActionCommand("Screen Condition");
        setScreen.addActionListener(this);

        setBat = new JButton("Set Battery");
        setBat.setActionCommand("Set Battery");
        setBat.addActionListener(this);

        buttons.add(setScreen);
        buttons.add(setBat);
        initiateButtons();
    }

    //MODIFIES: this, parent
    //EFFECTS: initiates graphics for laptop processing ui
    public void initiateGraphics(Laptop laptop) {
        this.laptop = laptop;
        parent.setLayout(new BorderLayout());
        parent.add(buttonsPanel, BorderLayout.PAGE_END);
        parent.add(laptopPic, BorderLayout.PAGE_START);
    }

    //MODIFIES: this
    //EFFECTS: adds buttons to buttonPanel
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
        buttonsPanel.add(setBat);
        buttonsPanel.add(setScreen);
        buttonsPanel.add(exit);

    }

    //EFFECT: adds laptop to repaired laptops and exits
    @Override
    protected void processDeviceRepair(RepairDevice device) {
        device.setRepairProgress(4);
        deviceRepair.getLaptops().finishRepairDevice(device.getServiceNum());
        exit();
    }

    //MODIFIES: this, laptop
    //EFFECTS: sets laptop battery
    private void setBat(Laptop laptop) {
        int battery = JOptionPane.showOptionDialog(null, "Does the Device have a Battery?",
                null, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null,
                null);

        JOptionPane.showMessageDialog(null, "Device Battery Status Set To: " + (battery == 0));

        laptop.setBattery(battery == 0);

        if (battery == 0) {
            setBat.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        } else {
            setBat.setBorder(BorderFactory.createLineBorder(Color.RED));
        }
    }

    //MODIFIES: this, laptop
    //EFFECTS: sets laptop screen condition
    private void setScreen(Laptop laptop) {
        String[] options = new String[]{"Working", "Broken", "Black"};
        int screen = JOptionPane.showOptionDialog(null, "Set Screen Condition",
                null, JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, 0);

        if (screen == 1 || screen == 2) {
            laptop.setScreenCondition(screen);
            setScreen.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
        } else {
            laptop.setScreenCondition(0);
            setScreen.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        }
    }

    //MODIFIES: this
    //EFFECT: exits processing ui
    @Override
    public void exit() {
        laptop = null;
        super.exit();
    }

    //MODIFIES: this
    //EFFECTS: processes button clicks for laptop
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Repair Progress":
                setDeviceRepairProgress(laptop);
                break;
            case "Power":
                setDevicePower(laptop);
                break;
            case "Brand":
                setDeviceBrand(laptop);
                break;
            case "Notes":
                setDeviceNotes(laptop);
                break;
            case "Hard Drive":
                setHardDrive(laptop);
                break;
            case "Solid State":
                setSolidState(laptop);
                break;
            case "Hard Drive Caddy":
                setHardDriveCaddy(laptop);
                break;
            case "Ram":
                setRam(laptop);
                break;
            case "CPU":
                setCpu(laptop);
                break;
            case "GPU":
                setGpu(laptop);
                break;
            case "Operating System":
                setOS(laptop);
                break;
            case "Screen Condition":
                setScreen(laptop);
                break;
            case "Set Battery":
                setBat(laptop);
                break;
            case "Exit":
                exit();
                break;
        }
    }
}
