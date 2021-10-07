package ui;

import model.Person;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Represents a window to create a new profile
public class CreateProfileDialog {

    private JDialog screen;
    private JPanel newPanel;

    private TextField newProfileNameField;
    private TextField newProfileDayField;
    private TextField newProfileMonthField;
    private TextField newProfileYearField;

    private JButton makeNewProfileButton;

    private SchedulerAppDisplay schedulerAppDisplay;

    //EFFECTS: Creates a new JDialog and adds it to schedulerAppDisplay
    public CreateProfileDialog(SchedulerAppDisplay schedulerAppDisplay) {
        this.schedulerAppDisplay = schedulerAppDisplay;
        init();

        TitleLabel newProfileTitle = new TitleLabel(schedulerAppDisplay, newPanel, "Create new profile", 0, 0);
        TitleLabel spacerLabel = new TitleLabel(schedulerAppDisplay, newPanel, "", 1, 0);
        TextLabel newProfileName = new TextLabel(schedulerAppDisplay, newPanel, "Name:", 0, 1);
        this.newProfileNameField = new TextField(schedulerAppDisplay, newPanel, 1, 1);
        ((JTextField) this.newProfileNameField.thisComponent).setText("");
        TextLabel newProfileDay = new TextLabel(schedulerAppDisplay, newPanel, "Day of birth:", 0, 2);
        this.newProfileDayField = new TextField(schedulerAppDisplay, newPanel, 1, 2);
        TextLabel newProfileMonth = new TextLabel(schedulerAppDisplay, newPanel, "Month of birth:", 0, 3);
        this.newProfileMonthField = new TextField(schedulerAppDisplay, newPanel, 1, 3);
        TextLabel newProfileYear = new TextLabel(schedulerAppDisplay, newPanel, "Year of birth:", 0, 4);
        this.newProfileYearField = new TextField(schedulerAppDisplay, newPanel, 1, 4);
        this.makeNewProfileButton = new JButton("Make new profile");
        this.makeNewProfileButton.addActionListener(new NewProfileButtonClickHandler());
        this.makeNewProfileButton.setBorderPainted(false);
        this.makeNewProfileButton.setFocusPainted(false);
        this.makeNewProfileButton.setContentAreaFilled(true);
        this.makeNewProfileButton.setBackground(Color.LIGHT_GRAY);
        this.newPanel.add(makeNewProfileButton);
    }

    // MODIFIES: this
    // EFFECTS: initializes JPanel and JDialog
    public void init() {
        this.newPanel = new JPanel();
        this.newPanel.setLayout(new GridLayout(6, 2));
        this.schedulerAppDisplay.add(newPanel);
        this.screen = new JDialog(schedulerAppDisplay, "Create profile");
        this.screen.add(newPanel);
        this.screen.setSize(350, 250);
        this.screen.setLocation(this.schedulerAppDisplay.getX(), this.schedulerAppDisplay.getY());
        this.screen.setVisible(true);
    }

    private class NewProfileButtonClickHandler implements ActionListener {

        // MODIFIES: this, schedulerAppDisplay
        // EFFECTS: creates new Person with respective values and adds it to PersonList. Disposes JDialog afterwards
        @Override
        public void actionPerformed(ActionEvent e) {
            schedulerAppDisplay.playClickSound();

            String name = ((JTextField) newProfileNameField.thisComponent).getText();
            int day = Integer.parseInt(((JTextField) newProfileDayField.thisComponent).getText());
            int month = Integer.parseInt(((JTextField) newProfileMonthField.thisComponent).getText());
            int year = Integer.parseInt(((JTextField) newProfileYearField.thisComponent).getText());

            schedulerAppDisplay.personListModel.addPerson(name, day, month, year);
            schedulerAppDisplay.appointmentInfo.setText(schedulerAppDisplay.personListModel.toString());
            screen.dispose();
        }
    }
}
