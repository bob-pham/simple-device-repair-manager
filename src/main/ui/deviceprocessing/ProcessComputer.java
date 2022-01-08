package ui.deviceprocessing;

import devices.Computer;
import ui.DeviceRepairManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public abstract class ProcessComputer extends ProcessDevice implements ActionListener {
    protected JButton hardDrive;
    protected JButton solidState;
    protected JButton hardDriveCaddy;
    protected JButton ram;
    protected JButton cpu;
    protected JButton gpu;
    protected JButton operatingSystem;

    //EFFECT: constructor for computer processor
    public ProcessComputer(DeviceRepairManager deviceRep, JComponent parent) {
        super(deviceRep, parent);
        constructButtons();
    }

    //MODIFIES: this
    //EFFECTS: constructs buttons for processing computers
    private void constructButtons() {
        hardDrive = new JButton("Hard Drive");
        hardDrive.setActionCommand("Hard Drive");
        hardDrive.addActionListener(this);

        solidState = new JButton("Solid State");
        solidState.setActionCommand("Solid State");
        solidState.addActionListener(this);

        hardDriveCaddy = new JButton("Hard Drive Caddy");
        hardDriveCaddy.setActionCommand("Hard Drive Caddy");
        hardDriveCaddy.addActionListener(this);

        ram = new JButton("Ram");
        ram.setActionCommand("Ram");
        ram.addActionListener(this);

        cpu = new JButton("CPU");
        cpu.setActionCommand("CPU");
        cpu.addActionListener(this);

        gpu = new JButton("GPU");
        gpu.setActionCommand("GPU");
        gpu.addActionListener(this);

        operatingSystem = new JButton("Operating System");
        operatingSystem.setActionCommand("Operating System");
        operatingSystem.addActionListener(this);

        addToButtonsList();
    }

    //MODIFIES: this
    //EFFECTS: adds buttons to button list
    private void addToButtonsList() {
        buttons.add(hardDrive);
        buttons.add(solidState);
        buttons.add(hardDriveCaddy);
        buttons.add(ram);
        buttons.add(cpu);
        buttons.add(gpu);
        buttons.add(operatingSystem);
    }

    //MODIFIES: this, computer
    //EFFECTS: sets computer hard drive size
    protected void setHardDrive(Computer computer) {
        String input = JOptionPane.showInputDialog("Please Enter Device HDD Storage Size (GB)");

        if (input == null || input.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No HDD Size set", null,
                    JOptionPane.INFORMATION_MESSAGE);
            computer.setHardDrive(0);
        } else if (!(isInputInt(input))) {
            JOptionPane.showMessageDialog(null, "HDD size can only be inputted as an integer, "
                    + " please try again", null, JOptionPane.WARNING_MESSAGE);
            setHardDrive(computer);
        } else {
            JOptionPane.showMessageDialog(null, "HDD size set to " + input + " GB", null,
                    JOptionPane.INFORMATION_MESSAGE);
            computer.setHardDrive(Integer.parseInt(input));
            hardDrive.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        }
    }

    //MODIFIES: this, computer
    //EFFECTS: sets computer solid state size
    protected void setSolidState(Computer computer) {
        String input = JOptionPane.showInputDialog("Please Enter Device SSD Storage Size (GB)");

        if (input == null || input.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No SSD Size set", null,
                    JOptionPane.INFORMATION_MESSAGE);
            computer.setSolidState(0);
        } else if (!(isInputInt(input))) {
            JOptionPane.showMessageDialog(null, "SSD size can only be inputted as an integer, "
                    + " please try again", null, JOptionPane.WARNING_MESSAGE);
            setSolidState(computer);
        } else {
            JOptionPane.showMessageDialog(null, "SSD size set to " + input + " GB", null,
                    JOptionPane.INFORMATION_MESSAGE);
            computer.setSolidState(Integer.parseInt(input));
            solidState.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        }
    }

    //MODIFIES: this, computer
    //EFFECTS: sets computer ram size
    protected void setRam(Computer computer) {
        String input = JOptionPane.showInputDialog("Please Enter Device RAM Size (MB)");

        if (input == null || input.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No RAM Size set", null,
                    JOptionPane.INFORMATION_MESSAGE);
            computer.setRam(0);
        } else if (!(isInputInt(input))) {
            JOptionPane.showMessageDialog(null, "RAM size can only be inputted as an integer, "
                    + " please try again", null, JOptionPane.WARNING_MESSAGE);
            setRam(computer);
        } else {
            JOptionPane.showMessageDialog(null, "RAM size set to " + input + " MB", null,
                    JOptionPane.INFORMATION_MESSAGE);
            computer.setRam(Integer.parseInt(input));
            ram.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        }
    }

    //MODIFIES: this, computer
    //EFFECTS: sets computer hard drive caddy status
    protected void setHardDriveCaddy(Computer computer) {
        int hdc = JOptionPane.showOptionDialog(null, "Does the Computer contain a Hard Drive "
                        + "Caddy?", null, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, null, null);

        JOptionPane.showMessageDialog(null, "Computer HDC set to " + (hdc == 0));

        computer.setHardDriveCaddy(hdc == 0);

        if (hdc == 0) {
            hardDriveCaddy.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        } else {
            hardDriveCaddy.setBorder(BorderFactory.createLineBorder(Color.RED));
        }
    }

    //MODIFIES: this, computer
    //EFFECT: sets computer cpu
    protected void setCpu(Computer computer) {
        String cpu = JOptionPane.showInputDialog("Please Enter The Computer CPU");

        if (cpu == null || cpu.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No CPU Set", null,
                    JOptionPane.INFORMATION_MESSAGE);
            computer.setCpu("N/A");
        } else {
            JOptionPane.showMessageDialog(null, "Computer CPU set", null,
                    JOptionPane.INFORMATION_MESSAGE);
            computer.setCpu(cpu);
            this.cpu.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        }
    }

    //MODIFIES: this, computer
    //EFFECT: sets computer gpu
    protected void setGpu(Computer computer) {
        String gpu = JOptionPane.showInputDialog("Please Enter The Computer GPU");

        if (gpu == null || gpu.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No GPU Set", null,
                    JOptionPane.INFORMATION_MESSAGE);
            computer.setGpu("N/A");
        } else {
            JOptionPane.showMessageDialog(null, "Computer GPU set", null,
                    JOptionPane.INFORMATION_MESSAGE);
            computer.setGpu(gpu);
            this.gpu.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        }
    }

    //MODIFIES: this, computer
    //EFFECT: sets computer gpu
    protected void setOS(Computer computer) {
        String os = JOptionPane.showInputDialog("Please Enter The Computer OS");

        if (os == null || os.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No OS Set", null,
                    JOptionPane.INFORMATION_MESSAGE);
            computer.setOS("N/A");
        } else {
            JOptionPane.showMessageDialog(null, "Computer OS set", null,
                    JOptionPane.INFORMATION_MESSAGE);
            computer.setOS(os);
            operatingSystem.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        }
    }
}
