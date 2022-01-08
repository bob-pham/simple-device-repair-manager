package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public abstract class DeviceSelection extends Menu implements ActionListener {
    private static final Dimension BUTTON_DIMENSION = new Dimension(500,100);
    private static final Dimension GAP = new Dimension(0,50);
    protected final Font defaultFont;
    protected JButton desktop;
    protected JButton laptop;
    protected JButton phone;
    protected JLabel header;

    public DeviceSelection(DeviceRepairManager deviceRepair, JComponent parent) {
        super(deviceRepair, parent);
        defaultFont = new Font("Century Gothic", Font.BOLD, 15);

        desktop = new JButton("Desktop");
        laptop = new JButton("Laptop");
        phone = new JButton("Phone");

        constructButtons(desktop);
        constructButtons(laptop);
        constructButtons(phone);

        header = new JLabel("Please Choose Device Type:", SwingConstants.CENTER);
        header.setFont(new Font("Century Gothic", Font.BOLD, 25));
        header.setForeground(Color.white);
    }

    //MODIFIES: this
    //EFFECTS: constructs buttons and sets action command and action listener to them
    private void constructButtons(JButton button) {
        button.setFont(defaultFont);
        button.setActionCommand(button.getText());
        button.setMinimumSize(BUTTON_DIMENSION);
        button.setMaximumSize(BUTTON_DIMENSION);
        button.setPreferredSize(BUTTON_DIMENSION);
        button.setForeground(Color.white);
        button.setBackground(DeviceRepairManager.BUTTON_COLOR);
        button.addActionListener(this);
    }

    //MODIFIES: this parent
    //EFFECTS: launches graphics for Device Selection Menu
    @Override
    protected void launchGraphics() {
        JMenuItem exit = new JMenuItem("Exit");
        exit.setActionCommand("exit");
        exit.addActionListener(this);
        deviceRepair.addToTopMenu(exit);

        parent.setLayout(new BoxLayout(parent, BoxLayout.Y_AXIS));
        parent.setAlignmentX(Component.CENTER_ALIGNMENT);
        header.setAlignmentX(Component.CENTER_ALIGNMENT);
        desktop.setAlignmentX(Component.CENTER_ALIGNMENT);
        laptop.setAlignmentX(Component.CENTER_ALIGNMENT);
        phone.setAlignmentX(Component.CENTER_ALIGNMENT);

        parent.setForeground(DeviceRepairManager.BACKGROUND_COLOR);
        parent.add(Box.createRigidArea(GAP));
        parent.add(header);
        parent.add(Box.createRigidArea(GAP));
        parent.add(desktop);
        parent.add(Box.createRigidArea(GAP));
        parent.add(laptop);
        parent.add(Box.createRigidArea(GAP));
        parent.add(phone);
        parent.add(Box.createRigidArea(GAP));
    }
}
