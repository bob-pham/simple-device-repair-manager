package ui.viewdevices;

import devices.Desktop;
import devices.*;
import model.ListOfComputer;
import model.ListOfDevice;
import ui.DeviceRepairManager;
import ui.Menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import static java.util.regex.Pattern.matches;

public class ViewDevices extends Menu implements ActionListener {
    private JPanel buttons;
    private JScrollPane deviceTable;

    public ViewDevices(DeviceRepairManager deviceRepair, JComponent parent) {
        super(deviceRepair, parent);
    }

    //MODIFIES: this, parent
    //EFFECT: launches graphics
    @Override
    public void launchGraphics() {
        buttons = new JPanel(new GridLayout(0, 1));

        createButtons(new JButton("Repaired Desktops"));
        createButtons(new JButton("WIP Desktops"));
        createButtons(new JButton("Repaired Laptops"));
        createButtons(new JButton("WIP Laptops"));
        createButtons(new JButton("Repaired Phones"));
        createButtons(new JButton("WIP Phones"));
        createButtons(new JButton("Search Devices"));
        createButtons(new JButton("Delete Device"));
        createButtons(new JButton("Exit"));

        deviceRepair.add(buttons, BorderLayout.SOUTH);
    }

    //MODIFIES: this
    //EFFECT: initializes buttons and adds action listener and sets action command
    private void createButtons(JButton button) {
        button.setActionCommand(button.getText());
        button.addActionListener(this);
        button.setForeground(Color.white);
        button.setBackground(DeviceRepairManager.BUTTON_COLOR);

        buttons.add(button);
    }

    //MODIFIES: this
    //EFFECTS: takes a list of desktops and returns a DesktopTable made from list
    private DesktopTable makeDesktopTable(java.util.List<devices.Desktop> desktops) {
        DesktopTable desktopTable = new DesktopTable();
        Object[][] desktopList = new Object[desktops.size()][desktopTable.getColumnCount()];

        for (int i = 0; i < desktops.size(); i++) {
            Desktop d = desktops.get(i);

            desktopList[i][0] = String.valueOf(d.getServiceNum());
            desktopList[i][1] = d.getUserName();
            desktopList[i][2] = repairStatus(d);
            desktopList[i][3] = d.getBrand();
            desktopList[i][4] = String.valueOf(d.doesPowerOn());
            desktopList[i][5] = d.getTotalStorage() + "(" + d.getHardDrive() + " + " + d.getSolidState() + ") GB";
            desktopList[i][6] = String.valueOf(d.getHardDriveCaddy());
            desktopList[i][7] = String.valueOf(d.getRam());
            desktopList[i][8] = d.getCpu();
            desktopList[i][9] = d.getGpu();
            desktopList[i][10] = d.getOS();
            desktopList[i][11] = d.getOtherNotes();

        }
        desktopTable.setData(desktopList);
        return desktopTable;
    }

    //MODIFIES: this
    //EFFECTS: takes a list of desktops and returns a DesktopTable made from list
    private LaptopTable makeLaptopTable(java.util.List<Laptop> laptops) {
        LaptopTable laptopTable = new LaptopTable();
        Object[][] laptopList = new Object[laptops.size()][laptopTable.getColumnCount()];

        for (int i = 0; i < laptops.size(); i++) {
            Laptop l = laptops.get(i);

            laptopList[i][0] = String.valueOf(l.getServiceNum());
            laptopList[i][1] = l.getUserName();
            laptopList[i][2] = repairStatus(l);
            laptopList[i][3] = l.getBrand();
            laptopList[i][4] = String.valueOf(l.doesPowerOn());
            laptopList[i][5] = l.getTotalStorage() + "(" + l.getHardDrive() + " + " + l.getSolidState() + ") GB";
            laptopList[i][6] = String.valueOf(l.hasBattery());
            laptopList[i][7] = screenCondString(l);
            laptopList[i][8] = String.valueOf(l.getHardDriveCaddy());
            laptopList[i][9] = String.valueOf(l.getRam());
            laptopList[i][10] = l.getCpu();
            laptopList[i][11] = l.getGpu();
            laptopList[i][12] = l.getOS();
            laptopList[i][13] = l.getOtherNotes();

        }
        laptopTable.setData(laptopList);
        return laptopTable;
    }

    //MODIFIES: this
    //EFFECTS: takes a list of desktops and returns a DesktopTable made from list
    private PhoneTable makePhoneTable(List<Phone> phones) {
        PhoneTable phoneTable = new PhoneTable();
        Object[][] phoneList = new Object[phones.size()][phoneTable.getColumnCount()];

        for (int i = 0; i < phones.size(); i++) {
            Phone l = phones.get(i);

            phoneList[i][0] = String.valueOf(l.getServiceNum());
            phoneList[i][1] = l.getUserName();
            phoneList[i][2] = repairStatus(l);
            phoneList[i][3] = l.getBrand();
            phoneList[i][4] = String.valueOf(l.doesPowerOn());
            phoneList[i][5] = l.getTotalStorage();
            phoneList[i][6] = String.valueOf(l.hasBattery());
            phoneList[i][7] = screenCondString(l);
            phoneList[i][8] = l.getOtherNotes();

        }
        phoneTable.setData(phoneList);
        return phoneTable;
    }

    //MODIFIES: this
    //EFFECT: asks user to select between which kind of device to delete, then attempts to delete it.
    private void deleteTree() {
        int input = selectDeviceType();

        switch (input) {
            case 0:
                deleteRepairedDevice(deviceRepair.getDesktops(), "Repaired Desktop");
                break;
            case 1:
                deleteWipDevice(deviceRepair.getDesktops(), "WIP Desktop");
                break;
            case 2:
                deleteRepairedDevice(deviceRepair.getLaptops(), "Repaired Laptop");
                break;
            case 3:
                deleteWipDevice(deviceRepair.getLaptops(), "WIP Laptop");
                break;
            case 4:
                deleteRepairedDevice(deviceRepair.getPhones(), "Repaired Phones");
                break;
            case 5:
                deleteWipDevice(deviceRepair.getPhones(), "WIP Phones");
                break;
        }
    }

    //MODIFIES: deviceList, this
    //EFFECT: prompts user to enter a device service number, attempts to delete device with that service number
    private void deleteRepairedDevice(ListOfDevice deviceList, String deviceName) {
        String input = JOptionPane.showInputDialog("Please Enter " + deviceName + " Service Num");

        if (input != null) {
            if (isInputInt(input)) {
                if (deviceList.removeRepairedDevice(Integer.parseInt(input))) {
                    JOptionPane.showMessageDialog(null, deviceName + " Deleted", null,
                            JOptionPane.INFORMATION_MESSAGE, null);
                } else {
                    JOptionPane.showMessageDialog(null, "Unable To Find " + deviceName, null,
                            JOptionPane.ERROR_MESSAGE, null);
                    deleteTree();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Invalid Service Num", null,
                        JOptionPane.ERROR_MESSAGE, null);
                deleteTree();
            }
        }
        deviceTable.repaint();
    }

    //MODIFIES: deviceList, this
    //EFFECT: prompts user to enter a device service number, attempts to delete device with that service number
    private void deleteWipDevice(ListOfDevice deviceList, String deviceName) {
        String input = JOptionPane.showInputDialog("Please Enter " + deviceName + " Service Num");

        if (input != null) {
            if (isInputInt(input)) {
                if (deviceList.removeWipDevice(Integer.parseInt(input))) {
                    JOptionPane.showMessageDialog(null, deviceName + " Deleted", null,
                            JOptionPane.INFORMATION_MESSAGE, null);
                } else {
                    JOptionPane.showMessageDialog(null, "Unable To Find " + deviceName, null,
                            JOptionPane.ERROR_MESSAGE, null);
                    deleteTree();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Invalid Service Num", null,
                        JOptionPane.ERROR_MESSAGE, null);
                deleteTree();
            }
        }
    }

    //EFFECT: prompts user to select which kind of device they want to search, then outputs the Service Numbers devices
    private void searchDevice() {
        int input = selectDeviceType();

        switch (input) {
            case 0:
                searchComputer(deviceRepair.getDesktops(), deviceRepair.getDesktops(), true);
                break;
            case 1:
                searchComputer(deviceRepair.getDesktops(), deviceRepair.getDesktops(), false);
                break;
            case 2:
                searchComputer(deviceRepair.getLaptops(), deviceRepair.getLaptops(), true);
                break;
            case 3:
                searchComputer(deviceRepair.getLaptops(), deviceRepair.getLaptops(), false);
                break;
            case 4:
                searchDeviceBrand(deviceRepair.getPhones(), true);
                break;
            case 5:
                searchDeviceBrand(deviceRepair.getPhones(), false);
                break;
        }
    }

    //EFFECT: prompts user to enter device brand, then outputs the Service Numbers devices
    private void searchDeviceBrand(ListOfDevice device, boolean repaired) {
        StringBuilder output = null;

        ArrayList<Integer> serviceNums = device
                .serviceNumsIsBrand(JOptionPane.showInputDialog("Please Enter Device Brand"), repaired);

        if (serviceNums.size() > 0) {
            for (int i = 0; i < serviceNums.size(); i++) {
                if (i == 0) {
                    output = new StringBuilder(String.valueOf(serviceNums.get(i)));
                } else {
                    output.append(", ").append(serviceNums.get(i));
                }
            }
            JOptionPane.showMessageDialog(null, "Service Numbers Found:" + "\n" + output);
        } else {
            JOptionPane.showMessageDialog(null, "No Devices Found With Brand ");
        }
    }

    //EFFECT: prompts user to select what part of computer to search, then outputs the Service Numbers devices
    private void searchComputer(ListOfComputer computers, ListOfDevice device, boolean repaired) {
        String[] options = new String[]{"Brand", "CPU", "GPU", "Cancel"};

        int input = JOptionPane.showOptionDialog(null, "Search Between:", null,
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, 4);

        switch (input) {
            case 0:
                searchDeviceBrand(device, repaired);
                break;
            case 1:
                searchComputerCPU(computers, repaired);
                break;
            case 2:
                searchComputerGPU(computers, repaired);
                break;
        }

    }

    //EFFECT: prompts user to enter in a cpu brand, then outputs service numbers of devices
    private void searchComputerCPU(ListOfComputer computer, boolean repaired) {
        StringBuilder output = null;

        ArrayList<Integer> serviceNums = computer
                .serviceNumsIsCpu(JOptionPane.showInputDialog("Please Enter Device CPU"), repaired);

        if (serviceNums.size() > 0) {
            for (int i = 0; i < serviceNums.size(); i++) {
                if (i == 0) {
                    output = new StringBuilder(String.valueOf(serviceNums.get(i)));
                } else {
                    output.append(", ").append(serviceNums.get(i));
                }
            }
            JOptionPane.showMessageDialog(null, "Service Numbers Found:" + "\n" + output);
        } else {
            JOptionPane.showMessageDialog(null, "No Devices Found With CPU ");
        }
    }

    //EFFECT: prompts user to enter in a gpu brand, then outputs service numbers of devices
    private void searchComputerGPU(ListOfComputer computer, boolean repaired) {
        StringBuilder output = null;

        ArrayList<Integer> serviceNums = computer
                .serviceNumsIsGpu(JOptionPane.showInputDialog("Please Enter Device GPU"), repaired);

        if (serviceNums.size() > 0) {
            for (int i = 0; i < serviceNums.size(); i++) {
                if (i == 0) {
                    output = new StringBuilder(String.valueOf(serviceNums.get(i)));
                } else {
                    output.append(", ").append(serviceNums.get(i));
                }
            }
            JOptionPane.showMessageDialog(null, "Service Numbers Found:" + "\n" + output);
        } else {
            JOptionPane.showMessageDialog(null, "No Devices Found With GPU ");
        }
    }

    //EFFECT: prompts user to choose between type of device, then an int corresponding to that device type
    private int selectDeviceType() {
        String[] options = new String[]{"Repaired Desktop", "WIP Desktop", "Repaired Laptop", "WIP Laptop",
                "Repaired Phone", "WIP Phone", "Cancel"};

        return JOptionPane.showOptionDialog(null,
                "Please Select Device Type", null, JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, options, 6);
    }

    //EFFECTS: returns a string percent based on repair progress of device
    private String repairStatus(RepairDevice device) {
        switch (device.checkRepairProgress()) {
            case 0:
                return "0%";
            case 1:
                return "25%";
            case 2:
                return "50%";
            case 3:
                return "75%";
            case 4:
                return "100%";
            default:
                return "N/A";
        }
    }

    //EFFECTS: returns a string that indicates screen condition
    private String screenCondString(MobileDevice device) {
        switch (device.getScreenCondition()) {
            case 0:
                return "Working";
            case 1:
                return "Black Screen";
            case 2:
                return "Broken Screen";
            default:
                return "N/A";
        }
    }

    //MODIFIES: this
    //EFFECT: processes action events from buttons
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Repaired Desktops":
                deviceTable = new JScrollPane(new JTable(makeDesktopTable(deviceRepair.getDesktops()
                        .getRepairedDesktops())));
                parentSetUp();
                break;
            case "WIP Desktops":
                deviceTable = new JScrollPane(new JTable(makeDesktopTable(deviceRepair.getDesktops().getWipDesktops())));
                parentSetUp();
                break;
            case "Repaired Laptops":
                deviceTable = new JScrollPane(new JTable(makeLaptopTable(deviceRepair.getLaptops().getRepairedLaptops())));
                parentSetUp();
                break;
            case "WIP Laptops":
                JTable table = new JTable(makeLaptopTable(deviceRepair.getLaptops().getWipLaptops()));
                deviceTable = new JScrollPane(table);
                parentSetUp();
                break;
            case "Repaired Phones":
                deviceTable = new JScrollPane(new JTable(makePhoneTable(deviceRepair.getPhones().getRepairedPhones())));
                parentSetUp();
                break;
            case "WIP Phones":
                deviceTable = new JScrollPane(new JTable(makePhoneTable(deviceRepair.getPhones().getWipPhones())));
                parentSetUp();
                break;
            case "Delete":
                deleteTree();
                break;
            case "Search":
                searchDevice();
                break;
            case "Exit":
                deviceRepair.remove(buttons);
                deviceRepair.resetUi();
                deviceRepair.launchDefaultUi();
                break;
        }
    }

    //MODIFIES: this, parent
    //EFFECT: sets parent up to show graphics
    private void parentSetUp() {
        deviceRepair.resetUi();
        deviceTable.getViewport().setBackground(DeviceRepairManager.BACKGROUND_COLOR);
        parent.setLayout(new BorderLayout());
        deviceTable.setBackground(DeviceRepairManager.BACKGROUND_COLOR);
        parent.add(deviceTable, BorderLayout.CENTER);
        parent.setBackground(DeviceRepairManager.BACKGROUND_COLOR);
        parent.revalidate();
        parent.repaint();
    }

    //EFFECT: returns true if all characters in string are numbers, false otherwise
    private boolean isInputInt(String command) {
        return matches("^[0-9]+", command);
    }
}
