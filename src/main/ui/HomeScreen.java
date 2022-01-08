package ui;

import persistence.JsonWriter;
import ui.deviceprocessing.ContinueRepair;
import ui.deviceprocessing.StartNewRepair;
import ui.viewdevices.ViewDevices;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class HomeScreen extends Menu implements ActionListener {
    private final Font defaultFont;
    private JButton startNewRepair;
    private JButton contRepair;
    private JButton viewDevices;
    private JButton exit;
    private final StartNewRepair newRepair;
    private final ContinueRepair continueRepair;
    private final ViewDevices deviceViewer;
    private JsonWriter jsonWriter;

    //EFFECT: constructor for HomeScreen
    public HomeScreen(DeviceRepairManager deviceRepair, JComponent parent) {
        super(deviceRepair, parent);
        newRepair = new StartNewRepair(deviceRepair, parent);
        continueRepair = new ContinueRepair(deviceRepair, parent);
        deviceViewer = new ViewDevices(deviceRepair, parent);
        defaultFont = new Font("Century Gothic", Font.BOLD, 15);
        jsonWriter = new JsonWriter(DeviceRepairManager.JSON_STORE);
        initiateButtons();
    }

    //MODIFIES: this
    //EFFECTS: constructs and initializes all buttons
    private void initiateButtons() {
        startNewRepair = new JButton("Start New Repair");
        contRepair = new JButton("Continue Repair");
        viewDevices = new JButton("View Devices");
        exit = new JButton("Exit");

        constructButtons(startNewRepair);
        constructButtons(contRepair);
        constructButtons(viewDevices);
        constructButtons(exit);
    }

    private void constructButtons(JButton button) {
        button.setFont(defaultFont);
        button.setActionCommand(button.getText());
        button.addActionListener(this);
        button.setForeground(Color.white);
        button.setBackground(DeviceRepairManager.BUTTON_COLOR);
    }

    //MODIFIES: this, parent
    //EFFECT: launches graphics for homescreen and initializes the menu
    @Override
    public void launchGraphics() {
        JLabel welcome = new JLabel("Welcome " + deviceRepair.getUserName());
        JPanel panel = new JPanel(new GridLayout(0, 1, 100, 50));

        try {
            BufferedImage myPic = ImageIO.read(new File("data/devArtHome.png"));
            Image image = myPic.getScaledInstance(400, 150, Image.SCALE_DEFAULT);
            JLabel picture = new JLabel(new ImageIcon(image));
            picture.setBounds(150, 25, 400, 150);
            parent.add(picture);
        } catch (IOException e) {
            e.printStackTrace();
        }

        welcome.setFont(defaultFont);
        welcome.setForeground(Color.white);
        welcome.setBounds(10, 0, 500, 50);
        parent.add(welcome);

        panel.setBackground(DeviceRepairManager.BACKGROUND_COLOR);
        panel.add(startNewRepair);
        panel.add(contRepair);
        panel.add(viewDevices);
        panel.add(exit);

        panel.setBounds(200, 200, 300, 400);

        parent.add(panel);
        initializeMenu();
    }

    //MODIFIES: this, menu
    //EFFECTS: initializes menuOptions
    private void initializeMenu() {
        createMenuItem(new JMenuItem("Start New Repair"), this);
        createMenuItem(new JMenuItem("Continue Repair"), this);
        createMenuItem(new JMenuItem("View Devices"), this);
        createMenuItem(new JMenuItem("Load Last Instance"), deviceRepair);
        createMenuItem(new JMenuItem("Load From Existing Source"), deviceRepair);
        createMenuItem(new JMenuItem("Save"), this);
        createMenuItem(new JMenuItem("Save To Another Location"), this);
        createMenuItem(new JMenuItem("Logout"), this);
        createMenuItem(new JMenuItem("Exit"), this);
    }

    //EFFECT: creates menu items
    private void createMenuItem(JMenuItem item, ActionListener listener) {
        item.setActionCommand(item.getText());
        item.addActionListener(listener);
        deviceRepair.addToTopMenu(item);
    }

    //MODIFIES: this, JsonWriter
    // EFFECTS: saves devices to file
    private void saveDevices() {
        try {
            jsonWriter.open();
            jsonWriter.write(deviceRepair.getDesktops(), deviceRepair.getLaptops(), deviceRepair.getPhones());
            jsonWriter.close();
            JOptionPane.showMessageDialog(null, "Saved devices to "
                    + DeviceRepairManager.JSON_STORE, null, JOptionPane.INFORMATION_MESSAGE, null);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Unable to save devices to "
                    + DeviceRepairManager.JSON_STORE, null, JOptionPane.ERROR_MESSAGE, null);
        }
    }

    //MODIFIES: this, JsonWriter
    //EFFECTS: saves devices to file
    private void saveNewDevice() {
        String input = JOptionPane.showInputDialog(null, "Save Location:",
                DeviceRepairManager.JSON_STORE);
        JsonWriter newJsonWriter = new JsonWriter(input);
        if (input != null && input.length() > 0) {
            try {
                newJsonWriter.open();
                newJsonWriter.write(deviceRepair.getDesktops(), deviceRepair.getLaptops(), deviceRepair.getPhones());
                newJsonWriter.close();
                JOptionPane.showMessageDialog(null, "Saved devices to "
                        + input, null, JOptionPane.INFORMATION_MESSAGE, null);
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, "Unable to save devices to "
                        + input, null, JOptionPane.ERROR_MESSAGE, null);
            }
        }
    }

    //MODIFIES: this, JsonWriter, deviceRepair
    //EFFECTS: asks user if they want to saves, if yes asks if they want to save to default or to file, then saves
    //         returns false if user cancels, returns true otherwise.
    private boolean likeToSave() {
        int input = JOptionPane.showOptionDialog(null, "Would You Like To Save?", null,
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, 2);
        if (input == 0) {
            String[] option = new String[]{"Save To Default", "Save To New File", "Cancel"};
            int input2 = JOptionPane.showOptionDialog(null, "Save To Default or New File?", null,
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, option, 2);
            if (input2 == 0) {
                saveDevices();
            } else if (input2 == 1) {
                saveNewDevice();
            } else {
                return false;
            }
            return true;
        } else {
            return input == 1;
        }
    }

    //MODIFIES: this
    //EFFECT: processes action commands
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Start New Repair":
                deviceRepair.resetUi();
                newRepair.launchGraphics();
                break;
            case "Continue Repair":
                deviceRepair.resetUi();
                continueRepair.launchGraphics();
                break;
            case "View Devices":
                deviceRepair.resetUi();
                deviceViewer.launchGraphics();
                break;
            case "Save":
                saveDevices();
                break;
            case "Save To Another Location":
                saveNewDevice();
                break;
            case "Logout":
                if (likeToSave()) {
                    deviceRepair.resetUi();
                    deviceRepair.startMenu();
                }
                break;
            case "Exit":
                if (likeToSave()) {
                    deviceRepair.dispose();
                }
                break;
        }
    }
}
