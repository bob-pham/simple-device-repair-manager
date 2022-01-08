package ui.deviceprocessing;

import devices.Phone;
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

public class ProcessPhone extends ProcessDevice implements ActionListener {
    private Phone phone;
    private static final Dimension BUTTON_DIMENSION = new Dimension(75,30);
    private JButton setScreen;
    private JButton setBat;
    private JButton setStorage;
    private JLabel phonePic;

    public ProcessPhone(DeviceRepairManager deviceRepair, JComponent parent) {
        super(deviceRepair, parent);
        buttonsPanel = new JPanel(new GridLayout(0,3,30,30));
        buttonsPanel.setBackground(DeviceRepairManager.BACKGROUND_COLOR);
        constructButtons();
        try {
            BufferedImage pic = ImageIO.read(new File("data/devArtPhone.png"));
            phonePic = new JLabel(new ImageIcon(pic));
        } catch (IOException io) {
            // do nothing
        }
    }

    //MODIFIES: this
    //EFFECTS: constructs the buttons for the phone processing
    private void constructButtons() {
        setScreen = new JButton("Screen Condition");
        setScreen.setActionCommand("Screen Condition");
        setScreen.addActionListener(this);

        setBat = new JButton("Set Battery");
        setBat.setActionCommand("Set Battery");
        setBat.addActionListener(this);

        setStorage = new JButton("Set Storage");
        setStorage.setActionCommand("Set Storage");
        setStorage.addActionListener(this);

        buttons.add(setScreen);
        buttons.add(setBat);
        buttons.add(setStorage);

        initiateButtons();
    }

    //MODIFIES: this
    //EFFECTS: initiates the buttons, makes them all size BUTTON_DIMENSION and then adds them to button panel
    private void initiateButtons() {
        for (JButton button : buttons) {
            button.setMinimumSize(BUTTON_DIMENSION);
            button.setPreferredSize(BUTTON_DIMENSION);
            button.setMaximumSize(BUTTON_DIMENSION);
            button.setForeground(Color.white);
            button.setBackground(DeviceRepairManager.BUTTON_COLOR);
        }

        buttonsPanel.add(repProg);
        buttonsPanel.add(brand);
        buttonsPanel.add(powerStat);
        buttonsPanel.add(setScreen);
        buttonsPanel.add(setBat);
        buttonsPanel.add(setStorage);
        buttonsPanel.add(notes);
        buttonsPanel.add(exit);
    }

    //MODIFIES: this, parent
    //EFFECTS: launches phone processing ui
    public void initiateGraphics(Phone phone) {
        this.phone = phone;
        parent.setLayout(new BorderLayout());
        parent.add(buttonsPanel, BorderLayout.PAGE_END);
        parent.add(phonePic, BorderLayout.PAGE_START);
    }

    //MODIFIES: this, phones
    //EFFECTS: finishes phone repair and exits
    @Override
    protected void processDeviceRepair(RepairDevice device) {
        device.setRepairProgress(4);
        deviceRepair.getPhones().finishRepairDevice(device.getServiceNum());
        exit();
    }

    //MODIFIES: this, phone
    //EFFECTS: sets phone battery
    public void setBat() {
        int battery = JOptionPane.showOptionDialog(null, "Does the Device have a Battery?",
                null, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null,
                null);

        JOptionPane.showMessageDialog(null, "Device Battery Status Set To: " + (battery == 0));

        phone.setBattery(battery == 0);
        if (battery == 0) {
            setBat.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        } else {
            setBat.setBorder(BorderFactory.createLineBorder(Color.RED));
        }
    }

    //MODIFIES: this, phone
    //EFFECTS: sets phone screen condition
    public void setScreen() {
        String[] options = new String[]{"Working", "Broken", "Black"};
        int screen = JOptionPane.showOptionDialog(null, "Set Screen Condition",
                null, JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, 0);

        if (screen == 1 || screen == 2) {
            phone.setScreenCondition(screen);
            setScreen.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
        } else {
            phone.setScreenCondition(0);
            setScreen.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        }
    }

    //MODIFIES: this, phone
    //EFFECTS: sets Phone battery size
    private void setStorage() {
        String input = JOptionPane.showInputDialog("Please Enter Device Storage Size (GB)");

        if (input == null || input.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No Storage Size set", null,
                    JOptionPane.INFORMATION_MESSAGE);
            phone.setStorage(0);
        } else if (!(isInputInt(input))) {
            JOptionPane.showMessageDialog(null, "Storage size can only be inputted as an integer,"
                    + " please try again", null, JOptionPane.WARNING_MESSAGE);
            setStorage();
        } else {
            JOptionPane.showMessageDialog(null, "Storage size set to " + input + " GB", null,
                    JOptionPane.INFORMATION_MESSAGE);
            phone.setStorage(Integer.parseInt(input));
            setStorage.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        }
    }

    //MODIFIES: this
    //EFFECT: exits processing ui
    @Override
    public void exit() {
        phone = null;
        super.exit();
    }

    //MODIFIES: this
    //EFFECTS: processes action events from buttons
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Repair Progress":
                setDeviceRepairProgress(phone);
                break;
            case "Power":
                setDevicePower(phone);
                break;
            case "Brand":
                setDeviceBrand(phone);
                break;
            case "Notes":
                setDeviceNotes(phone);
                break;
            case "Screen Condition":
                setScreen();
                break;
            case "Set Battery":
                setBat();
                break;
            case "Set Storage":
                setStorage();
                break;
            case "Exit":
                exit();
                break;
        }
    }
}
