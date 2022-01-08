package ui;

import model.ListOfDesktop;
import model.ListOfLaptop;
import model.ListOfPhone;
import persistence.JsonReader;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DeviceRepairManager extends JFrame implements ActionListener {

    public static final String JSON_STORE = "./data/devicerepair.json";
    public static final Color BACKGROUND_COLOR = new Color(43,43,43);
    public static final Color BUTTON_COLOR = new Color(56,56,56);
    private ListOfDesktop desktops;
    private ListOfLaptop laptops;
    private ListOfPhone phones;
    private JPanel menuPanel;
    private JMenuBar defaultMenu;
    private JMenu menu;
    private JTextField textField;
    private final HomeScreen homeScreen;
    private String userName;
    private boolean loadedFile;

    //EFFECTS: constructor for DeviceRepairManagerGUI
    public DeviceRepairManager() {
        super("Device Repair Manager");
        initializeGUI();
        homeScreen = new HomeScreen(this, menuPanel);
        loadedFile = false;
    }

    public static void main(String[] args) {
        new DeviceRepairManager();
    }

    //EFFECTS: Loads GUI for DeviceRepairManager
    private void initializeGUI() {
        menuPanel = new JPanel();
        setLayout(new BorderLayout());
        setSize(700, 700);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        menuPanel.setBackground(BACKGROUND_COLOR);
        add(menuPanel);

        defaultMenu = new JMenuBar();
        setJMenuBar(defaultMenu);

        menu = new JMenu("Options");
        defaultMenu.add(menu);

        addDefaultBarMenu();

        startMenu();
        setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: adds options to menu
    private void addDefaultBarMenu() {

        JMenuItem loadLast = new JMenuItem("Load Last Instance");
        JMenuItem loadNew = new JMenuItem("Load From Existing Source");

        loadLast.setActionCommand("Load Last Instance");
        loadLast.addActionListener(this);

        loadNew.setActionCommand("Load From Existing Source");
        loadNew.addActionListener(this);

        menu.add(loadLast);
        menu.add(loadNew);
    }

    //MODIFIES: this
    //EFFECTS: loads devices from default, if exception thrown initializes new lists
    public void load() {
        JsonReader reader = new JsonReader(JSON_STORE);
        try {
            reader.read();
            desktops = reader.getDesktops();
            laptops = reader.getLaptops();
            phones = reader.getPhones();
            JOptionPane.showMessageDialog(null, "Devices Successfully Loaded", null,
                    JOptionPane.INFORMATION_MESSAGE, null);
            loadedFile = true;
        } catch (IOException io) {
            JOptionPane.showMessageDialog(null, "Failed To Load File, Loading Defaults", null,
                    JOptionPane.ERROR_MESSAGE, null);
            desktops = new ListOfDesktop();
            laptops = new ListOfLaptop();
            phones = new ListOfPhone();
            loadedFile = true;
        }
    }

    //MODIFIES: this
    //EFFECTS: loads devices from path, if exception thrown initializes new lists
    public void load(String path) {
        if (path != null && path.length() > 0) {
            JsonReader reader = new JsonReader(path);
            try {
                reader.read();
                desktops = reader.getDesktops();
                laptops = reader.getLaptops();
                phones = reader.getPhones();
                JOptionPane.showMessageDialog(null, "Devices Successfully Loaded", null,
                        JOptionPane.INFORMATION_MESSAGE, null);
                loadedFile = true;
            } catch (IOException io) {
                JOptionPane.showMessageDialog(null, "Failed To Load File, Loading Defaults", null,
                        JOptionPane.ERROR_MESSAGE, null);
                desktops = new ListOfDesktop();
                laptops = new ListOfLaptop();
                phones = new ListOfPhone();
                loadedFile = true;
            }
        }
    }

    //EFFECT: Starts the start menu to get name
    public void startMenu() {
        textField = new JTextField(20);
        JButton submit = new JButton("Enter");
        JLabel label = new JLabel("Please Enter Your Name");
        label.setForeground(Color.white);

        menuPanel.removeAll();
        menuPanel.setLayout(null);

        loadMenuImage();

        label.setBounds(250, 400, getWidth() / 3, getHeight() / 15);
        label.setFont(new Font("Century Gothic", Font.BOLD, 20));
        menuPanel.add(label);

        textField.setBounds(250, 440, getWidth() / 3, getHeight() / 20);
        textField.setFont(new Font("Century Gothic", Font.PLAIN, 15));
        menuPanel.add(textField);

        submit.setBounds(310, 490, getWidth() / 6, getHeight() / 25);
        submit.setActionCommand("enter");
        submit.addActionListener(this);
        submit.setForeground(Color.white);
        submit.setBackground(BUTTON_COLOR);

        menuPanel.add(submit);
    }

    //MODIFIES: this
    //EFFECTS: loads the default computer image
    private void loadMenuImage() {
        try {
            BufferedImage myPic = ImageIO.read(new File("data/computerDevArt.png"));
            JLabel picture = new JLabel(new ImageIcon(myPic));
            picture.setBounds(190, 70, getWidth() / 2, getHeight() / 2);
            menuPanel.add(picture);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //MODIFIES: this, desktops, laptops, phones
    //EFFECT: prompts user to load a file if they haven't already, then launches the default ui
    private void checkLoaded() {
        if (!loadedFile) {
            String[] options = new String[]{"Load From Last Save", "Load From File", "Load Defaults"};
            int progress = JOptionPane.showOptionDialog(null, "Load:",
                    null, JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    options, 0);
            if (progress == 2) {
                resetUi();
                launchDefaultUi();
                JOptionPane.showMessageDialog(null, "Loaded Defaults", null,
                        JOptionPane.INFORMATION_MESSAGE, null);
                desktops = new ListOfDesktop();
                laptops = new ListOfLaptop();
                phones = new ListOfPhone();
            } else if (progress == 1) {
                load(JOptionPane.showInputDialog(null, "Please Enter File Path", ""));
            } else if (progress == 0) {
                load();
            }
        }
        resetUi();
        launchDefaultUi();
    }

    //MODIFIES: this
    //EFFECTS: adds component to top dropdown menu
    public void addToTopMenu(JComponent component) {
        menu.add(component);
    }

    //MODIFIES: this
    //EFFECTS: resents the menuPanel to blank.
    public void resetUi() {
        menuPanel.removeAll();
        menuPanel.revalidate();
        menuPanel.repaint();
        menuPanel.setLayout(null);

        menu.removeAll();
        menu.repaint();
    }

    //MODIFIES: this
    //EFFECT: launches the homescreen ui
    public void launchDefaultUi() {
        homeScreen.launchGraphics();
    }

    public String getUserName() {
        return userName;
    }

    public ListOfDesktop getDesktops() {
        return desktops;
    }

    public ListOfLaptop getLaptops() {
        return laptops;
    }

    public ListOfPhone getPhones() {
        return phones;
    }

    //MODIFIES: this
    //EFFECT: processes button and menu inputs
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("enter")) {
            if (!(textField.getText().isEmpty())) {
                userName = textField.getText();
                checkLoaded();
            } else {
                JOptionPane.showMessageDialog(null, "Please Enter a Name", null,
                        JOptionPane.WARNING_MESSAGE);
            }
        } else if (e.getActionCommand().equals("Load Last Instance")) {
            load();
        } else if (e.getActionCommand().equals("Load From Existing Source")) {
            load(JOptionPane.showInputDialog(null, "Please Enter File Path", ""));
        }
    }

}
